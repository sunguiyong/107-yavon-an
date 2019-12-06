package com.zt.igreen.module.main.frame.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.igreen.R;
import com.zt.igreen.module.data.TabBean;
import com.zt.igreen.module.main.frame.view.MainActivity;
import com.zt.igreen.utils.Constants;
import com.zt.igreen.widget.RvBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 首页viewpager的自定义类
 */
public class RvDevices extends RvBase<TabBean.MachineBean> {
    private MainActivity mActivity;

    //    private OnSwitchStateChangeListener checkedChangeListener;
    public RvDevices(Context context) {
        super(context);
    }

    public RvDevices(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setEmptyViewText("没有设备", false);
    }

    public RvDevices(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //    public void setOnCheckedChangeListener(OnSwitchStateChangeListener checkedChangeListener){
//        this.checkedChangeListener = checkedChangeListener;
//    }
    @Override
    public void init(Context context) {
        super.init(context);
        mActivity = (MainActivity) context;
    }

    private boolean mIsOften;

    public void setIsOften(boolean isOften) {
        mIsOften = isOften;
    }

    @Override
    public LayoutManager customSetLayoutManager(Context context) {
        return new GridLayoutManager(context, 2);
    }

    @Override
    public int customSetItemLayoutId() {
        return R.layout.rv_device_item_layout;
    }

    @Override
    public void customConvert(BaseViewHolder holder, TabBean.MachineBean bean) {
        ImageView cbPower = holder.getView(R.id.cb_power);
        RelativeLayout firstRl = holder.getView(R.id.first_rl);
        ImageView selectImg = holder.getView(R.id.select_img);
        FrameLayout linrv = holder.getView(R.id.lin_rv);
        TextView tvStatus = holder.getView(R.id.tv_status);
        TextView tvAuthor = holder.getView(R.id.tv_author_dev);
        TextView tvAuthor1 = holder.getView(R.id.tv_author_dev1);
        TextView tvName = holder.getView(R.id.tv_name);
        TextView tvLocation = holder.getView(R.id.tv_location);
        LinearLayout llCenter = holder.getView(R.id.ll_center);
        tvAuthor1.setVisibility(GONE);
        if (mSelectMode) {
//            selectImg.setImageResource(R.drawable.selector_cb_check);
//            selectImg.setSelected(mSelectList.contains(holder.getAdapterPosition()));
//            cbPower.setVisibility(GONE);
            cbPower.setVisibility(VISIBLE);
            cbPower.setImageResource(R.drawable.selector_cb_check);
            cbPower.setSelected(mSelectList.contains(holder.getAdapterPosition()));
        } else {
            cbPower.setImageResource(R.drawable.selector_cb_power);
            cbPower.setSelected(bean.isPowerOn());
        }
        boolean onLine = "ONLINE".equals(bean.online_status);
        if (Constants.MACHINE_TYPE_ADJUST_TABLE.equals(bean.machine_type)) {//智能升降桌
            cbPower.setVisibility(mSelectMode ? View.VISIBLE : View.GONE);

//            holder.setGone(R.id.black_frame, onLine ? false : true);
            tvStatus.setText(onLine ? "设备在线" : "设备离线");
        } else if (Constants.MACHINE_TYPE_BATTERY_LOCK.equals(bean.machine_type)) {
            cbPower.setVisibility(View.VISIBLE);
//            holder.setGone(R.id.black_frame, false);
            tvStatus.setText(bean.isPowerOn() ? "设备开启" : "设备关闭");
        } else if (Constants.MACHINE_TYPE_LIGHT.equals(bean.machine_type)) {
            cbPower.setVisibility(View.VISIBLE);
            if (!onLine) {
                cbPower.setVisibility(VISIBLE);
            }
//            holder.setGone(R.id.black_frame, onLine ? false : true);
            tvStatus.setText(onLine ? (bean.isPowerOn() ? "设备开启" : "设备关闭") : "设备离线");
        } else if (Constants.MACHINE_TYPE_AIR_HEALTH.equals(bean.machine_type)) { //健康
            cbPower.setVisibility(View.GONE);//开关按钮
            String deviceId = bean.device_id;//
            String device = deviceId.substring(8, deviceId.length());
            tvStatus.setText(device);//设备开启或关闭状态
            tvAuthor1.setVisibility(VISIBLE);
            tvAuthor1.setSelected(bean.is_authorized);
            tvAuthor1.setText("未连接");
        } else if (Constants.MACHINE_TYPE_AIR_MACHINE.equals(bean.machine_type)) {//新风机
            cbPower.setVisibility(View.GONE);
            tvStatus.setText(bean.online_status.equals("ONLINE") ? "设备在线" : "设备离线");
        } else {
            cbPower.setVisibility(View.GONE);
//            holder.setGone(R.id.black_frame, false);
        }

        if ("ADMIN".equals(bean.user_type)) {
            tvAuthor.setVisibility(GONE);
        } else if (bean.isLastOne) {
            tvAuthor.setVisibility(GONE);
        } else {
            tvAuthor.setVisibility(VISIBLE);
            tvAuthor.setSelected(bean.is_authorized);
            if (bean.is_authorized) {
                tvAuthor.setText("已授权");
            } else {
                tvAuthor.setText("未授权");
            }
        }

        holder.setText(R.id.tv_name, bean.isLastOne ? "添加设备" : bean.name)
                .setGone(R.id.tv_location, true)
                .setText(R.id.tv_location, bean.isLastOne ? "" : bean.from_room);

//        && mIsOften
        holder.setGone(R.id.tv_location, !TextUtils.isEmpty(bean.from_room) && !bean.isLastOne)
                .setGone(R.id.tv_status, !bean.isLastOne);
        //如果不是常用，那么tv_location也显示，但是为空字符串
        holder.setText(R.id.tv_location, mIsOften ? bean.from_room : "");
        holder.addOnClickListener(R.id.cb_power);
        holder.addOnClickListener(R.id.select_img);
        ImageView ivIcon = holder.getView(R.id.iv_icon);

        holder.setGone(R.id.tv_location, true);

        //判断设备状态，设不同背景
        if (!bean.isLastOne) {
            llCenter.setBackground(getResources().getDrawable(R.drawable.online_bg2));
            tvLocation.setBackground(getResources().getDrawable(R.drawable.online_bg1));
//            llCenter.setBackgroundColor(getResources().getColor(R.color.officebg));
//            tvLocation.setBackgroundColor(getResources().getColor(R.color.devicebg));
        }
        if (bean.isLastOne) {
            firstRl.setVisibility(GONE);
            ivIcon.setImageResource(R.mipmap.ic_item_add);
//            linrv.setBackgroundColor(getResources().getColor(R.color.adddevicebg));
            linrv.setBackground(getResources().getDrawable(R.drawable.online_bg));

//            tvName.setTextColor(getResources().getColor(R.color.white));
        } else {
            Glide.with(getContext()).load(bean.icon).into(ivIcon);
        }

        if (tvStatus.getText().toString().equals("设备离线")) {
            llCenter.setBackground(getResources().getDrawable(R.drawable.offline_bg2));
            tvLocation.setBackground(getResources().getDrawable(R.drawable.offline_bg1));
//            tvLocation.setBackgroundColor(getResources().getColor(R.color.lixian1));
//            llCenter.setBackgroundColor(getResources().getColor(R.color.lixian));
        }
        if (tvName.getText().toString().equals("添加设备") && mIsOften && bean.isLastOne) {
            tvLocation.setVisibility(GONE);
            ivIcon.setImageResource(R.mipmap.ic_item_add);
//            linrv.setBackgroundColor(getResources().getColor(R.color.adddevicebg));
            linrv.setBackground(getResources().getDrawable(R.drawable.online_bg));

        }
    }

    public HashSet<Integer> mSelectList = new HashSet<>();
    private boolean mSelectMode = false;
    private int mSelectIndex = -1;

    public boolean isSelectMode() {
        return mSelectMode;
    }

    public int getSelectCount() {
//        int selectCount = 0;
//        for (Integer item : mSelectList) {
//            selectCount++;
//        }
//        LogUtil.d("======selectCount:"+mSelectList.size()+",data:"+ Arrays.toString(mSelectList.toArray()));
        return mSelectList.size();
    }

    public void enterMultiSelectMode(int selectIndex) {
        mSelectMode = true;
        mSelectList.clear();
        mSelectList.add(selectIndex);
        mAdapter.notifyDataSetChanged();
    }

    public void exitMultiSelectMode() {
//        mSelectIndex = -1;
        mSelectMode = false;
        mAdapter.notifyDataSetChanged();
    }

    public List<TabBean.MachineBean> getSelectBeans() {
        List<TabBean.MachineBean> beans = new ArrayList<>();
        for (int item : mSelectList) {
            beans.add(mAdapter.getItem(item));
        }
        return beans;
    }

    public boolean isSelected(int position) {
        for (int item : mSelectList) {
            if (item == position) {
                return true;
            }
        }
        return false;
    }

    public void setItemSelect(int position, boolean isSelected) {
        if (mSelectMode) {
            View view = getLayoutManager().findViewByPosition(position);
//            View checkBox = view.findViewById(R.id.cb_power);
            View checkBox = view.findViewById(R.id.select_img);

            checkBox.setSelected(isSelected);
        }
    }

    public void addSelection(int position) {
        mSelectList.add(position);
    }

    public void removeSelection(int position) {
        mSelectList.remove(position);
    }

    public void selectAll() {
//        LogUtil.d("=============size:"+mAdapter.getItemCount());
        int i = 0;
        for (TabBean.MachineBean item : mAdapter.getData()) {
            if (!item.isLastOne)
                mSelectList.add(i++);
        }
        mAdapter.notifyDataSetChanged();
    }
//    public interface OnSwitchStateChangeListener{
//        void onSwitchStateChange(boolean isChecked,TabBean.MachineBean bean);
//    }
}
