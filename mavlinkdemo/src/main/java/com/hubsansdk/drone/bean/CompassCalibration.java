package com.hubsansdk.drone.bean;

import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces.DroneEventsType;
import com.hubsansdk.drone.HubsanDroneVariable;

/**
 * 地磁校准
 * 
 * @author shengkun.cheng
 *
 */
public class CompassCalibration extends HubsanDroneVariable {

	private int calibrationState;
	private boolean horizontalCalibration;
	//手动校准
	private boolean autoHorizontalCalibration;
	private boolean needHorizontalCalibration;
	private boolean needHorizontalCalibrationTag;

	public CompassCalibration(HubsanDrone myDrone) {
		super(myDrone);
		// TODO Auto-generated constructor stub
	}

	public void setCompassCalibrationStatus(int par2, int par6) {
		if (par2 == 0 && par6 == 0) {
			calibrationState = 0;
			myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_COMPASS_CALIBRATION_OVER);
		} else if (par2 == 1 && par6 == 1) {
			// 水平旋转
			calibrationState = 1;
			myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_COMPASS_CALIBRATION_HORIZONTAL);
		} else if (par2 == 1 && par6 == 2) {
			// 垂直旋转
			calibrationState = 2;
			myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_COMPASS_CALIBRATION_VERTICAL);
		}
	}

	public int getCalibrationState() {
		return calibrationState;
	}

	public boolean isHorizontalCalibration() {
		return horizontalCalibration;
	}

	public void setHorizontalCalibration(boolean horizontalCalibration) {
		this.horizontalCalibration = horizontalCalibration;
	}

	public boolean isAutoHorizontalCalibration() {
		return autoHorizontalCalibration;
	}

	public void setAutoHorizontalCalibration(boolean autoHorizontalCalibration) {
		this.autoHorizontalCalibration = autoHorizontalCalibration;
	}

	public boolean isNeedHorizontalCalibration() {
		return needHorizontalCalibration;
	}

	public void setNeedHorizontalCalibration(boolean needHorizontalCalibration) {
		this.needHorizontalCalibration = needHorizontalCalibration;
	}

	public boolean isNeedHorizontalCalibrationTag() {
		return needHorizontalCalibrationTag;
	}

	public void setNeedHorizontalCalibrationTag(boolean needHorizontalCalibrationTag) {
		this.needHorizontalCalibrationTag = needHorizontalCalibrationTag;
	}
}
