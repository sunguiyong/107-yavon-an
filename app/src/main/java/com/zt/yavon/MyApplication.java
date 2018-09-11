package com.zt.yavon;

import android.support.multidex.MultiDexApplication;

import com.tuya.smart.sdk.TuyaSdk;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zt.yavon.baidumap.LocationService;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by hp on 2018/6/6.
 */

public class MyApplication extends MultiDexApplication{
    private static MyApplication instance;
    public static MyApplication getInstance(){
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ZXingLibrary.initDisplayOpinion(this);
        TuyaSdk.init(this);
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);
//        TuyaSdk.setDebugMode(true);
//        LoginBean bean = SPUtil.getAccount(this);
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
