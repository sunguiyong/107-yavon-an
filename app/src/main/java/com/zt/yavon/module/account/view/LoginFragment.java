package com.zt.yavon.module.account.view;

import android.view.View;
import android.widget.TextView;

import com.common.base.utils.LogUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseFragment;
import com.zt.yavon.module.account.view.ResetPasswordActivity;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.module.main.frame.view.MainActivity;
import com.zt.yavon.utils.SPUtil;
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
//        hideBackButton();
//        setTitle(getString(R.string.tab_mine));
        LoginBean bean = SPUtil.getAccount(getContext());
        if(bean != null){
            etAccount.setEditTextText(bean.getMobile());
            etPwd.setEditTextText(bean.getPwd());
        }
        boolean isAutoLogin = SPUtil.getBoolean(getContext(),SPUtil.AUTO_LOGIN,false);
        tvAutoLogin.setSelected(isAutoLogin);
        if(isAutoLogin){
            ((LoginRegisterActivity)getActivity()).mPresenter.login(bean.getAccount(),bean.getPwd());
        }
    }


    @OnClick({R.id.tv_auto_login, R.id.tv_forget_psd, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_auto_login:
                tvAutoLogin.setSelected(!tvAutoLogin.isSelected());
                SPUtil.putBoolean(getContext(),SPUtil.AUTO_LOGIN,tvAutoLogin.isSelected());
                break;
            case R.id.tv_forget_psd:
                ResetPasswordActivity.start(getActivity(),ResetPasswordActivity.FLAG_LOGIN);
                break;
            case R.id.tv_login:
                ((LoginRegisterActivity)getActivity()).mPresenter.login(etAccount.getEditText().toString().trim(),
                        etPwd.getEditText().toString().trim());
                break;
        }
    }
}
