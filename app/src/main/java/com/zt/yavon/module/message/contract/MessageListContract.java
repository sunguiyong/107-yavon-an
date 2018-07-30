package com.zt.yavon.module.message.contract;

import com.zt.yavon.component.BasePresenter;
import com.zt.yavon.module.data.MsgBean;

import java.util.List;

/**
 * Created by lifujun on 2018/7/27.
 */

public interface MessageListContract {
    interface View{
        void returnDataList(List<MsgBean> list);
        void deleteSuccess(List<MsgBean> list);
        void readSuccess(MsgBean bean);
        void doMsgSuccess(MsgBean bean);
    }
    abstract class Presenter extends BasePresenter<View> {
        public abstract void getMsgList(int type,int page,int pageSize);
        public abstract void deleteMsg(int type,List<MsgBean> list);
        public abstract void readMsg(int type,MsgBean bean);
        public abstract void doFaultMsg(MsgBean bean);
        public abstract void doShareMsg(MsgBean bean,boolean agree);
    }
}
