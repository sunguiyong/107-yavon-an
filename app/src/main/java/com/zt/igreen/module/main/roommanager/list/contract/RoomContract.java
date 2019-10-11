package com.zt.igreen.module.main.roommanager.list.contract;

import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.TabBean;

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
