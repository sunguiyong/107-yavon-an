package com.zt.yavon.module.mine.presenter;

import android.text.TextUtils;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.module.mine.contract.BindEmailContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.RegexUtils;
import com.zt.yavon.utils.SPUtil;

/**
 * Created by hp on 2018/6/13.
 */

public class BindEmailPresenter extends BindEmailContract.Presenter {

    @Override
    public void bindEmail(String email) {
        if(TextUtils.isEmpty(email)){
            ToastUtil.showShort(mContext,"请输入邮箱");
            return;
        }
        if(!RegexUtils.checkEmail(email)){
            ToastUtil.showShort(mContext,"邮箱格式不正确");
            return;
        }
        mRxManage.add(Api.bindEmail(SPUtil.getToken(mContext),email)
                .subscribeWith(new RxSubscriber<LoginBean>(mContext,true) {
                    @Override
                    protected void _onNext(LoginBean bean) {
                        mView.bindSuccess(email);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
