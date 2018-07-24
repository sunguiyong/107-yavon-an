package com.zt.yavon.module.main.frame.model;

import java.io.Serializable;

public class TabItemBean implements Serializable {
    public String mId;
    public String mTitle;
    public int mSelectResId;
    public int mUnSelectResId;
    public int mGrayResId;

    public TabItemBean(String id, String title, int selectResId, int unselectResId) {
        mId = id;
        mSelectResId = selectResId;
        mUnSelectResId = unselectResId;
        mTitle = title;
        mGrayResId = mSelectResId;
    }
}
