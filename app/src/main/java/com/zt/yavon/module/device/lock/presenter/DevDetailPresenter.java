package com.zt.yavon.module.device.lock.presenter;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.DevDetailBean;
import com.zt.yavon.module.device.lock.contract.DevDetailContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

/**
 * Created by hp on 2018/6/13.
 */

public class DevDetailPresenter extends DevDetailContract.Presenter {

    @Override
    public void getDevDetail(String machine_id) {
        mRxManage.add(Api.getDevDetail(machine_id,SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<DevDetailBean>(mContext,true) {
                    @Override
                    protected void _onNext(DevDetailBean bean) {
                        mView.returnDevDetail(bean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void switchDev(String machine_id,boolean isOn) {
        mRxManage.add(Api.switchDev(machine_id,SPUtil.getToken(mContext),isOn?"ON":"OFF")
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext,false) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
