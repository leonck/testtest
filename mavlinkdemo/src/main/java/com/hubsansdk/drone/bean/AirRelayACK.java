package com.hubsansdk.drone.bean;

import android.os.Handler;
import android.os.Message;

import com.MAVLink.common.msg_mission_item;
import com.MAVLink.common.msg_param_value;
import com.MAVLink.common.msg_timesync;
import com.MAVLink.enums.MAV_CMD;
import com.MAVLink.enums.MAV_RESULT;
import com.MAVLink.hubsan.msg_hubsan_ack;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces.DroneEventsType;
import com.hubsansdk.drone.HubsanDroneVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * 飞机返回的答复消息
 *
 * @author shengkun.cheng
 */
public class AirRelayACK extends HubsanDroneVariable {
    // 设置中继器是否成功
    private int relayerSettingIsSuccess;
    private boolean relayEquipmentIsSuccess;
    private boolean sendWaypointSuccess;
    private int relayerListSize;
    private boolean isSyncTimeSuccess;
    //中继器的ssid
    private List<RelayWifiItem> relayWifiLists = new ArrayList<RelayWifiItem>();

    public AirRelayACK(HubsanDrone myDrone) {
        super(myDrone);
        // TODO Auto-generated constructor stub
    }

    public int getRelayerSettingIsSuccess() {
        return relayerSettingIsSuccess;
    }

    public void setRelayerSettingIsSuccess(int relayerSettingIsSuccess) {
        this.relayerSettingIsSuccess = relayerSettingIsSuccess;
    }

    public boolean isRelayEquipmentIsSuccess() {
        return relayEquipmentIsSuccess;
    }

    public boolean isSendWaypointSuccess() {
        return sendWaypointSuccess;
    }

    public int getRelayerListSize() {
        return relayerListSize;
    }

    public void setRelayerListSize(int relayerListSize) {
        this.relayerListSize = relayerListSize;
    }

    public List<RelayWifiItem> getRelayWifiLists() {
        return relayWifiLists;
    }

    public void setRelayWifiLists(List<RelayWifiItem> relayWifiLists) {
        this.relayWifiLists = relayWifiLists;
    }

    public boolean isSyncTimeSuccess() {
        return isSyncTimeSuccess;
    }

    public void setSyncTimeSuccess(boolean syncTimeSuccess) {
        isSyncTimeSuccess = syncTimeSuccess;
    }

    /**
     * 飞机返回的状态
     *
     * @param command
     */
    public void setAirACK(int command) {
        switch (command) {
            case 7:// wifi 修改状态
                break;
            case msg_mission_item.MAVLINK_MSG_ID_MISSION_ITEM:// 航点
                this.sendWaypointSuccess = true;
                myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_SEND_WAYPOINT_SUCCESS);
                break;
            case MAV_CMD.MAV_CMD_VIDEO_START_CAPTURE:// 认证成功
                myDrone.airRelayEquipmentCertification.setAuthentication(true);
                break;
            default:
                break;
        }
    }

    /**
     * 时间同步,航点设置成功
     *
     * @param hubsan_ack
     */
    public void setTimeSync(msg_hubsan_ack hubsan_ack) {
        switch (hubsan_ack.ack_msgid) {
            case msg_timesync.MAVLINK_MSG_ID_TIMESYNC:
                //时间同步
                if (hubsan_ack.param1 == MAV_RESULT.MAV_RESULT_ACCEPTED) {
                    //成功
                    this.isSyncTimeSuccess = true;
                } else if (hubsan_ack.param1 == MAV_RESULT.MAV_RESULT_FAILED) {
                    //失败
                    this.isSyncTimeSuccess = false;
                    mHandler.postDelayed(mRunnable, 1000);
                }
                break;
            case msg_mission_item.MAVLINK_MSG_ID_MISSION_ITEM:// 航点
                this.sendWaypointSuccess = true;
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (isSyncTimeSuccess == false) {
                        myDrone.mavLinkSendMessage.sendSysTime();
                    } else {
                        mHandler.removeCallbacks(mRunnable);
                    }
                    break;
                default:
                    mHandler.removeCallbacks(mRunnable);
                    break;
            }
        }
    };
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);
            mHandler.postDelayed(mRunnable, 1000);
        }
    };

    /**
     * 中继器返回的状态
     *
     * @param result
     */
    public void setRelayACK(int result) {
        switch (result) {
            case MAV_RESULT.MAV_RESULT_ACCEPTED: // wifi设置成功
                this.relayerSettingIsSuccess = 1;
                setRelayerSettingIsSuccess(1);
                break;
            case MAV_RESULT.MAV_RESULT_FAILED:// wifi设置失败
                this.relayerSettingIsSuccess = 0;
                setRelayerSettingIsSuccess(0);
                break;
            case MAV_CMD.MAV_CMD_VIDEO_START_CAPTURE:// 中继器认证成功
                myDrone.airRelayEquipmentCertification.setAuthentication(true);
                break;
            default:
                break;
        }
    }

    /**
     * 得到中继器WIFI列表
     *
     * @param wifi
     */
    public void setRelayWifiList(msg_param_value wifi) {
        String wifiList = new String(wifi.param_value2);
        int index = wifi.param_index;
        this.relayerListSize = index;
        //获取列表的个数
        RelayWifiItem relayWifiList = new RelayWifiItem();
        relayWifiList.setRelayWifiSize(wifi.param_index);
        relayWifiList.setRelayWifiListItem(wifiList);
        relayWifiLists.add(relayWifiList);
        if (index == relayWifiLists.size()) {
            setRelayWifiLists(relayWifiLists);
        }
    }

    /**
     * 清空list集合中的参数
     */
    public void cleanRelayWifiList() {
        if (relayWifiLists.size() > 0) {
            relayWifiLists.clear();
            myDrone.relayList.setRelayWifiSize(0);
        }
    }
}
