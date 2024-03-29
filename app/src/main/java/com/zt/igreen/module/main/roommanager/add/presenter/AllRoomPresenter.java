package com.zt.igreen.module.main.roommanager.add.presenter;

import com.zt.igreen.module.main.roommanager.add.contract.AllRoomContract;
import com.zt.igreen.module.main.roommanager.add.model.RoomItemBean;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Administrator
 * Date: 2018/7/17
 */
public class AllRoomPresenter extends AllRoomContract.Presenter {
    @Override
    public void getAddRoomData() {
        mRxManage.add(Api.getAllRoomData(SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<List<RoomItemBean>>(mContext, true) {
                    @Override
                    protected void _onNext(List<RoomItemBean> response) {
                        if (response == null) {
                            response = new ArrayList<>();
                        }
                        response.add(new RoomItemBean());
                        mView.returnAddRoomData(response);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.errorAddRoomData(message);
                    }
                }).getDisposable());
    }
}
