package com.zt.yavon.module.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by lifujun on 2018/7/11.
 */

public class SectionItem implements MultiItemEntity {
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_DETAIL = 2;
    private Object data;
    private int type;

    public SectionItem(int type, Object data) {
        this.type = type;
        this.data = data;
    }

    @Override
    public int getItemType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}
