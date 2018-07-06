package com.zt.yavon.module.mine;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseFragment;

/**
 * Created by hp on 2018/6/11.
 */

public class MineFragment extends BaseFragment{
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        hideBackButton();
        setTitle(getString(R.string.tab_mine));
    }
}
