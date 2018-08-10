package com.zt.yavon.module.data;

import java.util.List;

/**
 * Created by lifujun on 2018/8/10.
 */

public class UserRecordBean {
    public MachineInfo current;
    public List<Record> records;
    public class Record{
        public String date;
        public String week;
        public List<RecordDetail> logs;
    }
    public static class RecordDetail{
        public String mobile;
        public String user_type;
        public String time;
        public String content;
        public String date;
        public String week;
        public boolean isFist;
    }
    public class MachineInfo{
        public String machine_name;
        public String machine_status;
        public String icon;
    }
}
