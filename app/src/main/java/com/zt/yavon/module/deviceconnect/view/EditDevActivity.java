package com.zt.yavon.module.deviceconnect.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.CatogrieBean;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.deviceconnect.adapter.EditTypeAdapter;
import com.zt.yavon.module.deviceconnect.adapter.RoomNameAdapter;
import com.zt.yavon.module.deviceconnect.contract.EditDevContract;
import com.zt.yavon.module.deviceconnect.presenter.EditDevPresenter;
import com.zt.yavon.module.main.roommanager.add.view.ActAddRoom;
import com.zt.yavon.utils.Constants;
import com.zt.yavon.widget.CustomEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class EditDevActivity extends BaseActivity<EditDevPresenter> implements EditDevContract.View{
//    public static final int TYPE_LAMP = 1;
//    public static final int TYPE_LOCK1 = 3;
//    public static final int TYPE_LOCK2 = 4;
//    public static final int TYPE_DESK = 5;
    @BindView(R.id.et_name_dev)
    CustomEditText etDevName;
    @BindView(R.id.layout_type_edit)
    View typeLayout;
    @BindView(R.id.tv_name_room)
    TextView tvRoomName;
    @BindView(R.id.recyclerview_edit_dev)
    RecyclerView recyclerView1;
    @BindView(R.id.recyclerview2_dev_edit)
    RecyclerView recyclerView2;
    private RoomNameAdapter nameAdapter;
    private EditTypeAdapter typeAdapter;
    private String defaultName;
    private String defaultRoomId;
    private String typeString;
    private TabBean.MachineBean machineBean;
    @Override
    public int getLayoutId() {
        return R.layout.activity_dev_edit;
    }

    @Override
    public void initPresenter() {
        machineBean = (TabBean.MachineBean) getIntent().getSerializableExtra("machineBean");
        mPresenter.setVM(this);
    }

    @Override
    public void initView() {
        setTitle("修改设备");
//        setRightMenuImage(R.mipmap.more_right);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        nameAdapter = new RoomNameAdapter();
        nameAdapter.bindToRecyclerView(recyclerView1);
        nameAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TabBean bean = nameAdapter.getItem(position);
                tvRoomName.setText(bean.name);
                defaultRoomId = bean.id+"";
            }
        });
        switch (machineBean.machine_type){
            case Constants.MACHINE_TYPE_BLUE_LOCK:
                defaultName = "蓝牙琐";
                typeString = "LOCK";
                initType();
                break;
            case Constants.MACHINE_TYPE_BATTERY_LOCK:
                defaultName = "电池琐";
                typeString = "LOCK";
                initType();
                break;
            case Constants.MACHINE_TYPE_LIGHT:
                typeString = "LIGHT";
                defaultName = "智能灯";
                initType();
                break;
            case Constants.MACHINE_TYPE_ADJUST_TABLE:
                defaultName = "智能桌";
                typeLayout.setVisibility(View.GONE);
                break;
        }
        etDevName.setHint("设备名称（默认"+defaultName+"）");
        mPresenter.getRoomList();
    }
    private void initType(){
        recyclerView2.setLayoutManager(new GridLayoutManager(this,3));
        typeAdapter = new EditTypeAdapter();
        typeAdapter.bindToRecyclerView(recyclerView2);
        typeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                typeAdapter.setSelectedPosition(position);
            }
        });
        mPresenter.getCatogries(typeString);
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
                startActForResult(ActAddRoom.class);
                break;
            case R.id.tv_submit_edit:
                String name = etDevName.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    name = defaultName;
                }
                String catagoryId = null;
                if(typeAdapter != null){
                    catagoryId = typeAdapter.getSelectItem().getId();
                }
                String roomId = null;
                if(!TextUtils.isEmpty(tvRoomName.getText().toString().trim())){
                    roomId = defaultRoomId;
                }
                if(Constants.MACHINE_TYPE_BATTERY_LOCK.equals(machineBean.machine_type)){
                    mPresenter.bindBatteryLock(name,catagoryId,roomId,machineBean);
                }else{
                    mPresenter.bindDev(name,catagoryId,roomId,machineBean);
                }
                break;
        }
    }
    public static void startAction(Context context,TabBean.MachineBean machineBean){
        Intent intent = new Intent(context,EditDevActivity.class);
        intent.putExtra("machineBean",machineBean);
        context.startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_COMMON && resultCode == RESULT_OK) {
            //刷新房间名列表
            mPresenter.getRoomList();
        }
    }

    @Override
    public void returnRoomList(List<TabBean> list) {
        nameAdapter.setNewData(list);
    }

    @Override
    public void returnCatogries(List<CatogrieBean> list) {
        typeAdapter.setNewData(list);
    }

    @Override
    public void bindSuccess() {
        ToastUtil.showShort(this,"绑定成功");
        mRxManager.post(Constants.EVENT_BIND_DEV_SUCCESS,1);
        finish();
    }

//    @Override
//    public void devExist() {
//        switch (machineBean.machine_type){
//            case Constants.MACHINE_TYPE_BLUE_LOCK:
//                break;
//            case Constants.MACHINE_TYPE_BATTERY_LOCK:
//                //删除琐
//                mPresenter.deleteLockById(machineBean.locker_id);
//                break;
//            case Constants.MACHINE_TYPE_LIGHT:
//                break;
//            case Constants.MACHINE_TYPE_ADJUST_TABLE:
//                break;
//        }
//    }
}
