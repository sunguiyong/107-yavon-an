package com.zt.yavon.module.main.frame.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseFragment;
import com.zt.yavon.module.deviceconnect.view.DeviceAddActivity;
import com.zt.yavon.module.deviceconnect.view.ScanCodeActivity;
import com.zt.yavon.module.main.frame.contract.HomeContract;
import com.zt.yavon.module.main.frame.model.TabItemBean;
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

    public List<TabItemBean> mTabData;

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
                    slidingTabLayout.getTitleView(i).setCompoundDrawablesWithIntrinsicBounds(0,
                            i == position ? mTabData.get(i).mSelectResId : mTabData.get(i).mUnSelectResId,
                            0, 0);
                    slidingTabLayout.getTitleView(i).setCompoundDrawablePadding(10);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPresenter.getTabData();

    }


    @Override
    public void returnTabData(List<TabItemBean> data) {
        mTabData = data;
        ArrayList<Fragment> fmts = new ArrayList<>();
        String[] titles = new String[data.size()];
        int[] unSelectResIds = new int[data.size()];
        int[] selectResIds = new int[data.size()];

        for (int i = 0; i < data.size(); i++) {
            titles[i] = data.get(i).mTitle;
            unSelectResIds[i] = data.get(i).mUnSelectResId;
            selectResIds[i] = data.get(i).mSelectResId;
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.EXTRA_DEVICE_TAB_ITEM_BEAN, data.get(i));
            Fragment fmt = Fragment.instantiate(getActivity(), FmtDevice.class.getName(), bundle);
            fmts.add(fmt);
        }
        slidingTabLayout.setViewPager(viewPager, titles, getActivity(), fmts);
        for (int i = 0; i < slidingTabLayout.getTabCount(); i++) {
            slidingTabLayout.getTitleView(i).setCompoundDrawablesWithIntrinsicBounds(0,
                    i == 0 ? data.get(i).mSelectResId : data.get(i).mUnSelectResId,
                    0, 0);
            slidingTabLayout.getTitleView(i).setCompoundDrawablePadding(10);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.iv_scan, R.id.iv_add,R.id.layout_msg})
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
                MessageListActivity.startAction(getActivity(),MessageListActivity.TYPE_INTERNAL);
                break;
        }
    }

    public void addTab(TabItemBean item) {
        // TODO refresh data
    }
}