package com.zt.yavon.module.deviceconnect.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.common.base.utils.LoadingDialog;
import com.common.base.utils.LogUtil;
import com.common.base.utils.NetWorkUtils;
import com.common.base.utils.ToastUtil;
import com.google.gson.Gson;
import com.tuya.smart.android.common.log.LogBean;
import com.tuya.smart.android.device.utils.WiFiUtil;
import com.tuya.smart.android.user.api.ILoginCallback;
import com.tuya.smart.android.user.api.IRegisterCallback;
import com.tuya.smart.android.user.api.IValidateCallback;
import com.tuya.smart.android.user.bean.User;
import com.tuya.smart.sdk.TuyaActivator;
import com.tuya.smart.sdk.TuyaUser;
import com.tuya.smart.sdk.api.ITuyaActivator;
import com.tuya.smart.sdk.api.ITuyaActivatorGetToken;
import com.tuya.smart.sdk.api.ITuyaSmartActivatorListener;
import com.tuya.smart.sdk.bean.DeviceBean;
import com.tuya.smart.sdk.builder.ActivatorBuilder;
import com.tuya.smart.sdk.enums.ActivatorModelEnum;
import com.zt.yavon.R;
import com.zt.yavon.component.BaseActivity;
import com.zt.yavon.module.data.DevTypeBean;
import com.zt.yavon.module.data.LoginBean;
import com.zt.yavon.module.main.frame.view.WebviewActivity;
import com.zt.yavon.utils.Constants;
import com.zt.yavon.utils.DialogUtil;
import com.zt.yavon.utils.SPUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class WifiDeviceActivity extends BaseActivity {


    @BindView(R.id.tv_scan)
    TextView tvScan;

    @BindView(R.id.tv_disconnect)
    TextView tvDisconnect;
    @BindView(R.id.lin_search_nothing)
    LinearLayout linSearchNothing;
    @BindView(R.id.lin_scan_bluetooth)
    LinearLayout linScanBluetooth;
    private Dialog dialog;
    private DevTypeBean.TYPE typeData;
    private ITuyaActivator iTuyaActivator;
    private String accessToken;
    @Override
    public int getLayoutId() {
        return R.layout.activity_wifi_device;
    }

    @Override
    public void initPresenter() {
        typeData = (DevTypeBean.TYPE) getIntent().getSerializableExtra("typeData");
    }

    @Override
    public void initView() {
        setTitle("连接设备");
        setRightMenuText("说明书");
        setRightMenuTopImage(R.mipmap.iv_explan, 12);
        initDialog();
    }

    private void initDialog() {
        String ssid = NetWorkUtils.getConnectWifiSsid(this);
        if (NetWorkUtils.isWifiConnected(this) && ssid != null){
            ssid = ssid.replaceAll("\"","");
            DialogUtil.dismiss(dialog);
            dialog = DialogUtil.createWifiDialog(this, ssid,new DialogUtil.OnComfirmListening2() {
                @Override
                public void confirm(String pwd) {
//                 linSearchNothing.setVisibility(View.VISIBLE);
                    try {
                        choosePeiWangType(pwd);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }else {
            DialogUtil.dismiss(dialog);
            dialog = DialogUtil.create2BtnInfoDialog(this, "目前手机没有连接Wi-Fi", "取消", "去连接", new DialogUtil.OnComfirmListening() {
                @Override
                public void confirm() {
                 startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                }
            });
        }
    }

    private void choosePeiWangType(final String pwd) {
        if(Constants.MACHINE_TYPE_LIGHT.equals(typeData.type)){
            if(!TextUtils.isEmpty(accessToken)){
                tuyaPeiwang(accessToken,pwd);
                return;
            }
            LoginBean bean = SPUtil.getAccount(this);
            LogUtil.d("============params,account:"+bean.getAccount()+",mobile:"+bean.getMobile()+",pwd:"+bean.getPwd());
            TuyaUser.getUserInstance().registerAccountWithUid("86", bean.getMobile(), bean.getPwd(), new IRegisterCallback() {
                @Override
                public void onSuccess(User user) {
                    LogUtil.d("============registerAccountWithPhone,user:"+user.toString());
                    tuya(user,pwd);
                }

                @Override
                public void onError(String msg, String msg2) {
                    //账号已存在
                    if("IS_EXISTS".equals(msg)){//登录
                        LogUtil.d("============registerAccountWithPhone,error:账号已经存在");
                        TuyaUser.getUserInstance().loginWithUid("86",  bean.getMobile(), bean.getPwd(), new ILoginCallback() {
                            @Override
                            public void onSuccess(User user) {
                                tuya(user,pwd);
                            }

                            @Override
                            public void onError(String code, String error) {
                                ToastUtil.showShort(WifiDeviceActivity.this,error);
                            }
                        });
                    }else{
                        ToastUtil.showShort(WifiDeviceActivity.this,msg2);
                    }
                }
            });
        }
    }
    private void tuya(User user,String pwd){
                    TuyaActivator.getInstance().getActivatorToken(user.getPartnerIdentity(), new ITuyaActivatorGetToken() {
                        @Override
                        public void onSuccess(String token) {
                            accessToken = token;
                            LogUtil.d("============getActivatorToken,token:"+token);
                            tuyaPeiwang(token, pwd);
                        }

                        @Override
                        public void onFailure(String msg, String s1) {
                            ToastUtil.showShort(WifiDeviceActivity.this,msg);
                        }
                    });
    }

    private void tuyaPeiwang(String token, String pwd) {
        dialog = LoadingDialog.showDialogForLoading(WifiDeviceActivity.this, "配网中...", true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        iTuyaActivator = TuyaActivator.getInstance().newActivator(new ActivatorBuilder()
        .setSsid(WiFiUtil.getCurrentSSID(WifiDeviceActivity.this))
        .setContext(WifiDeviceActivity.this)
        .setPassword(pwd)
        .setActivatorModel(ActivatorModelEnum.TY_EZ)//EZ模式
//       .setActivatorModel(ActivatorModelEnum.TY_AP)//AP模式
//        .setTimeOut(60) //unit is seconds 超时时间
        .setToken(token)
        .setListener(new ITuyaSmartActivatorListener() {
            @Override
            public void onError(String s, String s1) {
                iTuyaActivator.stop();
                DialogUtil.dismiss(dialog);
                dialog = DialogUtil.createInfoDialogWithListener(WifiDeviceActivity.this, "配网超时", new DialogUtil.OnComfirmListening() {
                    @Override
                    public void confirm() {
                        initDialog();
                    }
                });
                ToastUtil.showShort(WifiDeviceActivity.this,s);
            }
            @Override
            public void onActiveSuccess(DeviceBean deviceBean) {
                iTuyaActivator.stop();
                DialogUtil.dismiss(dialog);
                dialog = DialogUtil.createInfoDialogWithListener(WifiDeviceActivity.this, "成功", new DialogUtil.OnComfirmListening() {
                    @Override
                    public void confirm() {
                        typeData.sn = deviceBean.getDevId();
                        DeviceTypeActivity.start(WifiDeviceActivity.this,typeData);
                        finish();
                    }
                });
                LogUtil.d("============onActiveSuccess,token:"+deviceBean.toString());

            }

            @Override
            public void onStep(String s, Object o) {
//                ToastUtil.showShort(WifiDeviceActivity.this,s);
            }
        }));
        iTuyaActivator.start();
    }

    public static void start(Activity activity, DevTypeBean.TYPE typeData) {
        Intent intent = new Intent(activity, WifiDeviceActivity.class);
        intent.putExtra("typeData",typeData);
        activity.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDialog();
    }

    @OnClick({R.id.tv_scan, R.id.tv_disconnect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_scan:

                finish();
                break;
            case R.id.tv_disconnect:
                WebviewActivity.start(this,"www.baidu.com");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if(iTuyaActivator != null){
            iTuyaActivator.onDestroy();
        }
        DialogUtil.dismiss(dialog);
        super.onDestroy();
    }
}
