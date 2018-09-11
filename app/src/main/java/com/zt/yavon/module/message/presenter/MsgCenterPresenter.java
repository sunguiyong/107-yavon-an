package com.zt.yavon.module.message.presenter;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.MsgBean;
import com.zt.yavon.module.message.contract.MsgCenterContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

import java.util.List;

/**
 * Created by lifujun on 2018/7/27.
 */

public class MsgCenterPresenter extends MsgCenterContract.Presenter{

    @Override
    public void getNotifications(boolean showLoading) {
        mRxManage.add(Api.getNotifications(SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<List<MsgBean>>(mContext,showLoading) {
                    @Override
                    protected void _onNext(List<MsgBean> list) {
                        mView.returnDataList(list);
                    }
                    @Override
                    protected void _onError(String message) {
                        mView.returnDataList(null);
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
