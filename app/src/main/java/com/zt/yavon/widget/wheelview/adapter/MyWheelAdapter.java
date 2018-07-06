package com.zt.yavon.widget.wheelview.adapter;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.base.utils.DensityUtil;


/**
 * Created by hp on 2017/8/30.
 */
public class MyWheelAdapter extends BaseWheelAdapter<Integer> {
    public static final int TYPE_SUBSCRIBE = 0;
    public static final int TYPE_INTERVAL = 1;
    private int type = TYPE_SUBSCRIBE;
    public MyWheelAdapter(int type){
        this.type = type;
    }
    @Override
    protected View bindView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(parent.getContext());
        int paddingTop = DensityUtil.dp2px(parent.getContext(),6);
        int paddingBottom = DensityUtil.dp2px(parent.getContext(),8);
        textView.setPadding(0,paddingTop,0,paddingBottom);
        textView.setText(getDataByPosition(position));
        textView.setGravity(Gravity.CENTER);
        textView.setSingleLine();
        return textView;
    }
    private String getDataByPosition(int position){
        int totalMin = mList.get(position)*15;
        switch (type){
            case TYPE_SUBSCRIBE:
                return totalMin/60+":"+totalMin%60;
            case TYPE_INTERVAL:
                return totalMin/60+"小时"+totalMin%60+"分钟";
        }
        return null;
    }
}
