package com.zt.yavon.module.mine.presenter;

import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.DocBean;
import com.zt.yavon.module.mine.contract.DocContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

/**
 * Created by hp on 2018/6/13.
 */

public class DocPresenter extends DocContract.Presenter {

    @Override
    public void getDoc(String type) {
        mRxManage.add(Api.getDoc(SPUtil.getToken(mContext),type)
                .subscribeWith(new RxSubscriber<DocBean>(mContext,true) {
                    @Override
                    protected void _onNext(DocBean bean) {
                        mView.returnDoc(bean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
