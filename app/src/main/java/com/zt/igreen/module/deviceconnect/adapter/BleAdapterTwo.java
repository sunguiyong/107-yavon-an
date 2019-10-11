package com.zt.igreen.module.deviceconnect.adapter;

import android.bluetooth.BluetoothDevice;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmjk.qmjkcloud.entity.BLEDeviceBean;
import com.zt.igreen.R;
import com.zt.igreen.component.MyQuickAdapter;
import com.zt.igreen.module.data.DevTypeBean;

/**
 * Created by hp on 2017/11/15.
 */
public class BleAdapterTwo extends MyQuickAdapter<BLEDeviceBean,BaseViewHolder> {
    public BleAdapterTwo() {
        super(R.layout.blue_item,null);
    }


    @Override
    protected void convert(BaseViewHolder helper, BLEDeviceBean data) {
            helper.setText(R.id.tv_name,data.getDevice().getName());
            helper.addOnClickListener(R.id.tv_lianjie);
    }


}
