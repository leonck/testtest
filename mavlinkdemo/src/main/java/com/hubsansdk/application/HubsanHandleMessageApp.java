package com.hubsansdk.application;

import com.MAVLink.Messages.MAVLinkMessage;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces.DroneEventsType;
import com.hubsansdk.drone.bean.LocalLatLon;
import com.hubsansdk.mavlink.MavLinkMsgHandler;
import com.hubsansdk.service.MAVLinkClient;
import com.hubsansdk.service.MAVLinkClient.OnMavlinkClientListener;
import com.hubsansdk.service.MAVLinkUDPSendJoystick;

/**
 * 通过接口监听MAVLink消息和中继器消息
 *
 * @author shengkun.cheng
 */
public class HubsanHandleMessageApp extends HubsanApplication implements
        OnMavlinkClientListener {


    public HubsanDrone drone;
    private MavLinkMsgHandler mavLinkMsgHandler;
    private MAVLinkClient mAVClient;
    private MAVLinkUDPSendJoystick mUDPthread;
    public LocalLatLon localLatLon = new LocalLatLon();

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mAVClient = new MAVLinkClient(this, this);
        drone = new HubsanDrone(mAVClient);
        mavLinkMsgHandler = new MavLinkMsgHandler(drone);
        //开启服务,启动线程链接心跳
        mAVClient.startService();
        if (mUDPthread == null) {
            mUDPthread = new MAVLinkUDPSendJoystick(drone);
        }
        //启动发送遥感数据的线程
        mUDPthread.startSendJoystickThread();
    }

    public HubsanHandleMessageApp() {

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        drone.mavLinkClient.stopBindService();
        if (mUDPthread != null) {
            mUDPthread.stopSendJoystickThread();
            mUDPthread = null;
        }
    }

    @Override
    public void notifyAirConnected() {
        // TODO Auto-generated method stub
        drone.events.notifyDroneEvent(DroneEventsType.HUBSAN_AIR_CONNECTION);
    }

    @Override
    public void notifyAirDisconnected() {
        // TODO Auto-generated method stub
        drone.events.notifyDroneEvent(DroneEventsType.HUBSAN_AIR_DISCONNECTION);
        drone.airHeartBeat.airDisConnection();
        mavLinkMsgHandler.setMavlinkDeful();
    }

    @Override
    public void notifyAirReceivedData(MAVLinkMessage msg) {
        // TODO Auto-generated method stub
        mavLinkMsgHandler.receiveAirData(msg);

    }

    @Override
    public void notifyRelayReceivedData(MAVLinkMessage msg) {
        // TODO Auto-generated method stub
        mavLinkMsgHandler.receiveRelayData(msg);
    }

    @Override
    public void notifyRelayDisconnected() {
        // TODO Auto-generated method stub
        drone.events.notifyDroneEvent(DroneEventsType.HUBSAN_RELAY_DISCONNECTION);
    }

}
