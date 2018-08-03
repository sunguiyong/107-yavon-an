package com.zt.yavon.module.deviceconnect.presenter;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.deviceconnect.contract.DevTypeContract;
import com.zt.yavon.network.Api2;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.network.YSBResponse;

/**
 * Created by hp on 2018/6/13.
 */

public class DevTypePresenter extends DevTypeContract.Presenter {

    @Override
    public void getToken() {
        mRxManage.add(Api2.getToken()
                .subscribeWith(new RxSubscriber<YSBResponse>(mContext,true) {
                    @Override
                    protected void _onNext(YSBResponse response) {
                        mView.getTokenSuccess(response.getHeader());
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void addScanLock(String author, String scanString) {
        mRxManage.add(Api2.addScanLock(author,scanString)
                .subscribeWith(new RxSubscriber<YSBResponse>(mContext,true) {
                    @Override
                    protected void _onNext(YSBResponse response) {
                        mView.addScanLockSuccess(response.getLock_id());
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void getLockSN(String author, String scanString) {
        mRxManage.add(Api2.getLockSN(author,scanString)
                .subscribeWith(new RxSubscriber<YSBResponse>(mContext,true) {
                    @Override
                    protected void _onNext(YSBResponse response) {
                        mView.getLockSNSuccess(response);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void getLockPwd(String author, String sn) {
        mRxManage.add(Api2.getLockPwd(author,sn)
                .subscribeWith(new RxSubscriber<YSBResponse>(mContext,true) {
                    @Override
                    protected void _onNext(YSBResponse response) {
                        mView.returnLockPwd(response);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }


}
