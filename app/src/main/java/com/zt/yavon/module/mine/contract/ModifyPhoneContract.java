package com.zt.yavon.module.mine.contract;

import com.zt.yavon.component.BasePresenter;

/**
 * Created by lifujun on 2018/7/25.
 */

public interface ModifyPhoneContract {
    interface View {
        void sendCodeResult(String msg);
        void modifySuccess(String phone);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void sendCode(String account);
        public abstract void modifyPhone(String account,String code,String mobile,String mobileConfirm);
    }
}
