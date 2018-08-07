package com.zt.yavon.module.deviceconnect.presenter;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.CatogrieBean;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.deviceconnect.contract.EditDevContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.Api2;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.network.YSBResponse;
import com.zt.yavon.utils.SPUtil;

import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class EditDevPresenter extends EditDevContract.Presenter {
    @Override
    public void bindBatteryLock(String name,String category_id,String room_id,TabBean.MachineBean machineBean) {
        mRxManage.add(Api2.getToken()
                .subscribeWith(new RxSubscriber<YSBResponse>(mContext,true) {
                    @Override
                    protected void _onNext(YSBResponse response) {
                        addScanLock(response.getHeader(),name,category_id,room_id,machineBean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    private void addScanLock(String author, String name,String category_id,String room_id,TabBean.MachineBean machineBean) {
        mRxManage.add(Api2.addScanLock(author,machineBean.from_room)
                .subscribeWith(new RxSubscriber<YSBResponse>(mContext,true) {
                    @Override
                    protected void _onNext(YSBResponse response) {
                        machineBean.locker_id = response.getLock_id();
                        getLockPwd(author,name,category_id,room_id,machineBean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }


    private void getLockPwd(String author,String name,String category_id,String room_id,TabBean.MachineBean machineBean) {
        mRxManage.add(Api2.getLockPwd(author,machineBean.asset_number)
                .subscribeWith(new RxSubscriber<YSBResponse>(mContext,true) {
                    @Override
                    protected void _onNext(YSBResponse response) {
                        machineBean.password = response.getData();
                        bindDev(name,category_id,room_id,machineBean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
    @Override
    public void getRoomList() {
        mRxManage.add(Api.getTabData(SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<List<TabBean>>(mContext,true) {
                    @Override
                    protected void _onNext(List<TabBean> list) {
                        mView.returnRoomList(list);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void getCatogries(String type) {
        mRxManage.add(Api.getCatogries(SPUtil.getToken(mContext),type)
                .subscribeWith(new RxSubscriber<List<CatogrieBean>>(mContext,true) {
                    @Override
                    protected void _onNext(List<CatogrieBean> list) {
                        mView.returnCatogries(list);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
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
    public void bindDev(String name,String category_id,String room_id,TabBean.MachineBean machineBean) {
//        if(Constants.MACHINE_TYPE_BATTERY_LOCK.equals(type)){
//            asset_number = null;
//        }
        mRxManage.add(Api.bindDev(SPUtil.getToken(mContext),name,machineBean.asset_number,
                machineBean.asset_number,machineBean.asset_number,category_id,room_id,
                machineBean.machine_type,machineBean.locker_id,machineBean.password)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.bindSuccess();
                    }
                    @Override
                    protected void _onError(String message) {
//                        if(!TextUtils.isEmpty(message) && message.contains("设备已被绑定")){
//                            mView.devExist();
//                        }
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
