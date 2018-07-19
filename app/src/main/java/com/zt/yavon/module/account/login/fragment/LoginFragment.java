package com.zt.yavon.module.account.login.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseFragment;
import com.zt.yavon.module.account.login.view.ResetPasswordActivity;
import com.zt.yavon.module.main.frame.view.MainActivity;
import com.zt.yavon.widget.CustomEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hp on 2018/6/11.
 */

public class LoginFragment extends BaseFragment {
    @BindView(R.id.et_account)
    CustomEditText etAccount;
    @BindView(R.id.et_pwd)
    CustomEditText etPwd;
    @BindView(R.id.tv_auto_login)
    TextView tvAutoLogin;
    @BindView(R.id.tv_forget_psd)
    TextView tvForgetPsd;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_login;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        hideBackButton();
        setTitle(getString(R.string.tab_mine));
    }


    @OnClick({R.id.tv_auto_login, R.id.tv_forget_psd, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_auto_login:
                tvAutoLogin.setSelected(!tvAutoLogin.isSelected());
                break;
            case R.id.tv_forget_psd:
                ResetPasswordActivity.start(getActivity());
                break;
            case R.id.tv_login:
                // DeviceAddActivity.start(getActivity());
                MainActivity.startAction(getActivity());
                break;
        }
    }
}
