package com.zt.igreen.module.mine.presenter;

import com.common.base.utils.ToastUtil;
import com.zt.igreen.module.data.DocBean;
import com.zt.igreen.module.data.LoginBean;
import com.zt.igreen.module.mine.contract.MineContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.SPUtil;

/**
 * Created by hp on 2018/6/13.
 */

public class MinePresenter extends MineContract.Presenter {

    @Override
    public void getPersonalInfo() {
        mRxManage.add(Api.personalInfo(SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<LoginBean>(mContext,false) {
                    @Override
                    protected void _onNext(LoginBean bean) {
                        mView.returnPersonalInfo(bean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
    @Override
    public void getDoc(String type) {
        mRxManage.add(Api.getDoc(SPUtil.getToken(mContext),type)
                .subscribeWith(new RxSubscriber<DocBean>(mContext,true) {
                    @Override
                    protected void _onNext(DocBean bean) {
                        mView.returnDoc(bean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
