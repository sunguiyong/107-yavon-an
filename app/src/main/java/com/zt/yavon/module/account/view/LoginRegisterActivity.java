package com.zt.yavon.module.account.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.account.contract.LoginRegisterContract;
import com.zt.yavon.module.account.presenter.LoginRegisterPresenter;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.module.main.frame.view.MainActivity;
import com.zt.yavon.utils.Constants;
import com.zt.yavon.utils.SPUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class LoginRegisterActivity extends BaseActivity<LoginRegisterPresenter> implements LoginRegisterContract.View {


    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.fr_login_register)
    FrameLayout frLoginRegister;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private Fragment nowFragment;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private String mode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_register;
    }

    public static void start(Context activity, String mode) {
        Intent intent = new Intent(activity, LoginRegisterActivity.class);
        intent.putExtra("mode", mode);
        activity.startActivity(intent);
    }

    @Override
    public void initPresenter() {
        mRxManager.on(Constants.EVENT_LOGIN_SUCCESS, new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                finish();
            }
        });
        mode = getIntent().getStringExtra("mode");
        mPresenter.setVM(this);
    }

    @Override
    public void initView() {
        initFragment();
    }

    private void initFragment() {
        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        switch (mode) {
            case "register":
                nowFragment = registerFragment;
                tvRegister.setSelected(true);
                tvLogin.setSelected(false);
                tvRegister.setBackgroundResource(R.color.alpha_05_white);
                tvLogin.setBackgroundResource(R.color.black);
                setTitle("创建新账户");
                break;
            case "login":
                nowFragment = loginFragment;
                tvRegister.setSelected(false);
                tvLogin.setSelected(true);
                tvLogin.setBackgroundResource(R.color.alpha_05_white);
                tvRegister.setBackgroundResource(R.color.black);
                setTitle("登录");
                break;
        }
        fragmentManager = getSupportFragmentManager();
        //默认显示商品详情tab
        fragmentManager.beginTransaction().replace(R.id.fr_login_register, nowFragment).commitAllowingStateLoss();
    }

    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if (nowFragment != toFragment) {
            fragmentTransaction = fragmentManager.beginTransaction();
            if (!toFragment.isAdded()) {    // 先判断是否被add过
                fragmentTransaction.hide(fromFragment).add(R.id.fr_login_register, toFragment).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到activity中
            } else {
                fragmentTransaction.hide(fromFragment).show(toFragment).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
    public void switchToLogin(){
        switchFragment(nowFragment, loginFragment);
        nowFragment = loginFragment;
        tvRegister.setSelected(false);
        tvLogin.setSelected(true);
        tvLogin.setBackgroundResource(R.color.alpha_05_white);
        tvRegister.setBackgroundResource(R.color.black);
        setTitle("登录");
    }
    @OnClick({R.id.tv_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                switchToLogin();
                break;
            case R.id.tv_register:
                switchFragment(nowFragment, registerFragment);
                nowFragment = registerFragment;
                tvRegister.setSelected(true);
                tvLogin.setSelected(false);
                tvLogin.setBackgroundResource(R.color.black);
                tvRegister.setBackgroundResource(R.color.alpha_05_white);
                setTitle("创建新账户");
                break;
        }
    }

    @Override
    public void sendCodeResult(String msg) {
        if(TextUtils.isEmpty(msg)){
            ToastUtil.showShort(this,"发送成功");
        }else{
            registerFragment.sendCodeFail();
        }
    }

    @Override
    public void loginRegisterSuccess(LoginBean bean) {
        SPUtil.saveAccount(this,bean);
        MainActivity.startAction(this);
    }
}
