package com.zt.igreen.module.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;

/**
 * Created by Administrator on 2019/4/17 0017.
 */
@Entity
public class DeviceINtell_Info {
    @Id(autoincrement = true)
    Long id;
    //智能情景的id
    private int intell_id;
    //房间名称
    private String room_name;
    //设备图片
    private String device_img;
    //背景图
    private String back_img;
    //设备名称
    private String device_name;
    //情景名称
    private String intell_name;
    //设备状态
    private String device_state;
    //设备id
    private int device_id;
    private int qufen_id;
    private String device_type;
    private String actions;

    @Generated(hash = 1686494382)
    public DeviceINtell_Info(Long id, int intell_id, String room_name,
                             String device_img, String back_img, String device_name,
                             String intell_name, String device_state, int device_id, int qufen_id,
                             String device_type, String actions) {
        this.id = id;
        this.intell_id = intell_id;
        this.room_name = room_name;
        this.device_img = device_img;
        this.back_img = back_img;
        this.device_name = device_name;
        this.intell_name = intell_name;
        this.device_state = device_state;
        this.device_id = device_id;
        this.qufen_id = qufen_id;
        this.device_type = device_type;
        this.actions = actions;
    }

    @Generated(hash = 946724875)
    public DeviceINtell_Info() {
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public int getQufen_id() {
        return qufen_id;
    }

    public void setQufen_id(int qufen_id) {
        this.qufen_id = qufen_id;
    }


    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIntell_id() {
        return intell_id;
    }

    public void setIntell_id(int intell_id) {
        this.intell_id = intell_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getDevice_img() {
        return device_img;
    }

    public void setDevice_img(String device_img) {
        this.device_img = device_img;
    }

    public String getBack_img() {
        return back_img;
    }

    public void setBack_img(String back_img) {
        this.back_img = back_img;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getIntell_name() {
        return intell_name;
    }

    public void setIntell_name(String intell_name) {
        this.intell_name = intell_name;
    }

    public String getDevice_state() {
        return device_state;
    }

    public void setDevice_state(String device_state) {
        this.device_state = device_state;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }
}
