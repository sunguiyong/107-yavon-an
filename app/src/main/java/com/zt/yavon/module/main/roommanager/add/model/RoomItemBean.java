package com.zt.yavon.module.main.roommanager.add.model;

import com.zt.yavon.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Administrator
 * Date: 2018/7/17
 */
public class RoomItemBean implements Serializable {
    public static List<RoomItemBean> data = new ArrayList<>();

    static {
        data.add(new RoomItemBean("办公室", R.drawable.selector_tab_office, R.mipmap.ic_office_checked, R.mipmap.ic_office_normal));
        data.add(new RoomItemBean("会议室", R.drawable.selector_tab_meeting, R.mipmap.ic_meeting_checked, R.mipmap.ic_meeting_normal));
        data.add(new RoomItemBean("休息室", R.drawable.selector_tab_rest, R.mipmap.ic_rest_checked, R.mipmap.ic_rest_normal));
        data.add(new RoomItemBean("接待区", R.drawable.selector_tab_reception, R.mipmap.ic_reception_checked, R.mipmap.ic_reception_normal));
        data.add(new RoomItemBean("茶水间", R.drawable.selector_tab_water, R.mipmap.ic_water_checked, R.mipmap.ic_water_normal));
        data.add(new RoomItemBean("餐厅", R.drawable.selector_tab_dining, R.mipmap.ic_dining_checked, R.mipmap.ic_dining_normal));
        data.add(new RoomItemBean("休闲室", R.drawable.selector_tab_game, R.mipmap.ic_game_checked, R.mipmap.ic_game_normal));
        data.add(new RoomItemBean("活动室", R.drawable.selector_tab_sport, R.mipmap.ic_sport_checked, R.mipmap.ic_sport_normal));
        data.add(new RoomItemBean("卫生间", R.drawable.selector_tab_wash, R.mipmap.ic_wash_checked, R.mipmap.ic_wash_normal));
        data.add(new RoomItemBean("工作区", R.drawable.selector_tab_work, R.mipmap.ic_work_checked, R.mipmap.ic_work_normal));
        data.add(new RoomItemBean("储物间", R.drawable.selector_tab_store, R.mipmap.ic_store_checked, R.mipmap.ic_store_normal));
        data.add(new RoomItemBean("自定义", R.mipmap.ic_add, 0, 0));
    }

    public String mName;
    public int mResId;
    public int mCheckedResId;
    public int mUncheckedResId;

    public RoomItemBean(String name, int selectorResId, int checkedResId, int uncheckedResId) {
        mName = name;
        mResId = selectorResId;
        mCheckedResId = checkedResId;
        mUncheckedResId = uncheckedResId;
    }
}
