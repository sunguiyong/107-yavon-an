package com.zt.yavon.module.main.roommanager.detail.contract;


import com.common.base.rx.BaseResponse;
import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.main.roommanager.detail.model.RoomDetailBean;

/**
 * Created by Administrator on 2017/5/20 0020.
 */
public interface RoomDetailContract {
    interface View {
        void returnModifyRoomData(TabBean data);

        void errorModifyRoomData(String message);

        void returnRoomDetailData(RoomDetailBean data);

        void errorRoomDetailData(String message);

        void returnDelRoom(BaseResponse data);

        void errorDelRoom(String message);

        void returnDelDevice(BaseResponse data);

        void errorDelDevice(String message);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void modifyRoom(int roomId, String newName, int newIconId);

        public abstract void getRoomDetail(int roomId);

        public abstract void delRoom(int roomId);

        public abstract void delDevice(int deviceId);
    }
}
