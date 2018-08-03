package com.zt.yavon.module.main.adddevice.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.SparseIntArray;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.main.adddevice.model.AddDeviceBean;
import com.zt.yavon.widget.RvBaseMulti;

public class RvAddDevice extends RvBaseMulti<AddDeviceBean> {

    public static final int ITEM_TYPE_GROUP = 0;
    public static final int ITEM_TYPE_CHILD = 1;

    public RvAddDevice(Context context) {
        super(context);
    }

    public RvAddDevice(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RvAddDevice(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public LayoutManager customSetLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    @Override
    public SparseIntArray customSetItemLayoutIds() {
        SparseIntArray layouts = new SparseIntArray();
        layouts.put(ITEM_TYPE_GROUP, R.layout.rv_add_device_item_group_layout);
        layouts.put(ITEM_TYPE_CHILD, R.layout.rv_add_device_item_child_layout);
        return layouts;
    }

    @Override
    public void customConvert(BaseViewHolder holder, AddDeviceBean bean) {
        if (bean.getItemType() == ITEM_TYPE_GROUP) {
        } else if (bean.getItemType() == ITEM_TYPE_CHILD) {
        } else {
            throw new IllegalStateException("没有此ItemType " + bean.getItemType());
        }
    }
}
