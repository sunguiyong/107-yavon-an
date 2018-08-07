package com.zt.yavon.network;

import com.common.base.rx.RxSchedulers;
import com.zt.yavon.BuildConfig;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class Api2 {
//    public static final String HOST = "https://s1.zetadata.com.cn/";//测试
//    public static final String HOST_H5 = "https://s1.zetadata.com.cn/";//测试
    public static final String HOST_YSB = "https://api.yeeloc.com/";//易琐宝
    public static final String CLIENT_ID = "QQ0UodVkEWjX";//易琐宝
    public static final String CLIENT_SECRET = "wnZHCofHKtTUg3B0SQSuFiXRKqxatJHti4gMsOsA";//易琐宝
    public static final String USERNAME = "18013283596";//易琐宝
    public static final String PWD = "luonan1234567890";//易琐宝
    private static ApiService2 mAPI;



    public static ApiService2 getRxApi() {
        if (mAPI != null) {
            return mAPI;
        }
//        if(mGson == null)
//        mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
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
        mAPI = new Retrofit.Builder().baseUrl(HOST_YSB).client(client)
//                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addConverterFactory(ResponseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService2.class);
        return mAPI;
    }
    public static Observable<YSBResponse> getToken() {
        return getRxApi().getToken("password",CLIENT_ID,CLIENT_SECRET,USERNAME,PWD).compose(RxSchedulers.<YSBResponse>io_main());
    }
    public static Observable<YSBResponse> addScanLock(String author, String scanString) {
        return getRxApi().addScanLock(author,scanString).compose(RxSchedulers.<YSBResponse>io_main());
    }
    public static Observable<YSBResponse> getLockSN(String author, String scanString) {
        return getRxApi().getLockSN(author,scanString).compose(RxSchedulers.<YSBResponse>io_main());
    }
    public static Observable<YSBResponse> getLockPwd(String author, String sn) {
        return getRxApi().getLockPwd(author,sn).compose(RxSchedulers.<YSBResponse>io_main());
    }
    public static Observable<YSBResponse> deleteLock(String author, String lock_id) {
        return getRxApi().deleteLock(author,lock_id).compose(RxSchedulers.<YSBResponse>io_main());
    }

}