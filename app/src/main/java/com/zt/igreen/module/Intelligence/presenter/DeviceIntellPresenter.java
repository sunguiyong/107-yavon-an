package com.zt.igreen.module.Intelligence.presenter;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.util.Log;
import android.view.View;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.LoadingDialog;
import com.common.base.utils.LogUtil;
import com.common.base.utils.ToastUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zt.igreen.module.Intelligence.contract.DeviceIntellContract;
import com.zt.igreen.module.data.DevDetailBean;
import com.zt.igreen.module.data.IntellAddBean;
import com.zt.igreen.module.data.TabBean;
import com.zt.igreen.module.main.frame.contract.DeviceContract;
import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.Constants;
import com.zt.igreen.utils.DialogUtil;
import com.zt.igreen.utils.PackageUtil;
import com.zt.igreen.utils.SPUtil;
import com.zt.igreen.utils.TuYaLampSDK;
import com.zt.igreen.utils.YisuobaoSDK;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hp on 2018/6/13.
 */

public class DeviceIntellPresenter extends DeviceIntellContract.Presenter {
    private Dialog dialog;
    @Override
    public void getRoomList(String machine_id) {
        mRxManage.add(Api.getAddDO(machine_id,SPUtil.getToken(mContext))
                .subscribeWith(new RxSubscriber<List<IntellAddBean>>(mContext,true) {
                    @Override
                    protected void _onNext(List<IntellAddBean> list) {
                        mView.returnRoomList(list);
                    }
                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext,message);
                    }
                }).getDisposable());
    }
}
