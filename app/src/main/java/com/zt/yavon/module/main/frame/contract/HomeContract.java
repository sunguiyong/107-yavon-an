package com.zt.yavon.module.main.frame.contract;


import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.main.frame.model.TabItemBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 */
public interface HomeContract {
    interface View {
        void returnTabData(List<TabItemBean> data);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getTabData();
    }
}
