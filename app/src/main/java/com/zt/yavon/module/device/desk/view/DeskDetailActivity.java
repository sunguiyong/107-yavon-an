package com.zt.yavon.module.device.desk.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.common.base.utils.LogUtil;
import com.espressif.iot.esptouch.EsptouchTask;
import com.espressif.iot.esptouch.IEsptouchResult;
import com.espressif.iot.esptouch.IEsptouchTask;
import com.espressif.iot.esptouch.task.__IEsptouchTask;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.component.LeakSafeHandler;
import com.zt.yavon.module.data.DeskBean;
import com.zt.yavon.module.data.DevDetailBean;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.device.desk.contract.DeskDetailContract;
import com.zt.yavon.module.device.desk.presenter.DeskDetailPresenter;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.widget.MyTextView;
import com.zt.yavon.widget.VerticalSeekBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by lifujun on 2018/7/10.
 */

public class DeskDetailActivity extends BaseActivity<DeskDetailPresenter> implements DeskDetailContract.View{
    public static final int HEIGHT_BOTTOM = 86;
    public static final int HEIGHT_TOP = 126;
    private int WHAT_SET = 0x21;
    @BindView(R.id.progress_desk)
    VerticalSeekBar seekBar;
    @BindView(R.id.tv_progress_desk)
    TextView tvProgress;
    @BindViews({R.id.tv_zdy1_desk,R.id.tv_zdy2_desk,R.id.tv_zdy3_desk,R.id.tv_zdy4_desk,R.id.tv_zdy5_desk})
    List<MyTextView> zdyList;
    private Dialog dialog;
    private List<DeskBean> heightList;
    private DevDetailBean bean;
    private int REQ_SETTING = 0x100;
    private LeakSafeHandler<DeskDetailActivity> mHandler = new LeakSafeHandler<DeskDetailActivity>(this){
        @Override
        public void onMessageReceived(DeskDetailActivity activity, Message msg) {
            if(activity.timer == null) return;
            if(msg.what == activity.WHAT_SET){
                //延时提交
                activity.timer.cancel();
                activity.timer.start();
            }
        }
    };
    private CountDownTimer timer = new CountDownTimer(1000,50) {
        int lastProgress = -1;
        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            //设置桌子高度
            int curProgress = seekBar.getProgress();
            if(seekBar.getProgress() != lastProgress){
                lastProgress = curProgress;
                mPresenter.setDeskHeight(machineBean.id+"",HEIGHT_BOTTOM+seekBar.getProgress()+"");
//                LogUtil.d("=====================send data");
            }
        }
    };
    private MyTimer2 timer2 = new MyTimer2(1000,50);
    private TabBean.MachineBean machineBean;
    private long curMills;
    private int delta = 1;

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
                LogUtil.d("==============onProgressChanged:"+HEIGHT_BOTTOM+p);
                tvProgress.setText(HEIGHT_BOTTOM+p+"");
                updateCustomButtonName();
            }

            @Override
            public void onStartTrackingTouch(VerticalSeekBar verticalSeekBar) {
            }

            @Override
            public void onStopTrackingTouch(VerticalSeekBar verticalSeekBar) {
//                mHandler.sendEmptyMessage(WHAT_SET);
                mPresenter.setDeskHeight(machineBean.id+"",HEIGHT_BOTTOM+seekBar.getProgress()+"");
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
                ElectricityStatisticsActivity.startAction(this,machineBean.id+"");
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
        int curProgress = seekBar.getProgress()+HEIGHT_BOTTOM;
        int index = 0;
        for(DeskBean bean:heightList){
            MyTextView mTextView = zdyList.get(index);
            mTextView.setText(bean.name);
            mTextView.setSelected(bean.height == curProgress);
            index++;
        }
    }

    @OnTouch({R.id.btn_up_desk,R.id.btn_down_desk})
    public boolean doTouch(MotionEvent event,View view) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            curMills = SystemClock.elapsedRealtime();
                switch (view.getId()){
                    case R.id.btn_up_desk:
                        delta = 1;
                        break;
                    case R.id.btn_down_desk:
                        delta = -1;
                        break;
                }
                timer2.setDelta(delta);
                timer2.start();
        }else if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL){
            timer2.cancel();
            if(SystemClock.elapsedRealtime() - curMills <1000){
                //click
                int curProgress = seekBar.getProgress()+delta;
                if(curProgress >= 0 && curProgress <= 40){
                    seekBar.setProgress(curProgress);
                    mHandler.sendEmptyMessage(WHAT_SET);
                }
            }else{
                //stop move
                mPresenter.stopDeskMove(machineBean.id+"");
            }
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
            updateCustomButtonName();
        }
    }

    @Override
    public void returnHeiht(DeskBean bean) {
        setSeekBarProgress(bean.height);
    }

    public class MyTimer2 extends CountDownTimer{
        boolean isFistTime = true;
        int delta = 1;
        public MyTimer2(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            //设置桌子高度
            int curProgress = seekBar.getProgress()+delta;
            seekBar.setProgress(curProgress);
            if(isFistTime){
                isFistTime = false;
                mPresenter.startDeskMove(machineBean.id+"",delta > 0?true:false);
            }
            if(curProgress <=0 || curProgress >=40){
                mPresenter.stopDeskMove(machineBean.id+"");
            }else{
                start();
            }
        }
        public void setDelta(int delta){
            this.delta = delta;
            isFistTime = true;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_SETTING && resultCode == RESULT_OK){
            if(bean != null && data != null){
                bean.setSedentary_time(data.getStringExtra("time"));
                bean.setSedentary_reminder(data.getBooleanExtra("reminder",false));
            }
        }
    }

    @Override
    protected void onDestroy() {
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }
    private void setSeekBarProgress(int progress){
        int realProgress = -1;
        if(progress <HEIGHT_BOTTOM){
            realProgress = 0;
        }else if(progress > HEIGHT_TOP){
            realProgress = HEIGHT_TOP-HEIGHT_BOTTOM;
        }else{
            realProgress = progress-HEIGHT_BOTTOM;
        }
        if(realProgress == seekBar.getProgress()){
            tvProgress.setText(HEIGHT_BOTTOM+seekBar.getProgress()+"");
            updateCustomButtonName();
        }else{
            seekBar.setProgress(realProgress);
        }
    }


}
