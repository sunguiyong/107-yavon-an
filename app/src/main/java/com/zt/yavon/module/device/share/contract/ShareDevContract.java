package com.zt.yavon.module.device.share.contract;

import com.zt.yavon.component.BasePresenter;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface ShareDevContract {
    interface View{
        void shareSuccess();
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void shareDev(String machineId,String mobile,String expire_type,String expire_value);
    }
}
