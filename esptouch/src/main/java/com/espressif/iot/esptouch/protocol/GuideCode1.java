package com.espressif.iot.esptouch.protocol;

import com.espressif.iot.esptouch.task.ICodeData1;
import com.espressif.iot.esptouch.util.ByteUtil1;

public class GuideCode1 implements ICodeData1 {

    public static final int GUIDE_CODE_LEN = 4;

    @Override
    public byte[] getBytes() {
        throw new RuntimeException("DataCode1 don't support getBytes()");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        char[] dataU8s = getU8s();
        for (int i = 0; i < GUIDE_CODE_LEN; i++) {
            String hexString = ByteUtil1.convertU8ToHexString(dataU8s[i]);
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
        char[] guidesU8s = new char[GUIDE_CODE_LEN];
        guidesU8s[0] = 515;
        guidesU8s[1] = 514;
        guidesU8s[2] = 513;
        guidesU8s[3] = 512;
        return guidesU8s;
    }
}
