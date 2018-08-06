package com.zt.yavon.module.main.adddevice.view;

import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.main.adddevice.adapter.RvAddDevice;
import com.zt.yavon.module.main.adddevice.contract.AddDeviceContract;
import com.zt.yavon.module.main.adddevice.model.AddDeviceBean;
import com.zt.yavon.module.main.adddevice.presenter.AddDevicePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ActAddDevice extends BaseActivity<AddDevicePresenter> implements AddDeviceContract.View {

    @BindView(R.id.rv_add_device)
    RvAddDevice rvAddDevice;

    @Override
    public int getLayoutId() {
        return R.layout.act_add_device_layout;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }


    @Override
    public void initView() {
        setTitle("添加设备");
        setRightMenuText("完成");
        findViewById(R.id.tv_right_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<DeviceItemBean> checkedBeans = new ArrayList<>();
//                if (cb1.isChecked())
//                    checkedBeans.add(new DeviceItemBean("", "办公室", true, DeviceEnum.Lamp));
//                if (cb2.isChecked())
//                    checkedBeans.add(new DeviceItemBean("", "办公室", true, DeviceEnum.Lamp));
//                if (cb3.isChecked())
//                    checkedBeans.add(new DeviceItemBean("", "会议室", true, DeviceEnum.Lamp));
//                if (cb4.isChecked())
//                    checkedBeans.add(new DeviceItemBean("", "会议室", true, DeviceEnum.Lamp));
//                setResult(RESULT_OK, (Serializable) checkedBeans);
//                finish();
            }
        });
        mPresenter.getAddDeviceData();
    }

    @Override
    public void returnAddDeviceData(List<AddDeviceBean> data) {
        List<MultiItemEntity> beans = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            AddDeviceBean bean = data.get(i);
            for (int j = 0; j < bean.machines.size(); j++) {
                bean.addSubItem(bean.machines.get(i));
            }
//            bean.setSubItems(bean.machines);
            beans.add(bean);
        }
        rvAddDevice.setData(beans);
    }

    @Override
    public void errorAddDeviceData(String message) {
        ToastUtil.showLong(this, message);
    }
}
