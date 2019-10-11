package com.zt.igreen.module.Intelligence.contract;


import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.IntellAddBean;
import com.zt.igreen.module.data.TabBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 */
public interface DeviceIntellContract {
    interface View {
        void returnRoomList(List<IntellAddBean> list);


    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getRoomList(String machine_id);

    }
}
