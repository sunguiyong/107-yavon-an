package com.zt.yavon.module.device.share.presenter;

import android.text.TextUtils;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.device.share.contract.AuthorDevContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.RegexUtils;
import com.zt.yavon.utils.SPUtil;

/**
 * Created by hp on 2018/6/13.
 */

public class AuthorDevPresenter extends AuthorDevContract.Presenter {

    @Override
    public void shareAuthor(String machineId, String mobile, String start_at, String end_at, long start, long end) {

        if(TextUtils.isEmpty(mobile)){
            ToastUtil.showShort(mContext,"手机号不能为空");
            return;
        }
        if(!RegexUtils.isMobile(mobile)){
            ToastUtil.showShort(mContext,"手机号不正确");
            return;
        }
        if(TextUtils.isEmpty(start_at)){
            ToastUtil.showShort(mContext,"请选择开始时间");
            return;
        }
        if(TextUtils.isEmpty(end_at)){
            ToastUtil.showShort(mContext,"请选择结束时间");
            return;
        }
        if(start >= end){
            ToastUtil.showShort(mContext,"结束时间必须大于开始时间");
            return;
        }
        mRxManage.add(Api.shareAuthor(SPUtil.getToken(mContext),machineId,mobile,start_at,end_at)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.shareSuccess();
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
