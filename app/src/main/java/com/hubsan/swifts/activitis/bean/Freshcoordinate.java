package com.hubsan.swifts.activitis.bean;

/**
 * Description
 *  保存飞机传递过来的经纬度
 * @author shengkun.cheng
 * @date 2016/9/14
 * @see [class/class#method]
 * @since [product/model]
 */
public class Freshcoordinate {
    private  double lat;
    private  double lon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
