package com.hubsansdk.drone.bean;

/**
 * @project name: X-Hubsan
 * @class name：com.csk.hbsdrone.activities.bean
 * @class describe:
 * @anthor shengkun.cheng
 * @time 2017/2/22 18:09
 * @change
 * @chang time:
 * @class describe:
 */
public class LocalLatLon {
    private double localLat;//经度
    private double localLon;//纬度
    private double localGPSNumber;//纬度
    private double localNeedGPS;//是否需要GPS
    private double localTestGPS ;//GPS测试结果
    private boolean isReal;

    private float locationAccuracy;//GPS精度
    private double locationMapLat;
    private double locationMapLon;
    private float mapZoom;
    private double mapCenterLat;//屏幕中心点位置
    private double mapCenterLon;

    private double locationGPStoMapLat;//gps-->map 坐标LAT
    private double locationGPStoMapLon;//gps-->map 坐标LON

    private double focusLat;
    private double focusLon;
    private double guideLat;
    private double guideLon;
    private double notifyLat;//广播GPS纬度
    private double notifyLon;//广播GPS经度


    public double getLocalLat() {
        return localLat;
    }

    public void setLocalLat(double localLat) {
        this.localLat = localLat;
    }

    public double getLocalLon() {
        return localLon;
    }

    public void setLocalLon(double localLon) {
        this.localLon = localLon;
    }

    public double getLocalGPSNumber() {
        return localGPSNumber;
    }

    public void setLocalGPSNumber(double localGPSNumber) {
        this.localGPSNumber = localGPSNumber;
    }

    public double getLocalNeedGPS() {
        return localNeedGPS;
    }

    public void setLocalNeedGPS(double localNeedGPS) {
        this.localNeedGPS = localNeedGPS;
    }

    public double getLocalTestGPS() {
        return localTestGPS;
    }

    public void setLocalTestGPS(double localTestGPS) {
        this.localTestGPS = localTestGPS;
    }

    public double getFocusLat() {
        return focusLat;
    }

    public void setFocusLat(double focusLat) {
        this.focusLat = focusLat;
    }

    public double getFocusLon() {
        return focusLon;
    }

    public void setFocusLon(double focusLon) {
        this.focusLon = focusLon;
    }

    public double getGuideLat() {
        return guideLat;
    }

    public void setGuideLat(double guideLat) {
        this.guideLat = guideLat;
    }

    public double getGuideLon() {
        return guideLon;
    }

    public void setGuideLon(double guideLon) {
        this.guideLon = guideLon;
    }

    public double getNotifyLat() {
        return notifyLat;
    }

    public void setNotifyLat(double notifyLat) {
        this.notifyLat = notifyLat;
    }

    public double getNotifyLon() {
        return notifyLon;
    }

    public void setNotifyLon(double notifyLon) {
        this.notifyLon = notifyLon;
    }

    public float getLocationAccuracy() {
        return locationAccuracy;
    }

    public void setLocationAccuracy(float locationAccuracy) {
        this.locationAccuracy = locationAccuracy;
    }

    public double getLocationMapLat() {
        return locationMapLat;
    }

    public void setLocationMapLat(double locationMapLat) {
        this.locationMapLat = locationMapLat;
    }

    public double getLocationMapLon() {
        return locationMapLon;
    }

    public void setLocationMapLon(double locationMapLon) {
        this.locationMapLon = locationMapLon;
    }

    public float getMapZoom() {
        return mapZoom;
    }

    public void setMapZoom(float mapZoom) {
        this.mapZoom = mapZoom;
    }

    public double getMapCenterLat() {
        return mapCenterLat;
    }

    public void setMapCenterLat(double mapCenterLat) {
        this.mapCenterLat = mapCenterLat;
    }

    public double getMapCenterLon() {
        return mapCenterLon;
    }

    public void setMapCenterLon(double mapCenterLon) {
        this.mapCenterLon = mapCenterLon;
    }

    public boolean isReal() {
        return isReal;
    }

    public void setReal(boolean real) {
        isReal = real;
    }

    public double getLocationGPStoMapLat() {
        return locationGPStoMapLat;
    }

    public void setLocationGPStoMapLat(double locationGPStoMapLat) {
        this.locationGPStoMapLat = locationGPStoMapLat;
    }

    public double getLocationGPStoMapLon() {
        return locationGPStoMapLon;
    }

    public void setLocationGPStoMapLon(double locationGPStoMapLon) {
        this.locationGPStoMapLon = locationGPStoMapLon;
    }
}
