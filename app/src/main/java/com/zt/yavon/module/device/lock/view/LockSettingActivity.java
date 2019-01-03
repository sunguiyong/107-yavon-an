package com.zt.yavon.module.device.lock.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.DevDetailBean;
import com.zt.yavon.module.data.DocBean;
import com.zt.yavon.module.data.MineRoomBean;
import com.zt.yavon.module.device.desk.view.DevUseRecordActivity;
import com.zt.yavon.module.device.lock.contract.LockSettingContract;
import com.zt.yavon.module.device.lock.presenter.LockSettingPresenter;
import com.zt.yavon.module.device.share.view.ShareSettingActivity;
import com.zt.yavon.module.main.frame.view.WebviewActivity;
import com.zt.yavon.utils.Constants;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class LockSettingActivity extends BaseActivity<LockSettingPresenter> implements LockSettingContract.View{
    private DevDetailBean machine;

    @BindView(R.id.switch_auto_close)
    SwitchCompat switch1;
    @BindView(R.id.switch_low_open)
    SwitchCompat switch2;
    @BindView(R.id.tv_sn)
    TextView tvSn;
    @Override
    public int getLayoutId() {
        return R.layout.activity_more_setting_lock;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        machine = (DevDetailBean)getIntent().getSerializableExtra("machine");
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.title_more));
//        setRightMenuImage(R.mipmap.more_right);
        switch1.setChecked(machine.isAuto_lock());
        switch2.setChecked(machine.isLowpower_hand_unlock());
        tvSn.setText(machine.getAsset_number());
    }

    @OnClick({R.id.tv_use_direct_lock,R.id.tv_use_record_lock,R.id.tv_share_setting})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.tv_use_direct_lock:
//                LockUseActivity.startAction(this);
                String type = Constants.DOC_TYPE_BATTERY_LOCK_INSTRUCTION;
                if(machine != null && Constants.DOC_TYPE_BLUE_LOCK_INSTRUCTION.equals(machine.getMachine_type())){
                    type = Constants.DOC_TYPE_BLUE_LOCK_INSTRUCTION;
                }
                mPresenter.getDoc(type);
                break;
            case R.id.tv_use_record_lock:
                DevUseRecordActivity.startAction(this,machine.getMachine_id());
//                LockRecordActivity.startAction(this);
                break;
            case R.id.tv_share_setting:
                MineRoomBean.Machine bean = new MineRoomBean.Machine();
                bean.setMachine_id(machine.getMachine_id());
                bean.setUser_type(machine.getUser_type());
                ShareSettingActivity.startAction(this,bean);
                break;
        }
    }
    @OnCheckedChanged({R.id.switch_auto_close,R.id.switch_low_open})
    public void checkedchanged(CompoundButton button,boolean isChecked){
        if(button.getId() == R.id.switch_auto_close){
            if(isChecked){
                if(machine.isAuto_lock()){
                    machine.setAuto_lock(false);
                }else{
                   mPresenter.autoLock(machine.getMachine_id(),true);
                }
            }else{
                mPresenter.autoLock(machine.getMachine_id(),false);
            }
        }else{
            if(isChecked){
                if(machine.isLowpower_hand_unlock()){
                    machine.setLowpower_hand_unlock(false);
                }else{
                    mPresenter.lowBatteryUnlock(machine.getMachine_id(),true);
                }
            }else{
                mPresenter.lowBatteryUnlock(machine.getMachine_id(),false);
            }
        }
    }
    public static void startAction(Context context, DevDetailBean devBean){
        Intent intent = new Intent(context,LockSettingActivity.class);
        intent.putExtra("machine",devBean);
        context.startActivity(intent);
    }

    @Override
    public void autoLockSuccess(boolean result) {
        mRxManager.post(Constants.EVENT_AUTO_LOCK,result);
    }

    @Override
    public void lowBatteryUnlockSuccess(boolean result) {
        mRxManager.post(Constants.EVENT_AUTO_UNLOCK_LOW,result);
    }

    @Override
    public void returnDoc(DocBean bean) {
        WebviewActivity.start(this,bean.url,null,null);
    }
}
