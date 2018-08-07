package com.zt.yavon.module.main.roommanager.setting.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.main.roommanager.add.model.RoomItemBean;
import com.zt.yavon.module.main.roommanager.selecticon.view.ActSelectIcon;
import com.zt.yavon.module.main.roommanager.setting.contract.RoomSettingContract;
import com.zt.yavon.module.main.roommanager.setting.presenter.RoomSettingPresenter;
import com.zt.yavon.utils.DialogUtil;

import butterknife.BindView;

public class ActRoomSetting extends BaseActivity<RoomSettingPresenter> implements RoomSettingContract.View {
    RoomItemBean mRoomItemBean;
    @BindView(R.id.tv_room_name)
    TextView tvRoomName;
    @BindView(R.id.iv_room)
    ImageView ivRoom;
    @BindView(R.id.tv_select_room_icon)
    TextView tvSelectRoomIcon;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.fl_icon)
    FrameLayout flIcon;

    RoomItemBean mSelectRoomItemBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_room_setting_layout;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle("自定义房间");
        mRoomItemBean = (RoomItemBean) getIntent().getSerializableExtra(EXTRA_COMMON_DATA_BEAN);
        if (mRoomItemBean.id == 0) {
            mRoomItemBean = null;
        }
        if (mRoomItemBean == null) {
            ivRoom.setVisibility(View.GONE);
            tvSelectRoomIcon.setVisibility(View.VISIBLE);
        } else {
            ivRoom.setVisibility(View.VISIBLE);
            tvSelectRoomIcon.setVisibility(View.GONE);
            Glide.with(this).load(mRoomItemBean.icon_select).into(ivRoom);
        }
        tvRoomName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.createEtDialog(ActRoomSetting.this, "重命名", mRoomItemBean.name, new DialogUtil.OnComfirmListening2() {
                    @Override
                    public void confirm(String data) {
                        tvRoomName.setText(data);
                    }
                });
            }
        });
        flIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActForResult(ActSelectIcon.class);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tvRoomName.getText().toString()) || tvRoomName.getText().toString().equals("请输入房间名称")) {
                    ToastUtil.showLong(ActRoomSetting.this, "请输入房间名");
                    return;
                }
                if (mSelectRoomItemBean == null && mRoomItemBean == null) {
                    ToastUtil.showLong(ActRoomSetting.this, "请选择房间图标");
                    return;
                }
                int resId;
                if (mSelectRoomItemBean != null) {
                    resId = mSelectRoomItemBean.id;
                } else {
                    resId = mRoomItemBean.id;
                }
                mPresenter.addRoom(tvRoomName.getText().toString(), resId);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_COMMON && resultCode == RESULT_OK) {
            mSelectRoomItemBean = (RoomItemBean) data.getSerializableExtra(EXTRA_COMMON_DATA_BEAN);
            Glide.with(this).load(mSelectRoomItemBean.icon_select).into(ivRoom);
            tvSelectRoomIcon.setVisibility(View.GONE);
            ivRoom.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void returnAddRoomData(RoomItemBean bean) {
        if (bean == null || bean.id == 0) {
            errorAddRoomData("添加失败，请重试");
        } else {
            setResult(RESULT_OK, bean);
            finish();
        }
    }

    @Override
    public void errorAddRoomData(String message) {
        ToastUtil.showLong(this, message);
    }
}
