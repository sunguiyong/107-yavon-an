package com.zt.igreen.utils.tuya;

import android.util.Log;

import com.tuya.smart.android.user.api.ILoginCallback;
import com.tuya.smart.android.user.bean.User;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.home.sdk.bean.HomeBean;
import com.tuya.smart.home.sdk.callback.ITuyaGetHomeListCallback;

import java.util.List;

public class TuyaMethod {
    public static void shareDevice(String countryCode, String uid, String passwd, ILoginCallback callback) {
        TuyaHomeSdk.getUserInstance().loginOrRegisterWithUid(countryCode, uid, passwd, new ILoginCallback() {
            @Override
            public void onSuccess(User user) {
                TuyaHomeSdk.getHomeManagerInstance().queryHomeList(new ITuyaGetHomeListCallback() {
                    @Override
                    public void onSuccess(List<HomeBean> homeBeans) {
                        homeBeans.get(0).getHomeId();
                        Log.d("queryHomeList", "onSuccess: " + homeBeans.size());
                    }

                    @Override
                    public void onError(String errorCode, String error) {

                    }
                });
            }

            @Override
            public void onError(String code, String error) {

            }
        });
    }
}
