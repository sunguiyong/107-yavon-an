package com.zt.yavon.module.main.frame.presenter;

import com.common.base.rx.BaseResponse;
import com.zt.yavon.module.data.CityLocation;
import com.zt.yavon.module.data.CountBean;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.data.WeatherBean;
import com.zt.yavon.module.main.frame.contract.HomeContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class HomePresenter extends HomeContract.Presenter {


    @Override
    public void getTabData() {
        mRxManage.add(Api.getTabData(SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<List<TabBean>>(mContext, true) {
                    @Override
                    protected void _onNext(List<TabBean> response) {
                        mView.returnTabData(response);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.returnTabData(null);
                        mView.errorTabData(message);
                    }
                }).getDisposable());
    }

    @Override
    public void getInternalMsgUnreadCount() {
        mRxManage.add(Api.getInternalMsgUnreadCount(SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<CountBean>(mContext, true) {
                    @Override
                    protected void _onNext(CountBean bean) {
                        mView.unreadMsgCount(bean.count);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.errorTabData(message);
                    }
                }).getDisposable());
    }

    @Override
    public void getCity(String location) {
        mRxManage.add(Api.getCity(location)
                .subscribeWith(new RxSubscriber<CityLocation>(mContext, false) {
                    @Override
                    protected void _onNext(CityLocation bean) {
                        if("OK".equals(bean.status)){
                            getWeather(bean.result.addressComponent.city);
                        }
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.errorTabData(message);
                    }
                }).getDisposable());
    }
    private void getWeather(String city) {
        mRxManage.add(Api.getWeather(city)
                .subscribeWith(new RxSubscriber<WeatherBean>(mContext, false) {
                    @Override
                    protected void _onNext(WeatherBean bean) {
                        if(bean != null && "10000".equals(bean.code)){
                            mView.updateWeather(bean);
                        }
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.errorTabData(message);
                    }
                }).getDisposable());
    }
}
