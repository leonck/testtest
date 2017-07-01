package com.hubsan.swifts.mapUtils;

import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.drone.HubsanDrone;
import com.utils.Utils;

/**
 * Created by leon.li on 2017/6/28.
 */

public class MapCommonUtil {
    /**
     * 计算经纬度差异
     *
     * @param mLoctionLat 地图经
     * @param mLoctionLon 地图纬
     */
    public static void computeDiffer(double mLoctionLat, double mLoctionLon, HubsanDrone drone) {
        //TODO 计算差值
        double difLats = Utils.sub(drone.airGPS.getAirLat(), mLoctionLat);//得到飞机和本地的经度差异参数
        double difLons = Utils.sub(drone.airGPS.getAirLon(), mLoctionLon);//得到飞机和本地的纬度差异参数
        drone.locationGPS.setDifferenceLat(difLats);
        drone.locationGPS.setDifferenceLon(difLons);
    }
}
