package com.zt.yavon.module.main.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.data.DemoBean;

/**
 * Created by hp on 2018/6/12.
 */

public class MainAdapter  extends BaseQuickAdapter<DemoBean,BaseViewHolder> {
    public MainAdapter() {
        super(R.layout.item_demo);
    }

    @Override
    protected void convert(BaseViewHolder helper, DemoBean bean) {
//        int position = helper.getAdapterPosition();
        helper.addOnClickListener(R.id.tv_demo);
        helper.setText(R.id.tv_demo, bean.getDemoString());
    }

}
