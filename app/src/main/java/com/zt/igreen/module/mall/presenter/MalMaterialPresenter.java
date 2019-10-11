package com.zt.igreen.module.mall.presenter;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.igreen.module.data.IntellListBean;
import com.zt.igreen.module.data.MaterialDetailsBean;
import com.zt.igreen.module.data.MaterialTypeBean;
import com.zt.igreen.module.mall.contract.MalMaterialContract;
import com.zt.igreen.module.mall.contract.MaterialStyleContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.SPUtil;

import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class MalMaterialPresenter extends MalMaterialContract.Presenter {
    @Override
    public void getMalMaterial(String material_id) {
        mRxManage.add(Api.getMaterialDetails(material_id,SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<MaterialDetailsBean>(mContext,true) {
                    @Override
                    protected void _onNext(MaterialDetailsBean list) {
                        mView.returnMalMaterial(list);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void getCollect(String material_id) {
        mRxManage.add(Api.getCollect(material_id,SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                    @Override
                    protected void _onNext(BaseResponse list) {
                        mView.returnCollect();
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
