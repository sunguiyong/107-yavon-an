package com.zt.yavon.network;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lifujun on 2018/8/1.
 */

public interface ApiService2 {

    //  api文档：   https://api.yeeloc.com/open-apidoc/#api-oauth-GetAccessToken

    /**
     * 获取token
     *
     * @return
     */
    @POST("open-api/oauth/access_token")
    @FormUrlEncoded
    Observable<YSBResponse> getToken(
            @Field("grant_type") String grant_type,
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("username") String username,
            @Field("password") String password
    );
    /**
     * 扫码添加锁
     *
     * @return
     */
    @POST("open-api/qrcode/data")
    @FormUrlEncoded
    Observable<YSBResponse> addScanLock(
            @Header("Authorization") String authorization,
            @Field("qrcode_data") String qrcode_data
    );
    /**
     * 根据二维码获取SN和KEY
     * @return
     */
    @POST("open-api/qrcode/sn_key")
    @FormUrlEncoded
    Observable<YSBResponse> getLockSN(
            @Header("Authorization") String authorization,
            @Field("qrcode_data") String qrcode_data
    );
    /**
     * 获取蓝牙锁管理员密码
     * @return
     */
    @GET("open-api/ble/admin/password")
    Observable<YSBResponse> getLockPwd(
            @Header("Authorization") String authorization,
            @Query("sn") String sn
    );
}
