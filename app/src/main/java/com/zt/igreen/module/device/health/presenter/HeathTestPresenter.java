package com.zt.igreen.module.device.health.presenter;

import com.zt.igreen.module.data.MedicalBean;
import com.zt.igreen.module.data.ZUIJINBean;
import com.zt.igreen.module.device.health.contract.HeanthContract;
import com.zt.igreen.module.device.health.contract.HeanthTestContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.SPUtil;

/**
 * Created by hp on 2018/6/13.
 */

public class HeathTestPresenter extends HeanthTestContract.Presenter {
    @Override
    public void getHeanth(String machine_id,String heart_rate,String abnormal_heart_rate,String blood_oxygen,String high_blood_pressure,String low_blood_pressure,String breathing_rate,String peripheral_circulation,String heart_rate_variability,String neurological_balance,String mental_stress_level,String physical_fatigue,String vascular_health,String vascular_health_age) {
        mRxManage.add(Api.getRecord(SPUtil.getToken(mContext),machine_id,heart_rate,abnormal_heart_rate,blood_oxygen,high_blood_pressure,low_blood_pressure,breathing_rate,peripheral_circulation,heart_rate_variability,neurological_balance,mental_stress_level,physical_fatigue,vascular_health,vascular_health_age)
                .subscribeWith(new RxSubscriber<MedicalBean>(mContext, true) {
                    @Override
                    protected void _onNext(MedicalBean bean) {
                        mView.returnTest(bean);
                    }

                    @Override
                    protected void _onError(String message) {
                    }
                }).getDisposable());
    }
}
