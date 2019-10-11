package com.zt.igreen.module.main.roommanager.add.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.igreen.R;
import com.zt.igreen.module.main.roommanager.add.model.RoomItemBean;
import com.zt.igreen.widget.RvBase;

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
        cb.setText(bean.name);
        ImageView iv = holder.getView(R.id.iv_icon);
        if (holder.getAdapterPosition() == mCheckedPosition && mCheckedPosition != mAdapter.getItemCount() - 1) {
            Glide.with(getContext()).load(bean.icon_select).into(iv);
            cb.setChecked(true);
        } else {
            if (holder.getAdapterPosition() == mAdapter.getItemCount() - 1) {
                Glide.with(getContext()).load(R.mipmap.ic_add).into(iv);
            } else {
                Glide.with(getContext()).load(bean.icon).into(iv);
            }
        }
    }

    public int mCheckedPosition = -1;

    public void onItemCheck(int position) {
        mCheckedPosition = position;
        mAdapter.notifyDataSetChanged();
    }
}
