package com.zt.igreen.module.data;

/**
 * Created by lifujun on 2018/7/25.
 */

public class LoginBean {
    private String nick_name;
    private String pwd;
    private String account;
    private String email;
    private String mobile;
    private String avatar;
    private String api_token;
    private boolean open_auto_update;
    private boolean open_system_message;

    public String getTy_family_id() {
        return ty_family_id;
    }

    public void setTy_family_id(String ty_family_id) {
        this.ty_family_id = ty_family_id;
    }

    private String ty_family_id;

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public boolean getOpen_auto_update() {
        return open_auto_update;
    }

    public void setOpen_auto_update(boolean open_auto_update) {
        this.open_auto_update = open_auto_update;
    }

    public boolean getOpen_system_message() {
        return open_system_message;
    }

    public void setOpen_system_message(boolean open_system_message) {
        this.open_system_message = open_system_message;
    }

    @Override
    public String toString() {
        return "{nick_name='" + nick_name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", account='" + account + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", avatar='" + avatar + '\'' +
                ", api_token='" + api_token + '\'' +
                ", open_auto_update='" + open_auto_update + '\'' +
                ", open_system_message='" + open_system_message + '\'' +
                '}';
    }
}
