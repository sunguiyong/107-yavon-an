package com.zt.yavon.module.account.view;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.account.contract.ResetPwdContract;
import com.zt.yavon.module.account.presenter.ResetPwdPresenter;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.module.main.frame.view.MainActivity;
import com.zt.yavon.utils.SPUtil;
import com.zt.yavon.widget.CustomEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity<ResetPwdPresenter> implements ResetPwdContract.View {
    public static String FLAG_LOGIN = "isFromLogin";
    @BindView(R.id.et_phone_reset)
    EditText etPhone;
    @BindView(R.id.et_verify_reset)
    CustomEditText etVerify;
    @BindView(R.id.et_pwd_reset)
    CustomEditText etPwd;
    @BindView(R.id.et_pwd2_reset)
    CustomEditText etPwdConfirm;
    @BindView(R.id.iv_divider_phone)
    ImageView ivDivider;
    @BindView(R.id.tv_get_reset)
    TextView tvGetNotify;
    private CountDownTimer timer;
    private boolean isFromLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        if(FLAG_LOGIN.equals(getIntent().getStringExtra("flag"))){
            isFromLogin = true;
        }
    }

    @Override
    public void initView() {
        setTitle("重置密码");
        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ivDivider.setSelected(hasFocus);
            }
        });
        timer = new CountDownTimer(60*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvGetNotify.setText("重新获取("+millisUntilFinished/1000+")");
            }

            @Override
            public void onFinish() {
                tvGetNotify.setEnabled(true);
                tvGetNotify.setText("获取验证码");
            }
        };
    }

    public static void start(Activity activity,String value) {
        Intent intent = new Intent(activity, ResetPasswordActivity.class);
        intent.putExtra("flag",value);
        activity.startActivity(intent);
    }

    @OnClick({R.id.tv_get_reset,R.id.tv_confirm_reset})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.tv_get_reset:
                String mobile = etPhone.getText().toString().trim();
                if(TextUtils.isEmpty(mobile)){
                    ToastUtil.showShort(this,"手机号不能为空");
                    return;
                }
//                if(!RegexUtils.isMobile(mobile)){
//                    ToastUtil.showShort(getContext(),"手机号不正确");
//                    return;
//                }
                tvGetNotify.setEnabled(false);
                timer.start();
                mPresenter.sendCode(mobile);
                break;
            case R.id.tv_confirm_reset:
                break;
        }
    }

    @Override
    public void sendCodeResult(String msg) {
        if(TextUtils.isEmpty(msg)){
            ToastUtil.showShort(this,"发送成功");
        }else{
            if(timer != null){
                timer.cancel();
                timer.onFinish();
            }
        }
    }

    @Override
    public void loginRegisterSuccess(LoginBean bean) {
        SPUtil.saveAccount(this,bean);
        if(isFromLogin){
            MainActivity.startAction(this);
        }
        finish();
    }
}
