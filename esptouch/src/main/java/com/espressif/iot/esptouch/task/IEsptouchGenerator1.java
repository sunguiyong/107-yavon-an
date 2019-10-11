package com.espressif.iot.esptouch.task;

public interface IEsptouchGenerator1 {
    /**
     * Get guide code by the format of byte[][]
     *
     * @return guide code by the format of byte[][]
     */
    byte[][] getGCBytes2();

    /**
     * Get data code by the format of byte[][]
     *
     * @return data code by the format of byte[][]
     */
    byte[][] getDCBytes2();
}
