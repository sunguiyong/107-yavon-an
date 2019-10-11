package com.espressif.iot.esptouch.protocol;

import com.espressif.iot.esptouch.task.ICodeData1;
import com.espressif.iot.esptouch.util.ByteUtil1;
import com.espressif.iot.esptouch.util.CRC81;

/**
 * one data format:(data code should have 2 to 65 data)
 * <p>
 * control byte       high 4 bits    low 4 bits
 * 1st 9bits:       0x0             crc(high)      data(high)
 * 2nd 9bits:       0x1                sequence header
 * 3rd 9bits:       0x0             crc(low)       data(low)
 * <p>
 * sequence header: 0,1,2,...
 *
 * @author afunx
 */
public class DataCode1 implements ICodeData1 {

    public static final int DATA_CODE_LEN = 6;

    private static final int INDEX_MAX = 127;

    private final byte mSeqHeader;
    private final byte mDataHigh;
    private final byte mDataLow;
    // the crc here means the crc of the data and sequence header be transformed
    // it is calculated by index and data to be transformed
    private final byte mCrcHigh;
    private final byte mCrcLow;

    /**
     * Constructor of DataCode1
     *
     * @param u8    the character to be transformed
     * @param index the index of the char
     */
    public DataCode1(char u8, int index) {
        if (index > INDEX_MAX) {
            throw new RuntimeException("index > INDEX_MAX");
        }
        byte[] dataBytes = ByteUtil1.splitUint8To2bytes(u8);
        mDataHigh = dataBytes[0];
        mDataLow = dataBytes[1];
        CRC81 crc8 = new CRC81();
        crc8.update(ByteUtil1.convertUint8toByte(u8));
        crc8.update(index);
        byte[] crcBytes = ByteUtil1.splitUint8To2bytes((char) crc8.getValue());
        mCrcHigh = crcBytes[0];
        mCrcLow = crcBytes[1];
        mSeqHeader = (byte) index;
    }

    @Override
    public byte[] getBytes() {
        byte[] dataBytes = new byte[DATA_CODE_LEN];
        dataBytes[0] = 0x00;
        dataBytes[1] = ByteUtil1.combine2bytesToOne(mCrcHigh, mDataHigh);
        dataBytes[2] = 0x01;
        dataBytes[3] = mSeqHeader;
        dataBytes[4] = 0x00;
        dataBytes[5] = ByteUtil1.combine2bytesToOne(mCrcLow, mDataLow);
        return dataBytes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        byte[] dataBytes = getBytes();
        for (int i = 0; i < DATA_CODE_LEN; i++) {
            String hexString = ByteUtil1.convertByte2HexString(dataBytes[i]);
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
        throw new RuntimeException("DataCode1 don't support getU8s()");
    }

}
