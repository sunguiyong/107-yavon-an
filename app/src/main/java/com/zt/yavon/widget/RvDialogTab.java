package com.zt.yavon.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.data.TabBean;

public class RvDialogTab extends RvBase<TabBean> {
    public RvDialogTab(Context context) {
        super(context);
    }

    public RvDialogTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RvDialogTab(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public LayoutManager customSetLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    @Override
    public int customSetItemLayoutId() {
        return R.layout.rv_dialog_tab_item_layout;
    }

    @Override
    public void customConvert(BaseViewHolder holder, TabBean bean) {
        CheckBox checkBox = holder.getView(R.id.checkbox);
        checkBox.setChecked(mSelectItem == holder.getAdapterPosition());
        String resUrl = (mSelectItem == holder.getAdapterPosition() ? bean.icon_select : bean.icon);
        Glide.with(getContext()).load(resUrl).asBitmap().
                into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        BitmapDrawable drawable = new BitmapDrawable(getContext().getResources(), resource);
                        /// 这一步必须要做,否则不会显示.                  drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                        checkBox.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    }
                });
        holder.setText(R.id.checkbox, bean.name);
    }

    private int mSelectItem = -1;

    public void setSelection(int position) {
        mSelectItem = position;
        mAdapter.notifyDataSetChanged();
    }
    public int getSelectPosition(){
        return mSelectItem;
    }
}
