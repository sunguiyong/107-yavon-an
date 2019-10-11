package com.zt.igreen.module.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/2/21 0021.
 */

public class DetailsBean implements Serializable{

    /**
     * fault_1 : 3
     * fault_2 : 5
     * fault_desc : 缺水;原水缺水
     * filter_1 : 83
     * filter_2 : 88
     * filter_3 : 87
     * filter_beng : 25497
     * open_cold : 0
     * open_hot : 0
     * open_power : 1
     * sensor : S00
     * tank_full : 0
     * tds_clean : 57
     * tds_source : 427
     * unit : 01
     * volume_left : 8687
     * volume_out : 1
     * water_temp_hot : 14
     * water_temp_normal : 0
     * water_temp_warm : 0
     * water_type : F
     * fix_time : [{"id":0,"type":"WORK","start_at":"00:00","end_at":"00:00","status":"OFF"},{"id":0,"type":"REST","start_at":"00:00","end_at":"00:00","status":"OFF"}]
     */

    private String fault_1;
    private String fault_2;
    private String fault_desc;
    private String filter_1;
    private String filter_2;
    private String filter_3;
    private String filter_beng;
    private String open_cold;
    private String open_hot;
    private String open_power;
    private String sensor;
    private String tank_full;
    private String tds_clean;
    private String tds_source;
    private String unit;
    private String volume_left;
    private String volume_out;
    private String water_temp_hot;
    private String water_temp_normal;
    private String water_temp_warm;
    private String water_type;
    private List<FixTimeBean> fix_time;

    public String getFault_1() {
        return fault_1;
    }

    public void setFault_1(String fault_1) {
        this.fault_1 = fault_1;
    }

    public String getFault_2() {
        return fault_2;
    }

    public void setFault_2(String fault_2) {
        this.fault_2 = fault_2;
    }

    public String getFault_desc() {
        return fault_desc;
    }

    public void setFault_desc(String fault_desc) {
        this.fault_desc = fault_desc;
    }

    public String getFilter_1() {
        return filter_1;
    }

    public void setFilter_1(String filter_1) {
        this.filter_1 = filter_1;
    }

    public String getFilter_2() {
        return filter_2;
    }

    public void setFilter_2(String filter_2) {
        this.filter_2 = filter_2;
    }

    public String getFilter_3() {
        return filter_3;
    }

    public void setFilter_3(String filter_3) {
        this.filter_3 = filter_3;
    }

    public String getFilter_beng() {
        return filter_beng;
    }

    public void setFilter_beng(String filter_beng) {
        this.filter_beng = filter_beng;
    }

    public String getOpen_cold() {
        return open_cold;
    }

    public void setOpen_cold(String open_cold) {
        this.open_cold = open_cold;
    }

    public String getOpen_hot() {
        return open_hot;
    }

    public void setOpen_hot(String open_hot) {
        this.open_hot = open_hot;
    }

    public String getOpen_power() {
        return open_power;
    }

    public void setOpen_power(String open_power) {
        this.open_power = open_power;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getTank_full() {
        return tank_full;
    }

    public void setTank_full(String tank_full) {
        this.tank_full = tank_full;
    }

    public String getTds_clean() {
        return tds_clean;
    }

    public void setTds_clean(String tds_clean) {
        this.tds_clean = tds_clean;
    }

    public String getTds_source() {
        return tds_source;
    }

    public void setTds_source(String tds_source) {
        this.tds_source = tds_source;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getVolume_left() {
        return volume_left;
    }

    public void setVolume_left(String volume_left) {
        this.volume_left = volume_left;
    }

    public String getVolume_out() {
        return volume_out;
    }

    public void setVolume_out(String volume_out) {
        this.volume_out = volume_out;
    }

    public String getWater_temp_hot() {
        return water_temp_hot;
    }

    public void setWater_temp_hot(String water_temp_hot) {
        this.water_temp_hot = water_temp_hot;
    }

    public String getWater_temp_normal() {
        return water_temp_normal;
    }

    public void setWater_temp_normal(String water_temp_normal) {
        this.water_temp_normal = water_temp_normal;
    }

    public String getWater_temp_warm() {
        return water_temp_warm;
    }

    public void setWater_temp_warm(String water_temp_warm) {
        this.water_temp_warm = water_temp_warm;
    }

    public String getWater_type() {
        return water_type;
    }

    public void setWater_type(String water_type) {
        this.water_type = water_type;
    }

    public List<FixTimeBean> getFix_time() {
        return fix_time;
    }

    public void setFix_time(List<FixTimeBean> fix_time) {
        this.fix_time = fix_time;
    }

    public static class FixTimeBean implements Serializable{
        /**
         * id : 0
         * type : WORK
         * start_at : 00:00
         * end_at : 00:00
         * status : OFF
         */

        private int id;
        private String type;
        private String start_at;
        private String end_at;
        private String status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStart_at() {
            return start_at;
        }

        public void setStart_at(String start_at) {
            this.start_at = start_at;
        }

        public String getEnd_at() {
            return end_at;
        }

        public void setEnd_at(String end_at) {
            this.end_at = end_at;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
