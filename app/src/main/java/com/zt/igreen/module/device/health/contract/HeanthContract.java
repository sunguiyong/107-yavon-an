package com.zt.igreen.module.device.health.contract;

import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.MedicalBean;
import com.zt.igreen.module.data.WeatherBean;
import com.zt.igreen.module.data.ZUIJINBean;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface HeanthContract {
    interface View{
        void returnHeanth(ZUIJINBean bean);
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void getHeanth(String machine_id);
    }
}
