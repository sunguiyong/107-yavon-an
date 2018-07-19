package com.zt.yavon.module.main.roommanager.add.contract;


import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.main.roommanager.add.model.RoomItemBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 */
public interface AddRoomContract {
    interface View {
        void returnAddRoomData(List<RoomItemBean> data);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getAddRoomData();
    }
}
