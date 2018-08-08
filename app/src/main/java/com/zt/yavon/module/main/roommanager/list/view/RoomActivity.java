package com.zt.yavon.module.main.roommanager.list.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.main.roommanager.add.model.RoomItemBean;
import com.zt.yavon.module.main.roommanager.add.view.ActAllRoom;
import com.zt.yavon.module.main.roommanager.detail.ActRoomDetail;
import com.zt.yavon.module.main.roommanager.list.adapter.RvRoom;

import java.util.List;

import butterknife.BindView;

public class RoomActivity extends BaseActivity {

    @BindView(R.id.rv_room)
    RvRoom rvRoom;

    List<TabBean> mTabBeans;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTabBeans = (List<TabBean>) getIntent().getSerializableExtra(EXTRA_COMMON_DATA_BEAN);
        rvRoom.setData(mTabBeans);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_COMMON && resultCode == RESULT_OK) {
            RoomItemBean item = (RoomItemBean) data.getSerializableExtra(EXTRA_COMMON_DATA_BEAN);
            TabBean localBean = new TabBean();
            localBean.id = item.id;
            localBean.name = item.name;
            localBean.icon_select = item.icon_select;
            localBean.icon = item.icon;
            rvRoom.addData(localBean);
            setResult(RESULT_OK, item);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_room_layout;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        setTitle("房间管理");
        setRightMenuText("添加房间");
        findViewById(R.id.tv_right_header).setOnClickListener(v -> {
            startActForResult(ActAllRoom.class);
        });
        rvRoom.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startAct(ActRoomDetail.class, (TabBean) adapter.getItem(position));
            }
        });
    }
}
