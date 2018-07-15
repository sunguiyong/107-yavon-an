package com.zt.yavon.module.main.frame.presenter;

import com.zt.yavon.module.main.frame.contract.DeviceContract;
import com.zt.yavon.module.main.frame.model.DeviceEnum;
import com.zt.yavon.module.main.frame.model.DeviceItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class DevicePresenter extends DeviceContract.Presenter {

    @Override
    public void getDeviceData(String currTabId) {
        List<DeviceItemBean> data = new ArrayList<>();
        data.add(new DeviceItemBean("1", "办公室1", false, DeviceEnum.Lamp));
        data.add(new DeviceItemBean("2", "办公室3", true, DeviceEnum.Desk));
        data.add(new DeviceItemBean("3", "会议室3", false, DeviceEnum.Lock));
        data.add(new DeviceItemBean());
        mView.returnDeviceData(data);
    }
}
