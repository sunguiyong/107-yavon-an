package com.zt.yavon.module.account.login.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;

import butterknife.BindView;

public class ResetPasswordActivity extends BaseActivity {
    @BindView(R.id.et_phone_reset)
    EditText etPhone;
    @BindView(R.id.iv_divider_phone)
    ImageView ivDivider;


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
        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ivDivider.setSelected(hasFocus);
            }
        });
    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ResetPasswordActivity.class);
        activity.startActivity(intent);
    }

}
