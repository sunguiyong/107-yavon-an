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

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class LockDetailActivity extends BaseActivity<LockDetailPresenter> implements LockDetailContract.View {
    @BindView(R.id.iv_lock)
    ImageView ivLock;
    @BindView(R.id.tv_switch_lock)
    TextView tvSwith;
    private TabBean.MachineBean machineBean;
    private BleEngine engine;
    private DevDetailBean bean;
    private Dialog dialog;
    @Override
    public int getLayoutId() {
        return R.layout.activity_lock_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        machineBean = (TabBean.MachineBean) getIntent().getSerializableExtra("machineBean");
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.title_lock));
        setRightMenuImage(R.mipmap.more_right);
        updateView("ON".equals(machineBean.status));
        mPresenter.getDevDetail(machineBean.id + "");
        try {
            initSDK();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void initSDK(){
        if (Constants.MACHINE_TYPE_BATTERY_LOCK.equals(machineBean.machine_type)) {
            engine = ElocSDK.getBleEngine(this);
//            engine.setGlobalCallback(new BleEngine.Callback() {
//                @Override
//                public void onReceive(int status, Object data) {
//                    switch (status) {
//                        case BleStatus.BATTERY_POWER:
//                            LogUtil.d("============BATTERY_POWER,data:" + data.toString());
//                            break;
//                        case BleStatus.DEVICE_BUSY:
//                            LogUtil.d("============DEVICE_BUSY,data:" + data.toString());
//                            break;
//                        case BleStatus.INVALID_OPCODE:
//                            LogUtil.d("============INVALID_OPCODE,data:" + data.toString());
//                            break;
//                        case BleStatus.GET_TIME:
//                            LogUtil.d("============GET_TIME,data:" + data.toString());
//                            break;
//                        case BleStatus.GRANT_EXPIRE:
//                            LogUtil.d("============GRANT_EXPIRE,data:" + data.toString());
//                            break;
//                        case BleStatus.LOCK_START:
//                            LogUtil.d("============LOCK_START,data:" + data.toString());
//                            break;
//                        case BleStatus.LOCK_STATE:
//                            LogUtil.d("============LOCK_STATE,data:" + data.toString());
////                            updateView(!(Boolean) data);
//                            break;
//                        case BleStatus.REQUEST_FOR_TIME:
//                            LogUtil.d("============REQUEST_FOR_TIME,data:" + data.toString());
//                            break;
//                        case BleStatus.SCAN_FAILED:
//                            LogUtil.d("============SCAN_FAILED,data:" + data.toString());
//                            break;
//                        case BleStatus.SIGN_INVALID:
//                            LogUtil.d("============SIGN_INVALID,data:" + data.toString());
//                            break;
//                        case BleStatus.UNLOCK_START:
//                            LogUtil.d("============UNLOCK_START,data:" + data.toString());
//                            break;
//                        default:
//                    }
//                }
//            });
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
                LockSettingActivity.startAction(this);
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
//                    engine.getDevice(bean.getAsset_number(), new BleEngine.Callback() {
//                        @Override
//                        public void onReceive(int i, Object o) {
//
//                        }
//                    });
                    DialogUtil.dismiss(dialog);
                    dialog = LoadingDialog.showDialogForLoading(LockDetailActivity.this,"操作中...",true,null);
                    engine.unlock(bean.getAsset_number(), bean.getPassword(), UnlockMode.MODE_TOGGLE, new BleEngine.Callback() {
                        @Override
                        public void onReceive(int status, Object data) {
                            switch (status) {
                                case BleStatus.LOCK_COMPLETE:
                                    DialogUtil.dismiss(dialog);
//                                    LogUtil.d("============LOCK_COMPLETE,data:" + data);
                                    updateView(false);
                                    mPresenter.switchDev(machineBean.id + "", false);
                                    break;
                                case BleStatus.UNLOCK_COMPLETE:
                                    DialogUtil.dismiss(dialog);
//                                    LogUtil.d("============UNLOCK_COMPLETE,data:" + data);
                                    updateView(true);
                                    mPresenter.switchDev(machineBean.id + "", true);
                                    break;
                            }
                        }
                    });
                })
                .onDenied(permissions -> {
                    LogUtil.d("=========denied permissions:"+ Arrays.toString(permissions.toArray()));
                    DialogUtil.create2BtnInfoDialog(LockDetailActivity.this, "需要蓝牙和定位权限，马上去开启?", "取消", "开启", new DialogUtil.OnComfirmListening() {
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
//        updateView("ON".equals(bean.getMachine_status()));
        engine.getLockState(bean.getAsset_number(), bean.getPassword(), new BleEngine.Callback() {
            @Override
            public void onReceive(int i, Object data) {
                if (i == BleStatus.LOCK_STATE) {
                    updateView((Boolean) data);
                }
            }
        });
    }
    private void updateView(boolean isOn){
        tvSwith.setSelected(isOn);
        ivLock.setSelected(isOn);
    }

    @Override
    protected void onDestroy() {
        if(engine != null)
        engine.release();
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }
}
