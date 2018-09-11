package com.zt.yavon.module.main.frame.presenter;

import com.common.base.utils.LogUtil;
import com.tuya.smart.android.user.api.ILoginCallback;
import com.tuya.smart.android.user.bean.User;
import com.tuya.smart.sdk.TuyaUser;
import com.zt.yavon.module.data.CityLocation;
import com.zt.yavon.module.data.CountBean;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.data.VersionBean;
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

    public void loginTuYa(){
//        TuyaUser.getUserInstance().loginWithPhonePassword("86",  "18106223213", "123456", new ILoginCallback() {
//        TuyaUser.getUserInstance().loginWithUid("86",  bean.getMobile(), bean.getPwd(), new ILoginCallback() {
        TuyaUser.getUserInstance().loginWithUid("86",  "15556092750", "1111111", new ILoginCallback() {
            @Override
            public void onSuccess(User user) {
                LogUtil.d("==================loginTuYa success");
            }

            @Override
            public void onError(String code, String error) {
                LogUtil.d("==================loginTuYa error,code:"+code+",error:"+error);
            }
        });
    }
    @Override
    public void getTabData(boolean showLoading) {
        mRxManage.add(Api.getTabData(SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<List<TabBean>>(mContext, showLoading) {
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
                .subscribeWith(new RxSubscriber<CountBean>(mContext, false) {
                    @Override
                    protected void _onNext(CountBean bean) {
                        mView.unreadMsgCount(bean.count);
                    }

                    @Override
                    protected void _onError(String message) {
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
                    }
                }).getDisposable());
    }

    @Override
    public void getVersion() {
        mRxManage.add(Api.getVersion(SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<VersionBean>(mContext, false) {
                    @Override
                    protected void _onNext(VersionBean bean) {
//                        bean.version = "0.0.9";
//                        bean.is_force = false;
//                        bean.url = "http://app.wowzhua.com/app.apk";
                        mView.returnVersion(bean);
                    }

                    @Override
                    protected void _onError(String message) {
                    }
                }).getDisposable());
    }

    public void getWeather(String city) {
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
                    }
                }).getDisposable());
    }
}
