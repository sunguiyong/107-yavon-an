package com.zt.igreen.module.main.adddevice.view;

import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.common.base.utils.ToastUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.main.adddevice.adapter.RvAddDevice;
import com.zt.igreen.module.main.adddevice.contract.AddDeviceContract;
import com.zt.igreen.module.main.adddevice.model.AddDeviceBean;
import com.zt.igreen.module.main.adddevice.presenter.AddDevicePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.zt.igreen.module.main.adddevice.adapter.RvAddDevice.ITEM_TYPE_CHILD;

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
        ImmersionBar.with(this)
                .statusBarColor(R.color.qingse).statusBarDarkFont(true).flymeOSStatusBarFontColor(R.color.qingse).init();
        sethead(R.color.qingse);
        setColor(Color.parseColor("#ffffff"));
        setTitle("添加常用设备");
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
                if (sb.length() != 0) {
                   sb.setLength(sb.length() -1);
                }
                mPresenter.setAddDeviceData(sb.toString());
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
