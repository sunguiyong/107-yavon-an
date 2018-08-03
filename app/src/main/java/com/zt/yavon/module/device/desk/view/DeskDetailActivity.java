package com.zt.yavon.module.device.desk.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.common.base.utils.LogUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.component.LeakSafeHandler;
import com.zt.yavon.module.data.CustomHeightBean;
import com.zt.yavon.module.data.DeskBean;
import com.zt.yavon.module.data.DevDetailBean;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.device.desk.contract.DeskDetailContract;
import com.zt.yavon.module.device.desk.presenter.DeskDetailPresenter;
import com.zt.yavon.module.device.lock.contract.LockDetailContract;
import com.zt.yavon.module.device.lock.presenter.LockDetailPresenter;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.widget.MyTextView;
import com.zt.yavon.widget.VerticalSeekBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by lifujun on 2018/7/10.
 */

public class DeskDetailActivity extends BaseActivity<DeskDetailPresenter> implements DeskDetailContract.View{
    private int WHAT_SET = 0x21;
    @BindView(R.id.progress_desk)
    VerticalSeekBar seekBar;
    @BindView(R.id.tv_progress_desk)
    TextView tvProgress;
    @BindView(R.id.tv_zdy1_desk)
    MyTextView tvZDY1;
    @BindView(R.id.tv_zdy2_desk)
    MyTextView tvZDY2;
    @BindView(R.id.tv_zdy3_desk)
    MyTextView tvZDY3;
    @BindView(R.id.tv_zdy4_desk)
    MyTextView tvZDY4;
    @BindView(R.id.tv_zdy5_desk)
    MyTextView tvZDY5;
    private Dialog dialog;
    private List<DeskBean> heightList;
    private DevDetailBean bean;
    private int REQ_SETTING = 0x100;
//    private LeakSafeHandler<DeskDetailActivity> mHandler = new LeakSafeHandler<DeskDetailActivity>(this){
//        @Override
//        public void onMessageReceived(DeskDetailActivity activity, Message msg) {
//            if(activity.timer == null) return;
//            if(msg.what == activity.WHAT_SET){
//                //延时提交
//                activity.timer.cancel();
//                activity.timer.start();
//            }
//        }
//    };
//    private CountDownTimer timer = new CountDownTimer(700,50) {
//        int lastProgress = -1;
//        @Override
//        public void onTick(long millisUntilFinished) {
//        }
//
//        @Override
//        public void onFinish() {
//            //设置桌子高度
//            int curProgress = seekBar.getProgress();
//            if(seekBar.getProgress() != lastProgress){
//                lastProgress = curProgress;
//                LogUtil.d("=====================send data");
//            }
//        }
//    };
//    private MyTimer2 timer2 = new MyTimer2(100,50);
    private TabBean.MachineBean machineBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_desk_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        machineBean = (TabBean.MachineBean) getIntent().getSerializableExtra("machineBean");
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.title_desk));
        setRightMenuImage(R.mipmap.more_right);
        tvProgress.setText(seekBar.getProgress()+"");
        seekBar.setOnSeekBarChangeListener(new VerticalSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(VerticalSeekBar verticalSeekBar, int p, boolean fromUser) {
                tvProgress.setText(p+"");
            }

            @Override
            public void onStartTrackingTouch(VerticalSeekBar verticalSeekBar) {
            }

            @Override
            public void onStopTrackingTouch(VerticalSeekBar verticalSeekBar) {
//                mHandler.sendEmptyMessage(WHAT_SET);
                mPresenter.setDeskHeight(machineBean.id+"",seekBar.getProgress()+"");
            }
        });
        heightList = new ArrayList<>();
        mPresenter.getDevDetail(machineBean.id+"");
    }
    public static void startAction(Context context, TabBean.MachineBean machineBean) {
        Intent intent = new Intent(context, DeskDetailActivity.class);
        intent.putExtra("machineBean", machineBean);
        context.startActivity(intent);
    }
    @OnClick({R.id.tv_coulometry_desk,R.id.iv_setting_height_desk,R.id.tv_zdy1_desk,R.id.tv_zdy2_desk,R.id.tv_zdy3_desk,R.id.tv_zdy4_desk,R.id.tv_zdy5_desk})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.tv_right_header:
                if(bean != null)
                DeskSettingActivity.startAction(this,bean,REQ_SETTING);
                break;
            case R.id.tv_coulometry_desk:
                ElectricityStatisticsActivity.startAction(this);
                break;
            case R.id.tv_zdy1_desk:
                if(heightList != null && heightList.size() > 0){
                    setSeekBarProgress(heightList.get(0).height);
                }
                break;
            case R.id.tv_zdy2_desk:
                if(heightList != null && heightList.size() > 0){
                    setSeekBarProgress(heightList.get(1).height);
                }
                break;
            case R.id.tv_zdy3_desk:
                if(heightList != null && heightList.size() > 0){
                    setSeekBarProgress(heightList.get(2).height);
                }
                break;
            case R.id.tv_zdy4_desk:
                if(heightList != null && heightList.size() > 0){
                    setSeekBarProgress(heightList.get(3).height);
                }
                break;
            case R.id.tv_zdy5_desk:
                if(heightList != null && heightList.size() > 0){
                    setSeekBarProgress(heightList.get(4).height);
                }
                break;
            case R.id.iv_setting_height_desk:
                DialogUtil.dismiss(dialog);
                dialog = DialogUtil.createCustomHeightDialog(this, heightList, new DialogUtil.OnComfirmListening() {
                    @Override
                    public void confirm() {
                        updateCustomButtonName();
                        mPresenter.setDeskCustomHeightTag(machineBean.id+"",heightList);
                    }
                });
                break;
        }
    }

    private void updateCustomButtonName() {
        if(heightList == null || heightList.isEmpty()){
            return;
        }
        String name0 = heightList.get(0).name;
        if(TextUtils.isEmpty(name0)){
            name0 = "自定义1";
        }
        tvZDY1.setText(name0);
        String name1 = heightList.get(1).name;
        if(TextUtils.isEmpty(name1)){
            name1 = "自定义2";
        }
        tvZDY2.setText(name1);
        String name2 = heightList.get(2).name;
        if(TextUtils.isEmpty(name2)){
            name2 = "自定义3";
        }
        tvZDY3.setText(name2);
        String name3 = heightList.get(3).name;
        if(TextUtils.isEmpty(name3)){
            name3 = "自定义4";
        }
        tvZDY4.setText(name3);
        String name4 = heightList.get(4).name;
        if(TextUtils.isEmpty(name4)){
            name4 = "自定义5";
        }
        tvZDY5.setText(name4);
    }

    @OnTouch({R.id.btn_up_desk,R.id.btn_down_desk})
    public boolean doTouch(MotionEvent event,View view) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
                switch (view.getId()){
                    case R.id.btn_up_desk:
//                        seekBar.setProgress(seekBar.getProgress()+1);
//                        timer2.setDelta(1);
                        mPresenter.startDeskMove(machineBean.id+"",true);
                        break;
                    case R.id.btn_down_desk:
//                        seekBar.setProgress(seekBar.getProgress()-1);
                        mPresenter.startDeskMove(machineBean.id+"",true);
//                        timer2.setDelta(-1);
                        break;
                }
//                timer2.start();
        }else if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL){
//            timer2.cancel();
//            mHandler.sendEmptyMessage(WHAT_SET);
            mPresenter.stopDeskMove(machineBean.id+"");
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void returnDevDetail(DevDetailBean bean) {
        if(bean != null){
            this.bean = bean;
            setSeekBarProgress(bean.getHeight());
            heightList.clear();
            heightList.addAll(bean.getAdjust_table_height());
        }
    }

    @Override
    public void returnHeiht(DeskBean bean) {
        setSeekBarProgress(bean.height);
    }

//    public class MyTimer2 extends CountDownTimer{
//        int delta = 1;
//        public MyTimer2(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//        }
//
//        @Override
//        public void onFinish() {
//            //设置桌子高度
//            seekBar.setProgress(seekBar.getProgress()+delta);
//            start();
//        }
//        public void setDelta(int delta){
//            this.delta = delta;
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_SETTING && resultCode == RESULT_OK){
            if(bean != null)
            bean.setSedentary_time(data.getStringExtra("time"));
            bean.setSedentary_reminder(data.getBooleanExtra("reminder",false));
        }
    }

    @Override
    protected void onDestroy() {
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }
    private void setSeekBarProgress(int progress){
        if(progress <0 || progress > 100){
            return ;
        }
        seekBar.setProgress(progress);
    }
}
