package com.zt.yavon.module.main.frame.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseFragment;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.device.desk.view.DeskDetailActivity;
import com.zt.yavon.module.device.lamp.view.LampDetailActivity;
import com.zt.yavon.module.device.lock.view.LockDetailActivity;
import com.zt.yavon.module.main.adddevice.view.ActAddDevice;
import com.zt.yavon.module.main.frame.adapter.RvDevices;
import com.zt.yavon.module.main.widget.MenuWidget;
import com.zt.yavon.utils.Constants;
import com.zt.yavon.utils.DialogUtil;

import java.util.ArrayList;
import java.util.List;

public class FmtDevice extends BaseFragment {
    private TabBean mTabItemBean;
    private RvDevices mRvDevices;
    private MainActivity mActivity;
    public MenuWidget mMenuWidget;
    private LinearLayout mLlTitle;
    private TextView mBtnSelectAll;
    private TextView mBtnOk;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTabItemBean = (TabBean) getArguments().getSerializable(Constants.EXTRA_DEVICE_TAB_ITEM_BEAN);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fmt_device_layout;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    protected void initView() {
        mRvDevices = (RvDevices) rootView;
        mActivity = (MainActivity) getActivity();
        mMenuWidget = mActivity.findViewById(R.id.menu_widget);

        HomeFragment fmtHome = (HomeFragment) mActivity.getSupportFragmentManager().findFragmentByTag(MainActivity.texts[0]);
        mLlTitle = fmtHome.rootView.findViewById(R.id.ll_title);
        mBtnSelectAll = fmtHome.rootView.findViewById(R.id.title_select_all);
        mBtnOk = fmtHome.rootView.findViewById(R.id.title_ok);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitMultiSelectMode();
            }
        });
        mActivity.findViewById(R.id.menu_recent).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_move).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_rename).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_share).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_more).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_del).setOnClickListener(mMenuWidget);
        mActivity.findViewById(R.id.menu_report).setOnClickListener(mMenuWidget);
        mMenuWidget.setOnItemClickListener(new MenuWidget.OnItemClickListener() {
            @Override
            public void onRecentClick() {
                List<TabBean.MachineBean> beans = mRvDevices.getSelectBeans();
                if (beans.isEmpty()) {
                    ToastUtil.showLong(mActivity, "未选择设备");
                    return;
                }
                exitMultiSelectMode();
            }

            @Override
            public void onMoveClick() {
                List<TabBean.MachineBean> beans = mRvDevices.getSelectBeans();
                if (beans.isEmpty()) {
                    ToastUtil.showLong(mActivity, "未选择设备");
                    return;
                }
                HomeFragment fmtHome = (HomeFragment) mActivity.getSupportFragmentManager().findFragmentByTag(MainActivity.texts[0]);
                DialogUtil.createMoveDeviceDialog(mActivity, fmtHome.mTabData, new DialogUtil.OnComfirmListening() {
                    @Override
                    public void confirm() {

                    }
                });
                exitMultiSelectMode();
            }

            @Override
            public void onRenameClick() {
                List<TabBean.MachineBean> beans = mRvDevices.getSelectBeans();
                if (beans.isEmpty()) {
                    ToastUtil.showLong(mActivity, "未选择设备");
                    return;
                }
                if (beans.size() > 1) {
                    ToastUtil.showLong(mActivity, "只能选择一个设备重命名");
                    return;
                }
                DialogUtil.createEtDialog(mActivity, "重命名", beans.get(0).name, new DialogUtil.OnComfirmListening2() {
                    @Override
                    public void confirm(String data) {
                        exitMultiSelectMode();
                    }
                });

            }

            @Override
            public void onShareClick() {
                List<TabBean.MachineBean> beans = mRvDevices.getSelectBeans();
                if (beans.isEmpty()) {
                    ToastUtil.showLong(mActivity, "未选择设备");
                    return;
                }

            }

            @Override
            public void onDelClick() {
                List<TabBean.MachineBean> beans = mRvDevices.getSelectBeans();
                if (beans.isEmpty()) {
                    ToastUtil.showLong(mActivity, "未选择设备");
                    return;
                }
                DialogUtil.create2BtnInfoDialog(mActivity, "确定删除此设备吗？", null, null, new DialogUtil.OnComfirmListening() {
                    @Override
                    public void confirm() {
                        exitMultiSelectMode();
                    }
                });
            }

            @Override
            public void onReportClick() {
                List<TabBean.MachineBean> beans = mRvDevices.getSelectBeans();
                if (beans.isEmpty()) {
                    ToastUtil.showLong(mActivity, "未选择设备");
                    return;
                }
                DialogUtil.createEtDialog(mActivity, "上报故障", "请填写上报内容", new DialogUtil.OnComfirmListening2() {
                    @Override
                    public void confirm(String data) {
                        exitMultiSelectMode();
                    }
                });
            }
        });
        mRvDevices.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TabBean.MachineBean item = (TabBean.MachineBean) adapter.getItem(position);
                if (item.isLastOne) {
                    ((MainActivity) getActivity()).startActForResult(ActAddDevice.class);
                } else {
                    if (view.getId() == R.id.ll_center) {
                        if (Constants.MACHINE_TYPE_LIGHT.equals(item.machine_type)) {
                            LampDetailActivity.startAction(getContext(),item);
                        } else if (Constants.MACHINE_TYPE_ADJUST_TABLE.equals(item.machine_type)) {
                            DeskDetailActivity.startAction(getContext());
                        } else{
                            LockDetailActivity.startAction(getContext(),item);
                        }
                    }
                }
            }
        });
        mRvDevices.addOnItemTouchListener(new OnItemLongClickListener() {
            @Override
            public void onSimpleItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                enterMultiSelectMode(position);
            }
        });

        List<TabBean.MachineBean> machines = new ArrayList<>();
        machines.addAll(mTabItemBean.machines);
        machines.add(new TabBean.MachineBean(true));
        mRvDevices.setData(machines);
    }

    public void addData(List<TabBean.MachineBean> beans) {
        mRvDevices.mAdapter.getData().addAll(mRvDevices.mAdapter.getItemCount() - 1, beans);
        mRvDevices.mAdapter.notifyDataSetChanged();
    }

    private void exitMultiSelectMode() {
        mLlTitle.setVisibility(View.GONE);
        mRvDevices.exitMultiSelectMode();
        mMenuWidget.setVisibility(View.INVISIBLE);
    }

    private void enterMultiSelectMode(int position) {
        mRvDevices.enterMultiSelectMode(position);
        mMenuWidget.setVisibility(View.VISIBLE);
        mLlTitle.setVisibility(View.VISIBLE);
    }
}
