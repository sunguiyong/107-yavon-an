package com.zt.igreen.module.main.frame.presenter;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.common.base.rx.BaseResponse;
import com.common.base.utils.LoadingDialog;
import com.common.base.utils.LogUtil;
import com.common.base.utils.ToastUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zt.igreen.module.data.DevDetailBean;
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

public class DevicePresenter extends DeviceContract.Presenter {
    private Dialog dialog;
    private Handler handlerone;
    private Handler handler;

    @Override
    public void getRoomList() {
        mRxManage.add(Api.getRoomList(SPUtil.getToken(mContext), "REMOVE_MACHINE")
                .subscribeWith(new RxSubscriber<List<TabBean>>(mContext, true) {
                    @Override
                    protected void _onNext(List<TabBean> list) {
                        mView.returnRoomList(list);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext, message);
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
                mRxManage.add(Api.deleteDevice(SPUtil.getToken(mContext), getIds(beans))
                        .subscribeWith(new RxSubscriber<BaseResponse>(mContext, true) {
                            @Override
                            protected void _onNext(BaseResponse response) {
                                mView.deleteSuccess(beans);
                            }

                            @Override
                            protected void _onError(String message) {
                                ToastUtil.showShort(mContext, message);
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
        mRxManage.add(Api.setOften(SPUtil.getToken(mContext), getIds(beans))
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext, true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.setOftenSuccess(beans);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext, message);
                    }
                }).getDisposable());
//            }
//        });
    }

    @Override
    public void moveDev(List<TabBean.MachineBean> beans, String roomId) {
        mRxManage.add(Api.moveDev(SPUtil.getToken(mContext), getIds(beans), roomId)
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext, true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.moveSuccess(beans);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext, message);
                    }
                }).getDisposable());
    }

    @Override
    public void renameDev(List<TabBean.MachineBean> beans) {
        if (beans.isEmpty()) {
            ToastUtil.showLong(mContext, "未选择设备");
            return;
        }
        if (beans.size() > 1) {
            ToastUtil.showLong(mContext, "只能选择一个设备重命名");
            return;
        }
        TabBean.MachineBean machineBean = beans.get(0);
        dialog = DialogUtil.createEtDialog(mContext, true, "重命名", machineBean.name, new DialogUtil.OnComfirmListening2() {
            @Override
            public void confirm(String data) {
                mRxManage.add(Api.renameDev(SPUtil.getToken(mContext), beans.get(0).id + "", data)
                        .subscribeWith(new RxSubscriber<DevDetailBean>(mContext, true) {
                            @Override
                            protected void _onNext(DevDetailBean bean) {
                                DialogUtil.dismiss(dialog);
                                machineBean.name = data;
                                mView.renameSuccess(machineBean);
                            }

                            @Override
                            protected void _onError(String message) {
                                ToastUtil.showShort(mContext, message);
                            }
                        }).getDisposable());
            }
        });
    }

    @Override
    public void uploadFault(List<TabBean.MachineBean> beans) {
        if (beans.isEmpty()) {
            ToastUtil.showLong(mContext, "未选择设备");
            return;
        }
        if (beans.size() > 1) {
            ToastUtil.showLong(mContext, "只能选择一个设备共享");
            return;
        }
        TabBean.MachineBean machineBean = beans.get(0);
        if ("ADMIN".equals(machineBean.user_type)) {
            ToastUtil.showLong(mContext, "您已是管理员，无需上报");
            return;
        }
        dialog = DialogUtil.createEtDialog(mContext, true, "上报故障", "请填写上报内容", new DialogUtil.OnComfirmListening2() {
            @Override
            public void confirm(String data) {
                mRxManage.add(Api.uploadFault(SPUtil.getToken(mContext), machineBean.id + "", data)
                        .subscribeWith(new RxSubscriber<BaseResponse>(mContext, true) {
                            @Override
                            protected void _onNext(BaseResponse response) {
                                DialogUtil.dismiss(dialog);
                                mView.uploadFaultSuccess();
                            }

                            @Override
                            protected void _onError(String message) {
                                ToastUtil.showShort(mContext, message);
                            }
                        }).getDisposable());
            }
        });

    }

    @Override
    public void removeOften(List<TabBean.MachineBean> beans) {
        mRxManage.add(Api.removeOften(SPUtil.getToken(mContext), getIds(beans))
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext, true) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                        mView.removeOftenSuccess(beans);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext, message);
                    }
                }).getDisposable());
    }

    public String getIds(List<TabBean.MachineBean> beans) {
        StringBuilder sb = new StringBuilder();
        for (TabBean.MachineBean bean : beans) {
            sb.append(bean.id).append(",");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public void onDestroy() {
        DialogUtil.dismiss(dialog);
        super.onDestroy();
        handler.removeMessages(0);
        handlerone.removeMessages(0);
    }

    public void switchDevice(View view, boolean isChecked, TabBean.MachineBean bean) {
        handlerone = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        DialogUtil.dismiss(dialog);
                        break;
                }
            }
        };
        switch (bean.machine_type) {
            case Constants.MACHINE_TYPE_LIGHT:
                dialog = LoadingDialog.showDialogForLoading(mContext, "操作中...", true, null);
                handlerone.sendEmptyMessageDelayed(0, 50000);
                TuYaLampSDK tuYaLampSDK = new TuYaLampSDK(bean.light_device_id, new TuYaLampSDK.TuYaListener() {
                    @Override
                    public void onSwitchChanged(boolean isOn) {
                        DialogUtil.dismiss(dialog);
                        view.setSelected(isOn);
                        handlerone.removeMessages(0);
                        reportServerDevSwitch(bean.id + "", isOn);
                    }
                });
                tuYaLampSDK.switchLamp(isChecked);
//                tuYaLampSDK.release();
                break;
            case Constants.MACHINE_TYPE_BATTERY_LOCK:
                switchBatteryLock(view, isChecked, bean);
                break;
        }

    }

    private void switchBatteryLock(View view, boolean isChecked, TabBean.MachineBean bean) {
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            BluetoothAdapter.getDefaultAdapter().enable();
        } else {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 0:
                            DialogUtil.dismiss(dialog);
                            break;
                    }
                }
            };
            AndPermission.with(mContext)
                    .runtime()
                    .permission(Permission.Group.LOCATION)
                    .onGranted(permissions -> {
                        DialogUtil.dismiss(dialog);
                        dialog = LoadingDialog.showDialogForLoading(mContext, "操作中...", true, null);
                        handler.sendEmptyMessageDelayed(0, 50000);
                        YisuobaoSDK yisuobaoSDK = new YisuobaoSDK(mContext, bean.asset_number, bean.password, new YisuobaoSDK.YisuobaoListener() {
                            @Override
                            public void onLockStateChanged(boolean isOpen) {
                                DialogUtil.dismiss(dialog);
                                view.setSelected(isOpen);
                                handler.removeMessages(0);
                                reportServerDevSwitch(bean.id + "", isOpen);
                            }
                        });
                        yisuobaoSDK.autoLock(bean.auto_lock);
                        yisuobaoSDK.switchLock();
//                yisuobaoSDK.release();
                    })
                    .onDenied(permissions -> {
                        LogUtil.d("=========denied permissions:" + Arrays.toString(permissions.toArray()));
                        DialogUtil.create2BtnInfoDialog(mContext, "需要蓝牙和定位权限，马上去开启?", "取消", "开启", new DialogUtil.OnComfirmListening() {
                            @Override
                            public void confirm() {
                                PackageUtil.startAppSettings(mContext);
                            }
                        });
                    })
                    .start();
        }
    }

    private void reportServerDevSwitch(String machine_id, boolean isOn) {
        mRxManage.add(Api.switchDev(machine_id, SPUtil.getToken(mContext), isOn ? "ON" : "OFF")
                .subscribeWith(new RxSubscriber<BaseResponse>(mContext, false) {
                    @Override
                    protected void _onNext(BaseResponse response) {
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(mContext, message);
                    }
                }).getDisposable());
    }
}
