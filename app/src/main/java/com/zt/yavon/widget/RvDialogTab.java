package com.zt.yavon.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.main.frame.model.TabItemBean;

public class RvDialogTab extends RvBase<TabItemBean> {
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
    public void customConvert(BaseViewHolder holder, TabItemBean bean) {
        CheckBox checkBox = holder.getView(R.id.checkbox);
        checkBox.setChecked(mSelectItem == holder.getLayoutPosition());
        // checkBox.setCompoundDrawablesWithIntrinsicBounds(mSelectItem == holder.getLayoutPosition() ? bean.mSelectResId : bean.mUnSelectResId, 0, 0, 0);
        checkBox.setCompoundDrawablesWithIntrinsicBounds(bean.mSelectResId, 0, 0, 0);
        holder.setText(R.id.checkbox, bean.mTitle);
    }

    private int mSelectItem = -1;

    public void setSelection(int position) {
        mSelectItem = position;
        mAdapter.notifyDataSetChanged();
    }
}
