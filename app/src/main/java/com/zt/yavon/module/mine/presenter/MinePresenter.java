package com.zt.yavon.module.mine.presenter;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.module.mine.contract.MineContract;
import com.zt.yavon.module.mine.contract.PersonalInfoContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

/**
 * Created by hp on 2018/6/13.
 */

public class MinePresenter extends MineContract.Presenter {

    @Override
    public void getPersonalInfo() {
        mRxManage.add(Api.personalInfo(SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<LoginBean>(mContext,false) {
                    @Override
                    protected void _onNext(LoginBean bean) {
                        mView.returnPersonalInfo(bean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
