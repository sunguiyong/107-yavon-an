package com.zt.yavon.module.main.roommanager.detail.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RoomDetailBean {

    /**
     * id : 2
     * name : 默认
     * icon_id : 1
     * icon : http://yafeng.dev.com/storage/upload/room/default/default.png
     * machines : [{"id":2,"icon":"http://yafeng.dev.com/images/light/desk.png","name":"彩霞","status":""}]
     * machine_total : 1
     */

    public int id;
    public String name;
    public int icon_id;
    public String icon;
    public int machine_total;
    public List<MachinesBean> machines;

    public static RoomDetailBean objectFromData(String str) {

        return new Gson().fromJson(str, RoomDetailBean.class);
    }

    public static List<RoomDetailBean> arrayRoomDetailBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<RoomDetailBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static class MachinesBean {
        /**
         * id : 2
         * icon : http://yafeng.dev.com/images/light/desk.png
         * name : 彩霞
         * status :
         */

        public int id;
        public String icon;
        public String name;
        public String status;

        public static MachinesBean objectFromData(String str) {

            return new Gson().fromJson(str, MachinesBean.class);
        }

        public static List<MachinesBean> arrayMachinesBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<MachinesBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }
    }
}
