package com.zt.yavon.module.account.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.LoginBean;

/**
 * Created by lifujun on 2018/7/25.
 */

public interface ResetPwdContract {
    interface View{
        void sendCodeResult(String msg);
        void loginRegisterSuccess(LoginBean bean);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void sendCode(String account);
        public abstract void resetPwd(String mobile,String code,String password,String confirmPwd);
    }
}
