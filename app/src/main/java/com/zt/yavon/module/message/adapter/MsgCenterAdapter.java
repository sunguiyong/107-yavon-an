package com.zt.yavon.module.message.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.data.MsgBean;

/**
 * Created by lifujun on 2018/7/18.
 */

public class MsgCenterAdapter extends BaseQuickAdapter<MsgBean,BaseViewHolder>{
    public final String TYPE_SYSTEM = "SYSTEM";
    public final String TYPE_FAULT = "FAULT";
    public final String TYPE_SHARE = "SHARE";
    public MsgCenterAdapter() {
        super(R.layout.item_message_center, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgBean bean) {
        if(TYPE_SYSTEM.equals(bean.getType())){
            helper.setImageResource(R.id.iv_type,R.mipmap.msg_system);
        }else if(TYPE_FAULT.equals(bean.getType())){
            helper.setImageResource(R.id.iv_type,R.mipmap.msg_error);
        }else if(TYPE_SHARE.equals(bean.getType())){
            helper.setImageResource(R.id.iv_type,R.mipmap.msg_share);
        }
        helper.setText(R.id.tv_title,bean.getTitle());
        helper.setText(R.id.tv_content,bean.getContent());
        if(bean.getNew_count() > 0){
            helper.setGone(R.id.tv_count_msg,true);
            helper.setText(R.id.tv_count_msg,bean.getNew_count()+"");
        }else{
            helper.setGone(R.id.tv_count_msg,false);
        }
    }
}
