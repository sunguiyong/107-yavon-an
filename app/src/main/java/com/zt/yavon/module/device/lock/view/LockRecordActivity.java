package com.zt.yavon.module.device.lock.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.device.lock.adapter.LockRecordAdapter;
import com.zt.yavon.module.data.LockRecordItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lifujun on 2018/7/10.
 */

public class LockRecordActivity extends BaseActivity{
    @BindView(R.id.recycler_record_lock)
    RecyclerView recyclerView;
    private LockRecordAdapter adapter;

    //    @BindView(R.id.tv_switch_lock)
//    TextView tvSwith;
    @Override
    public int getLayoutId() {
        return R.layout.activity_record_use_lock;
    }
    @Override
    public void setThemeStyle() {
        super.setThemeStyle();
        setStatusBarColor(ContextCompat.getColor(this,R.color.black2));
    }
    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitleBackgroudColor(R.color.black2);
        setTitle(getString(R.string.more_record));
//        setRightMenuImage(R.mipmap.more_right);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor(getContext(),R.color.colorPrimary), DensityUtil.dp2px(getContext(),10f));
//        itemDecoration.setDrawLastItem(false);
//        recyclerView.addItemDecoration(itemDecoration);
        adapter = new LockRecordAdapter();
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
        Intent intent = new Intent(context,LockRecordActivity.class);
        context.startActivity(intent);
    }
}
