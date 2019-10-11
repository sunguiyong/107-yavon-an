package com.zt.igreen.module.main.roommanager.add.contract;


import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.main.roommanager.add.model.RoomItemBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 */
public interface AllRoomContract {
    interface View {
        void returnAddRoomData(List<RoomItemBean> data);
        void errorAddRoomData(String message);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getAddRoomData();
    }
}
