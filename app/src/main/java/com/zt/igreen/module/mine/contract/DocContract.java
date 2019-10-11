package com.zt.igreen.module.mine.contract;

import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.DocBean;

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
