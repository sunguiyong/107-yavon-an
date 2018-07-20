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
    public static final int TYPE_COUNT = 0;
    public static final int TYPE_DAY = 1;
    private int type = TYPE_COUNT;
    public MyWheelAdapter(){}
    public MyWheelAdapter(int type){
        this.type = type;
    }
    @Override
    protected View bindView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(parent.getContext());
        int paddingTop = DensityUtil.dp2px(parent.getContext(),8);
        int paddingBottom = DensityUtil.dp2px(parent.getContext(),8);
        textView.setPadding(0,paddingTop,0,paddingBottom);
        textView.setText(mList.get(position)+"");
        textView.setGravity(Gravity.CENTER);
        textView.setSingleLine();
        return textView;
    }


    private String getDataByPosition(int position,TextView textView){
        String item = mList.get(position)+"";
        if(position%mWheelSize == mWheelSize/2){
            String unit = "";
            switch (type){
                case TYPE_COUNT:
                    unit = "次";
                case TYPE_DAY:
                    unit = "天";
            }

            textView.setPadding((int) (textView.getTextSize()*item.length()),0,0,0);
            return item+unit;
        }else{
            textView.setPadding(0,0,0,0);
          return item+"";
        }
    }
}
