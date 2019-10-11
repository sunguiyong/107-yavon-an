package com.zt.igreen.module.mall.presenter;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.igreen.module.data.FavoriteBean;
import com.zt.igreen.module.mall.contract.FavoriteContract;
import com.zt.igreen.module.mall.contract.WordContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.SPUtil;

import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class WordPresenter extends WordContract.Presenter {
    @Override
    public void getWord(String material_id,String context, String company_name, String link_name, String mobile) {
        mRxManage.add(Api.getLiuYan(material_id,SPUtil.getToken(mContext),context,company_name,link_name,mobile)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                    @Override
                    protected void _onNext(BaseResponse list) {
                        mView.returnWord();
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
