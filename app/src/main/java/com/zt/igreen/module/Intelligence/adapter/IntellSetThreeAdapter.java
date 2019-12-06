package com.zt.igreen.module.Intelligence.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.igreen.R;
import com.zt.igreen.component.MyQuickAdapter;
import com.zt.igreen.module.data.DeviceINtell_Info;
import com.zt.igreen.module.data.IntellDetails;
import com.zt.igreen.module.data.StatusIntellBean;

import java.util.List;

/**
 * Created by hp on 2017/11/15.
 */
public class IntellSetThreeAdapter extends MyQuickAdapter<DeviceINtell_Info, BaseViewHolder> {
    Context mActivity;
    String strjson;

    public IntellSetThreeAdapter(Context context) {
        super(R.layout.rv_health_item_layout, null);
        mActivity = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceINtell_Info data) {
        helper.setText(R.id.tv_location, data.getRoom_name())
                .setText(R.id.tv_name, data.getDevice_name());
        /*.setText(R.id.tv_state,data.getStatus());*/
        ImageView ivicon = helper.getView(R.id.iv_icon);
        Glide.with(mActivity).load(data.getDevice_img()).into(ivicon);
        TextView tvstatee = helper.getView(R.id.tv_state);
        TextView tvlocation = helper.getView(R.id.tv_location);
        LinearLayout ll = helper.getView(R.id.ll_center);
        String name = data.getDevice_state();
        if ("ON".equals(name)) {
            strjson = "开启";
        } else if ("OFF".equals(name)) {
            strjson = "关闭";
        } else if ("SOCKET_ON".equals(name)) {
            strjson = "插座开启";
        } else if ("SOCKET_OFF".equals(name)) {
            strjson = "插座关闭";
        } else if ("CUSTOM_0".equals(name)) {
            strjson = "自定义1";
        } else if ("CUSTOM_1".equals(name)) {
            strjson = "自定义2";
        } else if ("CUSTOM_2".equals(name)) {
            strjson = "自定义3";
        } else if ("CUSTOM_3".equals(name)) {
            strjson = "自定义4";
        } else if ("CUSTOM_4".equals(name)) {
            strjson = "自定义5";
        } else if ("BOOT_UP".equals(name)) {
            strjson = "开机";
        } else if ("SHUTDOWN".equals(name)) {
            strjson = "关机";
        } else if ("SMART".equals(name)) {
            strjson = "智能";
        } else if ("LOW".equals(name)) {
            strjson = "一档";
        } else if ("MID".equals(name)) {
            strjson = "二档";
        } else if ("HIGH".equals(name)) {
            strjson = "三档";
        } else if ("REFRIGERATION".equals(name)) {
            strjson = "制冷";
        } else if ("HEATING".equals(name)) {
            strjson = "制热";
        } else {
            strjson = name;
        }
        tvstatee.setText(strjson);
        tvlocation.setBackgroundColor(mActivity.getResources().getColor(R.color.bangongshi));
        ll.setBackgroundColor(mActivity.getResources().getColor(R.color.officebg));
    }

    public void setItemSelect(int position, String isSelected) {
        View view = getViewByPosition(getRecyclerView(), position, R.id.tv_state);
        TextView textView = view.findViewById(R.id.tv_state);
        textView.setText(isSelected);
        notifyDataSetChanged();
    }

}
