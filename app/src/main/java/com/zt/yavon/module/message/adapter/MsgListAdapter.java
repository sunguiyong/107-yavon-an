package com.zt.yavon.module.message.adapter;

import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.message.view.MessageListActivity;

/**
 * Created by lifujun on 2018/7/18.
 */

public class MsgListAdapter extends BaseQuickAdapter<Object,BaseViewHolder>{
    private boolean isSelectMode;
    private int type;
    public MsgListAdapter(int type) {
        super(R.layout.item_message, null);
        this.type = type;
    }

    public void setSelectMode(boolean selectMode) {
        isSelectMode = selectMode;
    }

    public boolean isSelectMode() {
        return isSelectMode;
    }


    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        int position = helper.getAdapterPosition();
        helper.setGone(R.id.iv_select,isSelectMode?true:false);
        if(type == MessageListActivity.TYPE_SYS){
            helper.setText(R.id.tv_title,"系统消息");
            helper.setText(R.id.tv_content,"这是一条系统消息");
            helper.setText(R.id.tv_time,"5天前");
            helper.setImageResource(R.id.iv_type,R.mipmap.msg_system2);
        }else if(type == MessageListActivity.TYPE_ERROR){
            helper.setText(R.id.tv_title,"故障消息");
            helper.setText(R.id.tv_content,"您的小桌子坏了");
            helper.setImageResource(R.id.iv_type,R.mipmap.msg_error2);
            helper.setText(R.id.tv_time,"2天前");
        }else{
            helper.setText(R.id.tv_title,"共享消息");
            helper.setText(R.id.tv_content,"分享可以抽奖，一起分享吧！");
            helper.setImageResource(R.id.iv_type,R.mipmap.lock1_small);
            helper.setText(R.id.tv_time,"2018/07/18");
        }
    }
}
