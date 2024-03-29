package com.zt.igreen.module.data;

/**
 * Created by lifujun on 2018/7/27.
 * 点击不同类型的消息时使用的bean
 */

public class MsgBean {
    private boolean isSelect;
    private String id;
    private String title;
    private String content;
    private boolean is_read;
    private boolean is_operate;
    private String time;
    private String flag;
    private String status;
    private String new_at;
    private String machine_name;
    private int new_count;
    private String type;
    private String ty_share_id;

    public String getMachine_type() {
        return machine_type;
    }

    public void setMachine_type(String machine_type) {
        this.machine_type = machine_type;
    }

    private String machine_type;


    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    private String device_id;


    public String getApply_mobile() {
        return apply_mobile;
    }

    public void setApply_mobile(String apply_mobile) {
        this.apply_mobile = apply_mobile;
    }

    private String apply_mobile;


    public String getTy_share_id() {
        return ty_share_id;
    }

    public void setTy_share_id(String ty_share_id) {
        this.ty_share_id = ty_share_id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNew_at() {
        return new_at;
    }

    public void setNew_at(String new_at) {
        this.new_at = new_at;
    }

    public int getNew_count() {
        return new_count;
    }

    public void setNew_count(int new_count) {
        this.new_count = new_count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIs_operate() {
        return is_operate;
    }

    public void setIs_operate(boolean is_operate) {
        this.is_operate = is_operate;
    }

    public String getMachine_name() {
        return machine_name;
    }

    public void setMachine_name(String machine_name) {
        this.machine_name = machine_name;
    }
}
