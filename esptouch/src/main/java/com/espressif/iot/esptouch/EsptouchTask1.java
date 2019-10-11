package com.espressif.iot.esptouch;

import android.content.Context;
import android.text.TextUtils;

import com.espressif.iot.esptouch.protocol.TouchData;
import com.espressif.iot.esptouch.task.EsptouchTaskParameter1;
import com.espressif.iot.esptouch.task.__EsptouchTask1;
import com.espressif.iot.esptouch.util.EspAES;
import com.espressif.iot.esptouch.util.EspNetUtil1;

import java.util.List;

public class EsptouchTask1 implements IEsptouchTask1 {

    public __EsptouchTask1 _mEsptouchTask;
    private EsptouchTaskParameter1 _mParameter;

    /**
     * Constructor of EsptouchTask1
     *
     * @param apSsid     the Ap's ssid
     * @param apBssid    the Ap's bssid
     * @param apPassword the Ap's password
     * @param context    the Context of the Application
     */
    public EsptouchTask1(String apSsid, String apBssid, String apPassword, Context context) {
        this(apSsid, apBssid, apPassword, null, context);
    }

    /**
     * Constructor of EsptouchTask1
     *
     * @param apSsid     the Ap's ssid
     * @param apBssid    the Ap's bssid
     * @param apPassword the Ap's password
     * @param espAES     AES secret key and iv
     * @param context    the Context of the Application
     */
    public EsptouchTask1(String apSsid, String apBssid, String apPassword, EspAES espAES, Context context) {
        if (TextUtils.isEmpty(apSsid)) {
            throw new NullPointerException("SSID can't be empty");
        }
        if (TextUtils.isEmpty(apBssid)) {
            throw new NullPointerException("BSSID can't be empty");
        }
        if (apPassword == null) {
            apPassword = "";
        }
        TouchData ssid = new TouchData(apSsid);
        TouchData bssid = new TouchData(EspNetUtil1.parseBssid2bytes(apBssid));
        TouchData password = new TouchData(apPassword);
        init(context, ssid, bssid, password, espAES);
    }

    public EsptouchTask1(byte[] apSsid, byte[] apBssid, byte[] apPassword, EspAES espAES, Context context) {
        if (apSsid == null || apSsid.length == 0) {
            throw new NullPointerException("SSID can't be empty");
        }
        if (apBssid == null || apBssid.length == 0) {
            throw new NullPointerException("BSSID can't be empty");
        }
        if (apPassword == null) {
            apPassword = new byte[0];
        }
        TouchData ssid = new TouchData(apSsid);
        TouchData bssid = new TouchData(apBssid);
        TouchData password = new TouchData(apPassword);
        init(context, ssid, bssid, password, espAES);
    }

    private void init(Context context, TouchData ssid, TouchData bssid, TouchData password, EspAES aes) {
        _mParameter = new EsptouchTaskParameter1();
        _mEsptouchTask = new __EsptouchTask1(context, ssid, bssid, password, aes, _mParameter, true);
    }

    @Override
    public void interrupt() {
        _mEsptouchTask.interrupt();
    }

    @Override
    public IEsptouchResult1 executeForResult() throws RuntimeException {
        return _mEsptouchTask.executeForResult();
    }

    @Override
    public boolean isCancelled() {
        return _mEsptouchTask.isCancelled();
    }

    @Override
    public List<IEsptouchResult1> executeForResults(int expectTaskResultCount)
            throws RuntimeException {
        if (expectTaskResultCount <= 0) {
            expectTaskResultCount = Integer.MAX_VALUE;
        }
        return _mEsptouchTask.executeForResults(expectTaskResultCount);
    }

    @Override
    public void setEsptouchListener(IEsptouchListener1 esptouchListener) {
        _mEsptouchTask.setEsptouchListener(esptouchListener);
    }
}
