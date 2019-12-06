package com.zt.igreen.module.deviceconnect.contract;

import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.DevTypeBean;

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
