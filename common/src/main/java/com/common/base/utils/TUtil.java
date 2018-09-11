package com.common.base.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 类转换初始化
 */
public class TUtil {
    public static <T> T getT(Object o, int i) {
        try {
            Type type = o.getClass().getGenericSuperclass();
            if(type instanceof ParameterizedType){
                return ((Class<T>) ((ParameterizedType) type).getActualTypeArguments()[i])
                        .newInstance();
            }
        } catch (Exception e) {
            // exception
//            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
