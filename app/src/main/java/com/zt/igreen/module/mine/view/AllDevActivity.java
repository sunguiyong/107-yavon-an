package com.zt.igreen.module.mine.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.data.MineRoomBean;
import com.zt.igreen.module.device.share.view.ShareSettingActivity;
import com.zt.igreen.module.mine.adapter.AllDevAdapter;
import com.zt.igreen.module.mine.contract.AllDevContract;
import com.zt.igreen.module.mine.presenter.AllDevPresenter;
import com.zt.igreen.utils.Constants;

import java.util.List;

import butterknife.BindView;

/**
 * Created by lifujun on 2018/7/10.
 */

public class AllDevActivity extends BaseActivity<AllDevPresenter> implements SwipeRefreshLayout.OnRefreshListener, AllDevContract.View {
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

        sethead(R.color.touming);
        ImmersionBar.with(this)
                .statusBarColor(R.color.touming)
                .statusBarDarkFont(false).flymeOSStatusBarFontColor(R.color.touming).init();
       /* ImmersionBar.with(this)
                .statusBarColor(R.color.qingse).statusBarDarkFont(true).flymeOSStatusBarFontColor(R.color.qingse).init();*/
        setColor(Color.parseColor("#ffffff"));
        setTitle("全部设备");
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary1);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor(getContext(),R.color.colorPrimary), DensityUtil.dp2px(getContext(),10f));
//        itemDecoration.setDrawLastItem(false);
//        recyclerView.addItemDecoration(itemDecoration);
        adapter = new AllDevAdapter();
        View empView = getLayoutInflater().inflate(R.layout.view_empty, null);
        adapter.setEmptyView(empView);
        adapter.bindToRecyclerView(recyclerView);




            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapterView, View view, int position) {
                if (adapter.getItemViewType(position) == AllDevAdapter.TYPE_DETAIL) {
                    MineRoomBean.Machine bean = (MineRoomBean.Machine) adapter.getItem(position);
                    if (Constants.MACHINE_TYPE_LIGHT.equals(bean.getMachine_type()) && !bean.getUser_type().equals("ADMIN")) {
                        return;
                    }
                    if (Constants.MACHINE_TYPE_AIR_MACHINE.equals(bean.getMachine_type())
                            || Constants.MACHINE_TYPE_NFC_COASTER.equals(bean.getMachine_type())
                            || Constants.MACHINE_TYPE_WATER_DISPENSER.equals(bean.getMachine_type())
                            || Constants.MACHINE_TYPE_AIR_HEALTH.equals(bean.getMachine_type())) {
                    } else {
                        ShareSettingActivity.startAction(AllDevActivity.this, bean);
                    }
                }
            }
        });
        mPresenter.getAllDevs(true);
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
    public static void startAction(Context context) {
        Intent intent = new Intent(context, AllDevActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mPresenter.getAllDevs(false);
    }

    @Override
    public void returnDevs(List<MineRoomBean> list) {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        if (list != null) {
            adapter.setNewData(list);
            adapter.expandAll();
        }
    }
}
