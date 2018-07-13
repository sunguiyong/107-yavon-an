package com.zt.yavon.module.deviceconnect.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.DeviceBean;
import com.zt.yavon.module.deviceconnect.adapter.DeviceAdapter;
import com.zt.yavon.module.deviceconnect.adapter.DeviceTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DeviceAddActivity extends BaseActivity {

    @BindView(R.id.re_device_type)
    EasyRecyclerView reDeviceType;
    @BindView(R.id.re_device)
    EasyRecyclerView reDevice;
    private DeviceAdapter deviceAdapter;
    private DeviceTypeAdapter deviceTypeAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_device_add;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        reDeviceType.setLayoutManager(new LinearLayoutManager(this));
        deviceTypeAdapter = new DeviceTypeAdapter(this);
        deviceTypeAdapter.setPostion(0);
        List<String> list = new ArrayList<String>();
        list.add("智能灯系列");
        list.add("智能锁系列");
        list.add("升降桌系列");
        deviceTypeAdapter.addAll(list);
        reDeviceType.setAdapter(deviceTypeAdapter);
        reDevice.setLayoutManager(new GridLayoutManager(this, 3));
        deviceAdapter = new DeviceAdapter(this);
        DeviceBean bean = new DeviceBean();
        bean.setDeviceName("智能灯");
        bean.setDeviceType("0");
        deviceAdapter.add(bean);
        reDevice.setAdapter(deviceAdapter);
        deviceTypeAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                deviceAdapter.clear();
                deviceTypeAdapter.setPostion(position);
                deviceTypeAdapter.notifyDataSetChanged();
                DeviceBean bean = new DeviceBean();
                switch (position) {
                    case 0:
                        bean.setDeviceName("智能灯");
                        bean.setDeviceType("0");
                        deviceAdapter.add(bean);
                        break;
                    case 1:
                        bean.setDeviceName("智能锁");
                        bean.setDeviceType("1");
                        deviceAdapter.add(bean);
                        DeviceBean bean2 = new DeviceBean();
                        bean2.setDeviceName("蓝牙锁");
                        bean2.setDeviceType("3");
                        deviceAdapter.add(bean2);
                        break;
                    case 2:
                        bean.setDeviceName("智能升降桌");
                        bean.setDeviceType("2");
                        deviceAdapter.add(bean);
                        break;
                }
            }
        });
        deviceAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (deviceAdapter.getAllData().get(position).getDeviceType()) {
                    case "0":

                        break;
                    case "1":
                        ScanCodeActivity.start(DeviceAddActivity.this);
                        break;
                    case "2":

                        break;
                    case "3":
                        BluetoothActivity.start(DeviceAddActivity.this);
                        break;
                }
            }
        });
    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, DeviceAddActivity.class);
        activity.startActivity(intent);
    }

}
