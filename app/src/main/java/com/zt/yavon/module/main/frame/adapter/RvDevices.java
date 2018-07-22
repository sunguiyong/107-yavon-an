package com.zt.yavon.module.main.frame.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.main.frame.model.DeviceItemBean;
import com.zt.yavon.module.main.frame.view.FmtDevice;
import com.zt.yavon.module.main.frame.view.HomeFragment;
import com.zt.yavon.module.main.frame.view.MainActivity;
import com.zt.yavon.widget.RvBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class RvDevices extends RvBase<DeviceItemBean> {
    private MainActivity mActivity;

    public RvDevices(Context context) {
        super(context);
    }

    public RvDevices(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RvDevices(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        mActivity = (MainActivity) context;
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
    public void customConvert(BaseViewHolder holder, DeviceItemBean bean) {
        CheckBox cbPower = holder.getView(R.id.cb_power);
//        checkBox.setOnCheckedChangeListener(null);
        if (mSelectMode) {
            cbPower.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.selector_cb_check, 0, 0);
            cbPower.setChecked(mSelectIndex == holder.getLayoutPosition());
        } else {
            cbPower.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.selector_cb_power, 0, 0);
            cbPower.setChecked(bean.mOnOffStatus);
        }

        holder.setVisible(R.id.tv_location, bean.mDeviceEnum != null)
                .setGone(R.id.cb_power, bean.mDeviceEnum != null)
                .setGone(R.id.tv_status, bean.mDeviceEnum != null);
        holder.setText(R.id.tv_name, bean.mDeviceEnum == null ? "添加设备" : bean.mDeviceEnum.mName)
                .setText(R.id.tv_status, bean.mDeviceEnum == null ? "" : (bean.mOnOffStatus ? "设备开启" : "设备关闭"))
                .setText(R.id.tv_location, bean.mDeviceEnum == null ? "" : bean.mLocation)
                .setImageResource(R.id.iv_icon, bean.mDeviceEnum == null ? R.mipmap.ic_item_add : bean.mDeviceEnum.mResId)
                .setOnCheckedChangeListener(R.id.cb_power, new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (mSelectMode) {
                            mSelectMap.put(holder.getLayoutPosition(), isChecked);

                            HomeFragment fmtHome = (HomeFragment) mActivity.getSupportFragmentManager().findFragmentByTag(MainActivity.texts[0]);
                            FmtDevice fmtDevice = (FmtDevice) ((FragmentPagerAdapter) fmtHome.viewPager.getAdapter()).getItem(fmtHome.viewPager.getCurrentItem());
                            fmtDevice.mMenuWidget.setRenameEnable(getSelectCount() == 1);
                        } else {
                        }
                    }
                });
        holder.addOnClickListener(R.id.ll_center).addOnClickListener(R.id.cb_power);
    }

    public static Hashtable<Integer, Boolean> mSelectMap = new Hashtable<>();
    private boolean mSelectMode = false;
    private int mSelectIndex = -1;

    public int getSelectCount() {
        int selectCount = 0;
        for (Map.Entry<Integer, Boolean> item : mSelectMap.entrySet()) {
            if (item.getValue()) {
                selectCount++;
            }
        }
        Log.e("TTTTTT", selectCount + "");
        return selectCount;
    }

    public void enterMultiSelectMode(int selectIndex) {
        mSelectMode = true;
        mSelectMap.clear();
        mSelectMap.put(selectIndex, true);
        mSelectIndex = selectIndex;
        mAdapter.notifyDataSetChanged();
    }

    public void exitMultiSelectMode() {
        mSelectIndex = -1;
        mSelectMode = false;
        mAdapter.notifyDataSetChanged();
    }

    public List<DeviceItemBean> getSelectBeans() {
        List<DeviceItemBean> beans = new ArrayList<>();
        for (Map.Entry<Integer, Boolean> item : mSelectMap.entrySet()) {
            if (item.getValue()) {
                beans.add(mAdapter.getItem(item.getKey()));
            }
        }
        return beans;
    }
}
