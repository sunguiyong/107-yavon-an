package com.zt.igreen.module.device.health.adapter;




import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.igreen.R;
import com.zt.igreen.module.data.HistoryBean;
import com.zt.igreen.module.data.MoreBean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryApater extends BaseQuickAdapter<HistoryBean,BaseViewHolder> {
    String name;
    public HistoryApater(String name) {
        super(R.layout.history_item);
        this.name=name;
    }
    @Override
    protected void convert(BaseViewHolder helper, HistoryBean item) {
        String date=item.getDate();
        long l=System.currentTimeMillis();
        Date datetime=new Date(l);
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String nyr = dateFormat.format(datetime);
        if (date.length()>10){
          String subdate=date.substring(0,10);
            if (nyr.equals(subdate)){
                helper.setText(R.id.tv_date,"今天");
            }else {
               helper.setText(R.id.tv_date,subdate);}
          String time=date.substring(10,date.length());
            helper.setText(R.id.tv_time,time);
        }else {
         if (nyr.equals(date)){
             helper.setText(R.id.tv_date,"今天");
         } else {
          helper.setText(R.id.tv_date,item.getDate());
         }
        }
        Log.e("xuxinyi",name);
        if ("Base".equals(name)){
        helper.setText(R.id.tv_xinlv,item.getHeart_rate()+"");
        helper.setText(R.id.tv_xueyang,item.getBlood_oxygen()+"");
        helper.setText(R.id.tv_xueya,item.getHigh_blood_pressure()+"/"+item.getLow_blood_pressure());
        helper.setText(R.id.tv_huxi,item.getBreathing_rate()+"");
        helper.setText(R.id.tv_xunhuan,item.getPeripheral_circulation()+"");
        } else if ("PRESS".equals(name)){
            LinearLayout lin_xunhuan=helper.getView(R.id.lin_xunhuan);
            lin_xunhuan.setVisibility(View.GONE);
            TextView tvxinlvname=helper.getView(R.id.tv_xinlv_name);
            TextView tvxueyangname=helper.getView(R.id.tv_xueyang_name);
            TextView tvxueyaname=helper.getView(R.id.tv_xueya_name);
            TextView tvhuxiname=helper.getView(R.id.tv_huxi_name);
            tvxinlvname.setTextSize(12);
            tvxinlvname.setText("神经系统平衡性");
            tvxueyangname.setText("神经压力");
            tvxueyaname.setText("身体疲劳");
            tvhuxiname.setText("心率变异性");
            helper.setText(R.id.tv_xinlv,item.getNeurological_balance_str()+"");
            helper.setText(R.id.tv_xueyang,item.getMental_stress_level_str()+"");
            helper.setText(R.id.tv_xueya,item.getPhysical_fatigue_str());
            helper.setText(R.id.tv_huxi,item.getHeart_rate_variability_str()+"");
        } else if ("BLOOD".equals(name)){
            LinearLayout lin_xunhuan=helper.getView(R.id.lin_xunhuan);
            lin_xunhuan.setVisibility(View.GONE);
            LinearLayout lin_huxi=helper.getView(R.id.lin_huxi);
            lin_huxi.setVisibility(View.GONE);
            LinearLayout lin_xueya=helper.getView(R.id.lin_xueya);
            lin_xueya.setVisibility(View.GONE);
            LinearLayout lin_xueyang=helper.getView(R.id.lin_xueyang);
            lin_xueyang.setVisibility(View.GONE);
            LinearLayout lin_xinlv=helper.getView(R.id.lin_xinlv);
            lin_xinlv.setVisibility(View.GONE);
            LinearLayout lin_xueguan=helper.getView(R.id.lin_xueguan);
            lin_xueguan.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_xueguan,item.getVascular_health_str()+"");
        }
    }
}
