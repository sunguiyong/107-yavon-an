package com.zt.igreen.module.main.frame.contract;


import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.HealthInfoBean;
import com.zt.igreen.module.data.TabBean;
import com.zt.igreen.module.data.VersionBean;
import com.zt.igreen.module.data.WeatherBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 */
public interface HomeContract {
    interface View {
        void returnTabData(List<TabBean> data);
        void errorTabData(String message);
        void returnVersion(VersionBean bean);
        void unreadMsgCount(int count);
        void updateWeather(WeatherBean bean);

    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getTabData(boolean showLoading);
        public abstract void getInternalMsgUnreadCount();
        public abstract void getCity(String location);
        public abstract void getVersion();

    }
}
