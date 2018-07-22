package com.zt.yavon.module.main.roommanager.detail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.main.frame.model.DeviceItemBean;
import com.zt.yavon.widget.RvBase;

public class RvRoomDevice extends RvBase<DeviceItemBean> {
    public RvRoomDevice(Context context) {
        super(context);
    }

    public RvRoomDevice(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RvRoomDevice(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public LayoutManager customSetLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    @Override
    public int customSetItemLayoutId() {
        return R.layout.rv_room_device_item_layout;
    }

    @Override
    public void customConvert(BaseViewHolder holder, DeviceItemBean bean) {
        holder.setText(R.id.tv_name, bean.mDeviceEnum.mName)
                .setText(R.id.tv_status, bean.mOnOffStatus ? "设备开启" : "设备关闭")
                .setImageResource(R.id.iv_icon, bean.mDeviceEnum.mResId);
        holder.setOnClickListener(R.id.iv_del, new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.remove(holder.getLayoutPosition());
            }
        });
    }
}
