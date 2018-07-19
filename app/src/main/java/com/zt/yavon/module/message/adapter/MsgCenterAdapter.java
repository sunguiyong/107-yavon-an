package com.zt.yavon.module.message.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;

/**
 * Created by lifujun on 2018/7/18.
 */

public class MsgCenterAdapter extends BaseQuickAdapter<Object,BaseViewHolder>{
    public MsgCenterAdapter() {
        super(R.layout.item_message_center, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        int position = helper.getAdapterPosition();
        if(position == 0){
            helper.setText(R.id.tv_title,"系统消息");
            helper.setText(R.id.tv_content,"这是一条系统消息");
            helper.setImageResource(R.id.iv_type,R.mipmap.msg_system);
            helper.setText(R.id.tv_count_msg,"2");
        }else if(position == 1){
            helper.setText(R.id.tv_title,"故障消息");
            helper.setText(R.id.tv_content,"您的小桌子坏了");
            helper.setImageResource(R.id.iv_type,R.mipmap.msg_error);
            helper.setGone(R.id.tv_count_msg,false);
        }else{
            helper.setText(R.id.tv_title,"共享消息");
            helper.setText(R.id.tv_content,"分享可以抽奖，一起分享吧！");
            helper.setImageResource(R.id.iv_type,R.mipmap.msg_share);
            helper.setText(R.id.tv_count_msg,"6");
        }
    }
}
