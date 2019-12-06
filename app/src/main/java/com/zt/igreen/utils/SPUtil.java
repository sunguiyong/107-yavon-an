package com.zt.igreen.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.google.gson.Gson;
import com.zt.igreen.module.data.LoginBean;

import java.nio.charset.Charset;

/**
 * Created by hp on 2018/6/14.
 */

public class SPUtil {
    public static String PREFERENCE_NAME = "data";

    /**用户名的key值*/
    public static String TEMP = "temp";
    public static String AUTO_LOGIN = "autologin";
    public static boolean saveAccount(Context context,LoginBean bean){
        String encodeString = Base64.encodeToString(bean.toString().getBytes(Charset.forName("utf-8")),Base64.NO_WRAP);
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TEMP, encodeString);
        return editor.commit();
    }


    public static LoginBean getAccount(Context context){
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, 0);
        String decodeString = sp.getString(TEMP,null);
        if(decodeString != null){
            try {
                decodeString = new String(Base64.decode(decodeString,Base64.NO_WRAP),"utf-8");
                return new Gson().fromJson(decodeString,LoginBean.class);
            }catch (Exception e){

            }
        }
        return null;
    }
//    /**保存TOKEN*/
//    public static boolean saveToken(Context context, String uid) {
//        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(TEMP, uid);
//        return editor.commit();
//    }
    /**读取TOKEN*/
    public static String getToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, 0);
        String decodeString = sp.getString(TEMP,null);
        if(decodeString != null){
            try {
                decodeString = new String(Base64.decode(decodeString,Base64.NO_WRAP),"utf-8");
                return new Gson().fromJson(decodeString,LoginBean.class).getApi_token();
            }catch (Exception e){

            }
        }
        return null;
    }
    /**存储字符串*/
    public static boolean putString(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }
    /**读取字符串*/
    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }
    /**存储字符串*/
    public static boolean putLocation(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }
    /**读取字符串*/
    public static String getLocation(Context context, String key) {
        return getString(context, key, null);
    }
    /**存储字符串*/
    public static boolean putUid(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }
    /**读取字符串*/
    public static String getUid(Context context, String key) {
        return getString(context, key, null);
    }
    /**存储字符串*/
    public static boolean puttoken(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }
    /**读取字符串*/
    public static String gettoken(Context context, String key) {
        return getString(context, key, null);
    }
    /**读取字符串（带默认值的）*/
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        return preferences.getString(key, defaultValue);
    }
    /**存储整型数字*/
    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }
    /**读取整型数字*/
    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }
    /**读取整型数字（带默认值的）*/
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        return preferences.getInt(key, defaultValue);
    }
    /**存储长整型数字*/
    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }
    /**读取长整型数字*/
    public static long getLong(Context context, String key) {
        return getLong(context, key, 0xffffffff);
    }
    /**读取长整型数字（带默认值的）*/
    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        return preferences.getLong(key, defaultValue);
    }
    /**存储Float数字*/
    public static boolean putFloat(Context context, String key, float value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }
    /**读取Float数字*/
    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1.0f);
    }
    /**读取Float数字（带默认值的）*/
    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        return preferences.getFloat(key, defaultValue);
    }
    /**存储boolean类型数据*/
    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }
    /**读取boolean类型数据*/
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }
    /**读取boolean类型数据（带默认值的）*/
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        return preferences.getBoolean(key, defaultValue);
    }
    /**清除数据*/
    public static boolean clearPreferences(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        return editor.commit();
    }
    public static void saveUserName(Context context,String guid){
        SharedPreferences share =context.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("username", guid);
        editor.commit();
    }
    public static String getUserName(Context context){
        SharedPreferences sp =context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getString("username", "");
    }
}
