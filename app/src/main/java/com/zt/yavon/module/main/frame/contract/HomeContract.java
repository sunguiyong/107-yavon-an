package com.zt.yavon.module.main.frame.contract;


import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.TabBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 */
public interface HomeContract {
    interface View {
        void returnTabData(List<TabBean> data);

        void errorTabData(String message);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getTabData();
    }
}
