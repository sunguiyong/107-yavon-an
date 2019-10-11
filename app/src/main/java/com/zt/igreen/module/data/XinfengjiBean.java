package com.zt.igreen.module.data;

/**
 * Created by Administrator on 2019/2/27 0027.
 */

public class XinfengjiBean {

    /**
     * alerts : {"alm1":false,"alm2":false}
     * data : {"clralm1":false,"clralm2":false,"itm":21,"ipm2d5":8,"fsval":0,"ico2":1578,"irh":48,"onoff":true}
     */

    private AlertsBean alerts;
    private DataBean data;

    public AlertsBean getAlerts() {
        return alerts;
    }

    public void setAlerts(AlertsBean alerts) {
        this.alerts = alerts;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class AlertsBean {
        /**
         * alm1 : false
         * alm2 : false
         */

        private boolean alm1;
        private boolean alm2;

        public boolean isAlm1() {
            return alm1;
        }

        public void setAlm1(boolean alm1) {
            this.alm1 = alm1;
        }

        public boolean isAlm2() {
            return alm2;
        }

        public void setAlm2(boolean alm2) {
            this.alm2 = alm2;
        }
    }

    public static class DataBean {
        public DataBean(int itm, int ipm2d5,  int ico2, int irh) {
            this.itm = itm;
            this.ipm2d5 = ipm2d5;
            this.ico2 = ico2;
            this.irh = irh;
        }

        /**
         * clralm1 : false
         * clralm2 : false
         * itm : 21
         * ipm2d5 : 8
         * fsval : 0
         * ico2 : 1578
         * irh : 48
         * onoff : true
         */

        private boolean clralm1;
        private boolean clralm2;
        private int itm;
        private int ipm2d5;
        private int fsval;
        private int ico2;
        private int irh;
        private boolean onoff;

        public boolean isClralm1() {
            return clralm1;
        }

        public void setClralm1(boolean clralm1) {
            this.clralm1 = clralm1;
        }

        public boolean isClralm2() {
            return clralm2;
        }

        public void setClralm2(boolean clralm2) {
            this.clralm2 = clralm2;
        }

        public int getItm() {
            return itm;
        }

        public void setItm(int itm) {
            this.itm = itm;
        }

        public int getIpm2d5() {
            return ipm2d5;
        }

        public void setIpm2d5(int ipm2d5) {
            this.ipm2d5 = ipm2d5;
        }

        public int getFsval() {
            return fsval;
        }

        public void setFsval(int fsval) {
            this.fsval = fsval;
        }

        public int getIco2() {
            return ico2;
        }

        public void setIco2(int ico2) {
            this.ico2 = ico2;
        }

        public int getIrh() {
            return irh;
        }

        public void setIrh(int irh) {
            this.irh = irh;
        }

        public boolean isOnoff() {
            return onoff;
        }

        public void setOnoff(boolean onoff) {
            this.onoff = onoff;
        }
    }
}
