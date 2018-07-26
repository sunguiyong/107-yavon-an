package com.zt.yavon.module.mine.contract;

import com.zt.yavon.component.BasePresenter;

/**
 * Created by lifujun on 2018/7/25.
 */

public interface ModifyEmailContract {
    interface View {
        void sendCodeResult(String msg);
        void modifySuccess(String email);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void sendCode(String account);
        public abstract void modifyEmail(String account,String code,String email,String emailConfirm);
    }
}
