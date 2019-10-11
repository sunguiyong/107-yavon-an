package com.zt.igreen.module.data;

import java.util.List;

/**
 * Created by Administrator on 2019/3/15 0015.
 */

public class IntellDetails {


    /**
     * id : 1
     * name : 开启设备
     * cover_image : http://lvzhihui.dev.com/images/intelligent/1.png
     * actions : [{"id":2,"intelligent_id":1,"machine_id":48,"machine_name":"升降桌","machine_type":"ADJUST_TABLE","action":"SOCKET_ON","room_name":"默认","machine_icon":"http://lvzhihui.dev.com/images/category/default/table.png"}]
     */

    private int id;
    private String name;
    private String cover_image;
    private List<ActionsBean> actions;

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

    public List<ActionsBean> getActions() {
        return actions;
    }

    public void setActions(List<ActionsBean> actions) {
        this.actions = actions;
    }

    public static class ActionsBean {
        /**
         * id : 2
         * intelligent_id : 1
         * machine_id : 48
         * machine_name : 升降桌
         * machine_type : ADJUST_TABLE
         * action : SOCKET_ON
         * room_name : 默认
         * machine_icon : http://lvzhihui.dev.com/images/category/default/table.png
         */

        private int id;
        private int intelligent_id;
        private int machine_id;
        private String machine_name;
        private String machine_type;
        private String action;
        private String room_name;
        private String machine_icon;
        private Object actions;

        public Object getActions() {
            return actions;
        }

        public void setActions(Object actions) {
            this.actions = actions;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIntelligent_id() {
            return intelligent_id;
        }

        public void setIntelligent_id(int intelligent_id) {
            this.intelligent_id = intelligent_id;
        }

        public int getMachine_id() {
            return machine_id;
        }

        public void setMachine_id(int machine_id) {
            this.machine_id = machine_id;
        }

        public String getMachine_name() {
            return machine_name;
        }

        public void setMachine_name(String machine_name) {
            this.machine_name = machine_name;
        }

        public String getMachine_type() {
            return machine_type;
        }

        public void setMachine_type(String machine_type) {
            this.machine_type = machine_type;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public String getMachine_icon() {
            return machine_icon;
        }

        public void setMachine_icon(String machine_icon) {
            this.machine_icon = machine_icon;
        }
    }
}
