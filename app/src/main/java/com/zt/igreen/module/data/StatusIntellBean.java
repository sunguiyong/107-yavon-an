package com.zt.igreen.module.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/11 0011.
 */

public class StatusIntellBean implements Serializable{
    /*public String changjing_id;
    public String changjing_name;
    public String back_img;*/
    public   String message;
    public String room_name;
    public String devicename;
    public String status;
    public String deviceimg;
    public String style;
    private String intellid;

    public String getIntellid() {
        return intellid;
    }

    public void setIntellid(String intellid) {
        this.intellid = intellid;
    }

    /*public String getBack_img() {
            return back_img;
        }

        public void setBack_img(String back_img) {
            this.back_img = back_img;
        }*/
    public StatusIntellBean(String message) {
        this.message=message;

    }
    private int id;
    public StatusIntellBean(int id,String room_name, String devicename, String status, String deviceimg, String style,String intellid) {
        this.id=id;
        this.room_name = room_name;
        this.devicename = devicename;
        this.status = status;
        this.deviceimg = deviceimg;
        this.style = style;
        this.intellid=intellid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StatusIntellBean() {

    }
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String ON;
    public String OFF;
    public String SOCKET_ON;
    public String SOCKET_OFF;
    public String CUSTOM_0;
    public String CUSTOM_1;
    public String CUSTOM_2;
    public String CUSTOM_3;
    public String CUSTOM_4;
    public String BOOT_UP;
    public String SHUTDOWN;
    public String SMART;
    public String LOW;
    public String MID;
    public String HIGH;
    public String OPEN_REFRIGERATION;
    public String OPEN_HEATING;

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeviceimg() {
        return deviceimg;
    }

    public void setDeviceimg(String deviceimg) {
        this.deviceimg = deviceimg;
    }

    public String getON() {
        return ON;
    }

    public void setON(String ON) {
        this.ON = ON;
    }

    public String getOFF() {
        return OFF;
    }

    public void setOFF(String OFF) {
        this.OFF = OFF;
    }

    public String getSOCKET_ON() {
        return SOCKET_ON;
    }

    public void setSOCKET_ON(String SOCKET_ON) {
        this.SOCKET_ON = SOCKET_ON;
    }

    public String getSOCKET_OFF() {
        return SOCKET_OFF;
    }

    public void setSOCKET_OFF(String SOCKET_OFF) {
        this.SOCKET_OFF = SOCKET_OFF;
    }

    public String getCUSTOM_0() {
        return CUSTOM_0;
    }

    public void setCUSTOM_0(String CUSTOM_0) {
        this.CUSTOM_0 = CUSTOM_0;
    }

    public String getCUSTOM_1() {
        return CUSTOM_1;
    }

    public void setCUSTOM_1(String CUSTOM_1) {
        this.CUSTOM_1 = CUSTOM_1;
    }

    public String getCUSTOM_2() {
        return CUSTOM_2;
    }

    public void setCUSTOM_2(String CUSTOM_2) {
        this.CUSTOM_2 = CUSTOM_2;
    }

    public String getCUSTOM_3() {
        return CUSTOM_3;
    }

    public void setCUSTOM_3(String CUSTOM_3) {
        this.CUSTOM_3 = CUSTOM_3;
    }

    public String getCUSTOM_4() {
        return CUSTOM_4;
    }

    public void setCUSTOM_4(String CUSTOM_4) {
        this.CUSTOM_4 = CUSTOM_4;
    }

    public String getBOOT_UP() {
        return BOOT_UP;
    }

    public void setBOOT_UP(String BOOT_UP) {
        this.BOOT_UP = BOOT_UP;
    }

    public String getSHUTDOWN() {
        return SHUTDOWN;
    }

    public void setSHUTDOWN(String SHUTDOWN) {
        this.SHUTDOWN = SHUTDOWN;
    }

    public String getSMART() {
        return SMART;
    }

    public void setSMART(String SMART) {
        this.SMART = SMART;
    }

    public String getLOW() {
        return LOW;
    }

    public void setLOW(String LOW) {
        this.LOW = LOW;
    }

    public String getMID() {
        return MID;
    }

    public void setMID(String MID) {
        this.MID = MID;
    }

    public String getHIGH() {
        return HIGH;
    }

    public void setHIGH(String HIGH) {
        this.HIGH = HIGH;
    }

    public String getOPEN_REFRIGERATION() {
        return OPEN_REFRIGERATION;
    }

    public void setOPEN_REFRIGERATION(String OPEN_REFRIGERATION) {
        this.OPEN_REFRIGERATION = OPEN_REFRIGERATION;
    }

    public String getOPEN_HEATING() {
        return OPEN_HEATING;
    }

    public void setOPEN_HEATING(String OPEN_HEATING) {
        this.OPEN_HEATING = OPEN_HEATING;
    }
}
