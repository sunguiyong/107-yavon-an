package com.zt.yavon.module.mine.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.LoginBean;

/**
 * Created by lifujun on 2018/7/25.
 */

public interface MineContract {
    interface View {
        void returnPersonalInfo(LoginBean bean);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getPersonalInfo();
    }
}
