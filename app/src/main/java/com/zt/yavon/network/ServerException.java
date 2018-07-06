package com.zt.yavon.network;

/**
 * des:服务器请求异常
 * Created by xsf
 * on 2016.09.10:16
 */
public class ServerException extends RuntimeException {
    public ServerException(String msg)  {
        super(msg);
//        throw new ServerException(msg);
    }

}
