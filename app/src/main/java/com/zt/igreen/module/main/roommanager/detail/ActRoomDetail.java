package com.zt.igreen.module.main.roommanager.detail;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.data.TabBean;
import com.zt.igreen.module.main.roommanager.add.model.RoomItemBean;
import com.zt.igreen.module.main.roommanager.detail.adapter.RvRoomDevice;
import com.zt.igreen.module.main.roommanager.detail.contract.RoomDetailContract;
import com.zt.igreen.module.main.roommanager.detail.model.RoomDetailBean;
import com.zt.igreen.module.main.roommanager.detail.presenter.RoomDetailPresenter;
import com.zt.igreen.module.main.roommanager.selecticon.view.ActSelectIcon;
import com.zt.igreen.utils.DialogUtil;

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
    Dialog dialog;
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
        sethead(R.color.backgroundblack);
        setColor(Color.parseColor("#ffffff"));
        ImmersionBar.with(this).statusBarColor(R.color.backgroundblack).statusBarDarkFont(false)
                .flymeOSStatusBarFontColor(R.color.backgroundblack).init();
        setColor(Color.parseColor("#ffffff"));
        setTitle("房间详情");
        mTabBean = (TabBean) getIntent().getSerializableExtra(EXTRA_COMMON_DATA_BEAN);
        tvRoomName.setText(mTabBean.name);
        Glide.with(this).load(mTabBean.icon_select).into(ivRoom);
        mDeviceCount = mTabBean.getMachineSize();
        tvCount.setText("已移入" + mDeviceCount + "个设备");
        mPresenter.getRoomDetail(mTabBean.id);
        if ("DEFAULT".equals(mTabBean.type)) {
            tvRoomName.setTextColor(ContextCompat.getColor(this, R.color.white_tran));
        }
        tvRoomName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("DEFAULT".equals(mTabBean.type)) {
                    ToastUtil.showShort(ActRoomDetail.this, "不能修改默认房间名称");
                } else {
                    dialog = DialogUtil.createEtDialog(ActRoomDetail.this, false, "重命名", mTabBean.name, new DialogUtil.OnComfirmListening2() {
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
            }
        });

        ivRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("DEFAULT".equals(mTabBean.type)) {
                    ToastUtil.showShort(ActRoomDetail.this, "不能修改默认房间图标");
                } else {
                    startActForResult(ActSelectIcon.class);
                }
            }
        });
        if ("DEFAULT".equals(mTabBean.type)) {
            btnDel.setVisibility(View.GONE);
        } else {
            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtil.dismiss(dialog);
                    dialog = DialogUtil.create2BtnInfoDialog(ActRoomDetail.this, "确定删除吗？", "取消", "确定", new DialogUtil.OnComfirmListening() {
                        @Override
                        public void confirm() {
                            mPresenter.delRoom(mTabBean.id);
                        }
                    });
//                    mPresenter.delRoom(mTabBean.id);
                }
            });
        }
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
        DialogUtil.dismiss(dialog);
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

    @Override
    protected void onDestroy() {
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }
}
