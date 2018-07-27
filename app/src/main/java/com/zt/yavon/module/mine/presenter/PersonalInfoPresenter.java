package com.zt.yavon.module.mine.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.module.mine.contract.PersonalInfoContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.ImageUtils;
import com.zt.yavon.utils.SPUtil;
import com.zt.yavon.utils.UriToPathUtil;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by hp on 2018/6/13.
 */

public class PersonalInfoPresenter extends PersonalInfoContract.Presenter {

    @Override
    public void getPersonalInfo() {
        mRxManage.add(Api.personalInfo(SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<LoginBean>(mContext,true) {
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

    @Override
    public void modifyNickname(String nickName) {
        mRxManage.add(Api.modifyNickname(SPUtil.getToken(mContext),nickName)
                .subscribeWith(new RxSubscriber<LoginBean>(mContext,true) {
                    @Override
                    protected void _onNext(LoginBean bean) {
                        mView.modifyNickNameSuccess(nickName);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void setAvatar(String filepath) {
        Bitmap bitmap = null;
        try {
//            bitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(UriToPathUtil.getUri(mContext,cacheFile)));
            mRxManage.add(Api.setAvatar(SPUtil.getToken(mContext), "data:image/png;base64,"+ImageUtils.compressAndResizeByMatrix(filepath,3*1024*1024,1080,1920))
                    .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                        @Override
                        protected void _onNext(BaseResponse bean) {
                            mView.uploadSuccess(filepath);
                        }
                        @Override
                        protected void _onError(String message) {
                            ToastUtil.showShort(mContext,message);
                        }
                    }).getDisposable());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
