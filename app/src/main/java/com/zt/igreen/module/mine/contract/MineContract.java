package com.zt.igreen.module.mine.contract;

import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.DocBean;
import com.zt.igreen.module.data.LoginBean;

/**
 * Created by lifujun on 2018/7/25.
 */

public interface MineContract {
    interface View {
        void returnPersonalInfo(LoginBean bean);
        void returnDoc(DocBean bean);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getPersonalInfo();
        public abstract void getDoc(String type);
    }
}
