package com.zt.igreen.module.main.frame.presenter;

import android.util.Log;

import com.common.base.rx.RxManager;
import com.common.base.utils.LogUtil;
import com.common.base.utils.ToastUtil;
import com.tuya.smart.android.user.api.ILoginCallback;
import com.tuya.smart.android.user.bean.User;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.home.sdk.bean.HomeBean;
import com.tuya.smart.home.sdk.callback.ITuyaGetHomeListCallback;
import com.tuya.smart.home.sdk.callback.ITuyaHomeResultCallback;
import com.tuya.smart.sdk.TuyaSdk;
import com.zt.igreen.module.data.BindTuyaHome;
import com.zt.igreen.module.data.CityLocation;
import com.zt.igreen.module.data.CountBean;
import com.zt.igreen.module.data.DataSave;
import com.zt.igreen.module.data.HealthInfoBean;
import com.zt.igreen.module.data.LoginBean;
import com.zt.igreen.module.data.TabBean;
import com.zt.igreen.module.data.VersionBean;
import com.zt.igreen.module.data.WeatherBean;
import com.zt.igreen.module.main.frame.contract.HomeContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class HomePresenter extends HomeContract.Presenter {

    /**
     * 涂鸦登录
     *
     * @param userName
     */
    public void loginTuYa(String userName) {
        TuyaHomeSdk.getUserInstance().loginOrRegisterWithUid("86", "tuyayavon" + userName, "tuyayavon123456", new ILoginCallback() {
            @Override
            public void onSuccess(User user) {
                Log.d("loginOrRegisterWithUidX", user.getUid() + "");
                checkTuyaHome(SPUtil.getAccount(mContext).getMobile() + "");
            }

            @Override
            public void onError(String code, String error) {
                Log.d("loginOrRegisterWithUid", code + "---" + error);
            }
        });
    }

    private List<String> roomLists = new ArrayList<>();
    long tuyahomeid;

    private void checkTuyaHome(String mobile) {
        roomLists.add(0, "room");
        TuyaHomeSdk.getHomeManagerInstance().queryHomeList(new ITuyaGetHomeListCallback() {
            @Override
            public void onSuccess(List<HomeBean> homeBeans) {
                if (homeBeans.size() > 0) {//有家庭
                    Log.d("queryHomeList", homeBeans.get(0).getHomeId() + "");
                    Log.d("queryHomeListName", homeBeans.get(0).getName() + "");
                    tuyahomeid = homeBeans.get(0).getHomeId();
                    DataSave.tuyaHomeId = tuyahomeid + "";
                } else {//无家庭，创建家庭
                    TuyaHomeSdk.getHomeManagerInstance().createHome("homeHasPrefix" + mobile,
                            0, 0, "苏州", roomLists, new ITuyaHomeResultCallback() {
                                @Override
                                public void onSuccess(HomeBean bean) {
                                    Log.d("createHome", bean.getHomeId() + "");
                                    tuyahomeid = bean.getHomeId();
                                    //todo 调用上传homeid的接口
                                    uploadHomeId(tuyahomeid + "");
                                }

                                @Override
                                public void onError(String errorCode, String errorMsg) {
                                    Log.d("createHome", errorCode + "---" + errorMsg);
                                }
                            });
                }
            }

            @Override
            public void onError(String errorCode, String error) {
                Log.i("queryHomeList", "onError: " + errorCode + "---" + error);
            }
        });
    }

    /**
     * 涂鸦homeid上传到接口服务器保存
     *
     * @param ty_family_id
     */
    public void uploadHomeId(String ty_family_id) {
        mRxManage.add(Api.bindTuyaHome(SPUtil.getToken(mContext), ty_family_id)
                .subscribeWith(new RxSubscriber<BindTuyaHome>(mContext, false) {
                    @Override
                    protected void _onNext(BindTuyaHome bean) {
                        Log.d("bindTuyaHome", bean.toString());
                    }

                    @Override
                    protected void _onError(String message) {
                        Log.d("bindTuyaHome_onError", message);
                    }
                }).getDisposable());
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
                        if ("OK".equals(bean.status)) {
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
                        if (bean != null && "10000".equals(bean.code)) {
                            mView.updateWeather(bean);
                        }
                    }

                    @Override
                    protected void _onError(String message) {
                    }
                }).getDisposable());
    }

}
