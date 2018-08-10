package com.zt.yavon.module.device.desk.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.SectionItem;
import com.zt.yavon.module.data.UserRecordBean;
import com.zt.yavon.module.device.desk.contract.DevUseRecoderContract;
import com.zt.yavon.module.device.desk.presenter.DevUseRecordPresenter;
import com.zt.yavon.module.device.desk.adapter.DevRecordAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lifujun on 2018/7/10.
 */

public class DevUseRecordActivity extends BaseActivity<DevUseRecordPresenter> implements SwipeRefreshLayout.OnRefreshListener, DevUseRecoderContract.View,BaseQuickAdapter.RequestLoadMoreListener{
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_record_desk)
    RecyclerView recyclerView;
    @BindView(R.id.iv_lock_record)
    ImageView ivDev;
    @BindView(R.id.tv_name_record)
    TextView tvName;
    @BindView(R.id.iv_status_record)
    TextView tvStatus;
    private DevRecordAdapter adapter;
    private int curPage = 1;
    private int COUNT_PER_PAGE = 20;
    private String machineId;
    private String lastDate = "old";
    @Override
    public int getLayoutId() {
        return R.layout.activity_record_use_desk;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        machineId = getIntent().getStringExtra("machineId");
    }

    @Override
    public void setThemeStyle() {
        super.setThemeStyle();
        setStatusBarColor(ContextCompat.getColor(this,R.color.black2));
    }

    @Override
    public void initView() {
        setTitleBackgroudColor(R.color.black2);
        setTitle(getString(R.string.more_record));
//        setRightMenuImage(R.mipmap.more_right);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor(getContext(),R.color.colorPrimary), DensityUtil.dp2px(getContext(),10f));
//        itemDecoration.setDrawLastItem(false);
//        recyclerView.addItemDecoration(itemDecoration);
        adapter = new DevRecordAdapter();
        View empView = getLayoutInflater().inflate(R.layout.view_empty,null);
        adapter.setEmptyView(empView);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnLoadMoreListener(this,recyclerView);
        adapter.setEnableLoadMore(false);
        onRefresh();
    }

//    @OnClick({R.id.tv_switch_lock,R.id.tv_right_header})
//    @Override
//    public void doubleClickFilter(View view) {
//        super.doubleClickFilter(view);
//    }
//
//    @Override
//    public void doClick(View view) {
//        switch (view.getId()){
//            case R.id.tv_switch_lock:
//                if(tvSwith.isSelected()){
//                    ivLock.setSelected(false);
//                    tvSwith.setSelected(false);
//                }else{
//                    ivLock.setSelected(true);
//                    tvSwith.setSelected(true);
//                }
//                break;
//            case R.id.tv_right_header:
//                break;
//        }
//    }
    public static void startAction(Context context,String machineId){
        Intent intent = new Intent(context,DevUseRecordActivity.class);
        intent.putExtra("machineId",machineId);
        context.startActivity(intent);
    }

    @Override
    public void returnUseRecord(UserRecordBean bean) {
        if(bean == null){
            if(curPage > 1){
                adapter.loadMoreFail();
            }else if(swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(false);
                ToastUtil.showShort(this,"刷新失败，请重试！");
            }
            return;
        }
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
        if(bean.current != null){
            tvName.setText(bean.current.machine_name);
            tvStatus.setText(bean.current.machine_status);
            Glide.with(this)
                    .load(bean.current.icon)
                    .into(ivDev);
        }
        List<SectionItem> list = converseData(bean.records);
        if(curPage == 1){
            adapter.setNewData(list);
        }else{
            adapter.addData(list);
        }
        if(curPage == 1){
            adapter.disableLoadMoreIfNotFullPage();
//        }else if(bean.records != null && bean.records.size() < COUNT_PER_PAGE){
        }else if(list.size() < COUNT_PER_PAGE){
            adapter.loadMoreEnd();
        }else{
            adapter.loadMoreComplete();
        }
        curPage++;
    }
    private List<SectionItem> converseData(List<UserRecordBean.Record> list){
        List<SectionItem> newList = new ArrayList<>();
        if(list != null){
            for(UserRecordBean.Record record:list){
                boolean isFirst = true;
                if(lastDate.equals(record.date)){
                    isFirst = false;
                }else{
                    UserRecordBean.RecordDetail detail = new UserRecordBean.RecordDetail();
                    detail.date = record.date;
                    detail.week = record.week;
                    newList.add(new SectionItem(SectionItem.TYPE_TITLE,detail));
                    lastDate = record.date;
                }
                if(record.logs != null){
                    int tempIndex = 0;
                    for(UserRecordBean.RecordDetail detailBean:record.logs){
                        if(tempIndex == 0 && isFirst)
                            detailBean.isFist = true;
                        newList.add(new SectionItem(SectionItem.TYPE_DETAIL,detailBean));
                        tempIndex++;
                    }
                }

            }
        }
        return newList;
    }
    @Override
    public void onLoadMoreRequested() {
        mPresenter.getUseRecord(machineId,curPage,COUNT_PER_PAGE);
    }

    @Override
    public void onRefresh() {
        curPage = 1;
        adapter.setEnableLoadMore(false);
        onLoadMoreRequested();
    }
}
