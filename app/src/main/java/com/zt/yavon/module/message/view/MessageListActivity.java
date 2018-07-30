package com.zt.yavon.module.message.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.common.base.utils.LogUtil;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.MsgBean;
import com.zt.yavon.module.message.adapter.MsgListAdapter;
import com.zt.yavon.module.message.contract.MessageListContract;
import com.zt.yavon.module.message.presenter.MsgListPresenter;
import com.zt.yavon.utils.DialogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/17.
 */

public class MessageListActivity extends BaseActivity<MsgListPresenter> implements SwipeRefreshLayout.OnRefreshListener,MessageListContract.View,BaseQuickAdapter.RequestLoadMoreListener{
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
    private int curPage = 1;
    private int COUNT_PER_PAGE = 20;
    @Override
    public int getLayoutId() {
        return R.layout.activity_message_list;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
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
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MsgListAdapter(type);
        View empView = getLayoutInflater().inflate(R.layout.view_empty,null);
        adapter.setEmptyView(empView);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnLoadMoreListener(this,recyclerView);
        adapter.setEnableLoadMore(false);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapterView, View view, int position) {
                MsgBean bean = adapter.getItem(position);
                if(adapter.isSelectMode()){
                    if(bean.isSelect()){
                        bean.setSelect(false);
                    }else{
                        bean.setSelect(true);
                    }
                    adapter.notifyItemChanged(position);
                }else{
                    if(!bean.isIs_read())
                    mPresenter.readMsg(type,bean);
                }
            }
        });
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapterView, View view, int position) {
                dialog = DialogUtil.create2BtnInfoDialog(MessageListActivity.this, "确定要删除这条消息吗?", null, null, new DialogUtil.OnComfirmListening() {
                    @Override
                    public void confirm() {
                        List<MsgBean> list = new ArrayList<>();
                        list.add(adapter.getItem(position));
                        mPresenter.deleteMsg(type,list);
                    }
                });
                return true;
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapterView, View view, int position) {
                MsgBean bean = adapter.getItem(position);
                if(type == TYPE_ERROR) {
                    if ("MACHINE_B_FAULT".equals(bean.getFlag())) {//使用者上报
                        DialogUtil.dismiss(dialog);
                        dialog = DialogUtil.create2BtnInfoDialog(MessageListActivity.this, "该设备是否已解决故障?", "未解决", "已解决", new DialogUtil.OnComfirmListening() {
                            @Override
                            public void confirm() {//故障状态操作
                                mPresenter.doFaultMsg(bean);
                            }
                        });
                    } else {//自己申请的
                        ToastUtil.showShort(MessageListActivity.this, "无法操作自己申请的故障");
                    }
                }else if(type == TYPE_SHARE){
                    if("APPLY".equals(bean.getType()) && bean.isIs_operate() && "WAIT".equals(bean.getStatus())){
                        dialog = DialogUtil.create2BtnInfoDialog2(MessageListActivity.this, bean.getContent(), "拒绝", "同意", new DialogUtil.OnComfirmListening() {
                            @Override
                            public void confirm() {//故障状态操作
                                mPresenter.doShareMsg(bean,false);
                            }
                        }, new DialogUtil.OnComfirmListening() {
                            @Override
                            public void confirm() {
                                mPresenter.doShareMsg(bean,true);
                            }
                        });
                    }
                }
            }
        });
        onRefresh();
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
                changeMode();
                break;
            case R.id.btn_delete_msglist:
                dialog = DialogUtil.create2BtnInfoDialog(MessageListActivity.this, "确定要删除所选消息吗?", null, null, new DialogUtil.OnComfirmListening() {
                    @Override
                    public void confirm() {
                        mPresenter.deleteMsg(type,adapter.getSelectItems());
                    }
                });
                break;
        }
    }

    @Override
    public void onHeadBack() {
        if(adapter.isSelectMode()){
            changeMode();
            return;
        }
        super.onHeadBack();
    }

    @Override
    public void onBackPressed() {
        if(adapter.isSelectMode()){
            changeMode();
            return;
        }
        super.onBackPressed();
    }

    private void changeMode(){
        boolean isChooseMode = !adapter.isSelectMode();
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
        curPage = 1;
        adapter.setEnableLoadMore(false);
        onLoadMoreRequested();
    }

    @Override
    public void returnDataList(List<MsgBean> list) {
        if(list == null){
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
        if(curPage == 1){
            if(list.size() == 0){
                adapter.setSelectMode(false);
                setRightMenuText("");
                btnDelete.setVisibility(View.GONE);
            }else{
                if(adapter.isSelectMode()){
                    setRightMenuText("完成");
                    setRightMenuTextColor(ContextCompat.getColor(this,R.color.mainGreen));
                    btnDelete.setVisibility(View.VISIBLE);
                }else{
                    setRightMenuText("选择");
                    setRightMenuTextColor(ContextCompat.getColor(this,R.color.white));
                    btnDelete.setVisibility(View.GONE);
                }
            }
            adapter.setNewData(list);
        }else{
            adapter.addData(list);
        }
        if(curPage == 1){
            adapter.disableLoadMoreIfNotFullPage();
        }else if(list.size() < COUNT_PER_PAGE){
            adapter.loadMoreEnd();
        }else{
            adapter.loadMoreComplete();
        }
        curPage++;
    }

    @Override
    public void deleteSuccess(List<MsgBean> list) {
        for(MsgBean bean:list){
            adapter.getData().remove(bean);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void readSuccess(MsgBean bean) {
        bean.setIs_read(true);
        adapter.notifyItemChanged(adapter.getData().indexOf(bean));
    }

    @Override
    public void doMsgSuccess(MsgBean bean) {
        adapter.notifyItemChanged(adapter.getData().indexOf(bean));
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getMsgList(type,curPage,COUNT_PER_PAGE);
    }
}
