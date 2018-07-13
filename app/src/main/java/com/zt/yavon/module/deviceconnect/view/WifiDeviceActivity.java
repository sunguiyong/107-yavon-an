package com.zt.yavon.module.deviceconnect.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.base.utils.NetWorkUtils;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.main.view.WebviewActivity;
import com.zt.yavon.utils.DialogUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class WifiDeviceActivity extends BaseActivity {


    @BindView(R.id.tv_scan)
    TextView tvScan;

    @BindView(R.id.tv_disconnect)
    TextView tvDisconnect;
    @BindView(R.id.lin_search_nothing)
    LinearLayout linSearchNothing;
    @BindView(R.id.lin_scan_bluetooth)
    LinearLayout linScanBluetooth;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wifi_device;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle("连接设备");
        setRightMenuText("说明书");
        setRightMenuTopImage(R.mipmap.iv_explan, 12);
        initDialog();
    }

    private void initDialog() {
        if (NetWorkUtils.isWifiConnected(this)){
            DialogUtil.createWifiDialog(this, new DialogUtil.OnComfirmListening() {
                @Override
                public void confirm() {
                 linSearchNothing.setVisibility(View.VISIBLE);
                }
            });
        }else {
            DialogUtil.create2BtnInfoDialog(this, "目前手机没有连接Wi-Fi", "取消", "去连接", new DialogUtil.OnComfirmListening() {
                @Override
                public void confirm() {
                 startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                }
            });
        }
    }


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, WifiDeviceActivity.class);
        activity.startActivity(intent);
    }

    @OnClick({R.id.tv_scan, R.id.tv_disconnect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_scan:
                DeviceTypeActivity.start(this,DeviceTypeActivity.WIFI_LIGHT);
                finish();
                break;
            case R.id.tv_disconnect:
                WebviewActivity.start(this,"www.baidu.com");
                break;
        }
    }
}
