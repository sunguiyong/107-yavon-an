package com.zt.yavon.module.account.presenter;

import android.text.TextUtils;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.LogUtil;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.account.contract.LoginRegisterContract;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.RegexUtils;

/**
 * Created by lifujun on 2018/7/25.
 */

public class LoginRegisterPresenter extends LoginRegisterContract.Presenter{
    @Override
    public void sendCode(String account) {
        mRxManage.add(Api.sendCode(account,"REGISTER","")
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        LogUtil.d("======LoginRegisterPresenter,msg:"+response.getMessage());
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
    public void register(String mobile, String code, String pwd, String confirmPwd) {
        if(TextUtils.isEmpty(mobile)){
            ToastUtil.showShort(mContext,"手机号不能为空");
            return;
        }
        if(!RegexUtils.isMobile(mobile)){
            ToastUtil.showShort(mContext,"手机号不正确");
            return;
        }
        if(TextUtils.isEmpty(code)){
            ToastUtil.showShort(mContext,"验证码不能为空");
            return;
        }
        if(TextUtils.isEmpty(pwd)){
            ToastUtil.showShort(mContext,"密码不能为空");
            return;
        }
        if(TextUtils.isEmpty(confirmPwd) || !pwd.equals(confirmPwd)){
            ToastUtil.showShort(mContext,"再次密码输入不一致");
            return;
        }
        mRxManage.add(Api.register(mobile,code,pwd,confirmPwd)
                .subscribeWith(new RxSubscriber<LoginBean>(mContext,true) {
                    @Override
                    protected void _onNext(LoginBean bean) {
                        if(bean != null)
                        bean.setPwd(pwd);
                        mView.loginRegisterSuccess(bean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }


    @Override
    public void login(String account, String password) {
        if(TextUtils.isEmpty(account)){
            ToastUtil.showShort(mContext,"帐号不能为空");
            return;
        }
//        if(!RegexUtils.isMobile(mobile)){
//            ToastUtil.showShort(mContext,"手机号不正确");
//            return;
//        }
        if(TextUtils.isEmpty(password)){
            ToastUtil.showShort(mContext,"密码不能为空");
            return;
        }
        mRxManage.add(Api.login(account,password)
                .subscribeWith(new RxSubscriber<LoginBean>(mContext,true) {
                    @Override
                    protected void _onNext(LoginBean bean) {
                        if(bean != null)
                            bean.setPwd(password);
                        mView.loginRegisterSuccess(bean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
