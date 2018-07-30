package com.zt.yavon.widget.wheelview.adapter;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.base.utils.DensityUtil;


/**
 * Created by hp on 2017/8/30.
 */
public class MyWheelAdapter<T> extends BaseWheelAdapter<T> {
    public static final int TYPE_COUNT = 0;
    public static final int TYPE_DAY = 1;
    public static final int TYPE_MONTH = 2;
    public static final int TYPE_YEAR = 3;
    public static final int TYPE_MDH = 4;
    private int type = -1;
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
//        textView.setText(Html.fromHtml(getDataByPosition(position)));
        textView.setText(getDataByPosition(position));
        textView.setGravity(Gravity.CENTER);
        textView.setSingleLine();
        return textView;
    }


    private String getDataByPosition(int position){
        String item = mList.get(position)+"";
        String unit = "";
        switch (type){
            case TYPE_COUNT:
                unit = "次";
                break;
            case TYPE_DAY:
                unit = "天";
                break;
            case TYPE_MONTH:
                unit = "个月";
                break;
            case TYPE_YEAR:
                unit = "年";
                break;
            case TYPE_MDH:
                item = item.split("-")[1];
                break;
        }
        return item;
    }
}
