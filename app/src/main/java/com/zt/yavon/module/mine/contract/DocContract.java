package com.zt.yavon.module.mine.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.DocBean;

/**
 * Created by lifujun on 2018/7/25.
 */

public interface DocContract {
    interface View {
        void returnDoc(DocBean bean);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getDoc(String type);
    }
}
