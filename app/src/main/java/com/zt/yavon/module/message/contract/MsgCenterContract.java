package com.zt.yavon.module.message.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.MsgBean;

import java.util.List;

/**
 * Created by lifujun on 2018/7/27.
 */

public interface MsgCenterContract {
    interface View{
        void returnDataList(List<MsgBean> list);
    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void getNotifications();
    }
}
