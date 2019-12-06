package com.zt.igreen.module.deviceconnect.contract;

import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.CatogrieBean;
import com.zt.igreen.module.data.TabBean;

import java.util.List;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface EditDevContract {
    interface View{
        void returnRoomList(List<TabBean> list);
        void returnCatogries(List<CatogrieBean> list);
        void bindSuccess(boolean isSuccess, String msg);
//        void devExist();
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void bindBatteryLock(String name,String category_id,String room_id,TabBean.MachineBean machineBean);
        public abstract void getRoomList();
        public abstract void getCatogries(String type);
//        public abstract void deleteLockById(String lock_id);
        public abstract void bindDev(String name,String category_id,String room_id,TabBean.MachineBean machineBean);
        public abstract void bindDev1(String name,String category_id,String room_id,TabBean.MachineBean machineBean);
        public abstract void bindDev2(String name,String category_id,String room_id,TabBean.MachineBean machineBean);
        public abstract void bindDev3(String name,String room_id,TabBean.MachineBean machineBean);
        public abstract void bindDev4(String name,String room_id,String category_id,TabBean.MachineBean machineBean);
        public abstract void bindDev5(String name,String room_id,String category_id,TabBean.MachineBean machineBean);
    }
}
