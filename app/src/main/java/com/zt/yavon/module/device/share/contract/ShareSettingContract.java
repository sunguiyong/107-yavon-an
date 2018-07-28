package com.zt.yavon.module.device.share.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.ShareListBean;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface ShareSettingContract {
    interface View{
        void returnShareList(ShareListBean bean);
        void cancleDevShareSuccess(ShareListBean.User user);
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void getShareList(String machineId);
        public abstract void cancleDevShare(String machineId,ShareListBean.User user);
    }
}
