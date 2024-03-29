//package com.zt.yavon.module.main.roommanager.setting.view;
//
//import android.view.View;
//import android.widget.Button;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.listener.OnItemClickListener;
//import com.zt.yavon.R;
//import com.zt.yavon.component.BaseActivity;
//import com.zt.yavon.module.main.roommanager.setting.adapter.RvRoomAdd;
//import com.zt.yavon.module.main.roommanager.setting.contract.RoomSettingContract;
//import com.zt.yavon.module.main.roommanager.setting.model.RoomItemBean;
//import com.zt.yavon.module.main.roommanager.setting.presenter.RoomSettingPresenter;
//
//import java.util.List;
//
//import butterknife.BindView;
//
///**
// * Author: Administrator
// * Date: 2018/7/17
// */
//public class ActAddRoom extends BaseActivity<RoomSettingPresenter> implements RoomSettingContract.View {
//    @BindView(R.id.rv_room_add)
//    RvRoomAdd rvRoomAdd;
//    @BindView(R.id.btn_ok)
//    Button btnOk;
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.act_add_room_layout;
//    }
//
//    @Override
//    public void initPresenter() {
//        mPresenter.setVM(this);
//    }
//
//    @Override
//    public void initView() {
//        setTitle("添加房间");
//        rvRoomAdd.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                rvRoomAdd.onItemCheck(position);
//            }
//        });
////        btnOk.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if (rvRoomAdd.mCheckedPosition != -1 && rvRoomAdd.mCheckedPosition != rvRoomAdd.mAdapter.getItemCount() - 1) {
////                    setResult(RESULT_OK, rvRoomAdd.mAdapter.getItem(rvRoomAdd.mCheckedPosition));
////                    finish();
////                }
////            }
////        });
//        mPresenter.getAddRoomData();
//    }
//
//    @Override
//    public void returnAddRoomData(List<RoomItemBean> data) {
//        rvRoomAdd.setData(data);
//    }
//
//    @Override
//    public void errorAddRoomData(String message) {
//        rvRoomAdd.onGetDataFail(message, false);
//    }
//}
