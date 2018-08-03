package com.zt.yavon.network;

/**
 * Created by lifujun on 2018/8/1.
 */

public class YSBResponse {
    private String access_token;
    private String token_type;
    private String lock_id;
    private String sn;
    private String data;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getLock_id() {
        return lock_id;
    }

    public void setLock_id(String lock_id) {
        this.lock_id = lock_id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHeader(){
        return token_type+" "+access_token;
    }
}
