package com.hubsansdk.drone.bean;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;


import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.application.HubsanHandleMessageApp;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces;
import com.hubsansdk.drone.HubsanDroneVariable;
import com.hubsansdk.utils.LogX;
import com.utils.Utils;

import java.util.Iterator;
import java.util.List;

/**
 * @project name: X-Hubsan
 * @class name：com.hubsansdk.drone.bean
 * @class describe:
 * @anthor shengkun.cheng
 * @time 2017/3/13 19:27
 * @change
 * @chang time:
 * @class describe:
 */
public class GPSManager extends HubsanDroneVariable {

    private HubsanDrone myDrone;
    private HubsanHandleMessageApp app;
    /*定位使用的管理类*/
    private LocationManager locationManager;
    private Location location;

    public double mLat = 0;
    public double mLon = 0;

    public GPSManager(HubsanDrone myDrone) {
        this.myDrone = myDrone;
        app = (HubsanHandleMessageApp) HubsanApplication.getApplication();
        if (hasGPSDevice() == true) {
            locationManager = (LocationManager) app.getSystemService(Context.LOCATION_SERVICE);
            //为获取地理位置信息时设置查询条件
            String bestProvider = locationManager.getBestProvider(getCriteria(), true);
            //获取位置信息
            //如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
            if (ActivityCompat.checkSelfPermission(HubsanApplication.getApplication(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(HubsanApplication.getApplication(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locationManager.getLastKnownLocation(bestProvider);
            updateView(location);
            //监听状态
//            locationManager.addGpsStatusListener(gpsListener);
            //参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种，参数2，位置信息更新周期，单位毫秒，参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        }
    }

    //位置监听
    private LocationListener locationListener = new LocationListener() {
        /**
         * 位置信息变化时触发
         */
        public void onLocationChanged(Location location) {
//            LogX.e("********位置信息变化时触发********");
            updateView(location);
        }

        /**
         * GPS状态变化时触发
         */
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                //GPS状态为可见时
                case LocationProvider.AVAILABLE:
//                    LogX.e("当前GPS状态为可见状态");
                    break;
                //GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
//                    LogX.e("当前GPS状态为服务区外状态");
                    break;
                //GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
//                    LogX.e("当前GPS状态为暂停服务状态");
                    break;
            }
        }

        /**
         * GPS开启时触发
         */
        public void onProviderEnabled(String provider) {
            if (locationManager != null) {
                if (ActivityCompat.checkSelfPermission(HubsanApplication.getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HubsanApplication.getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location location = locationManager.getLastKnownLocation(provider);
//                LogX.e("********GPS开启时触发********");
                updateView(location);
            }
        }

        /**
         * GPS禁用时触发
         */
        public void onProviderDisabled(String provider) {
//            LogX.e("********GPS禁用时触发********");
            updateView(null);
        }

    };

    /**
     * 实时更新文本内容
     *
     * @param location
     */
    private void updateView(Location location) {
        try {
            if (location != null) {
                mLat = location.getLatitude();
                mLon = location.getLongitude();
                double lat = Utils.getBigDec(mLat);
//                Double.valueOf(String.format("%.7f", mLat));
                double lon = Utils.getBigDec(mLon);
                //Double.valueOf(String.format("%.7f", mLon));
                float accuracy = location.getAccuracy();
                app.localLatLon.setLocationAccuracy(accuracy);
                app.localLatLon.setNotifyLat(lat);
                app.localLatLon.setNotifyLon(lon);

                myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_GPS_MANAGER);
                LogX.e("===========================================手机GPS得到的数据===================================（" + location.getLatitude() + "," + location.getLongitude() + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //状态监听
    private GpsStatus.Listener gpsListener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            switch (event) {
                //卫星状态改变
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    if (locationManager != null) {
//                    Log.i(TAG, "卫星状态改变");
                        //获取当前状态
                        if (ActivityCompat.checkSelfPermission(HubsanApplication.getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        GpsStatus gpsStatus = locationManager.getGpsStatus(null);
                        //获取卫星颗数的默认最大值
                        int maxSatellites = gpsStatus.getMaxSatellites();
                        //创建一个迭代器保存所有卫星
                        Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                        int count = 0;
                        while (iters.hasNext() && count <= maxSatellites) {
                            GpsSatellite s = iters.next();
                            count++;
                        }
//                        app.localLatLon.setLocalGPSNumber(count);
                    }
                    break;

            }
        }
    };

    /**
     * 返回查询条件
     *
     * @return
     */
    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        //设置是否需要方位信息
        criteria.setBearingRequired(false);
        //设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    /**
     * 判断手机是否GSP模块
     *
     * @return
     */
    private boolean hasGPSDevice() {
        final LocationManager mgr = (LocationManager) app.getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void closeLocation() {
        if (locationManager != null) {
            if (locationListener != null) {
                locationManager.removeGpsStatusListener(gpsListener);
                locationManager.removeUpdates(locationListener);
                locationListener = null;
            }
            locationManager = null;
        }
    }


}
