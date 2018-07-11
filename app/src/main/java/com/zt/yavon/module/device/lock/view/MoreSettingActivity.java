package com.zt.yavon.module.device.lock.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class MoreSettingActivity extends BaseActivity{
//    @BindView(R.id.iv_lock)
//    ImageView ivLock;
//    @BindView(R.id.tv_switch_lock)
//    TextView tvSwith;
    @Override
    public int getLayoutId() {
        return R.layout.activity_more_setting_lock;
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

    @OnClick({R.id.tv_use_direct_lock,R.id.tv_use_record_lock})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.tv_use_direct_lock:
                DirectUseActivity.startAction(this);
                break;
            case R.id.tv_use_record_lock:
                LockRecordActivity.startAction(this);
                break;
        }
    }
    public static void startAction(Context context){
        Intent intent = new Intent(context,MoreSettingActivity.class);
        context.startActivity(intent);
    }
}
