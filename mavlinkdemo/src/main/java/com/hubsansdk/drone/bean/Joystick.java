package com.hubsansdk.drone.bean;

import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces;
import com.hubsansdk.drone.HubsanDroneVariable;

/**
 * 摇杆的参数
 *
 * @author shengkun.cheng
 */
public class Joystick extends HubsanDroneVariable {
    private int throttleRaw;//油门,主要上升下降    原始数据
    private int rudderRaw;//机头左右转向          原始数据
    private int aileronRaw;//飞机左右飞行         原始数据
    private int elevatorRaw;//飞机前后飞行        原始数据
    private int throttle;//油门,主要上升下降
    private int rudder;//机头左右转向
    private int aileron;//飞机左右飞行
    private int elevator;//飞机前后飞行
    private int isNeedGPS;//是否需要GPS
    private boolean isOpenJoystick;//判断摇杆是否打开
    private boolean joystickMode;//摇杆模式：美国手   日本手
    private float JoystickJunior_Sensitivity;
    private float JoystickSenior_MaxValue;

    public Joystick(HubsanDrone myDrone) {
        super(myDrone);
    }

    public int getThrottle() {
        return throttle;
    }

    public void setThrottle(int throttle) {
        this.throttle = throttle;
    }

    public int getRudder() {
        return rudder;
    }

    public void setRudder(int rudder) {
        this.rudder = rudder;
    }

    public int getAileron() {
        return aileron;
    }

    public void setAileron(int aileron) {
        this.aileron = aileron;
    }

    public int getElevator() {
        return elevator;
    }

    public void setElevator(int elevator) {
        this.elevator = elevator;
    }

    public int getIsNeedGPS() {
        return isNeedGPS;
    }

    public void setIsNeedGPS(int isNeedGPS) {
        this.isNeedGPS = isNeedGPS;
    }

    public boolean isOpenJoystick() {
        return isOpenJoystick;
    }

    public void setOpenJoystick(boolean openJoystick) {
        isOpenJoystick = openJoystick;
    }

    public boolean isJoystickMode() {
        return joystickMode;
    }

    public void setJoystickMode(boolean joystickMode) {
        this.joystickMode = joystickMode;
    }

    public int getThrottleRaw() {
        return throttleRaw;
    }

    public void setThrottleRaw(int throttleRaw) {
        this.throttleRaw = throttleRaw;
    }

    public int getRudderRaw() {
        return rudderRaw;
    }

    public void setRudderRaw(int rudderRaw) {
        this.rudderRaw = rudderRaw;
    }

    public int getAileronRaw() {
        return aileronRaw;
    }

    public void setAileronRaw(int aileronRaw) {
        this.aileronRaw = aileronRaw;
    }

    public int getElevatorRaw() {
        return elevatorRaw;
    }

    public void setElevatorRaw(int elevatorRaw) {
        this.elevatorRaw = elevatorRaw;
    }

    /**
     * 设置左边的参数
     *
     * @param pans
     * @param tilts
     */
    public void setRockerLeftValue(float pans, float tilts, int operatingMode, boolean usOrJp) {
        selectMode();
        //以下是处理X轴,Y轴 的显示数据
        if (Math.abs(pans) < Math.abs(tilts) / 2) {
            pans = 0;
        }
        if (Math.abs(tilts) < Math.abs(pans) / 2) {
            tilts = 0;
        }
        pans = pans * 100;
        tilts = tilts * 100;
        switch (operatingMode) {
            case 1://高级
                if (usOrJp == true) {//美国手 throttleRaw 油门，不缩小最大值，优化灵敏度
                    if (tilts <= 800 && tilts > 0) {
                        tilts = (int) (Math.pow((tilts / 1000.0), 2) * 1000);
                    } else if (tilts > 800) {
                        tilts = (int) (1.8 * tilts - 800);
                    }
                    myDrone.joystick.setRudderRaw((int) pans);
                    myDrone.joystick.setThrottleRaw((int) tilts);
                    myDrone.joystick.setRudder((int) pans);
                    myDrone.joystick.setThrottle((int) tilts);
                    myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                } else {//日本手
                    myDrone.joystick.setRudderRaw((int) pans);
                    myDrone.joystick.setElevatorRaw((int) tilts);
                    myDrone.joystick.setRudder((int) pans);
                    myDrone.joystick.setElevator((int) tilts);
                    myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                }
                break;
            case 2://中级
                if (usOrJp == true) {//美国手
                    myDrone.joystick.setRudderRaw((int) pans);
                    myDrone.joystick.setThrottleRaw((int) tilts);
                    if (!(pans == -1000 && tilts == -1000)) {
                        if (tilts <= 800 && tilts > 0) {
                            tilts = (int) (Math.pow((tilts / 1000.0), 2) * 1000);
                        } else if (tilts > 800) {
                            tilts = (int) (1.8 * tilts - 800);
                        }
                        pans = pans * JoystickSenior_MaxValue;
                        myDrone.joystick.setRudder((int) pans);
                        myDrone.joystick.setThrottle((int) tilts);
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                    } else {
                        myDrone.joystick.setRudder((int) pans);
                        myDrone.joystick.setThrottle((int) tilts);
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                    }
                } else {
                    myDrone.joystick.setRudderRaw((int) pans);
                    myDrone.joystick.setElevatorRaw((int) tilts);
                    if (!(pans == -1000 && tilts == -1000)) {
                        pans = pans * JoystickSenior_MaxValue;
                        tilts = tilts * JoystickSenior_MaxValue;
                        myDrone.joystick.setRudder((int) pans);
                        myDrone.joystick.setElevator((int) tilts);
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                    }else {
                        myDrone.joystick.setRudder((int) pans);
                        myDrone.joystick.setElevator((int) tilts);
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                    }
                }

                break;
            case 3://初级
                if (usOrJp == true) {//美国手
                    myDrone.joystick.setRudderRaw((int) pans);
                    myDrone.joystick.setThrottleRaw((int) tilts);
                    if (!(pans == -1000 && tilts == -1000)) {
                        if (tilts <= 800 && tilts > 0) {
                            tilts = (int) (Math.pow((tilts / 1000.0), 2) * 1000);
                        } else if (tilts > 800) {
                            tilts = (int) (1.8 * tilts - 800);
                        }
                        pans = pans * JoystickJunior_Sensitivity;
                        myDrone.joystick.setRudder((int) pans);
                        myDrone.joystick.setThrottle((int) tilts);
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                    } else {
                        myDrone.joystick.setRudder((int) pans);
                        myDrone.joystick.setThrottle((int) tilts);
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                    }
                } else {
                    myDrone.joystick.setRudderRaw((int) pans);
                    myDrone.joystick.setElevatorRaw((int) tilts);
                    if (!(pans == -1000 && tilts == -1000)) {
                        pans = pans * JoystickJunior_Sensitivity;
                        tilts = tilts * JoystickJunior_Sensitivity;
                        myDrone.joystick.setRudder((int) pans);
                        myDrone.joystick.setElevator((int) tilts);
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                    }else {
                        myDrone.joystick.setRudder((int) pans);
                        myDrone.joystick.setElevator((int) tilts);
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                    }
                }
                break;
        }

    }

    /**
     * 设置右边的参数
     *
     * @param pans
     * @param tilts
     */
    public void setRockerRightValue(float pans, float tilts, int operatingMode, boolean usOrJp) {
        selectMode();
        if (Math.abs(pans) < Math.abs(tilts) / 2) {
            pans = 0;
        }
        if (Math.abs(tilts) < Math.abs(pans) / 2) {
            tilts = 0;
        }
        pans = Math.round(pans * 100);
        tilts = Math.round(tilts * 100);
        switch (operatingMode) {
            case 1://高级
                if (usOrJp == false) {//日本手
                    myDrone.joystick.setAileronRaw((int) pans);
                    myDrone.joystick.setThrottleRaw((int) tilts);
                    if (tilts <= 800 && tilts > 0) {
                        tilts = (int) (Math.pow((tilts / 1000.0), 2) * 1000);
                    } else if (tilts > 800) {
                        tilts = (int) (1.8 * tilts - 800);
                    }
                    myDrone.joystick.setAileron((int) pans); //
                    myDrone.joystick.setThrottle((int) tilts);
                    myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                } else {//美国手
                    myDrone.joystick.setAileronRaw((int) pans);
                    myDrone.joystick.setElevatorRaw((int) tilts);
                    myDrone.joystick.setAileron((int) pans);
                    myDrone.joystick.setElevator((int) tilts);
                    myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);

                }
                break;
            case 2://中级
                if (usOrJp == true) {//美国手
                    myDrone.joystick.setAileronRaw((int) pans);
                    myDrone.joystick.setElevatorRaw((int) tilts);
                    if (!(pans == 1000 && tilts == -1000)) {
                        tilts = tilts * JoystickSenior_MaxValue;
                        pans = pans * JoystickSenior_MaxValue;
                        myDrone.joystick.setAileron((int) pans);
                        myDrone.joystick.setElevator((int) tilts);
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                    }else {
                        myDrone.joystick.setAileron((int) pans);
                        myDrone.joystick.setElevator((int) tilts);
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                    }
                } else {
                    myDrone.joystick.setAileronRaw((int) pans);
                    myDrone.joystick.setThrottleRaw((int) tilts);
                    if (!(pans == 1000 && tilts == -1000)) {
                        if (tilts <= 800 && tilts > 0) {
                            tilts = (int) (Math.pow((tilts / 1000.0), 2) * 1000);
                        } else if (tilts > 800) {
                            tilts = (int) (1.8 * tilts - 800);
                        }
                        pans = pans * JoystickSenior_MaxValue;
                        myDrone.joystick.setAileron((int) pans);
                        myDrone.joystick.setThrottle((int) tilts);
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                    }else {
                        myDrone.joystick.setAileron((int) pans);
                        myDrone.joystick.setThrottle((int) tilts);
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                    }
                }

                break;
            case 3: //初级
                if (usOrJp == true) {//美国手
                    myDrone.joystick.setAileronRaw((int) pans);
                    myDrone.joystick.setElevatorRaw((int) tilts);
                    if (!(pans == 1000 && tilts == -1000)) {
                        tilts = tilts * JoystickJunior_Sensitivity;
                        pans = pans * JoystickJunior_Sensitivity;
                        myDrone.joystick.setAileron((int) pans);
                        myDrone.joystick.setElevator((int) tilts);
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                    } else {
                        myDrone.joystick.setAileron((int) pans);
                        myDrone.joystick.setElevator((int) tilts);
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                    }
                } else {//日本手
                    myDrone.joystick.setAileronRaw((int) pans);
                    myDrone.joystick.setThrottleRaw((int) tilts);
                    if (!(pans == 1000 && tilts == -1000)) {
                        if (tilts <= 800 && tilts > 0) {
                            tilts = (int) (Math.pow((tilts / 1000.0), 2) * 1000);
                        } else if (tilts > 800) {
                            tilts = (int) (1.8 * tilts - 800);
                        }
                        pans = pans * JoystickJunior_Sensitivity;
                        myDrone.joystick.setAileron((int) pans);
                        myDrone.joystick.setThrottle((int) tilts);
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                    }else {
                        myDrone.joystick.setAileron((int) pans);
                        myDrone.joystick.setThrottle((int) tilts);
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_JOYSTICK_VALUE);
                    }
                }

                break;
        }
    }

    /**
     * 判断当前选择飞机的型号
     */
    private void selectMode() {
        if (myDrone.airBaseParameters.getAirSelectMode().equals("H501A")) {
            JoystickJunior_Sensitivity = 0.8f;
            JoystickSenior_MaxValue = 0.9f;
        } else {
            JoystickJunior_Sensitivity = 0.5f;
            JoystickSenior_MaxValue = 0.75f;
        }
    }
}
