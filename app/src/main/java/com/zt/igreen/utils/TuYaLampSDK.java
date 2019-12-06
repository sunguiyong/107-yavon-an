package com.zt.igreen.utils;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.common.base.utils.LogUtil;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.home.sdk.bean.HomeBean;
import com.tuya.smart.home.sdk.callback.ITuyaHomeResultCallback;
import com.tuya.smart.sdk.api.IDevListener;
import com.tuya.smart.sdk.api.ITuyaDevice;
import com.tuya.smart.sdk.bean.DeviceBean;

import java.util.HashMap;

/**
 * Created by lifujun on 2018/8/9.
 */

public class TuYaLampSDK {
    private final ITuyaDevice mDevice;
    //        private TuyaDevice mDevice;
    private DeviceBean deviceBean;
    public final String STHEME_LAMP_DPID_1 = "1"; //灯开关

    public TuYaLampSDK(String devId, TuYaListener listener) {
        LogUtil.d("=============devId:" + devId);

         mDevice=TuyaHomeSdk.newDeviceInstance(devId);
//        TuyaUser.getDeviceInstance().queryDevList();
//        deviceBean = TuyaUser.getDeviceInstance().getDev(devId);
//        LogUtil.d("=============deviceBean:" + JSONObject.toJSONString(deviceBean));
//        mDevice = new TuyaDevice(devId);

//        iTuyaDevice.registerDevListener(new IDevListener() {
//            @Override
//            public void onDpUpdate(String devId, String dpStr) {
//
//            }
//
//            @Override
//            public void onRemoved(String devId) {
//
//            }
//
//            @Override
//            public void onStatusChanged(String devId, boolean online) {
//
//            }
//
//            @Override
//            public void onNetworkStatusChanged(String devId, boolean status) {
//
//            }
//
//            @Override
//            public void onDevInfoUpdate(String devId) {
//
//            }
//        });
        mDevice.registerDevListener(new IDevListener() {
            @Override
            public void onDpUpdate(String devId, String dpStr) {
                //dp数据更新:devId 和相应dp数据
                LogUtil.d("=======dpstr:" + dpStr);
                if (!TextUtils.isEmpty(dpStr) && listener != null) {
                    listener.onSwitchChanged(dpStr.contains("true"));
                }
            }

            @Override
            public void onRemoved(String devId) {
                //设备被移除
            }

            @Override
            public void onStatusChanged(String devId, boolean online) {
                //设备在线状态，online
            }

            @Override
            public void onNetworkStatusChanged(String devId, boolean status) {
                //网络状态监听
            }

            @Override
            public void onDevInfoUpdate(String devId) {
                //设备信息变更，目前只有设备名称变化，会调用该接口
            }
        });
    }

    public DeviceBean getDeviceBean() {
        return deviceBean;
    }

    /**
     * 开灯指令
     */
    private void openLamp() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(STHEME_LAMP_DPID_1, true);
        mDevice.publishDps(JSONObject.toJSONString(hashMap), null);
    }

    /**
     * 关灯指令
     */
    private void closeLamp() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(STHEME_LAMP_DPID_1, false);
        mDevice.publishDps(JSONObject.toJSONString(hashMap), null);
    }

    public void release() {
        if (mDevice != null) {
            mDevice.unRegisterDevListener();
        }
    }

    public void switchLamp(boolean isOn) {
        if (isOn) {
            openLamp();
        } else {
            closeLamp();
        }
    }

    public static class TuYaListener {
        public void onSwitchChanged(boolean isOn) {
        }
    }
}
