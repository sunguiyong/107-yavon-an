package com.zt.igreen.module.device.health.presenter;

import com.common.base.rx.BaseResponse;
import com.zt.igreen.module.data.HistoryBean;
import com.zt.igreen.module.data.MoreBean;
import com.zt.igreen.module.device.health.contract.HistoryContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.SPUtil;

import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class HistoryPresenter extends HistoryContract.Presenter {
    @Override
    public void getMore(String date, String machine_id, String type, boolean showLoading) {
        mRxManage.add(Api.getSearchResult(SPUtil.getToken(mContext),date,machine_id,type)
                .subscribeWith(new RxSubscriber<List<HistoryBean>>(mContext, true) {
                    @Override
                    protected void _onNext(List<HistoryBean> bean) {
                        mView.returnHistory(bean);
                    }

                    @Override
                    protected void _onError(String message) {
                    }
                }).getDisposable());
    }
}
