package com.zt.yavon.module.deviceconnect.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.deviceconnect.adapter.EditTypeAdapter;
import com.zt.yavon.module.deviceconnect.adapter.RoomNameAdapter;
import com.zt.yavon.widget.CustomEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class EditDevActivity extends BaseActivity{
    public static final int TYPE_LAMP = 1;
    public static final int TYPE_LOCK = 3;
    public static final int TYPE_DESK = 5;
    @BindView(R.id.et_name_dev)
    CustomEditText etDevName;
    @BindView(R.id.tv_name_room)
    TextView tvRoomName;
    @BindView(R.id.recyclerview_edit_dev)
    RecyclerView recyclerView1;
    @BindView(R.id.recyclerview2_dev_edit)
    RecyclerView recyclerView2;
    private RoomNameAdapter nameAdapter;
    private EditTypeAdapter typeAdapter;
    private int type;
    @Override
    public int getLayoutId() {
        return R.layout.activity_dev_edit;
    }

    @Override
    public void initPresenter() {
        type = getIntent().getIntExtra("type",TYPE_DESK);
    }

    @Override
    public void initView() {
        setTitle("修改设备");
//        setRightMenuImage(R.mipmap.more_right);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        nameAdapter = new RoomNameAdapter();
        nameAdapter.bindToRecyclerView(recyclerView1);
        nameAdapter.addData(new Object());
        nameAdapter.addData(new Object());
        nameAdapter.addData(new Object());
        if(type != TYPE_DESK){
            recyclerView2.setLayoutManager(new GridLayoutManager(this,3));
            typeAdapter = new EditTypeAdapter();
            typeAdapter.bindToRecyclerView(recyclerView2);
            typeAdapter.addData(new Object());
            typeAdapter.addData(new Object());
            typeAdapter.addData(new Object());
            typeAdapter.addData(new Object());
        }
    }

    @OnClick({R.id.tv_room_add_edit,R.id.tv_submit_edit})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.tv_room_add_edit:
                //添加房间
                break;
            case R.id.tv_submit_edit:
                break;
        }
    }
    public static void startAction(Context context,int type){
        Intent intent = new Intent(context,EditDevActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }
}
