package com.zt.igreen.module.device.health.contract;

import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.MedicalBean;
import com.zt.igreen.module.data.ZUIJINBean;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface MedicalContract {
    interface View{
        void returnMedical(MedicalBean bean);
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void getMedical(String history_id);

    }
}
