package com.zt.igreen.module.mine.contract;

import com.zt.igreen.component.BasePresenter;

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
