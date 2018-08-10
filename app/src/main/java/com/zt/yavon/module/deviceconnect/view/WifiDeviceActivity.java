package com.zt.yavon.module.deviceconnect.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.base.utils.LoadingDialog;
import com.common.base.utils.LogUtil;
import com.common.base.utils.NetWorkUtils;
import com.common.base.utils.ToastUtil;
import com.espressif.iot.esptouch.EsptouchTask;
import com.espressif.iot.esptouch.IEsptouchResult;
import com.espressif.iot.esptouch.IEsptouchTask;
import com.espressif.iot.esptouch.util.ByteUtil;
import com.espressif.iot.esptouch.util.EspNetUtil;
import com.tuya.smart.android.device.utils.WiFiUtil;
import com.tuya.smart.android.user.api.ILoginCallback;
import com.tuya.smart.android.user.api.IRegisterCallback;
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

import java.lang.ref.WeakReference;
import java.util.List;

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
    private EsptouchAsyncTask mTask;
    private String defaultPwd;
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
            dialog = DialogUtil.createWifiDialog(this, ssid,defaultPwd,new DialogUtil.OnComfirmListening2() {
                @Override
                public void confirm(String pwd) {
//                 linSearchNothing.setVisibility(View.VISIBLE);
                    defaultPwd = pwd;
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
//                        TuyaUser.getUserInstance().loginWithUid("86",  bean.getMobile(), bean.getPwd(), new ILoginCallback() {
                        TuyaUser.getUserInstance().loginWithUid("86",  "15556092750", "1111111", new ILoginCallback() {
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
        }else if(Constants.MACHINE_TYPE_ADJUST_TABLE.equals(typeData.type)){
            WifiInfo info = ((WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE)).getConnectionInfo();
            if(mTask != null) {
                mTask.cancelEsptouch();
            }
            mTask = new EsptouchAsyncTask(this);
            byte[] ssid = ByteUtil.getBytesByString(WiFiUtil.getCurrentSSID(this));
            byte[] password = ByteUtil.getBytesByString(pwd);
            byte [] bssid = EspNetUtil.parseBssid2bytes(info.getBSSID());
            mTask.execute(ssid, bssid, password, "0".getBytes());
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
                dialog = DialogUtil.createInfoDialogWithListener(WifiDeviceActivity.this, "配网成功", new DialogUtil.OnComfirmListening() {
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
    }

    @OnClick({R.id.tv_scan, R.id.tv_disconnect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_scan:
                initDialog();
                break;
            case R.id.tv_disconnect:
//                WebviewActivity.start(this,"www.baidu.com");
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
    private static class EsptouchAsyncTask extends AsyncTask<byte[], Void, List<IEsptouchResult>> {
        private WeakReference<WifiDeviceActivity> mActivity;

        // without the lock, if the user tap confirm and cancel quickly enough,
        // the bug will arise. the reason is follows:
        // 0. task is starting created, but not finished
        // 1. the task is cancel for the task hasn't been created, it do nothing
        // 2. task is created
        // 3. Oops, the task should be cancelled, but it is running
        private final Object mLock = new Object();
        private IEsptouchTask mEsptouchTask;

        EsptouchAsyncTask(WifiDeviceActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        void cancelEsptouch() {
            WifiDeviceActivity activity = mActivity.get();
            cancel(true);
            if(activity != null)
            DialogUtil.dismiss(activity.dialog);
            if (mEsptouchTask != null) {
                mEsptouchTask.interrupt();
            }
        }

        @Override
        protected void onPreExecute() {
            WifiDeviceActivity activity = mActivity.get();
            if(activity != null){
                DialogUtil.dismiss(activity.dialog);
                activity.dialog = LoadingDialog.showDialogForLoading(activity, "配网中...", true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        synchronized (mLock) {
//                        if (__IEsptouchTask.DEBUG) {
//                            Log.i(TAG, "progress dialog back pressed canceled");
//                        }
                            if (mEsptouchTask != null) {
                                mEsptouchTask.interrupt();
                            }
                        }
                    }
                });
            }
        }

        @Override
        protected List<IEsptouchResult> doInBackground(byte[]... params) {
            WifiDeviceActivity activity = mActivity.get();
            int taskResultCount;
            synchronized (mLock) {
                // !!!NOTICE
                byte[] apSsid = params[0];
                byte[] apBssid = params[1];
                byte[] apPassword = params[2];
                byte[] deviceCountData = params[3];
                taskResultCount = deviceCountData.length == 0 ? -1 : Integer.parseInt(new String(deviceCountData));
                Context context = activity.getApplicationContext();
                mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword, null, context);
//                mEsptouchTask.setEsptouchListener(activity.myListener);
            }
            return mEsptouchTask.executeForResults(taskResultCount);
        }

        @Override
        protected void onPostExecute(List<IEsptouchResult> result) {
            WifiDeviceActivity activity = mActivity.get();
            if(activity == null){
                return;
            }
            DialogUtil.dismiss(activity.dialog);
            if (result == null) {
                activity.dialog = DialogUtil.createInfoDialogWithListener(activity, "配网失败,请重试", new DialogUtil.OnComfirmListening() {
                    @Override
                    public void confirm() {
                        activity.initDialog();
                    }
                });
                return;
            }

            IEsptouchResult firstResult = result.get(0);
            // check whether the task is cancelled and no results received
            if (!firstResult.isCancelled()) {
                int count = 0;
                // max results to be displayed, if it is more than maxDisplayCount,
                // just show the count of redundant ones
                final int maxDisplayCount = 5;
                // the task received some results including cancelled while
                // executing before receiving enough results
                if (firstResult.isSuc()) {
                    StringBuilder sb = new StringBuilder();
//                    for (IEsptouchResult resultInList : result) {
//                        sb.append("Esptouch success, bssid = ")
//                                .append(resultInList.getBssid())
//                                .append(", InetAddress = ")
//                                .append(resultInList.getInetAddress().getHostAddress())
//                                .append("\n");
//                        count++;
//                        if (count >= maxDisplayCount) {
//                            break;
//                        }
//                    }
//                    if (count < result.size()) {
//                        sb.append("\nthere's ")
//                                .append(result.size() - count)
//                                .append(" more result(s) without showing\n");
//                    }
                    if(result.size() > 0){
                        activity.dialog = DialogUtil.createInfoDialogWithListener(activity, "配网成功", new DialogUtil.OnComfirmListening() {
                            @Override
                            public void confirm() {
                                String bssid = result.get(0).getBssid();
                                activity.typeData.sn = bssid;
                                DeviceTypeActivity.start(activity,activity.typeData);
                                LogUtil.d("============onActiveSuccess,token:"+bssid);
                                activity.finish();
                            }
                        });

                    }else{
                        activity.dialog = DialogUtil.createInfoDialogWithListener(activity, "配网失败,请重试", new DialogUtil.OnComfirmListening() {
                            @Override
                            public void confirm() {
                                activity.initDialog();
                            }
                        });
                    }
                } else {
                    if (result == null) {
                        activity.dialog = DialogUtil.createInfoDialogWithListener(activity, "配网失败,请重试", new DialogUtil.OnComfirmListening() {
                            @Override
                            public void confirm() {
                                activity.initDialog();
                            }
                        });
                    }
                }
            }

            activity.mTask = null;
        }
    }
}
