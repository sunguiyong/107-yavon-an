package com.zt.igreen.module.main.roommanager.selecticon.view;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.common.base.utils.ToastUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.main.roommanager.add.model.RoomItemBean;
import com.zt.igreen.module.main.roommanager.selecticon.adapter.RvSelectIcon;
import com.zt.igreen.module.main.roommanager.selecticon.contract.SelectIconContract;
import com.zt.igreen.module.main.roommanager.selecticon.presenter.SelectIconPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Author: Administrator
 * Date: 2018/7/17
 */
public class ActSelectIcon extends BaseActivity<SelectIconPresenter> implements SelectIconContract.View {

    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.rv_room_add)
    RvSelectIcon rvSelectIcon;
    @BindView(R.id.btn_ok)
    Button btnOk;

    @Override
    public int getLayoutId() {
        return R.layout.act_select_icon_layout;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public void initView() {
        sethead(R.color.qingse);
        setColor(Color.parseColor("#ffffff"));
        ImmersionBar.with(this).statusBarColor(R.color.qingse).statusBarDarkFont(true).flymeOSStatusBarFontColor(R.color.qingse).init();
        setColor(Color.parseColor("#ffffff"));
        setTitle("选择图标");
        rvSelectIcon.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                rvSelectIcon.onItemCheck(position);
                Glide.with(ActSelectIcon.this).load(((RoomItemBean) adapter.getItem(position)).icon_select).into(ivTop);
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rvSelectIcon.mCheckedPosition != -1) {
                    setResult(RESULT_OK, rvSelectIcon.mAdapter.getItem(rvSelectIcon.mCheckedPosition));
                    finish();
                } else {
                    ToastUtil.showLong(ActSelectIcon.this, "请选择图标");
                }
            }
        });
        mPresenter.getAddRoomData();
    }

    @Override
    public void returnAddRoomData(List<RoomItemBean> data) {
        rvSelectIcon.setData(data);
    }

    @Override
    public void errorAddRoomData(String message) {
        rvSelectIcon.onGetDataFail(message, false);
    }
}
