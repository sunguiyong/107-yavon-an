package com.zt.yavon.module.deviceconnect.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.network.YSBResponse;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface DevTypeContract {
    interface View{
        void getTokenSuccess(String token);
        void getLockSNSuccess(YSBResponse response);
        void returnAssetNumb(String assetNumb);
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void getLockSN(String author,String scanString);
        public abstract void getAssetNumber(String mac, String type);
        public abstract void getToken();
    }
}
