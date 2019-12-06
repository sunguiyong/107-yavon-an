package com.zt.igreen.module.device.health.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.gyf.barlibrary.ImmersionBar;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.data.LoginBean;
import com.zt.igreen.module.data.TabBean;
import com.zt.igreen.module.device.health.bean.HealthDataSava;
import com.zt.igreen.module.main.widget.GlideCircleTransfrom;
import com.zt.igreen.utils.SPUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HealMoreDataActivity extends BaseActivity {
    int x = R.layout.activity_health_datashow;
    @BindView(R.id.tv_right_header1)
    TextView tvRightHeader1;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.lin_add_text)
    LinearLayout linAddText;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.lin_add_intell)
    LinearLayout linAddIntell;
    @BindView(R.id.lin_dian)
    LinearLayout linDian;
    @BindView(R.id.xinlv_tv)
    TextView xinlvTv;
    @BindView(R.id.xueyang_tv)
    TextView xueyangTv;
    @BindView(R.id.xueya_tv)
    TextView xueyaTv;
    @BindView(R.id.yuan2)
    ImageView yuan2;
    @BindView(R.id.yuan3)
    ImageView yuan3;
    @BindView(R.id.iv_avatar_info)
    ImageView ivAvatarInfo;
    @BindView(R.id.grade_tv)
    TextView gradeTv;
    @BindView(R.id.main_fl)
    FrameLayout mainFl;
    @BindView(R.id.pinglv_tv)
    TextView pinglvTv;
    @BindView(R.id.xunhuan_tv)
    TextView xunhuanTv;
    @BindView(R.id.pingheng)
    TextView pingheng;
    @BindView(R.id.yali_tv)
    TextView yaliTv;
    @BindView(R.id.pilao_tv)
    TextView pilaoTv;
    @BindView(R.id.bianyi_tv)
    TextView bianyiTv;
    @BindView(R.id.xueguan_tv)
    TextView xueguanTv;
    @BindView(R.id.getmore_img)
    ImageView getmoreImg;
    private TabBean.MachineBean machineBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_health_datashow;
    }

    public static void startAction(Context context, TabBean.MachineBean machineBean) {
        Intent intent = new Intent(context, HealMoreDataActivity.class);
        intent.putExtra("machineBean", machineBean);
        context.startActivity(intent);
    }

    @Override
    public void initPresenter() {
//        mPresenter.setVM(this);
        machineBean = (TabBean.MachineBean) getIntent().getSerializableExtra("machineBean");
    }

    @OnClick({R.id.getmore_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getmore_img:
                HistoryActivity.startAction(this, machineBean);
                break;
        }
    }

    @Override
    public void initView() {
        xinlvTv.setText(HealthDataSava.rate);
        xueyangTv.setText(HealthDataSava.oxygen);
        xueyaTv.setText(HealthDataSava.low + HealthDataSava.high);
        pinglvTv.setText(HealthDataSava.breath);
        xunhuanTv.setText(HealthDataSava.hrv);
        pingheng.setText(HealthDataSava.balance);
        yaliTv.setText(HealthDataSava.mentalScore);
        pilaoTv.setText(HealthDataSava.physical);
        bianyiTv.setText(HealthDataSava.pi);
        xueguanTv.setText(HealthDataSava.summeryVesselAge);
        //头像
        LoginBean bean = SPUtil.getAccount(this);
        if (bean != null) {
            Glide.with(this)
                    .load(bean.getAvatar())
//                    .placeholder(R.mipmap.avatar_default)
                    .error(R.mipmap.avatar_default)
                    .transform(new CenterCrop(this), new GlideCircleTransfrom(this))
                    .dontAnimate()
                    .into(ivAvatarInfo);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");// HH:mm:ss
//      获取当前时间
        Date date = new Date(System.currentTimeMillis());
        setLeftButtonImage(R.mipmap.back_one);
        setTitle(simpleDateFormat.format(date));
        sethead(R.color.touming);
        ImmersionBar.with(this)
                .statusBarColor(R.color.touming).statusBarDarkFont(false).flymeOSStatusBarFontColor(R.color.touming).init();
    }
}
