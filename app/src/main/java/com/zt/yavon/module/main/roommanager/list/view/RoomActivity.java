package com.zt.yavon.module.main.roommanager.list.view;


import android.content.Intent;
import android.support.v4.content.ContextCompat;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.main.roommanager.add.model.RoomItemBean;
import com.zt.yavon.module.main.roommanager.add.view.ActAddRoom;
import com.zt.yavon.module.main.roommanager.list.adapter.RvRoom;
import com.zt.yavon.module.main.roommanager.list.contract.RoomContract;
import com.zt.yavon.module.main.roommanager.list.model.RoomBean;
import com.zt.yavon.module.main.roommanager.list.presenter.RoomPresenter;

import java.util.List;

import butterknife.BindView;

public class RoomActivity extends BaseActivity<RoomPresenter> implements RoomContract.View {

    @BindView(R.id.rv_room)
    RvRoom rvRoom;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_COMMON && resultCode == RESULT_OK) {
            RoomItemBean item = (RoomItemBean) data.getSerializableExtra(EXTRA_COMMON_DATA_BEAN);
            rvRoom.addData(new RoomBean(item.mName, item.mResId, 2));
        }
    }

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
        findViewById(R.id.tv_right_header).setOnClickListener(v -> {
            startActForResult(ActAddRoom.class);
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
