package com.zt.yavon.module.main.frame.presenter;

import com.zt.yavon.R;
import com.zt.yavon.module.main.frame.contract.HomeContract;
import com.zt.yavon.module.main.frame.model.TabItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class HomePresenter extends HomeContract.Presenter {
    public String SET_SWITCH = "on";
    public String SET_LINK = "link";
    public String SET_FAN = "fan";
    public String SET_MODE = "mode";


    @Override
    public void getTabData() {
        List<TabItemBean> tabs = new ArrayList<>();
        tabs.add(new TabItemBean("1", "常用", R.mipmap.ic_recent_checked, R.mipmap.ic_recent_normal));
        tabs.add(new TabItemBean("2", "办公室", R.mipmap.ic_office_checked, R.mipmap.ic_office_normal));
        tabs.add(new TabItemBean("3", "会议室", R.mipmap.ic_meeting_checked, R.mipmap.ic_meeting_normal));
        tabs.add(new TabItemBean("4", "会议室1", R.mipmap.ic_meeting_checked, R.mipmap.ic_meeting_normal));
        mView.returnTabData(tabs);
    }

}
