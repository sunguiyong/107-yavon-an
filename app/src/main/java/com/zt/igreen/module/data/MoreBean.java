package com.zt.igreen.module.data;

/**
 * Created by Administrator on 2019/2/27 0027.
 */

public class MoreBean {

    /**
     * humidity : 22
     * pm : 33.0
     * temperature : 44
     * co2 : 55
     * date : 2019-01-15
     */

    private int humidity;
    private String pm;
    private int temperature;
    private int co2;
    private String date;

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
