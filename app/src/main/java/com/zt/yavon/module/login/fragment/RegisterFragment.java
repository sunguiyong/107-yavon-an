package com.zt.yavon.module.login.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hp on 2018/6/11.
 */

public class RegisterFragment extends BaseFragment {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_get_notify)
    TextView tvGetNotify;
    @BindView(R.id.et_notify)
    EditText etNotify;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.et_psd_again)
    EditText etPsdAgain;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    Unbinder unbinder;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_register;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        hideBackButton();
        setTitle(getString(R.string.tab_mine));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_get_notify, R.id.tv_agree, R.id.tv_register, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_notify:
                break;
            case R.id.tv_agree:
                break;
            case R.id.tv_register:
                break;
            case R.id.tv_login:
                break;
        }
    }
}