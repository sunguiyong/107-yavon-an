package com.zt.igreen.module.mall.contract;

import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.IntellListBean;
import com.zt.igreen.module.data.MaterialDetailsBean;
import com.zt.igreen.module.data.MaterialTypeBean;

import java.util.List;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface MalMaterialContract {
    interface View{
        void returnMalMaterial(MaterialDetailsBean bean);
        void returnCollect();
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void getMalMaterial(String material_id);
       public abstract void getCollect(String material_id);
    }
}
