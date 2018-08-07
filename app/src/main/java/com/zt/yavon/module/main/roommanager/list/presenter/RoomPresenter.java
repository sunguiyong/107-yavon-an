//package com.zt.yavon.module.main.roommanager.list.presenter;
//
//import com.zt.yavon.module.main.roommanager.list.contract.RoomContract;
//import com.zt.yavon.module.main.roommanager.list.model.RoomBean;
//import com.zt.yavon.network.Api;
//import com.zt.yavon.network.RxSubscriber;
//import com.zt.yavon.utils.SPUtil;
//
//import java.util.List;
//
//public class RoomPresenter extends RoomContract.Presenter {
//    @Override
//    public void getRoomData() {
//        mRxManage.add(Api.getRoomData(SPUtil.getToken(mContext), "ROOM_MANAGE")
//                .subscribeWith(new RxSubscriber<List<RoomBean>>(mContext, true) {
//                    @Override
//                    protected void _onNext(List<RoomBean> response) {
//                        mView.returnRoomData(response);
//                    }
//
//                    @Override
//                    protected void _onError(String message) {
//                        mView.errorRoomData(message);
//                    }
//                }).getDisposable());
//    }
//}
