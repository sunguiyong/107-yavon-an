package com.zt.yavon.component;

import android.content.Context;

import com.common.base.rx.RxManager;


public abstract class BasePresenter<T>{
    public Context mContext;
    public T mView;
    public RxManager mRxManage = new RxManager();

    public void setVM(T v) {
        this.mView = v;
//        this.onStart();
    }
//    public void onStart(){}
    public void onDestroy() {
        mRxManage.clear();
    }
}
