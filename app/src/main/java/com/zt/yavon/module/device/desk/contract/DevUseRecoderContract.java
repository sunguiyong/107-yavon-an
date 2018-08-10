package com.zt.yavon.module.device.desk.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.UserRecordBean;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface DevUseRecoderContract {
    interface View{
        void returnUseRecord(UserRecordBean bean);
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void getUseRecord(String machine_id,int page,int perPage);
    }
}
