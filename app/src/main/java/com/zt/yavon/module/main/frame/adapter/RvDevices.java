package com.zt.yavon.module.main.frame.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.main.frame.view.FmtDevice;
import com.zt.yavon.module.main.frame.view.HomeFragment;
import com.zt.yavon.module.main.frame.view.MainActivity;
import com.zt.yavon.widget.RvBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RvDevices extends RvBase<TabBean.MachineBean> {
    private MainActivity mActivity;
    private OnSwitchStateChangeListener checkedChangeListener;
    public RvDevices(Context context) {
        super(context);
    }

    public RvDevices(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RvDevices(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public void setOnCheckedChangeListener(OnSwitchStateChangeListener checkedChangeListener){
        this.checkedChangeListener = checkedChangeListener;
    }
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
        CheckBox cbPower = holder.getView(R.id.cb_power);
        cbPower.setOnCheckedChangeListener(null);
        if (mSelectMode) {
            cbPower.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.selector_cb_check, 0, 0);
            cbPower.setChecked(mSelectList.contains(holder.getAdapterPosition()));
        } else {
            cbPower.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.selector_cb_power, 0, 0);
            cbPower.setChecked(bean.isPowerOn());
        }

        holder.setGone(R.id.tv_location, !TextUtils.isEmpty(bean.from_room) && !bean.isLastOne && mIsOften)
                .setGone(R.id.cb_power, !bean.isLastOne)
                .setGone(R.id.tv_status, !bean.isLastOne);
        holder.setText(R.id.tv_name, bean.isLastOne ? "添加常用设备" : bean.name)
                .setText(R.id.tv_status, bean.isLastOne ? "" : (bean.isPowerOn() ? "设备开启" : "设备关闭"))
                .setText(R.id.tv_location, bean.isLastOne ? "" : bean.from_room)
                .setOnCheckedChangeListener(R.id.cb_power, new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (mSelectMode) {
//                            LogUtil.d("============on checked change:"+isChecked+",position:"+holder.getAdapterPosition());
                            if(isChecked){
                                mSelectList.add(holder.getAdapterPosition());
                            }else{
                                mSelectList.remove(holder.getAdapterPosition());
                            }
                            HomeFragment fmtHome = (HomeFragment) mActivity.getSupportFragmentManager().findFragmentByTag(MainActivity.texts[0]);
                            FmtDevice fmtDevice = (FmtDevice) ((FragmentPagerAdapter) fmtHome.viewPager.getAdapter()).getItem(fmtHome.viewPager.getCurrentItem());
                            int count = getSelectCount();
                            if(fmtDevice.mMenuWidget != null){
                                fmtDevice.mMenuWidget.setRenameEnable(count == 1);
                                fmtDevice.mMenuWidget.setShareEnable(count == 1);
                                fmtDevice.mMenuWidget.setReportEnable(count == 1);
                            }
                        } else if(checkedChangeListener != null){
                            checkedChangeListener.onSwitchStateChange(isChecked,bean);
                        }
                    }
                });
        ImageView ivIcon = holder.getView(R.id.iv_icon);
        if (bean.isLastOne) {
            ivIcon.setImageResource(R.mipmap.ic_item_add);
        } else {
            Glide.with(getContext()).load(bean.icon).into(ivIcon);
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

    public void setItemSelect(int position) {
        if (mSelectMode) {
            View view = getLayoutManager().findViewByPosition(position);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_power);
            checkBox.setChecked(!checkBox.isChecked());
        }
    }
    public void selectAll(){
//        LogUtil.d("=============size:"+mAdapter.getItemCount());
        int i = 0;
        for(TabBean.MachineBean item: mAdapter.getData()){
            if(!item.isLastOne)
            mSelectList.add(i++);
        }
        mAdapter.notifyDataSetChanged();
    }
    public interface OnSwitchStateChangeListener{
        void onSwitchStateChange(boolean isChecked,TabBean.MachineBean bean);
    }
}
