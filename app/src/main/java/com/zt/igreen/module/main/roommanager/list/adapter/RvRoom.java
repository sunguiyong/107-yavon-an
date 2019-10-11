package com.zt.igreen.module.main.roommanager.list.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.igreen.R;
import com.zt.igreen.module.data.TabBean;
import com.zt.igreen.widget.RvBase;

public class RvRoom extends RvBase<TabBean> {
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
    public void customConvert(BaseViewHolder holder, TabBean bean) {
        holder.setText(R.id.tv_name, bean.name)
                .setText(R.id.device_count, "设备：" + bean.getMachineSize() + "");
        TextView tvName = holder.getView(R.id.tv_name);
        Glide.with(getContext()).load(bean.icon_select).asBitmap().
                into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        BitmapDrawable drawable = new BitmapDrawable(getContext().getResources(), resource);
                        drawable.setBounds(0, 0, 60, 60);
                        tvName.setCompoundDrawables(drawable, null, null, null);
                    }
                });
    }
}
