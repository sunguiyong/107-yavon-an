package com.zt.yavon.module.message.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.component.MyQuickAdapter;
import com.zt.yavon.module.data.MsgBean;
import com.zt.yavon.module.message.view.MessageListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lifujun on 2018/7/18.
 */

public class MsgListAdapter extends MyQuickAdapter<MsgBean,BaseViewHolder> {
    private boolean isSelectMode;
    private int type;
    public MsgListAdapter(int type) {
        super(R.layout.item_message, null);
        this.type = type;
    }

    public void setSelectMode(boolean selectMode) {
        isSelectMode = selectMode;
        if(!isSelectMode){
            for(MsgBean bean:getData()){
                bean.setSelect(false);
            }
            notifyDataSetChanged();
        }
    }

    public boolean isSelectMode() {
        return isSelectMode;
    }
    public List<MsgBean> getSelectItems(){
        List<MsgBean> ids = new ArrayList<>();
        for(MsgBean bean:getData()){
            if(bean.isSelect()){
                ids.add(bean);
            }
        }
        return ids;
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgBean item) {
        View ivSelect = helper.getView(R.id.iv_select);
        if(isSelectMode){
            ivSelect.setVisibility(View.VISIBLE);
            ivSelect.setSelected(item.isSelect());
        }else{
            ivSelect.setVisibility(View.GONE);
        }
        helper.setGone(R.id.iv_select,isSelectMode?true:false);
        helper.setGone(R.id.iv_red_point,item.isIs_read()?false:true);
        if(type == MessageListActivity.TYPE_SYS){
            helper.setText(R.id.tv_title,item.getTitle());
            helper.setText(R.id.tv_content,item.getContent());
            helper.setText(R.id.tv_time,item.getTime());
            helper.setImageResource(R.id.iv_type,R.mipmap.msg_system2);
        }else if(type == MessageListActivity.TYPE_ERROR){
            helper.setText(R.id.tv_title,item.getTitle());
            helper.setText(R.id.tv_content,item.getContent());
            helper.setText(R.id.tv_time,item.getTime());
            helper.setImageResource(R.id.iv_type,R.mipmap.msg_error2);
            helper.setText(R.id.tv_time,"2天前");
        }else if(type == MessageListActivity.TYPE_SHARE){
            helper.setText(R.id.tv_title,"共享消息");
            helper.setText(R.id.tv_content,"分享可以抽奖，一起分享吧！");
            helper.setImageResource(R.id.iv_type,R.mipmap.lock1_small);
            helper.setText(R.id.tv_time,"2018/07/18");
        }else if(type == MessageListActivity.TYPE_INTERNAL){
            helper.setText(R.id.tv_title,item.getTitle());
            helper.setText(R.id.tv_content,item.getContent());
            helper.setImageResource(R.id.iv_type,R.mipmap.msg_internal);
            helper.setText(R.id.tv_time,item.getTime());
        }
    }
}
