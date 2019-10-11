package com.zt.igreen.module.main.adddevice.model;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.zt.igreen.module.main.adddevice.adapter.RvAddDevice.ITEM_TYPE_CHILD;
import static com.zt.igreen.module.main.adddevice.adapter.RvAddDevice.ITEM_TYPE_GROUP;

public class AddDeviceBean extends AbstractExpandableItem<AddDeviceBean.MachineBean> implements MultiItemEntity {

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

    @Override
    public int getItemType() {
        return ITEM_TYPE_GROUP;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    public static class MachineBean implements MultiItemEntity {
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

        @Override
        public int getItemType() {
            return ITEM_TYPE_CHILD;
        }

        public String getStatus() {
            return "ON".equals(status) ? "设备开启" : "设备关闭";
        }
    }
}
