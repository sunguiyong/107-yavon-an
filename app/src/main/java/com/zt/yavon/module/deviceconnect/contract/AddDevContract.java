package com.zt.yavon.module.deviceconnect.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.DevTypeBean;

import java.util.List;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface AddDevContract {
    interface View{
        void returnMachine(List<DevTypeBean> list);
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void getMachineTypes();
    }
}
