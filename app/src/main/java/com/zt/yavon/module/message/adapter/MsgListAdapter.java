package com.zt.yavon.module.message.adapter;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

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
        helper.setGone(R.id.iv_red_point,item.isIs_read()?false:true);
        if(type == MessageListActivity.TYPE_SYS){
            helper.setGone(R.id.tv_do_msg,false);
            helper.setText(R.id.tv_title,item.getContent());
            helper.setText(R.id.tv_content,item.getTime());
//            helper.setText(R.id.tv_time,item.getTime());
            helper.setImageResource(R.id.iv_type,R.mipmap.msg_system2);
        }else if(type == MessageListActivity.TYPE_ERROR){
            helper.setText(R.id.tv_title,item.getContent());
            helper.setText(R.id.tv_content,item.getTime());
//            helper.setText(R.id.tv_time,item.getTime());
            helper.setImageResource(R.id.iv_type,R.mipmap.msg_error2);
            TextView tvDo = helper.getView(R.id.tv_do_msg);
            tvDo.setVisibility(View.VISIBLE);
            if("PROGRESS".equals(item.getStatus())){
                tvDo.setBackgroundResource(R.drawable.shape_edittext_bk2);
                tvDo.setTextColor(ContextCompat.getColor(mContext,R.color.mainGreen));
                tvDo.setText("进行中");
                helper.addOnClickListener(R.id.tv_do_msg);
            }else{
                tvDo.setText("已解决");
                tvDo.setBackground(null);
                tvDo.setTextColor(ContextCompat.getColor(mContext,R.color.white_tran));
                tvDo.setOnClickListener(null);
            }
        }else if(type == MessageListActivity.TYPE_SHARE){
            helper.setText(R.id.tv_title,item.getContent());
            if(item.isIs_read()){
                helper.setTextColor(R.id.tv_title,ContextCompat.getColor(mContext,R.color.white_tran));
            }else {
                helper.setTextColor(R.id.tv_title,ContextCompat.getColor(mContext,R.color.white));
            }
            helper.setText(R.id.tv_content,item.getTime()+"  "+item.getMachine_name());
            helper.setGone(R.id.layout_icon,false);
//            helper.setImageResource(R.id.iv_type,R.mipmap.lock1_small);
//            helper.setText(R.id.tv_time,"2018/07/18");
            TextView tvDo = helper.getView(R.id.tv_do_msg);
            tvDo.setVisibility(View.VISIBLE);
            if("APPLY".equals(item.getType()) && item.isIs_operate()){
                tvDo.setBackgroundResource(R.drawable.shape_edittext_bk2);
                tvDo.setTextColor(ContextCompat.getColor(mContext,R.color.mainGreen));
//                tvDo.setText("待授权");
                helper.addOnClickListener(R.id.tv_do_msg);
            }else{
//                tvDo.setText("已解决");
                tvDo.setBackground(null);
                tvDo.setTextColor(ContextCompat.getColor(mContext,R.color.white_tran));
                tvDo.setOnClickListener(null);
            }
            if("PASSED".equals(item.getStatus())){
                tvDo.setText("已同意");
            }else if("REFUSED".equals(item.getStatus())){
                tvDo.setText("已拒绝");
            }else if("WAIT".equals(item.getStatus())){
                tvDo.setText("待授权");
            }else{
                tvDo.setText("");
            }
        }else if(type == MessageListActivity.TYPE_INTERNAL){
            helper.setText(R.id.tv_title,item.getTitle());
//            helper.setText(R.id.tv_content,item.getContent());
            helper.setText(R.id.tv_content,item.getTime());
            helper.setImageResource(R.id.iv_type,R.mipmap.msg_internal);
            helper.setText(R.id.tv_time,"");
            helper.setGone(R.id.tv_do_msg,false);
        }
    }
}
