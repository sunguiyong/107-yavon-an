package com.zt.igreen.module.device.health.presenter;

import com.common.base.rx.BaseResponse;
import com.zt.igreen.module.data.WeatherBean;
import com.zt.igreen.module.data.ZUIJINBean;
import com.zt.igreen.module.device.health.contract.HeanthContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.SPUtil;

/**
 * Created by hp on 2018/6/13.
 */

public class HeathPresenter extends HeanthContract.Presenter {
    @Override
    public void getHeanth(String machine_id) {
        mRxManage.add(Api.getZUIJIN(SPUtil.getToken(mContext),machine_id)
                .subscribeWith(new RxSubscriber<ZUIJINBean>(mContext, true) {
                    @Override
                    protected void _onNext(ZUIJINBean bean) {
                        mView.returnHeanth(bean);
                    }

                    @Override
                    protected void _onError(String message) {
                    }
                }).getDisposable());
    }
}
