package com.zt.igreen.module.mine.presenter;

import android.text.TextUtils;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.igreen.module.data.LoginBean;
import com.zt.igreen.module.mine.contract.ModifyEmailContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.RegexUtils;
import com.zt.igreen.utils.SPUtil;

/**
 * Created by hp on 2018/6/13.
 */

public class ModifyEmailPresenter extends ModifyEmailContract.Presenter {

    @Override
    public void sendCode(String account) {
        mRxManage.add(Api.sendCode(account,"MODIFY_BIND_EMAIL", SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.sendCodeResult(null);
                    }
                    @Override
                    protected void _onError(String message) {
                        mView.sendCodeResult(message);
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void modifyEmail(String account,String code,String email,String emailConfirm) {
        if(TextUtils.isEmpty(account)){
            ToastUtil.showShort(mContext,"请输入手机号或邮箱");
            return;
        }
        if(TextUtils.isEmpty(code)){
            ToastUtil.showShort(mContext,"请输入验证码");
            return;
        }
        if(TextUtils.isEmpty(email)){
            ToastUtil.showShort(mContext,"请输入邮箱");
            return;
        }
        if(!RegexUtils.checkEmail(email)){
            ToastUtil.showShort(mContext,"邮箱格式不正确");
            return;
        }
        if(!email.equals(emailConfirm)){
            ToastUtil.showShort(mContext,"两次输入邮箱不一致");
            return;
        }
        mRxManage.add(Api.modifyEmail(SPUtil.getToken(mContext),account,code,email,emailConfirm)
                .subscribeWith(new RxSubscriber<LoginBean>(mContext,true) {
                    @Override
                    protected void _onNext(LoginBean bean) {
                        mView.modifySuccess(email);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
