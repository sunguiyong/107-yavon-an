package com.zt.yavon.module.account.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.LoginBean;

/**
 * Created by lifujun on 2018/7/25.
 */

public interface LoginRegisterContract {
    interface View{
        void sendCodeResult(String msg);
        void loginRegisterSuccess(LoginBean bean);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void sendCode(String account);
        public abstract void register(String mobile,String code,String password,String confirmPwd);
        public abstract void login(String account,String password);
    }
}
