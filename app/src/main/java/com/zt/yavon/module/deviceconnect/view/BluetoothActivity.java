package com.zt.yavon.module.deviceconnect.view;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.main.frame.view.WebviewActivity;
import com.zt.yavon.utils.DialogUtil;

import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

public class BluetoothActivity extends BaseActivity {

    @BindView(R.id.tv_scan)
    TextView tvScan;
    @BindView(R.id.tv_device)
    TextView tvDevice;
    @BindView(R.id.tv_disconnect)
    TextView tvDisconnect;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.lin_searching)
    LinearLayout linSearching;
    @BindView(R.id.lin_search_nothing)
    LinearLayout linSearchNothing;
    @BindView(R.id.lin_scan_bluetooth)
    LinearLayout linScanBluetooth;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean first=true;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 搜索到的不是已经绑定的蓝牙设备
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
//                     显示在TextView上
                    Log.e("device", device.getName() + ":"
                            + device.getAddress() + "\n");
                    tvDevice.append(device.getName() + ":"
                            + device.getAddress() + "\n");
                }
                // 搜索完成
            } else if (action
                    .equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                //搜索完成没有搜到蓝牙设备
                linSearchNothing.setVisibility(View.VISIBLE);
                tvDisconnect.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                tvDisconnect.getPaint().setAntiAlias(true);//抗锯齿
                linSearching.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_bluetooth;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initblueconfig();

        // 注册搜索完时的receiver
        checkBleDevice();
        setTitle("连接设备");
        setRightMenuText("说明书");
        setRightMenuTopImage(R.mipmap.iv_explan, 12);


    }

    private void initblueconfig() {
        // 注册用以接收到已搜索到的蓝牙设备的receiver
        IntentFilter mFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, mFilter);
        mFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, mFilter);
        initPermission();

    }

    private void initPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.LOCATION)
                .onGranted(permissions -> {
                })
                .onDenied(permissions -> {
                    DialogUtil.create2BtnInfoDialog(BluetoothActivity.this, getString(R.string.scan_permission), "取消", "开启", new DialogUtil.OnComfirmListening() {
                        @Override
                        public void confirm() {
                            initPermission();
                        }
                    });
                })
                .start();
    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, BluetoothActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 判断是否支持蓝牙，并打开蓝牙
     * 获取到BluetoothAdapter之后，还需要判断是否支持蓝牙，以及蓝牙是否打开。
     * 如果没打开，需要让用户打开蓝牙：
     */
    private void checkBleDevice() {
        //首先获取BluetoothManager
        bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        //获取BluetoothAdapter
        if (bluetoothManager != null) {
            mBluetoothAdapter = bluetoothManager.getAdapter();
            if (mBluetoothAdapter != null) {
                if (!mBluetoothAdapter.isEnabled()) {
                    //调用enable()方法直接打开蓝牙
                    if (!mBluetoothAdapter.enable()) {
                        DialogUtil.create2BtnInfoDialog(BluetoothActivity.this, getString(R.string.bluetooth_permission), "取消", "开启", new DialogUtil.OnComfirmListening() {
                            @Override
                            public void confirm() {
//                            该方法也可以打开蓝牙，但是会有一个系统的弹窗
                                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                                enableBtIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(enableBtIntent);
                            }
                        });
                    } else {
                        initBlueTooth();
                    }
                } else {
                    initBlueTooth();
                }
            } else {
                Log.e("tag", "同意申请");
            }
        }
    }

    private void initBlueTooth() {
        tvScan.setTextColor(getResources().getColor(R.color.mainGreen));
        tvScan.setBackgroundResource(R.drawable.shape_stroke_green);
        // 获取所有已经绑定的蓝牙设备
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        if (devices.size() > 0) {
            for (BluetoothDevice bluetoothDevice : devices) {
                tvDevice.append(bluetoothDevice.getName() + ":"
                        + bluetoothDevice.getAddress() + "\n\n");
            }
        }

        mBluetoothAdapter.startDiscovery();
        linScanBluetooth.setVisibility(View.VISIBLE);
        linSearching.setVisibility(View.VISIBLE);
    }

    protected void onDestroy() {
        super.onDestroy();
        //解除注册
        unregisterReceiver(mReceiver);
    }

    @OnClick({R.id.tv_right_header, R.id.tv_scan, R.id.tv_disconnect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right_header:
                break;
            case R.id.tv_disconnect:
                WebviewActivity.start(this,"www.baidu.com");
                break;
            case R.id.tv_scan:
                if (first){
                    mBluetoothAdapter.startDiscovery();
                    linSearchNothing.setVisibility(View.GONE);
                    linSearching.setVisibility(View.VISIBLE);
                    first=false;
                }else {
                    linSearchNothing.setVisibility(View.GONE);
                    linSearching.setVisibility(View.GONE);
                    DialogUtil.create2BtnInfoDialog(this, getString(R.string.device_is_used), "取消", "去联系", new DialogUtil.OnComfirmListening() {
                        @Override
                        public void confirm() {
                            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:123456"));//跳转到拨号界面，同时传递电话号码
                            startActivity(dialIntent);

                        }
                    });
                }
                break;
        }
    }


}
