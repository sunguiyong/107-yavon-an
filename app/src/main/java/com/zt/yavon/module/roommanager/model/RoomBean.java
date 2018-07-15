package com.zt.yavon.module.roommanager.model;

public class RoomBean {
    public String mName;
    public int mResId;
    public int mDeviceCount;

    public RoomBean(String name, int resId, int deviceCount) {
        mName = name;
        mResId = resId;
        mDeviceCount = deviceCount;
    }
}
