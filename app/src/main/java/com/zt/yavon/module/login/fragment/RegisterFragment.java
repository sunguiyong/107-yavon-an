package com.zt.yavon.module.login.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseFragment;
import com.zt.yavon.module.main.view.ScanCodeActivity;

import butterknife.BindView;
import butterknife.OnClick;

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
        tvAgree.setSelected(true);
    }





    @OnClick({R.id.tv_get_notify, R.id.tv_agree, R.id.tv_register, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_notify:
                break;
            case R.id.tv_agree:
                if (tvAgree.isSelected()){
                    tvAgree.setSelected(false);
                }else {
                    tvAgree.setSelected(true);
                }
                break;
            case R.id.tv_register:
                ScanCodeActivity.start(getActivity());
                break;
            case R.id.tv_login:
                break;
        }
    }
}
