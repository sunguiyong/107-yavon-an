package com.zt.yavon.module.data;

/**
 * Created by lifujun on 2018/8/10.
 */

public class CityLocation {
    public String status;
    public Result result;
    public class Result{
        public Address addressComponent;
    }
    public class Address{
        public String city;
    }
}
