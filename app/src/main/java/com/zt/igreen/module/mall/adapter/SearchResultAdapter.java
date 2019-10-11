package com.zt.igreen.module.mall.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.igreen.R;
import com.zt.igreen.component.MyQuickAdapter;
import com.zt.igreen.module.data.IntellListBean;
import com.zt.igreen.module.data.SearchResultBean;

import java.util.List;

/**
 * Created by hp on 2017/11/15.
 */
public class SearchResultAdapter extends MyQuickAdapter<SearchResultBean,BaseViewHolder> {
    public SearchResultAdapter() {
        super(R.layout.item_device, null);
    }
    @Override
    protected void convert(BaseViewHolder helper, SearchResultBean  data) {

                helper.setText(R.id.tv_device_name, data.getName());
                Glide.with(mContext)
                     .load(data.getCover_image())
                     .into((ImageView) helper.getView(R.id.iv_dev_type));

    }
}
