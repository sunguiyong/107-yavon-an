package com.zt.yavon.module.device.desk.presenter;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.ElectricDayBean;
import com.zt.yavon.module.data.ElectricMonthBean;
import com.zt.yavon.module.device.desk.contract.ElectricityContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class ElectricityPresenter extends ElectricityContract.Presenter {

    @Override
    public void getMonthPower(String machine_id) {
        mRxManage.add(Api.getMonthPower(SPUtil.getToken(mContext),machine_id)
                .subscribeWith(new RxSubscriber<List<ElectricMonthBean>>(mContext,true) {
                    @Override
                    protected void _onNext(List<ElectricMonthBean> list) {
                        mView.returnMonthList(list);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void getDayPower(String machine_id) {
        mRxManage.add(Api.getDayPower(SPUtil.getToken(mContext),machine_id)
                .subscribeWith(new RxSubscriber<List<ElectricDayBean>>(mContext,true) {
                    @Override
                    protected void _onNext(List<ElectricDayBean> list) {
                        mView.returnDayList(list);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
