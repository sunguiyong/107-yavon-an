package com.zt.yavon.module.main.roommanager.detail.presenter;

import com.common.base.rx.BaseResponse;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.main.roommanager.detail.contract.RoomDetailContract;
import com.zt.yavon.module.main.roommanager.detail.model.RoomDetailBean;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

public class RoomDetailPresenter extends RoomDetailContract.Presenter {

    @Override
    public void modifyRoom(int roomId, String newName, int newIconId) {
        mRxManage.add(Api.modifyRoom(roomId, SPUtil.getToken(mContext), newName, newIconId)
                .subscribeWith(new RxSubscriber<TabBean>(mContext, true) {
                    @Override
                    protected void _onNext(TabBean response) {
                        mView.returnModifyRoomData(response);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.errorModifyRoomData(message);
                    }
                }).getDisposable());
    }

    @Override
    public void getRoomDetail(int roomId) {
        mRxManage.add(Api.getRoomDetail(SPUtil.getToken(mContext), roomId)
                .subscribeWith(new RxSubscriber<RoomDetailBean>(mContext, true) {
                    @Override
                    protected void _onNext(RoomDetailBean response) {
                        mView.returnRoomDetailData(response);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.errorRoomDetailData(message);
                    }
                }).getDisposable());
    }

    @Override
    public void delRoom(int roomId) {
        mRxManage.add(Api.delRoom(SPUtil.getToken(mContext), roomId)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext, true) {
                    @Override
                    protected void _onNext(BaseResponse baseResponse) {
                        mView.returnDelRoom(baseResponse);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.errorDelRoom(message);
                    }
                }).getDisposable()
        );
    }

    @Override
    public void delDevice(int deviceId) {
        mRxManage.add(Api.delDevice(SPUtil.getToken(mContext), deviceId)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext, true) {
                    @Override
                    protected void _onNext(BaseResponse baseResponse) {
                        mView.returnDelDevice(baseResponse);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.errorDelDevice(message);
                    }
                }).getDisposable()
        );
    }
}
