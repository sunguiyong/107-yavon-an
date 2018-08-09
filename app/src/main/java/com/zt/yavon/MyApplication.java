package com.zt.yavon;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.tuya.smart.android.user.api.ILoginCallback;
import com.tuya.smart.android.user.bean.User;
import com.tuya.smart.sdk.TuyaSdk;
import com.tuya.smart.sdk.TuyaUser;
import com.tuya.smart.sdk.api.INeedLoginListener;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.utils.SPUtil;

/**
 * Created by hp on 2018/6/6.
 */

public class MyApplication extends MultiDexApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
        TuyaSdk.init(this);
        TuyaSdk.setDebugMode(true);
        LoginBean bean = SPUtil.getAccount(this);
//        TuyaSdk.setOnNeedLoginListener(new INeedLoginListener() {
//            @Override
//            public void onNeedLogin(Context context) {
//                if(bean != null)
//                TuyaUser.getUserInstance().loginWithUid("86",  bean.getMobile(), bean.getPwd(), new ILoginCallback() {
//                    @Override
//                    public void onSuccess(User user) {
//                    }
//
//                    @Override
//                    public void onError(String code, String error) {
//                    }
//                });
//            }
//        });
    }
}
