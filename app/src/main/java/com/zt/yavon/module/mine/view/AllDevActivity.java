package com.zt.yavon.module.mine.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.LockRecordItem;
import com.zt.yavon.module.device.lock.adapter.LockRecordAdapter;
import com.zt.yavon.module.mine.adapter.AllDevAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lifujun on 2018/7/10.
 */

public class AllDevActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
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

    }


    @Override
    public void initView() {
        setTitle(getString(R.string.more_record));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor(getContext(),R.color.colorPrimary), DensityUtil.dp2px(getContext(),10f));
//        itemDecoration.setDrawLastItem(false);
//        recyclerView.addItemDecoration(itemDecoration);
        adapter = new AllDevAdapter();
        adapter.bindToRecyclerView(recyclerView);
        List<LockRecordItem> list = new ArrayList<>();
        for(int i = 0;i<50;i++){
            if(i%7 == 0 || i%5 == 0){
                list.add(new LockRecordItem(LockRecordItem.TYPE_TITLE,new Object()));
            }else{
                list.add(new LockRecordItem(LockRecordItem.TYPE_DETAIL,new Object()));
            }
        }
        adapter.setNewData(list);
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
        swipeRefreshLayout.setRefreshing(false);
    }
}
