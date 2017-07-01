package com.hubsansdk.mavlink;

import com.MAVLink.common.msg_command_long;
import com.MAVLink.common.msg_gps_raw_int;
import com.MAVLink.common.msg_heartbeat;
import com.MAVLink.common.msg_local_position_ned;
import com.MAVLink.common.msg_mission_clear_all;
import com.MAVLink.common.msg_mission_item;
import com.MAVLink.common.msg_param_request_list;
import com.MAVLink.common.msg_param_request_read;
import com.MAVLink.common.msg_timesync;
import com.MAVLink.enums.MAV_CMD;
import com.MAVLink.enums.MAV_COMPONENT;
import com.MAVLink.enums.MAV_HUBSAN_CAMERA_CONTROL;
import com.MAVLink.hubsan.msg_hubsan_quad_mode;
import com.MAVLink.hubsan.msg_hubsan_sd_request;
import com.MAVLink.hubsan.msg_hubsan_set_wifi;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.utils.Constants;
import com.hubsansdk.utils.ParameterUtil;
import com.hubsansdk.utils.LogX;

import java.util.List;

/**
 * 主要处理MAVLink发送的消息类
 *
 * @author shengkun.cheng
 */
public class MavLinkSendMessage {
    private HubsanDrone drone;
    private byte followByte = 0;
    private byte surroundByte = 0;
    private byte waypointByte = 0;
    private byte basemode = 0;

    public MavLinkSendMessage(HubsanDrone drone) {
        this.drone = drone;
    }


    /**
     * 发送心跳
     */
    public void sendHeartbeat() {
        if (drone.airConnectionStatus.isReceiveBSSID())
            basemode |= 0x1;
        else
            basemode &= ~0x1;
        msg_heartbeat mHeartbeat = new msg_heartbeat();
        mHeartbeat.custom_mode = 1;
        mHeartbeat.type = (byte) 2;
        mHeartbeat.autopilot = (byte) 3;
        mHeartbeat.base_mode = basemode;
        mHeartbeat.system_status = (byte) 5;
        mHeartbeat.mavlink_version = (byte) 6;
        drone.mavLinkClient.sendMavPacket(mHeartbeat.pack());
    }

    /**
     * 给中继器发送心跳
     */
    public void sendRelayHeartbeat() {
        msg_heartbeat mHeartbeat = new msg_heartbeat();
        mHeartbeat.custom_mode = 1;
        mHeartbeat.type = (byte) 2;
        mHeartbeat.autopilot = (byte) 3;
        mHeartbeat.base_mode = (byte) 4;
        mHeartbeat.system_status = (byte) 5;
        mHeartbeat.mavlink_version = (byte) 6;
        drone.mavLinkClient.sendRelayPacker(mHeartbeat.pack());
    }

    /**
     * 拍照模式 Description
     *
     * @see [class/class#field/class#method]
     */
    public void sendPhotographMode() {
        msg_command_long mCommand_long = new msg_command_long();
        mCommand_long.command = MAV_CMD.MAV_CMD_HUBSAN_CAMERA;
        mCommand_long.param1 = MAV_HUBSAN_CAMERA_CONTROL.MAV_HUBSAN_CAMERA_ENTER_SNAP;
        mCommand_long.target_system = 1;
        mCommand_long.target_component = MAV_COMPONENT.MAV_COMP_ID_CAMERA;
        drone.mavLinkClient.sendMavPacket(mCommand_long.pack());
    }

    /**
     * 摄像模式 Description
     *
     * @see [class/class#field/class#method]
     */
    public void sendRecordingMode() {
        msg_command_long mCommand_long = new msg_command_long();
        mCommand_long.command = MAV_CMD.MAV_CMD_HUBSAN_CAMERA;
        mCommand_long.param1 = MAV_HUBSAN_CAMERA_CONTROL.MAV_HUBSAN_CAMERA_ENTER_RECORD;
        mCommand_long.target_system = 1;
        mCommand_long.target_component = MAV_COMPONENT.MAV_COMP_ID_CAMERA;
        drone.mavLinkClient.sendMavPacket(mCommand_long.pack());
    }

    /**
     * 拍照 Description
     *
     * @see [class/class#field/class#method]
     */
    public void sendPhotograph() {
        msg_command_long mCommand_long = new msg_command_long();
        mCommand_long.command = MAV_CMD.MAV_CMD_HUBSAN_CAMERA;
        mCommand_long.param1 = MAV_HUBSAN_CAMERA_CONTROL.MAV_HUBSAN_CAMERA_SNAP;
        mCommand_long.target_system = 1;
        mCommand_long.target_component = MAV_COMPONENT.MAV_COMP_ID_CAMERA;
        drone.mavLinkClient.sendMavPacket(mCommand_long.pack());
    }

    /**
     * 开始摄像 Description
     *
     * @see [class/class#field/class#method]
     */
    public void sendRecordingBegin() {
        msg_command_long mCommand_long = new msg_command_long();
        mCommand_long.command = MAV_CMD.MAV_CMD_HUBSAN_CAMERA;
        mCommand_long.param1 = MAV_HUBSAN_CAMERA_CONTROL.MAV_HUBSAN_CAMERA_RECORD_BEGIN;
        mCommand_long.target_system = 1;
        mCommand_long.target_component = MAV_COMPONENT.MAV_COMP_ID_CAMERA;
        drone.mavLinkClient.sendMavPacket(mCommand_long.pack());
    }

    /**
     * 停止摄像 Description
     *
     * @see [class/class#field/class#method]
     */
    public void sendRecordingStop() {
        msg_command_long mCommand_long = new msg_command_long();
        mCommand_long.command = MAV_CMD.MAV_CMD_HUBSAN_CAMERA;
        mCommand_long.param1 = MAV_HUBSAN_CAMERA_CONTROL.MAV_HUBSAN_CAMERA_RECORD_STOP;
        mCommand_long.target_system = 1;
        mCommand_long.target_component = MAV_COMPONENT.MAV_COMP_ID_CAMERA;
        drone.mavLinkClient.sendMavPacket(mCommand_long.pack());
    }

    /**
     * 开启一键返航
     */
    public void sendOpenReturnRouteBack() {
        msg_command_long mCommand_long = new msg_command_long();
        mCommand_long.command = MAV_CMD.MAV_CMD_NAV_RETURN_TO_LAUNCH;
        mCommand_long.target_system = 1;
        mCommand_long.param1 = 1;
        mCommand_long.target_component = MAV_COMPONENT.MAV_COMP_ID_CAMERA;
        drone.mavLinkClient.sendMavPacket(mCommand_long.pack());
    }

    /**
     * 关闭一键返航
     */
    public void sendCloseReturnRouteBack() {
        msg_command_long mCommand_long = new msg_command_long();
        mCommand_long.command = MAV_CMD.MAV_CMD_NAV_RETURN_TO_LAUNCH;
        mCommand_long.target_system = 1;
        mCommand_long.param1 = 0;
        mCommand_long.target_component = MAV_COMPONENT.MAV_COMP_ID_CAMERA;
        drone.mavLinkClient.sendMavPacket(mCommand_long.pack());
    }

    /**
     * Description一键降落
     *
     * @see [class/class#field/class#method]
     */
    public void sendTouchDown() {

        msg_command_long mCommand_long = new msg_command_long();
        mCommand_long.command = MAV_CMD.MAV_CMD_NAV_LAND;
        mCommand_long.target_system = 1;
        mCommand_long.target_component = MAV_COMPONENT.MAV_COMP_ID_CAMERA;
        drone.mavLinkClient.sendMavPacket(mCommand_long.pack());
    }

    /**
     * Description 将飞行的经纬度发送给飞机 0 0 0 0 0 0 0
     *
     * @param data
     * @see [class/class#field/class#method]
     */
    public boolean sendWaypointsContinue(List<msg_mission_item> data, HubsanDrone drone) {
        if (data != null) {
            int index = 0;
            for (int i = 0; i < data.size(); i++) {
                LogX.e("DifferenceLat():" + drone.locationGPS.getDifferenceLat() + "	,getDifferenceLon:" + drone.locationGPS.getDifferenceLon());
                float mLat = drone.parameterUtil.addFloat(Float.parseFloat(String.valueOf(data.get(i).x)), (float) drone.locationGPS.getDifferenceLat());
                float mLon = drone.parameterUtil.addFloat(Float.parseFloat(String.valueOf(data.get(i).y)), (float) drone.locationGPS.getDifferenceLon());
                msg_mission_item mMissionItem = new msg_mission_item();
                mMissionItem.command = MAV_CMD.MAV_CMD_NAV_WAYPOINT; // 航点指令
                mMissionItem.param1 = data.get(i).param1; // 悬停时间
                mMissionItem.param2 = data.get(i).param2 * 10; // speed
                mMissionItem.param3 = data.size(); // 航点个数
                mMissionItem.x = mLat;
                mMissionItem.y = mLon;
                mMissionItem.z = data.get(i).z;
                index = i + 1;
                LogX.e("lat:" + mLat + "  ,lon:" + mLon + "  .悬停时间:" + data.get(i).param1 +
                        "   ,speed:" + data.get(i).param2 * 10 + "    ,height:" + data.get(i).z + "   ,index:" + index);
                mMissionItem.seq = index; // 索引 此索引需要加1 要不然数据会有问题
                drone.mavLinkClient.sendMavPacket(mMissionItem.pack());
            }
            return true;
        }
        return false;
    }

    /**
     * Description 一键Guide
     *
     * @see [class/class#field/class#method]
     */
    public void sendGuide(float latitude, float longitude,float height) {

        msg_command_long mCommand_long = new msg_command_long();
        mCommand_long.command = MAV_CMD.MAV_CMD_OVERRIDE_GOTO;
        mCommand_long.target_system = 1;
        mCommand_long.target_component = MAV_COMPONENT.MAV_COMP_ID_CAMERA;
        mCommand_long.param5 = latitude;// lat 纬度
        mCommand_long.param6 = longitude;// long 经度
        mCommand_long.param7 = height;// 高度
        drone.mavLinkClient.sendMavPacket(mCommand_long.pack());
    }

    /**
     * 发送焦点
     *
     * @param lat
     * @param lon
     * @param altitude
     */
    public void sendFocus(float lat, float lon, float altitude) {
        msg_command_long mCommand_long = new msg_command_long();
        mCommand_long.command = MAV_CMD.MAV_CMD_NAV_ROI;
        mCommand_long.target_system = 1;
        mCommand_long.param5 = lat;// lat 纬度
        mCommand_long.param6 = lon;// long 经度
        mCommand_long.param7 = altitude;// 高度
        drone.mavLinkClient.sendMavPacket(mCommand_long.pack());
    }

    /**
     * Description 请求参数一个
     *
     * @see [class/class#field/class#method]
     */
    public void sendRequestParam() {

        // 长度不能超过15
        String str = "ModelName";
        msg_param_request_read mRequestParam = new msg_param_request_read();
        int i = 0;
        for (i = 0; i < str.getBytes().length; i++) {
            mRequestParam.param_id[i] = str.getBytes()[i];
        }
        mRequestParam.param_id[i] = '\0';
        mRequestParam.target_system = 1;
        mRequestParam.target_component = MAV_COMPONENT.MAV_COMP_ID_CAMERA;
        drone.mavLinkClient.sendMavPacket(mRequestParam.pack());
    }

    /**
     * Description 请求设备的基本参数,返回时list集合
     *
     * @see [class/class#field/class#method]
     */
    public void sendRequestParamList() {
        msg_param_request_list mRequestParam = new msg_param_request_list();
        mRequestParam.target_system = 1;
        mRequestParam.target_component = MAV_COMPONENT.MAV_COMP_ID_CAMERA;
        drone.mavLinkClient.sendMavPacket(mRequestParam.pack());
    }

    /**
     * Description 请求SDCard 状态
     *
     * @see [class/class#field/class#method]
     */
    public void sendSDCardRequestd() {
        msg_hubsan_sd_request mSdRequest = new msg_hubsan_sd_request();
        mSdRequest.target_system = 1;
        mSdRequest.target_component = MAV_COMPONENT.MAV_COMP_ID_CAMERA;
        drone.mavLinkClient.sendMavPacket(mSdRequest.pack());
    }

    /**
     * 请求飞控软件版本号
     */
    public void sendAutopilotVer() {
        msg_param_request_read request_read = new msg_param_request_read();
        String param = "AutopilotVer";
        request_read.param_id = param.getBytes();
        drone.mavLinkClient.sendMavPacket(request_read.pack());
    }

    /**
     * Description 发送系统时间
     *
     * @see [class/class#field/class#method]
     */
    public void sendSysTime() {
        msg_timesync mSystem = new msg_timesync();
        mSystem.tc1 = System.currentTimeMillis();
        drone.mavLinkClient.sendMavPacket(mSystem.pack());
    }

    /**
     * Description 发送有头或者无头模式
     *
     * @see [class/class#field/class#method]
     */
    public void sendHeadModel(short status) {
        msg_hubsan_quad_mode quadMode = new msg_hubsan_quad_mode();
        quadMode.hubsan_quad_mode = status;
        LogX.e("模式指令："+quadMode.toString());
        drone.mavLinkClient.sendMavPacket(quadMode.pack());
    }

    /**
     * Description 发送修改飞机WIFI密码和用户名 密码只能是number
     *
     * @see [class/class#field/class#method]
     */
    public void sendSetWifi(String key, String ssid) {
        msg_hubsan_set_wifi wifiValue = new msg_hubsan_set_wifi();
        wifiValue.setWifi_Key(key);
        wifiValue.setWifi_Ssid(ssid);
        drone.mavLinkClient.sendRelayPacker(wifiValue.pack());
    }

    /**
     * Description一键返航
     *
     * @see [class/class#field/class#method]
     */
    public void sendReturnRouteBack(int tag) {
        msg_command_long mCommand_long = new msg_command_long();
        mCommand_long.command = MAV_CMD.MAV_CMD_NAV_RETURN_TO_LAUNCH;
        mCommand_long.target_system = 1;
        mCommand_long.param1 = tag;
        mCommand_long.target_component = MAV_COMPONENT.MAV_COMP_ID_CAMERA;
        drone.mavLinkClient.sendMavPacket(mCommand_long.pack());
        LogX.e("一键返航");
    }

    /**
     * 删除已存在的waypoint
     */
    public void sendCancelWaypoint() {
        /* 发送之前清除飞机所有航点数据 */
        msg_mission_clear_all clear_all = new msg_mission_clear_all();
        drone.mavLinkClient.sendMavPacket(clear_all.pack());
    }

    /**
     * 发送手机GPS数据
     *
     * @param lat       纬度
     * @param lon       经度
     * @param gpsNumber GPS的数量
     */
    public void sendGPSStatus(int lat, int lon, short gpsNumber) {

        msg_gps_raw_int gps_raw_int = new msg_gps_raw_int();
        gps_raw_int.lat = lat;
        gps_raw_int.lon = lon;
        gps_raw_int.satellites_visible = gpsNumber;
        drone.mavLinkClient.sendMavPacket(gps_raw_int.pack());
        LogX.e("发送GPS数据"+ lat +"  "+ lon+"  "+gpsNumber+"消息编号："+gps_raw_int.msgid);
    }


    public void sendMacHeartbeat() {
        msg_heartbeat mHeartbeat = new msg_heartbeat();
        mHeartbeat.custom_mode = 1;

    }

    /**
     * 上锁的时候降落
     */
    public void sendBackAir() {
        msg_command_long commandLong = new msg_command_long();
        commandLong.command = MAV_CMD.MAV_CMD_NAV_LAND;
        drone.mavLinkClient.sendMavPacket(commandLong.pack());
    }

    /**
     * 请求中继器版本信息
     */
    public void sendRequesRelayVersion() {
        msg_param_request_list request_list = new msg_param_request_list();
        request_list.compid = 191;// 获取版本信息
        drone.mavLinkClient.sendRelayPacker(request_list.pack());
    }

    /**
     * 开启环绕模式
     */
    public void sendOpenSurroundMode() {
        surroundByte |= Constants.HUBSAN_SURROUND_FLY;
        msg_hubsan_quad_mode quadMode = new msg_hubsan_quad_mode();
        quadMode.hubsan_quad_mode = surroundByte;
        drone.mavLinkClient.sendMavPacket(quadMode.pack());
    }

    /**
     * 发送0 取消跟随模式
     */
    public void sendCancelFollowMeMode() {
        drone.locationGPS.setLocationLat(0);
        drone.locationGPS.setLocationLon(0);
        drone.mavLinkSendMessage.sendGPSStatus(0, 0, (short) 0);
    }

    /**
     * 关闭环绕模式
     */
    public void sendCloseSurroundMode() {
        surroundByte &= ~Constants.HUBSAN_SURROUND_FLY;
        msg_hubsan_quad_mode quadMode = new msg_hubsan_quad_mode();
        quadMode.hubsan_quad_mode = surroundByte;
        drone.mavLinkClient.sendMavPacket(quadMode.pack());
    }

    /**
     * 开启跟飞模式
     */
    public void sendOpenFollowMeMode() {
        followByte |= Constants.HUBSAN_FOLLOW_MODE;
        msg_hubsan_quad_mode quadMode = new msg_hubsan_quad_mode();
        quadMode.hubsan_quad_mode = followByte;
        drone.mavLinkClient.sendMavPacket(quadMode.pack());
    }

    /**
     * 关闭跟飞模式
     */
    public void sendCloseFollowMeMode() {
        followByte &= ~Constants.HUBSAN_FOLLOW_MODE;
        msg_hubsan_quad_mode quadMode = new msg_hubsan_quad_mode();
        quadMode.hubsan_quad_mode = followByte;
        drone.mavLinkClient.sendMavPacket(quadMode.pack());
    }

    /**
     * 开启航点模式
     */
    public void sendOpenWaypointMode() {
        waypointByte |= Constants.HUBSAN_WAYPOINT_FLY;
        msg_hubsan_quad_mode quadMode = new msg_hubsan_quad_mode();
        quadMode.hubsan_quad_mode = waypointByte;
        drone.mavLinkClient.sendMavPacket(quadMode.pack());
    }

    /**
     * 关闭航点模式
     */
    public void sendCloseWaypointMode() {
        waypointByte &= ~Constants.HUBSAN_WAYPOINT_FLY;
        msg_hubsan_quad_mode quadMode = new msg_hubsan_quad_mode();
        quadMode.hubsan_quad_mode = waypointByte;
        drone.mavLinkClient.sendMavPacket(quadMode.pack());
    }

    /**
     * 中继器绑定的时候,飞机未绑定 在飞机未认证的时候发送给 中继器
     */
    public void sendRelayEquMavLink() {
        msg_command_long command_long = new msg_command_long();
        command_long.param1 = 2;
        command_long.command = MAV_CMD.MAV_CMD_VIDEO_START_CAPTURE;
        // 发给中继器
        drone.mavLinkClient.sendRelayPacker(command_long.pack());
    }


    /**
     * 发送请求中继器WIFI列表
     */
    public void sendRequestWifiList() {
        msg_param_request_list request_list = new msg_param_request_list();
        request_list.compid = 190;// wifi列表
        drone.mavLinkClient.sendRelayPacker(request_list.pack());
    }

    /**
     * 发送飞机距离给中继器
     *
     * @param distance
     */
    public void sendAirDistance(float distance) {
        msg_local_position_ned msg_local = new msg_local_position_ned();
        msg_local.x = distance;
        drone.mavLinkClient.sendRelayPacker(msg_local.pack());
    }


    /**
     * 中继器未绑定的时候,飞机绑定 发送飞机的BSSID 地址给中继器绑定
     */
    public void sendAirBSSIDRelay() {
        msg_timesync mt = new msg_timesync();
        long tc1 = ((long) (drone.airBSSID.getAirMac1() & 0xff) << 40)
                | ((long) (drone.airBSSID.getAirMac2() & 0xff) << 32)
                | ((long) (drone.airBSSID.getAirMac3() & 0xff) << 24
                | ((long) (drone.airBSSID.getAirMac4() & 0xff) << 16)
                | (((long) drone.airBSSID.getAirMac5() & 0xff) << 8) | (long) (drone.airBSSID
                .getAirMac6() & 0xff));
        mt.tc1 = tc1;
        // 发给中继器
        drone.mavLinkClient.sendRelayPacker(mt.pack());
    }


    /**
     * 发送设备认证的命令 发给飞机
     */
    public void sendEquipmentCer() {

        msg_command_long cLong = new msg_command_long();
        if (drone.airConnectionStatus.isReceiveBSSID()) {
            cLong.param1 = 1;// 1:中继，0:飞机
        } else {
            cLong.param1 = 0;// 1:中继，0:飞机
        }
        cLong.command = MAV_CMD.MAV_CMD_VIDEO_START_CAPTURE;
        byte[] b1 = new byte[4];
        b1[0] = (byte) drone.relayerBSSID.getRelayerMac1();
        b1[1] = (byte) drone.relayerBSSID.getRelayerMac2();
        b1[2] = (byte) drone.relayerBSSID.getRelayerMac3();
        b1[3] = (byte) drone.relayerBSSID.getRelayerMac4();
        byte[] b2 = new byte[4];
        b2[0] = (byte) 0;
        b2[1] = (byte) 0;
        b2[2] = (byte) drone.relayerBSSID.getRelayerMac5();
        b2[3] = (byte) drone.relayerBSSID.getRelayerMac6();

        byte[] b3 = new byte[4];
        b3[0] = (byte) drone.airPhoneBSSID.getAirPhoneMac1();
        b3[1] = (byte) drone.airPhoneBSSID.getAirPhoneMac2();
        b3[2] = (byte) drone.airPhoneBSSID.getAirPhoneMac3();
        b3[3] = (byte) drone.airPhoneBSSID.getAirPhoneMac4();
        byte[] b4 = new byte[4];
        b4[0] = (byte) 0;
        b4[1] = (byte) 0;
        b4[2] = (byte) drone.airPhoneBSSID.getAirPhoneMac5();
        b4[3] = (byte) drone.airPhoneBSSID.getAirPhoneMac6();

        // 中继器发过来的Mac地址
        cLong.param2 = ParameterUtil.getFloat(b1);
        cLong.param3 = ParameterUtil.getFloat(b2);
        // 本机的Mac地址
        cLong.param4 = ParameterUtil.getFloat(b3);
        cLong.param5 = ParameterUtil.getFloat(b4);
        drone.mavLinkClient.sendMavPacket(cLong.pack());
    }

    /**
     * @param param1 0:飞机 1:中继器 2:解除认证 3:清空所有列表 对于飞机：param1 =0：只绑飞机 ,param1 =1：
     *               绑飞机和中继器 , param1=2：清除认证 , param1=3：清除所有记录 对于中继： param1 =2：清除认证
     *               <p>
     *               <p>
     *               发2 的时候两个都发 3:只发给飞机
     */
    public void sendAirEquipmentCertification(float param1) {
        msg_command_long cLong = new msg_command_long();
        cLong.param1 = param1;
        cLong.command = MAV_CMD.MAV_CMD_VIDEO_START_CAPTURE;
        drone.mavLinkClient.sendMavPacket(cLong.pack());
    }

}
