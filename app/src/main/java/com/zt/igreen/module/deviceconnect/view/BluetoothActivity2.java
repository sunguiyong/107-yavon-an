package com.zt.igreen.module.deviceconnect.view;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.base.utils.LoadingDialog;
import com.common.base.utils.ToastUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.qmjk.qmjkcloud.entity.BLEDeviceBean;
import com.qmjk.qmjkcloud.listener.OnResponseDeviceListener;
import com.qmjk.qmjkcloud.listener.OnResponseNetworkListener;
import com.qmjk.qmjkcloud.manager.QmjkDeviceManager;
import com.qmjk.qmjkcloud.manager.QmjkNetworkManager;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.data.DevTypeBean;
import com.zt.igreen.module.data.HealthInfoBean;
import com.zt.igreen.module.data.LoginBean;
import com.zt.igreen.module.data.TabBean;
import com.zt.igreen.module.device.health.view.HealthActivity;
import com.zt.igreen.module.deviceconnect.adapter.BleAdapterTwo;
import com.zt.igreen.module.main.frame.contract.MainContract;
import com.zt.igreen.module.main.frame.presenter.MainPresenter;
import com.zt.igreen.utils.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

public class BluetoothActivity2 extends BaseActivity<MainPresenter> implements
        OnResponseNetworkListener, OnResponseDeviceListener, MainContract.View {


    @BindView(R.id.recycler)
    RecyclerView recycler;
    private QmjkDeviceManager deviceManager;
    private BleAdapterTwo adapterTwo;
    private DevTypeBean.TYPE typeData;
    private BLEDeviceBean bean;
    private String mac;
    private String name;
    private Dialog dialog;
    List<BLEDeviceBean> list;
    private QmjkNetworkManager mNetworkManager;
    private LoginBean beans;
    private TabBean.MachineBean machineBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bluetooth1;
    }

    @Override
    public void initPresenter() {
        typeData = (DevTypeBean.TYPE) getIntent().getSerializableExtra("typeData");
        machineBean = (TabBean.MachineBean) getIntent().getSerializableExtra("machineBean");
        mPresenter.setVM(this);
        mPresenter.getUserINfo();
    }

    @Override
    public void initView() {
        setTitle("蓝牙设备");
        showProgress("加载中");


        deviceManager = QmjkDeviceManager.getInstance(this);
        sethead(R.color.qingse);
        beans = SPUtil.getAccount(this);
        setColor(Color.parseColor("#ffffff"));
        ImmersionBar.with(this).statusBarColor(R.color.qingse).statusBarDarkFont(true).flymeOSStatusBarFontColor(R.color.qingse).init();
        setColor(Color.parseColor("#ffffff"));
        deviceManager.startScan(3, false);
        deviceManager.registerAPP();// 注册app到云端
        deviceManager.setBleSearchname("cooya");
        deviceManager.setFlashlight(2);// 0不亮1闪一下2长亮
        deviceManager.registerDeviceListeners(this);
        mNetworkManager = QmjkNetworkManager.getInstance(this);
        mNetworkManager.registerUIListener(this);
        adapterTwo = new BleAdapterTwo();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(adapterTwo);
        adapterTwo.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                bean = (BLEDeviceBean) adapter.getItem(position);
                Dialog dialog1 = LoadingDialog.showDialogForLoading(BluetoothActivity2.this);
                String device_name = bean.getDevice().getName();
                if (machineBean != null) {
                    if (name.equals(machineBean.asset_number)) {
                        bean.getDevice().connectGatt(BluetoothActivity2.this, true, new BluetoothGattCallback() {
                            @Override
                            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                                super.onConnectionStateChange(gatt, status, newState);
                                if (status == BluetoothGatt.GATT_SUCCESS) {
                                    name = gatt.getDevice().getName();
                                    mac = gatt.getDevice().getAddress();
                                    typeData.sn = mac;
                                    typeData.name = name;
                                    List<BluetoothGattService> list = gatt.getServices();
                                    dialog1.dismiss();
                                    HealthActivity.start(BluetoothActivity2.this, mac);
                                } else {
                                    ToastUtil.show(BluetoothActivity2.this, "连接正确的设备", Toast.LENGTH_LONG);
                                }
                            }
                        });
                    }
                } else if (typeData != null) {
                    bean.getDevice().connectGatt(BluetoothActivity2.this, true, new BluetoothGattCallback() {
                        @Override
                        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                            super.onConnectionStateChange(gatt, status, newState);
                            if (status == BluetoothGatt.GATT_SUCCESS) {
                                name = gatt.getDevice().getName();
                                mac = gatt.getDevice().getAddress();
                                typeData.sn = mac;
                                typeData.name = name;
                                List<BluetoothGattService> list = gatt.getServices();
                                dialog1.dismiss();

                                DeviceTypeActivity.startAction(BluetoothActivity2.this, typeData, mac);

                            }
                        }
                    });

                }
            }
        });
    }

    public static void startAction(Activity activity, TabBean.MachineBean machineBean) {
        Intent intent = new Intent(activity, BluetoothActivity2.class);
        intent.putExtra("machineBean", machineBean);
        activity.startActivity(intent);
    }

    public static void start(Activity activity, DevTypeBean.TYPE typeData) {
        Intent intent = new Intent(activity, BluetoothActivity2.class);
        intent.putExtra("typeData", typeData);
        activity.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (list != null) {
            deviceManager.unregisterOnResponseDeviceListeners(this);
            mNetworkManager.unregisterUIListener(this);
            deviceManager.quit();
        }
    }

    @Override
    public void onDeviceResponse(int i, Object o) {

    }

    @Override
    public void onDevicePlugIn() {

    }

    @Override
    public void onDevicePlugOut() {

    }

    @Override
    public void onResultBleDevices(final List<BluetoothDevice> list) {
     /*   // 获取所有扫描到的蓝牙设备
       runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapterTwo.setNewData(list);
            }
        });*/
    }

    @Override
    public void onResultBleDevicesDetail(List<BLEDeviceBean> list) {
        // 获取所有扫描到的蓝牙设备
        this.list = list;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (list == null) {
                    Looper.prepare();
                    ToastUtil.show(BluetoothActivity2.this, "未搜索到设备", Toast.LENGTH_LONG);
                    Looper.loop();
                    dismissProgress();

                } else {
                    dismissProgress();
                    adapterTwo.setNewData(list);
                }
            }
        });
    }

    @Override
    public void onResultUSBDevices(List<UsbSerialPort> list) {
        // 获取所有扫描到的USB设备
    }

    @Override
    public void onDataChanged(double[] doubles) {

    }

    @Override
    public void onBleConnectStateChanged(int i, int i1) {

    }

    @Override
    public void onBleBondStateChanged(int code) {
        runOnUiThread(new Runnable() {
            public void run() {
                switch (code) {
                    case -1:
                        ToastUtil.show(BluetoothActivity2.this, "连接超时", Toast.LENGTH_LONG);
                        break;
                    case 0:
                        ToastUtil.show(BluetoothActivity2.this, "断开连接", Toast.LENGTH_LONG);
                        break;
                    case 2:
                        ToastUtil.show(BluetoothActivity2.this, "连接成功", Toast.LENGTH_LONG);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onBleSwitchChanged(int code) {
        runOnUiThread(new Runnable() {
            public void run() {
                switch (code) {
                    case 1:
                        ToastUtil.show(BluetoothActivity2.this, "蓝牙开启中", Toast.LENGTH_LONG);
                        break;
                    case 2:
                        ToastUtil.show(BluetoothActivity2.this, "蓝牙已开启", Toast.LENGTH_LONG);
                        break;
                    case 3:
                        ToastUtil.show(BluetoothActivity2.this, "蓝牙关闭中", Toast.LENGTH_LONG);
                        break;
                    case 4:
                        ToastUtil.show(BluetoothActivity2.this, "蓝牙已关闭", Toast.LENGTH_LONG);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onBleBattery(int i) {

    }

    @Override
    public void onProgressChanged(String s, int i, float v, float v1, int i1, int i2) {

    }

    @Override
    public void onError(String s, int i, int i1, String s1) {

    }

    @Override
    public void onDfuProcessStarting(String s) {

    }

    @Override
    public void onDfuCompleted(String s) {

    }


    @Override
    public void returnUserINfo(HealthInfoBean bean) {
        JSONObject params = new JSONObject();
        try {
            params.put("userAccount", beans.getMobile());
            params.put("birth", bean.getBirthday() + "");
            params.put("infoHigh", "100");
            params.put("infoLow", "80");
            params.put("sex", bean.getSex() + "");
            params.put("height", bean.getHeight() + "");
            params.put("weight", bean.getWeight() + ""
            );
            params.put("infoBPSituation", "1");
            mNetworkManager.addUserinfo(params);
        } catch (JSONException e) {
            Log.e("e", e.getMessage());
        }
    }

    @Override
    public void onNetworkResponse(int i, Object result) {
        if (result != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject params = new JSONObject();
                   /* Toast.makeText(getApplicationContext(), result.toString(),
                            Toast.LENGTH_LONG).show();*/
                    try {
                        params.put("userAccount", beans.getMobile());
                        mNetworkManager.login(params);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }
}
