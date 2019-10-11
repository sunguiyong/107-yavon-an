package com.zt.igreen.module.mine.presenter;

import com.common.base.utils.ToastUtil;
import com.zt.igreen.module.data.MineRoomBean;
import com.zt.igreen.module.mine.contract.AllDevContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.SPUtil;

import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class AllDevPresenter extends AllDevContract.Presenter {

    @Override
    public void getAllDevs(boolean showLoading) {
        mRxManage.add(Api.getAllDevs(SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<List<MineRoomBean>>(mContext,showLoading) {
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
