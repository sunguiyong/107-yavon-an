package com.zt.igreen.module.mine.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/17.
 */

public class PersionDataActivity extends BaseActivity {
    @BindView(R.id.tv_base)
    LinearLayout tvBase;
    @BindView(R.id.tv_base_name)
    TextView tvBaseName;
    @BindView(R.id.tv_health)
    LinearLayout tvHealth;
    @BindView(R.id.tv_health_name)
    TextView tvHealthName;
    @BindView(R.id.tv_live)
    LinearLayout tvLive;
    @BindView(R.id.tv_live_name)
    TextView tvLiveName;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tv_right_header)
    TextView tvRightHeader;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private myadapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_persion_data;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setRightMenuText("完成");
        viewpager.setOnPageChangeListener(new MyPagerChangeListener());
        mFragments.add(new BaseInfoFragment("shouye"));
        mFragments.add(new BaseInfoFragment("zhuangkang"));
        mFragments.add(new BaseInfoFragment("live"));
        sethead(R.color.qingse);
        setColor(Color.parseColor("#ffffff"));
        ImmersionBar.with(this).statusBarColor(R.color.qingse).statusBarDarkFont(true).flymeOSStatusBarFontColor(R.color.qingse).init();
        setColor(Color.parseColor("#ffffff"));
        setTitle("我的资料");
        adapter = new myadapter(getSupportFragmentManager(), mFragments);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        tvBase.setBackgroundResource(R.mipmap.data_select);
        tvHealth.setBackgroundResource(R.mipmap.data_noselect);
        tvLive.setBackgroundResource(R.mipmap.data_noselect);
        tvBaseName.setTextColor(Color.parseColor("#88B826"));

    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, PersionDataActivity.class);
        context.startActivity(intent);
    }

    /*  public void onEventMainThread(deviceeventbus event) {
          String code = event.getCode();
          if ("two".equals(code)) {
              mFragments.clear();
              mFragments.add(new BaseInfoFragment("shouye_two"));
              changefragment(0);
              tvBase.setBackgroundResource(R.mipmap.data_noselect);
              tvHealth.setBackgroundResource(R.mipmap.data_select);
              tvLive.setBackgroundResource(R.mipmap.data_noselect);
              tvBaseName.setTextColor(Color.parseColor("#CCCCCC"));
              tvHealthName.setTextColor(Color.parseColor("#88B826"));
              tvLiveName.setTextColor(Color.parseColor("#CCCCCC"));
          } else if ("three".equals(code)) {
              mFragments.clear();
              mFragments.add(new BaseInfoFragment("shouye_three"));
              changefragment(0);
              tvBase.setBackgroundResource(R.mipmap.data_noselect);
              tvHealth.setBackgroundResource(R.mipmap.data_noselect);
              tvLive.setBackgroundResource(R.mipmap.data_select);
              tvBaseName.setTextColor(Color.parseColor("#CCCCCC"));
              tvHealthName.setTextColor(Color.parseColor("#CCCCCC"));
              tvLiveName.setTextColor(Color.parseColor("#88B826"));
          }
      }*/
    @OnClick({R.id.tv_base_name, R.id.tv_health_name, R.id.tv_live_name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_base_name:
                viewpager.setCurrentItem(0);
                tvBase.setBackgroundResource(R.mipmap.data_select);
                tvHealth.setBackgroundResource(R.mipmap.data_noselect);
                tvLive.setBackgroundResource(R.mipmap.data_noselect);
                tvBaseName.setTextColor(Color.parseColor("#88B826"));
                tvHealthName.setTextColor(Color.parseColor("#CCCCCC"));
                tvLiveName.setTextColor(Color.parseColor("#CCCCCC"));
                break;
            case R.id.tv_health_name:
                viewpager.setCurrentItem(1);
                tvBase.setBackgroundResource(R.mipmap.data_noselect);
                tvHealth.setBackgroundResource(R.mipmap.data_select);
                tvLive.setBackgroundResource(R.mipmap.data_noselect);
                tvBaseName.setTextColor(Color.parseColor("#CCCCCC"));
                tvHealthName.setTextColor(Color.parseColor("#88B826"));
                tvLiveName.setTextColor(Color.parseColor("#CCCCCC"));
                break;
            case R.id.tv_live_name:
                viewpager.setCurrentItem(2);
                tvBase.setBackgroundResource(R.mipmap.data_noselect);
                tvHealth.setBackgroundResource(R.mipmap.data_noselect);
                tvLive.setBackgroundResource(R.mipmap.data_select);
                tvBaseName.setTextColor(Color.parseColor("#CCCCCC"));
                tvHealthName.setTextColor(Color.parseColor("#CCCCCC"));
                tvLiveName.setTextColor(Color.parseColor("#88B826"));
                break;
        }
    }

   

    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {              //监听屏幕的滑动
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {                        //当前选中的页面textView颜色改变
            switch (position) {
                case 0:
                    tvBase.setBackgroundResource(R.mipmap.data_select);
                    tvHealth.setBackgroundResource(R.mipmap.data_noselect);
                    tvLive.setBackgroundResource(R.mipmap.data_noselect);
                    tvBaseName.setTextColor(Color.parseColor("#88B826"));
                    tvHealthName.setTextColor(Color.parseColor("#CCCCCC"));
                    tvLiveName.setTextColor(Color.parseColor("#CCCCCC"));
                    break;
                case 1:
                    tvBase.setBackgroundResource(R.mipmap.data_noselect);
                    tvHealth.setBackgroundResource(R.mipmap.data_select);
                    tvLive.setBackgroundResource(R.mipmap.data_noselect);
                    tvBaseName.setTextColor(Color.parseColor("#CCCCCC"));
                    tvHealthName.setTextColor(Color.parseColor("#88B826"));
                    tvLiveName.setTextColor(Color.parseColor("#CCCCCC"));
                    break;
                case 2:
                    tvBase.setBackgroundResource(R.mipmap.data_noselect);
                    tvHealth.setBackgroundResource(R.mipmap.data_noselect);
                    tvLive.setBackgroundResource(R.mipmap.data_select);
                    tvBaseName.setTextColor(Color.parseColor("#CCCCCC"));
                    tvHealthName.setTextColor(Color.parseColor("#CCCCCC"));
                    tvLiveName.setTextColor(Color.parseColor("#88B826"));
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class myadapter extends FragmentPagerAdapter {
        private FragmentManager mfragmentManager;
        private List<Fragment> mlist;

        public myadapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mlist = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            return mlist.get(arg0);//显示第几个页面
        }

        @Override
        public int getCount() {
            return mlist.size();//有几个页面
        }

    }


}
