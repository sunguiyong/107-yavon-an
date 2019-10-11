package com.zt.igreen.module.mall.contract;

import com.zt.igreen.component.BasePresenter;
import com.zt.igreen.module.data.FavoriteBean;

import java.util.List;

/**
 * Created by lifujun on 2018/7/28.
 */

public interface WordContract {
    interface View{
        void returnWord();

    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void getWord(String material_id,String context,String company_name,String link_name,String mobile);
    }
}
