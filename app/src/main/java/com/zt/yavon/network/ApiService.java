package com.zt.yavon.network;

import com.common.base.rx.BaseResponse;
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
import com.zt.yavon.module.main.roommanager.detail.model.RoomDetailBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
     *
     * @param account   手机或者邮箱
     * @param type      类型：REGISTER 注册， RESET_PASSWORD 重置密码， MODIFY_BIND_MOBILE 修改绑定手机号，MODIFY_BIND_EMAIL 修改绑定邮箱
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
     *
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
     *
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
     *
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
     *
     * @param api_token
     * @return
     */
    @GET("api/user/detail")
    Observable<BaseResponse<LoginBean>> personalInfo(
            @Query("api_token") String api_token
    );

    /**
     * 设置关联邮箱
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     * 共享信息列表
     *
     * @param api_token
     * @param page
     * @param per_page
     * @return
     */
    @GET("api/notifications/shares")
    Observable<BaseResponse<List<MsgBean>>> getShareMsgList(
            @Query("api_token") String api_token,
            @Query("page") String page,
            @Query("per_page") String per_page
    );

    /**
     * 消息列表（系统、报障、共享）
     *
     * @param api_token
     * @return
     */
    @GET("api/notifications")
    Observable<BaseResponse<List<MsgBean>>> getNotifications(
            @Query("api_token") String api_token
    );

    /**
     * 内部消息删除（批量）
     *
     * @param api_token
     * @param ids
     * @return
     */
    @HTTP(method = "DELETE", path = "api/notifications/insides/delete", hasBody = true)
    @FormUrlEncoded
    Observable<BaseResponse> deleteInternalMsg(
            @Field("api_token") String api_token,
            @Field("ids") String ids
    );

    /**
     * 系统消息删除（批量）
     *
     * @param api_token
     * @param ids
     * @return
     */
    @HTTP(method = "DELETE", path = "api/notifications/systems/delete", hasBody = true)
    @FormUrlEncoded
    Observable<BaseResponse> deleteSystemMsg(
            @Field("api_token") String api_token,
            @Field("ids") String ids
    );

    /**
     * 故障消息删除（批量）
     *
     * @param api_token
     * @param ids
     * @return
     */
    @HTTP(method = "DELETE", path = "api/notifications/faults/delete", hasBody = true)
    @FormUrlEncoded
    Observable<BaseResponse> deleteFaultMsg(
            @Field("api_token") String api_token,
            @Field("ids") String ids
    );

    /**
     * 共享消息删除（批量）
     *
     * @param api_token
     * @param ids
     * @return
     */
    @HTTP(method = "DELETE", path = "api/notifications/shares/delete", hasBody = true)
    @FormUrlEncoded
    Observable<BaseResponse> deleteShareMsg(
            @Field("api_token") String api_token,
            @Field("ids") String ids
    );

    /**
     * 内部消息标为已读
     *
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
     *
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
     * 故障状态操作(进行中、已解决)
     *
     * @param api_token
     * @param notification_id
     * @param api_token
     * @param status
     * @return
     */
    @PATCH("api/notifications/faults/{notification_id}/do_fault")
    @FormUrlEncoded
    Observable<BaseResponse> doFaultMsg(
            @Path("notification_id") String notification_id,
            @Field("api_token") String api_token,
            @Field("status") String status
    );

    /**
     * 全部设备
     *
     * @param api_token
     * @return
     */
    @GET("api/user/machines")
    Observable<BaseResponse<List<MineRoomBean>>> getAllDevs(
            @Query("api_token") String api_token
    );

    /**
     * 设备共享设置列表
     *
     * @param api_token
     * @return
     */
    @GET("api/machines/{machine_id}/user")
    Observable<BaseResponse<ShareListBean>> getShareList(
            @Path("machine_id") String machine_id,
            @Query("api_token") String api_token
    );

    /**
     * 取消用户设备共享
     *
     * @param api_token
     * @return
     */
    @PATCH("api/machines/{machine_id}/user/{user_id}/cancel_share")
    @FormUrlEncoded
    Observable<BaseResponse> cancleDevShare(
            @Path("machine_id") String machine_id,
            @Path("user_id") String user_id,
            @Field("api_token") String api_token
    );

    /**
     * 设备临时授权
     *
     * @param api_token
     * @return
     */
    @POST("api/machines/share")
    @FormUrlEncoded
    Observable<BaseResponse> shareAuthor(
            @Field("api_token") String api_token,
            @Field("machine_id") String machine_id,
            @Field("mobile") String mobile,
            @Field("start_at") String start_at,
            @Field("end_at") String end_at
    );

    /**
     * 设备共享
     *
     * @return
     */
    @POST("api/machines/share")
    Observable<BaseResponse> shareDev(
            @Body RequestBody expire_value
    );

    /**
     * 故障消息标为已读
     *
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

    /**
     * 共享消息标为已读
     *
     * @param api_token
     * @param ids
     * @return
     */
    @PATCH("api/notifications/shares/read")
    @FormUrlEncoded
    Observable<BaseResponse> readShareMsg(
            @Field("api_token") String api_token,
            @Field("ids") String ids
    );

    /**
     * 共享消息处理（同意、拒绝）
     *
     * @param api_token
     * @param status
     * @return
     */
    @PATCH("api/notifications/shares/{notification_id}/do_apply")
    @FormUrlEncoded
    Observable<BaseResponse> doShareMsg(
            @Path("notification_id") String notification_id,
            @Field("api_token") String api_token,
            @Field("status") String status
    );

    @GET("api/user/homepage")
    Observable<BaseResponse<List<TabBean>>> getTabData(@Query("api_token") String token);

    @GET("api/machines/add_often_list")
    Observable<BaseResponse<List<AddDeviceBean>>> getAddDeviceData(@Query("api_token") String token);

    /**
     * 设备绑定页面
     *
     * @param api_token
     * @return
     */
    @GET("api/machines/types")
    Observable<BaseResponse<List<DevTypeBean>>> getMachineTypes(
            @Query("api_token") String api_token
    );

    /**
     * 设备类型列表
     *
     * @param api_token
     * @return
     */
    @GET("api/categories")
    Observable<BaseResponse<List<CatogrieBean>>> getCatogries(
            @Query("api_token") String api_token,
            @Query("type") String type
    );

    /**
     * 设备详情
     *
     * @param api_token
     * @return
     */
    @GET("api/machines/{machine_id}")
    Observable<BaseResponse<DevDetailBean>> getDevDetail(
            @Path("machine_id") String machine_id,
            @Query("api_token") String api_token
    );

    /**
     * 房间列表
     * @param api_token
     * @param from 页面来源：ROOM_MANAGE房间管理页面， ADD_ROOM添加房间页面，REMOVE_MACHINE移动设备页面
     * @return
     */
    @GET("api/room")
    Observable<BaseResponse<List<TabBean>>> getRoomList(
            @Query("api_token") String api_token,
            @Query("from") String from
    );
    /**
     * 绑定设备
     *
     * @param api_token
     * @return
     */
    @POST("api/machines/bind")
    @FormUrlEncoded
    Observable<BaseResponse<List<CatogrieBean>>> bindDev(
            @Field("api_token") String api_token,
            @Field("name") String name,
            @Field("asset_number") String asset_number,
            @Field("mac") String mac,
            @Field("sn") String sn,
            @Field("category_id") String category_id,
            @Field("room_id") String room_id,
            @Field("type") String type,
            @Field("locker_id") String locker_id,
            @Field("password") String password
    );

    /**
     * 申请设备
     *
     * @param body
     * @return
     */
    @POST("api/machines/apply_machine")
    Observable<BaseResponse> applyDev(
            @Body RequestBody body
    );

    /**
     * 操作设备开关
     *
     * @return
     */
    @POST("api/machines/{machine_id}/operate")
    @FormUrlEncoded
    Observable<BaseResponse> switchDev(
            @Path("machine_id") String machine_id,
            @Field("api_token") String api_token,
            @Field("status") String status
    );

    /**
     * 获取升降桌当前高度
     *
     * @return
     */
    @GET("api/{machine_id}/height")
    Observable<BaseResponse<DeskBean>> getDefaultHeiht(
            @Path("machine_id") String machine_id,
            @Query("api_token") String api_token
    );

    /**
     * 长按开始运动桌子
     *
     * @return
     */
    @POST("api/machines/{machine_id}/table_start_move")
    @FormUrlEncoded
    Observable<BaseResponse<DeskBean>> startDeskMove(
            @Path("machine_id") String machine_id,
            @Field("api_token") String api_token,
            @Field("direction") String direction
    );

    /**
     * 松手停止运动桌子
     *
     * @return
     */
    @POST("api/machines/{machine_id}/table_end_move")
    @FormUrlEncoded
    Observable<BaseResponse<DeskBean>> stopDeskMove(
            @Path("machine_id") String machine_id,
            @Field("api_token") String api_token
    );

    /**
     * 桌子自定义运动
     *
     * @return
     */
    @POST("api/machines/{machine_id}/table_custom_move")
    @FormUrlEncoded
    Observable<BaseResponse> setDeskHeight(
            @Path("machine_id") String machine_id,
            @Field("api_token") String api_token,
            @Field("height") String height
    );

    /**
     * 自定义5个高度
     *
     * @return
     */
    @PATCH("api/machines/{machine_id}/custom_table_height")
    Observable<BaseResponse<DevDetailBean>> setDeskCustomHeightTag(
            @Path("machine_id") String machine_id,
            @Body RequestBody body
    );

    /**
     * 久坐时间设置
     */
    @PATCH("api/machines/sedentary_time_setting")
    @FormUrlEncoded
    Observable<BaseResponse<DevDetailBean>> setSeatTime(
            @Field("api_token") String api_token,
            @Field("machine_id") String machine_id,
            @Field("hour") String hour
    );

    /**
     * 久坐提醒开关设置
     *
     * @return
     */
    @PATCH("api/machines/{machine_id}/button_setting")
    @FormUrlEncoded
    Observable<BaseResponse<DevDetailBean>> setDeskRemindSwitch(
            @Path("machine_id") String machine_id,
            @Field("api_token") String api_token,
            @Field("sedentary_reminder") String sedentary_reminder
    );
    /**
     * 删除设备
     *
     * @param api_token
     * @param ids
     * @return
     */
    @HTTP(method = "DELETE", path = "api/machines/delete", hasBody = true)
    @FormUrlEncoded
    Observable<BaseResponse> deleteDevice(
            @Field("api_token") String api_token,
            @Field("machine_ids") String ids
    );
    /**
     * 设为常用设备（首页）
     *
     * @param api_token
     * @param ids
     * @return
     */
    @POST("api/machines/set_often")
    @FormUrlEncoded
    Observable<BaseResponse> setOften(
            @Field("api_token") String api_token,
            @Field("machine_ids") String ids
    );
    /**
     * 故障上报
     *
     * @param api_token
     * @param ids
     * @return
     */
    @POST("api/machines/fault")
    @FormUrlEncoded
    Observable<BaseResponse> uploadFault(
            @Field("api_token") String api_token,
            @Field("machine_ids") String ids,
            @Field("content") String content
    );
    /**
     * 移动设备（批量）
     *
     * @param api_token
     * @param ids
     * @return
     */
    @PATCH("api/machines/remove")
    @FormUrlEncoded
    Observable<BaseResponse> moveDev(
            @Field("api_token") String api_token,
            @Field("machine_ids") String ids,
            @Field("room_id") String room_id
    );
    /**
     * 设备重命名
     *
     * @param api_token
     * @param id
     * @return
     */
    @PATCH("api/machines/rename")
    @FormUrlEncoded
    Observable<BaseResponse<DevDetailBean>> renameDev(
            @Field("api_token") String api_token,
            @Field("machine_id") String id,
            @Field("machine_name") String name
    );

    @POST("api/machines/add_often")
    @FormUrlEncoded
    Observable<BaseResponse> setAddDeviceData(
            @Field("api_token") String token,
            @Field("machine_ids") String selectMachineIds);

    @GET("api/room/icons")
    Observable<BaseResponse<List<RoomItemBean>>> getAllRoomData(@Query("api_token") String token);

    @POST("api/room/add")
    @FormUrlEncoded
    Observable<BaseResponse<RoomItemBean>> addRoom(@Field("api_token") String token, @Field("name") String roomName, @Field("icon_id") int roomResId);

    @PATCH("api/room/{room_id}")
    @FormUrlEncoded
    Observable<BaseResponse<TabBean>> modifyRoom(@Path("room_id") int roomId, @Field("api_token") String token, @Field("name") String newName, @Field("icon_id") int newIconId);

    @GET("api/room/{room_id}")
    Observable<BaseResponse<RoomDetailBean>> getRoomDetail(@Path("room_id") int roomId, @Query("api_token") String token);

    @HTTP(method = "DELETE", path = "api/room/{room_id}", hasBody = true)
    @FormUrlEncoded
    Observable<BaseResponse> delRoom(@Path("room_id") int roomId, @Field("api_token") String token);

    @HTTP(method = "DELETE", path = "api/machines/delete", hasBody = true)
    @FormUrlEncoded
    Observable<BaseResponse> delDevice(@Field("api_token") String token, @Field("machine_ids") int deviceId);

    @GET("api/room")
    Observable<BaseResponse<List<TabBean>>> getRoomData(
            @Query("api_token") String api_token,
            @Query("from") String from
    );
}
