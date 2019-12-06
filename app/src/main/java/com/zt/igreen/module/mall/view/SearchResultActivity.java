package com.zt.igreen.module.mall.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.data.SearchResultBean;
import com.zt.igreen.module.mall.adapter.SearchAdapter;
import com.zt.igreen.module.mall.adapter.SearchResultAdapter;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/2/21 0021.
 */

public class SearchResultActivity extends BaseActivity {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private List<SearchResultBean> beans;
    private SearchResultAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_searchresult;
    }
    @Override
    public void initPresenter() {
        //mPresenter.setVM(this);
        beans = (List<SearchResultBean>) getIntent().getSerializableExtra("beans");

    }
    @Override
    public void initView() {
        sethead(R.color.touming);
        setColor(Color.parseColor("#ffffff"));
        ImmersionBar.with(this).statusBarColor(R.color.touming).statusBarDarkFont(false).flymeOSStatusBarFontColor(R.color.touming).init();
        setColor(Color.parseColor("#ffffff"));
        setTitle("搜索结果");
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new SearchResultAdapter();
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int id=beans.get(position).getId();
                MalDetailsActivity.startAction(SearchResultActivity.this,id+"");
            }
        });
        adapter.setNewData(beans);
    }

    public static void startAction(Context context, List<SearchResultBean> beans) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra("beans", (Serializable) beans);
        context.startActivity(intent);
    }
}
