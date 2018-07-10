package com.zt.yavon.module.login;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity {


    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_get_notify)
    TextView etGetNotify;
    @BindView(R.id.et_notify)
    EditText etNotify;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.et_psd_again)
    EditText etPsdAgain;
    @BindView(R.id.tv_center)
    TextView tvCenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle("重置密码");
    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ResetPasswordActivity.class);
        activity.startActivity(intent);
    }

    @OnClick({R.id.et_get_notify, R.id.tv_center})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_get_notify:
                break;
            case R.id.tv_center:
                break;
        }
    }
}
