package com.zt.yavon.module.main.roommanager.add.model;

import com.zt.yavon.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Administrator
 * Date: 2018/7/17
 */
public class RoomItemBean implements Serializable{
    public static List<RoomItemBean> data = new ArrayList<>();

    static {
        data.add(new RoomItemBean("办公室", R.drawable.selector_tab_office));
        data.add(new RoomItemBean("会议室", R.drawable.selector_tab_meeting));
        data.add(new RoomItemBean("休息室", R.drawable.selector_tab_rest));
        data.add(new RoomItemBean("接待区", R.drawable.selector_tab_reception));
        data.add(new RoomItemBean("茶水间", R.drawable.selector_tab_water));
        data.add(new RoomItemBean("餐厅", R.drawable.selector_tab_dining));
        data.add(new RoomItemBean("休闲室", R.drawable.selector_tab_game));
        data.add(new RoomItemBean("活动室", R.drawable.selector_tab_sport));
        data.add(new RoomItemBean("卫生间", R.drawable.selector_tab_wash));
        data.add(new RoomItemBean("工作区", R.drawable.selector_tab_work));
        data.add(new RoomItemBean("储物间", R.drawable.selector_tab_store));
        data.add(new RoomItemBean("自定义", R.mipmap.ic_add));
    }

    public String mName;
    public int mResId;

    public RoomItemBean(String name, int resId) {
        mName = name;
        mResId = resId;
    }
}
