package com.hubsansdk.drone.bean;

import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneVariable;

/**
 * 中继器和飞机认证的状态
 *
 * @author shengkun.cheng
 */
public class AirRealyEquipmentCertification extends HubsanDroneVariable {

    /**
     * 飞机认证
     */
    private boolean isAirEquipmentCertification;
    /**
     * 中继器认证
     */
    private boolean isRepeaterEquipmentCertification;

    //设置认证失败或者成功
    private boolean Authentication = false;

    public AirRealyEquipmentCertification(HubsanDrone myDrone) {
        super(myDrone);
    }

    public boolean isAirEquipmentCertification() {
        return isAirEquipmentCertification;
    }

    public void setAirEquipmentCertification(boolean isAirEquipmentCertification) {
        this.isAirEquipmentCertification = isAirEquipmentCertification;
    }

    public boolean isRepeaterEquipmentCertification() {
        return isRepeaterEquipmentCertification;
    }

    public void setRepeaterEquipmentCertification(
            boolean isRepeaterEquipmentCertification) {
        this.isRepeaterEquipmentCertification = isRepeaterEquipmentCertification;
    }

    public boolean isAuthentication() {
        return Authentication;
    }

    public void setAuthentication(boolean authentication) {
        Authentication = authentication;
    }


}
