package com.zt.igreen.module.mine.presenter;

import com.common.base.utils.ToastUtil;
import com.zt.igreen.module.data.LoginBean;
import com.zt.igreen.module.mine.contract.SettingContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.SPUtil;

/**
 * Created by hp on 2018/6/13.
 */

public class SettingPresenter extends SettingContract.Presenter {

    @Override
    public void switchUpdate(boolean isOn) {
        mRxManage.add(Api.sysSetting(SPUtil.getToken(mContext),null,isOn?"1":"0")
                .subscribeWith(new RxSubscriber<LoginBean>(mContext,false) {
                    @Override
                    protected void _onNext(LoginBean bean) {
                        mView.switchUpdateResult(true);
                    }
                    @Override
                    protected void _onError(String message) {
                        mView.switchUpdateResult(false);
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void switchMsg(boolean isOn) {
        mRxManage.add(Api.sysSetting(SPUtil.getToken(mContext),isOn?"1":"0",null)
                .subscribeWith(new RxSubscriber<LoginBean>(mContext,false) {
                    @Override
                    protected void _onNext(LoginBean bean) {
                        mView.switchMsgResult(true);
                    }
                    @Override
                    protected void _onError(String message) {
                        mView.switchMsgResult(false);
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
