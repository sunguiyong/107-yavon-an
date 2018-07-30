package com.zt.yavon.module.device.share.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.common.base.utils.LogUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.device.share.contract.AuthorDevContract;
import com.zt.yavon.module.device.share.presenter.AuthorDevPresenter;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.utils.TimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class AuthorActivity extends BaseActivity<AuthorDevPresenter> implements AuthorDevContract.View{
    @BindView(R.id.et_phone_author)
    EditText etPhone;
    @BindView(R.id.tv_start_time_author)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time_author)
    TextView tvEndTime;
    private Dialog dialog;
    private List<Object> dataList;
    private String[] defaultData = new String[3];
    private String[] defaultData2 = new String[3];
    private long startMilTime,endMilTime;
    private String machineId;
    @Override
    public int getLayoutId() {
        return R.layout.activity_author;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        machineId = getIntent().getStringExtra("machine_id");
    }

    @Override
    public void initView() {
        setTitle("临时授权");
//        setRightMenuImage(R.mipmap.more_right);
        initData();
    }

    private void initData() {
        dataList = new ArrayList<>();
        List<String> monthList = new ArrayList<>();
        Map<String, List<String>> dayList = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        int curMonth = calendar.get(Calendar.MONTH);
        defaultData[0] = calendar.get(Calendar.YEAR)+"-"+(curMonth+1);
        defaultData2[0] = calendar.get(Calendar.YEAR)+"-"+(curMonth+1);
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        defaultData[1] = curDay+"";
        defaultData2[1] = curDay+"";
        defaultData[2] = calendar.get(Calendar.HOUR_OF_DAY)+"";
        defaultData2[2] = calendar.get(Calendar.HOUR_OF_DAY)+"";
        for(int i = curMonth;i<curMonth+6;i++){
            if(i > curMonth){
                calendar.add(Calendar.MONTH,1);
            }
            String monthString = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1);
            monthList.add(monthString);
//            LogUtil.d("=============maxdays:"+calendar.getActualMaximum(Calendar.DATE));
            List<String> days = new ArrayList<>();
            for(int j = 1;j<=calendar.getActualMaximum(Calendar.DATE);j++){
                if(i == curMonth && j < curDay){
                    continue;
                }
                days.add(j+"");
            }
            dayList.put(monthString,days);
//            LogUtil.d("===========days size:"+ days.size());
        }
        dataList.add(monthList);
        dataList.add(dayList);
        List<String> hourList = new ArrayList<>();
        for(int i = 0;i<24;i++){
            hourList.add(i+"");
        }
        dataList.add(hourList);
    }

    @OnClick({R.id.tv_start_time_author,R.id.tv_end_time_author,R.id.tv_submit_author})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.tv_start_time_author:
                DialogUtil.dismiss(dialog);
                dialog = DialogUtil.createTimeWheelViewDialog2(this, defaultData, dataList, new DialogUtil.OnSelectCompleteListening2() {
                    @Override
                    public void onSelectComplete() {
                        String newDate = defaultData[0]+"-"+defaultData[1]+" "+defaultData[2];
                        try {
                            Date date = new SimpleDateFormat("y-M-d h").parse(newDate);
                            startMilTime = date.getTime();
                            newDate = TimeUtils.getFormatStringByDate("yyyy-MM-dd HH:mm:ss",date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        tvStartTime.setText(newDate);
                    }
                });
                break;
            case R.id.tv_end_time_author:
                DialogUtil.dismiss(dialog);
                dialog = DialogUtil.createTimeWheelViewDialog2(this, defaultData2, dataList, new DialogUtil.OnSelectCompleteListening2() {
                    @Override
                    public void onSelectComplete() {
                        String newDate = defaultData2[0]+"-"+defaultData2[1]+" "+defaultData2[2];
                        try {
                            Date date = new SimpleDateFormat("y-M-d h").parse(newDate);
                            endMilTime = date.getTime();
                            newDate = TimeUtils.getFormatStringByDate("yyyy-MM-dd HH:mm:ss",date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        tvEndTime.setText(newDate);
                    }
                });
                break;
            case R.id.tv_submit_author:
                mPresenter.shareAuthor(machineId,
                        etPhone.getText().toString().trim(),
                        tvStartTime.getText().toString().trim(),
                        tvEndTime.getText().toString().trim(),startMilTime,endMilTime);
                break;
        }
    }
    public static void startAction(Activity context, String machine_id, int reqCode){
        Intent intent = new Intent(context, AuthorActivity.class);
        intent.putExtra("machine_id",machine_id);
        context.startActivityForResult(intent,reqCode);
    }

    @Override
    protected void onDestroy() {
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }

    @Override
    public void shareSuccess() {
        setResult(RESULT_OK);
        finish();
    }
}
