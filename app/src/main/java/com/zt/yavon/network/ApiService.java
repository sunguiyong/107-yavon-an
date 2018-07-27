package com.zt.yavon.network;

import com.common.base.rx.BaseResponse;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.module.data.MsgBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
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
    @GET("api/send_code")
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
    @POST("api/auth/register")
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
    @POST("api/auth/login")
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
    @PATCH("api/auth/reset_password")
    @FormUrlEncoded
    Observable<BaseResponse> resetPwd(
            @Field("account") String account,
            @Field("code") String code,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation
    );
    /**
     * 用户详情接口
     * @param api_token
     * @return
     */
    @GET("api/user/detail")
    Observable<BaseResponse<LoginBean>> personalInfo(
            @Query("api_token") String api_token
    );
    /**
     * 设置关联邮箱
     * @param api_token
     * @param email
     * @return
     */
    @POST("api/user/bind_email")
    @FormUrlEncoded
    Observable<BaseResponse<LoginBean>> bindEmail(
            @Field("api_token") String api_token,
            @Field("email") String email
    );
    /**
     * 修改绑定邮箱
     * @param api_token
     * @param account
     * @param code
     * @param new_email
     * @param new_email_confirmation
     * @return
     */
    @PATCH("api/user/email")
    @FormUrlEncoded
    Observable<BaseResponse<LoginBean>> modifyEmail(
            @Field("api_token") String api_token,
            @Field("account") String account,
            @Field("code") String code,
            @Field("new_email") String new_email,
            @Field("new_email_confirmation") String new_email_confirmation
    );
    /**
     * 修改绑定手机
     * @param api_token
     * @param account
     * @param code
     * @param mobile
     * @param mobile_confirmation
     * @return
     */
    @PATCH("api/user/mobile")
    @FormUrlEncoded
    Observable<BaseResponse<LoginBean>> modifyPhone(
            @Field("api_token") String api_token,
            @Field("account") String account,
            @Field("code") String code,
            @Field("mobile") String mobile,
            @Field("mobile_confirmation") String mobile_confirmation
    );
    /**
     * 用户修改昵称
     * @param api_token
     * @param nick_name
     * @return
     */
    @PATCH("api/user/nick_name")
    @FormUrlEncoded
    Observable<BaseResponse<LoginBean>> modifyNickname(
            @Field("api_token") String api_token,
            @Field("nick_name") String nick_name
    );
    /**
     * 设置自动更新、是否接收系统消息
     * @param api_token
     * @param open_system_message
     * @param open_auto_update
     * @return
     */
    @PATCH("api/user/setting")
    @FormUrlEncoded
    Observable<BaseResponse<LoginBean>> sysSetting(
            @Field("api_token") String api_token,
            @Field("open_system_message") String open_system_message,
            @Field("open_auto_update") String open_auto_update
    );
    /**
     * 上传头像
     * @param api_token
     * @param avatar
     * @return
     */
    @POST("api/user/avatar")
    @FormUrlEncoded
    Observable<BaseResponse> setAvatar(
            @Field("api_token") String api_token,
            @Field("avatar") String avatar
    );
    /**
     * 内部消息列表
     * @param api_token
     * @param page
     * @param per_page
     * @return
     */
    @GET("api/notifications/insides")
    Observable<BaseResponse<List<MsgBean>>> getInternalMsgList(
            @Query("api_token") String api_token,
            @Query("page") String page,
            @Query("per_page") String per_page
    );
    /**
     * 系统消息列表
     * @param api_token
     * @param page
     * @param per_page
     * @return
     */
    @GET("api/notifications/systems")
    Observable<BaseResponse<List<MsgBean>>> getSysMsgList(
            @Query("api_token") String api_token,
            @Query("page") String page,
            @Query("per_page") String per_page
    );
    /**
     * 故障消息列表
     * @param api_token
     * @param page
     * @param per_page
     * @return
     */
    @GET("api/notifications/faults")
    Observable<BaseResponse<List<MsgBean>>> getFaultsMsgList(
            @Query("api_token") String api_token,
            @Query("page") String page,
            @Query("per_page") String per_page
    );
    /**
     * 消息列表（系统、报障、共享）
     * @param api_token
     * @return
     */
    @GET("api/notifications")
    Observable<BaseResponse<List<MsgBean>>> getNotifications(
            @Query("api_token") String api_token
    );
    /**
     * 内部消息删除（批量）
     * @param api_token
     * @param ids
     * @return
     */
    @HTTP(method = "DELETE",path = "api/notifications/insides/delete", hasBody = true)
    @FormUrlEncoded
    Observable<BaseResponse> deleteInternalMsg(
            @Field("api_token") String api_token,
            @Field("ids") String ids
    );
    /**
     * 系统消息删除（批量）
     * @param api_token
     * @param ids
     * @return
     */
    @HTTP(method = "DELETE",path = "api/notifications/systems/delete", hasBody = true)
    @FormUrlEncoded
    Observable<BaseResponse> deleteSystemMsg(
            @Field("api_token") String api_token,
            @Field("ids") String ids
    );
    /**
     * 故障消息删除（批量）
     * @param api_token
     * @param ids
     * @return
     */
    @HTTP(method = "DELETE",path = "api/notifications/faults/delete", hasBody = true)
    @FormUrlEncoded
    Observable<BaseResponse> deleteFaultMsg(
            @Field("api_token") String api_token,
            @Field("ids") String ids
    );
    /**
     * 内部消息标为已读
     * @param api_token
     * @param ids
     * @return
     */
    @PATCH("api/notifications/insides/read")
    @FormUrlEncoded
    Observable<BaseResponse> readInternalMsg(
            @Field("api_token") String api_token,
            @Field("ids") String ids
    );
    /**
     * 系统消息标为已读
     * @param api_token
     * @param ids
     * @return
     */
    @PATCH("api/notifications/systems/read")
    @FormUrlEncoded
    Observable<BaseResponse> readSystemMsg(
            @Field("api_token") String api_token,
            @Field("ids") String ids
    );
    /**
     * 故障消息标为已读
     * @param api_token
     * @param ids
     * @return
     */
    @PATCH("api/notifications/faults/read")
    @FormUrlEncoded
    Observable<BaseResponse> readFaultMsg(
            @Field("api_token") String api_token,
            @Field("ids") String ids
    );
}
