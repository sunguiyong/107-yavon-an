package com.zt.yavon.module.main.roommanager.add.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.main.roommanager.add.model.RoomItemBean;
import com.zt.yavon.widget.RvBase;

/**
 * Author: Administrator
 * Date: 2018/7/17
 */
public class RvRoomAdd extends RvBase<RoomItemBean> {
    public RvRoomAdd(Context context) {
        super(context);
    }

    public RvRoomAdd(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RvRoomAdd(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public LayoutManager customSetLayoutManager(Context context) {
        return new GridLayoutManager(context, 4);
    }

    @Override
    public int customSetItemLayoutId() {
        return R.layout.rv_room_add_layout;
    }

    @Override
    public void customConvert(BaseViewHolder holder, RoomItemBean bean) {
        CheckBox cb = holder.getView(R.id.cb_room_item);
        cb.setChecked(false);
        cb.setCompoundDrawablesWithIntrinsicBounds(0, bean.mResId, 0, 0);
    }
}
