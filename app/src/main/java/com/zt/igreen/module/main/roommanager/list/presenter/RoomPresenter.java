package com.zt.igreen.module.main.roommanager.list.presenter;

import com.zt.igreen.module.data.TabBean;
import com.zt.igreen.module.main.roommanager.list.contract.RoomContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.SPUtil;

import java.util.List;

public class RoomPresenter extends RoomContract.Presenter {
    @Override
    public void getRoomData() {
        mRxManage.add(Api.getRoomData(SPUtil.getToken(mContext), "ROOM_MANAGE")
                .subscribeWith(new RxSubscriber<List<TabBean>>(mContext, true) {
                    @Override
                    protected void _onNext(List<TabBean> response) {
                        mView.returnRoomData(response);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.errorRoomData(message);
                    }
                }).getDisposable());
    }
}
