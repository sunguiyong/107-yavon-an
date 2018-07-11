package com.zt.yavon.module.device.desk.view;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.common.base.utils.LogUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.component.LeakSafeHandler;
import com.zt.yavon.widget.VerticalSeekBar;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by lifujun on 2018/7/10.
 */

public class DeskDetailActivity extends BaseActivity{
    private int WHAT_SET = 0x21;
    @BindView(R.id.progress_desk)
    VerticalSeekBar seekBar;
    @BindView(R.id.tv_progress_desk)
    TextView tvProgress;
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
    private CountDownTimer timer = new CountDownTimer(700,50) {
        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            //设置桌子高度
            LogUtil.d("=====================send data");
        }
    };
    private MyTimer2 timer2 = new MyTimer2(100,50);
    @Override
    public int getLayoutId() {
        return R.layout.activity_desk_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));
        setTitle(getString(R.string.title_desk));
        setRightMenuImage(R.mipmap.more_right);
//        seekBar.setThumb(ContextCompat.getDrawable(this,R.drawable.round_point_record_w));
//        progress =seekBar.getProgress();
        tvProgress.setText(seekBar.getProgress()+"");
        seekBar.setOnSeekBarChangeListener(new VerticalSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(VerticalSeekBar verticalSeekBar, int p, boolean fromUser) {
//                progress = p;
                tvProgress.setText(p+"");
            }

            @Override
            public void onStartTrackingTouch(VerticalSeekBar verticalSeekBar) {
//                progress = verticalSeekBar.getProgress();
//                LogUtil.d("===========onStartTrackingTouch,progress:"+verticalSeekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(VerticalSeekBar verticalSeekBar) {
//                progress = verticalSeekBar.getProgress();
//                LogUtil.d("===========onStopTrackingTouch,progress:"+verticalSeekBar.getProgress());
            }
        });
    }
    public static void startAction(Context context){
        Intent intent = new Intent(context,DeskDetailActivity.class);
        context.startActivity(intent);
    }
    @OnClick({R.id.tv_coulometry_desk})
    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.tv_coulometry_desk:
                break;
        }
    }
    @OnTouch({R.id.btn_up_desk,R.id.btn_down_desk})
    public boolean doTouch(MotionEvent event,View view) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
                switch (view.getId()){
                    case R.id.btn_up_desk:
                        seekBar.setProgress(seekBar.getProgress()+1);
                        timer2.setDelta(1);
                        break;
                    case R.id.btn_down_desk:
                        seekBar.setProgress(seekBar.getProgress()-1);
                        timer2.setDelta(-1);
                        break;
                }
                timer2.start();
        }else if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL){
            timer2.cancel();
            mHandler.sendEmptyMessage(WHAT_SET);

        }

        return super.onTouchEvent(event);
    }
    public class MyTimer2 extends CountDownTimer{
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
            seekBar.setProgress(seekBar.getProgress()+delta);
            start();
        }
        public void setDelta(int delta){
            this.delta = delta;
        }
    }
}
