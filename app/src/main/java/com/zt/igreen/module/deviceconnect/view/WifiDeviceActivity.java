package com.zt.igreen.module.deviceconnect.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.common.base.rx.BaseResponse;
import com.common.base.rx.RxManager;
import com.common.base.utils.LoadingDialog;
import com.common.base.utils.LogUtil;
import com.common.base.utils.NetWorkUtils;
import com.common.base.utils.ToastUtil;
import com.espressif.iot.esptouch.EsptouchTask1;
import com.espressif.iot.esptouch.IEsptouchResult1;
import com.espressif.iot.esptouch.IEsptouchTask1;
import com.espressif.iot.esptouch.util.ByteUtil1;
import com.espressif.iot.esptouch.util.EspNetUtil1;
import com.gizwits.gizwifisdk.api.GizWifiSDK;
import com.gizwits.gizwifisdk.enumration.GizWifiConfigureMode;
import com.gizwits.gizwifisdk.enumration.GizWifiErrorCode;
import com.gizwits.gizwifisdk.enumration.GizWifiGAgentType;
import com.gizwits.gizwifisdk.listener.GizWifiSDKListener;
import com.gyf.barlibrary.ImmersionBar;
import com.tuya.smart.android.device.utils.WiFiUtil;
import com.tuya.smart.android.user.api.ILoginCallback;
import com.tuya.smart.android.user.bean.User;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.home.sdk.bean.HomeBean;
import com.tuya.smart.home.sdk.builder.ActivatorBuilder;
import com.tuya.smart.home.sdk.callback.ITuyaGetHomeListCallback;
import com.tuya.smart.home.sdk.callback.ITuyaHomeResultCallback;
import com.tuya.smart.sdk.api.IResultCallback;
import com.tuya.smart.sdk.api.ITuyaActivator;
import com.tuya.smart.sdk.api.ITuyaActivatorGetToken;
import com.tuya.smart.sdk.api.ITuyaSmartActivatorListener;
import com.tuya.smart.sdk.bean.DeviceBean;
import com.tuya.smart.sdk.enums.ActivatorModelEnum;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.data.BindTuyaHome;
import com.zt.igreen.module.data.DataSave;
import com.zt.igreen.module.data.DevTypeBean;
import com.zt.igreen.module.data.LoginBean;

import com.zt.igreen.network.Api;
import com.zt.igreen.network.RxSubscriber;
import com.zt.igreen.utils.Constants;
import com.zt.igreen.utils.DialogUtil;
import com.zt.igreen.utils.SPUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.inuker.bluetooth.library.utils.BluetoothUtils.getContext;

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
    private GizWifiSDK mSDKInstance;

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
        mSDKInstance = GizWifiSDK.sharedInstance();
        mSDKInstance.stopDeviceOnboarding();
        sethead(R.color.touming);
        setColor(Color.parseColor("#000000"));
        ImmersionBar.with(this).statusBarColor(R.color.touming).statusBarDarkFont(false).flymeOSStatusBarFontColor(R.color.touming).init();
        setColor(Color.parseColor("#ffffff"));
        setTitle("连接设备");
        setRightMenuText("说明书");
        setRightMenuTopImage(R.mipmap.iv_explan, 12);
        initDialog();
    }

    long tuyahomeid;
    private List<String> roomLists = new ArrayList<>();

    /**
     * 检验tuyaUID下是否有家庭
     */
    private void checkTuyaHome(String mobile) {
        roomLists.add(0, "room");
        TuyaHomeSdk.getHomeManagerInstance().queryHomeList(new ITuyaGetHomeListCallback() {
            @Override
            public void onSuccess(List<HomeBean> homeBeans) {
                if (homeBeans.size() > 0) {//有家庭
                    Log.d("queryHomeList", homeBeans.get(0).getHomeId() + "");
                    Log.d("queryHomeListName", homeBeans.get(0).getName() + "");
//                    Log.d("HomeBeansDevice", homeBeans.get(0).getDeviceList().get(0).getDevId()+"");
                    tuyahomeid = homeBeans.get(0).getHomeId();
                    DataSave.tuyaHomeId = tuyahomeid + "";
                    //获取tuyatoken
                    tuya(null, defaultPwd, tuyahomeid);
//                    uploadHomeId(tuyahomeid + "");

                } else {//无家庭，创建家庭
                    TuyaHomeSdk.getHomeManagerInstance().createHome("homeHasPrefix" + mobile,
                            0, 0, "苏州", roomLists, new ITuyaHomeResultCallback() {
                                @Override
                                public void onSuccess(HomeBean bean) {
                                    Log.d("createHome", bean.getHomeId() + "");
                                    tuyahomeid = bean.getHomeId();
                                    tuya(null, defaultPwd, tuyahomeid);
                                    //todo 调用上传homeid的接口
                                    uploadHomeId(tuyahomeid + "");
                                }

                                @Override
                                public void onError(String errorCode, String errorMsg) {
                                    Log.d("createHome", errorCode + "---" + errorMsg);
                                }
                            });
                }
            }

            @Override
            public void onError(String errorCode, String error) {
                Log.i("queryHomeList", "onError: " + errorCode + "---" + error);
            }
        });
    }

    private void initDialog() {
        String ssid = NetWorkUtils.getConnectWifiSsid(this);
        if (NetWorkUtils.isWifiConnected(this) && ssid != null) {
            ssid = ssid.replaceAll("\"", "");
            DialogUtil.dismiss(dialog);
            dialog = DialogUtil.createWifiDialog(this, ssid, defaultPwd, new DialogUtil.OnComfirmListening2() {
                @Override
                public void confirm(String pwd) {
//                 linSearchNothing.setVisibility(View.VISIBLE);
                    defaultPwd = pwd;
                    try {
                        choosePeiWangType(defaultPwd);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            DialogUtil.dismiss(dialog);
            dialog = DialogUtil.create2BtnInfoDialog(this, "目前手机没有连接Wi-Fi", "取消", "去连接", new DialogUtil.OnComfirmListening() {
                @Override
                public void confirm() {
                    startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                }
            });
        }
    }

    /**
     * BLUE_LOCK蓝牙锁 BATTERY_LOCK电池锁 LIGHT灯 ADJUST_TABLE升降桌
     */
    private void choosePeiWangType(final String pwd) {
        if (Constants.MACHINE_TYPE_LIGHT.equals(typeData.type)) {
            if (!TextUtils.isEmpty(accessToken)) {
                tuyaPeiwang(accessToken, pwd);
                return;
            }
            LoginBean bean = SPUtil.getAccount(this);
            LogUtil.d("============params,account:" + bean.getAccount() + ",mobile:" + bean.getMobile() + ",pwd:" + bean.getPwd());
            TuyaHomeSdk.getUserInstance().loginOrRegisterWithUid("86",
                    "tuyayavon" + bean.getMobile(),
                    "tuyayavon123456",
                    new ILoginCallback() {
                        @Override
                        public void onSuccess(User user) {
                            Log.d("loginOrRegisterWithUid", user.getUsername());
                            Toast.makeText(getApplicationContext(), user.getUsername() + "", Toast.LENGTH_LONG).show();
                            checkTuyaHome(bean.getMobile());
                        }

                        @Override
                        public void onError(String code, String error) {
                            Log.d("loginOrRegisterWithUid", code + "---" + error);
                            Toast.makeText(getApplicationContext(), code + "---" + error, Toast.LENGTH_LONG).show();
                        }
                    });
//            TuyaUser.getUserInstance().registerAccountWithUid("86", bean.getMobile(), bean.getPwd(), new IRegisterCallback() {
//                @Override
//                public void onSuccess(User user) {
//                    LogUtil.d("============registerAccountWithPhone,user:" + user.toString());
//                    tuya(user, pwd);
//                }
//
//                @Override
//                public void onError(String msg, String msg2) {
//                    //账号已存在
//                    if ("IS_EXISTS".equals(msg)) {//登录
//                        LogUtil.d("============registerAccountWithPhone,error:账号已经存在");
////                        TuyaUser.getUserInstance().loginWithUid("86",  bean.getMobile(), bean.getPwd(), new ILoginCallback() {
//                        TuyaUser.getUserInstance().loginWithUid("86", "15556092750", "1111111", new ILoginCallback() {
//                            @Override
//                            public void onSuccess(User user) {
//                                Log.e("xuxinyi", user + "coed");
//                                tuya(user, pwd);
//                            }
//
//                            @Override
//                            public void onError(String code, String error) {
//                                Log.e("xuxinyi", error + "coed" + code);
//                                ToastUtil.showShort(WifiDeviceActivity.this, error);
//                            }
//                        });
//                    } else {
//                        ToastUtil.showShort(WifiDeviceActivity.this, msg2);
//                    }
//                }
//            });
        } else if (Constants.MACHINE_TYPE_ADJUST_TABLE.equals(typeData.type)) {//智能升降桌
            WifiInfo info = ((WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE)).getConnectionInfo();
            if (mTask != null) {
                mTask.cancelEsptouch();
            }
            mTask = new EsptouchAsyncTask(this);
            byte[] ssid = ByteUtil1.getBytesByString(WiFiUtil.getCurrentSSID(this));
            byte[] password = ByteUtil1.getBytesByString(pwd);
            byte[] bssid = EspNetUtil1.parseBssid2bytes(info.getBSSID());
            mTask.execute(ssid, bssid, password, "0".getBytes());
        } else if (Constants.MACHINE_TYPE_AIR_MACHINE.equals(typeData.type)) {//新风机
            mSDKInstance.setListener(mListener);
            String ssid_one = NetWorkUtils.getConnectWifiSsid(this);
            List<GizWifiGAgentType> types = new ArrayList<GizWifiGAgentType>();
            types.add(GizWifiGAgentType.GizGAgentESP);
            String sub_ssid = ssid_one.substring(1, ssid_one.length() - 1);
            mSDKInstance.setDeviceOnboardingDeploy(sub_ssid, pwd, GizWifiConfigureMode.GizWifiAirLink, null, 60, types, false);
            DialogUtil.dismiss(dialog);
            dialog = LoadingDialog.showDialogForLoading(WifiDeviceActivity.this, "配网中...", true, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
          /*  dialog = DialogUtil.createInfoDialogWithListener(WifiDeviceActivity.this, "正在配网....", new DialogUtil.OnComfirmListening() {
                @Override
                public void confirm() {
                    initDialog();
                }
            });*/
        }
    }

    GizWifiSDKListener mListener = new GizWifiSDKListener() {
        //等待配置完成或超时，回调配置完成接口
        @Override
        public void didSetDeviceOnboarding(GizWifiErrorCode result, String mac, String did, String productKey) {
            // ToastUtil.show(WifiDeviceActivity.this,result+"",1);
            Log.e("xuxinyi", result.toString());
            if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS) {
                DialogUtil.dismiss(dialog);
                dialog = DialogUtil.createInfoDialogWithListener(WifiDeviceActivity.this, "配网成功", new DialogUtil.OnComfirmListening() {
                    @Override
                    public void confirm() {
//                         typeData.sn = deviceBean.getDevId();
                        DeviceTypeActivity.startAction(WifiDeviceActivity.this, typeData, mac);
                        finish();
                    }
                });
            } /*else if (result == GizWifiErrorCode.GIZ_SDK_DEVICE_CONFIG_IS_RUNNING){
                DialogUtil.dismiss(dialog);
                dialog = DialogUtil.createInfoDialogWithListener(WifiDeviceActivity.this, "配网失败", new DialogUtil.OnComfirmListening() {
                    @Override
                    public void confirm() {
                        initDialog();
                    }
                });
            }*/ else {
                // 配置失败

                if (!WifiDeviceActivity.this.isFinishing())//xActivity即为本界面的Activity
                {
                    DialogUtil.dismiss(dialog);
                    dialog = DialogUtil.createInfoDialogWithListener(WifiDeviceActivity.this, "配网失败", new DialogUtil.OnComfirmListening() {
                        @Override
                        public void confirm() {
                            initDialog();
                        }
                    });
                }

            }
        }
    };

    protected ITuyaActivator mTuyaActivator; //集成配网具体实现接口

    /**
     * 获取配网token
     *
     * @param user
     * @param pwd
     */
    private void tuya(User user, String pwd, long homeid) {
        TuyaHomeSdk.getActivatorInstance().getActivatorToken(homeid, new ITuyaActivatorGetToken() {
            @Override
            public void onSuccess(String token) {
                Log.d("getActivatorToken", token + "");
                tuyaPeiwang(token, pwd);
            }

            @Override
            public void onFailure(String errorCode, String errorMsg) {
                Log.d("getActivatorToken", errorCode + "---" + errorMsg);
            }
        });
//        TuyaActivator.getInstance().getActivatorToken(user.getPartnerIdentity(), new ITuyaActivatorGetToken() {
//            @Override
//            public void onSuccess(String token) {
//                accessToken = token;
//                LogUtil.d("============getActivatorToken,token:" + token);
//                tuyaPeiwang(token, pwd);
//            }
//
//            @Override
//            public void onFailure(String msg, String s1) {
//                ToastUtil.showShort(WifiDeviceActivity.this, msg);
//            }
//        });
    }

    /**
     * tuya配网
     *
     * @param token
     * @param pwd
     */
    private void tuyaPeiwang(String token, String pwd) {
        dialog = LoadingDialog.showDialogForLoading(WifiDeviceActivity.this, "配网中...",
                true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });
        /**
         * @param token: 配网所需要的激活key。
         * @param ssid: 配网之后，设备工作WiFi的名称。（家庭网络）
         * @param password: 配网之后，设备工作WiFi的密码。（家庭网络）
         * @param activatorModel: 现在给设备配网有以下两种方式:
        ActivatorModelEnum.TY_EZ: 传入该参数则进行EZ配网
        ActivatorModelEnum.TY_AP: 传入该参数则进行AP配网
         * @param timeout: 配网的超时时间设置，默认是100s.
         * @param context: 需要传入activity的context.
         */
        mTuyaActivator = TuyaHomeSdk.getActivatorInstance().newMultiActivator(new ActivatorBuilder()
                .setActivatorModel(ActivatorModelEnum.TY_EZ)
                .setContext(WifiDeviceActivity.this)
                .setPassword(pwd)
                .setToken(token)
                .setTimeOut(120)
                .setSsid(WiFiUtil.getCurrentSSID(WifiDeviceActivity.this))
                .setListener(new ITuyaSmartActivatorListener() {
                    @Override
                    public void onError(String errorCode, String errorMsg) {
                        Log.e("TuyaSmaAcErr", errorCode + "---" + errorMsg);

                        DialogUtil.dismiss(dialog);
                        dialog = DialogUtil.createInfoDialogWithListener(WifiDeviceActivity.this, "配网超时", new DialogUtil.OnComfirmListening() {
                            @Override
                            public void confirm() {
                                initDialog();
                            }
                        });
                        ToastUtil.showShort(WifiDeviceActivity.this, errorMsg);
                    }

                    @Override
                    public void onActiveSuccess(DeviceBean devResp) {
                        Toast.makeText(getApplicationContext(), "onActiveSu", Toast.LENGTH_LONG).show();
                        Log.d("TuyaSmaAcSuccess", devResp.getDevId());
                        typeData.sn = devResp.getDevId();
                        DialogUtil.dismiss(dialog);
                        dialog = DialogUtil.createInfoDialogWithListener(WifiDeviceActivity.this, "配网成功", new DialogUtil.OnComfirmListening() {
                            @Override
                            public void confirm() {
                                DeviceTypeActivity.start(WifiDeviceActivity.this, typeData);
                                finish();
                            }
                        });
//                        LogUtil.d("============onActiveSuccess,token:" + devResp.toString());
                    }

                    @Override
                    public void onStep(String step, Object data) {

                    }
                }));
        mTuyaActivator.start();

//        iTuyaActivator = TuyaActivator.getInstance().newActivator(new ActivatorBuilder()
//                .setSsid(WiFiUtil.getCurrentSSID(WifiDeviceActivity.this))
//                .setContext(WifiDeviceActivity.this)
//                .setPassword(pwd)
//                .setActivatorModel(ActivatorModelEnum.TY_EZ)//EZ模式
////       .setActivatorModel(ActivatorModelEnum.TY_AP)//AP模式
//                .setTimeOut(100) //unit is seconds 超时时间
//                .setToken(token)
//                .setListener(new ITuyaSmartActivatorListener() {
//                    @Override
//                    public void onError(String s, String s1) {
//                        Log.e("onError", s + "wos" + s1);
//                        iTuyaActivator.stop();
//                        DialogUtil.dismiss(dialog);
//                        dialog = DialogUtil.createInfoDialogWithListener(WifiDeviceActivity.this, "配网超时", new DialogUtil.OnComfirmListening() {
//                            @Override
//                            public void confirm() {
//                                initDialog();
//                            }
//                        });
//                        ToastUtil.showShort(WifiDeviceActivity.this, s);
//                    }
//
//                    @Override
//                    public void onActiveSuccess(DeviceBean deviceBean) {
//                        iTuyaActivator.stop();
//                        DialogUtil.dismiss(dialog);
//                        dialog = DialogUtil.createInfoDialogWithListener(WifiDeviceActivity.this, "配网成功", new DialogUtil.OnComfirmListening() {
//                            @Override
//                            public void confirm() {
//                                typeData.sn = deviceBean.getDevId();
//                                DeviceTypeActivity.start(WifiDeviceActivity.this, typeData);
//                                finish();
//                            }
//                        });
//                        LogUtil.d("============onActiveSuccess,token:" + deviceBean.toString());
//
//                    }
//
//                    @Override
//                    public void onStep(String s, Object o) {
////                ToastUtil.showShort(WifiDeviceActivity.this,s);
//                    }
//                }));
//        iTuyaActivator.start();
    }

    public static void start(Activity activity, DevTypeBean.TYPE typeData) {
        Intent intent = new Intent(activity, WifiDeviceActivity.class);
        intent.putExtra("typeData", typeData);
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

        super.onDestroy();
        if (iTuyaActivator != null) {
            iTuyaActivator.stop();
            iTuyaActivator.onDestroy();
        }
        ax11.clear();
        DialogUtil.dismiss(dialog);

    }

    private static class EsptouchAsyncTask extends AsyncTask<byte[], Void, List<IEsptouchResult1>> {
        private WeakReference<WifiDeviceActivity> mActivity;

        // without the lock, if the user tap confirm and cancel quickly enough,
        // the bug will arise. the reason is follows:
        // 0. task is starting created, but not finished
        // 1. the task is cancel for the task hasn't been created, it do nothing
        // 2. task is created
        // 3. Oops, the task should be cancelled, but it is running
        private final Object mLock = new Object();
        private IEsptouchTask1 mEsptouchTask;

        EsptouchAsyncTask(WifiDeviceActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        void cancelEsptouch() {
            WifiDeviceActivity activity = mActivity.get();
            cancel(true);
            if (activity != null)
                DialogUtil.dismiss(activity.dialog);
            if (mEsptouchTask != null) {
                mEsptouchTask.interrupt();
            }
        }

        @Override
        protected void onPreExecute() {
            WifiDeviceActivity activity = mActivity.get();
            if (activity != null) {
                DialogUtil.dismiss(activity.dialog);
                activity.dialog = LoadingDialog.showDialogForLoading(activity, "配网中...", true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        synchronized (mLock) {
//                        if (__IEsptouchTask1.DEBUG) {
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
        protected List<IEsptouchResult1> doInBackground(byte[]... params) {
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
                mEsptouchTask = new EsptouchTask1(apSsid, apBssid, apPassword, null, context);
//                mEsptouchTask.setEsptouchListener(activity.myListener);
            }
            return mEsptouchTask.executeForResults(taskResultCount);
        }

        @Override
        protected void onPostExecute(List<IEsptouchResult1> result) {
            WifiDeviceActivity activity = mActivity.get();
            if (activity == null) {
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

            IEsptouchResult1 firstResult = result.get(0);
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
//                    for (IEsptouchResult1 resultInList : result) {
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
                    if (result.size() > 0) {
                        activity.dialog = DialogUtil.createInfoDialogWithListener(activity, "配网成功", new DialogUtil.OnComfirmListening() {
                            @Override
                            public void confirm() {
                                String bssid = result.get(0).getBssid();
                                activity.typeData.sn = bssid;
                                DeviceTypeActivity.start(activity, activity.typeData);
                                LogUtil.d("============onActiveSuccess,token:" + bssid);
                                activity.finish();
                            }
                        });

                    } else {
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

    public RxManager ax11 = new RxManager();

    public void uploadHomeId(String ty_family_id) {
        ax11.add(Api.bindTuyaHome(SPUtil.getToken(getApplicationContext()), ty_family_id)
                .subscribeWith(new RxSubscriber<BindTuyaHome>(this, false) {
                    @Override
                    protected void _onNext(BindTuyaHome bean) {
                        Log.d("bindTuyaHome", bean.toString());
                    }

                    @Override
                    protected void _onError(String message) {
                        Log.d("bindTuyaHome_onError", message);
                    }
                }).getDisposable());
    }
}
