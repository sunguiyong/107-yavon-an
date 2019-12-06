package com.zt.igreen.module.deviceconnect.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.base.utils.ToastUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.data.CatogrieBean;
import com.zt.igreen.module.data.TabBean;
import com.zt.igreen.module.deviceconnect.adapter.EditTypeAdapter;
import com.zt.igreen.module.deviceconnect.adapter.RoomNameAdapter;
import com.zt.igreen.module.deviceconnect.contract.EditDevContract;
import com.zt.igreen.module.deviceconnect.presenter.EditDevPresenter;
import com.zt.igreen.module.main.roommanager.add.view.ActAllRoom;
import com.zt.igreen.utils.Constants;
import com.zt.igreen.utils.DialogUtil;
import com.zt.igreen.widget.CustomEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lifujun on 2018/7/10.
 */

public class EditDevActivity extends BaseActivity<EditDevPresenter> implements EditDevContract.View {
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
    private Dialog dialog;
    private TabBean.MachineBean machineBean;
    private String mac;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dev_edit;
    }

    @Override
    public void initPresenter() {
        machineBean = (TabBean.MachineBean) getIntent().getSerializableExtra("machineBean");
        mac = getIntent().getStringExtra("mac");
        if (!TextUtils.isEmpty(mac))
            machineBean.asset_number = mac;
        mPresenter.setVM(this);
    }

    @Override
    public void initView() {
        sethead(R.color.touming);
        setColor(Color.parseColor("#ffffff"));
        ImmersionBar.with(this).statusBarColor(R.color.touming).statusBarDarkFont(false).flymeOSStatusBarFontColor(R.color.touming).init();
        setColor(Color.parseColor("#ffffff"));
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
                defaultRoomId = bean.id + "";
            }
        });
        Log.e("xuxinyimachine_type", machineBean.machine_type + "");
        switch (machineBean.machine_type) {
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
            case Constants.MACHINE_TYPE_WATER_DISPENSER:
                defaultName = "饮水机";
                typeLayout.setVisibility(View.GONE);
                break;
            case Constants.MACHINE_TYPE_NFC_COASTER:
                defaultName = "NFC杯垫";
                typeLayout.setVisibility(View.GONE);
                break;
            case Constants.MACHINE_TYPE_AIR_MACHINE:
                defaultName = "新风机";
                typeLayout.setVisibility(View.GONE);
                break;
            case Constants.MACHINE_TYPE_AIR_HEALTH:
                defaultName = "健康指环";
                typeLayout.setVisibility(View.GONE);
                break;
        }
        etDevName.setHint("设备名称（默认" + defaultName + "）");
        mPresenter.getRoomList();
    }

    private void initType() {
        recyclerView2.setLayoutManager(new GridLayoutManager(this, 3));
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

    @OnClick({R.id.tv_room_add_edit, R.id.tv_submit_edit})
    @Override
    public void doubleClickFilter(View view) {
        super.doubleClickFilter(view);
    }

    @Override
    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.tv_room_add_edit:
                //添加房间
                startActForResult(ActAllRoom.class);
                break;
            case R.id.tv_submit_edit:
                String name = etDevName.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    name = defaultName;
                }
                String catagoryId = null;
                if (typeAdapter != null) {
                    catagoryId = typeAdapter.getSelectItem().getId();
                }
                String roomId = null;
                if (!TextUtils.isEmpty(tvRoomName.getText().toString().trim())) {
                    roomId = defaultRoomId;
                }
//                if(Constants.MACHINE_TYPE_BATTERY_LOCK.equals(machineBean.machine_type)){
//                    mPresenter.bindBatteryLock(name,catagoryId,roomId,machineBean);
//                }else{

                if (Constants.MACHINE_TYPE_WATER_DISPENSER.equals(machineBean.machine_type) || Constants.MACHINE_TYPE_NFC_COASTER.equals(machineBean.machine_type)) {
                    mPresenter.bindDev1(name, null, roomId, machineBean);
                } else if (Constants.MACHINE_TYPE_AIR_MACHINE.equals(machineBean.machine_type)) {
                    mPresenter.bindDev2(name, null, roomId, machineBean);
                } else if (Constants.MACHINE_TYPE_ADJUST_TABLE.equals(machineBean.machine_type)) {
                    mPresenter.bindDev3(name, roomId, machineBean);
                } else if (Constants.MACHINE_TYPE_AIR_HEALTH.equals(machineBean.machine_type)) {
                    mPresenter.bindDev4(name, roomId, catagoryId, machineBean);
                } else if (Constants.MACHINE_TYPE_BATTERY_LOCK.equals(machineBean.machine_type)) {
                    mPresenter.bindDev5(name, roomId, catagoryId, machineBean);
                } else {
                    Log.e("xuxinyi", machineBean.toString());
                    mPresenter.bindDev(name, catagoryId, roomId, machineBean);
                }

//                }
                break;
        }
    }

    public static void startAction(Context context, TabBean.MachineBean machineBean) {
        Intent intent = new Intent(context, EditDevActivity.class);
        intent.putExtra("machineBean", machineBean);
        context.startActivity(intent);
    }

    public static void startAction(Context context, TabBean.MachineBean machineBean, String mac) {
        Intent intent = new Intent(context, EditDevActivity.class);
        intent.putExtra("machineBean", machineBean);
        intent.putExtra("mac", mac);
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
    public void bindSuccess(boolean result, String msg) {
        if (result) {
            ToastUtil.showShort(this, "绑定成功");
            mRxManager.post(Constants.EVENT_BIND_DEV_SUCCESS, 1);
            mRxManager.post(Constants.EVENT_REFRESH_HOME, 0);
            finish();
        } else {
            dialog = DialogUtil.createInfoDialogWithListener(this, "设备已被绑定，您可以申请设备", new DialogUtil.OnComfirmListening() {
                @Override
                public void confirm() {
                    //  ScanCodeActivity.start(EditDevActivity.this);
                    mRxManager.post(Constants.EVENT_BIND_DEV_SUCCESS, 1);
                    mRxManager.post(Constants.EVENT_REFRESH_HOME, 0);
                    finish();
                }
            });
        }
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

    @Override
    protected void onDestroy() {
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }
}
