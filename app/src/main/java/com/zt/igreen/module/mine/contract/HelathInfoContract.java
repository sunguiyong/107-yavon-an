package com.zt.igreen.module.mine.contract;

import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.DocBean;
import com.zt.igreen.module.data.HealthInfoBean;

/**
 * Created by Administrator on 2019/4/7 0007.
 */

public interface HelathInfoContract {
    interface View {
        void returnINfo(HealthInfoBean bean);
        void returnUserINfo(HealthInfoBean bean);
    }

    public abstract class Presenter extends BasePresenter<HelathInfoContract.View> {
        public abstract void getInfo(String birthday,String height,String weight,String sex,String is_high_blood_pressure,String is_heart_disease,String is_diabetes,String smoke,String drink,String diet,String work_type);
        public abstract void getUserINfo();
    }
}
