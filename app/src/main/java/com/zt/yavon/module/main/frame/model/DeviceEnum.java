package com.zt.yavon.module.main.frame.model;

import com.zt.yavon.R;

import java.io.Serializable;

public enum DeviceEnum implements Serializable {
    Lamp(1, "智能台灯", R.mipmap.ic_item_lamp), Lock(2, "智能锁", R.mipmap.ic_item_lock), Desk(3, "智能升降桌", R.mipmap.ic_item_desk);
    public int mId;
    public int mResId;
    public String mName;

    DeviceEnum(int id, String name, int resId) {
        mId = id;
        mResId = resId;
        mName = name;
    }
}
