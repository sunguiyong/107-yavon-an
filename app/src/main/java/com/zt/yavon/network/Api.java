package com.zt.yavon.network;

import com.common.base.rx.BaseResponse;
import com.common.base.rx.RxSchedulers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zt.yavon.BuildConfig;
import com.zt.yavon.module.data.DemoBean;
import com.zt.yavon.utils.Constants;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class Api{
//    public static final String HOST = "https://s1.zetadata.com.cn/";//测试
//    public static final String HOST_H5 = "https://s1.zetadata.com.cn/";//测试
    public static final String HOST = "https://ecseal.cn/";//正式环境
    public static final String HOST_H5 = "https://ecseal.cn/";//正式环境
    private static Retrofit mRetrofit;
    private static Retrofit mGsonRetrofit;
    private static ApiService mAPI;
    private static Gson mGson;

    public static ApiService getInstance1() {
        if (mAPI != null) {
            return mAPI;
        }

        String url = "";
        mRetrofit = new Retrofit.Builder().baseUrl(url).build();
        mAPI = mRetrofit.create(ApiService.class);
        return mAPI;
    }


    public static ApiService getRxApi() {
        if (mAPI != null) {
            return mAPI;
        }
        if(mGson == null)
        mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 用于添加默认请求头的interceptor
//        Interceptor defaultHeaderInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Interceptor.Chain chain) throws IOException {
//                Request original = chain.request();
//
//                // 添加默认请求头
//                String appVersion = DeviceInfoUtil.getVersionName(PuApp.getInstance());//app版本
//                String clientDevBrand = DeviceInfoUtil.getCarrier();//手机品牌
//                String clientDevType = DeviceInfoUtil.getModel();//手机机型
//                String clientOSType = "android";//系统类型
//                String clientOSVersion = DeviceInfoUtil.getSystemVersion();//系统版本
//                String clientLong = "0";// 经度 todo
//                String clientLat = "0";//纬度 todo
//                //String ip = DeviceInfoUtil.getNetIp();//ip地址
//
//                Request.Builder requestBuilder = original.newBuilder()
//                        .addHeader("pu-version", appVersion)
//                        .addHeader("client-dev-brand", clientDevBrand)
//                        .addHeader("client-dev-type", clientDevType)
//                        .addHeader("client-os-type", clientOSType)
//                        .addHeader("client-os-version", clientOSVersion)
//                        .addHeader("client-long", clientLong)
//                        .addHeader("client-lat", clientLat);
//
//                Request request = requestBuilder.build();
////                LogUtil.d("request headers:" + request.headers().toString());
//                return chain.proceed(request);
//            }
//        };
//        builder.addInterceptor(defaultHeaderInterceptor);
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别
            builder.addInterceptor(loggingInterceptor);
        }

        OkHttpClient client = builder
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
//                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
//                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
        mAPI = new Retrofit.Builder().baseUrl(HOST).client(client)
//                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addConverterFactory(ResponseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class);
        return mAPI;
    }
    public static Observable<BaseResponse> addDevice(String token,String mac) {
        ApiService api = getRxApi();
        return api.addDevice(token, Constants.OEM,Constants.PHONE_TYPE, mac).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<BaseResponse> deleteDevice(String token,String mac) {
        ApiService api = getRxApi();
        return api.deleteDevice(token, Constants.OEM,Constants.PHONE_TYPE, mac).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<List<DemoBean>> getHomeDevList(String token, String page) {
        ApiService api = getRxApi();
        return api.getHomeDevList(token, Constants.OEM,Constants.PHONE_TYPE, page).compose(RxSchedulers.<List<DemoBean>>handleResult());
    }
    public static Observable<BaseResponse> renameDev(String token, String mac,String newName) {
        ApiService api = getRxApi();
        return api.renameDev(token, Constants.OEM,Constants.PHONE_TYPE, mac,newName).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<BaseResponse> controlDev(String token, String mac,String action,String val) {
        ApiService api = getRxApi();
        return api.controlDev(token, Constants.OEM,Constants.PHONE_TYPE, mac,action,val).compose(RxSchedulers.<BaseResponse>io_main());
    }
}