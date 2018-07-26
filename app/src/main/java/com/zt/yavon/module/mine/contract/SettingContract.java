package com.zt.yavon.module.mine.contract;

import com.zt.yavon.component.BasePresenter;

/**
 * Created by lifujun on 2018/7/25.
 */

public interface SettingContract {
    interface View {
        void switchUpdateResult(boolean isSuccess);
        void switchMsgResult(boolean isSuccess);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void switchUpdate(boolean isOn);
        public abstract void switchMsg(boolean isOn);
    }
}
