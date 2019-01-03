package com.zt.yavon.module.device.desk.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.DocBean;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface DeskSettingContract {
    interface View{
        void setTimeSuccess(String hour);
        void returnDoc(DocBean bean);
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void setSeatTime(String machine_id,String hour);
        public abstract void setSeatSwitch(String machine_id,boolean isOn);
        public abstract void getDoc(String type);
    }
}
