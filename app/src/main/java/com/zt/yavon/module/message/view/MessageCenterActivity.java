package com.zt.yavon.module.message.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.MsgBean;
import com.zt.yavon.module.message.adapter.MsgCenterAdapter;
import com.zt.yavon.module.message.contract.MsgCenterContract;
import com.zt.yavon.module.message.presenter.MsgCenterPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by lifujun on 2018/7/17.
 */

public class MessageCenterActivity extends BaseActivity<MsgCenterPresenter> implements SwipeRefreshLayout.OnRefreshListener,MsgCenterContract.View{
    @BindView(R.id.swipe_message_center)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_message_center)
    RecyclerView recyclerView;
    private MsgCenterAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.message));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MsgCenterAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapterView, View view, int position) {
                MsgBean bean = adapter.getItem(position);
                int type = 0;
                if(adapter.TYPE_SYSTEM.equals(bean.getType())){
                  type = MessageListActivity.TYPE_SYS;
                }else if(adapter.TYPE_FAULT.equals(bean.getType())){
                    type = MessageListActivity.TYPE_ERROR;
                }else{
                    type = MessageListActivity.TYPE_SHARE;
                }
                MessageListActivity.startAction(MessageCenterActivity.this,type);
            }
        });
        mPresenter.getNotifications();
    }
    public static void startAction(Context context){
        Intent intent = new Intent(context,MessageCenterActivity.class);
        context.startActivity(intent);
    }
//    @OnClick({R.id.btn_confirm_bind_email})
//    @Override
//    public void doubleClickFilter(View view) {
//        super.doubleClickFilter(view);
//    }
//
//    @Override
//    public void doClick(View view) {
//        switch (view.getId()){
//            case R.id.btn_confirm_bind_email:
//                String email = etEmail.getText().toString();
//                if(!TextUtils.isEmpty(email)){
//                    Intent intent = new Intent();
//                    intent.putExtra("email",email);
//                    setResult(RESULT_OK,intent);
//                    finish();
//                }else{
//                    ToastUtil.showShort(this,"邮箱不能为空");
//                }
//                break;
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        mPresenter.getNotifications();
    }

    @Override
    public void returnDataList(List<MsgBean> list) {
        if(list != null){
            adapter.setNewData(list);
        }
    }
}
