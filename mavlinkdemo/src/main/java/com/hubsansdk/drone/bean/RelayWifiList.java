package com.hubsansdk.drone.bean;



import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneVariable;

/**
 * 中继器的WIFI列表
 *
 * @author shengkun.cheng
 */
public class RelayWifiList extends HubsanDroneVariable{

    private String relayWifiListItem;
    private int relayWifiSize;
    private String relayerKey;
    private String relayerSSID;

    public RelayWifiList(HubsanDrone myDrone) {
        super(myDrone);
    }
    public RelayWifiList() {
    }

    public String getRelayWifiListItem() {
        return relayWifiListItem;
    }

    public void setRelayWifiListItem(String relayWifiListItem) {
        this.relayWifiListItem = relayWifiListItem;
    }

    public int getRelayWifiSize() {
        return relayWifiSize;
    }


    public void setRelayWifiSize(int relayWifiSize) {
        this.relayWifiSize = relayWifiSize;
    }

    public String getRelayerKey() {
        return relayerKey;
    }

    public void setRelayerKey(String relayerKey) {
        this.relayerKey = relayerKey;
    }

    public String getRelayerSSID() {
        return relayerSSID;
    }

    public void setRelayerSSID(String relayerSSID) {
        this.relayerSSID = relayerSSID;
    }

}
