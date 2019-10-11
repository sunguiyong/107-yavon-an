package com.zt.igreen.module.Intelligence.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.igreen.R;
import com.zt.igreen.component.MyQuickAdapter;

import java.util.List;

/**
 * Created by hp on 2017/11/15.
 */
public class IntellSetAdapter extends MyQuickAdapter<String,BaseViewHolder> {
    public IntellSetAdapter(List<String > list) {
        super(R.layout.canshu_item,list);
    }


    @Override
    protected void convert(BaseViewHolder helper, String data) {
            helper.setText(R.id.tv_context, data);

    }


}
