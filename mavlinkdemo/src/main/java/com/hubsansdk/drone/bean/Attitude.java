package com.hubsansdk.drone.bean;

import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces.DroneEventsType;
import com.hubsansdk.drone.HubsanDroneVariable;

/**
 * 姿态 信息
 * @author shengkun.cheng
 *
 */
public class Attitude extends HubsanDroneVariable {
	
	public Attitude(HubsanDrone myDrone) {
		super(myDrone);
		// TODO Auto-generated constructor stub
	}

	private double roll = 0;
	private double pitch = 0;
	private double yaw = 0;
	private float rotate;
	public double getRoll() {
		return roll;
	}
	public double getPitch() {
		return pitch;
	}
	public double getYaw() {
		return yaw;
	}
	public float getRotate() {
		return rotate;
	}
	
	public void setAttitude(double roll, double pitch, double yaw) {
		this.roll = (roll / Math.PI) * 180;
		this.pitch = (pitch / Math.PI) * 180;
		this.yaw = (yaw / Math.PI) * 180;
		this.rotate = (float)(yaw / Math.PI) * 180;
		myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_AIR_ATTITUDE);
	}
}
