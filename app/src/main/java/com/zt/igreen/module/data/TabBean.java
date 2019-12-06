package com.zt.igreen.module.data;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TabBean implements Serializable {

    /**
     * id : 27
     * name : 默认
     * icon : http://t27.zetadata.com.cn/images/room/default.png
     * icon_select : http://t27.zetadata.com.cn/images/room/default-select.png
     * type : DEFAULT
     * machines : [{"id":4,"name":"电池锁","icon":"http://t27.zetadata.com.cn/images/light/desk.png","status":"","machine_type":"ADJUST_TABLE","user_type":"FIRST_USER","is_authorized":false,"from_room":""}]
     */

    public int id;
    public String name;
    public String icon;
    public String icon_select;
    public String type;
    public List<MachineBean> machines;
    public int machine_total;

    public static TabBean objectFromData(String str) {

        return new Gson().fromJson(str, TabBean.class);
    }

    public static List<TabBean> arrayTabBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<TabBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getMachineSize() {
        return machine_total;
    }

    public static class MachineBean implements Serializable {
        /**
         * id : 4
         * name : 电池锁
         * icon : http://t27.zetadata.com.cn/images/light/desk.png
         * status :
         * machine_type : ADJUST_TABLE
         * user_type : FIRST_USER
         * is_authorized : false
         * from_room :
         */

        public int id;
        public String name;
        public String icon;
        public String status;
        public String machine_type;
        public String user_type;
        public boolean is_authorized;
        public boolean auto_lock;
        public String from_room;
        public String asset_number;
        public String locker_id;
        public String online_status;
        public String password;
        public String light_device_id;
        public boolean isLastOne;
        public String device_id;

        @Override
        public String toString() {
            return "MachineBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", icon='" + icon + '\'' +
                    ", status='" + status + '\'' +
                    ", machine_type='" + machine_type + '\'' +
                    ", user_type='" + user_type + '\'' +
                    ", is_authorized=" + is_authorized +
                    ", auto_lock=" + auto_lock +
                    ", from_room='" + from_room + '\'' +
                    ", asset_number='" + asset_number + '\'' +
                    ", locker_id='" + locker_id + '\'' +
                    ", online_status='" + online_status + '\'' +
                    ", password='" + password + '\'' +
                    ", light_device_id='" + light_device_id + '\'' +
                    ", isLastOne=" + isLastOne +
                    ", device_id='" + device_id + '\'' +
                    '}';
        }

        public boolean isPowerOn() {
            return !TextUtils.isEmpty(status) && status.equals("ON");
        }

        public MachineBean() {
        }

        public MachineBean(boolean isLastOne) {
            this.isLastOne = isLastOne;
        }

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
