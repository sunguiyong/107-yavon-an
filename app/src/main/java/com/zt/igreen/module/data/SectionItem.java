package com.zt.igreen.module.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by lifujun on 2018/8/25.
 */

public class SectionItem implements MultiItemEntity {
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_DETAIL = 2;
    private int type = TYPE_TITLE;
    public Object data;
    public SectionItem(int type,Object data){
        this.type = type;
        this.data = data;
    }
    @Override
    public int getItemType() {
        return type;
    }
}
