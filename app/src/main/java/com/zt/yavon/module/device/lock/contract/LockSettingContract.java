package com.zt.yavon.module.device.lock.contract;

import com.zt.yavon.component.BasePresenter;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface LockSettingContract {
    interface View{
        void autoLockSuccess(boolean auto_lock);
        void lowBatteryUnlockSuccess(boolean lowpower_hand_unlock);
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void autoLock(String machine_id,boolean auto_lock);
        public abstract void lowBatteryUnlock(String machine_id,boolean lowpower_hand_unlock);
    }
}
