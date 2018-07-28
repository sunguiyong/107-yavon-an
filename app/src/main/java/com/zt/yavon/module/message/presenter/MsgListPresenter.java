package com.zt.yavon.module.message.presenter;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.MsgBean;
import com.zt.yavon.module.message.contract.MessageListContract;
import com.zt.yavon.module.message.view.MessageListActivity;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.SPUtil;

import java.util.List;

/**
 * Created by lifujun on 2018/7/27.
 */

public class MsgListPresenter extends MessageListContract.Presenter{

    @Override
    public void getMsgList(int type, int page, int pageSize) {
        if(type == MessageListActivity.TYPE_INTERNAL){
            mRxManage.add(Api.getInternalMsgList(SPUtil.getToken(mContext),page+"", pageSize+"")
                    .subscribeWith(new RxSubscriber<List<MsgBean>>(mContext,true) {
                        @Override
                        protected void _onNext(List<MsgBean> list) {
                            mView.returnDataList(list);
                        }
                        @Override
                        protected void _onError(String message) {
                            mView.returnDataList(null);
                            ToastUtil.showShort(mContext,message);
                        }
                    }).getDisposable());

        }else if(type == MessageListActivity.TYPE_SHARE){

        }else if(type == MessageListActivity.TYPE_SYS){
            mRxManage.add(Api.getSysMsgList(SPUtil.getToken(mContext),page+"", pageSize+"")
                    .subscribeWith(new RxSubscriber<List<MsgBean>>(mContext,true) {
                        @Override
                        protected void _onNext(List<MsgBean> list) {
                            mView.returnDataList(list);
                        }
                        @Override
                        protected void _onError(String message) {
                            mView.returnDataList(null);
                            ToastUtil.showShort(mContext,message);
                        }
                    }).getDisposable());

        }else if(type == MessageListActivity.TYPE_ERROR){
            mRxManage.add(Api.getFaultsMsgList(SPUtil.getToken(mContext),page+"", pageSize+"")
                    .subscribeWith(new RxSubscriber<List<MsgBean>>(mContext,true) {
                        @Override
                        protected void _onNext(List<MsgBean> list) {
                            mView.returnDataList(list);
                        }
                        @Override
                        protected void _onError(String message) {
                            mView.returnDataList(null);
                            ToastUtil.showShort(mContext,message);
                        }
                    }).getDisposable());
        }
    }

    @Override
    public void deleteMsg(int type, List<MsgBean> list) {
        if(list.size() == 0){
            ToastUtil.showShort(mContext,"请选择需要删除的消息");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for(MsgBean bean:list){
            sb.append(bean.getId()).append(",");
        }
        String ids = sb.substring(0,sb.length()-1);
        if(type == MessageListActivity.TYPE_INTERNAL){
            mRxManage.add(Api.deleteInternalMsg(SPUtil.getToken(mContext),ids)
                    .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                        @Override
                        protected void _onNext(BaseResponse response) {
                            mView.deleteSuccess(list);
                        }
                        @Override
                        protected void _onError(String message) {
                            ToastUtil.showShort(mContext,message);
                        }
                    }).getDisposable());

        }else if(type == MessageListActivity.TYPE_SHARE){

        }else if(type == MessageListActivity.TYPE_SYS){
            mRxManage.add(Api.deleteSystemMsg(SPUtil.getToken(mContext),ids)
                    .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                        @Override
                        protected void _onNext(BaseResponse response) {
                            mView.deleteSuccess(list);
                        }
                        @Override
                        protected void _onError(String message) {
                            ToastUtil.showShort(mContext,message);
                        }
                    }).getDisposable());
        }else if(type == MessageListActivity.TYPE_ERROR){
            mRxManage.add(Api.deleteFaultMsg(SPUtil.getToken(mContext),ids)
                    .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                        @Override
                        protected void _onNext(BaseResponse response) {
                            mView.deleteSuccess(list);
                        }
                        @Override
                        protected void _onError(String message) {
                            ToastUtil.showShort(mContext,message);
                        }
                    }).getDisposable());
        }
    }

    @Override
    public void readMsg(int type, MsgBean bean) {
        if(type == MessageListActivity.TYPE_INTERNAL){
            mRxManage.add(Api.readInternalMsg(SPUtil.getToken(mContext),bean.getId())
                    .subscribeWith(new RxSubscriber<BaseResponse>(mContext,false) {
                        @Override
                        protected void _onNext(BaseResponse response) {
                            mView.readSuccess(bean);
                        }
                        @Override
                        protected void _onError(String message) {
                            ToastUtil.showShort(mContext,message);
                        }
                    }).getDisposable());

        }else if(type == MessageListActivity.TYPE_SHARE){

        }else if(type == MessageListActivity.TYPE_SYS){
            mRxManage.add(Api.readSystemMsg(SPUtil.getToken(mContext),bean.getId())
                    .subscribeWith(new RxSubscriber<BaseResponse>(mContext,false) {
                        @Override
                        protected void _onNext(BaseResponse response) {
                            mView.readSuccess(bean);
                        }
                        @Override
                        protected void _onError(String message) {
                            ToastUtil.showShort(mContext,message);
                        }
                    }).getDisposable());
        }else if(type == MessageListActivity.TYPE_ERROR){
            mRxManage.add(Api.readFaultMsg(SPUtil.getToken(mContext),bean.getId())
                    .subscribeWith(new RxSubscriber<BaseResponse>(mContext,false) {
                        @Override
                        protected void _onNext(BaseResponse response) {
                            mView.readSuccess(bean);
                        }
                        @Override
                        protected void _onError(String message) {
                            ToastUtil.showShort(mContext,message);
                        }
                    }).getDisposable());
        }
    }

    @Override
    public void doFaultMsg(MsgBean bean) {
        mRxManage.add(Api.doFaultMsg(bean.getId(),SPUtil.getToken(mContext),"RESOLVED")
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext,true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.doFaultSuccess(bean);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
