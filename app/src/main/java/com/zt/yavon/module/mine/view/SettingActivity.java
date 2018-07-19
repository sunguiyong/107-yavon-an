package com.zt.yavon.module.mine.view;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.utils.PakageUtil;

import butterknife.BindView;

/**
 * Created by lifujun on 2018/7/17.
 */

public class SettingActivity extends BaseActivity{
    @BindView(R.id.tv_version_settting)
    TextView tvVersion;
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle(getString(R.string.setting));
        tvVersion.setText("v"+PakageUtil.getAppVersion(this));
    }
    public static void startAction(Context context){
        Intent intent = new Intent(context,SettingActivity.class);
        context.startActivity(intent);
    }
//    @OnClick({R.id.btn_confirm_bind_email})
//    @Override
//    public void doubleClickFilter(View view) {
//        super.doubleClickFilter(view);
//    }
//
//    @Override
//    public void doClick(View view) {
//        switch (view.getId()){
//            case R.id.btn_confirm_bind_email:
//                String email = etEmail.getText().toString();
//                if(!TextUtils.isEmpty(email)){
//                    Intent intent = new Intent();
//                    intent.putExtra("email",email);
//                    setResult(RESULT_OK,intent);
//                    finish();
//                }else{
//                    ToastUtil.showShort(this,"邮箱不能为空");
//                }
//                break;
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
