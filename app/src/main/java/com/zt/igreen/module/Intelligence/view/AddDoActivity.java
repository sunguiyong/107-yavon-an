package com.zt.igreen.module.Intelligence.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.common.base.utils.ToastUtil;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.barlibrary.ImmersionBar;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.Intelligence.contract.AddDoContract;
import com.zt.igreen.module.Intelligence.presenter.AddDoPresenter;
import com.zt.igreen.module.data.IntellAddBean;
import com.zt.igreen.module.data.IntellFragmentBean;
import com.zt.igreen.module.data.StatusIntellBean;
import com.zt.igreen.module.data.TabBean;
import com.zt.igreen.module.main.widget.MySwipeRefreshLayout;
import com.zt.igreen.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2019/3/5 0005.
 */

public class AddDoActivity extends BaseActivity<AddDoPresenter> implements AddDoContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.sliding_tab_layout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.swipe_home)
    MySwipeRefreshLayout refreshLayout;
    public List<TabBean> mTabData;
    public ArrayList<Fragment> fmts;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.lin_add_text)
    LinearLayout linAddText;
    @BindView(R.id.btn_back_header)
    TextView btnBackHeader;
    private List<StatusIntellBean> list_status;
    private IntellFragmentBean intellFragmentBean;
    private String url;
    private String name;

    @Override
    public int getLayoutId() {
        return R.layout.activity_adddo;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        mPresenter.getTabData(true);
        intellFragmentBean = (IntellFragmentBean) getIntent().getSerializableExtra("bean");
        url = getIntent().getStringExtra("url");
        name = getIntent().getStringExtra("name");
    }

    @Override
    public void initView() {
        sethead(R.color.qingse);
        setColor(Color.parseColor("#ffffff"));
        ImmersionBar.with(this).statusBarColor(R.color.qingse).statusBarDarkFont(true).flymeOSStatusBarFontColor(R.color.qingse).init();
        setColor(Color.parseColor("#ffffff"));
        setTitle("添加动作");
        //refreshLayout.setColorSchemeResources(R.color.white);
        refreshLayout.setOnRefreshListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < slidingTabLayout.getTabCount(); i++) {
                    String resUrl = (i == position ? mTabData.get(i).icon_select : mTabData.get(i).icon);
                    int finalI = i;
                    Glide.with(AddDoActivity.this).load(resUrl).asBitmap().
                            into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    BitmapDrawable drawable = new BitmapDrawable(AddDoActivity.this.getResources(), resource);
                                    drawable.setBounds(0, 0, 60, 60);
                                    slidingTabLayout.getTitleView(finalI).setCompoundDrawables(null, drawable, null, null);
                                    slidingTabLayout.getTitleView(finalI).setCompoundDrawablePadding(16);
                                }
                            });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btnBackHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (intellFragmentBean!=null){
                   SetIntellActivity.start(AddDoActivity.this,"complete",intellFragmentBean);
               finish();
               }else{
                   SetIntellActivity.startAction(AddDoActivity.this);
                   finish();
               }
            }
        });
    }

    public static void startAction(Context context,String url,String name) {
        Intent intent = new Intent(context, AddDoActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("name",name);
        context.startActivity(intent);
    }
    public static void startAction1(Context context, IntellFragmentBean bean) {
        Intent intent = new Intent(context, AddDoActivity.class);
        intent.putExtra("bean",bean);
        context.startActivity(intent);
    }
    @Override
    public void errorTabData(String message) {
        ToastUtil.showLong(this, message);
    }

    private int mSelectIndex =0;

    @Override
    public void returnTabData(List<TabBean> data) {
        {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(false);
            }
            if (data == null || data.isEmpty()) {
                ToastUtil.showShort(this, "数据加载失败，请重试！");
                return;
            }
            mSelectIndex = viewPager.getCurrentItem();
            mTabData = data;
            if (viewPager.getAdapter() != null) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                for (int i = 1; i < viewPager.getAdapter().getCount(); i++) {
                    Fragment item = ((FragmentPagerAdapter) viewPager.getAdapter()).getItem(i);
                    ft.remove(item);
                }
                ft.commitNowAllowingStateLoss();
            }
            if (fmts == null) {
                fmts = new ArrayList<>();
            } else if (fmts.size() > 0) {
                fmts.clear();
            }
            String[] titles = new String[data.size()-1];

            for (int i = 1; i < mTabData.size(); i++) {
                String showName = mTabData.get(i).name;
                if (showName.length() > 3) {
                    showName = showName.substring(0, 3) + "...";
                }
                titles[i-1] = showName;
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.EXTRA_DEVICE_TAB_ITEM_BEAN, mTabData.get(i));
                bundle.putSerializable("bean",intellFragmentBean);
                bundle.putString("url",url);
                bundle.putString("name",name);
                Fragment fmt = Fragment.instantiate(this, FmtIntellDevice.class.getName(), bundle);
                fmts.add(fmt);

            }

            slidingTabLayout.setViewPager(viewPager, titles, this, fmts);
            viewPager.post(new Runnable() {
                @Override
                public void run() {
                    viewPager.getAdapter().notifyDataSetChanged();
                    viewPager.setCurrentItem(mSelectIndex);
                }
            });
            for (int i = 0; i < slidingTabLayout.getTabCount(); i++) {
                String resUrl = (i == mSelectIndex ? mTabData.get(i).icon_select : mTabData.get(i).icon);
                int textColor = i == mSelectIndex ? Color.parseColor("#9D9D9D") : Color.parseColor("#8B8B8B");
                int finalI = i;
                Glide.with(this).load(resUrl).asBitmap().
                        into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                BitmapDrawable drawable = new BitmapDrawable(getResources(), resource);
                                drawable.setBounds(0, 0, 60, 60);
                                slidingTabLayout.getTitleView(finalI).setCompoundDrawables(null, drawable, null, null);
                                // slidingTabLayout.getTitleView(finalI).setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                                slidingTabLayout.getTitleView(finalI).setCompoundDrawablePadding(16);
                                slidingTabLayout.getTitleView(finalI).setTextColor(textColor);
                            }
                        });
            }
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
    }

}
