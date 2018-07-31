package com.zt.yavon.module.mall;

import android.view.View;
import android.widget.AdapterView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseFragment;
import com.zt.yavon.module.device.desk.view.DeskDetailActivity;
import com.zt.yavon.module.device.lamp.view.LampDetailActivity;
import com.zt.yavon.module.device.lock.view.LockDetailActivity;
import com.zt.yavon.module.device.share.view.ApplyDevActivity;
import com.zt.yavon.module.device.share.view.AuthorActivity;
import com.zt.yavon.module.device.share.view.ShareDevActivity;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.widget.calendar.DateSelectActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/16.
 */

public class MallFragment extends BaseFragment{

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mall_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        hideBackButton();
        setTitle(getString(R.string.tab_mall));
    }
    @OnClick({R.id.btn_lamp,R.id.btn_lock,R.id.btn_desk,R.id.btn_share,R.id.btn_apply,R.id.btn_author})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }
    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.btn_lamp:
                LampDetailActivity.startAction(getActivity());
                break;
            case R.id.btn_lock:
                LockDetailActivity.startAction(getActivity());
                break;
            case R.id.btn_desk:
                DeskDetailActivity.startAction(getActivity());
                break;
            case R.id.btn_share:
                ShareDevActivity.startAction(getActivity(),"",0);
                break;
            case R.id.btn_apply:
                ApplyDevActivity.startAction(getActivity(),"","");
                break;
            case R.id.btn_author:
                AuthorActivity.startAction(getActivity(),"",0);
                break;
        }
    }
}
