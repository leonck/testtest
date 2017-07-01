//package com.hubsan.swifts.bean;
//
//import android.os.Handler;
//import android.os.Message;
//
//import com.hubsan.swifts.HubsanDrone;
//import com.hubsan.swifts.hubsansdk.api.msg_heartbeat;
//
//
///**
// * 心跳
// *
// * @author shengkun.cheng
// */
//public class AirHeartBeat extends HubsanDroneVariable {
//
//    private msg_heartbeat msgHeartbeat;
//    //马达是否转动状态
//    private boolean motorStatus;
//    private String locationBSSID;
//    public boolean isFirstConnection = false;
//    public boolean isFirstTimerSync = false;
//    private int firstRequestTime = 0;
//    private int autoHorizontalCTag = 0;
//    private int showHCDialogTag = 0;
//
//    public AirHeartBeat(HubsanDrone myDrone) {
//        super(myDrone);
//        // TODO Auto-generated constructor stub
//    }
//
//    // 接收到的心跳信息
//    public void doHeartBeat(msg_heartbeat msg) {
//        setMsgHeartbeat(msg);
//        airBSSID(msg);
//        myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_HEARTBEAT);
//        //3:电机停止工作  4:电机开始工作
//        if (msg.system_status == 3) {
//            //电机停止工作
//            this.motorStatus = false;
//            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_MOTOR_STOP);
//        } else if (msg.system_status == 4) {
//            //电机开始工作
//            this.motorStatus = true;
//            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_MOTOR_START);
//        }
//        //判断是否绑定 0:已认证  1:未认证
//        if (((msg.base_mode >> 1) & 0x01) == 0) {
//            myDrone.airRelayEquipmentCertification.setAirEquipmentCertification(true);
//        } else {
//            myDrone.airRelayEquipmentCertification.setAirEquipmentCertification(false);
//        }
//        if (((msg.base_mode >> 2) & 0x01) == 0) {
//            myDrone.compassCalibration.setNeedHorizontalCalibration(false);
//        } else {
//            //需要进行水平校准
//            showHCDialogTag = showHCDialogTag + 1;
//            if (showHCDialogTag == 1) {
//                myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_NEED_HORIZONTAL_CALIBRATION);
//                myDrone.compassCalibration.setNeedHorizontalCalibration(true);
//                showHCDialogTag = 2;
//            }
//        }
//        if (((msg.base_mode >> 3) & 0x01) == 0) {
//            autoHorizontalCTag = autoHorizontalCTag + 1;
//            if (autoHorizontalCTag >= 12) {
//                myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_NEED_HORIZONTAL_CALIBRATION_OVER);
//                //水平校准完成
//                myDrone.compassCalibration.setAutoHorizontalCalibration(false);
//                autoHorizontalCTag = 0;
//            }
//        } else {
//            //正在进行水平校准
//            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_NEED_HORIZONTAL_CALIBRATION_ING);
//            myDrone.compassCalibration.setAutoHorizontalCalibration(true);
//            showHCDialogTag = 0;
//        }
////        LogX.e("" + ((msg.base_mode) & 0x01) + "：" + ((msg.base_mode >> 1) & 0x01) + "：" + ((msg.base_mode >> 2) & 0x01) + "：" + ((msg.base_mode >> 3) & 0x01));
//        if (isFirstConnection == false) {
//            mHandler.postDelayed(mRunnable, 10);
//            isFirstConnection = true;
//        }
//    }
//
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            firstRequestTime = firstRequestTime + 1;
//            switch (msg.what) {
//                case 1:
//                    if (firstRequestTime == 1) {
//                        myDrone.mavLinkSendMessage.sendSDCardRequestd();
//                    } else if (firstRequestTime == 2) {
//                        myDrone.mavLinkSendMessage.sendSysTime();
//                    } else if (firstRequestTime == 3) {
//                        myDrone.mavLinkSendMessage.sendRequestParamList();
//                    } else if (firstRequestTime == 4) {
//                        myDrone.mavLinkSendMessage.sendAutopilotVer();
//                        mHandler.removeCallbacks(mRunnable);
//                    }
//                    break;
//                default:
//                    mHandler.removeCallbacks(mRunnable);
//                    break;
//            }
//        }
//    };
//
//    //请求飞机的基本参数
//    public void requestAirBaseValue() {
//        firstRequestTime = 0;
//        mHandler.removeCallbacks(mRunnable);
//        mHandler.postDelayed(mRunnable, 10);
//    }
//
//    private Runnable mRunnable = new Runnable() {
//        @Override
//        public void run() {
//            Message message = new Message();
//            message.what = 1;
//            mHandler.sendMessage(message);
//            mHandler.postDelayed(mRunnable, 500);
//        }
//    };
//
//    /**
//     * 解析飞机发送过来的ssid
//     *
//     * @param msg_heart
//     */
//    public void airBSSID(msg_heartbeat msg_heart) {
//        int mMac1 = ((int) msg_heart.custom_mode >> 24) & 0xff;
//        int mMac2 = ((int) msg_heart.custom_mode >> 16) & 0xff;
//        int mMac3 = ((int) msg_heart.custom_mode >> 8) & 0xff;
//        int mMac4 = (int) msg_heart.custom_mode & 0xff;
//        int mMac5 = (int) msg_heart.type & 0xff;
//        int mMac6 = (int) msg_heart.autopilot & 0xff;
//        myDrone.airBSSID.setAirMac1(mMac1);
//        myDrone.airBSSID.setAirMac2(mMac2);
//        myDrone.airBSSID.setAirMac3(mMac3);
//        myDrone.airBSSID.setAirMac4(mMac4);
//        myDrone.airBSSID.setAirMac5(mMac5);
//        myDrone.airBSSID.setAirMac6(mMac6);
//
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append(ParameterUtil.textSizeAddZero(mMac1) + ":"
//                + ParameterUtil.textSizeAddZero(mMac2) + ":"
//                + ParameterUtil.textSizeAddZero(mMac3) + ":"
//                + ParameterUtil.textSizeAddZero(mMac4) + ":"
//                + ParameterUtil.textSizeAddZero(mMac5) + ":"
//                + ParameterUtil.textSizeAddZero(mMac6));
//        myDrone.airBSSID.setAirBssid(stringBuffer.toString());
//        if (myDrone.airBSSID.getAirBssid().equals(myDrone.airPhoneBSSID.getPhoneBSSID())) {// 两个字符串相等的时候为飞机
//            myDrone.airConnectionStatus.setConnectionType(0);
//            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_CONNECTION_AIR);
//        } else if (mMac1 == 0 && mMac2 == 0 && mMac3 == 0 && mMac4 == 0
//                && mMac5 == 0 && mMac6 == 0) {
//            myDrone.airConnectionStatus.setConnectionType(0);
//            myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_CONNECTION_AIR);
//        } else {
//            //主要是为了判断中继器连接不上的时候，出现异常
//            if (myDrone.airConnectionStatus.isReceiveBSSID()) {
//                // 不相等的时候中继器
//                myDrone.airConnectionStatus.setConnectionType(1);
//                myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_CONNECTION_RELAY);
//            } else {
//                myDrone.airConnectionStatus.setConnectionType(0);
//                myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_CONNECTION_AIR);
//            }
//        }
//    }
//
//    /**
//     * 飞机心跳断开
//     */
//    public void airDisConnection() {
//        isFirstConnection = false;
//        isFirstTimerSync = false;
//    }
//
//    public msg_heartbeat getMsgHeartbeat() {
//        return msgHeartbeat;
//    }
//
//    public void setMsgHeartbeat(msg_heartbeat msgHeartbeat) {
//        this.msgHeartbeat = msgHeartbeat;
//    }
//
//    public boolean isMotorStatus() {
//        return motorStatus;
//    }
//
//    public String getLocationBSSID() {
//        return locationBSSID;
//    }
//
//    public void setLocationBSSID(String locationBSSID) {
//        this.locationBSSID = locationBSSID;
//    }
//
//
//}
