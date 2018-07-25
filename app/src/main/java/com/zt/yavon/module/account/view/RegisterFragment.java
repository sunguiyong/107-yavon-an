package com.zt.yavon.module.account.view;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseFragment;
import com.zt.yavon.module.account.view.LoginRegisterActivity;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.utils.RegexUtils;
import com.zt.yavon.widget.CustomEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hp on 2018/6/11.
 */

public class RegisterFragment extends BaseFragment {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv_divider_phone)
    ImageView ivDivider;
    @BindView(R.id.tv_get_notify)
    TextView tvGetNotify;
    @BindView(R.id.et_notify)
    CustomEditText etNotify;
    @BindView(R.id.et_psd)
    CustomEditText etPsd;
    @BindView(R.id.et_psd_again)
    CustomEditText etPsdAgain;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    private CountDownTimer timer;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_register;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
//        hideBackButton();
//        setTitle(getString(R.string.tab_mine));
//        tvAgree.setSelected(true);
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





    @OnClick({R.id.tv_get_notify, R.id.tv_agree, R.id.tv_register, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_notify:
                String mobile = etPhone.getText().toString().trim();
                if(TextUtils.isEmpty(mobile)){
                    ToastUtil.showShort(getContext(),"手机号不能为空");
                    return;
                }
//                if(!RegexUtils.isMobile(mobile)){
//                    ToastUtil.showShort(getContext(),"手机号不正确");
//                    return;
//                }
                tvGetNotify.setEnabled(false);
                timer.start();
                ((LoginRegisterActivity)getActivity()).mPresenter.sendCode(mobile);
                break;
            case R.id.tv_agree:
                if (tvAgree.isSelected()){
                    tvAgree.setSelected(false);
                }else {
                    tvAgree.setSelected(true);
                }
                break;
            case R.id.tv_register:
                if (tvAgree.isSelected()){
                    ((LoginRegisterActivity)getActivity()).mPresenter.register(etPhone.getText().toString().trim(),
                            etNotify.getEditText().toString().trim(),
                            etPsd.getEditText().toString().trim(),
                            etPsdAgain.getEditText().toString().trim());
                }else{
                    ToastUtil.showShort(getContext(),"请先同意注册协议");
                }
                break;
            case R.id.tv_login:
                ((LoginRegisterActivity)getActivity()).switchToLogin();
                break;
        }
    }

    @Override
    public void onDestroy() {
        if(timer != null ){
            timer.cancel();
        }
        super.onDestroy();
    }

    public void sendCodeFail() {
        if(timer != null){
            timer.cancel();
            timer.onFinish();
        }
    }
}
