package com.zt.yavon.module.mine.view;

import android.view.View;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseFragment;

import butterknife.OnClick;

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
        setRightMenuImage(R.mipmap.ic_email);
    }
    @OnClick({R.id.layout_avatar})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.layout_avatar:
                PersonalInfoActivity.startAction(getContext());
                break;
        }
    }
}
