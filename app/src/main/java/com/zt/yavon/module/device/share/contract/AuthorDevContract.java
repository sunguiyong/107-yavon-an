package com.zt.yavon.module.device.share.contract;

import com.zt.yavon.component.BasePresenter;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface AuthorDevContract {
    interface View{
        void shareSuccess();
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void shareAuthor(String machineId,String mobile,String start_at,String end_at,long start,long end);
    }
}
