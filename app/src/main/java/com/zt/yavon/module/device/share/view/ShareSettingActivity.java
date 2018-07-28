package com.zt.yavon.module.device.share.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.ShareListBean;
import com.zt.yavon.module.device.share.adapter.ShareSettingAdapter;
import com.zt.yavon.module.device.share.contract.ShareSettingContract;
import com.zt.yavon.module.device.share.presenter.ShareSettingPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class ShareSettingActivity extends BaseActivity<ShareSettingPresenter> implements ShareSettingContract.View{
    TextView tvName;
    TextView tvUser;
    TextView tvDo;
    ImageView ivDev;
    @BindView(R.id.tv_share_setting)
    TextView tvShare;
    @BindView(R.id.recyclerview_share_setting)
    RecyclerView recyclerView;
    private ShareSettingAdapter adapter;
    private String id;
    @Override
    public int getLayoutId() {
        return R.layout.activity_share_setting;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        id = getIntent().getStringExtra("id");
    }

    @Override
    public void initView() {
        setTitle("共享设置");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShareSettingAdapter();
//        View empView = getLayoutInflater().inflate(R.layout.view_empty,null);
//        adapter.setEmptyView(empView);
        adapter.bindToRecyclerView(recyclerView);
        View headerView = getLayoutInflater().inflate(R.layout.header_share_setting,null);
        tvName = ButterKnife.findById(headerView,R.id.tv_name_dev);
        ivDev = ButterKnife.findById(headerView,R.id.iv_dev);
        tvUser = ButterKnife.findById(headerView,R.id.tv_title_user);
        tvDo = ButterKnife.findById(headerView,R.id.tv_do);
        adapter.addHeaderView(headerView);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapterView, View view, int position) {
                ShareListBean.User user = adapter.getItem(position);
                mPresenter.cancleDevShare(id,user);
            }
        });
        mPresenter.getShareList(id);
    }
    @OnClick({R.id.tv_share_setting})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.tv_share_setting:
                break;
        }
    }
    public static void startAction(Context context,String id){
        Intent intent = new Intent(context, ShareSettingActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void returnShareList(ShareListBean bean) {
        if(bean != null){
            adapter.setType(bean.getMachine().getUser_type());
            tvName.setText(bean.getMachine().getMachine_name());
            Glide.with(this)
                    .load(bean.getMachine().getMachine_icon())
//                    .fitCenter()
                    .into(ivDev);
            List<ShareListBean.User> list = bean.getUsers();
            if("ADMIN".equals(bean.getMachine().getUser_type())){
                //管理员
                tvUser.setText("使用者");
                if(list == null || list.isEmpty()){
                    tvShare.setVisibility(View.VISIBLE);
                }else{
                    tvShare.setVisibility(View.GONE);
                    adapter.setNewData(list);
                }
            }else if("FIRST_USER".equals(bean.getMachine().getUser_type())){
                //一级用户
                tvUser.setText("使用者");
            }else{
                //二级用户
                tvUser.setText("管理员");
                tvDo.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void cancleDevShareSuccess(ShareListBean.User user) {
        adapter.remove(adapter.getData().indexOf(user));
    }
}