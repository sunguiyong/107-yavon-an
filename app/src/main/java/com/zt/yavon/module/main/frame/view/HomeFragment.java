package com.zt.yavon.module.main.frame.view;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
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
import com.zt.yavon.module.main.widget.MenuWidget;
import com.zt.yavon.module.message.view.MessageListActivity;
import com.zt.yavon.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

/**
 * Created by hp on 2018/6/11.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {
    @BindView(R.id.sliding_tab_layout)
    public SlidingTabLayout slidingTabLayout;
    @BindView(R.id.view_pager)
    public ViewPager viewPager;
    MenuWidget mMenuWidget;
    Unbinder unbinder;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    Unbinder unbinder1;
    private LinearLayoutManager layoutManager;
    private int curPage = 1;

    public List<TabBean> mTabData;
    public ArrayList<Fragment> fmts;
    private MainActivity mActivity;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        mRxManager.on(Constants.EVENT_REFRESH_HOME, new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                mPresenter.getTabData();
            }
        });

    }

    @Override
    protected void initView() {
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).startActForResult(RoomActivity.class, MainActivity.REQUEST_CODE_ADD_ROOM, (Serializable) mTabData);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((FmtDevice) fmts.get(position)).exitMultiSelectMode();
                for (int i = 0; i < slidingTabLayout.getTabCount(); i++) {
                    String resUrl = (i == position ? mTabData.get(i).icon_select : mTabData.get(i).icon);
                    int finalI = i;
                    Glide.with(getActivity()).load(resUrl).asBitmap().
                            into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    BitmapDrawable drawable = new BitmapDrawable(getActivity().getResources(), resource);
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
        mActivity = (MainActivity) getActivity();
        mMenuWidget = mActivity.findViewById(R.id.menu_widget);
        mActivity.findViewById(R.id.menu_recent).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_move).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_rename).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_share).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_more).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_del).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_report).setOnClickListener(mMenuWidget);
        mMenuWidget.setOnItemClickListener(new MenuWidget.OnItemClickListener() {
            @Override
            public void onRecentClick() {
                ((FmtDevice) fmts.get(viewPager.getCurrentItem())).onRecentClick();
            }

            @Override
            public void onMoveClick() {
                ((FmtDevice) fmts.get(viewPager.getCurrentItem())).onMoveClick();
            }

            @Override
            public void onRenameClick() {
                ((FmtDevice) fmts.get(viewPager.getCurrentItem())).onRenameClick();
            }

            @Override
            public void onShareClick() {
                ((FmtDevice) fmts.get(viewPager.getCurrentItem())).onShareClick();
            }

            @Override
            public void onDelClick() {
                ((FmtDevice) fmts.get(viewPager.getCurrentItem())).onDelClick();
            }

            @Override
            public void onReportClick() {
                ((FmtDevice) fmts.get(viewPager.getCurrentItem())).onReportClick();
            }
        });
        mPresenter.getTabData();

    }


    @Override
    public void returnTabData(List<TabBean> data) {
        mTabData = data;
        fmts = new ArrayList<>();
        String[] titles = new String[data.size()];

        for (int i = 0; i < data.size(); i++) {
            String showName = data.get(i).name;
            if (showName.length() > 3) {
                showName = showName.substring(0, 3) + "...";
            }
            titles[i] = showName;
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.EXTRA_DEVICE_TAB_ITEM_BEAN, data.get(i));
//            Fragment fmt = Fragment.instantiate(getActivity(), FmtDevice.class.getName(), bundle);
            Fragment fmt = new FmtDevice();
            fmt.setArguments(bundle);
            fmts.add(fmt);
        }
        if (viewPager.getAdapter() != null) {
            FragmentManager fm = mActivity.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
                Fragment item = ((FragmentPagerAdapter) viewPager.getAdapter()).getItem(i);
                ft.remove(item);
            }
            ft.commit();
        }
        slidingTabLayout.setViewPager(viewPager, titles, getActivity(), fmts);
        viewPager.getAdapter().notifyDataSetChanged();
        for (int i = 0; i < slidingTabLayout.getTabCount(); i++) {
            String resUrl = (i == 0 ? mTabData.get(i).icon_select : mTabData.get(i).icon);
            int finalI = i;
            Glide.with(getActivity()).load(resUrl).asBitmap().
                    into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            BitmapDrawable drawable = new BitmapDrawable(getActivity().getResources(), resource);
                            drawable.setBounds(0, 0, 60, 60);
                            slidingTabLayout.getTitleView(finalI).setCompoundDrawables(null, drawable, null, null);
                            // slidingTabLayout.getTitleView(finalI).setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                            slidingTabLayout.getTitleView(finalI).setCompoundDrawablePadding(16);
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

    public void refreshData() {
        mPresenter.getTabData();
    }
}
