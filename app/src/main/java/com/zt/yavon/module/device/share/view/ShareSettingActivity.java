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
import com.common.base.utils.LogUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.MineRoomBean;
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
    private MineRoomBean.Machine machine;
    private final int REQ_SHARE = 0x110;
    @Override
    public int getLayoutId() {
        return R.layout.activity_share_setting;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        machine = (MineRoomBean.Machine) getIntent().getSerializableExtra("machine");
    }

    @Override
    public void initView() {
        if("ADMIN".equals(machine.getUser_type())){
            setTitle("共享设置");
        }else{
            setTitle("授权设置");
        }

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
                mPresenter.cancleDevShare(machine.getMachine_id(),user);
            }
        });
        mPresenter.getShareList(machine.getMachine_id());
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
            case R.id.tv_right_header:
                if("ADMIN".equals(machine.getUser_type())){
                    ShareDevActivity.startAction(this,machine,REQ_SHARE);
                }else{
                    AuthorActivity.startAction(this,machine,REQ_SHARE);
                }
                break;
        }
    }
    public static void startAction(Context context,MineRoomBean.Machine bean){
        Intent intent = new Intent(context, ShareSettingActivity.class);
        intent.putExtra("machine",bean);
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
            String rightMenu = "";
            if("ADMIN".equals(bean.getMachine().getUser_type())){
                //管理员
                tvUser.setText("使用者");
                rightMenu = "共享";
                if(list == null || list.isEmpty()){
                    tvShare.setVisibility(View.VISIBLE);
                    setRightMenuText("");
                }else{
                    tvShare.setVisibility(View.GONE);
                    setRightMenuText(rightMenu);
                    adapter.setNewData(list);
                }
            }else if("FIRST_USER".equals(bean.getMachine().getUser_type())){
                //一级用户
                tvUser.setText("使用者");
                rightMenu = "临时授权";
                if(list == null || list.isEmpty()){
                    tvShare.setVisibility(View.VISIBLE);
                    setRightMenuText("");
                }else{
                    tvShare.setVisibility(View.GONE);
                    setRightMenuText(rightMenu);
                    adapter.setNewData(list);
                }
            }else{
                //二级用户
                tvUser.setText("管理员");
                rightMenu = "";
                tvDo.setVisibility(View.GONE);
                tvShare.setVisibility(View.GONE);
            }
            tvShare.setText(rightMenu);
        }
    }

    @Override
    public void cancleDevShareSuccess(ShareListBean.User user) {
        adapter.remove(adapter.getData().indexOf(user));
        if(adapter.getData().isEmpty()){
            tvShare.setVisibility(View.VISIBLE);
        }else{
            tvShare.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_SHARE && resultCode == RESULT_OK){
            mPresenter.getShareList(machine.getMachine_id());
        }
    }
}
