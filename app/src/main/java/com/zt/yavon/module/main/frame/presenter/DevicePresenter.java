package com.zt.yavon.module.main.frame.presenter;

import android.app.Dialog;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.ToastUtil;
import com.zt.yavon.module.data.DevDetailBean;
import com.zt.yavon.module.data.TabBean;
import com.zt.yavon.module.main.frame.contract.DeviceContract;
import com.zt.yavon.network.Api;
import com.zt.yavon.network.RxSubscriber;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.utils.SPUtil;

import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class DevicePresenter extends DeviceContract.Presenter {
    private Dialog dialog;
    @Override
    public void getRoomList() {
        mRxManage.add(Api.getRoomList(SPUtil.getToken(mContext),"REMOVE_MACHINE")
                .subscribeWith(new RxSubscriber<List<TabBean>>(mContext,true) {
                    @Override
                    protected void _onNext(List<TabBean> list) {
                        mView.returnRoomList(list);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
    @Override
    public void deleteDevice(List<TabBean.MachineBean> beans) {
        if (beans == null || beans.isEmpty()) {
            ToastUtil.showShort(mContext, "未选择设备");
            return;
        }
        dialog = DialogUtil.create2BtnInfoDialog(mContext, "确定删除此设备吗？", null, null, new DialogUtil.OnComfirmListening() {
            @Override
            public void confirm() {
                mRxManage.add(Api.deleteDevice(SPUtil.getToken(mContext),getIds(beans))
                        .subscribeWith(new RxSubscriber<BaseResponse>(mContext, true) {
                            @Override
                            protected void _onNext(BaseResponse response) {
                                mView.deleteSuccess(beans);
                            }

                            @Override
                            protected void _onError(String message) {
                                ToastUtil.showShort(mContext,message);
                            }
                        }).getDisposable());
            }
        });

    }

    @Override
    public void setOften(List<TabBean.MachineBean> beans) {
        if (beans == null || beans.isEmpty()) {
            ToastUtil.showShort(mContext, "未选择设备");
            return;
        }
//        DialogUtil.create2BtnInfoDialog(mContext, "确定删除此设备吗？", null, null, new DialogUtil.OnComfirmListening() {
//            @Override
//            public void confirm() {
                mRxManage.add(Api.setOften(SPUtil.getToken(mContext),getIds(beans))
                        .subscribeWith(new RxSubscriber<BaseResponse>(mContext, true) {
                            @Override
                            protected void _onNext(BaseResponse response) {
                                mView.setOftenSuccess(beans);
                            }

                            @Override
                            protected void _onError(String message) {
                                ToastUtil.showShort(mContext,message);
                            }
                        }).getDisposable());
//            }
//        });
    }

    @Override
    public void moveDev(List<TabBean.MachineBean> beans, String roomId) {
        mRxManage.add(Api.moveDev(SPUtil.getToken(mContext),getIds(beans),roomId)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext, true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.moveSuccess(beans);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    @Override
    public void renameDev(TabBean.MachineBean machineBean, String name) {
        mRxManage.add(Api.renameDev(SPUtil.getToken(mContext),machineBean.id+"",name)
                .subscribeWith(new RxSubscriber<DevDetailBean>(mContext, true) {
                    @Override
                    protected void _onNext(DevDetailBean bean) {
                        machineBean.name = name;
                        mView.renameSuccess(machineBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }

    public String getIds(List<TabBean.MachineBean> beans){
        StringBuilder sb = new StringBuilder();
        for(TabBean.MachineBean bean:beans){
            sb.append(bean.id).append(",");
        }
        if(sb.length() > 0){
            sb.setLength(sb.length()-1);
        }
        return sb.toString();
    }

    @Override
    public void onDestroy() {
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }
}
