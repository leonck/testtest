///*
// * Copyright 2017 lizhaotailang
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.hubsan.swifts.activitis;
//
//import android.content.Context;
//import android.graphics.Point;
//import android.support.annotation.NonNull;
//import android.support.v4.content.ContextCompat;
//import android.util.Log;
//
//import com.MAVLink.common.msg_mission_item;
//import com.MAVLink.enums.MAV_CMD;
//import com.amap.api.maps.AMap;
//import com.amap.api.maps.model.BitmapDescriptorFactory;
//import com.amap.api.maps.model.LatLng;
//import com.amap.api.maps.model.Marker;
//import com.amap.api.maps.model.MarkerOptions;
//import com.amap.api.maps.model.Polyline;
//import com.amap.api.maps.model.PolylineOptions;
//import com.hubsan.swifts.R;
//import com.hubsan.swifts.TestShow.TestShowContract;
//import com.hubsan.swifts.mapUtils.MapProjection;
//import com.hubsan.swifts.mapUtils.marks.PolygonPoint;
//import com.hubsansdk.application.HubsanApplication;
//import com.hubsansdk.application.HubsanHandleMessageApp;
//import com.hubsansdk.drone.HubsanDrone;
//import com.hubsansdk.utils.LogX;
//
//import java.util.List;
//
///**
// * Created by lizhaotailang on 2016/12/27.
// */
//
//public class MapPresenter implements MainContract.MapPresenter {
//    Context context;
//    MainContract.MapView view;
//    AMap aMap;
//    List<LatLng> points;
//    private Polyline missionPath;
//    HubsanDrone drone = ((HubsanHandleMessageApp) HubsanApplication.getApplication()).drone;
//
//    public MapPresenter(@NonNull Context context, @NonNull MainContract.MapView view) {
//        this.context = context;
//        this.view = view;
//        this.view.setPresenter(this);
//    }
//
//    @Override
//    public void start() {
//    }
//
//    @Override
//    public void clean() {
//        aMap.clear();
//    }
//
//    @Override
//    public void requestData() {
//    }
//
//    public void onPathFinished(List<Point> path) {
//        aMap = view.getSupperLinMap();
//        if (aMap == null) {
//            return;
//        }
//        points = MapProjection.projectPathIntoMap(path, aMap);
//
//        if (points!=null&&points.size()>0){
//            sendPoints(points);
//        }
//        addMarks(points);
//    }
//
//    public void sendPoints(List<LatLng> points){
//        for (int i=0;i<points.size();i++){
//            msg_mission_item mMissionItem = new msg_mission_item();
//            mMissionItem.command = MAV_CMD.MAV_CMD_NAV_WAYPOINT; // 航点指令
////            mMissionItem.param1 = data.get(i).param1; // 悬停时间
////            mMissionItem.param2 = data.get(i).param2 * 10; // speed
//            mMissionItem.param3 = points.size(); // 航点个数
//            mMissionItem.x = (float) points.get(i).latitude;
//            mMissionItem.y =  (float) points.get(i).longitude;
////            mMissionItem.z = data.get(i).z;
////            index = i + 1;
//            LogX.e("发送的数据：（"+(float) points.get(i).latitude+","+ (float) points.get(i).longitude+")   ");
//            drone.mavLinkClient.sendMavPacket(mMissionItem.pack());
//        }
//    }
//
//    public void addMarks(List<LatLng> newPath) {
//        for (int i = 0, size = newPath.size(); i < size; i++) {
//            MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_wp_map))
//                    .position(newPath.get(i))
//                    .draggable(true)
//                    .anchor(0.5f, 0.5f); //设置Marker覆盖物的锚点比例。
//            Marker marker = aMap.addMarker(markerOption);
//            PolygonPoint p = new PolygonPoint(newPath.get(i).latitude, newPath.get(i).longitude);
//        }
//        update();
//    }
//
//    public void update() {
//        PolylineOptions flightPath = new PolylineOptions();
//        flightPath.color(ContextCompat.getColor(context, R.color.hubasn_connection_status_true_color)).setUseTexture(true);
//        flightPath.addAll(points);
//        missionPath = aMap.addPolyline(flightPath);
//        missionPath.setPoints(points);
//        missionPath.setVisible(true);
//    }
//
//}
