package com.zt.yavon.module.mine.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.LoginBean;

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
