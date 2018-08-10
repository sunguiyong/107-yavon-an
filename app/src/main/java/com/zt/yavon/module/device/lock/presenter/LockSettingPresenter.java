package com.zt.yavon.module.device.lock.presenter;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.device.lock.contract.LockSettingContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

/**
 * Created by hp on 2018/6/13.
 */

public class LockSettingPresenter extends LockSettingContract.Presenter {


    @Override
    public void autoLock(String machine_id, boolean auto_lock) {
        mRxManage.add(Api.autoLock(machine_id,SPUtil.getToken(mContext),auto_lock?"1":"0")
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.autoLockSuccess(auto_lock);
                    }
                    @Override
                    protected void _onError(String message) {
//                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void lowBatteryUnlock(String machine_id, boolean lowpower_hand_unlock) {
        mRxManage.add(Api.lowBatteryUnlock(machine_id,SPUtil.getToken(mContext),lowpower_hand_unlock?"1":"0")
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.lowBatteryUnlockSuccess(lowpower_hand_unlock);
                    }
                    @Override
                    protected void _onError(String message) {
//                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
