package com.zt.yavon.module.data;

import java.util.List;

/**
 * Created by lifujun on 2018/7/28.
 */

public class MineRoomBean {
    private String id;
    private String name;
    private List<Machine> machines;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public class Machine{
        private String machine_id;
        private String machine_name;
        private String machine_icon;
        private String user_type;
        private String expire_type;
        private String expire_value;

        public String getMachine_id() {
            return machine_id;
        }

        public void setMachine_id(String machine_id) {
            this.machine_id = machine_id;
        }

        public String getMachine_name() {
            return machine_name;
        }

        public void setMachine_name(String machine_name) {
            this.machine_name = machine_name;
        }

        public String getMachine_icon() {
            return machine_icon;
        }

        public void setMachine_icon(String machine_icon) {
            this.machine_icon = machine_icon;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getExpire_type() {
            return expire_type;
        }

        public void setExpire_type(String expire_type) {
            this.expire_type = expire_type;
        }

        public String getExpire_value() {
            return expire_value;
        }

        public void setExpire_value(String expire_value) {
            this.expire_value = expire_value;
        }
    }
}
