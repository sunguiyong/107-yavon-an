package com.zt.yavon.module.device.desk.presenter;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.UserRecordBean;
import com.zt.yavon.module.device.desk.contract.DevUseRecoderContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

/**
 * Created by hp on 2018/6/13.
 */

public class DevUseRecordPresenter extends DevUseRecoderContract.Presenter {


    @Override
    public void getUseRecord(String machine_id, int page, int perPage) {
        mRxManage.add(Api.getUseRecord(machine_id,SPUtil.getToken(mContext),page,perPage)
                .subscribeWith(new RxSubscriber<UserRecordBean>(mContext,true) {
                    @Override
                    protected void _onNext(UserRecordBean bean) {
                        mView.returnUseRecord(bean);
                    }
                    @Override
                    protected void _onError(String message) {
                        mView.returnUseRecord(null);
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
