package com.zt.yavon.module.main.roommanager.detail;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.main.roommanager.add.model.RoomItemBean;
import com.zt.yavon.module.main.roommanager.detail.adapter.RvRoomDevice;
import com.zt.yavon.module.main.roommanager.detail.contract.RoomDetailContract;
import com.zt.yavon.module.main.roommanager.detail.model.RoomDetailBean;
import com.zt.yavon.module.main.roommanager.detail.presenter.RoomDetailPresenter;
import com.zt.yavon.module.main.roommanager.selecticon.view.ActSelectIcon;
import com.zt.yavon.utils.DialogUtil;

import butterknife.BindView;

public class ActRoomDetail extends BaseActivity<RoomDetailPresenter> implements RoomDetailContract.View {
    @BindView(R.id.tv_room_name)
    TextView tvRoomName;
    @BindView(R.id.iv_room)
    ImageView ivRoom;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.rv_room_device)
    RvRoomDevice rvRoomDevice;
    @BindView(R.id.btn_del)
    Button btnDel;

    TabBean mTabBean;
    RoomDetailBean mRoomDetailBean;
    int mTempIconId;
    int mDeviceCount;

    @Override
    public int getLayoutId() {
        return R.layout.act_room_detail_layout;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public void initView() {
        setTitle("房间详情");
        mTabBean = (TabBean) getIntent().getSerializableExtra(EXTRA_COMMON_DATA_BEAN);
        tvRoomName.setText(mTabBean.name);
        Glide.with(this).load(mTabBean.icon_select).into(ivRoom);
        mDeviceCount = mTabBean.getMachineSize();
        tvCount.setText("已移入" + mDeviceCount + "个设备");
        mPresenter.getRoomDetail(mTabBean.id);
        tvRoomName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.createEtDialog(ActRoomDetail.this, true, "重命名", mTabBean.name, new DialogUtil.OnComfirmListening2() {
                    @Override
                    public void confirm(String data) {
                        if (TextUtils.isEmpty(data)) {
                            ToastUtil.showLong(ActRoomDetail.this, "房间名不能为空");
                            return;
                        }
                        mPresenter.modifyRoom(mTabBean.id, data, mRoomDetailBean.icon_id);
                    }
                });
            }
        });

        ivRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActForResult(ActSelectIcon.class);
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.delRoom(mTabBean.id);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_COMMON && resultCode == RESULT_OK) {
            RoomItemBean item = (RoomItemBean) data.getSerializableExtra(EXTRA_COMMON_DATA_BEAN);
            mTempIconId = item.id;
            mPresenter.modifyRoom(mTabBean.id, mRoomDetailBean.name, item.id);
        }
    }

    @Override
    public void returnModifyRoomData(TabBean data) {
        setResult(RESULT_OK, data);
        mTabBean = data;
        mRoomDetailBean.name = data.name;
        mRoomDetailBean.icon_id = mTempIconId;
        tvRoomName.setText(mTabBean.name);
        Glide.with(this).load(mTabBean.icon_select).into(ivRoom);
    }

    @Override
    public void errorModifyRoomData(String message) {
        ToastUtil.showLong(this, message);
    }

    @Override
    public void returnRoomDetailData(RoomDetailBean data) {
        mRoomDetailBean = data;
        rvRoomDevice.setData(mRoomDetailBean.machines);
    }

    @Override
    public void errorRoomDetailData(String message) {
        ToastUtil.showLong(this, message);
        finish();
    }

    @Override
    public void returnDelRoom(BaseResponse data) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void errorDelRoom(String message) {
        ToastUtil.showLong(this, message);
    }

    @Override
    public void returnDelDevice(BaseResponse data) {
        rvRoomDevice.mAdapter.remove(rvRoomDevice.mSelectIndex);
        tvCount.setText("已移入" + (--mDeviceCount) + "个设备");
        setResult(RESULT_OK, mTabBean);
    }

    @Override
    public void errorDelDevice(String message) {
        ToastUtil.showLong(this, message);
    }
}
