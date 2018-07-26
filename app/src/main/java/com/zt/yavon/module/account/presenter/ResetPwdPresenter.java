package com.zt.yavon.module.account.presenter;

import android.text.TextUtils;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.LogUtil;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.account.contract.ResetPwdContract;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.RegexUtils;
import com.zt.yavon.utils.SPUtil;

/**
 * Created by lifujun on 2018/7/25.
 */

public class ResetPwdPresenter extends ResetPwdContract.Presenter{
    @Override
    public void sendCode(String account) {
        mRxManage.add(Api.sendCode(account,"RESET_PASSWORD", SPUtil.getToken(mContext))
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
    public void resetPwd(String mobile, String code, String pwd, String confirmPwd) {
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
        mRxManage.add(Api.resetPwd(mobile,code,pwd,confirmPwd)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        LoginBean bean = new LoginBean();
                        bean.setPwd(pwd);
                        mView.loginRegisterSuccess(bean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

}
