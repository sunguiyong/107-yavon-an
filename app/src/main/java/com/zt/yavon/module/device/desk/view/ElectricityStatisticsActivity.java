package com.zt.yavon.module.device.desk.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.github.mikephil.charting.charts.CombinedChart;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.ElectricDayBean;
import com.zt.yavon.module.data.ElectricMonthBean;
import com.zt.yavon.module.device.desk.contract.ElectricityContract;
import com.zt.yavon.module.device.desk.presenter.ElectricityPresenter;
import com.zt.yavon.utils.CombinedChartManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class ElectricityStatisticsActivity extends BaseActivity<ElectricityPresenter> implements ElectricityContract.View{
    public static final int TYPE_DAY = 1;
    public static final int TYPE_MONTH = 3;
    @BindView(R.id.chart)
    CombinedChart mCombinedChart1;
    private String machineId;
    private CombinedChartManager combineChartManager;
    private List<ElectricDayBean> dayList;
    private List<ElectricMonthBean> monthList;
    private int type = TYPE_DAY;
    @Override
    public int getLayoutId() {
        return R.layout.activity_elctricity_statistics;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        machineId = getIntent().getStringExtra("machineId");
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.coulometry));
        //x轴数据
//        List<String> xData = new ArrayList<>();
//        for (int i = 2; i <= 8; i++) {
//            xData.add(String.valueOf(i));
//        }
        //y轴数据集合
//        List<Float> yBarDatas = new ArrayList<>();
        //4种直方图
            //y轴数
//            for (int j = 0; j <= 6; j++) {
//                yBarDatas.add((float) (Math.random() * 100));
//            }

        //管理类
        combineChartManager = new CombinedChartManager(mCombinedChart1);
        mCombinedChart1.setNoDataText("没有数据");
        mCombinedChart1.setNoDataTextColor(ContextCompat.getColor(this,R.color.white_tran));
//        combineChartManager.showCombinedChart(xData, yBarDatas, yBarDatas,
//                "直方图", "线性图", ContextCompat.getColor(this,R.color.mainGreen), Color.WHITE);
        mPresenter.getDayPower(machineId);
    }

    @OnClick({R.id.type_change_electric})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.type_change_electric:
                if(type == TYPE_DAY){
                    type = TYPE_MONTH;
                    if(monthList == null){
                        mPresenter.getMonthPower(machineId);
                    }else{
                        updateChart();
                    }
                }else{
                    type = TYPE_DAY;
                    if(dayList == null){
                        mPresenter.getDayPower(machineId);
                    }else{
                        updateChart();
                    }
                }
                break;
        }
    }
    public static void startAction(Context context,String machineId){
        Intent intent = new Intent(context,ElectricityStatisticsActivity.class);
        intent.putExtra("machineId",machineId);
        context.startActivity(intent);
    }

    @Override
    public void returnDayList(List<ElectricDayBean> list) {
        dayList = list;
        if(type == TYPE_DAY)
        updateChart();
    }
    private void updateChart(){
        //x轴数据
        List<String> xData = new ArrayList<>();
        //y轴数据集合
        List<Float> yBarDatas = new ArrayList<>();
        if(type == TYPE_DAY){
            for (ElectricDayBean bean:dayList) {
                xData.add(bean.day);
                yBarDatas.add((float) bean.power);
            }
        }else{
            for (ElectricMonthBean bean:monthList) {
                xData.add(bean.month);
                yBarDatas.add((float) bean.power);
            }
        }
        combineChartManager.showCombinedChart(xData, yBarDatas, yBarDatas,
                "直方图", "线性图", ContextCompat.getColor(this,R.color.mainGreen), Color.WHITE);
    }

    @Override
    public void returnMonthList(List<ElectricMonthBean> list) {
        monthList = list;
        if(type == TYPE_MONTH)
            updateChart();
    }
}
