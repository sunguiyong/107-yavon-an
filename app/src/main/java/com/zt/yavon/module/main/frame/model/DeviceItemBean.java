package com.zt.yavon.module.main.frame.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeviceItemBean implements Serializable{
    public static List<DeviceItemBean> data = new ArrayList<>();
    static {
        data.add(new DeviceItemBean("1", "办公室1", false, DeviceEnum.Lamp));
        data.add(new DeviceItemBean("2", "办公室3", true, DeviceEnum.Desk));
        data.add(new DeviceItemBean("3", "会议室3", false, DeviceEnum.Lock));
    }
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
