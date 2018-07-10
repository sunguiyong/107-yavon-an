package com.common.base.rx;

/**
 * Created by hp on 2018/1/3.
 */
public class BaseResponse<T> {
    private int code;
    private String message;
    private T datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }

}