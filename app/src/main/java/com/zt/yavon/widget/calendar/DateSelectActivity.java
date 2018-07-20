package com.zt.yavon.widget.calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述：时间段选择
 * 时间：2018/1/27/027
 * 作者：xjh
 */
public class DateSelectActivity extends BaseActivity {
    @BindView(R.id.recyclerView_calendar)
    RecyclerView mRecyclerView;
    public static final int RERSULT_CODE = 200;
    public static final String START = "START";
    public static final String END = "END";
    public static final String DAY_NUMBER = "DAY_NUMBER";
    private List<MonthBean> monthTimeEntities;
//    private List<DayBean> currList;
//    private List<DayBean> nextList;
    private List<List<DayBean>> dataList = new ArrayList<>();
    private DayBean today,start,end;

    @Override
    public int getLayoutId() {
        return R.layout.activity_calendar_select;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle(getString(R.string.date_choose));
        initData();
        initRecyclerView();
    }


    private void initData() {
        monthTimeEntities = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int currYear = c.get(Calendar.YEAR);
        int currMonth = c.get(Calendar.MONTH) + 1;
        today = new DayBean(currYear,currMonth,c.get(Calendar.DAY_OF_MONTH),"",false,false,false);
        monthTimeEntities.add(new MonthBean(currYear, currMonth));
        for(int i = 0;i<60;i++){
            c.add(Calendar.MONTH, 1);  // 得到下一个月
            int nextYear = c.get(Calendar.YEAR);
            int nextMonth = c.get(Calendar.MONTH) + 1;
            monthTimeEntities.add(new MonthBean(nextYear, nextMonth));
        }
        handleData();
    }

    private void handleData() {

        for (int i = 0; i < monthTimeEntities.size(); i++) {
                ArrayList<DayBean> currList = new ArrayList<>();
                MonthBean entity = monthTimeEntities.get(i);
                int month = entity.getMonth();
                int year = entity.getYear();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month - 1, 1);  // 设置这个月的第一天
//                int currYear = calendar.get(Calendar.YEAR);
//                int currMonth = calendar.get(Calendar.MONTH);
                int day_of_week = calendar.get(Calendar.DAY_OF_WEEK); // 获取当前这天的星期
                // 获取第一天的前面空了几个星期
                int offset = day_of_week - 1;
                // 获取当月最后一天
//                calendar.add(Calendar.MONTH, 1); // 下一个月
//                calendar.add(Calendar.DATE, -1); // 减一天
                int totalDays = calendar.getActualMaximum(Calendar.DATE);  // 获取当月的天数
//            Log.d("======","=======totalDays:"+calendar.getActualMaximum(Calendar.DATE)+",min:"+calendar.getActualMinimum(Calendar.DATE));
                for (int i1 = 0; i1 < offset; i1++) {
//                    String jr = DateUtils.getJR(0, 0);
                    currList.add(new DayBean(0, 0, 0, "", false, false,false));
                }
                for (int i1 = 0; i1 < totalDays; i1++) {
                    boolean checkable = true;
                    if(i == 0 && i1+1<today.getDay()){
                        checkable = false;
                    }
                    currList.add(new DayBean(year, month , (i1 + 1), "", false, false,checkable));
//                    calendar.add(Calendar.DATE, 1); // 减一天
                }
                dataList.add(currList);
        }
    }

    private void initRecyclerView() {
        final MonthDateSelectAdapter adapter = new MonthDateSelectAdapter(this, monthTimeEntities, dataList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setListener(new MonthDateSelectAdapter.onDateSelectListener() {
            @Override
            public void onSelect(int pos, int month) {
                // 判断当前有多少个选中的
                List<DayBean> selectedList = new ArrayList<>();
                DayBean curBean = dataList.get(month).get(pos);
                for (List<DayBean> dayList : dataList) {
                    for(DayBean bean:dayList) {
                        if(bean.isSelect())
                        selectedList.add(bean);
                    }
                }
                // 如果选了2个全部重置
                if (selectedList.size() > 1) {
                    for (List<DayBean> dayList : dataList) {
                        for(DayBean bean:dayList) {
                            bean.setSelect(false);
                            bean.setMiddle(false);
                            bean.setJr("");
                        }
                    }
                    // 设置当前选中
                    curBean.setSelect(true);
                    curBean.setJr("开始");
                    start = curBean;
                    end = null;
                } else if (selectedList.size() == 1) {
                    DayBean selectedBean = selectedList.get(0);
                    int isBefore = isBefore(curBean,selectedBean);
                    if(isBefore < 0){//当前比之前选中的日期早，选择的是开始日期
                        selectedBean.setSelect(false);
                        selectedBean.setMiddle(false);
                        selectedBean.setJr("");
                        curBean.setSelect(true);
                        curBean.setMiddle(false);
                        curBean.setJr("开始");
                        start = curBean;
                        end = null;
                    }else if(isBefore > 0){//选择的是结束日期
                        curBean.setSelect(true);
                        curBean.setMiddle(false);
                        curBean.setJr("结束");
                        end = curBean;
                        //把中间日期加背景色
                        for (List<DayBean> dayList : dataList) {
                            for(DayBean bean:dayList) {
                                if(isBefore(bean,selectedBean) > 0 && isBefore(bean,curBean) < 0 ){
                                    bean.setSelect(false);
                                    bean.setMiddle(true);
                                    bean.setJr("");
                                }
                            }
                        }

                    }
                } else if (selectedList.size() == 0) {
                    curBean.setSelect(true);
                    curBean.setJr("开始");
                    start = curBean;
                    end = null;
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private int isBefore(DayBean curBean,DayBean selectedBean){
        if(curBean.getYear()<selectedBean.getYear()){
            return -1;
        }else if(curBean.getYear()>selectedBean.getYear()){
            return 1;
        }else{
            if(curBean.getMonth()<selectedBean.getMonth()){
                return -1;
            }else if(curBean.getMonth()>selectedBean.getMonth()){
                return 1;
            }else{
                if(curBean.getDay()<selectedBean.getDay()){
                    return -1;
                }else if(curBean.getDay()>selectedBean.getDay()){
                    return 1;
                }else{
                    return  0;
                }
            }
        }
    }
    public static void startAction(Activity context, int reqCode){
        Intent intent = new Intent(context,DateSelectActivity.class);
        context.startActivityForResult(intent,reqCode);
    }
    @OnClick({R.id.tv_right_header})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.tv_right_header:
                sure();
                break;
        }
    }
    public void sure() {
        if(start == null){
            ToastUtil.showShort(this,"请选择开始日期");
            return;
        }
        if(end == null){
            ToastUtil.showShort(this,"请选择截止日期");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("date",start.toString()+"-"+end.toString());
        setResult(RESULT_OK,intent);
        finish();
    }
}
