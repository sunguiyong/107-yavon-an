package com.zt.yavon.module.device.lamp.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class LampDetailActivity extends BaseActivity{
    @BindView(R.id.iv_lamp)
    ImageView ivLamp;
    @BindView(R.id.tv_switch_lamp)
    TextView tvSwith;
    @Override
    public int getLayoutId() {
        return R.layout.activity_lamp_detail;
    }

    @Override
    public void initPresenter() {

    }
    @Override
    public void initView() {
        setTitle(getString(R.string.title_lamp));
        setRightMenuImage(R.mipmap.more_right);
    }

    @OnClick({R.id.tv_switch_lamp,R.id.tv_right_header})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.tv_switch_lamp:
                if(tvSwith.isSelected()){
                    ivLamp.setSelected(false);
                    tvSwith.setSelected(false);
                }else{
                    ivLamp.setSelected(true);
                    tvSwith.setSelected(true);
                }
                break;
            case R.id.tv_right_header:
                LampSettingActivity.startAction(this);
                break;
        }
    }
    public static void startAction(Context context){
        Intent intent = new Intent(context,LampDetailActivity.class);
        context.startActivity(intent);
    }
}
