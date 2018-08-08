package com.zt.yavon.module.main.roommanager.list.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.TabBean;

import java.util.List;

public interface RoomContract {
    interface View {
        void returnRoomData(List<TabBean> data);

        void errorRoomData(String message);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getRoomData();
    }
}
