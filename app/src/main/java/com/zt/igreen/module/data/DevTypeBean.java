package com.zt.igreen.module.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lifujun on 2018/7/31.
 */

public class DevTypeBean {
    private String name;
    private List<TYPE> types;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TYPE> getTypes() {
        return types;
    }

    public void setTypes(List<TYPE> types) {
        this.types = types;
    }

    public class TYPE implements Serializable{
        public String name;
        public String icon;
        public String icon_big;
        public String type;//BLUE_LOCK蓝牙锁 BATTERY_LOCK电池锁 LIGHT灯 ADJUST_TABLE升降桌
        public String description;
        public String sn;
        public String version;
    }
}
