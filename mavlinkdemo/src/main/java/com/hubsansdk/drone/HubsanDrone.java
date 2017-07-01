package com.hubsansdk.drone;

import com.hubsansdk.drone.bean.AirBSSID;
import com.hubsansdk.drone.bean.AirBaseParameters;
import com.hubsansdk.drone.bean.AirConnectionStatus;
import com.hubsansdk.drone.bean.AirGPS;
import com.hubsansdk.drone.bean.AirHeartBeat;
import com.hubsansdk.drone.bean.AirMode;
import com.hubsansdk.drone.bean.AirPhoneBSSID;
import com.hubsansdk.drone.bean.AirRealyEquipmentCertification;
import com.hubsansdk.drone.bean.AirRelayACK;
import com.hubsansdk.drone.bean.Attitude;
import com.hubsansdk.drone.bean.Battery;
import com.hubsansdk.drone.bean.CommonNotify;
import com.hubsansdk.drone.bean.CommonStatus;
import com.hubsansdk.drone.bean.CompassCalibration;
import com.hubsansdk.drone.bean.GPSManager;
import com.hubsansdk.drone.bean.HCamera;
import com.hubsansdk.drone.bean.Joystick;
import com.hubsansdk.drone.bean.LocationGPS;
import com.hubsansdk.drone.bean.NetWorkUtil;
import com.hubsansdk.drone.bean.RelayHeartBeat;
import com.hubsansdk.drone.bean.RelayWifiItem;
import com.hubsansdk.drone.bean.RelayWifiList;
import com.hubsansdk.drone.bean.RelayerBSSID;
import com.hubsansdk.drone.bean.SDCardStatus;
import com.hubsansdk.mavlink.MavLinkSendMessage;
import com.hubsansdk.service.MAVLinkClient;
import com.hubsansdk.service.MAVLinkUDPSendJoystick;
import com.hubsansdk.utils.ParameterUtil;

/**
 * 
 * @author shengkun.cheng
 *
 */
public class HubsanDrone {

	public HubsanDroneEvents events = new HubsanDroneEvents(this);
	// 摇杆参数
	public Joystick joystick = new Joystick(this);
	// 设备认证
	public AirRealyEquipmentCertification airRelayEquipmentCertification = new AirRealyEquipmentCertification(this);
	// 飞机连接的状态和 当前连接的设备
	public AirConnectionStatus airConnectionStatus = new AirConnectionStatus();
	// 本地的GPS数量和经纬度
	public LocationGPS locationGPS = new LocationGPS();
	// 封装的发送MAVLink消息
	public MavLinkSendMessage mavLinkSendMessage = new MavLinkSendMessage(this);
	// 功能按钮的状态
	public CommonStatus commonButtonStatus = new CommonStatus();
	// 公共的通知状态
	public CommonNotify commonNotify = new CommonNotify(this);
	// udp发送摇杆数据
	public MAVLinkUDPSendJoystick udpSendJoystick = new MAVLinkUDPSendJoystick(this);
	// 简单的运算
	public ParameterUtil parameterUtil = new ParameterUtil();
	// 飞机的BSSID
	public AirBSSID airBSSID = new AirBSSID();
	// 手机的BSSID
	public AirPhoneBSSID airPhoneBSSID = new AirPhoneBSSID();
	// 心跳的数据
	public AirHeartBeat airHeartBeat = new AirHeartBeat(this);
	//地磁校准
	public CompassCalibration compassCalibration = new CompassCalibration(this);
	//电量
	public Battery battery = new Battery(this);
	//sdcard
	public SDCardStatus sdCardStatus = new SDCardStatus(this);
	//air GPS 
	public AirGPS airGPS = new AirGPS(this);
	//姿态信息
	public Attitude attitude = new Attitude(this);
	//飞机的基本参数
	public AirBaseParameters airBaseParameters = new AirBaseParameters(this);
	//ACK
	public AirRelayACK airRelayAck = new AirRelayACK(this);
	//Air的当前模式
	public AirMode airMode = new AirMode(this);
	//中继器的心跳数据
	public RelayHeartBeat relayHeartBeat = new RelayHeartBeat(this);
	//中继器BSSID
	public RelayerBSSID relayerBSSID = new RelayerBSSID();
	//发送摇杆数据的udp
	public MAVLinkUDPSendJoystick mavLinkUDPSendJoystick = new MAVLinkUDPSendJoystick(this);
	//拍照,录像
	public HCamera hCamera = new HCamera(this);
	//中继器
	public RelayWifiList relayList = new RelayWifiList(this);
	public RelayWifiItem relayWifiItem = new RelayWifiItem();
	//控制中心
	public HubsanSendInstructionAir hubsanSendInstructionAir = new HubsanSendInstructionAir(this);
	//管理GPS数据
	public GPSManager gpsManager = new GPSManager(this);

	public MAVLinkClient mavLinkClient;

	public HubsanDrone(MAVLinkClient mavLinkClient) {
		this.mavLinkClient = mavLinkClient;
	}
	public NetWorkUtil netWorkUtil = new NetWorkUtil();

}
