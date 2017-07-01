package com.hubsansdk.service;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.common.msg_manual_control;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces;
import com.hubsansdk.drone.bean.CommonUrl;
import com.hubsansdk.utils.LogX;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 发送遥感数据
 */
public class MAVLinkUDPSendJoystick implements Runnable {

    private Thread rthread = null;
    private DatagramSocket socket = null;
    private DatagramPacket sendPacket = null;
    private int heartbeat, isTturnBackTag = 0, sendGPSTag = 0;
    private boolean isExitUDPSendJoystick = false;
    private int haveValue = 20, noVlaue = 100;
    private int aileronLTime = 0;
    private HubsanDrone drone;
    private String sendJoystickIP = CommonUrl.hubsanHeadIp;
    private int sendJoystickPORT = CommonUrl.hubsanJoystickPort;

    public MAVLinkUDPSendJoystick(HubsanDrone drone) {
        this.drone = drone;
    }

    /**
     * 开启线程发送遥感数据
     */
    public void startSendJoystickThread() {
        if (rthread == null) {
            // 必须添加this，要不然就是在主线程
            rthread = new Thread(this);
            rthread.start();
            isExitUDPSendJoystick = true;
        }
    }

    /**
     * @param haveValue 设置数据不为0时发送时间间隔
     * @param noValue   设置数据为0时发送时间间隔
     */
    public void setHaveValueTime(int haveValue, int noValue) {
        this.haveValue = haveValue;
        this.noVlaue = noValue;
    }

    /**
     * @param sendJoystickIP   IP地址
     * @param sendJoystickPORT 端口号
     */
    public void setIPAndDPROT(String sendJoystickIP, int sendJoystickPORT) {
        this.sendJoystickIP = sendJoystickIP;
        this.sendJoystickPORT = sendJoystickPORT;
    }

    /**
     * 停止当前线程发送遥感数据
     */
    public void stopSendJoystickThread() {

        if (rthread != null && rthread.isAlive()) {
            rthread.interrupt();
        }
        isExitUDPSendJoystick = false;
    }

    @Override
    public void run() {

        while (isExitUDPSendJoystick == true) {
            if (drone.compassCalibration.isHorizontalCalibration() == true) {
                aileronLTime = aileronLTime + 1;
                if (aileronLTime <= 20) {
                    for (int i = 0; i < 3; i++) {
//                        LogX.e("isHorizontalCalibration---------------" + i);
                        drone.joystick.setRudder(1000);
                        if (aileronLTime % 2 == 0) {
                            drone.joystick.setAileron(1000);
                        } else {
                            drone.joystick.setAileron(-1000);
                        }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    drone.joystick.setRudder(0);
                    drone.joystick.setAileron(0);
                    drone.compassCalibration.setHorizontalCalibration(false);
                    aileronLTime = 0;
                    drone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_HORIZONTAL_CALIBRATION_OVER);
                }
                sendRelayHearBeat();
                sendGPSVlaue();
                sendTimingRocker();
            } else {
                int mRudder = drone.joystick.getRudder();
                int mThrottle = drone.joystick.getThrottle();
                int mElevator = drone.joystick.getElevator();
                int mAileron = drone.joystick.getAileron();
                if (mRudder == 0 && mThrottle == 0 && mElevator == 0 && mAileron == 0) {
                    try {
                        // 发送遥感数据
                        sendRelayHearBeat();
                        sendTimingRocker();
                        sendGPSTag = sendGPSTag + 1;
                        if (sendGPSTag >= 2) {
                            sendGPSVlaue();
                            sendGPSTag = 0;
                        }
                        Thread.sleep(noVlaue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        if (drone.airMode.isReturnBackMode() == false) {
                            //发送遥感数据
                            sendTimingRocker();
                        } else {
                            //在返航时对头对尾
                            int rudder = drone.joystick.getRudder();
                            if (rudder >= 0 || rudder <= 0) {
                                sendTimingRocker();
                            }
                            isTturnBackTag = isTturnBackTag + 20;
                            if (isTturnBackTag >= 800) {
                                if (drone.joystick.isJoystickMode()) {
                                    if (Math.abs(mAileron) > 100 || Math.abs(mElevator) > 100) {
                                        //发送取消返航
                                        drone.mavLinkSendMessage.sendCloseReturnRouteBack();
                                        drone.commonNotify.udpCancelTurnback();
                                        sendTimingRocker();
                                        isTturnBackTag = 0;
                                    }
                                } else if (Math.abs(mAileron) > 100 || Math.abs(mThrottle) > 100) {
                                    //发送取消返航
                                    drone.mavLinkSendMessage.sendCloseReturnRouteBack();
                                    drone.commonNotify.udpCancelTurnback();
                                    sendTimingRocker();
                                    isTturnBackTag = 0;
                                }
                            } else {
                                if (isTturnBackTag >= 1000) {
                                    isTturnBackTag = 0;
                                }
                            }
                        }
                        sendRelayHearBeat();
                        sendGPSTag = sendGPSTag + 1;
                        if (sendGPSTag >= 5) {
                            sendGPSVlaue();
                            sendGPSTag = 0;
                        }
                        // 501A
                        Thread.sleep(haveValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 发送GPS的经纬度,卫星个数
     *
     * @param
     */
    public void sendGPSVlaue() {
        int lat = (int) (drone.gpsManager.mLat * 10000000);
        int lon = (int) (drone.gpsManager.mLon * 10000000);
        short GPSNumber = (short) drone.airGPS.getGpsNumber();
        if (lat != 0 & lon != 0) {
            if (drone.airConnectionStatus.isAirConnection() == true) {
                //手机位置 发送本地有效经纬度，飞机位置 发送0
                if (drone.airMode.isReturnBackMode() == true) {
                    if (drone.locationGPS.isAirUpFlyPoint()) {
                        drone.mavLinkSendMessage.sendGPSStatus(0, 0, GPSNumber);
                    } else {
                        drone.mavLinkSendMessage.sendGPSStatus(lat, lon, GPSNumber);
                    }
                } else {
                    drone.mavLinkSendMessage.sendGPSStatus(lat, lon, GPSNumber);
                }
            }
        }
    }

    /**
     * 发送心跳给中继器
     */
    private void sendRelayHearBeat() {
        heartbeat = heartbeat + 20;
        if (heartbeat >= 1000) {
            heartbeat = 0;
            if (drone.airConnectionStatus.isAirConnection() == true) {
                // 发送距离给中继器
                drone.mavLinkSendMessage.sendAirDistance(drone.airBaseParameters.getX());
            }
        }
    }

    /**
     * 发送遥感数据
     */
    public void sendTimingRocker() {
        msg_manual_control manual_control = new msg_manual_control();
        manual_control.x = (short) drone.joystick.getRudder();
        manual_control.y = (short) drone.joystick.getThrottle();
        manual_control.z = (short) drone.joystick.getAileron();
        manual_control.r = (short) drone.joystick.getElevator();
//        LogX.e("getRudder():"+drone.joystick.getRudder()+"     ,getThrottle():"+drone.joystick.getThrottle()
//                +"     ,getAileron():"+drone.joystick.getAileron()+"     ,getElevator():"+drone.joystick.getElevator());
        // 1:不需要GPS    0:需要GPS
//        manual_control.buttons = drone.joystick.getIsNeedGPS();
        manual_control.buttons = 1;
        MAVLinkPacket packet = manual_control.pack();
        // 判断当前链接上飞机并且没有退出界面
        if (drone.airConnectionStatus.isAirConnection() == true) {
            if (drone.airConnectionStatus.getConnectionType() == 0) {
                // 飞机
//                if (drone.airRelayEquipmentCertification.isAirEquipmentCertification() == true) {
                    SendData(packet);
//                } else {
//                    sendRockerMedian();
//                }
            } else {
                // 中继器
                if (drone.airRelayEquipmentCertification.isRepeaterEquipmentCertification()) {
                    SendData(packet);
                } else {
                    sendRockerMedian();
                }
            }
        }
    }

    private void sendRockerMedian() {
        msg_manual_control manual_controls = new msg_manual_control();
        manual_controls.x = (short) 0;
        manual_controls.y = (short) 0;
        manual_controls.z = (short) 0;
        manual_controls.r = (short) 0;
        SendData(manual_controls.pack());
    }

    /**
     * 通过UDP发送遥感数据 Description
     *
     * @see [class/class#field/class#method]
     */
    public void SendData(MAVLinkPacket packet) {
        try {
            byte[] sBuffer = packet.encodePacket();
            InetAddress serverAddress = InetAddress.getByName(sendJoystickIP);
            if (socket == null) {
                socket = new DatagramSocket();
                socket.setSoTimeout(3000);
            }
            if (sendPacket == null) {
                sendPacket = new DatagramPacket(sBuffer, sBuffer.length, serverAddress, sendJoystickPORT);
            }
            socket.send(sendPacket);
            msg_manual_control data = (msg_manual_control)packet.unpack();
//            LogX.e("udp发送遥感数据  编号" + packet.msgid+"     "+data.toString());

            socket.close();
            sendPacket = null;
            socket = null;
        } catch (Exception ie) {
            ie.printStackTrace();
            LogX.e("udp发送遥感数据  异常");
            socket.close();
            sendPacket = null;
            socket = null;
        }
    }

}
