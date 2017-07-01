package com.hubsansdk.drone.bean;

import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces.DroneEventsType;
import com.hubsansdk.drone.HubsanDroneVariable;

/**
 * 电池电量
 * @author shengkun.cheng
 *
 */
public class Battery extends HubsanDroneVariable {
	private int batteryValue = -1;
	
	public Battery(HubsanDrone myDrone) {
		super(myDrone);
		// TODO Auto-generated constructor stub
	}

	public int getBatteryValue() {
		return batteryValue;
	}

	public void setBatteryValue(int batteryValue) {
		this.batteryValue = batteryValue;
		myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_BATTERY);
	}
	
	
	

}
