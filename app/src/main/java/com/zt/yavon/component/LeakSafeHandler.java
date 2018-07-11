package com.zt.yavon.component;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.SoftReference;

/**
 * Created by hp on 2017/11/22.
 */
public class LeakSafeHandler<K extends BaseActivity> extends Handler {
    private SoftReference<K> activity;
    public LeakSafeHandler(K activity){
        this.activity = new SoftReference(activity);
    }
    @Override
    public void handleMessage(Message msg) {
        K mActivity = getActivity();
        if(mActivity == null){
            return;
        }
        onMessageReceived(mActivity,msg);
    }
    public K getActivity(){
        if(activity != null){
            return activity.get();
        }
        return null;
    }
    public void clean(int what){
        if(hasMessages(what)){
            removeMessages(what);
        }
    }
    public void onMessageReceived(K mActivity,Message msg){}
}
