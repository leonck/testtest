package com.hubsansdk.drone.bean;

import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces.DroneEventsType;
import com.hubsansdk.drone.HubsanDroneVariable;

/**
 * 控制相机的操作类
 * Created by shengkun.cheng on 2017/1/22.
 */

public class HCamera extends HubsanDroneVariable {


    public HCamera(HubsanDrone myDrone) {
        super(myDrone);
    }

    /**
     * 切换拍照模式
     */
    public boolean changePhotographMode() {
        if (myDrone.sdCardStatus.isSdcardStatus() == true) {
            myDrone.mavLinkSendMessage.sendPhotographMode();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 切换录像模式
     */
    public boolean changeRecordingMode() {
        if (myDrone.sdCardStatus.isSdcardStatus() == true) {
            myDrone.mavLinkSendMessage.sendRecordingMode();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 拍照
     */
    public boolean startPhotograph() {
        //判断SDCard时候存在
        if (myDrone.sdCardStatus.isSdcardStatus() == true) {
//            myDrone.mavLinkSendMessage.sendSDCardRequestd();
            //判断容量
            if (myDrone.sdCardStatus.getSd_surplus() <= 200) {
                //提示容量不足
                myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_SDCARD_MEMORY_DEFICIENCY);
                return false;
            } else {
                //执行拍照
                myDrone.mavLinkSendMessage.sendPhotograph();
                return true;
            }
        } else {
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_NO_SDCARD);
//            myDrone.mavLinkSendMessage.sendSDCardRequestd();
            return false;
        }
    }

    /**
     * 开始录像
     */
    public boolean startVideotape() {
        //判断SDCard时候存在
        if (myDrone.sdCardStatus.isSdcardStatus() == true) {
//            myDrone.mavLinkSendMessage.sendSDCardRequestd();
            //判断容量
            if (myDrone.sdCardStatus.getSd_surplus() <= 200) {
                //提示容量不足
                myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_SDCARD_MEMORY_DEFICIENCY);
                return false;
            } else {
                if (myDrone.airMode.isRecord() == false) {
                    //执行录像
                    myDrone.mavLinkSendMessage.sendRecordingBegin();
                    return true;
                } else {
                    myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_YES_RECORD_NOYIFY);
                    return false;
                }
            }
        } else {
//            myDrone.mavLinkSendMessage.sendSDCardRequestd();
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_NO_SDCARD);
            return false;
        }
    }

    /**
     * 停止录像
     */
    public boolean stopVideotape() {
        //判断SDCard时候存在
        if (myDrone.sdCardStatus.isSdcardStatus() == true) {
            if (myDrone.airMode.isRecord() == true) {
                //执行停止录像
                myDrone.mavLinkSendMessage.sendRecordingStop();
                return true;
            } else {
                return false;
            }
        } else {
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_NO_SDCARD);
            return false;
        }
    }
}
