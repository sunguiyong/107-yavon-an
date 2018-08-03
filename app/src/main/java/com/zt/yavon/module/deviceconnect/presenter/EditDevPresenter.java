package com.zt.yavon.module.deviceconnect.presenter;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.CatogrieBean;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.deviceconnect.contract.EditDevContract;
import com.zt.yavon.module.deviceconnect.view.EditDevActivity;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.Constants;
import com.zt.yavon.utils.SPUtil;

import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class EditDevPresenter extends EditDevContract.Presenter {

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

    @Override
    public void bindDev(String name, String asset_number, String sn, String category_id, String room_id, String type,String lockId, String password) {
        if(Constants.MACHINE_TYPE_BATTERY_LOCK.equals(type)){
            asset_number = null;
        }
        mRxManage.add(Api.bindDev(SPUtil.getToken(mContext),name,asset_number,sn,category_id,room_id,type,lockId,password)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.bindSuccess();
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
