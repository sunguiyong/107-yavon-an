package com.zt.yavon.component;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.SoftReference;

/**
 * Created by hp on 2017/11/22.
 */
public class LeakSafeHandler2<K extends BaseFragment> extends Handler {
    private SoftReference<K> fragment;
    private boolean isFinish = false;
    public LeakSafeHandler2(K fragment){
        this.fragment = new SoftReference(fragment);
    }
    @Override
    public void handleMessage(Message msg) {
        if(isFinish)return;
        K mActivity = getActivity();
        if(mActivity == null){
            return;
        }
        onMessageReceived(mActivity,msg);
    }
    public K getActivity(){
        if(fragment != null){
            return fragment.get();
        }
        return null;
    }
    public void onMessageReceived(K fragment,Message msg){}
    public void clean(int what){
        if(hasMessages(what)){
            removeMessages(what);
        }
//        this.isFinish = true;
    }
    public void resume(){
        isFinish = false;
    }
}
