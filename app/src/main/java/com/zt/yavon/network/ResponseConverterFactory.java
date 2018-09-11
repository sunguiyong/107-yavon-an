package com.zt.yavon.network;

import android.text.TextUtils;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.LogUtil;
import com.common.base.utils.TUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zt.yavon.module.main.adddevice.model.AddDeviceBean;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
        return new GsonResponseBodyConverter<Type>(gson, type);
    }

//    @Override
//    public Converter<?, RequestBody> requestBodyConverter(Type type,
//                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
//        //返回我们自定义的Gson响应体变换器
//        return new GsonResponseBodyConverter<>(gson, type);
//    }
    public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private Gson gson = null;
        private Type type = null;


        public GsonResponseBodyConverter(Gson gson, Type type) {
            this.gson = gson;
            this.type = type;
//            LogUtil.d("============= Type:"+ type);
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
//            LogUtil.d("=======ResponseBody:"+value);
            String response = value.string();
//            LogUtil.d("=======convert:"+response);
            if(TextUtils.isEmpty(response) || "[]".equals(response) || "{}".equals(response)){
                return (T) new BaseResponse();
            }else{
                T t = gson.fromJson(response, type);
                if(t instanceof BaseResponse){
                    BaseResponse t2 = (BaseResponse) t;
                    Object data = t2.getData();
                    if(data == null || TextUtils.isEmpty(data.toString()) || "null".equalsIgnoreCase(data.toString()) || "[]".equals(data.toString()) || "{}".equals(data.toString())){
                        if(type instanceof ParameterizedType){
                            Type childType = ((ParameterizedType) type).getActualTypeArguments()[0];
                            String jsonString = "";
                            if(childType == String.class){
                                jsonString = "";
                                t2.setData(jsonString);
                            }else if(childType.toString().startsWith(List.class.getName())){
                                jsonString = "[]";
                                t2.setData(gson.fromJson(jsonString,childType));
                            }else{
                                jsonString = "{}";
                                t2.setData(gson.fromJson(jsonString,childType));
                            }
//                            LogUtil.d("============= childType:"+childType+",jsonString:"+ jsonString);

                        }
                    }
                }
                return t;
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

