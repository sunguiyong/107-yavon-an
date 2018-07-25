package com.zt.yavon.module.main.roommanager.list.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.main.roommanager.list.model.RoomBean;
import com.zt.yavon.widget.RvBase;

public class RvRoom extends RvBase<RoomBean> {
    public RvRoom(Context context) {
        this(context, null);
    }

    public RvRoom(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RvRoom(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public LayoutManager customSetLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    @Override
    public int customSetItemLayoutId() {
        return R.layout.rv_room_item_layout;
    }

    @Override
    public void customConvert(BaseViewHolder holder, RoomBean bean) {
        holder.setText(R.id.tv_name, bean.mName)
                .setText(R.id.device_count, "设备：" + bean.mDeviceCount + "");
        TextView tvName = holder.getView(R.id.tv_name);
        tvName.setCompoundDrawablesWithIntrinsicBounds(bean.mResId, 0, 0, 0);
    }
}