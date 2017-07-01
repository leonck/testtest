package com.hubsansdk.drone.bean;

import com.MAVLink.common.msg_heartbeat;
import com.MAVLink.common.msg_param_value;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces.DroneEventsType;
import com.hubsansdk.drone.HubsanDroneVariable;
import com.hubsansdk.utils.ParameterUtil;

/**
 * 中继器的心跳数据
 * @author shengkun.cheng
 *
 */
public class RelayHeartBeat extends HubsanDroneVariable {

    private String hardwareVer;
    private String modelName;
    private String softwareVer;

	public RelayHeartBeat(HubsanDrone myDrone) {
		super(myDrone);
		// TODO Auto-generated constructor stub
	}

	public void setRelayHeartBeat(msg_heartbeat heartbeat){
		relayBSSID(heartbeat);
		setRelayIsEquipment(heartbeat);
	}

    /**
     * 设置中继器版本信息
     * @param mms
     */
    public void setRelayerVersion(msg_param_value mms){
        String value = new String(mms.param_value2).trim();
        String keyName = new String(mms.param_id).trim();
        if (keyName.equals("HardwareVer")) {
            setHardwareVer(value);
            hardwareVer = value;
        }else if (keyName.equals("ModelName")){
            setModelName(value);
            modelName = value;
        }else if (keyName.equals("SoftwareVer")){
            setSoftwareVer(value);
            softwareVer = value;
        }
        myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_RELAYER_VERSION_INFO);
    }
	
	public void setRelayIsEquipment(msg_heartbeat heartbeat){
		//判断中继器是否绑定 0:已认证  1:未认证
        if (((heartbeat.base_mode >> 1) & 0x01) == 0) {
            myDrone.airRelayEquipmentCertification.setRepeaterEquipmentCertification(true);
            if (myDrone.airRelayEquipmentCertification.isAirEquipmentCertification() == false) {
               myDrone.mavLinkSendMessage.sendRelayEquMavLink();
            }
        } else {
            myDrone.airRelayEquipmentCertification.setRepeaterEquipmentCertification(false);
            if (myDrone.airRelayEquipmentCertification.isAirEquipmentCertification()== true) {
                //将飞机的Mac地址发给中继器
            	myDrone.mavLinkSendMessage.sendAirBSSIDRelay();
            }
        }
	}
	
	
	/**
     * 接收到中继器的bssid
     *
     * @param msg_heart
     */
    public void relayBSSID(msg_heartbeat msg_heart) {
        int mMac1 = ((int) msg_heart.custom_mode >> 24) & 0xff;
        int mMac2 = ((int) msg_heart.custom_mode >> 16) & 0xff;
        int mMac3 = ((int) msg_heart.custom_mode >> 8) & 0xff;
        int mMac4 = (int) msg_heart.custom_mode & 0xff;
        int mMac5 = (int) msg_heart.type & 0xff;
        int mMac6 = (int) msg_heart.autopilot & 0xff;

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(ParameterUtil.textSizeAddZero(mMac1) + ":" + ParameterUtil.textSizeAddZero(mMac2) + ":"
                + ParameterUtil.textSizeAddZero(mMac3) + ":" + ParameterUtil.textSizeAddZero(mMac4) + ":"
                + ParameterUtil.textSizeAddZero(mMac5) + ":" + ParameterUtil.textSizeAddZero(mMac6));
        myDrone.relayerBSSID.setRelayBSSID(stringBuffer.toString());

        if (stringBuffer.toString().equals(myDrone.airPhoneBSSID.getPhoneBSSID())) {//两个字符串相等的时候为中继器
            //中继器
            myDrone.airConnectionStatus.setConnectionType(1);
            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_CONNECTION_RELAY);
        } else {
            //不相等的时候飞机
            //飞机
        	myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_CONNECTION_AIR);
            myDrone.airConnectionStatus.setConnectionType(0);
        }
    }

    public String getHardwareVer() {
        return hardwareVer;
    }

    public void setHardwareVer(String hardwareVer) {
        this.hardwareVer = hardwareVer;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getSoftwareVer() {
        return softwareVer;
    }

    public void setSoftwareVer(String softwareVer) {
        this.softwareVer = softwareVer;
    }
}
