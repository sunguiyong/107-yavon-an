package com.zt.yavon.module.device.desk.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.device.lamp.view.LampRecordActivity;
import com.zt.yavon.module.device.lamp.view.LampUseActivity;
import com.zt.yavon.utils.DialogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class DeskSettingActivity extends BaseActivity{
//    @BindView(R.id.iv_lock)
//    ImageView ivLock;
    @BindView(R.id.tv_time_set)
    TextView tvTime;
    private Dialog dialog;
    @Override
    public int getLayoutId() {
        return R.layout.activity_more_setting_desk;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle(getString(R.string.title_more));
//        setRightMenuImage(R.mipmap.more_right);
    }

    @OnClick({R.id.tv_time_set,R.id.tv_use_direct_desk,R.id.tv_use_record_desk})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.tv_time_set:
                DialogUtil.dismiss(dialog);
                dialog = DialogUtil.createSitTimeDialog(this, tvTime.getText().toString(), new DialogUtil.OnComfirmListening2() {
                    @Override
                    public void confirm(String data) {
                        tvTime.setText(data);
                    }
                });
                break;
            case R.id.tv_use_direct_desk:
                DeskUseActivity.startAction(this);
                break;
            case R.id.tv_use_record_desk:
                DeskRecordActivity.startAction(this);
                break;
        }
    }
    public static void startAction(Context context){
        Intent intent = new Intent(context,DeskSettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }
}
