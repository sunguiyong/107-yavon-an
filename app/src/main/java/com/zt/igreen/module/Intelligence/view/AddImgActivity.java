package com.zt.igreen.module.Intelligence.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.Intelligence.adapter.ImgAdapter;
import com.zt.igreen.module.Intelligence.contract.ImgAddContract;
import com.zt.igreen.module.Intelligence.presenter.ImgAddPresenter;
import com.zt.igreen.module.data.AddImgBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2019/3/5 0005.
 */

public class AddImgActivity extends BaseActivity<ImgAddPresenter> implements ImgAddContract.View {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.btn_close_header)
    ImageView btnCloseHeader;
    @BindView(R.id.tv_right_header1)
    TextView tvRightHeader1;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.lin_add_text)
    LinearLayout linAddText;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.lin_add_intell)
    LinearLayout linAddIntell;
    @BindView(R.id.lin_dian)
    LinearLayout linDian;
    @BindView(R.id.btn_back_header)
    TextView btnBackHeader;
    private ImgAdapter adapter;
    List<String> listbean = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_addimg;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        mPresenter.imgsuccess();
    }

    @Override
    public void initView() {
        sethead(R.color.backgroundblack);
        setColor(Color.parseColor("#ffffff"));
        ImmersionBar.with(this).statusBarColor(R.color.touming).statusBarDarkFont(false)
                .flymeOSStatusBarFontColor(R.color.touming).init();
        setColor(Color.parseColor("#ffffff"));
        setTitle("设置封面");
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ImgAdapter(this);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String url = listbean.get(position);
                EventBus.getDefault().post(new AddImgBean(url));
                finish();
            }
        });
        btnBackHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, AddImgActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void imgSuccess(List<String> list) {
        listbean = list;
        Log.e("xuxinyi", list.size() + "");
        adapter.setNewData(list);
    }

}
