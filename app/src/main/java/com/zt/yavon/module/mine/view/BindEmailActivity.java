package com.zt.yavon.module.mine.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/17.
 */

public class BindEmailActivity extends BaseActivity{
    @BindView(R.id.et_email_bind)
    EditText etEmail;
    @BindView(R.id.iv_divider)
    ImageView ivDivider;
    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_email;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle(getString(R.string.bind_email));
        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ivDivider.setSelected(hasFocus);
            }
        });
    }
    public static void startAction(Activity context,int reqCode){
        Intent intent = new Intent(context,BindEmailActivity.class);
        context.startActivityForResult(intent,reqCode);
    }
    @OnClick({R.id.btn_confirm_bind_email})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.btn_confirm_bind_email:
                String email = etEmail.getText().toString();
                if(!TextUtils.isEmpty(email)){
                    Intent intent = new Intent();
                    intent.putExtra("email",email);
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    ToastUtil.showShort(this,"邮箱不能为空");
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
