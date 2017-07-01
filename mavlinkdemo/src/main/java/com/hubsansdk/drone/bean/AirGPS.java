package com.hubsansdk.drone.bean;

import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces.DroneEventsType;
import com.hubsansdk.drone.HubsanDroneVariable;

/**
 * 飞机的GPS信息
 * @author shengkun.cheng
 *
 */
public class AirGPS extends HubsanDroneVariable {
	private int gpsNumber;
	private double airLat;
	private double airLon;
	private boolean needGPS;
	

	public AirGPS(HubsanDrone myDrone) {
		super(myDrone);
		// TODO Auto-generated constructor stub
	}


	public int getGpsNumber() {
		return gpsNumber;
	}


	public void setGpsNumber(int gpsNumber) {
		this.gpsNumber = gpsNumber;
		myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_GPS_NUMBER);
	}


	public double getAirLat() {
		return airLat;
	}


	public void setAirLatLon(double airLat,double airLon) {
		this.airLat = airLat;
		this.airLon = airLon;
		myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_LAT_LON);
	}


	public double getAirLon() {
		return airLon;
	}

	public boolean isNeedGPS() {
		return needGPS;
	}

	public void setNeedGPS(boolean needGPS) {
		this.needGPS = needGPS;
	}
}
