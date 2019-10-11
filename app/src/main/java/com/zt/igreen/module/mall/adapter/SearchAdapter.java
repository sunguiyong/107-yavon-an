package com.zt.igreen.module.mall.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.igreen.R;
import com.zt.igreen.component.MyQuickAdapter;
import com.zt.igreen.module.data.HotBean;
import com.zt.igreen.module.data.IntellListBean;

/**
 * Created by hp on 2017/11/15.
 */
public class SearchAdapter extends MyQuickAdapter<HotBean,BaseViewHolder> {
    public SearchAdapter() {
        super(R.layout.search_item,null);
    }
    @Override
    protected void convert(BaseViewHolder helper, HotBean data) {
            helper.setText(R.id.tv_count_apply, data.getName());
    }
}
