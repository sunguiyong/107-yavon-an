package com.zt.igreen.module.device.PAU.view;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.api.GizWifiSDK;
import com.gyf.barlibrary.ImmersionBar;
import com.zt.igreen.R;
import com.zt.igreen.component.BaseActivity;
import com.zt.igreen.module.data.TabBean;
import com.zt.igreen.module.data.WeatherBean;
import com.zt.igreen.module.data.XinfengjiBean;
import com.zt.igreen.module.device.PAU.contract.PauContract;
import com.zt.igreen.module.device.PAU.presenter.PauPresenter;
import com.zt.igreen.module.device.desk.view.CustomView;
import com.zt.igreen.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by lifujun on 2018/7/10.
 */

public class PauNewActivity extends BaseActivity<PauPresenter> implements PauContract.View {
    int x = R.layout.activity_pau_new;
    @BindView(R.id.co2nongdu)
    TextView co2nongdu;
    @BindView(R.id.wendu)
    TextView wendu;
    @BindView(R.id.shidu)
    TextView shidu;
    @BindView(R.id.pm)
    TextView pm;
    @BindView(R.id.devicename)
    TextView devicename;
    //    @BindView(R.id.customView_id)
//    CustomView customViewId;
//    @BindView(R.id.seekbar)
//    SeekBar seekbar;
//    @BindView(R.id.img_switch)
//    ImageView imgSwitch;
//    @BindView(R.id.tv_switch)
//    TextView tvSwitch;
//    @BindView(R.id.lin_switch)
//    LinearLayout linSwitch;
//    @BindView(R.id.img_Intelligence)
//    ImageView imgIntelligence;
//    @BindView(R.id.tv_Intelligence)
//    TextView tvIntelligence;
//    @BindView(R.id.lin_Intelligence)
//    LinearLayout linIntelligence;
//    @BindView(R.id.img_gear)
//    ImageView imgGear;
//    @BindView(R.id.tv_gear)
//    TextView tvGear;
//    @BindView(R.id.lin_gear)
//    LinearLayout linGear;
//    @BindView(R.id.lin_dian)
//    LinearLayout linDian;
    @BindView(R.id.switch_img)
    ImageView switchImg;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_shidu)
    TextView tvShidu;
    @BindView(R.id.tv_wendu)
    TextView tvWendu;
    @BindView(R.id.tv_PM)
    TextView tvPM;
    @BindView(R.id.levela_ll)
    LinearLayout la;
    @BindView(R.id.level1_ll)
    LinearLayout l1;
    @BindView(R.id.level2_ll)
    LinearLayout l2;
    @BindView(R.id.level3_ll)
    LinearLayout l3;
    //    @BindView(R.id.tv_device_shidu)
//    TextView tvDeviceShidu;
//    @BindView(R.id.tv_device_wendu)
//    TextView tvDeviceWendu;
//    @BindView(R.id.tv_device_co)
//    TextView tvDeviceCo;
//    @BindView(R.id.tv_PMSText)
//    TextView tvPMSText;
//    @BindView(R.id.tv_PMState)
//    TextView tvPMState;

    private boolean isLevelSelected = true;
    private int statusLevel = 0;
    private TabBean.MachineBean machineBean;
    GizWifiDevice mDevice = null;
    private GizWifiSDK mSDKInstance;
    private List<GizWifiDevice> gizWifiDevice;
    GizWifiDevice mDevice_two = null;
    private final int GET_WEhTER = 1;
    ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
    boolean bool_switch = false, bool_Intelligence = false, bool_gear = false;
    private List<GizWifiDevice> wifidevices = new ArrayList<>();
    private GizWifiSDK gizWifiSDK;
    String status = null;
    private final int GET_WEhData = 2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pau_new;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        machineBean = (TabBean.MachineBean) getIntent().getSerializableExtra("machineBean");

    }

    @Override
    public void initView() {
        devicename.setText(machineBean.name + "");

        Log.d("machinebean", machineBean.device_id + "--" + machineBean.online_status + "" + machineBean.status);
        if (machineBean.status.equals("BOOT_UP")) {
            switchImg.setImageResource(R.drawable.pauswitch);
        }
        EventBus.getDefault().register(this);
//        /*gizWifiSDK = GizWifiSDK.sharedInstance();
//        wifidevices= gizWifiSDK.getDeviceList();
//       // gizWifiSDK.setListener(mListener_user);
//        gizWifiSDK.userLoginAnonymous();
//        gizWifiSDK.setListener(mListener1);*/
//        customViewId.startAnimation();
        sethead(R.color.touming);
        setRightMenuImage(R.mipmap.more_right);
        ImmersionBar.with(this).statusBarColor(R.color.touming).statusBarDarkFont(false).flymeOSStatusBarFontColor(R.color.touming).init();
        setColor(Color.parseColor("#ffffff"));
//        setTitle(machineBean.name);
//        linDian.setVisibility(View.VISIBLE);
    }

    public void onEventMainThread(XinfengjiBean.DataBean bean) {
        if (bean.isOnoff()) {
            switchImg.setImageResource(R.drawable.pauswitch);
        } else {
            switchImg.setImageResource(R.mipmap.xinfengjiswitch_close);
        }
        if (bean.getFsval() == 0) {
            levelChange(1);
        }
        if (bean.getFsval() == 1) {
            levelChange(2);
        }
        if (bean.getFsval() == 2) {
            levelChange(3);
        }
        if (bean.getFsval() == 3) {
            levelChange(0);
        }
        int ico2 = bean.getIco2();
        if (co2nongdu != null) {
            co2nongdu.setText(ico2 + "");
        }
        int itm = bean.getItm();
        if (wendu != null)
            wendu.setText(itm + "");
        int irh = bean.getIrh();
        if (shidu != null)
            shidu.setText(irh + "");
        int ipm2d5 = bean.getIpm2d5();
        if (pm != null)
            pm.setText(ipm2d5 + "");
//        if (ipm2d5 < 50) {
//            if (tvPMSText!=null)
//                tvPMState.setText("优");
//        } else if (ipm2d5 > 50 && ipm2d5 < 151) {
//            if (tvPMSText!=null)
//                tvPMState.setText("良");
//        } else if (ipm2d5 > 150) {
//            if (tvPMSText!=null)
//                tvPMState.setText("差");
//        }
        mPresenter.getUpload_record(machineBean.id + "", irh + "", ipm2d5 + "", itm + "", ico2 + "");
    }

    public static void startAction(Context context, TabBean.MachineBean machineBean) {
        Intent intent = new Intent(context, PauNewActivity.class);
        intent.putExtra("machineBean", machineBean);
        context.startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getWeather(SPUtil.getLocation(PauNewActivity.this, "loaction"));
    }


//    @OnClick({R.id.lin_dian,R.id.lin_switch, R.id.lin_Intelligence, R.id.lin_gear})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.lin_dian:
//             MoreActivity.startAction(this, machineBean);
//                break;
//            case R.id.lin_switch:
//                if (bool_switch == false) {
//                    status = "BOOT_UP";
//                    bool_switch = true;
//                    tvSwitch.setTextColor(Color.parseColor("#88B826"));
//                    imgSwitch.setImageResource(R.mipmap.switch_select);
//                } else if (bool_switch == true) {
//                    status = "SHUTDOWN";
//                    bool_switch = false;
//                    tvSwitch.setTextColor(Color.parseColor("#000000"));
//                    imgSwitch.setImageResource(R.mipmap.switch_noselect);
//                }
//                mPresenter.getDetail(machineBean.id + "", status);
//                break;
//            case R.id.lin_Intelligence:
//                status = "SMART";
//                if (bool_Intelligence == false) {
//                    bool_Intelligence = true;
//                    tvIntelligence.setTextColor(Color.parseColor("#88B826"));
//                    imgIntelligence.setImageResource(R.mipmap.intelligence_select);
//                } else if (bool_Intelligence == true) {
//                    bool_Intelligence = false;
//                    tvIntelligence.setTextColor(Color.parseColor("#000000"));
//                    imgIntelligence.setImageResource(R.mipmap.intelligence_noselect);
//                }
//                mPresenter.getDetail(machineBean.id + "", status);
//                break;
//            case R.id.lin_gear:
//                if (bool_gear == false) {
//                    bool_gear = true;
//                    tvGear.setTextColor(Color.parseColor("#88B826"));
//                    imgGear.setImageResource(R.mipmap.gear_select);
//                    int progress = seekbar.getProgress();
//                    if (progress > 25 && progress <= 50) {
//                        status = "MID";
//                    } else if (progress > 0 && progress <= 25) {
//                        status = "LOW";
//                    } else if (progress > 50 && progress <= 100) {
//                        status = "HIGH";
//                    }
//                    mPresenter.getDetail(machineBean.id + "", status);
//                } else if (bool_gear == true) {
//                    bool_gear = false;
//                    tvGear.setTextColor(Color.parseColor("#000000"));
//                    imgGear.setImageResource(R.mipmap.gear_noselect);
//                }
//                break;
//        }
//    }


    @Override
    public void returnDetail(String machine_id, String status) {

    }

    @Override
    public void updateWeather(WeatherBean bean) {
        //mPresenter.getWeather(SPUtil.getLocation(HealthActivity.this,"loaction"));
        List<WeatherBean.HeWeather> list = bean.result.HeWeather5;
        if (list != null && !list.isEmpty()) {
            WeatherBean.HeWeather weather = list.get(0);
            tvLocation.setText(SPUtil.getLocation(PauNewActivity.this, "loaction"));
            tvShidu.setText(weather.now.hum + " %");
            tvWendu.setText(weather.now.tmp + " °C");
            tvPM.setText(weather.aqi.city.pm25 + " μg/m3");
            // tvAir.setText("室外空气 "+weather.aqi.city.qlty);
        }
    }

    @Override
    public void reUpload_record(String machine_id, String humidity, String pm, String temperature, String co2) {

    }

    boolean low = false, mid = false, high = false, auto = false;

    @OnClick({R.id.tv_right_header, R.id.switch_img, R.id.levela_ll, R.id.level1_ll, R.id.level2_ll, R.id.level3_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right_header:
                MoreActivity.startAction(this, machineBean);
                break;
            case R.id.switch_img:
                if (bool_switch == false) {
                    status = "BOOT_UP";
                    bool_switch = true;
                } else if (bool_switch == true) {
                    status = "SHUTDOWN";
                    bool_switch = false;
                }
                mPresenter.getDetail(machineBean.id + "", status);
                break;
            case R.id.levela_ll:
                levelChange(0);
//                if (auto == false) {
                status = "SMART";
                mPresenter.getDetail(machineBean.id + "", status);
                auto = true;

//                } else if (auto == true) {
//                    auto = false;
//                }
                break;
            case R.id.level1_ll:
                levelChange(1);
//                if (low == false) {
                status = "LOW";

                mPresenter.getDetail(machineBean.id + "", status);
                low = true;
//                } else if (low == true) {
//                    low = false;
//                }

                break;
            case R.id.level2_ll:
                levelChange(2);
//                if (mid == false) {
                status = "MID";
                mPresenter.getDetail(machineBean.id + "", status);
                mid = true;
//                } else if (mid == true) {
//                    mid = false;
//                }
                break;
            case R.id.level3_ll:
                levelChange(3);
//                if (high == false) {
                status = "HIGH";
                mPresenter.getDetail(machineBean.id + "", status);
                high = true;
//                } else if (high == true) {
//                    high = false;
//                }
                break;
        }
    }

    private void levelChange(int status) {
        switch (status) {
            case 0:
                la.setBackgroundResource(R.mipmap.levelselected);
                l1.setBackgroundResource(R.color.touming);
                l2.setBackgroundResource(R.color.touming);
                l3.setBackgroundResource(R.color.touming);
                break;
            case 1:
                l1.setBackgroundResource(R.mipmap.levelselected);
                la.setBackgroundResource(R.color.touming);
                l2.setBackgroundResource(R.color.touming);
                l3.setBackgroundResource(R.color.touming);
                break;
            case 2:
                l2.setBackgroundResource(R.mipmap.levelselected);
                l1.setBackgroundResource(R.color.touming);
                la.setBackgroundResource(R.color.touming);
                l3.setBackgroundResource(R.color.touming);
                break;
            case 3:
                l3.setBackgroundResource(R.mipmap.levelselected);
                l1.setBackgroundResource(R.color.touming);
                l2.setBackgroundResource(R.color.touming);
                la.setBackgroundResource(R.color.touming);
                break;
        }
    }


}
