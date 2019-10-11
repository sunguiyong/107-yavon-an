package com.zt.igreen.module.mall.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.igreen.R;
import com.zt.igreen.component.MyQuickAdapter;
import com.zt.igreen.module.data.FavoriteBean;

/**
 * Created by hp on 2017/11/15.
 */
public class FavoriteAdapter extends MyQuickAdapter<FavoriteBean,BaseViewHolder> {
    public FavoriteAdapter() {
        super(R.layout.item_device,null);
    }


    @Override
    protected void convert(BaseViewHolder helper, FavoriteBean data) {
            helper.setText(R.id.tv_device_name, data.getMaterial_name());
            Glide.with(mContext)
                    .load(data.getCover_image())
//                    .fitCenter()
                    .into((ImageView) helper.getView(R.id.iv_dev_type));
    }


}
