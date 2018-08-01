package com.zt.yavon.module.deviceconnect.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.DevTypeBean;
import com.zt.yavon.module.deviceconnect.adapter.DeviceAdapter;
import com.zt.yavon.module.deviceconnect.adapter.DeviceTypeAdapter;
import com.zt.yavon.module.deviceconnect.contract.AddDevContract;
import com.zt.yavon.module.deviceconnect.presenter.AddDevPresenter;

import java.util.List;

import butterknife.BindView;

public class DeviceAddActivity extends BaseActivity<AddDevPresenter> implements AddDevContract.View{

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
        mPresenter.setVM(this);
    }

    @Override
    public void initView() {
        setTitle("设备添加");
        reDeviceType.setLayoutManager(new LinearLayoutManager(this));
        deviceTypeAdapter = new DeviceTypeAdapter(this);
        reDeviceType.setAdapter(deviceTypeAdapter);
        reDevice.setLayoutManager(new GridLayoutManager(this, 3));
        deviceAdapter = new DeviceAdapter(this);
        reDevice.setAdapter(deviceAdapter);
        deviceTypeAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                DevTypeBean bean = deviceTypeAdapter.getItem(position);
                deviceAdapter.clear();
                deviceTypeAdapter.setPostion(position);
                deviceTypeAdapter.notifyDataSetChanged();
                deviceAdapter.addAll(bean.getTypes());
            }
        });
        deviceAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                DevTypeBean.TYPE bean = deviceAdapter.getItem(position);
                switch (bean.type) {
                    case "wifi":
//                        WifiDeviceActivity.start(DeviceAddActivity.this);
                        EditDevActivity.startAction(DeviceAddActivity.this,EditDevActivity.TYPE_LAMP);
                        break;
                    case "blueTooth":
                        EditDevActivity.startAction(DeviceAddActivity.this,EditDevActivity.TYPE_LAMP);
                        break;
                    case "scan":
                        ScanCodeActivity.start(DeviceAddActivity.this);
                        break;
                }
            }
        });
        mPresenter.getMachineTypes();
    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, DeviceAddActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void returnMachine(List<DevTypeBean> list) {
        deviceTypeAdapter.addAll(list);
    }
}
