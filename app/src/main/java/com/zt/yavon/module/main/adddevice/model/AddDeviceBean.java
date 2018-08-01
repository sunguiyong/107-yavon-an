package com.zt.yavon.module.main.adddevice.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddDeviceBean {

    /**
     * id : 2
     * name : 默认
     * is_all_often : false
     * machines : [{"machine_id":1,"machine_name":"蓝牙锁220版","machine_icon":"http://yafeng.dev.com/images/lock/low_cabinet.png","status":"OFF","is_often":false},{"machine_id":4,"machine_name":"升降桌","machine_icon":"http://yafeng.dev.com/images/light/desk.png","status":"ON","is_often":false}]
     */

    public int id;
    public String name;
    public boolean is_all_often;
    public List<MachineBean> machines;

    public static AddDeviceBean objectFromData(String str) {

        return new Gson().fromJson(str, AddDeviceBean.class);
    }

    public static List<AddDeviceBean> arrayAddDeviceBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<AddDeviceBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static class MachineBean {
        /**
         * machine_id : 1
         * machine_name : 蓝牙锁220版
         * machine_icon : http://yafeng.dev.com/images/lock/low_cabinet.png
         * status : OFF
         * is_often : false
         */

        public int machine_id;
        public String machine_name;
        public String machine_icon;
        public String status;
        public boolean is_often;

        public static MachineBean objectFromData(String str) {

            return new Gson().fromJson(str, MachineBean.class);
        }

        public static List<MachineBean> arrayMachineBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<MachineBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }
    }
}
