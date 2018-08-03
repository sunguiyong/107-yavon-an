package com.zt.yavon.module.device.lamp.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.DevDetailBean;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface LampDetailContract {
    interface View{
        void returnDevDetail(DevDetailBean bean);
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void getDevDetail(String machine_id);
        public abstract void switchDev(String machine_id,boolean isOn);
    }
}
