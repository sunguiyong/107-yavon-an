package com.zt.igreen.module.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/4/5 0005.
 */

public class HistoryBean  implements Serializable{

    /**
     * id : 3
     * machine_id : 3
     * heart_rate : 1
     * abnormal_heart_rate : 1
     * blood_oxygen : 2
     * high_blood_pressure : 80
     * low_blood_pressure : 100
     * breathing_rate : 3
     * peripheral_circulation : 0.0
     * heart_rate_variability : 90
     * heart_rate_variability_str : 优秀
     * neurological_balance : 40
     * neurological_balance_str : 均衡
     * mental_stress_level : 60
     * mental_stress_level_str : 中度
     * physical_fatigue : 30
     * physical_fatigue_str : 轻度
     * vascular_health : 第一阶段
     * vascular_health_str : 血管柔软
     * date : 2018-01-02
     */

    private int id;
    private int machine_id;
    private int heart_rate;
    private int abnormal_heart_rate;
    private int blood_oxygen;
    private int high_blood_pressure;
    private int low_blood_pressure;
    private int breathing_rate;
    private String peripheral_circulation;
    private int heart_rate_variability;
    private String heart_rate_variability_str;
    private int neurological_balance;
    private String neurological_balance_str;
    private int mental_stress_level;
    private String mental_stress_level_str;
    private int physical_fatigue;
    private String physical_fatigue_str;
    private String vascular_health;
    private String vascular_health_str;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(int machine_id) {
        this.machine_id = machine_id;
    }

    public int getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(int heart_rate) {
        this.heart_rate = heart_rate;
    }

    public int getAbnormal_heart_rate() {
        return abnormal_heart_rate;
    }

    public void setAbnormal_heart_rate(int abnormal_heart_rate) {
        this.abnormal_heart_rate = abnormal_heart_rate;
    }

    public int getBlood_oxygen() {
        return blood_oxygen;
    }

    public void setBlood_oxygen(int blood_oxygen) {
        this.blood_oxygen = blood_oxygen;
    }

    public int getHigh_blood_pressure() {
        return high_blood_pressure;
    }

    public void setHigh_blood_pressure(int high_blood_pressure) {
        this.high_blood_pressure = high_blood_pressure;
    }

    public int getLow_blood_pressure() {
        return low_blood_pressure;
    }

    public void setLow_blood_pressure(int low_blood_pressure) {
        this.low_blood_pressure = low_blood_pressure;
    }

    public int getBreathing_rate() {
        return breathing_rate;
    }

    public void setBreathing_rate(int breathing_rate) {
        this.breathing_rate = breathing_rate;
    }

    public String getPeripheral_circulation() {
        return peripheral_circulation;
    }

    public void setPeripheral_circulation(String peripheral_circulation) {
        this.peripheral_circulation = peripheral_circulation;
    }

    public int getHeart_rate_variability() {
        return heart_rate_variability;
    }

    public void setHeart_rate_variability(int heart_rate_variability) {
        this.heart_rate_variability = heart_rate_variability;
    }

    public String getHeart_rate_variability_str() {
        return heart_rate_variability_str;
    }

    public void setHeart_rate_variability_str(String heart_rate_variability_str) {
        this.heart_rate_variability_str = heart_rate_variability_str;
    }

    public int getNeurological_balance() {
        return neurological_balance;
    }

    public void setNeurological_balance(int neurological_balance) {
        this.neurological_balance = neurological_balance;
    }

    public String getNeurological_balance_str() {
        return neurological_balance_str;
    }

    public void setNeurological_balance_str(String neurological_balance_str) {
        this.neurological_balance_str = neurological_balance_str;
    }

    public int getMental_stress_level() {
        return mental_stress_level;
    }

    public void setMental_stress_level(int mental_stress_level) {
        this.mental_stress_level = mental_stress_level;
    }

    public String getMental_stress_level_str() {
        return mental_stress_level_str;
    }

    public void setMental_stress_level_str(String mental_stress_level_str) {
        this.mental_stress_level_str = mental_stress_level_str;
    }

    public int getPhysical_fatigue() {
        return physical_fatigue;
    }

    public void setPhysical_fatigue(int physical_fatigue) {
        this.physical_fatigue = physical_fatigue;
    }

    public String getPhysical_fatigue_str() {
        return physical_fatigue_str;
    }

    public void setPhysical_fatigue_str(String physical_fatigue_str) {
        this.physical_fatigue_str = physical_fatigue_str;
    }

    public String getVascular_health() {
        return vascular_health;
    }

    public void setVascular_health(String vascular_health) {
        this.vascular_health = vascular_health;
    }

    public String getVascular_health_str() {
        return vascular_health_str;
    }

    public void setVascular_health_str(String vascular_health_str) {
        this.vascular_health_str = vascular_health_str;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

