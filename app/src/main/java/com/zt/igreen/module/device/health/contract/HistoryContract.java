package com.zt.igreen.module.device.health.contract;

import com.common.base.rx.BaseResponse;
import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.HistoryBean;
import com.zt.igreen.module.data.MoreBean;

import java.util.List;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface HistoryContract {
    interface View{
        void returnHistory(List<HistoryBean> beans);
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void getMore(String date,String machine_id,String type,boolean showLoading );

    }
}
