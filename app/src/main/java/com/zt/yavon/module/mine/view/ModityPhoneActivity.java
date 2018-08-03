package com.zt.yavon.module.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.mine.contract.ModifyPhoneContract;
import com.zt.yavon.module.mine.presenter.ModifyPhonePresenter;
import com.zt.yavon.widget.CustomEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/17.
 */

public class ModityPhoneActivity extends BaseActivity<ModifyPhonePresenter> implements ModifyPhoneContract.View{
    @BindView(R.id.et_phone_modify)
    EditText etPhone;
    @BindView(R.id.et_verify_modify_phone)
    CustomEditText etCode;
    @BindView(R.id.et_phone_modify_phone)
    CustomEditText etNewPhone;
    @BindView(R.id.et_phone2_modify_phone)
    CustomEditText etNewPhone2;
    @BindView(R.id.tv_get_phone)
    TextView tvGetNotify;
    @BindView(R.id.iv_divider_phone)
    ImageView ivPhoneDivider;
    private CountDownTimer timer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_phone;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
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
        timer = new CountDownTimer(60*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvGetNotify.setText("重新获取("+millisUntilFinished/1000+")");
            }

            @Override
            public void onFinish() {
                tvGetNotify.setEnabled(true);
                tvGetNotify.setText("获取验证码");
            }
        };
    }
    public static void startAction(Activity context,int reqCode){
        Intent intent = new Intent(context,ModityPhoneActivity.class);
        context.startActivityForResult(intent,reqCode);
    }
    @OnClick({R.id.tv_get_phone,R.id.btn_confirm_modify_phone})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.tv_get_phone:
                String mobile = etPhone.getText().toString().trim();
                if(TextUtils.isEmpty(mobile)){
                    ToastUtil.showShort(this,"手机号不能为空");
                    return;
                }
                tvGetNotify.setEnabled(false);
                timer.start();
                mPresenter.sendCode(mobile);
                break;
            case R.id.btn_confirm_modify_phone:
                mPresenter.modifyPhone(etPhone.getText().toString().trim(),
                        etCode.getText().toString().trim(),
                        etNewPhone.getText().toString().trim(),
                        etNewPhone2.getText().toString().trim());
                break;
        }
    }


    @Override
    public void sendCodeResult(String msg) {
        if(TextUtils.isEmpty(msg)){
            ToastUtil.showShort(this,"发送成功");
        }else{
            if(timer != null){
                timer.cancel();
                timer.onFinish();
            }
        }
    }

    @Override
    public void modifySuccess(String email) {
        ToastUtil.showShort(this,"修改成功");
        Intent intent = new Intent();
        intent.putExtra("phone",email);
        setResult(RESULT_OK,intent);
        finish();
    }
    @Override
    public void onDestroy() {
        if(timer != null ){
            timer.cancel();
        }
        super.onDestroy();
    }
}
