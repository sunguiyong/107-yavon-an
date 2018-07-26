package com.zt.yavon.module.mine.presenter;

import android.text.TextUtils;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.module.mine.contract.ModifyEmailContract;
import com.zt.yavon.module.mine.contract.ModifyPhoneContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.RegexUtils;
import com.zt.yavon.utils.SPUtil;

/**
 * Created by hp on 2018/6/13.
 */

public class ModifyPhonePresenter extends ModifyPhoneContract.Presenter {

    @Override
    public void sendCode(String account) {
        mRxManage.add(Api.sendCode(account,"MODIFY_BIND_MOBILE", SPUtil.getToken(mContext))
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
    public void modifyPhone(String account,String code,String phone,String phoneConfirm) {
        if(TextUtils.isEmpty(account)){
            ToastUtil.showShort(mContext,"请输入手机号或邮箱");
            return;
        }
        if(TextUtils.isEmpty(code)){
            ToastUtil.showShort(mContext,"请输入验证码");
            return;
        }
        if(TextUtils.isEmpty(phone)){
            ToastUtil.showShort(mContext,"请输入手机号");
            return;
        }
        if(!RegexUtils.isMobile(phone)){
            ToastUtil.showShort(mContext,"手机号格式不正确");
            return;
        }
        if(!phone.equals(phoneConfirm)){
            ToastUtil.showShort(mContext,"两次输入手机号不一致");
            return;
        }
        mRxManage.add(Api.modifyPhone(SPUtil.getToken(mContext),account,code,phone,phoneConfirm)
                .subscribeWith(new RxSubscriber<LoginBean>(mContext,true) {
                    @Override
                    protected void _onNext(LoginBean bean) {
                        mView.modifySuccess(phone);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
