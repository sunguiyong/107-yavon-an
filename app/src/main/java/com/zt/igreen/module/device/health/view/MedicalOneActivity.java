package com.zt.igreen.module.device.health.view;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
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

public class MedicalOneActivity extends BaseActivity<MedicalPresenter> implements MedicalContract.View {


    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_suishu)
    TextView tvSuishu;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_gengxin)
    TextView tvGengxin;
    @BindView(R.id.tv_xinlv_two)
    TextView tvXinlvTwo;
    @BindView(R.id.img_shenjing)
    ImageView imgShenjing;
    @BindView(R.id.tv_fenxicontext)
    TextView tvFenxicontext;
    @BindView(R.id.tv_xueyang_two)
    TextView tvXueyangTwo;
    @BindView(R.id.img_jingshen)
    ImageView imgJingshen;
    @BindView(R.id.tv_xueyangfenxicontext)
    TextView tvXueyangfenxicontext;
    @BindView(R.id.tv_xueya_two)
    TextView tvXueyaTwo;
    @BindView(R.id.img_pilao)
    ImageView imgPilao;
    @BindView(R.id.tv_xueyafenxicontext)
    TextView tvXueyafenxicontext;
    @BindView(R.id.tv_huxi_two)
    TextView tvHuxiTwo;
    @BindView(R.id.seekbar)
    MySeekBar seekbar;
    @BindView(R.id.tv_huxifenxicontext)
    TextView tvHuxifenxicontext;
    @BindView(R.id.tv_xinlvbianyi)
    TextView tvXinlvbianyi;
    private HistoryBean bean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_medica_twol;
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
        LoginBean loginBean =SPUtil.getAccount(this);
        sethead(R.color.huangse);
        tvName.setText(loginBean.getNick_name());
        tvGengxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersionDataActivity.startAction(MedicalOneActivity.this);
            }
        });
    }

    private void initSDK() {

    }

    public static void startAction(Context context, HistoryBean bean) {
        Intent intent = new Intent(context, MedicalOneActivity.class);
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
        int balance = bean.getNeurological_balance();
        if (balance < 30) {
            imgShenjing.setImageResource(R.mipmap.shenjing_one);
        } else if (balance >= 30 && balance <= 70) {
            imgShenjing.setImageResource(R.mipmap.shenjing_two);
        } else if (balance > 70) {
            imgShenjing.setImageResource(R.mipmap.shenjing_shen);
        }
        String balance_str = bean.getNeurological_balance_str().getAnalysis();
        tvFenxicontext.setText(balance_str);
        int stress_level = bean.getMental_stress_level();
        if (stress_level > 0 && stress_level < 30) {
            imgJingshen.setImageResource(R.mipmap.jingshen_one);
        } else if (stress_level >= 30 && stress_level < 50) {
            imgJingshen.setImageResource(R.mipmap.jingshen_two);
        } else if (stress_level >= 50 && stress_level < 70) {
            imgJingshen.setImageResource(R.mipmap.jingshen_three);
        } else if (stress_level >= 70 && stress_level < 90) {
            imgJingshen.setImageResource(R.mipmap.jingshen_four);
        } else if (stress_level >= 90 && stress_level <= 100) {
            imgJingshen.setImageResource(R.mipmap.jingshen_five);
        }
        String analysis = bean.getMental_stress_level_str().getAnalysis();
        tvXueyangfenxicontext.setText(analysis);


        int fatigue = bean.getPhysical_fatigue();
        if (fatigue > 0 && fatigue < 30) {
            imgPilao.setImageResource(R.mipmap.pilao_one);
        } else if (fatigue >= 30 && fatigue < 50) {
            imgPilao.setImageResource(R.mipmap.pilao_two);
        } else if (fatigue >= 50 && fatigue < 70) {
            imgPilao.setImageResource(R.mipmap.pilao_three);
        } else if (fatigue >= 70 && fatigue < 90) {
            imgPilao.setImageResource(R.mipmap.pilao_four);
        } else if (fatigue >= 90 && fatigue <= 100) {
            imgPilao.setImageResource(R.mipmap.pilao_five);
        }
        String fatigue_str = bean.getPhysical_fatigue_str().getAnalysis();
        tvXueyafenxicontext.setText(fatigue_str);
        int variability=bean.getHeart_rate_variability();
        seekbar.setProgress(variability);
        tvXinlvbianyi.setText(variability+"/"+100);
        String variability_str=bean.getHeart_rate_variability_str().getAnalysis();
        tvHuxifenxicontext.setText(variability_str);

    }

}
