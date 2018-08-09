package com.zt.yavon.module.deviceconnect.presenter;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.AssetNumbBean;
import com.zt.yavon.module.deviceconnect.contract.DevTypeContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.Api2;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.network.YSBResponse;
import com.zt.yavon.utils.SPUtil;

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
    public void getAssetNumber(String mac, String type) {
        mRxManage.add(Api.getAssetNumber(SPUtil.getToken(mContext),mac,type)
                .subscribeWith(new RxSubscriber<AssetNumbBean>(mContext,true) {
                    @Override
                    protected void _onNext(AssetNumbBean bean) {
                        mView.returnAssetNumb(bean.asset_number);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }


}
