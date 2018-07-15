package com.zt.yavon.module.roommanager.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.roommanager.model.RoomBean;

import java.util.List;

public interface RoomContract {
    interface View {
        void returnRoomData(List<RoomBean> data);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getRoomData();
    }
}
