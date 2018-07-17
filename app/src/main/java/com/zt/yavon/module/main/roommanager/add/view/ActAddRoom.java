package com.zt.yavon.module.main.roommanager.add.view;

import android.widget.Button;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.main.roommanager.add.adapter.RvRoomAdd;
import com.zt.yavon.module.main.roommanager.add.contract.AddRoomContract;
import com.zt.yavon.module.main.roommanager.add.model.RoomItemBean;
import com.zt.yavon.module.main.roommanager.add.presenter.AddRoomPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Author: Administrator
 * Date: 2018/7/17
 */
public class ActAddRoom extends BaseActivity<AddRoomPresenter> implements AddRoomContract.View {
    @BindView(R.id.rv_room_add)
    RvRoomAdd rvRoomAdd;
    @BindView(R.id.btn_ok)
    Button btnOk;

    @Override
    public int getLayoutId() {
        return R.layout.act_add_room_layout;
    }

    @Override
    public void initPresenter() {
         mPresenter.setVM(this);
    }

    @Override
    public void initView() {
        mPresenter.getAddRoomData();
    }

    @Override
    public void returnAddRoomData(List<RoomItemBean> data) {
        rvRoomAdd.setData(RoomItemBean.data);
    }
}
