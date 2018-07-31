package com.zt.yavon.module.deviceconnect.presenter;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.DevTypeBean;
import com.zt.yavon.module.deviceconnect.contract.AddDevContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class AddDevPresenter extends AddDevContract.Presenter {

    @Override
    public void getMachineTypes() {
        mRxManage.add(Api.getMachineTypes(SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<List<DevTypeBean>>(mContext,true) {
                    @Override
                    protected void _onNext(List<DevTypeBean> list) {
                        mView.returnMachine(list);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
