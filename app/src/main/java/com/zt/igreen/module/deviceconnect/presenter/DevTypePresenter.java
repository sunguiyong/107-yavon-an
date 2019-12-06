package com.zt.igreen.module.deviceconnect.presenter;

import com.common.base.utils.ToastUtil;
import com.zt.igreen.module.data.AssetNumbBean;
import com.zt.igreen.module.deviceconnect.contract.DevTypeContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.Api2;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.network.YSBResponse;
import com.zt.igreen.utils.SPUtil;

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
    public void getAssetNumber(String mac, String barcode, String type) {
        mRxManage.add(Api.getAssetNumber(SPUtil.getToken(mContext),mac,barcode,type)
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
