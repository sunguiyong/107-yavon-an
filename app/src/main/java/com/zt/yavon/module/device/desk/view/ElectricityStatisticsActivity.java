package com.zt.yavon.module.device.desk.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.charts.CombinedChart;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.utils.CombinedChartManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lifujun on 2018/7/10.
 */

public class ElectricityStatisticsActivity extends BaseActivity{
    @BindView(R.id.chart)
    CombinedChart mCombinedChart1;
    @Override
    public int getLayoutId() {
        return R.layout.activity_elctricity_statistics;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle(getString(R.string.coulometry));
        //x轴数据
        List<String> xData = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            xData.add(String.valueOf(i));
        }
        //y轴数据集合
        List<Float> yBarDatas = new ArrayList<>();
        //4种直方图
            //y轴数
            for (int j = 0; j <= 6; j++) {
                yBarDatas.add((float) (Math.random() * 100));
            }

        //管理类
        CombinedChartManager combineChartManager = new CombinedChartManager(mCombinedChart1);
        combineChartManager.showCombinedChart(xData, yBarDatas, yBarDatas,
                "直方图", "线性图", ContextCompat.getColor(this,R.color.mainGreen), Color.WHITE);
    }

//    @OnClick({R.id.tv_switch_lock,R.id.tv_right_header})
//    @Override
//    public void doubleClickFilter(View view) {
//        super.doubleClickFilter(view);
//    }
//
//    @Override
//    public void doClick(View view) {
//        switch (view.getId()){
//            case R.id.tv_switch_lock:
//                if(tvSwith.isSelected()){
//                    ivLock.setSelected(false);
//                    tvSwith.setSelected(false);
//                }else{
//                    ivLock.setSelected(true);
//                    tvSwith.setSelected(true);
//                }
//                break;
//            case R.id.tv_right_header:
//                break;
//        }
//    }
    public static void startAction(Context context){
        Intent intent = new Intent(context,ElectricityStatisticsActivity.class);
        context.startActivity(intent);
    }
}
