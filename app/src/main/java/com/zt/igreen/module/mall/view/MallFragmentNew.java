package com.zt.igreen.module.mall.view;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseFragment;
import com.zt.igreen.module.data.IntellListBean;
import com.zt.igreen.module.data.MaterialTypeBean;
import com.zt.igreen.module.mall.contract.MaterialStyleContract;
import com.zt.igreen.module.mall.presenter.MaterialStylePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MallFragmentNew extends BaseFragment<MaterialStylePresenter> implements MaterialStyleContract.View, View.OnClickListener {
    int x = R.layout.fragment_mall_new;
    @BindView(R.id.shoucang_ll)
    LinearLayout shoucangLl;
    @BindView(R.id.sliding_tab_layout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    Unbinder unbinder;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mall_new;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        mPresenter.getMaterialTypes();
        mPresenter.getMaterialList(1 + "");
    }

    private void bindListener() {
        searchLl.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        bindListener();
        initSlidingTabLayout();
    }

    private void initSlidingTabLayout() {

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < slidingTabLayout.getTabCount(); i++) {
                    String tab = "aaa";
                    int num = i;
                    slidingTabLayout.getTitleView(num).setText(tab + i);
                    slidingTabLayout.getTitleView(num).setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void returnMaterial(List<MaterialTypeBean> list) {

    }

    @Override
    public void returnMaterialList(List<IntellListBean> list) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_ll:
                SearchActivity.startAction(getActivity());
                break;
        }
    }
}
