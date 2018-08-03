package com.zt.yavon.module.device.share.contract;

import com.zt.yavon.component.BasePresenter;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface ApplyDevContract {
    interface View{
        void applySuccess();
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void applyDev(String name,String asset_number,String expire_type,String expire_value,String room_id);
    }
}
