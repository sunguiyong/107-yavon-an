package com.zt.yavon.module.main.frame.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.yavon.R;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.main.frame.view.MainActivity;
import com.zt.yavon.utils.Constants;
import com.zt.yavon.widget.RvBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RvDevices extends RvBase<TabBean.MachineBean> {
    private MainActivity mActivity;
//    private OnSwitchStateChangeListener checkedChangeListener;
    public RvDevices(Context context) {
        super(context);
    }

    public RvDevices(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setEmptyViewText("没有设备",false);
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
        TextView tvStatus = holder.getView(R.id.tv_status);
        if (mSelectMode) {
            cbPower.setImageResource(R.drawable.selector_cb_check);
            cbPower.setSelected(mSelectList.contains(holder.getAdapterPosition()));
        } else {
            cbPower.setImageResource(R.drawable.selector_cb_power);
            cbPower.setSelected(bean.isPowerOn());
        }
        boolean onLine = "ONLINE".equals(bean.online_status);
        if(Constants.MACHINE_TYPE_ADJUST_TABLE.equals(bean.machine_type)){
            cbPower.setVisibility(mSelectMode?View.VISIBLE:View.GONE);
            holder.setGone(R.id.black_frame,onLine?false:true);
            tvStatus.setText(onLine? "" : "设备离线");
        }else if(Constants.MACHINE_TYPE_BATTERY_LOCK.equals(bean.machine_type)){
            cbPower.setVisibility(View.VISIBLE);
            holder.setGone(R.id.black_frame,false);
            tvStatus.setText(bean.isPowerOn() ? "设备开启" : "设备关闭");
        }else if(Constants.MACHINE_TYPE_LIGHT.equals(bean.machine_type)){
            cbPower.setVisibility(View.VISIBLE);
            holder.setGone(R.id.black_frame,onLine?false:true);
            tvStatus.setText(onLine? (bean.isPowerOn() ? "设备开启" : "设备关闭"):"设备离线");
        }else{
            cbPower.setVisibility(View.GONE);
            holder.setGone(R.id.black_frame,false);
        }
        TextView tvAuthor = holder.getView(R.id.tv_author_dev);
        if("ADMIN".equals(bean.user_type)){
            tvAuthor.setVisibility(GONE);
        }else if(bean.isLastOne){
            tvAuthor.setVisibility(GONE);
        }else{
            tvAuthor.setVisibility(VISIBLE);
            tvAuthor.setSelected(bean.is_authorized);
            if(bean.is_authorized){
                tvAuthor.setText("已授权");
            }else{
                tvAuthor.setText("未授权");
            }
        }
        holder.setText(R.id.tv_name, bean.isLastOne ? "添加常用设备" : bean.name)
                .setText(R.id.tv_location, bean.isLastOne ? "" : bean.from_room);
        holder.setGone(R.id.tv_location, !TextUtils.isEmpty(bean.from_room) && !bean.isLastOne && mIsOften)
                .setGone(R.id.tv_status, !bean.isLastOne);

        holder.addOnClickListener(R.id.cb_power);
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
    public boolean isSelected(int position){
        for (int item : mSelectList) {
            if(item == position){
                return true;
            }
        }
        return false;
    }
    public void setItemSelect(int position,boolean isSelected) {
        if (mSelectMode) {
            View view = getLayoutManager().findViewByPosition(position);
            View checkBox = view.findViewById(R.id.cb_power);
            checkBox.setSelected(isSelected);
        }
    }
    public void addSelection(int position){
        mSelectList.add(position);
    }
    public void removeSelection(int position){
        mSelectList.remove(position);
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
//    public interface OnSwitchStateChangeListener{
//        void onSwitchStateChange(boolean isChecked,TabBean.MachineBean bean);
//    }
}
