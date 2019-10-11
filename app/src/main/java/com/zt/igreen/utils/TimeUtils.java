package com.zt.igreen.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lifujun on 2018/7/30.
 */

public class TimeUtils {
    public static String getFormatStringByDate(String format,Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
