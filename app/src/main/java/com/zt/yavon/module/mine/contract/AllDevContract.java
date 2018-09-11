package com.zt.yavon.module.mine.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.MineRoomBean;

import java.util.List;

/**
 * Created by lifujun on 2018/7/25.
 */

public interface AllDevContract {
    interface View {
        void returnDevs(List<MineRoomBean> list);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getAllDevs(boolean showLoading);
    }
}
