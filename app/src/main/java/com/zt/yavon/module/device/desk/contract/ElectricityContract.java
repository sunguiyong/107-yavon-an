package com.zt.yavon.module.device.desk.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.ElectricDayBean;
import com.zt.yavon.module.data.ElectricMonthBean;

import java.util.List;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface ElectricityContract {
    interface View{
        void returnDayList(List<ElectricDayBean> list);
        void returnMonthList(List<ElectricMonthBean> list);
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void getMonthPower(String machine_id);
        public abstract void getDayPower(String machine_id);
    }
}
