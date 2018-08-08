package com.zt.yavon.module.main.frame.presenter;

import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.main.frame.contract.HomeContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class HomePresenter extends HomeContract.Presenter {
    public String SET_SWITCH = "on";
    public String SET_LINK = "link";
    public String SET_FAN = "fan";
    public String SET_MODE = "mode";


    @Override
    public void getTabData() {
        mRxManage.add(Api.getTabData(SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<List<TabBean>>(mContext, true) {
                    @Override
                    protected void _onNext(List<TabBean> response) {
                        mView.returnTabData(response);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.returnTabData(null);
                        mView.errorTabData(message);
                    }
                }).getDisposable());
    }
}
