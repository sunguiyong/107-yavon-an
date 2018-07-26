package com.zt.yavon.module.mine.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.module.mine.contract.SettingContract;
import com.zt.yavon.module.mine.presenter.SettingPresenter;
import com.zt.yavon.utils.PakageUtil;
import com.zt.yavon.utils.SPUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/17.
 */

public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View{
    @BindView(R.id.tv_version_settting)
    TextView tvVersion;
    @BindView(R.id.switch_update_setting)
    SwitchCompat updateSwitch;
    @BindView(R.id.switch_msg_setting)
    SwitchCompat msgSwitch;
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.setting));
        tvVersion.setText("v"+PakageUtil.getAppVersion(this));
        LoginBean bean = SPUtil.getAccount(this);
        if(bean != null){
            updateSwitch.setChecked(bean.getOpen_auto_update());
            msgSwitch.setChecked(bean.getOpen_system_message());
        }
    }
    public static void startAction(Context context){
        Intent intent = new Intent(context,SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }
    @OnClick({R.id.switch_update_setting,R.id.switch_msg_setting})
    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.switch_update_setting:
                mPresenter.switchUpdate(updateSwitch.isChecked());
                break;
            case R.id.switch_msg_setting:
                mPresenter.switchMsg(msgSwitch.isChecked());
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void switchUpdateResult(boolean isSuccess) {
        if(!isSuccess){
            updateSwitch.setChecked(updateSwitch.isChecked());
        }
    }

    @Override
    public void switchMsgResult(boolean isSuccess) {
        if(!isSuccess){
            msgSwitch.setChecked(msgSwitch.isChecked());
        }
    }
}
