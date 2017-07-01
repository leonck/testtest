package com.hubsansdk.drone.bean;

import com.MAVLink.common.msg_param_value;
import com.MAVLink.enums.MAV_PARAM_TYPE;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces.DroneEventsType;
import com.hubsansdk.drone.HubsanDroneVariable;

/**
 * 飞机的基本参数
 *
 * @author shengkun.cheng
 */
public class AirBaseParameters extends HubsanDroneVariable {

    private float x;// 距离
    private float y;
    private float z;
    private float vx;// 速度
    private float vy;
    private float vz;
    private float altitude;// 海拔高度

    private String modelName;
    // 飞机软件版本
    private String softwareVersion;
    // 电池容量
    private String batteryCapacity;
    //
    private String quadPower;
    // 电池电压
    private String batteryVoltage;
    // 记录比特率
    private String recordBitrate;
    //飞控软件版本号
    private String autopilotVer;
    private String airSelectMode = "H501A";

    public AirBaseParameters(HubsanDrone myDrone) {
        super(myDrone);
        // TODO Auto-generated constructor stub
    }


    /**
     * 飞机的基本参数
     *
     * @param x  距离
     * @param y
     * @param z
     * @param vx 速度
     * @param vy
     * @param vz
     */
    public void setBaseValue(float x, float y, float z, float vx, float vy,
                             float vz) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_BASE_VALUE);
    }

    /*
     * 飞机的海拔高度
     */
    public void setAltitude(float altitude) {
        this.altitude = altitude;
        myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_AIR_ALTITUDE);
    }

    /**
     * Air 版本信息及电池电压
     *
     * @param param_value
     */
    public void setAirSoftVersionBattery(msg_param_value param_value) {
        short type = param_value.param_type;
        if (type == MAV_PARAM_TYPE.MAV_PARAM_TYPE_STRING) {
            String value = new String(param_value.param_value2).trim();
            String keyName = new String(param_value.param_id).trim();
            if (keyName.equals("ModelName") || keyName == "ModelName") {
                this.modelName = value;
            } else if (keyName.equals("SoftwareVer") || keyName == "SoftwareVer") {
                this.softwareVersion = value;
            } else if (keyName.equals("BatteryCap") || keyName == "BatteryCap") {
                this.batteryCapacity = value;
            } else if (keyName.equals("QuadPower") || keyName == "QuadPower") {
                this.quadPower = value;
            } else if (keyName.equals("BatteryVoltage")
                    || keyName == "BatteryVoltage") {
                this.batteryVoltage = value;
            } else if (keyName.equals("RecordBitrate")
                    || keyName == "RecordBitrate") {
                this.recordBitrate = value;
            } else if (keyName.equals("AutopilotVer")
                    || keyName == "AutopilotVer") {
                this.autopilotVer = value;
                setAutopilotVer(value);
            }
        }
        myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_AIR_SOFT_VERSION);
    }

    /**
     * 距离
     *
     * @return
     */
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    /**
     * 速度
     *
     * @return
     */
    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }

    public float getVz() {
        return vz;
    }

    public float getAltitude() {
        return altitude;
    }

    public String getModelName() {
        return modelName;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public String getBatteryCapacity() {
        return batteryCapacity;
    }

    public String getQuadPower() {
        return quadPower;
    }

    public String getBatteryVoltage() {
        return batteryVoltage;
    }

    public String getRecordBitrate() {
        return recordBitrate;
    }

    public String getAutopilotVer() {
        return autopilotVer;
    }

    public void setAutopilotVer(String autopilotVer) {
        this.autopilotVer = autopilotVer;
    }

    public String getAirSelectMode() {
        return airSelectMode;
    }

    public void setAirSelectMode(String airSelectMode) {
        this.airSelectMode = airSelectMode;
    }
}
