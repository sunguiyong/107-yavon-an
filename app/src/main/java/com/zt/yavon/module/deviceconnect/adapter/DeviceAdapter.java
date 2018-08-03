package com.zt.yavon.module.deviceconnect.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.component.MyQuickAdapter;
import com.zt.yavon.module.data.DevTypeBean;

/**
 * Created by hp on 2017/11/15.
 */
public class DeviceAdapter extends MyQuickAdapter<DevTypeBean.TYPE,BaseViewHolder> {
    public DeviceAdapter() {
        super(R.layout.item_device,null);
    }


    @Override
    protected void convert(BaseViewHolder helper, DevTypeBean.TYPE data) {
            helper.setText(R.id.tv_device_name, data.name);
            Glide.with(mContext)
                    .load(data.icon)
//                    .fitCenter()
                    .into((ImageView) helper.getView(R.id.iv_dev_type));
    }


}
