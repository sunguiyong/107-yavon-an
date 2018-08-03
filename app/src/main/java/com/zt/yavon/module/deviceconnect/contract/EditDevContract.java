package com.zt.yavon.module.deviceconnect.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.CatogrieBean;
import com.zt.yavon.module.data.TabBean;

import java.util.List;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface EditDevContract {
    interface View{
        void returnRoomList(List<TabBean> list);
        void returnCatogries(List<CatogrieBean> list);
        void bindSuccess();
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void getRoomList();
        public abstract void getCatogries(String type);
        public abstract void bindDev(String name,String asset_number,String sn,String category_id,String room_id,String type,String lockId,String password);
    }
}
