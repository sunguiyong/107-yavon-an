package com.zt.yavon.module.deviceconnect.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.network.YSBResponse;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface DevTypeContract {
    interface View{
        void getTokenSuccess(String token);
        void addScanLockSuccess(String lockId);
        void getLockSNSuccess(YSBResponse response);
        void returnLockPwd(YSBResponse response);
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void getToken();
        public abstract void addScanLock(String author,String scanString);
        public abstract void getLockSN(String author,String scanString);
        public abstract void getLockPwd(String author,String sn);
    }
}
