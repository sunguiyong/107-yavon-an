package com.zt.igreen.module.data;

import com.zt.igreen.utils.TimeUtils;

import java.util.Date;

/**
 * Created by lifujun on 2018/7/30.
 */

public class TimeBean {
    private Date start;
    private Date end;

    public String getStart() {
        if(start == null){
            return null;
        }
        return TimeUtils.getFormatStringByDate("yyyy/MM/dd",start);
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public String getEnd() {
        if(end == null){
            return null;
        }
        return TimeUtils.getFormatStringByDate("yyyy/MM/dd",end);
    }

    public void setEnd(Date end) {
        this.end = end;
    }
    @Override
    public String toString() {
        return "{start:\"" + TimeUtils.getFormatStringByDate("yyyy-MM-dd",start) +
                "\", end:\"" + TimeUtils.getFormatStringByDate("yyyy-MM-dd",end)  +
                "\"}";
    }
}
