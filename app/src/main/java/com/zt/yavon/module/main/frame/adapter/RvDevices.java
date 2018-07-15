package com.zt.yavon.module.main.frame.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.main.frame.model.DeviceItemBean;
import com.zt.yavon.widget.RvBase;

public class RvDevices extends RvBase<DeviceItemBean> {
    public RvDevices(Context context) {
        super(context);
    }

    public RvDevices(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RvDevices(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public LayoutManager customSetLayoutManager(Context context) {
        return new GridLayoutManager(context, 2);
    }

    @Override
    public int customSetItemLayoutId() {
        return R.layout.rv_device_item_layout;
    }

    @Override
    public void customConvert(BaseViewHolder holder, DeviceItemBean bean) {
        holder.setVisible(R.id.tv_location, bean.mDeviceEnum != null)
                .setChecked(R.id.cb_power, false)
                .setGone(R.id.cb_power, bean.mDeviceEnum != null)
                .setGone(R.id.tv_status, bean.mDeviceEnum != null);
        holder.setText(R.id.tv_name, bean.mDeviceEnum == null ? "添加设备" : bean.mDeviceEnum.mName)
                .setText(R.id.tv_status, bean.mDeviceEnum == null ? "" : (bean.mOnOffStatus ? "设备开启" : "设备关闭"))
                .setText(R.id.tv_location, bean.mDeviceEnum == null ? "" : bean.mLocation)
                .setChecked(R.id.cb_power, bean.mOnOffStatus)
                .setImageResource(R.id.iv_icon, bean.mDeviceEnum == null ? R.mipmap.ic_item_add : bean.mDeviceEnum.mResId)
                .setOnCheckedChangeListener(R.id.cb_power, new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    }
                });
    }
}
