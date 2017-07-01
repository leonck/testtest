package com.hubsansdk.mavlink;

import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.common.msg_altitude;
import com.MAVLink.common.msg_attitude;
import com.MAVLink.common.msg_battery_status;
import com.MAVLink.common.msg_command_ack;
import com.MAVLink.common.msg_command_long;
import com.MAVLink.common.msg_gps_raw_int;
import com.MAVLink.common.msg_gps_status;
import com.MAVLink.common.msg_heartbeat;
import com.MAVLink.common.msg_local_position_ned;
import com.MAVLink.common.msg_param_value;
import com.MAVLink.common.msg_timesync;
import com.MAVLink.hubsan.msg_hubsan_ack;
import com.MAVLink.hubsan.msg_hubsan_quad_status;
import com.MAVLink.hubsan.msg_hubsan_sd_status;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces;

/**
 * 管理消息的
 */
public class MavLinkMsgHandler {

    private HubsanDrone drone;
    private int receiverAirBssid = 0;

    public MavLinkMsgHandler(HubsanDrone drone) {
        this.drone = drone;
    }

    /**
     * 接收到的信息
     *
     * @param msg
     */
    public void receiveAirData(MAVLinkMessage msg) {
        switch (msg.msgid) {
            case msg_heartbeat.MAVLINK_MSG_ID_HEARTBEAT: // 心跳
                msg_heartbeat heartbeat = (msg_heartbeat) msg;

                drone.airHeartBeat.doHeartBeat(heartbeat);
                break;
            case msg_command_long.MAVLINK_MSG_ID_COMMAND_LONG: // 指南针地磁校准
                msg_command_long command_long = (msg_command_long) msg;
                int par6 = (int) command_long.param6;
                int par2 = (int) command_long.param2;
                drone.compassCalibration.setCompassCalibrationStatus(par2, par6);
                break;
            case msg_battery_status.MAVLINK_MSG_ID_BATTERY_STATUS:// 电量
                msg_battery_status battery = (msg_battery_status) msg;
                byte mBattery = battery.battery_remaining;
                drone.battery.setBatteryValue(mBattery);
                break;
            case msg_hubsan_sd_status.MAVLINK_MSG_ID_HUBSAN_SD_STATUS: // SDCard
                msg_hubsan_sd_status sdStatus = (msg_hubsan_sd_status) msg;
                float capacity = sdStatus.sd_capacity;// 总容量 MB
                short status = sdStatus.sd_status;// 有无sd
                float surplus = sdStatus.sd_surplus;// 剩余容量
                drone.sdCardStatus.setSDCardStatus(capacity, status, surplus);
                break;
            case msg_gps_status.MAVLINK_MSG_ID_GPS_STATUS:// 获取GPS
                msg_gps_status gps = (msg_gps_status) msg;
                drone.airGPS.setGpsNumber(gps.satellites_visible);
                break;
            case msg_attitude.MAVLINK_MSG_ID_ATTITUDE:// 姿态
                msg_attitude m_att = (msg_attitude) msg;
                float roll = m_att.roll; // 翻滚
                float pitch = m_att.pitch; // 俯仰
                float yaw = m_att.yaw; // 偏航
                drone.attitude.setAttitude(roll, pitch, yaw);
                break;
            case msg_local_position_ned.MAVLINK_MSG_ID_LOCAL_POSITION_NED:// 获取飞机的高度基本参数
                msg_local_position_ned ned = (msg_local_position_ned) msg;
                float x = ned.x;// 距离
                float y = ned.y;
                float z = ned.z;
                float vx = ned.vx;// 速度
                float vy = ned.vy;
                float vz = ned.vz;
                drone.airBaseParameters.setBaseValue(x, y, z, vx, vy, vz);
                break;
            case msg_altitude.MAVLINK_MSG_ID_ALTITUDE:// 获取高度
                msg_altitude altitude = (msg_altitude) msg;
                float relative = altitude.altitude_amsl;// 相对home的高度
                drone.airBaseParameters.setAltitude(relative);
                break;
            case msg_gps_raw_int.MAVLINK_MSG_ID_GPS_RAW_INT:// 获取经纬度
                msg_gps_raw_int mGps = (msg_gps_raw_int) msg;
                float mLat = (float) mGps.lat / 10000000;
                float mLon = (float) mGps.lon / 10000000;
                drone.airGPS.setAirLatLon(mLat, mLon);
                break;
            case msg_command_ack.MAVLINK_MSG_ID_COMMAND_ACK:// 所有消息的答复
                msg_command_ack mag_ack = (msg_command_ack) msg;
                drone.airRelayAck.setAirACK(mag_ack.command);
                break;
            case msg_hubsan_ack.MAVLINK_MSG_ID_HUBSAN_ACK://自定义消息答复
                msg_hubsan_ack hubsan_ack = (msg_hubsan_ack) msg;
                drone.airRelayAck.setTimeSync(hubsan_ack);
                break;
            case msg_timesync.MAVLINK_MSG_ID_TIMESYNC:// 录像时间
                msg_timesync mMsg_timesync = (msg_timesync) msg;
                long recTime = mMsg_timesync.ts1;
                drone.sdCardStatus.setRecTime(recTime);
                break;
            case msg_param_value.MAVLINK_MSG_ID_PARAM_VALUE:// 获取飞机版本信息的基本参数
                msg_param_value param_value = (msg_param_value) msg;
                drone.airBaseParameters.setAirSoftVersionBattery(param_value);
                break;
            case msg_hubsan_quad_status.MAVLINK_MSG_ID_HUBSAN_QUAD_STATUS:// 接收到有头或者无头，和录像状态
                msg_hubsan_quad_status quad_status = (msg_hubsan_quad_status) msg;
                drone.airMode.setCurrentMode(quad_status);
                break;
        }
    }


    /**
     * 中继器的信息
     *
     * @param msg
     */
    public void receiveRelayData(MAVLinkMessage msg) {
        if (msg.compid == 191) {
            msg_param_value mms = (msg_param_value) msg;
            drone.relayHeartBeat.setRelayerVersion(mms);
        } else {
            switch (msg.msgid) {
                case msg_heartbeat.MAVLINK_MSG_ID_HEARTBEAT:
                    msg_heartbeat heartbeat = (msg_heartbeat) msg;
                    drone.relayHeartBeat.setRelayHeartBeat(heartbeat);
                    break;
                case msg_command_ack.MAVLINK_MSG_ID_COMMAND_ACK:// 所有消息的答复
                    msg_command_ack result = (msg_command_ack) msg;
                    drone.airRelayAck.setRelayACK(result.result);
                    break;
                case msg_timesync.MAVLINK_MSG_ID_TIMESYNC: // 中继器第二个MAC地址的获取
                    msg_timesync msg_timesync_t = (msg_timesync) msg;

                    drone.relayerBSSID.setRelayerMac1((msg_timesync_t.tc1 >> 40) & 0xff);
                    drone.relayerBSSID.setRelayerMac2((msg_timesync_t.tc1 >> 32) & 0xff);
                    drone.relayerBSSID.setRelayerMac3((msg_timesync_t.tc1 >> 24) & 0xff);
                    drone.relayerBSSID.setRelayerMac4((msg_timesync_t.tc1 >> 16) & 0xff);
                    drone.relayerBSSID.setRelayerMac5((msg_timesync_t.tc1 >> 8) & 0xff);
                    drone.relayerBSSID.setRelayerMac6(msg_timesync_t.tc1 & 0xff);
                    drone.relayerBSSID.setRelayerTc1(msg_timesync_t.tc1);

                    // 手机的Mac地址
                    drone.airPhoneBSSID.setAirPhoneMac1((msg_timesync_t.ts1 >> 40) & 0xff);
                    drone.airPhoneBSSID.setAirPhoneMac2((msg_timesync_t.ts1 >> 32) & 0xff);
                    drone.airPhoneBSSID.setAirPhoneMac3((msg_timesync_t.ts1 >> 24) & 0xff);
                    drone.airPhoneBSSID.setAirPhoneMac4((msg_timesync_t.ts1 >> 16) & 0xff);
                    drone.airPhoneBSSID.setAirPhoneMac5((msg_timesync_t.ts1 >> 8) & 0xff);
                    drone.airPhoneBSSID.setAirPhoneMac6(msg_timesync_t.ts1 & 0xff);
                    drone.airPhoneBSSID.setAirPhoneTs1(msg_timesync_t.ts1);
                    drone.airConnectionStatus.setReceiveBSSID(true);

                    //第二个中继器发过来中继器的Mac地址：2:126:86:11:247:220
                    //第二个中继器发过来手机的Mac地址：148:254:34:182:101:169

                    //第二个中继器发过来中继器的Mac地址：2:126:86:11:247:220
                    //第二个中继器发过来手机的Mac地址：208:101:202:66:199:70

                    break;
                case msg_param_value.MAVLINK_MSG_ID_PARAM_VALUE:// 获取中继器WIFI列表
                    msg_param_value wifiList = (msg_param_value) msg;
                    drone.airRelayAck.setRelayWifiList(wifiList);
                    break;
                case 19://中继器版本号

                    break;
            }
        }

    }

    public void setMavlinkDeful() {
        receiverAirBssid = 0;
        drone.airConnectionStatus.setReceiveBSSID(false);
    }

}
