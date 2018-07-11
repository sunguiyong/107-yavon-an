package com.zt.yavon.module.device;

import android.view.View;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.device.desk.view.DeskDetailActivity;
import com.zt.yavon.module.device.lamp.view.LampDetailActivity;
import com.zt.yavon.module.device.lock.view.LockDetailActivity;

import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/11.
 */

public class TestActivity extends BaseActivity{
    @Override
    public int getLayoutId() {
        return R.layout.activity_test_device;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }
    @OnClick({R.id.btn_lamp,R.id.btn_lock,R.id.btn_desk})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.btn_lamp:
                LampDetailActivity.startAction(this);
                break;
            case R.id.btn_lock:
                LockDetailActivity.startAction(this);
                break;
            case R.id.btn_desk:
                DeskDetailActivity.startAction(this);
                break;
        }
    }
}
