package com.zt.yavon.module.main.roommanager.detail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.widget.RvBase;

public class RvRoomDevice extends RvBase<TabBean.MachineBean> {
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
    public void customConvert(BaseViewHolder holder, TabBean.MachineBean bean) {
        holder.setText(R.id.tv_name, bean.name)
                .setText(R.id.tv_status, bean.isPowerOn() ? "设备开启" : "设备关闭");
        Glide.with(getContext()).load(bean.icon).into((ImageView) holder.getView(R.id.iv_icon));
        holder.setOnClickListener(R.id.iv_del, new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.remove(holder.getLayoutPosition());
            }
        });
    }
}
