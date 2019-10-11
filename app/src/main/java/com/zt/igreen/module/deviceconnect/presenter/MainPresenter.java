package com.zt.igreen.module.deviceconnect.presenter;

import com.common.base.utils.ToastUtil;
import com.zt.igreen.module.data.HealthInfoBean;
import com.zt.igreen.module.main.frame.contract.MainContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.SPUtil;

/**
 * Created by hp on 2018/6/13.
 */

public class MainPresenter extends MainContract.Presenter {
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
