package com.zt.igreen.module.main.roommanager.setting.contract;


import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.main.roommanager.add.model.RoomItemBean;

/**
 * Created by Administrator on 2017/5/20 0020.
 */
public interface RoomSettingContract {
    interface View {
        void returnAddRoomData(RoomItemBean bean);

        void errorAddRoomData(String message);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void addRoom(String s, int resId);
    }
}
