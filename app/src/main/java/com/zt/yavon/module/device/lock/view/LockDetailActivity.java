package com.zt.yavon.module.device.lock.view;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.base.utils.LoadingDialog;
import com.common.base.utils.LogUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yeeloc.elocsdk.ElocSDK;
import com.yeeloc.elocsdk.ble.BleEngine;
import com.yeeloc.elocsdk.ble.BleStatus;
import com.yeeloc.elocsdk.ble.UnlockMode;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.DevDetailBean;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.device.lock.contract.LockDetailContract;
import com.zt.yavon.module.device.lock.presenter.LockDetailPresenter;
import com.zt.yavon.utils.Constants;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.utils.PakageUtil;
import com.zt.yavon.utils.YisuobaoSDK;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by lifujun on 2018/7/10.
 */

public class LockDetailActivity extends BaseActivity<LockDetailPresenter> implements LockDetailContract.View {
    @BindView(R.id.iv_lock)
    ImageView ivLock;
    @BindView(R.id.tv_switch_lock)
    TextView tvSwith;
    private TabBean.MachineBean machineBean;

    private DevDetailBean bean;
    private Dialog dialog;

    private YisuobaoSDK ysbSDK;
    private YisuobaoSDK.YisuobaoListener listener = new YisuobaoSDK.YisuobaoListener(){
        @Override
        public void onBatteryPowerChanged(String power) {
            mPresenter.reportLowBatteryLock(bean.getMachine_id(),power);
        }

        @Override
        public void onLockStateChanged(boolean isOpen) {
            DialogUtil.dismiss(dialog);
            updateView(isOpen);
            if(ysbSDK != null)
            mPresenter.switchDev(machineBean.id + "", isOpen);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_lock_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        machineBean = (TabBean.MachineBean) getIntent().getSerializableExtra("machineBean");
        mRxManager.on(Constants.EVENT_AUTO_LOCK, new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                bean.setAuto_lock((Boolean) o);
                if(ysbSDK != null)
                    ysbSDK.autoLock((Boolean) o);
            }
        });
        mRxManager.on(Constants.EVENT_AUTO_UNLOCK_LOW, new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                bean.setLowpower_hand_unlock((Boolean) o);
                if(ysbSDK != null)
                    ysbSDK.autoLowBatUnLock((Boolean) o);
            }
        });
    }


    @Override
    public void initView() {
        setTitle(getString(R.string.title_lock));
        setRightMenuImage(R.mipmap.more_right);
        updateView("ON".equals(machineBean.status));
        try {
            initSDK();
        }catch (Exception e){
            e.printStackTrace();
        }
        mPresenter.getDevDetail(machineBean.id + "");
    }



    private void initSDK(){
        if (Constants.MACHINE_TYPE_BATTERY_LOCK.equals(machineBean.machine_type)) {
            ysbSDK = new YisuobaoSDK(this,machineBean.asset_number,machineBean.password,listener);
        }
    }

    @OnClick({R.id.tv_switch_lock, R.id.tv_right_header})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.tv_switch_lock:
                if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                    BluetoothAdapter.getDefaultAdapter().enable();
                } else {
                    initPermission();
                }
                break;
            case R.id.tv_right_header:
                LockSettingActivity.startAction(this,bean);
                break;
        }
    }

    private void initPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.LOCATION)
                .onGranted(permissions -> {
                    if(bean == null){
                        return;
                    }
                    DialogUtil.dismiss(dialog);
                    dialog = LoadingDialog.showDialogForLoading(LockDetailActivity.this,"操作中...",true,null);
                    ysbSDK.switchLock();
                })
                .onDenied(permissions -> {
                    LogUtil.d("=========denied permissions:"+ Arrays.toString(permissions.toArray()));
                    dialog = DialogUtil.create2BtnInfoDialog(LockDetailActivity.this, "需要蓝牙和定位权限，马上去开启?", "取消", "开启", new DialogUtil.OnComfirmListening() {
                        @Override
                        public void confirm() {
                            PakageUtil.startAppSettings(LockDetailActivity.this);
                        }
                    });
                })
                .start();
    }

    public static void startAction(Context context, TabBean.MachineBean machineBean) {
        Intent intent = new Intent(context, LockDetailActivity.class);
        intent.putExtra("machineBean", machineBean);
        context.startActivity(intent);
    }
    @Override
    public void returnDevDetail(DevDetailBean bean) {
        this.bean = bean;
        if(Constants.MACHINE_TYPE_BATTERY_LOCK.equals(bean.getMachine_type())){
            ivLock.setImageResource(R.drawable.selector_lock2_dev);
        }else{
            ivLock.setImageResource(R.drawable.selector_lock1_dev);
        }
        updateView("ON".equals(bean.getMachine_status()));
//        ysbSDK.autoLock(bean.isAuto_lock());
//        ysbSDK.autoLowBatUnLock(bean.isLowpower_hand_unlock());
        ysbSDK.getLockState();
    }
    private void updateView(boolean isOn){
        tvSwith.setSelected(isOn);
        ivLock.setSelected(isOn);
    }

    @Override
    protected void onDestroy() {
        ysbSDK.release();
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }
}
