package com.zt.yavon.module.main.roommanager.add.presenter;

import com.zt.yavon.module.main.roommanager.add.contract.AddRoomContract;
import com.zt.yavon.module.main.roommanager.add.model.RoomItemBean;

/**
 * Author: Administrator
 * Date: 2018/7/17
 */
public class AddRoomPresenter extends AddRoomContract.Presenter {
    @Override
    public void getAddRoomData() {
        mView.returnAddRoomData(RoomItemBean.data);
    }
}
