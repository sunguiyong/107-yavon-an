package com.zt.yavon.module.data;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zt.yavon.module.mine.adapter.AllDevAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lifujun on 2018/7/28.
 */

public class MineRoomBean extends AbstractExpandableItem<MineRoomBean.Machine> implements MultiItemEntity{
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


    @Override
    public int getItemType() {
        return AllDevAdapter.TYPE_TITLE;
    }

    @Override
    public List<MineRoomBean.Machine> getSubItems() {
        mSubItems = machines;
        return super.getSubItems();
    }

    @Override
    public int getLevel() {
        return 0;
    }

    public static class Machine implements Serializable,MultiItemEntity{
        private String machine_id;
        private String asset_number;
        private String machine_name;
        private String machine_icon;
        private String machine_type;
        private String user_type;
        private String expire_type;
        private String expire_value;

        @Override
        public int getItemType() {
            return AllDevAdapter.TYPE_DETAIL;
        }
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

        public String getAsset_number() {
            return asset_number;
        }

        public void setAsset_number(String asset_number) {
            this.asset_number = asset_number;
        }

        public String getMachine_type() {
            return machine_type;
        }

        public void setMachine_type(String machine_type) {
            this.machine_type = machine_type;
        }
    }
}
