package com.zt.igreen.module.data;

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
        public AQI aqi;
        public NOW now;
//        public SUG suggestion;
    }
    public class AQI{
        public City city;
    }
    public class City{
        public String qlty;
        public String pm25;
        public String co;
    }
    public class NOW{
        public COND cond;
        public String tmp;
        public String hum;
    }
    public class COND{
        public String txt;
    }
//    public class SUG{
//        public AIR air;
//    }
//    public class AIR{
//        public String brf;
//    }
}
