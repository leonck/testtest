package com.hubsansdk.drone.bean;

/**
 * 飞机个中继器链接状态
 * @author shengkun.cheng
 *
 */
public class AirConnectionStatus {

	//飞机连接状态
	private boolean airConnection;
	//中继器连接状态
	private boolean repeaterConnection;
	//当前连接的类型    飞机 :0 	 中继器 :1
	private int connectionType;
	//判断当前是否退出操作界面
	public boolean isExitAirConnection;
	//判断是否接收到中继器发过来的bssid
	private boolean isReceiveBSSID;
	
	public boolean isAirConnection() {
		return airConnection;
	}
	public void setAirConnection(boolean airConnection) {
		this.airConnection = airConnection;
	}
	public boolean isRepeaterConnection() {
		return repeaterConnection;
	}
	public void setRepeaterConnection(boolean repeaterConnection) {
		this.repeaterConnection = repeaterConnection;
	}
	public int getConnectionType() {
		return connectionType;
	}
	public void setConnectionType(int connectionType) {
		this.connectionType = connectionType;
	}

	public boolean isReceiveBSSID() {
		return isReceiveBSSID;
	}

	public void setReceiveBSSID(boolean receiveBSSID) {
		isReceiveBSSID = receiveBSSID;
	}

	public boolean isExitAirConnection() {
		return isExitAirConnection;
	}
	public void setExitAirConnection(boolean isExitAirConnection) {
		this.isExitAirConnection = isExitAirConnection;
	}
	
	
	
}
