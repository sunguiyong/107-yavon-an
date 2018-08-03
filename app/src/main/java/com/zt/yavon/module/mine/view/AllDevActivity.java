package com.zt.yavon.module.mine.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.MineRoomBean;
import com.zt.yavon.module.data.SectionItem;
import com.zt.yavon.module.device.share.view.ShareSettingActivity;
import com.zt.yavon.module.mine.adapter.AllDevAdapter;
import com.zt.yavon.module.mine.contract.AllDevContract;
import com.zt.yavon.module.mine.presenter.AllDevPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lifujun on 2018/7/10.
 */

public class AllDevActivity extends BaseActivity<AllDevPresenter> implements SwipeRefreshLayout.OnRefreshListener,AllDevContract.View{
    @BindView(R.id.swipe_dev_all)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_dev_all)
    RecyclerView recyclerView;
    private AllDevAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dev_all;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }


    @Override
    public void initView() {
        setTitle("全部设备");
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor(getContext(),R.color.colorPrimary), DensityUtil.dp2px(getContext(),10f));
//        itemDecoration.setDrawLastItem(false);
//        recyclerView.addItemDecoration(itemDecoration);
        adapter = new AllDevAdapter();
        View empView = getLayoutInflater().inflate(R.layout.view_empty,null);
        adapter.setEmptyView(empView);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapterView, View view, int position) {
                SectionItem item = adapter.getItem(position);
                if(item.getItemType() == SectionItem.TYPE_DETAIL){
                    MineRoomBean.Machine bean = (MineRoomBean.Machine)item.getData();
                    ShareSettingActivity.startAction(AllDevActivity.this,bean);
                }
            }
        });
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
    public static void startAction(Context context){
        Intent intent = new Intent(context,AllDevActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mPresenter.getAllDevs();
    }

    @Override
    public void returnDevs(List<MineRoomBean> list) {
        if(swipeRefreshLayout.isRefreshing())
        swipeRefreshLayout.setRefreshing(false);
        if(list != null){
            List<SectionItem> newList = new ArrayList<>();
            for(MineRoomBean bean:list){
                newList.add(new SectionItem(SectionItem.TYPE_TITLE,bean.getName()));
                if(bean.getMachines() != null)
                for(MineRoomBean.Machine machine:bean.getMachines()){
                    newList.add(new SectionItem(SectionItem.TYPE_DETAIL,machine));
                }
            }
            adapter.setNewData(newList);
        }
    }
}
