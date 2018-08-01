package com.zt.yavon.module.main.frame.view;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.common.base.utils.ToastUtil;
import com.flyco.tablayout.SlidingTabLayout;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseFragment;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.deviceconnect.view.DeviceAddActivity;
import com.zt.yavon.module.deviceconnect.view.ScanCodeActivity;
import com.zt.yavon.module.main.frame.contract.HomeContract;
import com.zt.yavon.module.main.frame.presenter.HomePresenter;
import com.zt.yavon.module.main.roommanager.list.view.RoomActivity;
import com.zt.yavon.module.message.view.MessageListActivity;
import com.zt.yavon.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hp on 2018/6/11.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {
    @BindView(R.id.sliding_tab_layout)
    public SlidingTabLayout slidingTabLayout;
    @BindView(R.id.view_pager)
    public ViewPager viewPager;
    Unbinder unbinder;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    Unbinder unbinder1;
    private LinearLayoutManager layoutManager;
    private int curPage = 1;

    public List<TabBean> mTabData;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    protected void initView() {
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).startActForResult(RoomActivity.class, MainActivity.REQUEST_CODE_ADD_ROOM);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < slidingTabLayout.getTabCount(); i++) {
                    String resUrl = (i == position ? mTabData.get(i).icon_select : mTabData.get(i).icon);
                    int finalI = i;
                    Glide.with(getActivity()).load(resUrl).asBitmap().
                            into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    BitmapDrawable drawable = new BitmapDrawable(getActivity().getResources(), resource);
                                    /// 这一步必须要做,否则不会显示.                  drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                                    slidingTabLayout.getTitleView(finalI).setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                                    slidingTabLayout.getTitleView(finalI).setCompoundDrawablePadding(20);
                                }
                            });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPresenter.getTabData();

    }


    @Override
    public void returnTabData(List<TabBean> data) {
        mTabData = data;
        ArrayList<Fragment> fmts = new ArrayList<>();
        String[] titles = new String[data.size()];

        for (int i = 0; i < data.size(); i++) {
            titles[i] = data.get(i).name;
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.EXTRA_DEVICE_TAB_ITEM_BEAN, data.get(i));
            Fragment fmt = Fragment.instantiate(getActivity(), FmtDevice.class.getName(), bundle);
            fmts.add(fmt);
        }
        slidingTabLayout.setViewPager(viewPager, titles, getActivity(), fmts);
        for (int i = 0; i < slidingTabLayout.getTabCount(); i++) {
            String resUrl = (i == 0 ? mTabData.get(i).icon_select : mTabData.get(i).icon);
            int finalI = i;
            Glide.with(getActivity()).load(resUrl).asBitmap().
                    into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            BitmapDrawable drawable = new BitmapDrawable(getActivity().getResources(), resource);
                            /// 这一步必须要做,否则不会显示.                  drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                            slidingTabLayout.getTitleView(finalI).setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                            slidingTabLayout.getTitleView(finalI).setCompoundDrawablePadding(20);
                        }
                    });
        }
    }

    @Override
    public void errorTabData(String message) {
        ToastUtil.showLong(getActivity(), message);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.iv_scan, R.id.iv_add, R.id.layout_msg})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }


    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.iv_scan:
                ScanCodeActivity.start(getActivity());
                break;
            case R.id.iv_add:
                DeviceAddActivity.start(getActivity());
                break;
            case R.id.layout_msg:
                MessageListActivity.startAction(getActivity(), MessageListActivity.TYPE_INTERNAL);
                break;
        }
    }

    public void addTab() {
        mPresenter.getTabData();
    }
}
