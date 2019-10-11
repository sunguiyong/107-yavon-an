package com.zt.igreen.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.common.base.utils.ToastUtil;

import java.util.List;

/**
 * Created by lifujun on 2018/8/10.
 */

public class LocationUtil {
    private Context context;
    private LocationChangedListener listener;
    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示
            showLocation(location);
        }
    };
    private LocationManager locationManager;
    private int locationTimes;
    public LocationUtil(Context context) {
        this.context = context;
    }
    public void setListener(LocationChangedListener listener){
        this.listener = listener;
    }
    public void getLocation() {
        String locationProvider;
        //获取地理位置管理器
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
//            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            ToastUtil.showShort(context, "定位失败");
            return;
        }
//        //获取Location
//        Location location = locationManager.getLastKnownLocation(locationProvider);
//        if (location != null) {
//            //不为空,显示地理位置经纬度
//            showLocation(location);
//        }
//        //监视地理位置变化
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
    }
    /**
     * 显示地理位置经度和纬度信息
     *
     * @param location
     */
    private void showLocation(Location location) {
//        if(locationTimes > 1 && locationManager != null){
            locationManager.removeUpdates(locationListener);
//            return;
//        }
//        String locationStr = "纬度：" + location.getLatitude() + "\n"
//                + "经度：" + location.getLongitude();
//        updateVersion(location.getLatitude() + "", location.getLongitude() + "");
//        ToastUtil.showShort(context, location.getLatitude() + ","+ location.getLongitude() );
        if(listener != null){
            listener.onLocationChanged(location.getLatitude() + ","+ location.getLongitude());
        }
    }
    //wd 纬度
//jd 经度
//    public void updateVersion(String wd, String jd) {
//        String path = "http://api.map.baidu.com/geocoder?output=json&location=" + wd + "," + jd + "&key="LRCNlmj6eRo0SDCSyrRBIGLfllQzvLbm";
//    }
    public interface LocationChangedListener{
        void onLocationChanged(String location);
    }
}
