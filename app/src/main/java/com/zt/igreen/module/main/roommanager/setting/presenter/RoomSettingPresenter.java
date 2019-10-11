package com.zt.igreen.module.main.roommanager.setting.presenter;

import com.zt.igreen.module.main.roommanager.add.model.RoomItemBean;
import com.zt.igreen.module.main.roommanager.setting.contract.RoomSettingContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.SPUtil;

/**
 * Author: Administrator
 * Date: 2018/7/17
 */
public class RoomSettingPresenter extends RoomSettingContract.Presenter {
    @Override
    public void addRoom(String roomName, int roomResId) {
        mRxManage.add(
                Api.addRoom(SPUtil.getToken(mContext), roomName, roomResId)
                        .subscribeWith(new RxSubscriber<RoomItemBean>(mContext, true) {
                            @Override
                            protected void _onNext(RoomItemBean bean) {
                                mView.returnAddRoomData(bean);
                            }

                            @Override
                            protected void _onError(String message) {
                                mView.errorAddRoomData(message);
                            }
                        })
                        .getDisposable()
        );
    }
}
