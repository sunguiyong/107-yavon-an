package com.espressif.iot.esptouch.protocol;

import com.espressif.iot.esptouch.task.IEsptouchGenerator1;
import com.espressif.iot.esptouch.util.ByteUtil1;

import java.net.InetAddress;

public class EsptouchGenerator1 implements IEsptouchGenerator1 {

    private final byte[][] mGcBytes2;
    private final byte[][] mDcBytes2;

    /**
     * Constructor of EsptouchGenerator1, it will cost some time(maybe a bit
     * much)
     *
     * @param apSsid      the Ap's ssid
     * @param apBssid     the Ap's bssid
     * @param apPassword  the Ap's password
     * @param inetAddress the phone's or pad's local ip address allocated by Ap
     * @param isSsidHiden whether the Ap's ssid is hidden
     */
    public EsptouchGenerator1(byte[] apSsid, byte[] apBssid, byte[] apPassword,
                              InetAddress inetAddress, boolean isSsidHiden) {
        // generate guide code
        GuideCode1 gc = new GuideCode1();
        char[] gcU81 = gc.getU8s();
        mGcBytes2 = new byte[gcU81.length][];

        for (int i = 0; i < mGcBytes2.length; i++) {
            mGcBytes2[i] = ByteUtil1.genSpecBytes(gcU81[i]);
        }

        // generate data code
        DatumCode1 dc = new DatumCode1(apSsid, apBssid, apPassword, inetAddress,
                isSsidHiden);
        char[] dcU81 = dc.getU8s();
        mDcBytes2 = new byte[dcU81.length][];

        for (int i = 0; i < mDcBytes2.length; i++) {
            mDcBytes2[i] = ByteUtil1.genSpecBytes(dcU81[i]);
        }
    }

    @Override
    public byte[][] getGCBytes2() {
        return mGcBytes2;
    }

    @Override
    public byte[][] getDCBytes2() {
        return mDcBytes2;
    }

}
