package com.zt.yavon.module.device.share.presenter;

import android.text.TextUtils;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.device.share.contract.ShareDevContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.RegexUtils;
import com.zt.yavon.utils.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit2.http.Field;

/**
 * Created by hp on 2018/6/13.
 */

public class ShareDevPresenter extends ShareDevContract.Presenter {

    @Override
    public void shareDev(String machineId, String mobile, String expire_type, String expire_value) {
        if(TextUtils.isEmpty(mobile)){
            ToastUtil.showShort(mContext,"手机号不能为空");
            return;
        }
        if(!RegexUtils.isMobile(mobile)){
            ToastUtil.showShort(mContext,"手机号不正确");
            return;
        }
        JSONObject json = new JSONObject();
        try {
            json.put("api_token",SPUtil.getToken(mContext));
            json.put("machine_id",machineId);
            json.put("mobile",mobile);
            json.put("expire_type",expire_type);
            if(!TextUtils.isEmpty(expire_value) && expire_value.contains("start")){
                json.put("expire_value",new JSONObject(expire_value));
            }else{
                json.put("expire_value",expire_value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json.toString());
        mRxManage.add(Api.shareDev(requestBody)
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
