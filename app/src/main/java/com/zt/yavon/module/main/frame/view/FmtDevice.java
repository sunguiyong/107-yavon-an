package com.zt.yavon.module.main.frame.view;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.base.utils.LogUtil;
import com.common.base.utils.ToastUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseFragment;
import com.zt.yavon.module.data.MineRoomBean;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.device.desk.view.DeskDetailActivity;
import com.zt.yavon.module.device.lamp.view.LampDetailActivity;
import com.zt.yavon.module.device.lock.view.LockDetailActivity;
import com.zt.yavon.module.device.share.view.ApplyDevActivity;
import com.zt.yavon.module.device.share.view.AuthorActivity;
import com.zt.yavon.module.device.share.view.ShareDevActivity;
import com.zt.yavon.module.main.adddevice.view.ActAddDevice;
import com.zt.yavon.module.main.frame.adapter.RvDevices;
import com.zt.yavon.module.main.frame.contract.DeviceContract;
import com.zt.yavon.module.main.frame.presenter.DevicePresenter;
import com.zt.yavon.module.main.widget.MenuWidget;
import com.zt.yavon.utils.Constants;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.utils.PackageUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class FmtDevice extends BaseFragment<DevicePresenter> implements DeviceContract.View {
    @BindView(R.id.rv_devices)
    RvDevices mRvDevices;
    //    @BindView(R.id.srl)
//    SwipeRefreshLayout srl;
    private TabBean mTabItemBean;
    private MainActivity mActivity;
    public MenuWidget mMenuWidget;
    private LinearLayout mLlTitle;
    private Dialog dialog;

    private HomeFragment fmtHome;

    private boolean mIsOften = false;


    @Override
    protected int getLayoutResource() {
        return R.layout.fmt_device_layout;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        mTabItemBean = (TabBean) getArguments().getSerializable(Constants.EXTRA_DEVICE_TAB_ITEM_BEAN);
        mIsOften = mTabItemBean.type.equals("OFTEN");
//        LogUtil.d("=============name:"+mTabItemBean.name+",type:"+mTabItemBean.type);
    }

    @Override
    protected void initView() {
//        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                fmtHome.refreshData();
//            }
//        });
        mRvDevices.setIsOften(mIsOften);
        mActivity = (MainActivity) getActivity();
        mMenuWidget = mActivity.findViewById(R.id.menu_widget);
        fmtHome = (HomeFragment) mActivity.getSupportFragmentManager().findFragmentByTag(MainActivity.texts[0]);
        mLlTitle = fmtHome.rootView.findViewById(R.id.ll_title);
//        mRvDevices.setOnCheckedChangeListener(new RvDevices.OnSwitchStateChangeListener() {
//            @Override
//            public void onSwitchStateChange(boolean isChecked, TabBean.MachineBean bean) {
//
//            }
//        });
        mRvDevices.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TabBean.MachineBean item = (TabBean.MachineBean) adapter.getItem(position);
                if (mRvDevices.isSelectMode()) {
                    if (!item.isLastOne){
                        boolean isChecked = !mRvDevices.isSelected(position);
                        mRvDevices.setItemSelect(position,isChecked);
                        if(isChecked){
                            mRvDevices.addSelection(position);
                        }else{
                            mRvDevices.removeSelection(position);
                        }
                        upadteMenu();
                    }
                } else {
                    if (item.isLastOne) {
                        ((MainActivity) getActivity()).startActForResult(ActAddDevice.class, MainActivity.REQUEST_CODE_ADD_DEVICE);
                    } else {
                        boolean isWifiDev = false;
                        if(Constants.MACHINE_TYPE_LIGHT.equals(item.machine_type) || Constants.MACHINE_TYPE_ADJUST_TABLE.equals(item.machine_type)){
                            isWifiDev = true;
                        }
//                        DeskDetailActivity.startAction(getContext(), item);
                        if(!isWifiDev || "ONLINE".equals(item.online_status)){
                            if("ADMIN".equals(item.user_type) || item.is_authorized){
                                if (Constants.MACHINE_TYPE_LIGHT.equals(item.machine_type)) {
                                    LampDetailActivity.startAction(getContext(), item);
                                } else if (Constants.MACHINE_TYPE_ADJUST_TABLE.equals(item.machine_type)) {
                                    DeskDetailActivity.startAction(getContext(), item);
                                } else {
                                    if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                                        BluetoothAdapter.getDefaultAdapter().enable();
                                    } else {
                                        initPermission(false,item);
                                    }
                                }
                            }else{
                                showAuthorDialog(item);
                            }
                        }else{
                            showOffLineDialog();
                        }

                    }
                }
            }
        });
        mRvDevices.mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mRvDevices.isSelectMode()) {
                    TabBean.MachineBean item = (TabBean.MachineBean) adapter.getItem(position);
                    if (!item.isLastOne) {
                        enterMultiSelectMode(position);
                    }
                }
                TabBean.MachineBean item = (TabBean.MachineBean) adapter.getItem(position);
                if (!item.isLastOne)
                    enterMultiSelectMode(position);
                return true;
            }
        });
        mRvDevices.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId() == R.id.cb_power){
                    TabBean.MachineBean bean = (TabBean.MachineBean) adapter.getItem(position);
                    boolean isChecked = !view.isSelected();
//                    boolean isChecked = !mRvDevices.isSelected(position);
                    if (mRvDevices.isSelectMode()) {
//                            LogUtil.d("============on checked change:"+isChecked+",position:"+holder.getAdapterPosition());
//                        isChecked = !mRvDevices.isSelected(position);
                        view.setSelected(isChecked);
                        if(isChecked){
                            mRvDevices.addSelection(position);
                        }else{
                            mRvDevices.removeSelection(position);
                        }
//                        HomeFragment fmtHome = (HomeFragment) mActivity.getSupportFragmentManager().findFragmentByTag(MainActivity.texts[0]);
//                        FmtDevice fmtDevice = (FmtDevice) ((FragmentPagerAdapter) fmtHome.viewPager.getAdapter()).getItem(fmtHome.viewPager.getCurrentItem());
                        upadteMenu();
                    } else{
                        boolean isWifiDev = false;
                        if(Constants.MACHINE_TYPE_LIGHT.equals(bean.machine_type) || Constants.MACHINE_TYPE_ADJUST_TABLE.equals(bean.machine_type)){
                            isWifiDev = true;
                        }
                        if(!isWifiDev || "ONLINE".equals(bean.online_status)){
                            if("ADMIN".equals(bean.user_type) || bean.is_authorized){
                                mPresenter.switchDevice(view,isChecked,bean);
                            }else{
                                showAuthorDialog(bean);
                            }
                        }else{
                            showOffLineDialog();
                        }
                    }
                }
            }
        });
        List<TabBean.MachineBean> machines = new ArrayList<>();
        machines.addAll(mTabItemBean.machines);
        if (mIsOften) {
            machines.add(new TabBean.MachineBean(true));
        }
        mRvDevices.setData(machines);
    }
    private void upadteMenu(){
        int count = mRvDevices.getSelectCount();
        if(mMenuWidget != null){
            mMenuWidget.tvRename.setEnabled(count == 1);
            mMenuWidget.tvShare.setEnabled(count == 1);
            mMenuWidget.tvReport.setEnabled(count == 1);
        }
    }
    private void showOffLineDialog() {
        dialog = DialogUtil.createInfoDialogWithListener(getContext(),"设备离线中，请连接设备后刷新页面",null);
    }

    private void showAuthorDialog(TabBean.MachineBean bean){
        dialog = DialogUtil.create2BtnInfoDialog(getContext(), "该设备尚未授权或授权已过期", null, "再次申请", new DialogUtil.OnComfirmListening() {
            @Override
            public void confirm() {
                ApplyDevActivity.startAction(getActivity(),bean.name,bean.asset_number);
            }
        });
    }
    private void initPermission(boolean isDo,TabBean.MachineBean item) {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.LOCATION)
                .onGranted(permissions -> {
                    if(!isDo){
                        LockDetailActivity.startAction(getContext(), item);
                        return;
                    }
//                    engine.getDevice(bean.getAsset_number(), new BleEngine.Callback() {
//                        @Override
//                        public void onReceive(int i, Object o) {
//
//                        }
//                    });
//                    DialogUtil.dismiss(dialog);
//                    dialog = LoadingDialog.showDialogForLoading(getContext(),"操作中...",true,null);
//                    engine.unlock(bean.getAsset_number(), bean.getPassword(), UnlockMode.MODE_TOGGLE, new BleEngine.Callback() {
//                        @Override
//                        public void onReceive(int status, Object data) {
//                            switch (status) {
//                                case BleStatus.LOCK_COMPLETE:
//                                    DialogUtil.dismiss(dialog);
////                                    LogUtil.d("============LOCK_COMPLETE,data:" + data);
//                                    updateView(false);
//                                    mPresenter.switchDev(machineBean.id + "", false);
//                                    break;
//                                case BleStatus.UNLOCK_COMPLETE:
//                                    DialogUtil.dismiss(dialog);
////                                    LogUtil.d("============UNLOCK_COMPLETE,data:" + data);
//                                    updateView(true);
//                                    mPresenter.switchDev(machineBean.id + "", true);
//                                    break;
//                            }
//                        }
//                    });
                })
                .onDenied(permissions -> {
                    LogUtil.d("=========denied permissions:"+ Arrays.toString(permissions.toArray()));
                    DialogUtil.create2BtnInfoDialog(getContext(), "需要蓝牙和定位权限，马上去开启?", "取消", "开启", new DialogUtil.OnComfirmListening() {
                        @Override
                        public void confirm() {
                            PackageUtil.startAppSettings(getContext());
                        }
                    });
                })
                .start();
    }
    public void addData(List<TabBean.MachineBean> beans) {
        mRvDevices.mAdapter.getData().addAll(mRvDevices.mAdapter.getItemCount() - 1, beans);
        mRvDevices.mAdapter.notifyDataSetChanged();
    }

    public void onSelectAllClick() {
        mRvDevices.selectAll();
        upadteMenu();
    }

    public void onSelectCompleteClick() {
        exitMultiSelectMode();
    }

    public void exitMultiSelectMode() {
        try {
            mLlTitle.setVisibility(View.GONE);
            mRvDevices.exitMultiSelectMode();
            mMenuWidget.setVisibility(View.GONE);
        } catch (Exception e) {

        }
    }

    private void enterMultiSelectMode(int position) {
        mRvDevices.enterMultiSelectMode(position);
        mMenuWidget.setVisibility(View.VISIBLE);
        if(mIsOften){
            mMenuWidget.tvOften.setEnabled(false);
            mMenuWidget.tvMove.setText("移除");
        }
        mLlTitle.setVisibility(View.VISIBLE);
    }

    public boolean isMenuShown() {
        return mMenuWidget.isShown();
    }

    @Override
    public void returnRoomList(List<TabBean> list) {
        if (list.size() == 0) {
            dialog = DialogUtil.createInfoDialogWithListener(getContext(), "没有可移动的房间", null);
            exitMultiSelectMode();
        } else {
            dialog = DialogUtil.createMoveDeviceDialog(mActivity, list, new DialogUtil.OnComfirmListening2() {
                @Override
                public void confirm(String roomId) {
                    mPresenter.moveDev(mRvDevices.getSelectBeans(), roomId);
                }
            });
        }

    }

    public void onRecentClick() {
        mPresenter.setOften(mRvDevices.getSelectBeans());
    }

    public void onMoveClick() {
        List<TabBean.MachineBean> beans = mRvDevices.getSelectBeans();
        if (beans.isEmpty()) {
            ToastUtil.showLong(mActivity, "未选择设备");
            return;
        }
        if(mIsOften){
            dialog = DialogUtil.create2BtnInfoDialog(mActivity, "确认将设备从常用列表中移除吗?",null,null, new DialogUtil.OnComfirmListening() {
                @Override
                public void confirm() {
                    mPresenter.removeOften(beans);
                }
            });
        }else{
            mPresenter.getRoomList();
        }
    }

    public void onRenameClick() {
        mPresenter.renameDev(mRvDevices.getSelectBeans());
    }

    public void onShareClick() {
        List<TabBean.MachineBean> beans = mRvDevices.getSelectBeans();
        if (beans.isEmpty()) {
            ToastUtil.showLong(mActivity, "未选择设备");
            return;
        }
        if (beans.size() > 1) {
            ToastUtil.showLong(mActivity, "只能选择一个设备共享");
            return;
        }
        MineRoomBean.Machine machine = new MineRoomBean.Machine();
        machine.setMachine_id(mPresenter.getIds(beans));
        if ("ADMIN".equals(beans.get(0).user_type)) {
            ShareDevActivity.startAction(getContext(), machine);
        } else {
            AuthorActivity.startAction(getContext(), machine);
        }
    }

    public void onDelClick() {
        mPresenter.deleteDevice(mRvDevices.getSelectBeans());
    }

    public void onReportClick() {
        mPresenter.uploadFault(mRvDevices.getSelectBeans());
    }

    @Override
    public void deleteSuccess(List<TabBean.MachineBean> beans) {
        mRvDevices.mAdapter.getData().removeAll(beans);
        mRvDevices.mAdapter.notifyDataSetChanged();
        exitMultiSelectMode();
//        fmtHome.onRefresh();
    }

    @Override
    public void setOftenSuccess(List<TabBean.MachineBean> beans) {
        exitMultiSelectMode();
        fmtHome.onRefresh();
    }

    @Override
    public void moveSuccess(List<TabBean.MachineBean> beans) {
        exitMultiSelectMode();
        fmtHome.onRefresh();
    }

    @Override
    public void renameSuccess(TabBean.MachineBean bean) {
        mRvDevices.mAdapter.notifyItemChanged(mRvDevices.mAdapter.getData().indexOf(bean));
    }

    @Override
    public void uploadFaultSuccess() {
        ToastUtil.showShort(getContext(), "上报成功");
        exitMultiSelectMode();
    }

    @Override
    public void removeOftenSuccess(List<TabBean.MachineBean> beans) {
        mRvDevices.mAdapter.getData().removeAll(beans);
        mRvDevices.mAdapter.notifyDataSetChanged();
        exitMultiSelectMode();
    }

    @Override
    public void onDestroy() {
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }
}
