package com.zt.yavon.module.main.roommanager.list.model;

import java.io.Serializable;

public class RoomBean implements Serializable{
    public String mName;
    public int mResId;
    public int mDeviceCount;

    public RoomBean(String name, int resId, int deviceCount) {
        mName = name;
        mResId = resId;
        mDeviceCount = deviceCount;
    }
}
