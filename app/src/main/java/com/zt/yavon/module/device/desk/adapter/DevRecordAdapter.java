package com.zt.yavon.module.device.desk.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.component.MyBaseMultiItemQuickAdapter;
import com.zt.yavon.module.data.SectionItem;
import com.zt.yavon.module.data.UserRecordBean;

/**
 * Created by lifujun on 2018/7/11.
 */

public class DevRecordAdapter extends MyBaseMultiItemQuickAdapter<SectionItem,BaseViewHolder>{
    public DevRecordAdapter() {
        super(null);
        addItemType(SectionItem.TYPE_TITLE, R.layout.item_date_record);
        addItemType(SectionItem.TYPE_DETAIL,R.layout.item_detail_record);
    }


    @Override
    protected void convert(BaseViewHolder helper, SectionItem item) {
        UserRecordBean.RecordDetail bean = (UserRecordBean.RecordDetail) item.data;
        if(item.getItemType() == SectionItem.TYPE_DETAIL){
            helper.setText(R.id.tv_content_record,bean.time+bean.content);
            helper.setText(R.id.tv_from_record,"来自  "+bean.user_type);
            helper.setText(R.id.tv_phone_record,bean.mobile);
            if(bean.isFist){
                helper.setBackgroundRes(R.id.point_record_lock,R.drawable.round_point_record_b);
            }else{
                helper.setBackgroundRes(R.id.point_record_lock,R.drawable.round_point_record_w);
            }
        }else{
            if(!TextUtils.isEmpty(bean.date)){
                String[] titles = bean.date.split("-");
                if(titles != null && titles.length == 3){
                    helper.setText(R.id.tv_month_record,titles[1]+"月");
                    helper.setText(R.id.tv_day_record,titles[2]);
                }
            }
            helper.setText(R.id.tv_week_record,bean.week);
        }
    }
}
