package com.zt.igreen.module.mine.presenter;

import com.common.base.utils.ToastUtil;
import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.HealthInfoBean;
import com.zt.igreen.module.data.LoginBean;
import com.zt.igreen.module.mine.contract.HelathInfoContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.SPUtil;

/**
 * Created by Administrator on 2019/4/7 0007.
 */

public class HelathInfoPresenter extends HelathInfoContract.Presenter {
    @Override
    public void getInfo(String birthday, String height, String weight,String sex, String is_high_blood_pressure, String is_heart_disease, String is_diabetes, String smoke, String drink, String diet, String work_type) {
        mRxManage.add(Api.getPUTHealthInfo(SPUtil.getToken(mContext),birthday,height,weight,sex,is_high_blood_pressure,is_heart_disease,is_diabetes,smoke,drink,diet,work_type)
                .subscribeWith(new RxSubscriber<HealthInfoBean>(mContext,true) {
                    @Override
                    protected void _onNext(HealthInfoBean bean) {
                        mView.returnINfo(bean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void getUserINfo() {
        mRxManage.add(Api.getInfo(SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<HealthInfoBean>(mContext,true) {
                    @Override
                    protected void _onNext(HealthInfoBean bean) {
                        mView.returnUserINfo(bean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
