package com.zt.igreen.module.mine.contract;

import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.MineRoomBean;

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
