package com.zt.yavon.network;

import com.common.base.rx.BaseResponse;
import com.zt.yavon.module.data.DemoBean;
import com.zt.yavon.module.data.LoginBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    /**
     * 发送验证码
     * @param account 手机或者邮箱
     * @param type 类型：REGISTER 注册， RESET_PASSWORD 重置密码， MODIFY_BIND_MOBILE 修改绑定手机号，MODIFY_BIND_EMAIL 修改绑定邮箱
     * @param api_token 如果用户处于登录状态，需要传过来
     * @return
     */
    @GET(Api.HOST+"api/send_code")
    Observable<BaseResponse> sendCode(
            @Query("account") String account,
            @Query("type") String type,
            @Query("api_token") String api_token
    );

    /**
     * 用户注册接口
     * @param mobile
     * @param code
     * @param password
     * @param password_confirmation
     * @return
     */
    @POST(Api.HOST+"api/auth/register")
    @FormUrlEncoded
    Observable<BaseResponse<LoginBean>> register(
            @Field("mobile") String mobile,
            @Field("code") String code,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation
    );
    /**
     * 用户登录
     * @param account
     * @param password
     * @return
     */
    @POST(Api.HOST+"api/auth/login")
    @FormUrlEncoded
    Observable<BaseResponse<LoginBean>> login(
            @Field("account") String account,
            @Field("password") String password
    );
    /**
     * 重置密码
     * @param account
     * @param code
     * @param password
     * @param password_confirmation
     * @return
     */
    @POST(Api.HOST+"api/auth/reset_password")
    @FormUrlEncoded
    Observable<BaseResponse<LoginBean>> resetPwd(
            @Field("account") String account,
            @Field("code") String code,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation
    );


}
