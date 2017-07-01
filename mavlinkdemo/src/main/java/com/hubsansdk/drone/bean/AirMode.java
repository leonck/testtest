package com.hubsansdk.drone.bean;

import com.MAVLink.hubsan.msg_hubsan_quad_status;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces.DroneEventsType;
import com.hubsansdk.drone.HubsanDroneVariable;
import com.hubsansdk.utils.Constants;

/**
 * 飞机的所有模式
 *
 * @author shengkun.cheng
 */
public class AirMode extends HubsanDroneVariable {

    // true : 有头 false :无头
    private boolean isHeadMode;
    // true : 正在录像 false :没有录像
    private boolean isRecord;
    // 飞机的当前模式
    private int airCurrentMode;
    // 返航模式 true:返航 false :已取消返航
    private boolean isReturnBackMode;
    private boolean isWaypointMode;
    private boolean isFollowMode;
    private boolean isSurroundFly;
    private boolean isGPSMode;
    private byte headMode = 0, followMode = 0, surroundFlyMode = 0, waypointMode = 0, returnBackMode = 0;
    private int modeType = 0;
    private int modeTimeOut = 0;
    private msg_hubsan_quad_status quad_status;
    private boolean airLockStatus = false;
    private boolean testTag = false;

    public AirMode(HubsanDrone myDrone) {
        super(myDrone);
        // TODO Auto-generated constructor stub
    }

    public void setCurrentMode(msg_hubsan_quad_status quad_status) {
        short headMode = quad_status.quad_mode_status;// 1 ：无头 0：有头
        short cameraStatus = quad_status.recording_status; // 录像状态
        setQuad_status(quad_status);
        myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_AIL_MODE);
        if (cameraStatus == 1) {
            // 录像
            this.isRecord = true;
            setRecord(true);
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_YES_RECORD);
        } else {
            // 未录像
            this.isRecord = false;
            setRecord(false);
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_NO_RECORD);
        }

        // 这点判断特别重要,首先要判断是有头无头模式下的状态
        if (((headMode >> 6) & 0x01) == 1) {// 无头
            airCurrentMode = headMode - 64;
            this.isHeadMode = false;
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_NO_HEAD_MODE);
        } else {
            airCurrentMode = headMode;
            this.isHeadMode = true;
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_HEAD_MODE);
        }

        // 返航
        if (airCurrentMode == Constants.HUBSAN_RETURN_HOME) {
//            if (myDrone.airGPS.getGpsNumber() >= 6)
            this.isReturnBackMode = true;
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_YES_RETURN_BACK_MODE);
        } else {
            this.isReturnBackMode = false;
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_NO_RETURN_BACK_MODE);
        }

        // 航点模式
        if (airCurrentMode == Constants.HUBSAN_WAYPOINT_FLY) {
//            if (myDrone.airGPS.getGpsNumber() >= 6)
            this.isWaypointMode = true;
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_YES_WAYPOINTMODE);
        } else {
            this.isWaypointMode = false;
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_NO_WAYPOINTMODE);
        }

        // 跟随
        if (airCurrentMode == Constants.HUBSAN_FOLLOW_MODE) {
//            if (myDrone.airGPS.getGpsNumber() >= 6)
            this.isFollowMode = true;
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_YES_FOLLOW_MODE);
        } else {
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_NO_FOLLOW_MODE);
            this.isFollowMode = false;
        }

        // 环绕飞
        if (airCurrentMode == Constants.HUBSAN_SURROUND_FLY) {
//            if (myDrone.airGPS.getGpsNumber() >= 6)
            this.isSurroundFly = true;
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_YES_SURROUND_FLY);
        } else {
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_NO_SURROUND_FLY);
            this.isSurroundFly = false;
        }
        //定点模式==GPS模式
        if (airCurrentMode == Constants.HUBSAN_GPS_HOLD) {
            if (myDrone.airGPS.getGpsNumber() >= 6)
                this.isGPSMode = true;
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_GPS_MODE);
        } else {
            this.isGPSMode = false;
        }

        if (myDrone.airGPS.getGpsNumber() >= 6) {
            if (myDrone.airHeartBeat.isMotorStatus() == true) {
                //通知所有的模式
                myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_CURRENT_MODE);
            } else {
                // 电机锁定
                myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_MOTOR_STOP_MODE);
            }
        } else {
            // GPS 信号弱
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_WEAK_GPS);
        }
    }


    public boolean isFollowMode() {
        return isFollowMode;
    }

    public boolean isSurroundFly() {
        return isSurroundFly;
    }

    public boolean isReturnBackMode() {
        return isReturnBackMode;
    }

    public void setReturnBackMode(boolean returnBackMode) {
        isReturnBackMode = returnBackMode;
    }

    public void setWaypointMode(boolean waypointMode) {
        isWaypointMode = waypointMode;
    }

    public void setFollowMode(boolean followMode) {
        isFollowMode = followMode;
    }

    public void setSurroundFly(boolean surroundFly) {
        isSurroundFly = surroundFly;
    }

    public boolean isWaypointMode() {
        return isWaypointMode;
    }

    public boolean isHeadMode() {
        return isHeadMode;
    }

    public boolean isRecord() {
        return isRecord;
    }

    public int getAirCurrentMode() {
        return airCurrentMode;
    }

    public void setRecord(boolean record) {
        isRecord = record;
    }

    public boolean isGPSMode() {
        return isGPSMode;
    }

    public void setGPSMode(boolean GPSMode) {
        isGPSMode = GPSMode;
    }

    public msg_hubsan_quad_status getQuad_status() {
        return quad_status;
    }

    public void setQuad_status(msg_hubsan_quad_status quad_status) {
        this.quad_status = quad_status;
    }

    public boolean isAirLockStatus() {
        return airLockStatus;
    }

    public void setAirLockStatus(boolean airLockStatus) {
        this.airLockStatus = airLockStatus;
    }

    public boolean isTestTag() {
        return testTag;
    }

    public void setTestTag(boolean testTag) {
        this.testTag = testTag;
    }
}
