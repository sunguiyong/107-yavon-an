package com.zt.yavon.module.main.roommanager.list.presenter;

import com.zt.yavon.R;
import com.zt.yavon.module.main.roommanager.list.contract.RoomContract;
import com.zt.yavon.module.main.roommanager.list.model.RoomBean;

import java.util.ArrayList;
import java.util.List;

public class RoomPresenter extends RoomContract.Presenter {
    @Override
    public void getRoomData() {
        List<RoomBean> data = new ArrayList<>();
        data.add(new RoomBean("办公室", R.mipmap.ic_office_checked, 3));
        data.add(new RoomBean("会议室", R.mipmap.ic_meeting_checked, 3));
        mView.returnRoomData(data);
    }
}
