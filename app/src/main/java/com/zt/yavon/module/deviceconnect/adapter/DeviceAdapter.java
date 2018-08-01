package com.zt.yavon.module.deviceconnect.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zt.yavon.R;
import com.zt.yavon.module.data.DevTypeBean;

/**
 * Created by hp on 2017/11/15.
 */
public class DeviceAdapter extends RecyclerArrayAdapter<DevTypeBean.TYPE> {
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

    public class MyViewHolder extends BaseViewHolder<DevTypeBean.TYPE> {
        TextView tvName;

        public MyViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_device);
            tvName = $(R.id.tv_device_name);
            tvName = $(R.id.iv_dev_type);

        }

        @Override
        public void setData(DevTypeBean.TYPE data) {
          tvName.setText(data.name);
        }
    }

}
