package com.zt.igreen.module.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lifujun on 2018/8/1.
 */

public class DevDetailBean implements Serializable{
    private String admin_user_id;
    private String share_user_id;
    private String user_id;
    private String category_id;
    private String machine_id;
    private String machine_type;
    private String asset_number;
    private String machine_name;
    private String wifi_mac;
    private String user_type;
    private String status;
    private boolean auto_lock;
    private boolean lowpower_hand_unlock;
    private boolean sedentary_reminder;
    private String sedentary_time;
    private String locker_id;
    private String password;
    private String light_device_id;
    private String machine_status;
    private int height;
    private List<DeskBean> adjust_table_height;

    public String getAdmin_user_id() {
        return admin_user_id;
    }

    public void setAdmin_user_id(String admin_user_id) {
        this.admin_user_id = admin_user_id;
    }

    public String getShare_user_id() {
        return share_user_id;
    }

    public void setShare_user_id(String share_user_id) {
        this.share_user_id = share_user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(String machine_id) {
        this.machine_id = machine_id;
    }

    public String getMachine_type() {
        return machine_type;
    }

    public void setMachine_type(String machine_type) {
        this.machine_type = machine_type;
    }

    public String getAsset_number() {
        return asset_number;
    }

    public void setAsset_number(String asset_number) {
        this.asset_number = asset_number;
    }

    public String getMachine_name() {
        return machine_name;
    }

    public void setMachine_name(String machine_name) {
        this.machine_name = machine_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAuto_lock() {
        return auto_lock;
    }

    public void setAuto_lock(boolean auto_lock) {
        this.auto_lock = auto_lock;
    }

    public boolean isLowpower_hand_unlock() {
        return lowpower_hand_unlock;
    }

    public void setLowpower_hand_unlock(boolean lowpower_hand_unlock) {
        this.lowpower_hand_unlock = lowpower_hand_unlock;
    }

    public boolean isSedentary_reminder() {
        return sedentary_reminder;
    }

    public void setSedentary_reminder(boolean sedentary_reminder) {
        this.sedentary_reminder = sedentary_reminder;
    }

    public String getSedentary_time() {
        return sedentary_time;
    }

    public void setSedentary_time(String sedentary_time) {
        this.sedentary_time = sedentary_time;
    }

    public String getLocker_id() {
        return locker_id;
    }

    public void setLocker_id(String locker_id) {
        this.locker_id = locker_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMachine_status() {
        return machine_status;
    }

    public void setMachine_status(String machine_status) {
        this.machine_status = machine_status;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<DeskBean> getAdjust_table_height() {
        return adjust_table_height;
    }

    public void setAdjust_table_height(List<DeskBean> adjust_table_height) {
        this.adjust_table_height = adjust_table_height;
    }

    public String getLight_device_id() {
        return light_device_id;
    }

    public void setLight_device_id(String light_device_id) {
        this.light_device_id = light_device_id;
    }

    public String getWifi_mac() {
        return wifi_mac;
    }

    public void setWifi_mac(String wifi_mac) {
        this.wifi_mac = wifi_mac;
    }
}
