package com.hubsansdk.drone.bean;

/**
 * 本地GPS信息及经纬度
 * 
 * @author shengkun.cheng
 *
 */
public class LocationGPS {
	//本地纬度
	private double locationLat;
	//本地经度
	private double locationLon;
	//本地GPS的数量
	private int locationGpsNumber;
	//判断GPS是否测试通过
	private boolean isTestGPS;
	//国内地图和飞机纬度差异值
	private double differenceLat;
	//国内地图和飞机经度差异值
	private double differenceLon;
	//起飞点
	private boolean airUpFlyPoint = true;
	

	public double getLocationLat() {
		return locationLat;
	}

	public void setLocationLat(double locationLat) {
		this.locationLat = locationLat;
	}

	public double getLocationLon() {
		return locationLon;
	}

	public void setLocationLon(double locationLon) {
		this.locationLon = locationLon;
	}

	public int getLocationGpsNumber() {
		return locationGpsNumber;
	}

	public void setLocationGpsNumber(int locationGpsNumber) {
		this.locationGpsNumber = locationGpsNumber;
	}

	public boolean isTestGPS() {
		return isTestGPS;
	}

	public void setTestGPS(boolean testGPS) {
		isTestGPS = testGPS;
	}

	public double getDifferenceLat() {
		return differenceLat;
	}

	public void setDifferenceLat(double differenceLat) {
		this.differenceLat = differenceLat;
	}

	public double getDifferenceLon() {
		return differenceLon;
	}

	public void setDifferenceLon(double differenceLon) {
		this.differenceLon = differenceLon;
	}

	public boolean isAirUpFlyPoint() {
		return airUpFlyPoint;
	}

	public void setAirUpFlyPoint(boolean airUpFlyPoint) {
		this.airUpFlyPoint = airUpFlyPoint;
	}
}
