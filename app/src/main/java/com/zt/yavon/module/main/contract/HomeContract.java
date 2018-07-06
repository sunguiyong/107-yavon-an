package com.zt.yavon.module.main.contract;


import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.DemoBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 */
public interface HomeContract {
    interface View{
        void returnHomeDevList(List<DemoBean> list);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getHomeDevList(String page,boolean showLoading);
    }
}
