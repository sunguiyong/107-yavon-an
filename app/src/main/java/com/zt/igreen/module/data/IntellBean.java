package com.zt.igreen.module.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/13 0013.
 */

public class IntellBean implements Serializable {
    private int machine_id;
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public IntellBean(int machine_id, String action) {
        this.machine_id = machine_id;
        this.action = action;
    }

    public int getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(int machine_id) {
        this.machine_id = machine_id;
    }


}
