package com.zt.igreen.module.deviceconnect.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.igreen.R;
import com.zt.igreen.component.MyQuickAdapter;
import com.zt.igreen.module.data.DevTypeBean;

/**
 * Created by hp on 2017/11/15.
 */
public class DeviceTypeAdapter extends MyQuickAdapter<DevTypeBean, BaseViewHolder> {
    private int postion = 0;

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
//        Log.e("xuxinyi", data.getName());
        if (postion == helper.getAdapterPosition()) {
            Log.d("position--view", postion + "");
            tvName.setSelected(true);
            tvName.setBackgroundResource(R.mipmap.adddevice_itembg);
//            helper.setBackgroundColor(R.id.lin_back, Color.parseColor("#ffffff"));
            // helper.setBackgroundRes(R.id.line,R.mipmap.iv_edittext_activate);
        } else {
            tvName.setSelected(false);
            tvName.setBackgroundResource(R.color.touming);
//            helper.setBackgroundColor(R.id.lin_back, Color.parseColor("#F4F3F3"));
//            helper.setBackgroundRes(R.id.line,R.mipmap.iv_edittext_normal);
        }
    }
}
