package com.zt.igreen.module.mall.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.data.FavoriteBean;
import com.zt.igreen.module.mall.adapter.FavoriteAdapter;
import com.zt.igreen.module.mall.contract.FavoriteContract;
import com.zt.igreen.module.mall.presenter.FavoritePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/21 0021.
 */

public class FavoriteActivity extends BaseActivity<FavoritePresenter> implements FavoriteContract.View {
    @BindView(R.id.re_device)
    EasyRecyclerView reDevice;
    private FavoriteAdapter deviceAdapter;
    List<FavoriteBean> list=new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_favorite;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        mPresenter.getFavorite();
    }

    @Override
    public void initView() {
        sethead(R.color.qingse);
        setColor(Color.parseColor("#ffffff"));
        ImmersionBar.with(this).statusBarColor(R.color.qingse).statusBarDarkFont(true).flymeOSStatusBarFontColor(R.color.qingse).init();
        setColor(Color.parseColor("#ffffff"));
        setTitle("收藏夹");
        reDevice.setLayoutManager(new GridLayoutManager(this, 3));
        deviceAdapter = new FavoriteAdapter();
        reDevice.setAdapter(deviceAdapter);
        deviceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int id=list.get(position).getMaterial_id();
                MalDetailsActivity.startAction(FavoriteActivity.this,id+"");
            }
        });
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, FavoriteActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void returnFavorite(List<FavoriteBean> list) {
        this.list=list;
        deviceAdapter.setNewData(list);
    }

}
