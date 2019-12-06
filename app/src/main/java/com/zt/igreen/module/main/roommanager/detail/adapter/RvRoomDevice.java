package com.zt.igreen.module.main.roommanager.detail.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.igreen.R;
import com.zt.igreen.module.main.roommanager.detail.ActRoomDetail;
import com.zt.igreen.module.main.roommanager.detail.model.RoomDetailBean;
import com.zt.igreen.utils.DialogUtil;
import com.zt.igreen.widget.RvBase;

public class RvRoomDevice extends RvBase<RoomDetailBean.MachinesBean> {
    public int mSelectIndex = -1;
    Dialog dialog;

    public RvRoomDevice(Context context) {
        super(context);
    }

    public RvRoomDevice(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RvRoomDevice(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public LayoutManager customSetLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    @Override
    public int customSetItemLayoutId() {
        return R.layout.rv_room_device_item_layout;
    }

    @Override
    public void customConvert(BaseViewHolder holder, RoomDetailBean.MachinesBean bean) {
        holder.setText(R.id.tv_name, bean.name)
                .setText(R.id.tv_status, bean.isPowerOn() ? "设备开启" : "设备关闭");
        Glide.with(getContext()).load(bean.icon).into((ImageView) holder.getView(R.id.iv_icon));
        holder.setOnClickListener(R.id.iv_del, new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectIndex = holder.getLayoutPosition();
                DialogUtil.dismiss(dialog);
                dialog = DialogUtil.create2BtnInfoDialog((ActRoomDetail) getContext(), "确定删除吗？", "取消",
                        "确定", new DialogUtil.OnComfirmListening() {
                            @Override
                            public void confirm() {
                                ((ActRoomDetail) getContext()).mPresenter.delDevice(bean.id);
                            }
                        });
//                ((ActRoomDetail) getContext()).mPresenter.delDevice(bean.id);
            }
        });
    }
}
