package com.zt.igreen.module.deviceconnect.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zt.igreen.R;
import com.zt.igreen.module.data.TabBean;

/**
 * Created by hp on 2018/7/20.
 */

public class RoomNameAdapter  extends BaseQuickAdapter<TabBean,BaseViewHolder>{
    public RoomNameAdapter() {
        super(R.layout.item_room_name, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, TabBean item) {
        helper.setText(R.id.tv_name_room,item.name);
    }
}
