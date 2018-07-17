package com.zt.yavon.module.deviceconnect.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zt.yavon.R;
import com.zt.yavon.module.data.DeviceBean;

import java.util.List;

/**
 * Created by hp on 2017/11/15.
 */
public class DeviceAdapter extends RecyclerArrayAdapter<DeviceBean> {
    private Context context;
    public DeviceAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public MyViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }


    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        super.OnBindViewHolder(holder, position);
    }

    public class MyViewHolder extends BaseViewHolder<DeviceBean> {
        TextView tvName;

        public MyViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_device);
            tvName = $(R.id.tv_device_name);

        }

        @Override
        public void setData(DeviceBean data) {
          tvName.setText(data.getDeviceName());
        }
    }

}
