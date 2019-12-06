package com.zt.igreen.module.deviceconnect.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.igreen.module.data.CatogrieBean;
import com.zt.igreen.module.data.TabBean;
import com.zt.igreen.module.deviceconnect.contract.EditDevContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.Api2;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.network.YSBResponse;
import com.zt.igreen.utils.SPUtil;

import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class EditDevPresenter extends EditDevContract.Presenter {
    @Override
    public void bindBatteryLock(String name, String category_id, String room_id, TabBean.MachineBean machineBean) {
        mRxManage.add(Api2.getToken()
                .subscribeWith(new RxSubscriber<YSBResponse>(mContext, true) {
                    @Override
                    protected void _onNext(YSBResponse response) {
                        addScanLock(response.getHeader(), name, category_id, room_id, machineBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext, message);
                    }
                }).getDisposable());
    }

    private void addScanLock(String author, String name, String category_id, String room_id, TabBean.MachineBean machineBean) {
        mRxManage.add(Api2.addScanLock(author, machineBean.from_room)
                .subscribeWith(new RxSubscriber<YSBResponse>(mContext, true) {
                    @Override
                    protected void _onNext(YSBResponse response) {
                        machineBean.locker_id = response.getLock_id();
                        getLockPwd(author, name, category_id, room_id, machineBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext, message);
                    }
                }).getDisposable());
    }


    private void getLockPwd(String author, String name, String category_id, String room_id, TabBean.MachineBean machineBean) {
        mRxManage.add(Api2.getLockPwd(author, machineBean.asset_number)
                .subscribeWith(new RxSubscriber<YSBResponse>(mContext, true) {
                    @Override
                    protected void _onNext(YSBResponse response) {
                        machineBean.password = response.getData();
                        bindDev(name, category_id, room_id, machineBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext, message);
                    }
                }).getDisposable());
    }

    @Override
    public void getRoomList() {
        mRxManage.add(Api.getRoomList(SPUtil.getToken(mContext), "REMOVE_MACHINE")
                .subscribeWith(new RxSubscriber<List<TabBean>>(mContext, true) {
                    @Override
                    protected void _onNext(List<TabBean> list) {
                        mView.returnRoomList(list);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext, message);
                    }
                }).getDisposable());
    }

    @Override
    public void getCatogries(String type) {
        mRxManage.add(Api.getCatogries(SPUtil.getToken(mContext), type)
                .subscribeWith(new RxSubscriber<List<CatogrieBean>>(mContext, true) {
                    @Override
                    protected void _onNext(List<CatogrieBean> list) {
                        mView.returnCatogries(list);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext, message);
                    }
                }).getDisposable());
    }

//    @Override
//    public void deleteLockById(String lock_id) {
//        mRxManage.add(Api2.deleteLock(SPUtil.getToken(mContext), lock_id)
//                .subscribeWith(new RxSubscriber<YSBResponse>(mContext,true) {
//                    @Override
//                    protected void _onNext(YSBResponse response) {
//
//                    }
//                    @Override
//                    protected void _onError(String message) {
//                        ToastUtil.showShort(mContext,message);
//                    }
//                }).getDisposable());
//    }

    @Override
    public void bindDev(String name, String category_id, String room_id, TabBean.MachineBean machineBean) {
//        if(Constants.MACHINE_TYPE_BATTERY_LOCK.equals(type)){
//            asset_number = null;
//        }
        mRxManage.add(Api.bindDev(SPUtil.getToken(mContext), name, machineBean.from_room,
                machineBean.from_room, machineBean.from_room, machineBean.from_room, machineBean.asset_number, category_id, room_id,
                machineBean.machine_type)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext, true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.bindSuccess(true, null);
                    }

                    @Override
                    protected void _onError(String message) {
                        if (!TextUtils.isEmpty(message) && message.contains("已被绑定")) {
                            mView.bindSuccess(false, message);
                        } else {
                            ToastUtil.showShort(mContext, message);
                        }
                    }
                }).getDisposable());
    }

    @Override
    public void bindDev1(String name, String category_id, String room_id, TabBean.MachineBean machineBean) {
//        if(Constants.MACHINE_TYPE_BATTERY_LOCK.equals(type)){
//            asset_number = null;
//        }
        mRxManage.add(Api.bindDev(SPUtil.getToken(mContext), name, machineBean.from_room,
                machineBean.from_room, machineBean.from_room, machineBean.asset_number, machineBean.from_room, category_id, room_id, machineBean.machine_type)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext, true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.bindSuccess(true, null);
                    }

                    @Override
                    protected void _onError(String message) {
                        if (!TextUtils.isEmpty(message) && message.contains("已被绑定")) {
                            mView.bindSuccess(false, message);
                        } else {
                            ToastUtil.showShort(mContext, message);
                        }
                    }
                }).getDisposable());
    }

    @Override
    public void bindDev2(String name, String category_id, String room_id, TabBean.MachineBean machineBean) {
        mRxManage.add(Api.bindDev(SPUtil.getToken(mContext), name, machineBean.from_room,
                machineBean.asset_number, machineBean.from_room, machineBean.from_room, machineBean.from_room, category_id, room_id, machineBean.machine_type)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext, true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.bindSuccess(true, null);
                    }

                    @Override
                    protected void _onError(String message) {
                        if (!TextUtils.isEmpty(message) && message.contains("已被绑定")) {
                            mView.bindSuccess(false, message);
                        } else {
                            ToastUtil.showShort(mContext, message);
                        }
                    }
                }).getDisposable());
    }

    @Override
    public void bindDev3(String name, String room_id, TabBean.MachineBean machineBean) {
        mRxManage.add(Api.bindDev1(SPUtil.getToken(mContext), machineBean.asset_number, name, room_id, machineBean.from_room, machineBean.machine_type)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext, true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.bindSuccess(true, null);
                    }

                    @Override
                    protected void _onError(String message) {
                        if (!TextUtils.isEmpty(message) && message.contains("已被绑定")) {
                            mView.bindSuccess(false, message);
                        } else {
                            ToastUtil.showShort(mContext, message);
                        }
                    }
                }).getDisposable());
    }

    @Override
    public void bindDev4(String name, String room_id, String category_id, TabBean.MachineBean machineBean) {
        Log.e("xuxinyi", machineBean.asset_number);
        mRxManage.add(Api.bindDevBle(SPUtil.getToken(mContext), name, machineBean.asset_number, room_id, "1", machineBean.machine_type, machineBean.name)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext, true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.bindSuccess(true, null);
                    }

                    @Override
                    protected void _onError(String message) {
                        if (!TextUtils.isEmpty(message) && message.contains("已被绑定")) {
                            mView.bindSuccess(false, message);
                        } else {
                            ToastUtil.showShort(mContext, message);
                        }
                    }
                }).getDisposable());
    }

    @Override
    public void bindDev5(String name, String room_id, String category_id, TabBean.MachineBean machineBean) {
        Log.e("xuxinyi", machineBean.asset_number);
        mRxManage.add(Api.bindDevLock(SPUtil.getToken(mContext), machineBean.asset_number, machineBean.from_room, room_id, name, category_id, machineBean.machine_type)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext, true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.bindSuccess(true, null);
                    }

                    @Override
                    protected void _onError(String message) {
                        if (!TextUtils.isEmpty(message) && message.contains("已被绑定")) {
                            mView.bindSuccess(false, message);
                        } else {
                            ToastUtil.showShort(mContext, message);
                        }
                    }
                }).getDisposable());
    }
}
