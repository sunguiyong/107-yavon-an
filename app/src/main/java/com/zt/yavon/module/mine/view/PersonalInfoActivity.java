package com.zt.yavon.module.mine.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.account.login.view.ResetPasswordActivity;
import com.zt.yavon.utils.DialogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/17.
 */

public class PersonalInfoActivity extends BaseActivity{
    @BindView(R.id.tv_nickname_info)
    TextView tvNickName;
    @BindView(R.id.tv_email_info)
    TextView tvEmail;
    @BindView(R.id.tv_phone_info)
    TextView tvPhone;
    private Dialog dialog;
    private final int REQ_EMAIL = 0x10;
    private final int REQ_PHONE = 0x20;
    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle(getString(R.string.personal_info));
    }
    public static void startAction(Context context){
        Intent intent = new Intent(context,PersonalInfoActivity.class);
        context.startActivity(intent);
    }
    @OnClick({R.id.iv_avatar_info,R.id.iv2_avatar_info,R.id.tv_nickname_info,R.id.tv_email_info,R.id.key_email_info,R.id.tv_phone_info,R.id.tv_pwd_info,R.id.btn_exit_info})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.iv_avatar_info://修改头像
            case R.id.iv2_avatar_info:
                break;
            case R.id.key_email_info://绑定邮箱
            case R.id.tv_email_info:
                if(TextUtils.isEmpty(tvEmail.getText().toString())){
                    BindEmailActivity.startAction(this,REQ_EMAIL);
                }else{
                    ModityEmailActivity.startAction(this,REQ_EMAIL);
                }
                break;
            case R.id.tv_phone_info://绑定手机号
                ModityPhoneActivity.startAction(this,REQ_PHONE);
                break;
            case R.id.tv_pwd_info://修改密码
                ResetPasswordActivity.start(this);
                break;
            case R.id.tv_nickname_info://修改昵称
                DialogUtil.dismiss(dialog);
                dialog = DialogUtil.createNickNameDialog(this, tvNickName.getText().toString(), new DialogUtil.OnComfirmListening2() {
                    @Override
                    public void confirm(String data) {
                        tvNickName.setText(data);
                    }
                });
                break;
            case R.id.btn_exit_info://注销
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case REQ_EMAIL:
                    tvEmail.setText(data.getStringExtra("email"));
                    break;

            }
        }
    }

    @Override
    protected void onDestroy() {
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }
}
