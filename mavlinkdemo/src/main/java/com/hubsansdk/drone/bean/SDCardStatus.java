package com.hubsansdk.drone.bean;


import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.application.HubsanHandleMessageApp;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces.DroneEventsType;
import com.hubsansdk.drone.HubsanDroneVariable;
import com.hubsansdk.utils.LogX;
import com.utils.PreferenceUtils;

/**
 * SDCard状态
 * 
 * @author shengkun.cheng
 *
 */
public class SDCardStatus extends HubsanDroneVariable {

	// 总容量 MB
	private int sd_capacity;
	// 剩余容量
	private int sd_surplus;
	//录像时间
	private long recTime;//录像时间
	//SDCard的状态
	private boolean sdcardStatus;
	private HubsanHandleMessageApp application;

	public SDCardStatus(HubsanDrone myDrone) {
		super(myDrone);
		application = (HubsanHandleMessageApp) HubsanApplication.getApplication();
	}

	public int getSd_capacity() {
		return sd_capacity;
	}

	public int getSd_surplus() {
		return sd_surplus;
	}

	public void setSd_capacity(int sd_capacity) {
		this.sd_capacity = sd_capacity;
	}

	public void setSd_surplus(int sd_surplus) {
		this.sd_surplus = sd_surplus;
	}

	public void setSDCardStatus(float sd_capacity, short sd_status,
								float sd_surplus) {
		myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_SDCARD_VALUE);
		if (sd_status == 0) {
			// sdcard 正常
			sdcardStatus = true;
			setSd_surplus((int) Math.rint(sd_surplus));
			setSd_capacity((int) Math.rint(sd_capacity));
			setSdcardStatus(true);
			LogX.e("====sdcard 正常====sd_surplus====="+getSd_surplus()+"	,sd_capacity:"+ Math.rint(sd_capacity));
			PreferenceUtils.setPrefBoolean(application,"HANVE_SDCARD",true);
			myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_HAVE_SDCARD);
		} else if (sd_status == 1) {
			// sdcard 沒有
			sdcardStatus = false;
			setSdcardStatus(false);
			LogX.e("==== sdcard 沒有=========");
			PreferenceUtils.setPrefBoolean(application,"HANVE_SDCARD",false);
			myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_NO_SDCARD);
		} else if (sd_status == 2) {
			// sdcard 异常
			sdcardStatus = false;
			setSdcardStatus(false);
			LogX.e("==== sdcard 异常=========");
			PreferenceUtils.setPrefBoolean(application,"HANVE_SDCARD",false);
			myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_EXCEPT_SDCARD);
		}

	}

	public long getRecTime() {
		return recTime;
	}

	//设置录像时间
	public void setRecTime(long recTime) {
		this.recTime = recTime;
		 myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_AIR_RECTIME_TIME);
	}

	public boolean isSdcardStatus() {
		return sdcardStatus;
	}

	public void setSdcardStatus(boolean sdcardStatus) {
		this.sdcardStatus = sdcardStatus;

	}
}
