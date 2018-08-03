package com.zt.yavon.module.deviceconnect.adapter;

import android.content.Context;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.component.MyQuickAdapter;
import com.zt.yavon.module.data.DevTypeBean;

/**
 * Created by hp on 2017/11/15.
 */
public class DeviceTypeAdapter extends MyQuickAdapter<DevTypeBean, BaseViewHolder> {
    private int postion=0;
    public DeviceTypeAdapter(Context context) {
        super(R.layout.item_deice_type);
    }


    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }
    @Override
    protected void convert(BaseViewHolder helper, DevTypeBean data) {
        TextView tvName = helper.getView(R.id.tv_device_type);
        tvName.setText(data.getName());
        if (postion==helper.getAdapterPosition()){
            tvName.setSelected(true);
            helper.setBackgroundRes(R.id.line,R.mipmap.iv_edittext_activate);
        }else {
            tvName.setSelected(false);
            helper.setBackgroundRes(R.id.line,R.mipmap.iv_edittext_normal);
        }
    }
}
