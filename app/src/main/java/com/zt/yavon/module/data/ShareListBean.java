package com.zt.yavon.module.data;

import java.util.List;

/**
 * Created by lifujun on 2018/7/28.
 */

public class ShareListBean {
    private MineRoomBean.Machine machine;
    private List<User> users;

    public MineRoomBean.Machine getMachine() {
        return machine;
    }

    public void setMachine(MineRoomBean.Machine machine) {
        this.machine = machine;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public class User{
        public String user_id;
        public String mobile;
        public String nick_name;
        public String use_time;
    }
}
