package com.zt.yavon.module.device.lamp.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.device.lock.view.LockRecordActivity;
import com.zt.yavon.module.device.lock.view.LockUseActivity;

import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class LampSettingActivity extends BaseActivity{
//    @BindView(R.id.iv_lock)
//    ImageView ivLock;
//    @BindView(R.id.tv_switch_lock)
//    TextView tvSwith;
    @Override
    public int getLayoutId() {
        return R.layout.activity_more_setting_lamp;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));
        setTitle(getString(R.string.title_more));
//        setRightMenuImage(R.mipmap.more_right);
    }

    @OnClick({R.id.tv_use_direct_lamp,R.id.tv_use_record_lamp})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.tv_use_direct_lamp:
                LampUseActivity.startAction(this);
                break;
            case R.id.tv_use_record_lamp:
                LampRecordActivity.startAction(this);
                break;
        }
    }
    public static void startAction(Context context){
        Intent intent = new Intent(context,LampSettingActivity.class);
        context.startActivity(intent);
    }
}
