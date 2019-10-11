package com.zt.igreen.utils;

import android.content.Context;

import com.common.base.utils.LogUtil;
import com.yeeloc.elocsdk.ElocSDK;
import com.yeeloc.elocsdk.ble.BleEngine;
import com.yeeloc.elocsdk.ble.BleStatus;
import com.yeeloc.elocsdk.ble.UnlockMode;

/**
 * Created by lifujun on 2018/8/9.
 */

public class YisuobaoSDK {
    private BleEngine engine;
    private String devId,pwd;
    private YisuobaoListener listener;
    private UnlockMode mode = UnlockMode.MODE_TOGGLE;
    public YisuobaoSDK(Context context, String devId, String pwd,YisuobaoListener listener){
        LogUtil.d("=========init sdk,devId:"+devId+",pwd:"+pwd);
        this.devId = devId;
        this.pwd = pwd;
        this.listener = listener;
        engine = ElocSDK.getBleEngine(context);
        engine.setGlobalCallback(new BleEngine.Callback() {
            @Override
            public void onReceive(int status, Object data) {
                switch (status) {
                    case BleStatus.BATTERY_POWER:
                        LogUtil.d("============BATTERY_POWER,data:" + data);
                        if(listener != null){
                            if(data instanceof Integer)
                            listener.onBatteryPowerChanged(data+"");
                        }
                        break;
                    case BleStatus.LOCK_COMPLETE:
                        getBattery();
                        LogUtil.d("============LOCK_COMPLETE,data:" + data);
                        if(listener != null){
                            listener.onLockStateChanged(false);
                        }
                        break;
                    case BleStatus.UNLOCK_COMPLETE:
                        if(listener != null){
                            listener.onLockStateChanged(true);
                        }
                        LogUtil.d("============UNLOCK_COMPLETE,data:" + data);
                        getBattery();
                        break;
                    default:
                }
            }
        });
    }
    public void getLockState(){
        if(engine != null)
        engine.getLockState(devId, pwd, new BleEngine.Callback() {
            @Override
            public void onReceive(int status, Object data) {
                if(status == BleStatus.LOCK_STATE){
                    getBattery();
                    LogUtil.d("============LOCK_STATE,data:" + data);
                    if(listener != null){
                        listener.onLockStateChanged((Boolean) data);
                    }
                }
            }
        });
    }
    private void getBattery(){
        if(engine != null)
            engine.getBatteryPower(devId, pwd, null);
    }
    public void autoLowBatUnLock(boolean autoUnlock){
        if(engine !=  null){
            engine.setAutoUnlockForLowBattery(devId, pwd, autoUnlock, null);
        }
    }
    public void autoLock(boolean isOn){
        mode = isOn?UnlockMode.MODE_AUTO:UnlockMode.MODE_TOGGLE;
    }
    public static class YisuobaoListener{
        public void onBatteryPowerChanged(String power){}
        public void onLockStateChanged(boolean isOpen){}
    }
    public void switchLock(){
        if(engine != null)
        engine.unlock(devId, pwd, mode,null);
    }
    public void release(){
        if(engine != null)
            engine.release();
    }
}
