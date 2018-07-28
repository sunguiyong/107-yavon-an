package com.zt.yavon.module.mine.presenter;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.module.data.MineRoomBean;
import com.zt.yavon.module.mine.contract.AllDevContract;
import com.zt.yavon.module.mine.contract.SettingContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class AllDevPresenter extends AllDevContract.Presenter {

    @Override
    public void getAllDevs() {
        mRxManage.add(Api.getAllDevs(SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<List<MineRoomBean>>(mContext,true) {
                    @Override
                    protected void _onNext(List<MineRoomBean> list) {
                        mView.returnDevs(list);
                    }
                    @Override
                    protected void _onError(String message) {
                        mView.returnDevs(null);
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
