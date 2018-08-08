package com.zt.yavon.network;

import com.common.base.rx.BaseResponse;
import com.common.base.rx.RxSchedulers;
import com.zt.yavon.BuildConfig;
import com.zt.yavon.module.data.CatogrieBean;
import com.zt.yavon.module.data.DeskBean;
import com.zt.yavon.module.data.DevDetailBean;
import com.zt.yavon.module.data.DevTypeBean;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.module.data.MineRoomBean;
import com.zt.yavon.module.data.MsgBean;
import com.zt.yavon.module.data.ShareListBean;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.main.adddevice.model.AddDeviceBean;
import com.zt.yavon.module.main.roommanager.add.model.RoomItemBean;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class Api {
//    public static final String HOST = "https://s1.zetadata.com.cn/";//测试
//    public static final String HOST_H5 = "https://s1.zetadata.com.cn/";//测试
    public static final String HOST = "http://t27.zetadata.com.cn/";//正式环境
    public static final String HOST_H5 = "https://ecseal.cn/";//正式环境
//    private static Retrofit mRetrofit;
    private static ApiService mAPI;
//    private static Gson mGson;

//    public static ApiService getInstance1() {
//        if (mAPI != null) {
//            return mAPI;
//        }
//
//        String url = "";
//        mRetrofit = new Retrofit.Builder().baseUrl(url).build();
//        mAPI = mRetrofit.create(ApiService.class);
//        return mAPI;
//    }


    public static ApiService getRxApi() {
        if (mAPI != null) {
            return mAPI;
        }
//        if(mGson == null)
//        mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 用于添加默认请求头的interceptor
        Interceptor defaultHeaderInterceptor = new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                // 添加默认请求头
//                String appVersion = DeviceInfoUtil.getVersionName(PuApp.getInstance());//app版本
//                String clientDevBrand = DeviceInfoUtil.getCarrier();//手机品牌
//                String clientDevType = DeviceInfoUtil.getModel();//手机机型
//                String clientOSType = "android";//系统类型
//                String clientOSVersion = DeviceInfoUtil.getSystemVersion();//系统版本
//                String clientLong = "0";// 经度 todo
//                String clientLat = "0";//纬度 todo
                //String ip = DeviceInfoUtil.getNetIp();//ip地址

                Request.Builder requestBuilder = original.newBuilder()
//                        .addHeader("pu-version", appVersion)
//                        .addHeader("client-dev-brand", clientDevBrand)
//                        .addHeader("client-dev-type", clientDevType)
//                        .addHeader("client-os-type", clientOSType)
//                        .addHeader("client-os-version", clientOSVersion)
//                        .addHeader("client-long", clientLong)
                        .addHeader("terminal", "android");

                Request request = requestBuilder.build();
//                LogUtil.d("request headers:" + request.headers().toString());
                return chain.proceed(request);
            }
        };
        builder.addInterceptor(defaultHeaderInterceptor);
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
    public static Observable<BaseResponse> sendCode(String account,String type,String api_token) {
        return getRxApi().sendCode(account, type,api_token).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<LoginBean> register(String mobile,String code,String password,String confirmPwd) {
        return getRxApi().register(mobile, code,password,confirmPwd).compose(RxSchedulers.<LoginBean>handleResult());
    }
    public static Observable<BaseResponse> resetPwd(String account,String code,String password,String confirmPwd) {
        return getRxApi().resetPwd(account, code,password,confirmPwd).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<LoginBean> login(String account,String pwd) {
        return getRxApi().login(account,pwd).compose(RxSchedulers.<LoginBean>handleResult());
    }
    public static Observable<LoginBean> personalInfo(String token) {
        return getRxApi().personalInfo(token).compose(RxSchedulers.<LoginBean>handleResult());
    }
    public static Observable<LoginBean> bindEmail(String token,String email) {
        return getRxApi().bindEmail(token,email).compose(RxSchedulers.<LoginBean>handleResult());
    }
    public static Observable<LoginBean> modifyEmail(String token,String account,String code,String email,String emailConfirm) {
        return getRxApi().modifyEmail(token,account,code,email,emailConfirm).compose(RxSchedulers.<LoginBean>handleResult());
    }
    public static Observable<LoginBean> modifyPhone(String token,String account,String code,String mobile,String mobileConfirm) {
        return getRxApi().modifyPhone(token,account,code,mobile,mobileConfirm).compose(RxSchedulers.<LoginBean>handleResult());
    }
    public static Observable<LoginBean> modifyNickname(String token,String nickname) {
        return getRxApi().modifyNickname(token,nickname).compose(RxSchedulers.<LoginBean>handleResult());
    }
    public static Observable<LoginBean> sysSetting(String token,String msgSwitch,String updateSwitch) {
        return getRxApi().sysSetting(token,msgSwitch,updateSwitch).compose(RxSchedulers.<LoginBean>handleResult());
    }
    public static Observable<List<MsgBean>> getInternalMsgList(String token, String page, String per_page) {
        return getRxApi().getInternalMsgList(token, page, per_page).compose(RxSchedulers.<List<MsgBean>>handleResult());
    }
    public static Observable<List<MsgBean>> getSysMsgList(String token, String page, String per_page) {
        return getRxApi().getSysMsgList(token,page,per_page).compose(RxSchedulers.<List<MsgBean>>handleResult());
    }
    public static Observable<List<MsgBean>> getFaultsMsgList(String token, String page, String per_page) {
        return getRxApi().getFaultsMsgList(token,page,per_page).compose(RxSchedulers.<List<MsgBean>>handleResult());
    }
    public static Observable<List<MsgBean>> getShareMsgList(String token, String page, String per_page) {
        return getRxApi().getShareMsgList(token,page,per_page).compose(RxSchedulers.<List<MsgBean>>handleResult());
    }
    public static Observable<List<MsgBean>> getNotifications(String token) {
        return getRxApi().getNotifications(token).compose(RxSchedulers.<List<MsgBean>>handleResult());
    }
    public static Observable<BaseResponse> setAvatar(String token,String base64String) {
        return getRxApi().setAvatar(token,base64String).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<BaseResponse> deleteInternalMsg(String token,String ids) {
        return getRxApi().deleteInternalMsg(token,ids).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<BaseResponse> deleteSystemMsg(String token,String ids) {
        return getRxApi().deleteSystemMsg(token,ids).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<BaseResponse> deleteFaultMsg(String token,String ids) {
        return getRxApi().deleteFaultMsg(token,ids).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<BaseResponse> readInternalMsg(String token,String ids) {
        return getRxApi().readInternalMsg(token,ids).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<BaseResponse> readSystemMsg(String token,String ids) {
        return getRxApi().readSystemMsg(token,ids).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<BaseResponse> doFaultMsg(String id,String token,String status) {
        return getRxApi().doFaultMsg(id,token,status).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<BaseResponse> doShareMsg(String id,String token,String status) {
        return getRxApi().doShareMsg(id,token,status).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<List<MineRoomBean>> getAllDevs(String token) {
        return getRxApi().getAllDevs(token).compose(RxSchedulers.<List<MineRoomBean>>handleResult());
    }
    public static Observable<ShareListBean> getShareList(String token,String machineId) {
        return getRxApi().getShareList(machineId,token).compose(RxSchedulers.<ShareListBean>handleResult());
    }
    public static Observable<BaseResponse> shareDev(RequestBody expire_value) {
        return getRxApi().shareDev(expire_value).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<BaseResponse> shareAuthor(String token,String machineId,String mobile,String start,String end) {
        return getRxApi().shareAuthor(token,machineId,mobile,start,end).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<BaseResponse> cancleDevShare(String token,String machineId,String userId) {
        return getRxApi().cancleDevShare(machineId,userId,token).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<BaseResponse> readFaultMsg(String token,String ids) {
        return getRxApi().readFaultMsg(token,ids).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<BaseResponse> readShareMsg(String token,String ids) {
        return getRxApi().readShareMsg(token,ids).compose(RxSchedulers.<BaseResponse>io_main());
    }

    public static Observable<List<TabBean>> getTabData(String token) {
        return getRxApi().getTabData(token).compose(RxSchedulers.handleResult());
    }

    public static Observable<List<AddDeviceBean>> getAddDeviceData(String token) {
        return getRxApi().getAddDeviceData(token).compose(RxSchedulers.handleResult());
    }
    public static Observable<List<CatogrieBean>> getCatogries(String token,String type) {
        return getRxApi().getCatogries(token,type).compose(RxSchedulers.<List<CatogrieBean>>handleResult());
    }
    public static Observable<BaseResponse> bindDev(String token,String name,String asset_number,String mac,String sn,String category_id,String room_id,String type,String lockId,String password) {
        return getRxApi().bindDev(token,name,asset_number,mac,sn,category_id,room_id,type,lockId,password).compose(RxSchedulers.<BaseResponse>io_main());
    }

    public static Observable<DevDetailBean> getDevDetail(String id, String token) {
        return getRxApi().getDevDetail(id,token).compose(RxSchedulers.<DevDetailBean>handleResult());
    }
    public static Observable<BaseResponse> switchDev(String machine_id, String token,String status) {
        return getRxApi().switchDev(machine_id,token,status).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<List<DevTypeBean>> getMachineTypes(String token) {
        return getRxApi().getMachineTypes(token).compose(RxSchedulers.<List<DevTypeBean>>handleResult());
    }
    public static Observable<BaseResponse> applyDev(RequestBody body) {
        return getRxApi().applyDev(body).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<DeskBean> getDefaultHeiht(String machine_id,String token) {
        return getRxApi().getDefaultHeiht(machine_id,token).compose(RxSchedulers.<DeskBean>handleResult());
    }
    public static Observable<BaseResponse> startDeskMove(String machine_id,String token,String direction) {
        return getRxApi().startDeskMove(machine_id,token,direction).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<DeskBean> stopDeskMove(String machine_id,String token) {
        return getRxApi().stopDeskMove(machine_id,token).compose(RxSchedulers.<DeskBean>handleResult());
    }
    public static Observable<BaseResponse> setDeskHeight(String machine_id,String token,String height) {
        return getRxApi().setDeskHeight(machine_id,token,height).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<DevDetailBean> setDeskCustomHeightTag(String machine_id,RequestBody body) {
        return getRxApi().setDeskCustomHeightTag(machine_id,body).compose(RxSchedulers.<DevDetailBean>handleResult());
    }
    public static Observable<DevDetailBean> setSeatTime(String token,String machine_id,String hour) {
        return getRxApi().setSeatTime(token,machine_id,hour).compose(RxSchedulers.<DevDetailBean>handleResult());
    }
    public static Observable<DevDetailBean> setDeskRemindSwitch(String machine_id,String token,String isOn) {
        return getRxApi().setDeskRemindSwitch(machine_id,token,isOn).compose(RxSchedulers.<DevDetailBean>handleResult());
    }
    public static Observable<BaseResponse> deleteDevice(String token,String ids) {
        return getRxApi().deleteDevice(token,ids).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<BaseResponse> setOften(String token,String ids) {
        return getRxApi().setOften(token,ids).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<BaseResponse> moveDev(String token,String ids,String roomId) {
        return getRxApi().moveDev(token,ids,roomId).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<BaseResponse> uploadFault(String token,String ids,String content) {
        return getRxApi().uploadFault(token,ids,content).compose(RxSchedulers.<BaseResponse>io_main());
    }
    public static Observable<List<TabBean>> getRoomList(String token,String from) {
        return getRxApi().getRoomList(token,from).compose(RxSchedulers.<List<TabBean>>handleResult());
    }
    public static Observable<DevDetailBean> renameDev(String token,String id,String name) {
        return getRxApi().renameDev(token, id, name).compose(RxSchedulers.<DevDetailBean>handleResult());
    }

    public static Observable<BaseResponse> setAddDeviceData(String token, String selectMachineIds) {
        return getRxApi().setAddDeviceData(token, selectMachineIds).compose(RxSchedulers.io_main());
    }

    public static Observable<List<RoomItemBean>> getAllRoomData(String token) {
        return getRxApi().getAllRoomData(token).compose(RxSchedulers.handleResult());
    }

    public static Observable<RoomItemBean> addRoom(String token, String roomName, int roomResId) {
        return getRxApi().addRoom(token, roomName, roomResId).compose(RxSchedulers.handleResult());
    }
}