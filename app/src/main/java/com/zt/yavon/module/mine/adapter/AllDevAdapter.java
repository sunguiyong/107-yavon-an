package com.zt.yavon.module.mine.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.data.LockRecordItem;

/**
 * Created by lifujun on 2018/7/11.
 */

public class AllDevAdapter extends BaseMultiItemQuickAdapter<LockRecordItem,BaseViewHolder>{
    public AllDevAdapter() {
        super(null);
        addItemType(LockRecordItem.TYPE_TITLE, R.layout.item_dev_room);
        addItemType(LockRecordItem.TYPE_DETAIL,R.layout.item_dev_all);
    }
    @Override
    protected void convert(BaseViewHolder helper, LockRecordItem item) {
        if(item.getItemType() == LockRecordItem.TYPE_DETAIL){
            if(helper.getAdapterPosition() % 5 == 1){
                helper.setBackgroundRes(R.id.tv_setting_dev_item,R.drawable.shape_edittext_bk2)
                .setTextColor(R.id.tv_setting_dev_item, ContextCompat.getColor(mContext,R.color.mainGreen));
            }
        }else{
            helper.setText(R.id.tv_name_room,"房间"+helper.getAdapterPosition());
        }
    }
}
