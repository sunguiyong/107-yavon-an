package com.zt.yavon.module.device.share.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.component.MyQuickAdapter;
import com.zt.yavon.module.data.ShareListBean;

/**
 * Created by hp on 2018/7/20.
 */

public class ShareSettingAdapter extends MyQuickAdapter<ShareListBean.User,BaseViewHolder> {
    private String type;
    public ShareSettingAdapter() {
        super(R.layout.item_user_share, null);
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return type;
    }
    @Override
    protected void convert(BaseViewHolder helper, ShareListBean.User item) {
        if("SECOND_USER".equals(type)){
            helper.setGone(R.id.layout_do_share,false);
        }else{
            helper.addOnClickListener(R.id.layout_do_share);
        }
        helper.setText(R.id.tv_name_share,item.nick_name+"\n"+item.mobile)
                .setText(R.id.tv_time_share,item.use_time);
    }
}
