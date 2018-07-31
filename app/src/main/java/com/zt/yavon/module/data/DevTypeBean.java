package com.zt.yavon.module.data;

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

    public class TYPE{
        public String name;
        public String icon;
        public String icon_big;
        public String type;
        public String description;
    }
}
