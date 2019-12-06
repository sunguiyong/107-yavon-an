package com.zt.igreen.module.mall.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.base.utils.ToastUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.data.HotBean;
import com.zt.igreen.module.data.SearchResultBean;
import com.zt.igreen.module.mall.adapter.FavoriteAdapter;
import com.zt.igreen.module.mall.adapter.SearchAdapter;
import com.zt.igreen.module.mall.contract.SearchContract;
import com.zt.igreen.module.mall.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/2/21 0021.
 */

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.lin_search)
    LinearLayout linSearch;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private SearchAdapter adapter;
    List<HotBean> list = new ArrayList<>();
    private Handler handler = new Handler();
    private Runnable delayRunnable = new Runnable() {
        @Override
        public void run() {
            String name = edSearch.getText().toString().trim();
            if (!TextUtils.isEmpty(name))
                mPresenter.getResult(name);
            else {
                ToastUtil.show(SearchActivity.this, "请输入内容", Toast.LENGTH_LONG);
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        mPresenter.getHot();
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.touming).statusBarDarkFont(false)
                .flymeOSStatusBarFontColor(R.color.touming).init();
        recycler.setLayoutManager(new GridLayoutManager(this, 4));

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        //纵向列表recyclerview
        recycler.setLayoutManager(manager);
        adapter = new SearchAdapter();
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String name = list.get(position).getName();
                edSearch.setText(name);
                // mPresenter.getResult(name);
            }
        });
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (delayRunnable != null) {
                    handler.removeCallbacks(delayRunnable);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.postDelayed(delayRunnable, 1000);
            }
        });
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.img_back, R.id.lin_search, R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.lin_search:
                break;
            case R.id.tv_cancel:
                edSearch.setText("");
                break;
        }
    }

    @Override
    public void returnHot(List<HotBean> list) {
        this.list = list;
        adapter.setNewData(list);
    }

    @Override
    public void returnResult(List<SearchResultBean> list) {
        if (list != null) {
            SearchResultActivity.startAction(this, list);
        } else {
            ToastUtil.show(this, "没有数据", Toast.LENGTH_LONG);
        }
    }
}
