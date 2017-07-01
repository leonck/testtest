//package com.hubsan.swifts.drone.variables;
//
//import com.amap.api.maps.model.LatLng;
//import com.hubsansdk.drone.HubsanDrone;
//import com.hubsansdk.drone.HubsanDroneVariable;
//
///**
// *获取GPS数据,不能删除此文件
// */
//public class GPS extends HubsanDroneVariable {
//    private LatLng position;
//    private LatLng positionOffset = new LatLng(0, 0);
//    private boolean iszeroexclude = true;
//    private int gps_number;
//    private double lat;
//    private double lon;
//
//
//    public double getLat() {
//        return lat;
//    }
//
//    public double getLon() {
//        return lon;
//    }
//
//    public int getGps_number() {
//        return gps_number;
//    }
//
//    public LatLng getPositionOffset() {
//        return positionOffset;
//    }
//
//    public void setPositionOffset(LatLng positionOffset) {
//        this.positionOffset = positionOffset;
//    }
//
//    public GPS(HubsanDrone myDrone) {
//        super(myDrone);
//    }
//
//    public boolean isPositionValid() {
//        return (position != null);
//    }
//
//    public LatLng getPosition() {
//        if (isPositionValid()) {
//            return position;
//        } else {
//            return new LatLng(0, 0);
//        }
//    }
//
//    public void setPosition(LatLng position) {
//        if (iszeroexclude) {
//            if (position.latitude == 0 && position.longitude == 0) {
////                myDrone.events.notifyDroneEvent(DroneEventsType.GPS);
//                return;
//            }
//        }
//        if (this.position != position) {
//            this.position = new LatLng(position.latitude + positionOffset.latitude, position.longitude + positionOffset.longitude);
//
//        }
////        myDrone.events.notifyDroneEvent(DroneEventsType.GPS);
//    }
//}