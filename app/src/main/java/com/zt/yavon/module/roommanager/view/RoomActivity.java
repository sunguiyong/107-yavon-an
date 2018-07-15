package com.zt.yavon.module.roommanager.view;


import android.support.v4.content.ContextCompat;
import android.view.View;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.roommanager.adapter.RvRoom;
import com.zt.yavon.module.roommanager.contract.RoomContract;
import com.zt.yavon.module.roommanager.model.RoomBean;
import com.zt.yavon.module.roommanager.presenter.RoomPresenter;

import java.util.List;

import butterknife.BindView;

public class RoomActivity extends BaseActivity<RoomPresenter> implements RoomContract.View {

    @BindView(R.id.rv_room)
    RvRoom rvRoom;

    @Override
    public int getLayoutId() {
        return R.layout.act_room_layout;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public void initView() {
        setTitle("房间管理");
        setRightMenuText("添加房间");
        findViewById(R.id.tv_right_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mPresenter.getRoomData();
    }

    @Override
    public void returnRoomData(List<RoomBean> data) {
        rvRoom.setData(data);
    }

    @Override
    public void setThemeStyle() {
        super.setThemeStyle();
        setStatusBarColor(ContextCompat.getColor(this, R.color.black2));
    }
}
