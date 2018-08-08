package com.zt.yavon.module.main.roommanager.add.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.main.roommanager.add.adapter.RvRoomAdd;
import com.zt.yavon.module.main.roommanager.add.contract.AllRoomContract;
import com.zt.yavon.module.main.roommanager.add.model.RoomItemBean;
import com.zt.yavon.module.main.roommanager.add.presenter.AllRoomPresenter;
import com.zt.yavon.module.main.roommanager.setting.view.ActRoomSetting;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * Author: Administrator
 * Date: 2018/7/17
 */
public class ActAllRoom extends BaseActivity<AllRoomPresenter> implements AllRoomContract.View {
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
        setTitle("添加房间");
        rvRoomAdd.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                rvRoomAdd.onItemCheck(position);
                startActForResult(ActRoomSetting.class, (Serializable) adapter.getItem(position));
            }
        });
//        btnOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (rvRoomAdd.mCheckedPosition != -1 && rvRoomAdd.mCheckedPosition != rvRoomAdd.mAdapter.getItemCount() - 1) {
//                    setResult(RESULT_OK, rvRoomAdd.mAdapter.getItem(rvRoomAdd.mCheckedPosition));
//                    finish();
//                }
//            }
//        });
        mPresenter.getAddRoomData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_COMMON && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data.getSerializableExtra(EXTRA_COMMON_DATA_BEAN));
            finish();
        }
    }

    @Override
    public void returnAddRoomData(List<RoomItemBean> data) {
        rvRoomAdd.setData(data);
    }

    @Override
    public void errorAddRoomData(String message) {
        rvRoomAdd.onGetDataFail(message, false);
    }
}
