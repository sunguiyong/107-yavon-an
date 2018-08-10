package com.zt.yavon.module.device.lamp.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.DevDetailBean;
import com.zt.yavon.module.data.MineRoomBean;
import com.zt.yavon.module.device.desk.view.DevUseRecordActivity;
import com.zt.yavon.module.device.lock.view.LockRecordActivity;
import com.zt.yavon.module.device.lock.view.LockUseActivity;
import com.zt.yavon.module.device.share.view.ShareSettingActivity;

import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class LampSettingActivity extends BaseActivity{
    private DevDetailBean machine;

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
        machine = (DevDetailBean)getIntent().getSerializableExtra("machine");
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.title_more));
//        setRightMenuImage(R.mipmap.more_right);
    }

    @OnClick({R.id.tv_use_direct_lamp,R.id.tv_use_record_lamp,R.id.tv_share_setting})
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
                DevUseRecordActivity.startAction(this,machine.getMachine_id());
//                LampRecordActivity.startAction(this);
                break;
            case R.id.tv_share_setting:
                MineRoomBean.Machine bean = new MineRoomBean.Machine();
                bean.setMachine_id(machine.getMachine_id());
                bean.setUser_type(machine.getUser_type());
                ShareSettingActivity.startAction(this,bean);
                break;
        }
    }
    public static void startAction(Context context, DevDetailBean devBean){
        Intent intent = new Intent(context,LampSettingActivity.class);
        intent.putExtra("machine",devBean);
        context.startActivity(intent);
    }
}
