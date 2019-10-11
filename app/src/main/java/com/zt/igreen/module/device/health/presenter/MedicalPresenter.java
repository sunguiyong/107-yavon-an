package com.zt.igreen.module.device.health.presenter;

import com.zt.igreen.module.data.MedicalBean;
import com.zt.igreen.module.data.ZUIJINBean;
import com.zt.igreen.module.device.health.contract.HeanthContract;
import com.zt.igreen.module.device.health.contract.MedicalContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.SPUtil;

/**
 * Created by hp on 2018/6/13.
 */

public class MedicalPresenter extends MedicalContract.Presenter {
    @Override
    public void getMedical(String machine_id) {
        mRxManage.add(Api.getHistoryDet(machine_id,SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<MedicalBean>(mContext, true) {
                    @Override
                    protected void _onNext(MedicalBean bean) {
                        mView.returnMedical(bean);
                    }

                    @Override
                    protected void _onError(String message) {
                    }
                }).getDisposable());
    }
}
