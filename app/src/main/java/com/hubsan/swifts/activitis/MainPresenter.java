/*
 * Copyright 2017 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hubsan.swifts.activitis;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.hubsan.swifts.SwiftsApplication;
import com.hubsan.swifts.drone.variables.mission.Mission;
import com.hubsan.swifts.drone.variables.mission.MissionItem;
import com.hubsan.swifts.mapUtils.MapCommonUtil;
import com.hubsan.swifts.mapUtils.MapProjection;
import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.application.HubsanHandleMessageApp;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces;
import com.hubsansdk.utils.LogX;
import com.utils.Constants;
import com.utils.PreferenceUtils;
import com.utils.Utils;

import java.util.List;



public class MainPresenter implements MainContract.Presenter, HubsanDroneInterfaces.OnDroneListener {
    private Context context;
    private MainContract.View view;
    private AMap aMap;
    private  List<LatLng> points;
    private HubsanDrone drone = ((HubsanHandleMessageApp) HubsanApplication.getApplication()).drone;
    private SwiftsApplication app;
    private Mission mission;

   /**
    * @anthor leon.li
    * @time 2017/7/1 17:38
    * @change
    * @chang 17:38
    * @describe  describe
    */
    public MainPresenter(@NonNull Context context, @NonNull MainContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        drone.events.addDroneListener(this);
        app = (SwiftsApplication)SwiftsApplication.getApplication();
        this.mission = app.mission;
    }

    @Override
    public void start() {
    }

    @Override
    public void onPathFinished(List<Point> path) {
        aMap = view.getAMap();
        if (aMap == null) {
            return;
        }
        points = MapProjection.projectPathIntoMap(path, aMap);
        app.mission.addWaypoints(points);
    }

    //提交航点之前 先提交兴趣点
    @Override
    public void uploadWayPoin() {
        sendGuideFocus();
        app.mission.sendMissionToAPM(drone);
    }

    /**
     * 发送兴趣点和焦点
     * 如果没有数据就发送0
     */
    public void sendGuideFocus() {
        int height = 30;
        if (app.localLatLon.getFocusLat() != 0 && app.localLatLon.getFocusLon() != 0) {
            drone.mavLinkSendMessage.sendFocus((float) app.localLatLon.getFocusLat(), (float) app.localLatLon.getFocusLon(), height);
        } else {
            drone.mavLinkSendMessage.sendFocus(0, 0, 0);
        }
    }

    @Override
    public void beginOrCloseWapPoin() { //开启航点
        if (drone.airConnectionStatus.isAirConnection()) {
            if (drone.airMode.isWaypointMode()) { //当前为航点模式 发送关闭指令
                drone.hubsanSendInstructionAir.sendModeInstruction(HubsanDroneInterfaces.AirAllModeType.HUBSAN_WAYPOINT_FLY, false);
            } else {
                drone.hubsanSendInstructionAir.sendModeInstruction(HubsanDroneInterfaces.AirAllModeType.HUBSAN_WAYPOINT_FLY, true);
            }
        } else {
            Toast.makeText(context, "设备未连接", Toast.LENGTH_SHORT).show();
        }
    }

    //校验传过来的经纬度跟飞机的经纬度进行计算得到火星坐标与标准坐标的差值
    @Override
    public void dealCalibration(double lat, double lon) {
        MapCommonUtil.computeDiffer(lat, lon, drone);
        setAirMarkerLoction();
    }

    public void setAirMarkerLoction() {
        view.addMMarkerMap(Utils.sub(drone.airGPS.getAirLat(), drone.locationGPS.getDifferenceLat()), Utils.sub(drone.airGPS.getAirLon(), drone.locationGPS.getDifferenceLon()), 0, true);
    }

    @Override
    public void onDroneEvent(HubsanDroneInterfaces.DroneEventsType event, HubsanDrone drone) {
        switch (event){
            case HUBSAN_LAT_LON:
                setAirMarkerLoction();//更新飞机位置
                break;
        }
    }

    @Override
    public void deleteAllWaypoint(){
        List<MissionItem> mItems = mission.getItems();
        app.mission.removeWaypoints(mItems);
        clearWaypointLine();
    }

    @Override
    public void deleteWaypoint(MissionItem item){
        mission.removeWaypoint(item);
        mission.clearSelection();
        int missionSize = item.getMission().getItems().size();
        if (missionSize <= 1) {
            clearWaypointLine();
        }
    }

    public void clearWaypointLine(){
        view.clearWaypointLine();
    }

    /**
     *  @time 2017/7/1  17:41
     *  @describe 添加兴趣点
     */
    @Override
    public void addFocus(LatLng point) {
        //添加Focus  就是飞机摄像头对准这个点绕行
        int height = PreferenceUtils.getPrefInt(context, Constants.SETTING_FLIGHTDATA_DEFAULT_HEIGHT, 30);
        app.cameraFocus.initialize(point);
        //得到两个经纬度并且加上差异值
        double lat = point.latitude + drone.locationGPS.getDifferenceLat();
        double lon = point.longitude + drone.locationGPS.getDifferenceLon();
        //发送focus点给飞机
//                drone.mavLinkSendMessage.sendFocus((float) lat, (float) lon, height);
        LogX.e("Focus lat:" + lat + "     ,lon:" + lon);
//        editorToolsFragment.isSendWaypointStatus(false);
        app.localLatLon.setFocusLat(lat);
        app.localLatLon.setFocusLon(lon);
//        setSubmitStatu();
    }

}
