package com.zt.yavon.module.device.desk.contract;

import com.zt.yavon.component.BasePresenter;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface DeskSettingContract {
    interface View{
        void setTimeSuccess(String hour);
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void setSeatTime(String machine_id,String hour);
        public abstract void setSeatSwitch(String machine_id,boolean isOn);
    }
}
