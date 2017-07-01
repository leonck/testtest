package com.hubsansdk.drone;

import android.os.Handler;
import android.os.Message;

import com.hubsansdk.utils.Constants;

/**
 * Created by shengkun.cheng on 2017/2/8.
 */

public class HubsanSendInstructionAir extends HubsanDroneVariable {

    private byte headMode = 0, followMode = 0, surroundFlyMode = 0, waypointMode = 0, returnBackMode = 0;
    private int modeType = 0;
    private int modeTimeOut = 0;
    private final int HUBSAN_RETURN_BACK = 2, HUBSAN_SURROUND = 3, HUBSAN_WAYPOINT = 4, HUBSAN_FOLLOW_ME = 1, HUBSAN_UNLOCK = 5,
            HUBSAN_RELAYER_REQUEST_WIFI_LIST = 6, HUBSAN_RELAYER_SETTING_WIFI = 7;


    public HubsanSendInstructionAir(HubsanDrone drone) {
        super(drone);
    }

    /**
     * Operating camera
     *
     * @param type instruction type
     * @return
     */
    public boolean sendCameraVideoInstruction(HubsanDroneInterfaces.CameraVideoType type) {
        boolean isSuccess = false;
        switch (type) {
            case HUBSAN_SELECT_CAMERA_MODE:
                //拍照模式
                isSuccess = myDrone.hCamera.changePhotographMode();
                break;
            case HUBSAN_SELECT_VIDEO_MODE:
                //录像模式
                isSuccess = myDrone.hCamera.changeRecordingMode();
                break;
            case HUBSAN_START_CAMERA:
                //拍照
                isSuccess = myDrone.hCamera.startPhotograph();
                break;
            case HUBSAN_START_VIDEO:
                //开始录像
                isSuccess = myDrone.hCamera.startVideotape();
                break;
            case HUBSAN_STOP_VIDEO:
                //停止录像
                isSuccess = myDrone.hCamera.stopVideotape();
                break;
        }
        return isSuccess;
    }

    /**
     * 发送控制命令
     *
     * @param type
     * @param status
     */
    public void sendModeInstruction(HubsanDroneInterfaces.AirAllModeType type, boolean status) {
        switch (type) {
            case HUBSAN_FOLLOW_MODE:
                if (myDrone.airHeartBeat.isMotorStatus() == true) {
                    if (status == true) {
                        //开启跟随
                        modeType = HUBSAN_FOLLOW_ME;
                        modeTimeOut = 0;
                        followMode |= Constants.HUBSAN_FOLLOW_MODE;
                        myDrone.mavLinkSendMessage.sendHeadModel(followMode);
                        reStartTime();
                    } else {
                        //取消跟随
                        modeTimeOut = 0;
                        timeHandler.removeCallbacks(mRunnable);
                        followMode &= ~Constants.HUBSAN_FOLLOW_MODE;
                        myDrone.mavLinkSendMessage.sendHeadModel(followMode);
                    }
                } else {
                    if (myDrone.airMode.isFollowMode()==true){
                        //取消跟随
                        modeTimeOut = 0;
                        timeHandler.removeCallbacks(mRunnable);
                        followMode &= ~Constants.HUBSAN_FOLLOW_MODE;
                        myDrone.mavLinkSendMessage.sendHeadModel(followMode);
                    }
                    myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_MOTOR_STOP_NOTIFY);
                }
                break;
            case HUBSAN_RETURN_HOME:
                if (myDrone.airHeartBeat.isMotorStatus() == true) {
                    if (status == true) {
                        //开启返航
                        modeType = HUBSAN_RETURN_BACK;
                        modeTimeOut = 0;
                        returnBackMode |= Constants.HUBSAN_RETURN_HOME;
                        myDrone.mavLinkSendMessage.sendReturnRouteBack(1);
                    } else {
                        //取消返航
                        modeTimeOut = 0;
                        timeHandler.removeCallbacks(mRunnable);
                        returnBackMode &= ~Constants.HUBSAN_RETURN_HOME;
                        myDrone.mavLinkSendMessage.sendReturnRouteBack(0);
                    }
                } else {
                    if (myDrone.airMode.isReturnBackMode()==true){
                        modeTimeOut = 0;
                        timeHandler.removeCallbacks(mRunnable);
                        returnBackMode &= ~Constants.HUBSAN_RETURN_HOME;
                        myDrone.mavLinkSendMessage.sendReturnRouteBack(0);
                    }
                    myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_MOTOR_STOP_NOTIFY);
                }
                break;
            case HUBSAN_SURROUND_FLY:
                if (myDrone.airHeartBeat.isMotorStatus() == true) {
                    if (status == true) {
                        //开启环绕
                        modeType = HUBSAN_SURROUND;
                        modeTimeOut = 0;
                        surroundFlyMode |= Constants.HUBSAN_SURROUND_FLY;
                        myDrone.mavLinkSendMessage.sendHeadModel((short) 0x80);
                        reStartTime();
                    } else {
                        //取消环绕
                        modeTimeOut = 0;
                        timeHandler.removeCallbacks(mRunnable);
                        surroundFlyMode &= ~Constants.HUBSAN_SURROUND_FLY;
                        myDrone.mavLinkSendMessage.sendHeadModel((short) 0x00);
                    }
                } else {
                    if (myDrone.airMode.isSurroundFly() == true) {
                        modeTimeOut = 0;
                        timeHandler.removeCallbacks(mRunnable);
                        surroundFlyMode &= ~Constants.HUBSAN_SURROUND_FLY;
                        myDrone.mavLinkSendMessage.sendHeadModel((short) 0x00);
                    }
                    myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_MOTOR_STOP_NOTIFY);
                }
                break;
            case HUBSAN_WAYPOINT_FLY:
                if (myDrone.airHeartBeat.isMotorStatus() == true) {
                    if (status == true) {
                        //开启航点
                        modeType = HUBSAN_WAYPOINT;
                        modeTimeOut = 0;
                        waypointMode |= Constants.HUBSAN_WAYPOINT_FLY;
                        myDrone.mavLinkSendMessage.sendHeadModel(waypointMode);
                        reStartTime();
                    } else {
                        //取消航点
                        modeTimeOut = 0;
                        timeHandler.removeCallbacks(mRunnable);
                        waypointMode &= ~Constants.HUBSAN_WAYPOINT_FLY;
                        myDrone.mavLinkSendMessage.sendHeadModel(waypointMode);
                    }
                } else {
                    if (myDrone.airMode.isWaypointMode() == true) {
                        //取消航点
                        modeTimeOut = 0;
                        timeHandler.removeCallbacks(mRunnable);
                        waypointMode &= ~Constants.HUBSAN_WAYPOINT_FLY;
                        myDrone.mavLinkSendMessage.sendHeadModel(waypointMode);
                    }
                    myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_MOTOR_STOP_NOTIFY);
                }
                break;
            case HUBSAN_FREE_HEAD://有头无头
                if (myDrone.airMode.isHeadMode() == true) {
                    headMode |= Constants.HUBSAN_FREE_HEAD;
                    myDrone.mavLinkSendMessage.sendHeadModel(headMode);
                } else {
                    headMode &= ~Constants.HUBSAN_FREE_HEAD;
                    myDrone.mavLinkSendMessage.sendHeadModel(headMode);
                }
                break;
            case HUBSAN_UNLOCK://解锁
                if (myDrone.airRelayEquipmentCertification.isAirEquipmentCertification() == true) {
                    modeType = HUBSAN_UNLOCK;
                    modeTimeOut = 0;
                    reStartTime();
                    myDrone.airMode.setAirLockStatus(true);
                } else {
                    myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_NO_EQUIPMENT);
                }
                break;
            case HUBSAN_FORCE_UNLOCK://强制解锁
                if (myDrone.airRelayEquipmentCertification.isAirEquipmentCertification() == true) {
                    if (myDrone.airGPS.getGpsNumber() < 6) {
                        myDrone.joystick.setIsNeedGPS(1);//不需要GPS
                        modeType = HUBSAN_UNLOCK;
                        modeTimeOut = 0;
                        myDrone.airMode.setAirLockStatus(true);
                        reStartTime();
                    }
                } else {
                    myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_NO_EQUIPMENT);
                }
                break;
            case HUBSAN_LOCK://上锁
                if (status == true) {
                    myDrone.mavLinkSendMessage.sendBackAir();
                }
                break;
            default:
                timeHandler.removeCallbacks(mRunnable);
                break;
        }
    }

    /**
     * 1:发送请求获取中继器列表,2:设置中继器和飞机连接
     *
     * @param relayerType
     */
    public void sendRelayerInstruction(HubsanDroneInterfaces.RelayerType relayerType) {
        switch (relayerType) {
            case HUBSAN_REQUEST_WIFI_LIST://请求WiFi列表
                modeType = HUBSAN_RELAYER_REQUEST_WIFI_LIST;
                modeTimeOut = 0;
                myDrone.airRelayAck.cleanRelayWifiList();//清空集合中的数据
                myDrone.mavLinkSendMessage.sendRequestWifiList();
                reStartTime();
                break;
            case HUBSAN_SET_WIFI://设置WiFi
                modeType = HUBSAN_RELAYER_SETTING_WIFI;
                modeTimeOut = 0;
                myDrone.mavLinkSendMessage.sendSetWifi(myDrone.relayList.getRelayerKey(), myDrone.relayList.getRelayerSSID());
                myDrone.airRelayAck.setRelayerSettingIsSuccess(100);
                reStartTime();
                break;
            case HUBSAN_REQUEST_RELAYER_VERSION_INFO:
                //获取中继器版本信息
//                if (myDrone.airConnectionStatus.getConnectionType() == 1) {
                myDrone.mavLinkSendMessage.sendRequesRelayVersion();
//                }
                break;
        }
    }

    private void reStartTime() {
        modeTimeOut = 0;
        timeHandler.removeCallbacks(mRunnable);
        timeHandler.postDelayed(mRunnable, 100);
    }

    private Handler timeHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Message msg = new Message();
            msg.what = modeType;
            mHandler.sendMessage(msg);
            if (mHandler != null) {
                if (modeType == HUBSAN_UNLOCK) {
                    timeHandler.postDelayed(mRunnable, 500);
                } else {
                    timeHandler.postDelayed(mRunnable, 1000);
                }
            }
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HUBSAN_FOLLOW_ME://跟随
                    modeTimeOut = modeTimeOut + 1;
                    if (modeTimeOut <= 3) {
                        if (myDrone.airMode.isFollowMode() == true) {
                            modeTimeOut = 0;
                            timeHandler.removeCallbacks(mRunnable);
                        } else {
                            if (modeTimeOut == 3) {
                                modeTimeOut = 0;
                                myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_OPEN_FOLLOW_ME_FAILED);
                                timeHandler.removeCallbacks(mRunnable);
                            }
                        }
                    }
                    break;
                case HUBSAN_RETURN_BACK://返航
                    modeTimeOut = modeTimeOut + 1;
                    if (modeTimeOut <= 3) {
                        if (myDrone.airMode.isReturnBackMode() == true) {
                            modeTimeOut = 0;
                            timeHandler.removeCallbacks(mRunnable);
                        } else {
                            if (modeTimeOut == 3) {
                                modeTimeOut = 0;
                                myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_OPEN_RETURN_BACK_FAILED);
                                timeHandler.removeCallbacks(mRunnable);
                            }
                        }
                    }
                    break;
                case HUBSAN_SURROUND://环绕
                    modeTimeOut = modeTimeOut + 1;
                    if (modeTimeOut <= 3) {
                        if (myDrone.airMode.isSurroundFly() == true) {
                            modeTimeOut = 0;
                            timeHandler.removeCallbacks(mRunnable);
                        } else {
                            if (modeTimeOut == 3) {
                                modeTimeOut = 0;
                                myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_OPEN_SURROUND_FAILED);
                                timeHandler.removeCallbacks(mRunnable);
                            }
                        }
                    }

                    break;
                case HUBSAN_WAYPOINT://航点
                    modeTimeOut = modeTimeOut + 1;
                    if (modeTimeOut <= 4) {
                        if (myDrone.airMode.isWaypointMode() == true) {
                            modeTimeOut = 0;
                            timeHandler.removeCallbacks(mRunnable);
                        } else {
                            if (modeTimeOut == 4) {
                                modeTimeOut = 0;
                                timeHandler.removeCallbacks(mRunnable);
                                myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_OPEN_WAYPOINT_FAILED);
                            }
                        }
                    }
                    break;
                case HUBSAN_UNLOCK://解锁
                    modeTimeOut = modeTimeOut + 1;
                    myDrone.joystick.setAileron(1000);
                    myDrone.joystick.setThrottle(-1000);
                    myDrone.joystick.setRudder(-1000);
                    myDrone.joystick.setElevator(-1000);
                    if (modeTimeOut >= 4) {
                        modeTimeOut = 0;
                        myDrone.joystick.setAileron(0);
                        myDrone.joystick.setThrottle(0);
                        myDrone.joystick.setRudder(0);
                        myDrone.joystick.setElevator(0);
                        timeHandler.removeCallbacks(mRunnable);
                        myDrone.airMode.setAirLockStatus(false);
                    }
                    break;
                case HUBSAN_RELAYER_REQUEST_WIFI_LIST:
                    modeTimeOut = modeTimeOut + 1;
                    if (modeTimeOut >= 9) {
                        //请求超时
                        timeHandler.removeCallbacks(mRunnable);
                        modeTimeOut = 0;
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_REQUEST_WIFI_LIST_TIME_OUT);
                    } else {
                        if (myDrone.airRelayAck.getRelayerListSize() > 0 && myDrone.airRelayAck.getRelayWifiLists().size() > 0) {
                            timeHandler.removeCallbacks(mRunnable);
                            modeTimeOut = 0;
                            //广播WIFI列表
                            myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_RELAY_WIFI_LIST);
                        }
                    }
                    break;
                case HUBSAN_RELAYER_SETTING_WIFI:
                    modeTimeOut = modeTimeOut + 1;
                    if (modeTimeOut >= 9) {
                        timeHandler.removeCallbacks(mRunnable);
                        modeTimeOut = 0;
                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_SET_WIFI_TIME_OUT);
                    } else {
                        if (myDrone.airRelayAck.getRelayerSettingIsSuccess() == 1) {
                            //设置成功
                            timeHandler.removeCallbacks(mRunnable);
                            modeTimeOut = 0;
                            myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_RELAYER_SETTING_SUCCESS);
                        } else if (myDrone.airRelayAck.getRelayerSettingIsSuccess() == 0) {
                            //WiFi连接失败，请刷新列表
                            timeHandler.removeCallbacks(mRunnable);
                            modeTimeOut = 0;
                            myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_RELAYER_SETTING_FAILED);
                        }
                    }
                    break;
            }
        }
    };

}
