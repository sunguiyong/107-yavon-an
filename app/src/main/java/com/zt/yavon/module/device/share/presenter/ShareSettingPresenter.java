package com.zt.yavon.module.device.share.presenter;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.ShareListBean;
import com.zt.yavon.module.device.share.contract.ShareSettingContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

/**
 * Created by hp on 2018/6/13.
 */

public class ShareSettingPresenter extends ShareSettingContract.Presenter {

    @Override
    public void getShareList(String machineId) {
        mRxManage.add(Api.getShareList(SPUtil.getToken(mContext),machineId)
                .subscribeWith(new RxSubscriber<ShareListBean>(mContext,true) {
                    @Override
                    protected void _onNext(ShareListBean bean) {
                        mView.returnShareList(bean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void cancleDevShare(String machineId, ShareListBean.User user) {
        mRxManage.add(Api.cancleDevShare(SPUtil.getToken(mContext),machineId,user.user_id)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.cancleDevShareSuccess(user);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
