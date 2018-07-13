package com.zt.yavon.module.deviceconnect.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.deviceconnect.adapter.DeviceAdapter;

import butterknife.BindView;
import butterknife.OnClick;

public class DeviceTypeActivity extends BaseActivity {
    public static final String BULETOOTH_LOCK = "BULETOOTH_LOCK";
    public static final String BATTERY_LOCK = "BATTERY_LOCK";
    public static final String WIFI_LIGHT = "WIFI_LIGHT";
    public static final String LIFT_TABLES = "LIFT_TABLES";
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_device_dec)
    TextView tvDeviceDec;
    @BindView(R.id.iv_device)
    ImageView ivDevice;
    @BindView(R.id.re_device)
    EasyRecyclerView reDevice;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_device)
    TextView tvDevice;
    private String type;
    private DeviceAdapter deviceAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_device_type;
    }

    public static void start(Activity activity, String type) {
        Intent intent = new Intent(activity, DeviceTypeActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    @Override
    public void initPresenter() {
        type = getIntent().getStringExtra("type");
    }

    @Override
    public void initView() {
        switch (type) {
            case BULETOOTH_LOCK:
              tvDevice.setVisibility(View.GONE);
              reDevice.setVisibility(View.VISIBLE);
                break;
            case BATTERY_LOCK:
                tvDevice.setVisibility(View.VISIBLE);
                reDevice.setVisibility(View.GONE);
                break;
            case WIFI_LIGHT:
                tvDevice.setVisibility(View.VISIBLE);
                reDevice.setVisibility(View.GONE);
                break;
            case LIFT_TABLES:
                tvDevice.setVisibility(View.VISIBLE);
                reDevice.setVisibility(View.GONE);
                break;
        }
    }


    @OnClick({R.id.tv_right_header, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right_header:

                break;
            case R.id.tv_next:

                break;
        }
    }
}
