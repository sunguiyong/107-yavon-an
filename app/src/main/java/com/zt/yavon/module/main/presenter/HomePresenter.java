package com.zt.yavon.module.main.presenter;

import com.zt.yavon.module.data.DemoBean;
import com.zt.yavon.module.main.contract.HomeContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class HomePresenter extends HomeContract.Presenter{
    public String SET_SWITCH = "on";
    public String SET_LINK = "link";
    public String SET_FAN = "fan";
    public String SET_MODE = "mode";
    @Override
    public void getHomeDevList(String page,boolean showLoading) {
//        mRxManage.add(Api.getHomeDevList(SPUtil.getToken(mContext),page)
//                .subscribeWith(new RxSubscriber<List<DemoBean>>(mContext,showLoading) {
//                    @Override
//                    protected void _onNext(List<DemoBean> list) {
//                        mView.returnHomeDevList(list);
//                    }
//                    @Override
//                    protected void _onError(String message) {
//                        mView.returnHomeDevList(null);
//                        ToastUtil.showShort(mContext,message);
//                    }
//                }).getDisposable());
        List<DemoBean> list = new ArrayList<>();
        for(int i = 0;i<20;i++){
            DemoBean demoBean = new DemoBean();
            demoBean.setDemoString("item"+i);
            list.add(demoBean);
        }
        mView.returnHomeDevList(list);
    }

}
