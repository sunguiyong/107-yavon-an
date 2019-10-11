package com.zt.igreen.module.device.health.view;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.jaygoo.widget.RangeSeekBar;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.data.HistoryBean;
import com.zt.igreen.module.data.LoginBean;
import com.zt.igreen.module.data.MedicalBean;
import com.zt.igreen.module.device.PAU.view.MySeekBar;
import com.zt.igreen.module.device.health.contract.MedicalContract;
import com.zt.igreen.module.device.health.presenter.MedicalPresenter;
import com.zt.igreen.module.mine.view.PersionDataActivity;
import com.zt.igreen.utils.SPUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lifujun on 2018/7/10.
 */

public class MedicalActivity extends BaseActivity<MedicalPresenter> implements MedicalContract.View {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_suishu)
    TextView tvSuishu;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_xinlv)
    TextView tvXinlv;
    @BindView(R.id.tv_xueyang)
    TextView tvXueyang;
    @BindView(R.id.tv_xueya)
    TextView tvXueya;
    @BindView(R.id.tv_huxi)
    TextView tvHuxi;
    @BindView(R.id.tv_xunhuan)
    TextView tvXunhuan;
    @BindView(R.id.tv_gengxin)
    TextView tvGengxin;
    @BindView(R.id.tv_xinlv_two)
    TextView tvXinlvTwo;
    @BindView(R.id.tv_xinlv_number)
    TextView tvXinlvNumber;
    @BindView(R.id.tv_xinlv_yicang)
    TextView tvXinlvYicang;
    @BindView(R.id.seekbar)
    MySeekBar seekbar;
    @BindView(R.id.tv_fenxicontext)
    TextView tvFenxicontext;
    @BindView(R.id.tv_xueyang_two)
    TextView tvXueyangTwo;
    @BindView(R.id.tv_xinlv_bainumber)
    TextView tvXinlvBainumber;
    @BindView(R.id.seekbar_two)
    MySeekBar seekbarTwo;
    @BindView(R.id.tv_xueyangfenxicontext)
    TextView tvXueyangfenxicontext;
    @BindView(R.id.tv_xueya_two)
    TextView tvXueyaTwo;
    @BindView(R.id.tv_xueya_bainumber)
    TextView tvXueyaBainumber;
    @BindView(R.id.tv_xueyafenxicontext)
    TextView tvXueyafenxicontext;
    @BindView(R.id.tv_huxi_two)
    TextView tvHuxiTwo;
    @BindView(R.id.tv_huxi_bainumber)
    TextView tvHuxiBainumber;
    @BindView(R.id.seekbar_four)
    MySeekBar seekbarFour;
    @BindView(R.id.tv_huxifenxicontext)
    TextView tvHuxifenxicontext;
    @BindView(R.id.tv_xinlvbpm)
    TextView tvXinlvbpm;
    @BindView(R.id.tv_moshao_two)
    TextView tvMoshaoTwo;
    @BindView(R.id.tv_moshao_bainumber)
    TextView tvMoshaoBainumber;
    @BindView(R.id.seekbar_five)
    MySeekBar seekbarFive;
    @BindView(R.id.tv_moshaofenxicontext)
    TextView tvMoshaofenxicontext;
    @BindView(R.id.rangebar)
    RangeSeekBar rangebar;
    private HistoryBean bean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_medical;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        bean = (HistoryBean) getIntent().getSerializableExtra("bean");
        mPresenter.getMedical(bean.getId() + "");
    }


    @Override
    public void initView() {
        sethead(R.color.qingse);
        ImmersionBar.with(this).statusBarColor(R.color.huangse).statusBarDarkFont(true).flymeOSStatusBarFontColor(R.color.huangse).init();
        setColor(Color.parseColor("#ffffff"));
        setTitle("体检报告");
        sethead(R.color.huangse);
        LoginBean loginBean = SPUtil.getAccount(this);
        sethead(R.color.huangse);
        tvName.setText(loginBean.getNick_name());
        tvGengxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersionDataActivity.startAction(MedicalActivity.this);
            }
        });
    }

    private void initSDK() {

    }

    public static void startAction(Context context, HistoryBean bean) {
        Intent intent = new Intent(context, MedicalActivity.class);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void returnMedical(MedicalBean bean) {
        String date = bean.getDate();
        tvDate.setText(date);
        String year = bean.getYear();
        tvSuishu.setText(year);
        int heart_rate = bean.getHeart_rate();
        tvXinlv.setText(heart_rate + "");
        tvXinlvNumber.setText(heart_rate + "  BPM");
        int abnormal_heart_rate = bean.getAbnormal_heart_rate();
        tvXinlvYicang.setText(abnormal_heart_rate + "");
        seekbar.setProgress(heart_rate);
        String heart_rate_str = bean.getHeart_rate_str().getAnalysis();
        tvFenxicontext.setText(heart_rate_str);
        String result = bean.getHeart_rate_str().getResult();
        tvXinlvTwo.setText(result);
        int blood_oxygen = bean.getBlood_oxygen();
        tvXueyangTwo.setText(blood_oxygen + "");
        tvXinlvBainumber.setText(heart_rate + "");
        tvXueyang.setText(blood_oxygen + "");
        String analysis = bean.getBlood_oxygen_str().getAnalysis();
        tvXueyangfenxicontext.setText(analysis);
        int high_blood_pressure = bean.getHigh_blood_pressure();
        int low_blood_pressure = bean.getLow_blood_pressure();
        tvXueya.setText(high_blood_pressure + "/" + low_blood_pressure);
        tvXueyaBainumber.setText(high_blood_pressure + "/" + low_blood_pressure);
        rangebar.setEnabled(false);
       // rangebar.setRange(low_blood_pressure,high_blood_pressure);
        rangebar.setValue(60,100);
        String xueya_result = bean.getBlood_pressure_str().getResult();
        tvXueyaTwo.setText(xueya_result);
        String pressure_analysis = bean.getBlood_pressure_str().getAnalysis();
        tvXueyafenxicontext.setText(pressure_analysis);
        int huxi_rate = bean.getBreathing_rate();
        tvHuxi.setText(huxi_rate + "");
        seekbarFour.setProgress(huxi_rate);
        tvHuxiBainumber.setText(huxi_rate + "次/分");
        String rate_result = bean.getHeart_rate_str().getResult();
        tvHuxiTwo.setText(rate_result);
        String huxi_analysis = bean.getHeart_rate_str().getAnalysis();
        tvHuxifenxicontext.setText(huxi_analysis);
        String peripheral_circulation = bean.getPeripheral_circulation();
        tvXunhuan.setText(peripheral_circulation + "");
        tvMoshaoBainumber.setText(peripheral_circulation + "");
        String Peripheral_result = bean.getPeripheral_circulation_str().getResult();
        tvMoshaoTwo.setText(Peripheral_result);
        String Peripheral_analysis = bean.getPeripheral_circulation_str().getAnalysis();
        tvMoshaofenxicontext.setText(Peripheral_analysis);
        double parseDouble = Double.parseDouble(peripheral_circulation);
        seekbarFive.setProgress((int) parseDouble);
    }

}
