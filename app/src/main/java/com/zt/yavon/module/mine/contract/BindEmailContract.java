package com.zt.yavon.module.mine.contract;

import com.zt.yavon.component.BasePresenter;

/**
 * Created by lifujun on 2018/7/25.
 */

public interface BindEmailContract {
    interface View {
        void bindSuccess(String email);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void bindEmail(String email);
    }
}
