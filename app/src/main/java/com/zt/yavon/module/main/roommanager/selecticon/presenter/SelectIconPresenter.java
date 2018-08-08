package com.zt.yavon.module.main.roommanager.selecticon.presenter;

import com.zt.yavon.module.main.roommanager.add.model.RoomItemBean;
import com.zt.yavon.module.main.roommanager.selecticon.contract.SelectIconContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Administrator
 * Date: 2018/7/17
 */
public class SelectIconPresenter extends SelectIconContract.Presenter {
    @Override
    public void getAddRoomData() {
        mRxManage.add(Api.getAllRoomData(SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<List<RoomItemBean>>(mContext, true) {
                    @Override
                    protected void _onNext(List<RoomItemBean> response) {
                        if (response == null) {
                            response = new ArrayList<>();
                        }
                        mView.returnAddRoomData(response);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.errorAddRoomData(message);
                    }
                }).getDisposable());
    }
}
