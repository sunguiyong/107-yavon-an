package com.zt.igreen.module.device.health.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.common.base.utils.ToastUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.data.LoginBean;
import com.zt.igreen.module.data.MedicalBean;
import com.zt.igreen.module.data.TabBean;
import com.zt.igreen.module.device.health.contract.HeanthTestContract;
import com.zt.igreen.module.device.health.presenter.HeathTestPresenter;
import com.zt.igreen.module.main.widget.GlideCircleTransfrom;
import com.zt.igreen.utils.SPUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HealthNormalTestActivity extends BaseActivity<HeathTestPresenter> implements HeanthTestContract.View {
    int x = R.layout.activity_normalcheck;
    @BindView(R.id.yuan1)
    ImageView yuan1;
    @BindView(R.id.yuan2)
    ImageView yuan2;
    @BindView(R.id.yuan3)
    ImageView yuan3;
    @BindView(R.id.main_fl)
    FrameLayout mainFl;
    @BindView(R.id.iv_avatar_info)
    ImageView avatar;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.checking_tv)
    TextView checkingTv;
    private String[] str = new String[12];
    private static String checkType = "";
    private Disposable onSubscribe;
    private TabBean.MachineBean machineBean;
    private String mac;
    private TimeCount timecount_one;

    public static void startAction(Context context, String mac, TabBean.MachineBean machineBean, String checkType) {
        Intent intent = new Intent(context, HealthNormalTestActivity.class);
        intent.putExtra("mac", mac);
        intent.putExtra("machineBean", machineBean);
        intent.putExtra("type", checkType);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_normalcheck;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        machineBean = (TabBean.MachineBean) getIntent().getSerializableExtra("machineBean");
        mac = getIntent().getStringExtra("mac");
        checkType = getIntent().getStringExtra("type");
    }

    @Override
    public void initView() {
        HealthActivity.deviceManager.startTest();//体检开始
        setColor(Color.parseColor("#ffffff"));
        PollStatus();
        if ("普通体检".equals(checkType)) {
            timecount_one = new TimeCount(60000, 1000);
        } else {
            timecount_one = new TimeCount(180000, 1000);

        }
        timecount_one.start();
        ImmersionBar.with(this)
                .statusBarColor(R.color.touming).statusBarDarkFont(false).flymeOSStatusBarFontColor(R.color.touming).init();
        sethead(R.color.touming);
        setLeftButtonImage(R.mipmap.back_w);
        setTitle(checkType);
        getPersonInfo();
        animation(yuan1);
        animation1(yuan2, 300);
        animation1(yuan3, 600);
    }

    private void startCheck() {

    }

    public static String getType(String type) {
        checkType = type;
        return checkType;
    }

    @Override
    public void returnTest(MedicalBean bean) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void animation(View view) {
        animation1(view, 0);
    }

    private void animation1(View view, int startOffset) {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1500);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setStartOffset(startOffset);
        //让旋转动画一直转，不停顿的重点
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(-1);
        view.startAnimation(rotateAnimation);
    }

    private void getPersonInfo() {
        //头像
        LoginBean bean = SPUtil.getAccount(this);
        if (bean != null) {
            nameTv.setText(bean.getNick_name());
            Glide.with(this)
                    .load(bean.getAvatar())
//                    .placeholder(R.mipmap.avatar_default)
                    .error(R.mipmap.avatar_default)
                    .transform(new CenterCrop(this), new GlideCircleTransfrom(this))
                    .dontAnimate()
                    .into(avatar);
        }
    }

    private void PollStatus() {
        onSubscribe = Observable.interval(5, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        int status = HealthActivity.deviceManager.getFingerSence();
                        Log.e("aaaa", status + "");
                        if (status == 0) {
                            Toast.makeText(HealthNormalTestActivity.this, "未启动", Toast.LENGTH_LONG);
                            HealthActivity.deviceManager.stopTest();
                            onSubscribe.dispose();

                        } else if (status == 2) {
                            ToastUtil.show(HealthNormalTestActivity.this, "未插入", Toast.LENGTH_LONG);

                            HealthActivity.deviceManager.stopTest();
                            onSubscribe.dispose();

                        } else if (status == 1) {
                            double monitorRate = HealthActivity.deviceManager.getMonitorRate();
                            str[0] = (new Double(monitorRate)).intValue() + "";
                            double monitorOxygen = HealthActivity.deviceManager.getMonitorOxygen();
                            str[1] = (new Double(monitorOxygen)).intValue() + "";
                            double monitorBreath = HealthActivity.deviceManager.getMonitorBreath();
                            str[2] = (new Double(monitorBreath)).intValue() + "";
                            double monitorHigh = HealthActivity.deviceManager.getMonitorHigh();
                            str[3] = (new Double(monitorHigh)).intValue() + "";
                            double monitorLow = HealthActivity.deviceManager.getMonitorLow();
                            str[4] = (new Double(monitorLow)).intValue() + "";
                            double monitorHRV = HealthActivity.deviceManager.getMonitorHRV();
                            str[5] = (new Double(monitorHRV)).intValue() + "";
                            double monitorBalance = HealthActivity.deviceManager.getMonitorBalance();
                            str[6] = (new Double(monitorBalance)).intValue() + "";
                            double monitorMentalscore = HealthActivity.deviceManager.getMonitorMentalscore();
                            str[7] = (new Double(monitorMentalscore)).intValue() + "";
                            double monitorPhysical = HealthActivity.deviceManager.getMonitorPhysical();
                            str[8] = (new Double(monitorPhysical)).intValue() + "";
                            double summeryVesselStage = HealthActivity.deviceManager.getSummeryVesselStage();
                            str[9] = (new Double(summeryVesselStage)).intValue() + "";
                            double summeryVesselAge = HealthActivity.deviceManager.getSummeryVesselAge();
                            str[10] = (new Double(summeryVesselStage)).intValue() + "";
                            double monitorPI = HealthActivity.deviceManager.getMonitorPI();
                            str[11] = (new Double(monitorPI)).intValue() + "";
                            Toast.makeText(getApplicationContext(), "状态1", Toast.LENGTH_SHORT).show();
//                            tvXinlv.setText((new Double(monitorRate)).intValue() + "");
//                            tvXueyang.setText(new Double(monitorOxygen).intValue() + "");
//                            tvHuxi.setText(new Double(monitorBreath).intValue() + "");
//                            tvXueya.setText(new Double(monitorHigh).intValue() + "/" + new Double(monitorLow).intValue());
//                            tvMoshao.setText(new Double(monitorPI).intValue() + "");

                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (onSubscribe != null)
            onSubscribe.dispose();
        if (onSubscribe != null)
            onSubscribe.dispose();
        HealthActivity.deviceManager.stopTest();
        if (timecount_one != null) {
            timecount_one.cancel();
        }
        HealthActivity.deviceManager.stopTest();
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //每次执行方法
        @Override
        public void onTick(long millisUntilFinished) {
            Log.d("执行", millisUntilFinished + "");
        }

        @Override
        public void onFinish() {
            Log.d("onFinish time", "invoked");
            HealthActivity.deviceManager.stopTest();
            onSubscribe.dispose();
//            mPresenter.getHeanth(machineBean.id + "", str[0], str[5], str[1], str[3], str[4], str[6], str[11], str[5], str[2], str[7], str[8], str[9], str[10]);
            HealMoreDataActivity.startAction(getApplicationContext(), machineBean);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        HealthActivity.deviceManager.stopTest();
        finish();
    }
}
