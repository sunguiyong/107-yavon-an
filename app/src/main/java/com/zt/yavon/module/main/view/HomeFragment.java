package com.zt.yavon.module.main.view;

import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.base.utils.DensityUtil;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseFragment;
import com.zt.yavon.component.LeakSafeHandler2;
import com.zt.yavon.module.data.DemoBean;
import com.zt.yavon.module.main.adapter.MainAdapter;
import com.zt.yavon.module.main.contract.HomeContract;
import com.zt.yavon.module.main.presenter.HomePresenter;
import com.zt.yavon.widget.DividerDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * Created by hp on 2018/6/11.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,HomeContract.View{
    private int WHAT_TIMER = 0x11;
    private int WHAT_SET = 0x21;
    private long TIME_DELAY = 9*1000;
    private final int SIZE_PAGE = 10;
    @BindView(R.id.refresh_main)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_main)
    RecyclerView recyclerView;
    private MainAdapter adapter;
    private LinearLayoutManager layoutManager;
    private int curPage = 1;
    private LeakSafeHandler2<HomeFragment> mHandler = new LeakSafeHandler2<HomeFragment>(this){
        @Override
        public void onMessageReceived(HomeFragment fragment, Message msg) {
            if(fragment.recyclerView == null) return;
            if(msg.what == WHAT_TIMER){
//                fragment.mPresenter.getHomeDevList(fragment.curPage+"",false);
//                sendEmptyMessageDelayed(fragment.WHAT_TIMER,fragment.TIME_DELAY);
            }
        }
    };




    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    protected void initView() {
        hideBackButton();
        setTitle(getString(R.string.tab_main));
        refreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor(getContext(),R.color.colorPrimary), DensityUtil.dp2px(getContext(),10f));
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        adapter = new MainAdapter();
        recyclerView.setAdapter(adapter);
//        adapter.setOnLoadMoreListener(this,recyclerView);
        View empView = getLayoutInflater().inflate(R.layout.empty_main,null);
//        empView.findViewById(R.id.iv_add_device).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
        adapter.setEmptyView(empView);
        adapter.setEnableLoadMore(true);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                LogUtil.d("onItemClick,view id:"+view.getId());
                DemoBean bean = (DemoBean) adapter.getItem(position);
                ToastUtil.showShort(getContext(),"setOnItemChildClickListener:"+position);
            }
        });
        mPresenter.getHomeDevList(curPage+"",true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showShort(getContext(),"setOnItemClickListener:"+position);
            }
        });
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                DemoBean bean = (DemoBean) adapter.getItem(position);
                ToastUtil.showShort(getContext(),"onItemLongClick:"+position);
                return false;
            }
        });

    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getHomeDevList(curPage+"",false);
    }

    @Override
    public void returnHomeDevList(List<DemoBean> list) {
        if(refreshLayout.isRefreshing())
        refreshLayout.setRefreshing(false);
        if (list == null)
            return;
        if(curPage == 1){
            adapter.setNewData(list);
        }else{
            adapter.addData(list);
        }
        if(list.size() < SIZE_PAGE){
            adapter.loadMoreEnd(true);
            adapter.loadMoreComplete();
        }else{
            adapter.loadMoreComplete();
        }
        curPage++;
    }




    @Override
    public void onRefresh() {
        curPage = 1;
        mPresenter.getHomeDevList(curPage+"",true);
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.clean(WHAT_TIMER);
    }

    @Override
    public void onDestroyView() {
//        if(dialog != null && dialog.isShowing()){
//            dialog.dismiss();
//            dialog = null;
//        }
//        mHandler.clean(WHAT_SET);
        super.onDestroyView();
    }
}
