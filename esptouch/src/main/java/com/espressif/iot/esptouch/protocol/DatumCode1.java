package com.espressif.iot.esptouch.protocol;

import com.espressif.iot.esptouch.task.ICodeData1;
import com.espressif.iot.esptouch.util.ByteUtil1;
import com.espressif.iot.esptouch.util.CRC81;

import java.net.InetAddress;
import java.util.LinkedList;

public class DatumCode1 implements ICodeData1 {

    // define by the Esptouch protocol, all of the datum code should add 1 at last to prevent 0
    private static final int EXTRA_LEN = 40;
    private static final int EXTRA_HEAD_LEN = 5;

    private final LinkedList<DataCode1> mDataCodes;

    /**
     * Constructor of DatumCode1
     *
     * @param apSsid      the Ap's ssid
     * @param apBssid     the Ap's bssid
     * @param apPassword  the Ap's password
     * @param ipAddress   the ip address of the phone or pad
     * @param isSsidHiden whether the Ap's ssid is hidden
     */
    public DatumCode1(byte[] apSsid, byte[] apBssid, byte[] apPassword,
                      InetAddress ipAddress, boolean isSsidHiden) {
        // Data = total len(1 byte) + apPwd len(1 byte) + SSID CRC(1 byte) +
        // BSSID CRC(1 byte) + TOTAL XOR(1 byte)+ ipAddress(4 byte) + apPwd + apSsid apPwdLen <=
        // 105 at the moment

        // total xor
        char totalXor = 0;

        char apPwdLen = (char) apPassword.length;
        CRC81 crc = new CRC81();
        crc.update(apSsid);
        char apSsidCrc = (char) crc.getValue();

        crc.reset();
        crc.update(apBssid);
        char apBssidCrc = (char) crc.getValue();

        char apSsidLen = (char) apSsid.length;
        // hostname parse
        String ipAddrStrs[] = ipAddress.getHostAddress().split("\\.");
        int ipLen = ipAddrStrs.length;

        char ipAddrChars[] = new char[ipLen];
        // only support ipv4 at the moment
        for (int i = 0; i < ipLen; ++i) {
            ipAddrChars[i] = (char) Integer.parseInt(ipAddrStrs[i]);
        }

        char _totalLen = (char) (EXTRA_HEAD_LEN + ipLen + apPwdLen + apSsidLen);
        char totalLen = isSsidHiden ? (char) (EXTRA_HEAD_LEN + ipLen + apPwdLen + apSsidLen)
                : (char) (EXTRA_HEAD_LEN + ipLen + apPwdLen);

        // build data codes
        mDataCodes = new LinkedList<>();
        mDataCodes.add(new DataCode1(_totalLen, 0));
        totalXor ^= _totalLen;
        mDataCodes.add(new DataCode1(apPwdLen, 1));
        totalXor ^= apPwdLen;
        mDataCodes.add(new DataCode1(apSsidCrc, 2));
        totalXor ^= apSsidCrc;
        mDataCodes.add(new DataCode1(apBssidCrc, 3));
        totalXor ^= apBssidCrc;
        // ESPDataCode 4 is null
        for (int i = 0; i < ipLen; ++i) {
            mDataCodes.add(new DataCode1(ipAddrChars[i], i + EXTRA_HEAD_LEN));
            totalXor ^= ipAddrChars[i];
        }

        byte[] apPwdBytes = apPassword;
        char[] apPwdChars = new char[apPwdBytes.length];
        for (int i = 0; i < apPwdBytes.length; i++) {
            apPwdChars[i] = ByteUtil1.convertByte2Uint8(apPwdBytes[i]);
        }
        for (int i = 0; i < apPwdChars.length; i++) {
            mDataCodes.add(new DataCode1(apPwdChars[i], i + EXTRA_HEAD_LEN + ipLen));
            totalXor ^= apPwdChars[i];
        }

        byte[] apSsidBytes = apSsid;
        char[] apSsidChars = new char[apSsidBytes.length];
        // totalXor will xor apSsidChars no matter whether the ssid is hidden
        for (int i = 0; i < apSsidBytes.length; i++) {
            apSsidChars[i] = ByteUtil1.convertByte2Uint8(apSsidBytes[i]);
            totalXor ^= apSsidChars[i];
        }
        if (isSsidHiden) {
            for (int i = 0; i < apSsidChars.length; i++) {
                mDataCodes.add(new DataCode1(apSsidChars[i], i + EXTRA_HEAD_LEN + ipLen + apPwdLen));
            }
        }

        // add total xor last
        mDataCodes.add(4, new DataCode1(totalXor, 4));

        // add bssid
        int bssidInsertIndex = EXTRA_HEAD_LEN;
        for (int i = 0; i < apBssid.length; i++) {
            int index = totalLen + i;
            char c = ByteUtil1.convertByte2Uint8(apBssid[i]);
            DataCode1 dc = new DataCode1(c, index);
            if (bssidInsertIndex >= mDataCodes.size()) {
                mDataCodes.add(dc);
            } else {
                mDataCodes.add(bssidInsertIndex, dc);
            }
            bssidInsertIndex += 4;
        }
    }

    @Override
    public byte[] getBytes() {
        byte[] datumCode = new byte[mDataCodes.size() * DataCode1.DATA_CODE_LEN];
        int index = 0;
        for (DataCode1 dc : mDataCodes) {
            for (byte b : dc.getBytes()) {
                datumCode[index++] = b;
            }
        }
        return datumCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        byte[] dataBytes = getBytes();
        for (byte dataByte : dataBytes) {
            String hexString = ByteUtil1.convertByte2HexString(dataByte);
            sb.append("0x");
            if (hexString.length() == 1) {
                sb.append("0");
            }
            sb.append(hexString).append(" ");
        }
        return sb.toString();
    }

    @Override
    public char[] getU8s() {
        byte[] dataBytes = getBytes();
        int len = dataBytes.length / 2;
        char[] dataU8s = new char[len];
        byte high, low;
        for (int i = 0; i < len; i++) {
            high = dataBytes[i * 2];
            low = dataBytes[i * 2 + 1];
            dataU8s[i] = (char) (ByteUtil1.combine2bytesToU16(high, low) + EXTRA_LEN);
        }
        return dataU8s;
    }
}
