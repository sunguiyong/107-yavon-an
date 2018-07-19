package com.zt.yavon.module.main.roommanager.list.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.main.roommanager.list.model.RoomBean;

import java.util.List;

public interface RoomContract {
    interface View {
        void returnRoomData(List<RoomBean> data);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getRoomData();
    }
}
