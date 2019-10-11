package com.zt.igreen.module.deviceconnect.contract;


import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.HealthInfoBean;

/**
 * Created by Administrator on 2017/5/20 0020.
 */
public interface MainContract {
    interface View {
        void returnUserINfo(HealthInfoBean bean);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getUserINfo();
    }
}
