package com.zt.yavon.network;

import com.common.base.rx.BaseResponse;
import com.zt.yavon.module.data.DemoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * des:ApiService
 */
public interface ApiService {
//    /**
//     * example
//     *
//     * @param token
//     * @return
//     */
//    @POST(Api.BASE_URL_USER)
//    @FormUrlEncoded
//    Observable<BaseResponse> setPrivacySetting(
//            @Header("x-access-token") String token,
//            @Field("attendedActiveValue") String attendedActiveValue,
//    );
    @POST(Api.HOST+"i/ecseal/dev/my_add")
    @FormUrlEncoded
    Observable<BaseResponse> addDevice(
            @Field("api_token") String token,
            @Field("oem") String oem,
            @Field("phone_type") String phone_type,
            @Field("mac") String mac
    );
    @POST(Api.HOST+"i/ecseal/dev/index_list")
    @FormUrlEncoded
    Observable<BaseResponse<List<DemoBean>>> getHomeDevList(
            @Field("api_token") String token,
            @Field("oem") String oem,
            @Field("phone_type") String phone_type,
            @Field("page") String page
    );
    @POST(Api.HOST+"i/ecseal/dev/edit")
    @FormUrlEncoded
    Observable<BaseResponse> renameDev(
            @Field("api_token") String token,
            @Field("oem") String oem,
            @Field("phone_type") String phone_type,
            @Field("mac") String mac,
            @Field("rename") String rename
    );
    @POST(Api.HOST+"i/ecseal/dev/set")
    @FormUrlEncoded
    Observable<BaseResponse> controlDev(
            @Field("api_token") String token,
            @Field("oem") String oem,
            @Field("phone_type") String phone_type,
            @Field("mac") String mac,
            @Field("action") String action,
            @Field("val") String val
    );
    @POST(Api.HOST+"i/ecseal/dev/my_del")
    @FormUrlEncoded
    Observable<BaseResponse> deleteDevice(
            @Field("api_token") String token,
            @Field("oem") String oem,
            @Field("phone_type") String phone_type,
            @Field("mac") String mac
    );


}
