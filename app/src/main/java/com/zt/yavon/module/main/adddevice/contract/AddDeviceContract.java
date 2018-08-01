package com.zt.yavon.module.main.adddevice.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.main.adddevice.model.AddDeviceBean;

import java.util.List;

public interface AddDeviceContract {
    interface View {
        void returnAddDeviceData(List<AddDeviceBean> data);

        void errorAddDeviceData(String message);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getAddDeviceData();
    }
}
