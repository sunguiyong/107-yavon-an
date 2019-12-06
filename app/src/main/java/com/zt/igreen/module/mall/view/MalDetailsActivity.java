package com.zt.igreen.module.mall.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.common.base.utils.ToastUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.loader.ImageLoaderInterface;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.data.MaterialDetailsBean;
import com.zt.igreen.module.mall.contract.MalMaterialContract;
import com.zt.igreen.module.mall.presenter.MalMaterialPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/2/21 0021.
 */

public class MalDetailsActivity extends BaseActivity<MalMaterialPresenter> implements MalMaterialContract.View {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.device_name)
    TextView deviceName;
    @BindView(R.id.device_des)
    TextView deviceDes;
    @BindView(R.id.device_guize)
    TextView deviceGuize;
    @BindView(R.id.device_miaoshu)
    TextView deviceMiaoshu;
    @BindView(R.id.device_message)
    TextView deviceMessage;
    @BindView(R.id.device_style)
    TextView deviceStyle;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_soucang)
    ImageView imgSoucang;
    @BindView(R.id.img_sms)
    ImageView imgSms;
    MaterialDetailsBean bean = new MaterialDetailsBean();
    private String device_id;
    private boolean favorite_Status = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_malldetails;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        device_id = getIntent().getStringExtra("id");
        mPresenter.getMalMaterial(device_id);
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.touming)
                .statusBarDarkFont(false)
                .flymeOSStatusBarFontColor(R.color.touming).init();

    }

    public static void startAction(Context context, String id) {
        Intent intent = new Intent(context, MalDetailsActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @OnClick({R.id.img_back, R.id.img_soucang, R.id.img_sms})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_soucang:
                boolean favorite = bean.isIs_favorite();
                if (favorite_Status == false) {
                    favorite_Status = true;
                    imgSoucang.setImageResource(R.mipmap.select_souchang);
                    mPresenter.getCollect(device_id);
                } else if (favorite_Status == true) {
                    imgSoucang.setImageResource(R.mipmap.img_shoucang);
                    favorite_Status = false;
                    mPresenter.getCollect(device_id);
                }
                break;
            case R.id.img_sms:
                WordsActivity.startAction(this, bean.getId() + "");
                break;
        }
    }

    List<String> list_str = new ArrayList<>();

    @Override
    public void returnMalMaterial(MaterialDetailsBean bean) {
        this.bean = bean;
        boolean favorite = bean.isIs_favorite();
        if (favorite == true) {
            imgSoucang.setImageResource(R.mipmap.select_souchang);
            favorite_Status = true;
        } else if (favorite == false) {
            imgSoucang.setImageResource(R.mipmap.img_shoucang);
            favorite_Status = false;
        }
        deviceName.setText(bean.getName());
        deviceDes.setText(bean.getDescription());
        deviceGuize.setText(bean.getSpecification() + "");
        deviceMiaoshu.setText(bean.getCharacteristic());
        deviceMessage.setText(bean.getSupplier());
        List<MaterialDetailsBean.ImagesBean> img = bean.getImages();
        list_str.clear();
        for (int i = 0; i < img.size() - 1; i++) {
            String image = img.get(i).getImage();
            list_str.add(image);
        }
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list_str);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void returnCollect() {
        // ToastUtil.show(this,"已经成功",Toast.LENGTH_LONG);
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}
