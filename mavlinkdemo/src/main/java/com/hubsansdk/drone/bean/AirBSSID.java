package com.hubsansdk.drone.bean;

/**
 * 飞机的BSSID
 * @author shengkun.cheng
 *
 */
public class AirBSSID {

	private long airMac1;
    private long airMac2;
    private long airMac3;
    private long airMac4;
    private long airMac5;
    private long airMac6;
    
    private String airBssid;
	private String airMAC;
    
	public long getAirMac1() {
		return airMac1;
	}
	public void setAirMac1(long airMac1) {
		this.airMac1 = airMac1;
	}
	public long getAirMac2() {
		return airMac2;
	}
	public void setAirMac2(long airMac2) {
		this.airMac2 = airMac2;
	}
	public long getAirMac3() {
		return airMac3;
	}
	public void setAirMac3(long airMac3) {
		this.airMac3 = airMac3;
	}
	public long getAirMac4() {
		return airMac4;
	}
	public void setAirMac4(long airMac4) {
		this.airMac4 = airMac4;
	}
	public long getAirMac5() {
		return airMac5;
	}
	public void setAirMac5(long airMac5) {
		this.airMac5 = airMac5;
	}
	public long getAirMac6() {
		return airMac6;
	}
	public void setAirMac6(long airMac6) {
		this.airMac6 = airMac6;
	}
	public String getAirBssid() {
		return airBssid;
	}
	public void setAirBssid(String airBssid) {
		this.airBssid = airBssid;
	}

	public String getAirMAC() {
		return airMAC;
	}

	public void setAirMAC(String airMAC) {
		this.airMAC = airMAC;
	}
}
