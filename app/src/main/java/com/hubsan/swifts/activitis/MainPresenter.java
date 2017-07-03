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
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.Surface;
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
import com.utils.Utils;

import java.util.List;

/**
 * @describe 逻辑处理界面
 * @anthor leon.li
 * @time 2017/7/3 11:16
 * @chang 11:16
 */
class MainPresenter implements MainContract.Presenter, HubsanDroneInterfaces.OnDroneListener {
    private Context context;
    private MainContract.View view;
    private HubsanDrone drone = ((HubsanHandleMessageApp) HubsanApplication.getApplication()).drone;
    private SwiftsApplication app;
    private Mission mission;

    MainPresenter(@NonNull Context context, @NonNull MainContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        drone.events.addDroneListener(this);
        app = (SwiftsApplication) SwiftsApplication.getApplication();
        this.mission = app.mission;
    }

    @Override
    public void start() {
    }

    @Override
    public void onPathFinished(List<Point> path) {
        AMap aMap = view.getAMap();
        if (aMap == null) {
            return;
        }
        List<LatLng> points = MapProjection.projectPathIntoMap(path, aMap);
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
    private void sendGuideFocus() {
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

    private void setAirMarkerLoction() {
        view.addMMarkerMap(Utils.sub(drone.airGPS.getAirLat(), drone.locationGPS.getDifferenceLat()), Utils.sub(drone.airGPS.getAirLon(), drone.locationGPS.getDifferenceLon()), 0, true);
    }

//    @Override
//    public void onDroneEvent(HubsanDroneInterfaces.DroneEventsType event, HubsanDrone drone) {
//        switch (event) {
//            case HUBSAN_LAT_LON:
//                setAirMarkerLoction();//更新飞机位置
//                break;
//        }
//    }

    /**
     * @describe  操控界面指令监听
     * @param event
     * @param drone
     */
    @Override
    public void onDroneEvent(HubsanDroneInterfaces.DroneEventsType event, HubsanDrone drone) {
        switch (event) {
            case HUBSAN_BATTERY://电量
                Message batteryMsg = new Message();
                batteryMsg.what = 1;
                view.receiveInstructions(batteryMsg);
//                if (mMyHandler != null) {
//                    mMyHandler.sendMessage(batteryMsg);
//                }
                break;
            case HUBSAN_BASE_VALUE://高度 距离
                Message heightMsg = new Message();
                heightMsg.what = 2;
                view.receiveInstructions(heightMsg);
//                if (mMyHandler != null) {
//                    mMyHandler.sendMessage(heightMsg);
//                }
                break;
            case HUBSAN_AIR_ALTITUDE://飞机高度
                Message airHightMsg = new Message();
                airHightMsg.what = 3;
                view.receiveInstructions(airHightMsg);
//                if (mMyHandler != null) {
//                    mMyHandler.sendMessage(airHightMsg);
//                }
                break;
            case HUBSAN_LAT_LON://飞机经纬度
                Message lonMsg = new Message();
                lonMsg.what = 4;
                view.receiveInstructions(lonMsg);
//                if (mMyHandler != null) {
//                    mMyHandler.sendMessage(lonMsg);
//                }
                break;
            case HUBSAN_YES_FOLLOW_MODE: //开启跟随

                break;
            case HUBSAN_NO_FOLLOW_MODE: //关闭跟随

                break;
            case HUBSAN_AIL_MODE:// 飞机所有状态模式
                Message airModeMsg = new Message();
                airModeMsg.what = 122;
                view.receiveInstructions(airModeMsg);

//                if (mMyHandler != null) {
//                    mMyHandler.sendMessage(airModeMsg);
//                }
                break;
            case HUBSAN_GPS_MANAGER://
                Message localMsg = new Message();
                localMsg.what = 123;
                view.receiveInstructions(localMsg);

//                if (mMyHandler != null) {
//                    mMyHandler.sendMessage(localMsg);
//                }
                break;
            case HUBSAN_GPS_NUMBER://
                Message gpsMsg = new Message();
                gpsMsg.what = 124;
                view.receiveInstructions(gpsMsg);

//                if (mMyHandler != null) {
//                    mMyHandler.sendMessage(gpsMsg);
//                }
                break;
            case HUBSAN_OPEN_FOLLOW_ME_FAILED: //开启跟随失败
                Message followMsg = new Message();
                followMsg.what = 126;
                view.receiveInstructions(followMsg);

//                if (mMyHandler != null) {
//                    mMyHandler.sendMessage(followMsg);
//                }
                break;
            case HUBSAN_OPEN_SURROUND_FAILED: //开启环绕失败
                Message arrowMsg = new Message();
                arrowMsg.what = 127;
                view.receiveInstructions(arrowMsg);

//                if (mMyHandler != null) {
//                    mMyHandler.sendMessage(arrowMsg);
//                }
                break;
            case HUBSAN_OPEN_WAYPOINT_FAILED: //开启航点失败
                Message waypointMsg = new Message();
                waypointMsg.what = 128;
                view.receiveInstructions(waypointMsg);

//                if (mMyHandler != null) {
//                    mMyHandler.sendMessage(waypointMsg);
//                }
                break;
            case HUBSAN_JOYSTICK_VALUE://摇杆数据
                Message rockMsg = new Message();
                rockMsg.what = 129;
                view.receiveInstructions(rockMsg);//setRockerData();
                break;
        }
    }

    @Override
    public void deleteAllWaypoint() {
        List<MissionItem> mItems = mission.getItems();
        app.mission.removeWaypoints(mItems);
        clearWaypointLine();
    }

    @Override
    public void deleteWaypoint(MissionItem item) {
        mission.removeWaypoint(item);
        mission.clearSelection();
        int missionSize = item.getMission().getItems().size();
        if (missionSize <= 1) {
            clearWaypointLine();
        }
    }

    private void clearWaypointLine() {
        view.clearWaypointLine();
    }

    /**
     * @time 2017/7/1  17:41
     * @describe 添加兴趣点
     */
    @Override
    public void addFocus(LatLng point) {
        //添加Focus  就是飞机摄像头对准这个点绕行
//        int height = PreferenceUtils.getPrefInt(context, Constants.SETTING_FLIGHTDATA_DEFAULT_HEIGHT, 30);
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

    //==========================播放视频===============================
    //播放图传
    public void playCamera(Surface surface) {
        app.videoplayer.setSurface(surface);
        surface.release();
        app.videoplayer.surfaceChanged();
        LogX.e("*************等待一秒*****************");
        mStartVideoHandler.postDelayed(mStartVideoRunnable, 1000);
    }

    private Handler mStartVideoHandler = new Handler();
    private Runnable mStartVideoRunnable = new Runnable() {
        @Override
        public void run() {
            app.videoplayer.start(Constants.TCP_IMAGE_TRANSFER);
            LogX.e("播放视频");
            mStartVideoHandler.removeCallbacks(mStartVideoRunnable);
        }
    };
    //==========================播放视频end============================


}
