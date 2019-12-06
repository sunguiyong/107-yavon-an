package com.zt.igreen.module.mine.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.data.LoginBean;
import com.zt.igreen.module.mine.contract.SettingContract;
import com.zt.igreen.module.mine.presenter.SettingPresenter;
import com.zt.igreen.utils.PackageUtil;
import com.zt.igreen.utils.SPUtil;

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
        ImmersionBar.with(this).statusBarColor(R.color.touming).statusBarDarkFont(false)
                .flymeOSStatusBarFontColor(R.color.touming).init();
        sethead(R.color.touming);
        setColor(Color.parseColor("#ffffff"));
        setTitle(getString(R.string.setting));
        tvVersion.setText("v"+ PackageUtil.getAppVersion(this));
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
