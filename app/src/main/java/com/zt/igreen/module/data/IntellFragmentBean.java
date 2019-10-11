package com.zt.igreen.module.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/3/13 0013.
 */

public class IntellFragmentBean implements Serializable{

    /**
     * id : 10
     * name : 测试
     * cover_image : http://t27.zetadata.com.cn/images/intelligent/5.png
     * lights : [{"id":1,"wifi_mac":"2c3ae83d6aed","device_id":"012004452c3ae83d6aed","action":"OFF"}]
     */

    private int id;
    private String name;
    private String cover_image;
    private List<LightsBean> lights;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public List<LightsBean> getLights() {
        return lights;
    }

    public void setLights(List<LightsBean> lights) {
        this.lights = lights;
    }

    public static class LightsBean implements Serializable{
        /**
         * id : 1
         * wifi_mac : 2c3ae83d6aed
         * device_id : 012004452c3ae83d6aed
         * action : OFF
         */

        private int id;
        private String wifi_mac;
        private String device_id;
        private String action;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWifi_mac() {
            return wifi_mac;
        }

        public void setWifi_mac(String wifi_mac) {
            this.wifi_mac = wifi_mac;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
}
