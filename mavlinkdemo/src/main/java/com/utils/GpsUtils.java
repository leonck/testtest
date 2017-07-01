package com.utils;

import android.content.Context;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.hubsansdk.application.HubsanApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shengkun.cheng on 2016/12/20.
 */

public class GpsUtils {
    private Location location = null;
    private LocationManager locationManager = null;
    private Context context = null;
    private int gpsNumber = 0;

    /**
     * 初始化
     *
     * @param ctx
     */
    public GpsUtils(Context ctx) {
        context = ctx;
        try {
            locationManager = (LocationManager) HubsanApplication.getApplication().getSystemService(Context.LOCATION_SERVICE);
//        location = locationManager.getLastKnownLocation(getProvider());//获取最后位置差异
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 800, 0, locationListener);
            locationManager.addGpsStatusListener(gpsStatusListener);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    // 获取Location Provider
    private String getProvider() {
        // 构建位置查询条件
        Criteria criteria = new Criteria();
        // 查询精度：高
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 是否查询海拨：否
        criteria.setAltitudeRequired(false);
        // 是否查询方位角 : 否
        criteria.setBearingRequired(false);
        // 是否允许付费：是
        criteria.setCostAllowed(true);
        // 电量要求：低
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        // 返回最合适的符合条件的provider，第2个参数为true说明 , 如果只有一个provider是有效的,则返回当前provider
        return locationManager.getBestProvider(criteria, true);
    }

    private LocationListener locationListener = new LocationListener() {
        // 位置发生改变后调用
        public void onLocationChanged(Location mLocation) {
            if (location != null) {
                location = mLocation;
            }
        }
        // provider 被用户关闭后调用
        public void onProviderDisabled(String provider) {
            location = null;
        }

        // provider 被用户开启后调用
        public void onProviderEnabled(String provider) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l != null) {
                location = l;
            }
        }

        // provider 状态变化时调用
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    public Location getLocation() {
        return location;
    }

    public int getGpsNumber() {
        return gpsNumber;
    }

    public void closeLocation() {
        if (locationManager != null) {
            if (locationListener != null) {
                locationManager.removeGpsStatusListener(gpsStatusListener);
                locationManager.removeUpdates(locationListener);
                locationListener = null;
            }
            locationManager = null;
        }
    }

    /**
     * Gps状态监听
     */
    private GpsStatus.Listener gpsStatusListener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            if (locationManager != null) {
                GpsStatus gpsStatus = locationManager.getGpsStatus(null);
                switch (event) {
                    case GpsStatus.GPS_EVENT_SATELLITE_STATUS: {// 周期的报告卫星状态
                        // 得到所有收到的卫星的信息，包括 卫星的高度角、方位角、信噪比、和伪随机号（及卫星编号）
                        Iterable<GpsSatellite> satellites = gpsStatus.getSatellites();

                        List<GpsSatellite> satelliteList = new ArrayList<GpsSatellite>();

                        for (GpsSatellite satellite : satellites) {
                            // 包括 卫星的高度角、方位角、信噪比、和伪随机号（及卫星编号）
                    /*satellite.getElevation(); //卫星仰角 satellite.getAzimuth();   //卫星方位角 satellite.getSnr();       //信噪比
                     * satellite.getPrn();       //伪随机数，可以认为他就是卫星的编号 * satellite.hasAlmanac();   //卫星历书
					 * satellite.hasEphemeris(); * satellite.usedInFix();
					 */
                            satelliteList.add(satellite);
                        }
                        gpsNumber = satelliteList.size();
//                    drone.curentcoordinate.setGpsNumber((short) satelliteList.size());
                        break;
                    }
                }
            }
        }
    };


}
