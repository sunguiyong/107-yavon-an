package com.zt.igreen.module.device.health.contract;

import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.MedicalBean;
import com.zt.igreen.module.data.ZUIJINBean;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface HeanthTestContract {
    interface View{
        void returnTest(MedicalBean bean);
    }
    abstract class Presenter extends BasePresenter<View>{

        public abstract void getHeanth(String machine_id,String heart_rate,String abnormal_heart_rate,String blood_oxygen,String high_blood_pressure,String low_blood_pressure,String breathing_rate,String peripheral_circulation,String heart_rate_variability,String neurological_balance,String mental_stress_level,String physical_fatigue,String vascular_health,String vascular_health_age);

    }
}
