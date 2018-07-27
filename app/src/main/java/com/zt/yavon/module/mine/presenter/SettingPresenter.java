package com.zt.yavon.module.mine.presenter;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.module.mine.contract.MineContract;
import com.zt.yavon.module.mine.contract.SettingContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

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
