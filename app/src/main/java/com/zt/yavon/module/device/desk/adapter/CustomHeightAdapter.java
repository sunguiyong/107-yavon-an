package com.zt.yavon.module.device.desk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zt.yavon.R;
import com.zt.yavon.module.data.CustomHeightBean;
import com.zt.yavon.module.data.DeskBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lifujun on 2018/7/12.
 */

public class CustomHeightAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private List<DeskBean> list ;
    public CustomHeightAdapter(Context context, List<DeskBean> defaultList){
        this.context = context;
        inflater = LayoutInflater.from(context);
        if(defaultList == null){
            list = new ArrayList<>();
        }else{
            list = defaultList;
        }
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public DeskBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if(view == null){
            view = inflater.inflate(R.layout.item_grid_height_custom,null);
            vh = new ViewHolder();
            vh.tv = (TextView)view.findViewById(R.id.tv_grid_height);
            view.setTag(vh);
        }else{
           vh = (ViewHolder) view.getTag();
        }
        vh.tv.setText("自定义"+(i+1));
        vh.tv.setSelected(list.get(i).isSelect());
        return view;
    }
    class ViewHolder{
        TextView tv;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
