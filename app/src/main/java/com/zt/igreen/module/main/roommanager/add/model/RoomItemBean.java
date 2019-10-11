package com.zt.igreen.module.main.roommanager.add.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Administrator
 * Date: 2018/7/17
 */
public class RoomItemBean implements Serializable {
    public int id;
    public String name = "自定义";
    public String icon_select;
    public String icon;
    public int machine_total;

    public static RoomItemBean objectFromData(String str) {

        return new Gson().fromJson(str, RoomItemBean.class);
    }

    public static List<RoomItemBean> arrayRoomBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<RoomItemBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }
}
