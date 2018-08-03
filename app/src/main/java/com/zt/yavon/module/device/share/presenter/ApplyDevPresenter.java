package com.zt.yavon.module.device.share.presenter;

import android.text.TextUtils;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.device.share.contract.ApplyDevContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by hp on 2018/6/13.
 */

public class ApplyDevPresenter extends ApplyDevContract.Presenter {

    @Override
    public void applyDev(String name,String asset_number,String expire_type,String expire_value,String room_id) {
        JSONObject json = new JSONObject();
        try {
            json.put("api_token",SPUtil.getToken(mContext));
            json.put("name",name);
            json.put("asset_number",asset_number);
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
        mRxManage.add(Api.applyDev(requestBody)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.applySuccess();
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
