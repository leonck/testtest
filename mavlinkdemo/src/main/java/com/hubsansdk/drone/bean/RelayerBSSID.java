package com.hubsansdk.drone.bean;

/**
 * 中继器的BSSID
 * @author shengkun.cheng
 *
 */
public class RelayerBSSID {

	private long relayerMac1;
    private long relayerMac2;
    private long relayerMac3;
    private long relayerMac4;
    private long relayerMac5;
    private long relayerMac6;
    private long relayerTc1;
    private boolean receiveMac;
    
    private String relayBSSID;
    
    
    private String relayerBssid;

	public long getRelayerMac1() {
		return relayerMac1;
	}

	public void setRelayerMac1(long relayerMac1) {
		this.relayerMac1 = relayerMac1;
	}

	public long getRelayerMac2() {
		return relayerMac2;
	}

	public void setRelayerMac2(long relayerMac2) {
		this.relayerMac2 = relayerMac2;
	}

	public long getRelayerMac3() {
		return relayerMac3;
	}

	public void setRelayerMac3(long relayerMac3) {
		this.relayerMac3 = relayerMac3;
	}

	public long getRelayerMac4() {
		return relayerMac4;
	}

	public void setRelayerMac4(long relayerMac4) {
		this.relayerMac4 = relayerMac4;
	}

	public long getRelayerMac5() {
		return relayerMac5;
	}

	public void setRelayerMac5(long relayerMac5) {
		this.relayerMac5 = relayerMac5;
	}

	public long getRelayerMac6() {
		return relayerMac6;
	}

	public void setRelayerMac6(long relayerMac6) {
		this.relayerMac6 = relayerMac6;
	}

	public String getRelayerBssid() {
		return relayerBssid;
	}

	public void setRelayerBssid(String relayerBssid) {
		this.relayerBssid = relayerBssid;
	}

	public String getRelayBSSID() {
		return relayBSSID;
	}

	public void setRelayBSSID(String relayBSSID) {
		this.relayBSSID = relayBSSID;
	}

	
	public long getRelayerTc1() {
		return relayerTc1;
	}

	public void setRelayerTc1(long relayerTc1) {
		this.relayerTc1 = relayerTc1;
	}

	public boolean isReceiveMac() {
		return receiveMac;
	}

	public void setReceiveMac(boolean receiveMac) {
		this.receiveMac = receiveMac;
	}
    
	
    
    
    
}
