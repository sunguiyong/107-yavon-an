package com.zt.yavon.module.main.frame.model;

import java.io.Serializable;

public class DeviceItemBean implements Serializable{
    public String mDeviceId;
    public String mLocation;
    public boolean mOnOffStatus;
    public DeviceEnum mDeviceEnum;

    public DeviceItemBean(String deviceId, String location, boolean onOffStatus, DeviceEnum deviceEnum) {
        mDeviceId = deviceId;
        mLocation = location;
        mOnOffStatus = onOffStatus;
        mDeviceEnum = deviceEnum;
    }

    public DeviceItemBean() {

    }

}
