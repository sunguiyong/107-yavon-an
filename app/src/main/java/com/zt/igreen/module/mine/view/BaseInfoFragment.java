package com.zt.igreen.module.mine.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.common.base.utils.ToastUtil;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseFragment;
import com.zt.igreen.module.data.HealthInfoBean;
import com.zt.igreen.module.main.frame.view.MainActivity;
import com.zt.igreen.module.mine.contract.HelathInfoContract;
import com.zt.igreen.module.mine.presenter.HelathInfoPresenter;
import com.zt.igreen.utils.DialogUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


/**
 * Created by hp on 2018/6/11.
 */

@SuppressLint("ValidFragment")
public class BaseInfoFragment extends BaseFragment<HelathInfoPresenter> implements HelathInfoContract.View{
    @BindView(R.id.tv_name1)
    TextView tvName1;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.lin_one)
    LinearLayout linOne;
    @BindView(R.id.tv_name_height)
    TextView tvNameHeight;
    @BindView(R.id.tv_unit_height)
    TextView tvUnitHeight;
    @BindView(R.id.lin_two)
    LinearLayout linTwo;
    @BindView(R.id.tv_name_weight)
    TextView tvNameWeight;
    @BindView(R.id.tv_unit_weight)
    TextView tvUnitWeight;
    @BindView(R.id.lin_three)
    LinearLayout linThree;
    @BindView(R.id.tv_name_four)
    TextView tvNameFour;
    @BindView(R.id.tv_unit_four)
    TextView tvUnitFour;
    @BindView(R.id.lin_four)
    LinearLayout linFour;
    @BindView(R.id.xian)
    LinearLayout xian;
    private List<Integer> dataList;
    private List<Integer> dataList1;
    private List<Integer> dataList2;
    String Style;
    private Dialog dialog;
    private Dialog dialog1;
    private Dialog dialog2;
    private int defaultCount,defaultDay,defaultMonth,defaultHeight;
    private List<Object> dataList_two;
    String [] Base=new String[4];
    String [] health=new String[3];
    String [] live=new String[4];
    private TimePickerView pvTime1;
    String xueya;
    String sex;
    String xinzhang;
    String tangniao;
    String smoke;
    String drink;
    String diet;
    String work_type;
    private PersionDataActivity mActivity;

    public BaseInfoFragment(String shouye) {
        Style=shouye;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_base_info;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        mPresenter.getUserINfo();
    }

    @Override
    protected void initView() {
        mActivity = (PersionDataActivity) getActivity();
        if ("zhuangkang".equals(Style)){
            tvName1.setText("是否有高血压病");
            tvNameHeight.setText("是否有心脏病");
            tvNameWeight.setText("是否有糖尿病");
            xian.setVisibility(View.GONE);
            linFour.setVisibility(View.GONE);
            tvUnit.setText(health[0]);
            tvUnitHeight.setText(health[1]);
            tvUnitWeight.setText(health[2]);

        }else if ("live".equals(Style)){
            xian.setVisibility(View.VISIBLE);
            linFour.setVisibility(View.VISIBLE);
            tvName1.setText("是否吸烟");
            tvNameHeight.setText("是否饮酒");
            tvNameWeight.setText("饮食偏好");
            tvNameFour.setText("工作类型");
            tvUnit.setText(live[0]);
            tvUnitHeight.setText(live[1]);
            tvUnitWeight.setText(live[2]);
            tvUnitFour.setText(live[3]);
        }else{
            tvName1.setText("生日");
            tvNameHeight.setText("身高");
            tvNameWeight.setText("体重");
            tvNameWeight.setText("性别");
            xian.setVisibility(View.VISIBLE);
            linFour.setVisibility(View.VISIBLE);
            tvUnit.setText(Base[0]);
            tvUnitHeight.setText(Base[1]+"cm");
            tvUnitWeight.setText(Base[2]+"kg");
        }

        mActivity.findViewById(R.id.tv_right_header).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Log.e("xuxinyi","xuxinyi");
          if (Base[0]!=null&&Base[1]!=null&&Base[2]!=null&&Base[3]!=null&&health[0]!=null
                  &&health[1]!=null&&health[2]!=null&&live[0]!=null&&live[1]!=null&&live[2]!=null&&live[3]!=null){
           if ("是".equals(health[0])){
               xueya="1";
           }else if ("否".equals(health[0])){
               xueya="0";
           }

              if ("是".equals(health[1])){
                  xinzhang="1";
              }else if ("否".equals(health[1])){
                  xinzhang="0";
              }
              if ("是".equals(health[2])){
                  tangniao="1";
              }else if ("否".equals(health[2])){
                  tangniao="0";
              }
              if ("男".equals(Base[3])){
                  sex="1";
              }else if ("女".equals(Base[3])){
                  sex="0";
              }
             if ("不吸烟".equals(live[0])){
                  smoke="NO";
             }else if ("已戒烟".equals(live[0])){
                 smoke="QUIT";
             }else if ("偶尔".equals(live[0])){
                 smoke="SOMETIME";
             }else if ("经常".equals(live[0])){
                 smoke="OFTEN";
             }
              if ("不饮酒".equals(live[1])){
                  drink="NO";
              }else if ("已戒酒".equals(live[1])){
                  drink="QUIT";
              }else if ("偶尔".equals(live[1])){
                  drink="SOMETIME";
              }else if ("经常".equals(live[1])){
                  drink="OFTEN";
              }
              if ("正常".equals(live[2])){
                  diet="NORMAL";
              }else if ("不规律".equals(live[2])){
                  diet="IRREGULAR";
              }
              if ("久坐".equals(live[3])){
                  work_type="SEDENTARY";
              }else if ("久站".equals(live[3])){
                  work_type="LONG_STAND";
              }else if ("重体力活".equals(live[3])){
                  work_type="HEAVY_WORK";
              }else if ("其他".equals(live[3])){
                  work_type="OTHER";
              }
            mPresenter.getInfo(Base[0],Base[1],Base[2],sex,xueya,xinzhang,tangniao,smoke,drink,diet,work_type);
          }else{
              ToastUtil.customToastView(getActivity(),"请填写完成",Toast.LENGTH_LONG,null);
          }
          }
      });
    }





    @OnClick({R.id.lin_one, R.id.lin_two, R.id.lin_three, R.id.lin_four, R.id.xian})
    public void onClick(View view) {
        int viewId = view.getId();
        switch (view.getId()) {
            case R.id.lin_one:
                if ("zhuangkang".equals(Style)){
                    final List<String> mOptionsItems = new ArrayList<>();
                    mOptionsItems.add("是");
                    mOptionsItems.add("否");
                    Dialog(tvUnit,mOptionsItems);
                    String stunit=tvUnit.getText().toString().trim();
                    health[0]=stunit;
                }else if ("live".equals(Style)){
                    final List<String> mOptionsItems = new ArrayList<>();
                    mOptionsItems.add("不吸烟");
                    mOptionsItems.add("已戒烟");
                    mOptionsItems.add("偶尔");
                    mOptionsItems.add("经常");
                    Dialog(tvUnitWeight,mOptionsItems);
                    String stUnitWeight=tvUnitWeight.getText().toString().trim();
                    live[0]=stUnitWeight;
                }else{
                    initTimePicker() ;
                    pvTime1.show();
                    /*if (dataList == null) {
                        dataList = new ArrayList<>();
                        for (int i = 1; i < 240; i++) {
                            dataList.add(i);
                        }
                    }
                    dialog = DialogUtil.createTimeWheelViewDialog(getActivity(), "a", defaultHeight, dataList, new DialogUtil.OnSelectCompleteListening() {
                        @Override
                        public void onSelectComplete(int data) {
                            tvUnit.setText(data+"cm");
                            defaultHeight = data;
                            StartFragment("two");
                        }
                    });*/
                }
                break;
            case R.id.lin_two:
                if ("zhuangkang".equals(Style)){
                    final List<String> mOptionsItems = new ArrayList<>();
                    mOptionsItems.add("是");
                    mOptionsItems.add("否");
                    Dialog(tvUnitHeight,mOptionsItems);
                    String stUnitHeight=tvUnitHeight.getText().toString();
                    health[1]=stUnitHeight;
                }else if ("live".equals(Style)){
                    final List<String> mOptionsItems = new ArrayList<>();
                    mOptionsItems.add("不饮酒");
                    mOptionsItems.add("已戒酒");
                    mOptionsItems.add("偶尔");
                    mOptionsItems.add("经常");
                    Dialog(tvUnitHeight,mOptionsItems);
                    String stUnitHeight=tvUnitHeight.getText().toString();
                    live[1]=stUnitHeight;
                }else{
                    if (dataList1 == null) {
                        dataList1 = new ArrayList<>();
                        for (int i = 1; i < 240; i++) {
                            dataList1.add(i);
                        }
                    }
                dialog1 = DialogUtil.createTimeWheelViewDialog(getActivity(), "cm", defaultCount, dataList1, new DialogUtil.OnSelectCompleteListening() {
                    @Override
                    public void onSelectComplete(int data) {
                        tvUnitHeight.setText(data+"cm");
                        defaultCount = data;
                        Base[1]=data+"";
                    }
                });
                }
                break;
            case R.id.lin_three:
                if ("zhuangkang".equals(Style)){
                    final List<String> mOptionsItems = new ArrayList<>();
                    mOptionsItems.add("是");
                    mOptionsItems.add("否");
                    Dialog(tvUnitWeight,mOptionsItems);
                    String stUnitWeight=tvUnitWeight.getText().toString();
                    health[2]=stUnitWeight;
                }else if ("live".equals(Style)){
                    final List<String> mOptionsItems = new ArrayList<>();
                    mOptionsItems.add("正常");
                    mOptionsItems.add("不规律");
                    Dialog(tvUnitWeight,mOptionsItems);
                    String stUnitWeight=tvUnitWeight.getText().toString();
                    live[2]=stUnitWeight;
                }else{
                    if (dataList2 == null) {
                        dataList2 = new ArrayList<>();
                        for (int i = 1; i < 240; i++) {
                            dataList2.add(i);
                        }
                    }
                    dialog2 = DialogUtil.createTimeWheelViewDialog(getActivity(), "kg", defaultMonth, dataList2, new DialogUtil.OnSelectCompleteListening() {
                        @Override
                        public void onSelectComplete(int data) {
                            tvUnitWeight.setText(data+"kg");
                            defaultMonth = data;
                            Base[2]=data+"";
                        }
                    });
                }
                break;
            case R.id.lin_four:
                if ("live".equals(Style)){
                    final List<String> mOptionsItems = new ArrayList<>();
                    mOptionsItems.add("久坐");
                    mOptionsItems.add("久站");
                    mOptionsItems.add("重体力活");
                    mOptionsItems.add("其他");
                    Dialog(tvUnitFour,mOptionsItems);
                    String strUnitFour=tvUnitFour.getText().toString();
                    live[3]=strUnitFour;
                }else {
                    final List<String> mOptionsItems = new ArrayList<>();
                    mOptionsItems.add("男");
                    mOptionsItems.add("女");
                    Dialog(tvUnitFour,mOptionsItems);
                    String strUnitFour=tvUnitFour.getText().toString();
                    Base[3]=strUnitFour;
                }
                break;
            case R.id.xian:
                break;
        }
    }
    public  void StartFragment(String str){
        String str_tvunit=tvUnit.getText().toString().trim();
        String str_tvUnitHeight=tvUnitHeight.getText().toString().trim();
        String str_tvNameWeight=tvUnitWeight.getText().toString().trim();
        if ("shouye".equals(Style)){
           Base[0]=str_tvunit;
           Base[1]=str_tvUnitHeight;
           Base[2]=str_tvNameWeight;

        }else if ("zhuangkang".equals(Style)){
            health[0]=str_tvunit;
            health[1]=str_tvUnitHeight;
            health[2]=str_tvNameWeight;
        }


    }
    public void Dialog(TextView text,List<String> list){
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                text.setText(list.get(options1));
            }
        }).setSubmitColor(Color.parseColor("#8DBB30"))
                .setCancelColor(Color.parseColor("#9B9B9B")).build();
        pvOptions.setPicker(list);
        pvOptions.show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
    private void initTimePicker() {//选择出生年月日
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        SimpleDateFormat formatter_year = new SimpleDateFormat("yyyy ");
        String year_str = formatter_year.format(curDate);
        int year_int = (int) Double.parseDouble(year_str);


        SimpleDateFormat formatter_mouth = new SimpleDateFormat("MM ");
        String mouth_str = formatter_mouth.format(curDate);
        int mouth_int = (int) Double.parseDouble(mouth_str);

        SimpleDateFormat formatter_day = new SimpleDateFormat("dd ");
        String day_str = formatter_day.format(curDate);
        int day_int = (int) Double.parseDouble(day_str);


        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year_int, mouth_int - 1, day_int);

        //时间选择器
        //年月日时分秒 的显示与否，不设置则默认全部显示
//默认设置为年月日时分秒
//取消按钮文字
//确认按钮文字
//设置选中项的颜色
//设置没有被选中项的颜色
//设置X轴倾斜角度[ -90 , 90°]
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
        pvTime1 = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvUnit.setText(getTime(date));
                Base[0]=getTime(date);
            }
        }).setType(new boolean[]{true, true, true, false, false, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("年", "月", "日", "", "", "")//默认设置为年月日时分秒
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .isCenterLabel(false)
                .setDividerColor(Color.parseColor("#BABABA"))
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTextColorOut(Color.parseColor("#BABABA"))//设置没有被选中项的颜色
                .setDate(selectedDate)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(-10, 0,10, 0, 0, 0)//设置X轴倾斜角度[ -90 , 90°]
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void returnINfo(HealthInfoBean bean) {
      ToastUtil.show(getActivity(),"提交完成",Toast.LENGTH_LONG);
    }

    @Override
    public void returnUserINfo(HealthInfoBean bean) {
         String birthday=bean.getBirthday();
         Base[0]=birthday;
         int height=bean.getHeight();
         Base[1]=height+"";
         int weight=bean.getWeight();
         Base[2]=weight+"";
         int sex_bie=bean.getSex();
        if (sex_bie==1){
            sex="1";
            Base[3]="男";
        }else if (sex_bie==0){
            sex="0";
            Base[3]="女";
        }
         int is_high_blood_pressure=bean.getIs_high_blood_pressure();
        if (is_high_blood_pressure==1){
            xueya="1";
            health[0]="是";
        }else if (is_high_blood_pressure==0){
            xueya="0";
            health[0]="否";
        }
        int is_heart_disease=bean.getIs_heart_disease();
        if (is_heart_disease==1){
            xinzhang="1";
            health[1]="是";
        }else if (is_heart_disease==0){
            xinzhang="0";
            health[1]="否";
        }
        int is_diabetes=bean.getIs_diabetes();
        if (is_diabetes==1){
            tangniao="1";
            health[2]="是";
        }else if (is_diabetes==0){
            tangniao="0";
            health[2]="否";
        }
        String smoke=bean.getSmoke();
        if ("NO".equals(smoke)){
            smoke="NO";
            live[0]="不吸烟";
        }else if ("QUIT".equals(smoke)){
            smoke="QUIT";
            live[0]="已戒烟";
        }else if ("SOMETIME".equals(smoke)){
            smoke="SOMETIME";
            live[0]="偶尔";
        }else if ("OFTEN".equals(smoke)){
            smoke="OFTEN";
            live[0]="经常";
        }
        String drink=bean.getDrink();
        if ("NO".equals(drink)){
            drink="NO";
            live[1]="不饮酒";
        }else if ("QUIT".equals(drink)){
            drink="QUIT";
            live[1]="已戒酒";
        }else if ("SOMETIME".equals(drink)){
            drink="SOMETIME";
            live[1]="偶尔";
        }else if ("OFTEN".equals(drink)){
            drink="OFTEN";
            live[1]="偶尔";
        }
        String diet=bean.getDiet();
        if ("NORMAL".equals(diet)){
            diet="NORMAL";
            live[2]="正常";
        }else if ("IRREGULAR".equals(diet)){
            diet="IRREGULAR";
            live[2]="不规律";
        }
        String work_type=bean.getWork_type();
        if ("SEDENTARY".equals(work_type)){
            work_type="SEDENTARY";
            live[3]="久坐";
        }else if ("LONG_STAND".equals(work_type)){
            work_type="LONG_STAND";
            live[3]="久站";
        }else if ("HEAVY_WORK".equals(work_type)){
            work_type="HEAVY_WORK";
            live[3]="重体力活";
        }else if ("OTHER".equals(work_type)){
            work_type="OTHER";
            live[3]="其他";
        }
        if ("zhuangkang".equals(Style)){
            tvUnit.setText(health[0]);
            tvUnitHeight.setText(health[1]);
            tvUnitWeight.setText(health[2]);
        }else if ("live".equals(Style)){
            tvUnit.setText(live[0]);
            tvUnitHeight.setText(live[1]);
            tvUnitWeight.setText(live[2]);
            tvUnitFour.setText(live[3]);
        }else{
            tvUnit.setText(Base[0]);
            tvUnitHeight.setText(Base[1]+"cm");
            tvUnitWeight.setText(Base[2]+"kg");
            tvUnitFour.setText(Base[3]);
        }
    }
}
