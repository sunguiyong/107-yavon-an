package com.zt.igreen.module.mall.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseFragment;
import com.zt.igreen.module.data.IntellListBean;
import com.zt.igreen.module.data.MaterialTypeBean;
import com.zt.igreen.module.mall.adapter.DeviceTypeAdapter;
import com.zt.igreen.module.mall.adapter.StyleAdapter;
import com.zt.igreen.module.mall.contract.MalMaterialContract;
import com.zt.igreen.module.mall.contract.MaterialStyleContract;
import com.zt.igreen.module.mall.presenter.MaterialStylePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by lifujun on 2018/7/16.
 */

public class MallFragment extends BaseFragment<MaterialStylePresenter> implements MaterialStyleContract.View {
    @BindView(R.id.re_device_type)
    RecyclerView reDeviceType;
    @BindView(R.id.shoucangjia)
    TextView shoucangjia;
    @BindView(R.id.re_device)
    EasyRecyclerView reDevice;
    @BindView(R.id.lin_search)
    LinearLayout linSearch;
    Unbinder unbinder;
    Unbinder unbinder1;
    private DeviceTypeAdapter deviceTypeAdapter;
    private StyleAdapter deviceAdapter;
    List<IntellListBean> list=new ArrayList<>();
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mall_main;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        mPresenter.getMaterialTypes();
        mPresenter.getMaterialList(1 + "");
    }

    @Override
    public void initView() {
        reDeviceType.setLayoutManager(new LinearLayoutManager(getActivity()));
        deviceTypeAdapter = new DeviceTypeAdapter(getActivity());
        reDeviceType.setAdapter(deviceTypeAdapter);
        reDevice.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        deviceAdapter = new StyleAdapter();
        reDevice.setAdapter(deviceAdapter);
        deviceTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MaterialTypeBean bean = deviceTypeAdapter.getItem(position);
                deviceTypeAdapter.setPostion(position);
                deviceTypeAdapter.notifyDataSetChanged();
                mPresenter.getMaterialList(bean.getId() + "");
            }
        });
        deviceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int id=list.get(position).getId();
                MalDetailsActivity.startAction(getActivity(),id+"");
            }
        });
        shoucangjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteActivity.startAction(getActivity());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void returnMaterial(List<MaterialTypeBean> list) {
        deviceTypeAdapter.setNewData(list);
    }

    @Override
    public void returnMaterialList(List<IntellListBean> list) {
        this.list=list;
        deviceAdapter.setNewData(list);
    }

    @OnClick(R.id.lin_search)
    public void onClick() {
        SearchActivity.startAction(getActivity());
    }
}
