package com.zt.yavon.module.data;

import java.util.List;

/**
 * Created by lifujun on 2018/8/10.
 */

public class WeatherBean {
    public String code;
    public Result result;
    public class Result{
        public List<HeWeather> HeWeather5;
    }
    public class HeWeather{
//        public AQI aqi;
        public NOW now;
        public SUG suggestion;
    }
//    public class AQI{
//
//    }
    public class NOW{
        public COND cond;
        public String tmp;
    }
    public class COND{
        public String txt;
    }
    public class SUG{
        public AIR air;
    }
    public class AIR{
        public String brf;
    }
}
