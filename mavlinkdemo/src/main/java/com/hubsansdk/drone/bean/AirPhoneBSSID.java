package com.hubsansdk.drone.bean;

/**
 * 中继器接收手机的BSSID
 * 
 * @author shengkun.cheng
 *
 */
public class AirPhoneBSSID {

	private long airPhoneMac1;
	private long airPhoneMac2;
	private long airPhoneMac3;
	private long airPhoneMac4;
	private long airPhoneMac5;
	private long airPhoneMac6;
	private long airPhoneTs1;
	
	private String phoneBSSID;
	private String phoneMAC;
	
	

	public long getAirPhoneMac1() {
		return airPhoneMac1;
	}

	public void setAirPhoneMac1(long airPhoneMac1) {
		this.airPhoneMac1 = airPhoneMac1;
	}

	public long getAirPhoneMac2() {
		return airPhoneMac2;
	}

	public void setAirPhoneMac2(long airPhoneMac2) {
		this.airPhoneMac2 = airPhoneMac2;
	}

	public long getAirPhoneMac3() {
		return airPhoneMac3;
	}

	public void setAirPhoneMac3(long airPhoneMac3) {
		this.airPhoneMac3 = airPhoneMac3;
	}

	public long getAirPhoneMac4() {
		return airPhoneMac4;
	}

	public void setAirPhoneMac4(long airPhoneMac4) {
		this.airPhoneMac4 = airPhoneMac4;
	}

	public long getAirPhoneMac5() {
		return airPhoneMac5;
	}

	public void setAirPhoneMac5(long airPhoneMac5) {
		this.airPhoneMac5 = airPhoneMac5;
	}

	public long getAirPhoneMac6() {
		return airPhoneMac6;
	}

	public void setAirPhoneMac6(long airPhoneMac6) {
		this.airPhoneMac6 = airPhoneMac6;
	}

	public long getAirPhoneTs1() {
		return airPhoneTs1;
	}

	public void setAirPhoneTs1(long airPhoneTs1) {
		this.airPhoneTs1 = airPhoneTs1;
	}

	public String getPhoneBSSID() {
		return phoneBSSID;
	}

	public void setPhoneBSSID(String phoneBSSID) {
		this.phoneBSSID = phoneBSSID;
	}

	public String getPhoneMAC() {
		return phoneMAC;
	}

	public void setPhoneMAC(String phoneMAC) {
		this.phoneMAC = phoneMAC;
	}
}
