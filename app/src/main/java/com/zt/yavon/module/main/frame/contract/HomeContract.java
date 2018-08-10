package com.zt.yavon.module.main.frame.contract;


import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.data.WeatherBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 */
public interface HomeContract {
    interface View {
        void returnTabData(List<TabBean> data);

        void errorTabData(String message);

        void unreadMsgCount(int count);
        void updateWeather(WeatherBean bean);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getTabData();
        public abstract void getInternalMsgUnreadCount();
        public abstract void getCity(String location);
    }
}
