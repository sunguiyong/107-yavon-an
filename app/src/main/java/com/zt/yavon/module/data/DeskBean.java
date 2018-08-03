package com.zt.yavon.module.data;

import java.io.Serializable;

/**
 * Created by lifujun on 2018/8/3.
 */

public class DeskBean implements Serializable {
    private boolean isSelect;
    public String name;
    public int height;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
