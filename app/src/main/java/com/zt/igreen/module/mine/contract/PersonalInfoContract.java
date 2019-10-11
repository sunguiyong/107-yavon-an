package com.zt.igreen.module.mine.contract;

import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.LoginBean;

/**
 * Created by lifujun on 2018/7/25.
 */

public interface PersonalInfoContract {
    interface View {
        void returnPersonalInfo(LoginBean bean);
        void modifyNickNameSuccess(String nickName);
        void uploadSuccess(String filepath);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getPersonalInfo();
        public abstract void modifyNickname(String nickName);
        public abstract void setAvatar(String filepath);
    }
}
