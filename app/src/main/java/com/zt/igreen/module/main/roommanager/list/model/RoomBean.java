//package com.zt.yavon.module.main.roommanager.list.model;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.io.Serializable;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
//public class RoomBean implements Serializable{
//
//    /**
//     * id : 1
//     * name : 办公室
//     * icon : http://t27.zetadata.com.cn/images/room/often.png
//     * icon_select : http://t27.zetadata.com.cn/images/room/often-select.png
//     * machine_total : 0
//     */
//
//    public int id;
//    public String name;
//    public String icon;
//    public String icon_select;
//    public int machine_total;
//
//    public static RoomBean objectFromData(String str) {
//
//        return new Gson().fromJson(str, RoomBean.class);
//    }
//
//    public static List<RoomBean> arrayRoomBeanFromData(String str) {
//
//        Type listType = new TypeToken<ArrayList<RoomBean>>() {
//        }.getType();
//
//        return new Gson().fromJson(str, listType);
//    }
//}
