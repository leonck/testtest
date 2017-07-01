package com.hubsansdk.service;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.common.msg_heartbeat;
import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.application.HubsanHandleMessageApp;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces;
import com.hubsansdk.drone.bean.CommonStatus;
import com.hubsansdk.drone.bean.CommonUrl;
import com.hubsansdk.utils.ChildThread;
import com.hubsansdk.utils.LogX;
import com.hubsansdk.utils.NetUtil;

/**
 * Description connection MAVLink
 * 连接中继器和心跳的中间类
 *
 * @author ShengKun.Cheng
 * @date 2015年7月15日
 * @see [class/class#method]
 * @since [product/model]
 */
@SuppressLint("HandlerLeak")
@SuppressWarnings("unused")
public class MAVLinkClient {
    public static final int MSG_RECEIVED_DATA = 0;
    public static final int MSG_SELF_DESTRY_SERVICE = 1;
    public static final int MSG_TIMEOUT = 2;
    public static final int MSG_RELAY_RECEIVED_DATA = 3;
    public static final int MSG_NETWORK_CONNECTION = 4;
    public static final int MSG_DISCONNECTION = 5;
    private Context parent;
    private static OnMavlinkClientListener listener;
    private Messenger mService = null;
    private IncomingHandler incomingHandler;
    private Messenger mMessenger;
    private boolean mIsBound = false;
    private boolean isConnection;
    private int mavlinkResetTime = 1500;
    private int relaykResetTime = 0;
    private int mavlinkTimeout = 0;
    private MAVLinkPacket pack;
    private MAVLinkPacket packRelay;
    private HubsanHandleMessageApp app;
    private HubsanDrone drone;

    public interface OnMavlinkClientListener {

        void notifyAirConnected();

        void notifyAirDisconnected();

        void notifyAirReceivedData(MAVLinkMessage msg);

        void notifyRelayReceivedData(MAVLinkMessage msg);

        void notifyRelayDisconnected();
    }

    @SuppressWarnings("static-access")
    public MAVLinkClient(Context context, OnMavlinkClientListener listener) {
        this.parent = context;
        this.listener = listener;
    }

    // TODO 启动服务
    public void startService() {
        CommonStatus.isExitAirConnection = false;
        // 启动服务
        Intent mStartBind = new Intent();
        mStartBind.setClass(parent, MAVLinkService.class);
        parent.bindService(mStartBind, mConnection, Context.BIND_AUTO_CREATE);
        // 启动定时器
        resetAirTimeout();
        resetRelayTimeout();
        incomingHandler = new IncomingHandler();
        mMessenger = new Messenger(incomingHandler);
        app = (HubsanHandleMessageApp) HubsanApplication.getApplication();
        this.drone = app.drone;
    }

    // TODO 解绑服务
    public void stopBindService() {
        LogX.e("stop service");
        CommonStatus.isExitAirConnection = true;
        // 解绑服务
        if (isConnection == true) {
            Intent mBind = new Intent();
            mBind.setClass(parent, MAVLinkService.class);
            parent.unbindService(mConnection);
            isConnection = false;
            mService = null;
            relayHandler.removeCallbacks(relayRunnable);
            airHandler.removeCallbacks(airRunnable);
            incomingHandler.removeMessages(MSG_RECEIVED_DATA);
            incomingHandler.removeMessages(MSG_SELF_DESTRY_SERVICE);
            incomingHandler.removeMessages(MSG_TIMEOUT);
            incomingHandler.removeMessages(MSG_RELAY_RECEIVED_DATA);
            incomingHandler = null;
            mMessenger = null;
        }
        ChildThread.destoeyThread();

    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {

            if (mService == null) {
                mService = new Messenger(service);
            }
            try {
                Message msg = Message.obtain(null, MAVLinkService.MSG_REGISTER_CLIENT);
                msg.replyTo = mMessenger;
                mService.send(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            isConnection = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            // onDisconnectService();
        }
    };

    // TODO 接收心跳数据
    @SuppressLint("HandlerLeak")
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_RECEIVED_DATA: // 接收心跳的数据
                    Bundle b = msg.getData();
                    MAVLinkMessage m = (MAVLinkMessage) b.getSerializable("msg");
                    listener.notifyAirConnected();
                    listener.notifyAirReceivedData(m);
                    if (m.msgid == msg_heartbeat.MAVLINK_MSG_ID_HEARTBEAT) {
                        mIsBound = true;
                        drone.airConnectionStatus.setAirConnection(true);
                    }
//                    LogX.e("========接收到心跳======");
                    resetAirTimeout();
                    break;
                case MSG_SELF_DESTRY_SERVICE:
                    mIsBound = false;
                    break;
                case MSG_RELAY_RECEIVED_DATA:
                    // 接收中继器的数据
                    Bundle relayBundle = msg.getData();
                    MAVLinkMessage relayMsg = (MAVLinkMessage) relayBundle.getSerializable("relaymsg");
                    LogX.e("======接收中继器的数据========="+relayMsg);
                    listener.notifyRelayReceivedData(relayMsg);
                    resetRelayTimeout();
                    break;
                case MSG_DISCONNECTION:
                    listener.notifyAirDisconnected();
                    break;
                default:
                    // super.handleMessage(msg);
                    break;
            }
        }
    }

//    /**
//     * 发送飞机的消息
//     *
//     * @param pack
//     */
//    public void sendMavPacket(MAVLinkPacket pack) {
//        this.pack = pack;
////        LogX.e("pack.msgid:"+pack.msgid);
//        if (drone.airConnectionStatus.isAirConnection() == true) {
//            ChildThread.post(mRunnable);
//        }
//    }

    /**
     * 发送飞机的消息
     *
     * @param
     */
    public void sendMavPacket(final MAVLinkPacket packtemp) {
        this.pack = packtemp;
//        LogX.e("pack.msgid:"+pack.msgid);
        if (drone.airConnectionStatus.isAirConnection() == true) {
//            ChildThread.post(mRunnable);
            ChildThread.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    MAVLinkService.setMAVLinkData(packtemp);
                }
            });
        }
    }


    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            MAVLinkService.setMAVLinkData(pack);
        }
    };

    /**
     * 发送给中继器数据
     *
     * @param pack
     */
    public void sendRelayPacker(MAVLinkPacket pack) {
        this.packRelay = pack;
        if (drone.airConnectionStatus.getConnectionType() == 1) {
            ChildThread.post(mRunnableRelay);
        }
    }

    private Runnable mRunnableRelay = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            MAVLinkService.sendRelayData(packRelay);
        }
    };

    public boolean isConnected() {

        return mIsBound;
    }

    // TODO 超时操作
    Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    mavlinkTimeout = mavlinkTimeout + 1;
                    if (mavlinkTimeout == 5) {
                        // 表示没有退出界面
                        if (CommonStatus.isExitAirConnection == false) {
                            listener.notifyAirDisconnected();
                            mIsBound = false;
                            if (NetUtil.isSunFlowerWifi(HubsanApplication.getApplication(), CommonUrl.hubsanHeadIp)) {
                                LogX.e("NetUtil.isSunFlowerWifi=true");
                                sendOutTime();
                            } else {
                                if (NetUtil.isRelayerWifi(HubsanApplication.getApplication())){
                                    LogX.e("NetUtil.isRelayerWifi=true");
                                    sendOutTime();
                                }
                            }
                            mavlinkTimeout = 0;
                            resetAirTimeout();
                            drone.airConnectionStatus.setAirConnection(false);
                            drone.airHeartBeat.airDisConnection();
                            drone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_RECONNECT);
                            setDefulValue();
                        }
                        mavlinkTimeout = 0;
                    }
                    // 表示退出界面
                    if (CommonStatus.isExitAirConnection == true) {
                        stopBindService();
                    }
                    break;
                case 4:
                    relaykResetTime = relaykResetTime + 1;
                    if (relaykResetTime >= 5) {
                        // 表示没有退出界面
                        listener.notifyRelayDisconnected();
                        if (NetUtil.isSunFlowerWifi(HubsanApplication.getApplication(), CommonUrl.hubsanRelayIp)) {
                            sendRelayOutTime();
                        }
                        resetRelayTimeout();
                    }
                    // 中继器

                    break;
            }
            super.handleMessage(msg);
        }
    };

    // mavlink连接超时
    public void sendOutTime() {
        LogX.e("mavlink连接超时");
        if (mService != null) {
            Message msgs = Message.obtain(null, MAVLinkService.MSG_RESTART_CONNECTION);
            try {
                mService.send(msgs);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // 中继器连接超时
    public void sendRelayOutTime() {
        LogX.e("中继器连接超时");
        if (mService != null) {
            Message msgs = Message.obtain(null,
                    MAVLinkService.MSG_RELAY_RESTART_CONNECTION);
            try {
                mService.send(msgs);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    private Handler relayHandler = new Handler();
    private Runnable relayRunnable = new Runnable() {
        @Override
        public void run() {

            Message message = new Message();
            message.what = 4;
            handler.sendMessage(message);
            relayHandler.postDelayed(relayRunnable, 1000);
        }
    };

    /**
     * 重置中继器超时处理
     */
    private void resetRelayTimeout() {
        relaykResetTime = 0;
        relayHandler.removeCallbacks(relayRunnable);
        relayHandler.postDelayed(relayRunnable, 1000);
    }

    private Handler airHandler = new Handler();
    private Runnable airRunnable = new Runnable() {
        @Override
        public void run() {
            LogX.e("飞机连接超时");
            Message message = new Message();
            message.what = 3;
            handler.sendMessage(message);
            airHandler.postDelayed(airRunnable, 1000);
        }
    };

    private void resetAirTimeout() {
        mavlinkTimeout = 0;
        airHandler.removeCallbacks(airRunnable);
        airHandler.postDelayed(airRunnable, 1000);
    }

    private void setDefulValue() {
        drone.airHeartBeat.isFirstConnection = false;
        drone.airHeartBeat.isFirstTimerSync = false;
    }
}
