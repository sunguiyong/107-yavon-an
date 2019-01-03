package com.zt.yavon.module.device.desk.presenter;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.DevDetailBean;
import com.zt.yavon.module.data.DocBean;
import com.zt.yavon.module.device.desk.contract.DeskSettingContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

/**
 * Created by hp on 2018/6/13.
 */

public class DeskSettingPresenter extends DeskSettingContract.Presenter {

    @Override
    public void setSeatTime(String machine_id, String hour) {
        mRxManage.add(Api.setSeatTime(SPUtil.getToken(mContext),machine_id,hour)
                .subscribeWith(new RxSubscriber<DevDetailBean>(mContext,true) {
                    @Override
                    protected void _onNext(DevDetailBean bean) {
                        mView.setTimeSuccess(hour);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void setSeatSwitch(String machine_id, boolean isOn) {
        mRxManage.add(Api.setDeskRemindSwitch(machine_id,SPUtil.getToken(mContext),isOn?"1":"0")
                .subscribeWith(new RxSubscriber<DevDetailBean>(mContext,true) {
                    @Override
                    protected void _onNext(DevDetailBean bean) {
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
