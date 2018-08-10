package com.zt.yavon.module.device.desk.presenter;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.DeskBean;
import com.zt.yavon.module.data.DevDetailBean;
import com.zt.yavon.module.device.desk.contract.DeskDetailContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by hp on 2018/6/13.
 */

public class DeskDetailPresenter extends DeskDetailContract.Presenter {

    @Override
    public void getDevDetail(String machine_id) {
        mRxManage.add(Api.getDevDetail(machine_id,SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<DevDetailBean>(mContext,true) {
                    @Override
                    protected void _onNext(DevDetailBean bean) {
                        mView.returnDevDetail(bean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void getDefaultHeight(String machine_id) {
        mRxManage.add(Api.getDefaultHeiht(machine_id,SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<DeskBean>(mContext,true) {
                    @Override
                    protected void _onNext(DeskBean bean) {
                        mView.returnHeiht(bean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void startDeskMove(String machine_id, boolean isUP) {
        mRxManage.add(Api.startDeskMove(machine_id,SPUtil.getToken(mContext),isUP?"UP":"DOWN")
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext,false) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void stopDeskMove(String machine_id) {
        mRxManage.add(Api.stopDeskMove(machine_id,SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<DeskBean>(mContext,true) {
                    @Override
                    protected void _onNext(DeskBean bean) {
                        mView.returnHeiht(bean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void setDeskHeight(String machine_id, String height) {
        mRxManage.add(Api.setDeskHeight(machine_id,SPUtil.getToken(mContext),height)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                    @Override
                    protected void _onNext(BaseResponse bean) {

                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }


    @Override
    public void setDeskCustomHeightTag(String machine_id, List<DeskBean> height) {
        JSONArray array = new JSONArray();
        try {
            for(DeskBean bean:height){
                JSONObject json = new JSONObject();
                json.put("name",bean.name);
                json.put("height",bean.height);
                array.put(json);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        JSONObject json = new JSONObject();
        try {
            json.put("api_token",SPUtil.getToken(mContext));
            json.put("customs",array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json.toString());
        mRxManage.add(Api.setDeskCustomHeightTag(machine_id,requestBody)
                .subscribeWith(new RxSubscriber<DevDetailBean>(mContext,true) {
                    @Override
                    protected void _onNext(DevDetailBean bean) {
                        mView.returnDevDetail(bean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }


}
