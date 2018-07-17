package com.zt.yavon.module.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/17.
 */

public class ModityPhoneActivity extends BaseActivity{
    @BindView(R.id.et_phone_modify)
    EditText etPhone;
    @BindView(R.id.iv_divider_phone)
    ImageView ivPhoneDivider;
    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_phone;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle(getString(R.string.email_phone));
        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ivPhoneDivider.setSelected(hasFocus);
            }
        });
    }
    public static void startAction(Activity context,int reqCode){
        Intent intent = new Intent(context,ModityPhoneActivity.class);
        context.startActivityForResult(intent,reqCode);
    }
    @OnClick({R.id.btn_confirm_modify_phone})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.btn_confirm_modify_phone:

                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
