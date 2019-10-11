package com.zt.igreen.module.main.adddevice.presenter;

import com.common.base.rx.BaseResponse;
import com.zt.igreen.module.main.adddevice.contract.AddDeviceContract;
import com.zt.igreen.module.main.adddevice.model.AddDeviceBean;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.SPUtil;

import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class AddDevicePresenter extends AddDeviceContract.Presenter {
    @Override
    public void getAddDeviceData() {

        mRxManage.add(Api.getAddDeviceData(SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<List<AddDeviceBean>>(mContext, true) {
                    @Override
                    protected void _onNext(List<AddDeviceBean> response) {
                        mView.returnAddDeviceData(response);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.errorAddDeviceData(message);
                    }
                }).getDisposable());
    }

    @Override
    public void setAddDeviceData(String selectMachineIds) {
        mRxManage.add(Api.setAddDeviceData(SPUtil.getToken(mContext), selectMachineIds)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext, true) {
                    @Override
                    protected void _onNext(BaseResponse baseResponse) {
                        mView.returnSetDeviceData();
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.errorSetDeviceData(message);
                    }
                }).getDisposable());
    }
}
