package com.zt.yavon.module.main.frame.contract;


import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.TabBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 */
public interface DeviceContract {
    interface View {
        void returnRoomList(List<TabBean> list);
        void deleteSuccess(List<TabBean.MachineBean> beans);
        void setOftenSuccess(List<TabBean.MachineBean> beans);
        void moveSuccess(List<TabBean.MachineBean> beans);
        void renameSuccess(TabBean.MachineBean bean);
        void uploadFaultSuccess();
        void removeOftenSuccess(List<TabBean.MachineBean> beans);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getRoomList();
        public abstract void deleteDevice(List<TabBean.MachineBean> beans);
        public abstract void setOften(List<TabBean.MachineBean> beans);
        public abstract void moveDev(List<TabBean.MachineBean> beans,String roomId);
        public abstract void renameDev(List<TabBean.MachineBean> beans);
        public abstract void uploadFault(List<TabBean.MachineBean> beans);
        public abstract void removeOften(List<TabBean.MachineBean> beans);
    }
}
