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
import com.zt.igreen.module.device.health.contract.MedicalContract;
import com.zt.igreen.module.device.health.presenter.MedicalPresenter;
import com.zt.igreen.module.mine.view.PersionDataActivity;
import com.zt.igreen.utils.SPUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lifujun on 2018/7/10.
 */

public class MedicalTwoActivity extends BaseActivity<MedicalPresenter> implements MedicalContract.View {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_suishu)
    TextView tvSuishu;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_gengxin)
    TextView tvGengxin;
    @BindView(R.id.xueguan_nianling)
    TextView xueguanNianling;
    @BindView(R.id.img_jingshen)
    ImageView imgJingshen;
    @BindView(R.id.tv_xueyangfenxicontext)
    TextView tvXueyangfenxicontext;
    private HistoryBean bean;
    @Override
    public int getLayoutId() {
        return R.layout.activity_medica_three;
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
        LoginBean loginBean = SPUtil.getAccount(this);
        sethead(R.color.huangse);
        tvName.setText(loginBean.getNick_name());
        tvGengxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersionDataActivity.startAction(MedicalTwoActivity.this);
            }
        });
    }

    private void initSDK() {

    }

    public static void startAction(Context context,HistoryBean bean) {
        Intent intent = new Intent(context, MedicalTwoActivity.class);
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
        String vascular_health = bean.getVascular_health();
        if ("第一阶段".equals(vascular_health)){
            imgJingshen.setImageResource(R.mipmap.xueguan_one);
        }else if ("第二阶段".equals(vascular_health)){
            imgJingshen.setImageResource(R.mipmap.xueguan_two);
        }else if ("第三阶段".equals(vascular_health)){
            imgJingshen.setImageResource(R.mipmap.xueguan_three);
        }else if ("第四阶段".equals(vascular_health)){
            imgJingshen.setImageResource(R.mipmap.xueguan_four);
        }else if ("第五阶段".equals(vascular_health)){
            imgJingshen.setImageResource(R.mipmap.xueguan_five);
        }else if ("第六阶段".equals(vascular_health)){
            imgJingshen.setImageResource(R.mipmap.xueguan_six);
        }
        String vascular_health_age=bean.getVascular_health_age();
        xueguanNianling.setText(vascular_health_age+"岁");
        String analysis = bean.getVascular_health_str().getAnalysis();
        tvXueyangfenxicontext.setText(analysis);
    }

}
