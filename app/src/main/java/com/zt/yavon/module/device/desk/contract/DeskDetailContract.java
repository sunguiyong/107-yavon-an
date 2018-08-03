package com.zt.yavon.module.device.desk.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.DeskBean;
import com.zt.yavon.module.data.DevDetailBean;

import java.util.List;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface DeskDetailContract {
    interface View{
        void returnDevDetail(DevDetailBean bean);
        void returnHeiht(DeskBean bean);
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void getDevDetail(String machine_id);
        public abstract void getDefaultHeight(String machine_id);
        public abstract void startDeskMove(String machine_id,boolean isUP);
        public abstract void stopDeskMove(String machine_id);
        public abstract void setDeskHeight(String machine_id,String height);
        public abstract void setDeskCustomHeightTag(String machine_id,List<DeskBean> height);
    }
}
