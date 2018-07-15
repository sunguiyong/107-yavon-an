package com.zt.yavon.module.main.frame.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseFragment;
import com.zt.yavon.module.main.adddevice.view.ActAddDevice;
import com.zt.yavon.module.main.frame.adapter.RvDevices;
import com.zt.yavon.module.main.frame.contract.DeviceContract;
import com.zt.yavon.module.main.frame.model.DeviceItemBean;
import com.zt.yavon.module.main.frame.model.TabItemBean;
import com.zt.yavon.module.main.frame.presenter.DevicePresenter;
import com.zt.yavon.utils.Constants;

import java.util.List;

public class FmtDevice extends BaseFragment<DevicePresenter> implements DeviceContract.View {
    private TabItemBean mTabItemBean;
    private RvDevices mRvDevices;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTabItemBean = (TabItemBean) getArguments().getSerializable(Constants.EXTRA_DEVICE_TAB_ITEM_BEAN);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mPresenter.getDeviceData(mTabItemBean.mId);
        return view;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fmt_device_layout;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    protected void initView() {
        mRvDevices = (RvDevices) rootView;
        mRvDevices.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == adapter.getItemCount() - 1) {
                    ((MainActivity) getActivity()).startActForResult(ActAddDevice.class);
                }
            }
        });
    }

    @Override
    public void returnDeviceData(List<DeviceItemBean> data) {
        mRvDevices.setData(data);
    }

    public void addData(List<DeviceItemBean> beans) {
        mRvDevices.mAdapter.getData().addAll(mRvDevices.mAdapter.getItemCount() - 1, beans);
        mRvDevices.mAdapter.notifyDataSetChanged();
    }
}
