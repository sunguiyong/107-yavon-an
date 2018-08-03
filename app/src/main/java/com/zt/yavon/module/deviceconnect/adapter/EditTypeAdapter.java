package com.zt.yavon.module.deviceconnect.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.data.CatogrieBean;

/**
 * Created by hp on 2018/7/20.
 */

public class EditTypeAdapter extends BaseQuickAdapter<CatogrieBean,BaseViewHolder>{
    private int selectedPosition = 0;
    public EditTypeAdapter() {
        super(R.layout.item_type_dev, null);
    }
    public void setSelectedPosition(int position){
        selectedPosition = position;
        notifyDataSetChanged();
    }
    public CatogrieBean getSelectItem(){
        return getItem(selectedPosition);
    }
    @Override
    protected void convert(BaseViewHolder helper, CatogrieBean item) {
        Glide.with(mContext)
                .load(item.getIcon())
//                .fitCenter()
                .into((ImageView) helper.getView(R.id.iv_dev_type));
        if(selectedPosition == helper.getAdapterPosition()){
            helper.getView(R.id.iv_select_type).setSelected(true);
        }else{
            helper.getView(R.id.iv_select_type).setSelected(false);
        }
    }
}
