package com.zt.yavon.module.deviceconnect.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.DevTypeBean;
import com.zt.yavon.module.deviceconnect.adapter.DeviceAdapter;
import com.zt.yavon.module.deviceconnect.adapter.DeviceTypeAdapter;
import com.zt.yavon.module.deviceconnect.contract.AddDevContract;
import com.zt.yavon.module.deviceconnect.presenter.AddDevPresenter;
import com.zt.yavon.utils.Constants;

import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

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
        mRxManager.on(Constants.EVENT_BIND_DEV_SUCCESS, new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        setTitle("设备添加");
        reDeviceType.setLayoutManager(new LinearLayoutManager(this));
        deviceTypeAdapter = new DeviceTypeAdapter(this);
        reDeviceType.setAdapter(deviceTypeAdapter);
        reDevice.setLayoutManager(new GridLayoutManager(this, 3));
        deviceAdapter = new DeviceAdapter();
        reDevice.setAdapter(deviceAdapter);
        deviceTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DevTypeBean bean = deviceTypeAdapter.getItem(position);
                deviceTypeAdapter.setPostion(position);
                deviceTypeAdapter.notifyDataSetChanged();
                deviceAdapter.setNewData(bean.getTypes());
            }
        });
        deviceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DevTypeBean.TYPE bean = deviceAdapter.getItem(position);
                switch (bean.type) {
                    case Constants.MACHINE_TYPE_BLUE_LOCK:
                        WifiDeviceActivity.start(DeviceAddActivity.this,bean);
//                        EditDevActivity.startAction(DeviceAddActivity.this,EditDevActivity.TYPE_LOCK1);
                        break;
                    case Constants.MACHINE_TYPE_BATTERY_LOCK:
//                        EditDevActivity.startAction(DeviceAddActivity.this,EditDevActivity.TYPE_LOCK2);
                        ScanCodeActivity.start(DeviceAddActivity.this,bean);
                        break;
                    case Constants.MACHINE_TYPE_LIGHT:
                        WifiDeviceActivity.start(DeviceAddActivity.this,bean);
                        break;
                    case Constants.MACHINE_TYPE_ADJUST_TABLE:
//                        EditDevActivity.startAction(DeviceAddActivity.this,EditDevActivity.TYPE_DESK);
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
        deviceTypeAdapter.setNewData(list);
        deviceAdapter.setNewData(list.get(0).getTypes());
    }
}
