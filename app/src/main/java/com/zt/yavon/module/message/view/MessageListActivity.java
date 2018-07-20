package com.zt.yavon.module.message.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.base.utils.LogUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.message.adapter.MsgCenterAdapter;
import com.zt.yavon.module.message.adapter.MsgListAdapter;
import com.zt.yavon.utils.DialogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/17.
 */

public class MessageListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    public static final int TYPE_SYS = 1;
    public static final int TYPE_ERROR = 2;
    public static final int TYPE_SHARE = 3;
    public static final int TYPE_INTERNAL = 4;
    @BindView(R.id.swipe_message_list)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_message_list)
    RecyclerView recyclerView;
    @BindView(R.id.btn_delete_msglist)
    View btnDelete;
    private Dialog dialog;
    private MsgListAdapter adapter;
    private int type;
    private LinearLayoutManager layoutManager;
    private Set<Object> list = new android.support.v4.util.ArraySet<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_message_list;
    }

    @Override
    public void initPresenter() {
        type = getIntent().getIntExtra("type",TYPE_SYS);
    }

    @Override
    public void initView() {
        if(type == TYPE_SYS){
            setTitle(getString(R.string.msg_sys));
        }else if(type == TYPE_ERROR){
            setTitle(getString(R.string.msg_error));
        }else  if(type == TYPE_SHARE){
            setTitle(getString(R.string.msg_share));
        }else{
            setTitle(getString(R.string.msg_internal));
        }
        setRightMenuText("选择");
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MsgListAdapter(type);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapterView, View view, int position) {
                if(adapter.isSelectMode()){
                    View chooseView = view.findViewById(R.id.iv_select);
                    chooseView.setSelected(!chooseView.isSelected());
                    if(chooseView.isSelected()){
                        list.add(adapter.getItem(position));
                    }else{
                        list.remove(adapter.getItem(position));
                    }
                }
            }
        });
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                dialog = DialogUtil.create2BtnInfoDialog(MessageListActivity.this, "确定要删除这条消息吗?", null, null, new DialogUtil.OnComfirmListening() {
                    @Override
                    public void confirm() {
                        adapter.notifyItemRemoved(position);
                    }
                });
                return true;
            }
        });
        List<Object> list = new ArrayList<>();
        for(int i= 0;i<10;i++){
            list.add(new Object());
        }
        adapter.setNewData(list);
    }
    public static void startAction(Context context,int type){
        Intent intent = new Intent(context,MessageListActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }
    @OnClick({R.id.tv_right_header,R.id.btn_delete_msglist})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.tv_right_header:
                if(adapter.isSelectMode()){
                    finish();
                }else{
                    changeMode(true);
                }
                break;
            case R.id.btn_delete_msglist:
                dialog = DialogUtil.create2BtnInfoDialog(MessageListActivity.this, "确定要删除所选消息吗?", null, null, new DialogUtil.OnComfirmListening() {
                    @Override
                    public void confirm() {
                        for(Object bean:list){
                            adapter.getData().remove(bean);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
        }
    }

    @Override
    public void onHeadBack() {
        if(adapter.isSelectMode()){
            changeMode(false);
            return;
        }
        super.onHeadBack();
    }

    @Override
    public void onBackPressed() {
        if(adapter.isSelectMode()){
            changeMode(false);
            return;
        }
        super.onBackPressed();
    }

    private void changeMode(boolean isChooseMode){
        if(isChooseMode){
            setRightMenuText("完成");
            setRightMenuTextColor(ContextCompat.getColor(this,R.color.mainGreen));
        }else{
            setRightMenuText("选择");
            setRightMenuTextColor(ContextCompat.getColor(this,R.color.white));
        }
        btnDelete.setVisibility(isChooseMode?View.VISIBLE:View.GONE);
        adapter.setSelectMode(isChooseMode);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }
    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
