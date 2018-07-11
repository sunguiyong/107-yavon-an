package com.zt.yavon.module.device.lock.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.data.LockRecordItem;

/**
 * Created by lifujun on 2018/7/11.
 */

public class LockRecordAdapter extends BaseMultiItemQuickAdapter<LockRecordItem,BaseViewHolder>{
    public LockRecordAdapter() {
        super(null);
        addItemType(LockRecordItem.TYPE_TITLE, R.layout.item_date_record);
        addItemType(LockRecordItem.TYPE_DETAIL,R.layout.item_detail_record);
    }
    @Override
    protected void convert(BaseViewHolder helper, LockRecordItem item) {
        if(item.getItemType() == LockRecordItem.TYPE_DETAIL){
            if(helper.getAdapterPosition() % 5 == 1){
                helper.setBackgroundRes(R.id.point_record_lock,R.drawable.round_point_record_b);
            }
        }
    }
}
