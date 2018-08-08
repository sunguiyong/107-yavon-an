package com.zt.yavon.module.main.roommanager.list.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.main.roommanager.add.view.ActAllRoom;
import com.zt.yavon.module.main.roommanager.detail.ActRoomDetail;
import com.zt.yavon.module.main.roommanager.list.adapter.RvRoom;
import com.zt.yavon.module.main.roommanager.list.contract.RoomContract;
import com.zt.yavon.module.main.roommanager.list.presenter.RoomPresenter;

import java.util.List;

import butterknife.BindView;

public class RoomActivity extends BaseActivity<RoomPresenter> implements RoomContract.View {

    private static final int REQUEST_CODE_ADD_ROOM = 2001;
    private static final int REQUEST_CODE_MODIFY_ROOM = 2002;

    @BindView(R.id.rv_room)
    RvRoom rvRoom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_ADD_ROOM || requestCode == REQUEST_CODE_MODIFY_ROOM) {
                mPresenter.getRoomData();
                setResult(RESULT_OK);
            }
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

    private int mSelectPosition = -1;

    @Override
    public void initView() {
        setTitle("房间管理");
        setRightMenuText("添加房间");
        findViewById(R.id.tv_right_header).setOnClickListener(v -> {
            startActForResult(ActAllRoom.class, REQUEST_CODE_ADD_ROOM);
        });
        rvRoom.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                mSelectPosition = position;
                startActForResult(ActRoomDetail.class, REQUEST_CODE_MODIFY_ROOM, (TabBean) adapter.getItem(position));
            }
        });
        mPresenter.getRoomData();
    }

    @Override
    public void returnRoomData(List<TabBean> data) {
        rvRoom.setData(data);
    }

    @Override
    public void errorRoomData(String message) {
        ToastUtil.showLong(this, message);
        finish();
    }
}
