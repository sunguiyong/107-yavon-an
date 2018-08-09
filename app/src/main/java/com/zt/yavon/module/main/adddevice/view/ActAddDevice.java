package com.zt.yavon.module.main.adddevice.view;

import android.text.TextUtils;
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

import static com.zt.yavon.module.main.adddevice.adapter.RvAddDevice.ITEM_TYPE_CHILD;

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
                StringBuilder sb = new StringBuilder();
                List<MultiItemEntity> items = rvAddDevice.mAdapter.getData();
                if (items != null) {
                    for (int i = 0; i < items.size(); i++) {
                        MultiItemEntity item = items.get(i);
                        if (item.getItemType() == ITEM_TYPE_CHILD) {
                            AddDeviceBean.MachineBean childItem = (AddDeviceBean.MachineBean) item;
                            if (childItem.is_often) {
                                sb.append(childItem.machine_id);
                                sb.append(",");
                            }
                        } else {
                            continue;
                        }
                    }
                }
                String ids = sb.toString();
                if (!TextUtils.isEmpty(ids)) {
                    mPresenter.setAddDeviceData(sb.toString().substring(0, ids.length() - 1));
                }
            }
        });
        mPresenter.getAddDeviceData();
    }

    @Override
    public void returnAddDeviceData(List<AddDeviceBean> data) {
        List<MultiItemEntity> beans = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            AddDeviceBean bean = data.get(i);
            bean.setSubItems(bean.machines);
            beans.add(bean);
        }
        rvAddDevice.setData(beans);
    }

    @Override
    public void errorAddDeviceData(String message) {
        ToastUtil.showLong(this, message);
    }

    @Override
    public void returnSetDeviceData() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void errorSetDeviceData(String message) {
        ToastUtil.showLong(this, message);
    }
}
