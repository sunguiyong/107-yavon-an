package com.zt.yavon;

import android.support.multidex.MultiDexApplication;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by hp on 2018/6/6.
 */

public class MyApplication extends MultiDexApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
    }
}
