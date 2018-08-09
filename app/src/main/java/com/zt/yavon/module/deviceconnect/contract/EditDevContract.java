package com.zt.yavon.module.deviceconnect.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.CatogrieBean;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.network.YSBResponse;

import java.util.List;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface EditDevContract {
    interface View{
        void returnRoomList(List<TabBean> list);
        void returnCatogries(List<CatogrieBean> list);
        void bindSuccess(boolean isSuccess,String msg);
//        void devExist();
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void bindBatteryLock(String name,String category_id,String room_id,TabBean.MachineBean machineBean);
        public abstract void getRoomList();
        public abstract void getCatogries(String type);
//        public abstract void deleteLockById(String lock_id);
        public abstract void bindDev(String name,String category_id,String room_id,TabBean.MachineBean machineBean);
    }
}
