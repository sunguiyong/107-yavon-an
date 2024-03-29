package com.zt.igreen.module.main.adddevice.contract;

import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.main.adddevice.model.AddDeviceBean;

import java.util.List;

public interface AddDeviceContract {
    interface View {
        void returnAddDeviceData(List<AddDeviceBean> data);

        void errorAddDeviceData(String message);

        void returnSetDeviceData();

        void errorSetDeviceData(String message);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getAddDeviceData();

        public abstract void setAddDeviceData(String selectMachineIds);
    }
}
