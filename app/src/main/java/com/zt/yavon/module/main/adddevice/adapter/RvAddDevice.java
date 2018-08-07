package com.zt.yavon.module.main.adddevice.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zt.yavon.R;
import com.zt.yavon.module.main.adddevice.model.AddDeviceBean;
import com.zt.yavon.widget.RvBaseExpandable;

import java.util.List;

public class RvAddDevice extends RvBaseExpandable<MultiItemEntity> {

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
    public void customConvert(BaseViewHolder holder, Object bean) {
        CheckBox checkBox = holder.getView(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(false);
        if (holder.getItemViewType() == ITEM_TYPE_GROUP) {
            AddDeviceBean item = (AddDeviceBean) bean;
            holder.setText(R.id.tv_name, ((AddDeviceBean) bean).name);
            boolean isAllOften = true;
            List<AddDeviceBean.MachineBean> childs = item.getSubItems();
            if (childs != null) {
                for (AddDeviceBean.MachineBean childItem : childs) {
                    if (!childItem.is_often) {
                        isAllOften = false;
                        break;
                    }
                }
            } else {
                isAllOften = false;
            }
            checkBox.setChecked(isAllOften);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    item.is_all_often = isChecked;
                    for (AddDeviceBean.MachineBean childItem : item.machines) {
                        childItem.is_often = isChecked;
                    }
                    mAdapter.notifyDataSetChanged();
                }
            });
        } else if (holder.getItemViewType() == ITEM_TYPE_CHILD) {
            AddDeviceBean.MachineBean item = (AddDeviceBean.MachineBean) bean;
            holder.setText(R.id.tv_name, item.machine_name)
                    .setText(R.id.tv_status, item.getStatus());
            checkBox.setChecked(item.is_often);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    item.is_often = isChecked;
                    mAdapter.notifyDataSetChanged();
                }
            });
            ImageView ivIcon = holder.getView(R.id.iv_icon);
            Glide.with(getContext()).load(((AddDeviceBean.MachineBean) bean).machine_icon).into(ivIcon);
        } else {
            throw new IllegalStateException("没有此ItemType ");
        }
    }
}
