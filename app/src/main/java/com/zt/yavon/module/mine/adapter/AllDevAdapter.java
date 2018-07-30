package com.zt.yavon.module.mine.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.data.MineRoomBean;
import com.zt.yavon.module.data.SectionItem;

/**
 * Created by lifujun on 2018/7/11.
 */

public class AllDevAdapter extends BaseMultiItemQuickAdapter<SectionItem,BaseViewHolder>{
    public AllDevAdapter() {
        super(null);
        addItemType(SectionItem.TYPE_TITLE, R.layout.item_dev_room);
        addItemType(SectionItem.TYPE_DETAIL,R.layout.item_dev_all);
    }
    @Override
    protected void convert(BaseViewHolder helper, SectionItem item) {
        if(item.getItemType() == SectionItem.TYPE_DETAIL){
            MineRoomBean.Machine bean = (MineRoomBean.Machine)item.getData();
            Glide.with(mContext)
                    .load(bean.getMachine_icon())
                    .centerCrop()
                    .into((ImageView) helper.getView(R.id.iv_dev));
            helper.setText(R.id.tv_name_dev,bean.getMachine_name());
            TextView tvSetting = helper.getView(R.id.tv_setting_dev_item);
            String userType = "";
            if("ADMIN".equals(bean.getUser_type())){
                userType = "管理员";
                tvSetting.setText("共享设置");
                tvSetting.setBackgroundResource(R.drawable.shape_edittext_bk2);
                tvSetting.setTextColor(ContextCompat.getColor(mContext,R.color.mainGreen));
            }else{
                userType = "使用者";
                if("NEED".equals(bean.getExpire_type())){
                    tvSetting.setText("按需");
                }else if("FOREVER".equals(bean.getExpire_type())){
                    tvSetting.setText("永久");
                }else{
                    tvSetting.setText("18/07/30-25/06/18");
                }
                tvSetting.setBackground(null);
                tvSetting.setTextColor(ContextCompat.getColor(mContext,R.color.white));
            }
            helper.setText(R.id.tv_type_user,userType);
        }else{
            helper.setText(R.id.tv_name_room,(String)item.getData());
        }
    }
}
