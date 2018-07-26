package com.zt.yavon.network;

import android.text.TextUtils;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.LogUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/4/30.
 */

public class ResponseConverterFactory extends Converter.Factory {

    public static ResponseConverterFactory create() {
        return create(new Gson());
    }


    public static ResponseConverterFactory create(Gson gson) {
        return new ResponseConverterFactory(gson);
    }

    private final Gson gson;

    private ResponseConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        //返回我们自定义的Gson响应体变换器
        return new GsonResponseBodyConverter<>(gson, type);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        //返回我们自定义的Gson响应体变换器
        return new GsonResponseBodyConverter<>(gson, type);
    }
    public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private Gson gson = null;
        private Type type = null;


        public GsonResponseBodyConverter(Gson gson, Type type) {
            this.gson = gson;
            this.type = type;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            String response = value.string();
//            LogUtil.d("=======convert:"+response);
            if(TextUtils.isEmpty(response)){
                return (T) new BaseResponse<>();
            }else{
                return gson.fromJson(response, type);
            }
            //先将返回的json数据解析到Response中，如果code==200，则解析到我们的实体基类中，否则抛异常
//            BaseResponse httpResult = gson.fromJson(response, BaseResponse.class);
//            if (httpResult.getCode() == 200 ) {
//                return gson.fromJson(response, type);
//            } else if(httpResult.getCode() == 419){
//                throw new TokenException(httpResult.getMessage());
//            } else {
//            ErrorRespose errorResponse = gson.fromJson(response, ErrorRespose.class);
//            抛一个自定义ResultException 传入失败时候的状态码，和信息
//            try {
//                throw new ServerException(httpResult.getCode()+"");
//                    throw new ServerException(httpResult.getMessage());
//            } catch (ServerException e) {
//                e.printStackTrace();
//                throw new RuntimeException(httpResult.getMessage());
//            } finally {
//                value.close();
//            }
//            }
        }
    }

}

