package com.zt.igreen.module.mine.presenter;

import android.text.TextUtils;

import com.common.base.utils.ToastUtil;
import com.zt.igreen.module.data.LoginBean;
import com.zt.igreen.module.mine.contract.BindEmailContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.RegexUtils;
import com.zt.igreen.utils.SPUtil;

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
