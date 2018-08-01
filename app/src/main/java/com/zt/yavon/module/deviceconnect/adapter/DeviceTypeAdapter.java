package com.zt.yavon.module.deviceconnect.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zt.yavon.R;
import com.zt.yavon.module.data.DevTypeBean;

/**
 * Created by hp on 2017/11/15.
 */
public class DeviceTypeAdapter extends RecyclerArrayAdapter<DevTypeBean> {
    private Context context;
    private int postion=0;
    public DeviceTypeAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public MyViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }


    public class MyViewHolder extends BaseViewHolder<DevTypeBean> {

        TextView tvName;
        View line;
        public MyViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_deice_type);
            tvName = $(R.id.tv_device_type);
            line = $(R.id.line);

        }

        @Override
        public void setData(DevTypeBean data) {
          tvName.setText(data.getName());
          if (postion==getDataPosition()){
              tvName.setSelected(true);
              line.setBackgroundResource(R.mipmap.iv_edittext_activate);
          }else {
              tvName.setSelected(false);
              line.setBackgroundResource(R.mipmap.iv_edittext_normal);
          }
        }
    }

}
