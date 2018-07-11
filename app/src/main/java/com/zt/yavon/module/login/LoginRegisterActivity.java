package com.zt.yavon.module.login;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.login.fragment.LoginFragment;
import com.zt.yavon.module.login.fragment.RegisterFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginRegisterActivity extends BaseActivity {


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

    public static void start(Activity activity, String mode) {
        Intent intent = new Intent(activity, LoginRegisterActivity.class);
        intent.putExtra("mode", mode);
        activity.startActivity(intent);
    }

    @Override
    public void initPresenter() {
        mode = getIntent().getStringExtra("mode");
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
                setTitle("创建新账户");
                break;
            case "login":
                nowFragment = loginFragment;
                tvRegister.setSelected(false);
                tvLogin.setSelected(true);
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

    @OnClick({R.id.tv_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                switchFragment(nowFragment, loginFragment);
                nowFragment = loginFragment;
                tvRegister.setSelected(false);
                tvLogin.setSelected(true);
                setTitle("登录");
                break;
            case R.id.tv_register:
                switchFragment(nowFragment, registerFragment);
                nowFragment = registerFragment;
                tvRegister.setSelected(true);
                tvLogin.setSelected(false);
                setTitle("创建新账户");
                break;
        }
    }
}
