package com.hubsansdk.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.RemoteException;
import android.preference.PreferenceManager;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.hubsansdk.application.HubsanHandleMessageApp;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces;
import com.hubsansdk.service.MAVLinkSocketConnection.MavLinkSocketConnectionListener;
import com.hubsansdk.utils.LogX;

/**
 * http://developer.android.com/guide/components/bound-services.html#Messenger
 * Description
 * 此服务用于发送mavlink数据
 *
 * @author ShengKun.Cheng
 * @date 2015年10月20日
 * @see [class/class#method]
 * @since [product/model]
 */


public class MAVLinkService extends Service implements
        MavLinkSocketConnectionListener, MAVLinkRelayConnection.RelayConnectionListener, HubsanDroneInterfaces.OnDroneListener {
    public static final int MSG_REGISTER_CLIENT = 1;
    public static final int MSG_UNREGISTER_CLIENT = 2;
    public static final int MSG_SEND_DATA = 3;
    public static final int MSG_RESTART_CONNECTION = 4;
    public static final int MSG_RELAY_RESTART_CONNECTION = 5;
    private static MAVLinkSocketConnection mavConnection;
    private static MAVLinkRelayConnection mavLinkRelayConnection;
    private Messenger msgCenter = null;
    private IncomingHandler incomingHandler;
    private Messenger mMessenger;
    private boolean couldNotOpenConnection = false;
    private WakeLock wakeLock;
    private HubsanDrone drone;
    private HubsanHandleMessageApp application;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onRelayReceiveMessage(MAVLinkMessage msg) {
        try {
            if (msgCenter != null && msg != null) {
                Message msgs = Message.obtain(null,
                        MAVLinkClient.MSG_RELAY_RECEIVED_DATA);
                Bundle data = new Bundle();
                data.putSerializable("relaymsg", msg);
                msgs.setData(data);
                msgCenter.send(msgs);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDroneEvent(HubsanDroneInterfaces.DroneEventsType event, HubsanDrone drone) {
        switch (event) {
            case HUBSAN_CONNETED:
                handler.postDelayed(mRunnable, 200);
                break;
            case HUBSAN_RECONNECT:
                break;
            case HUBSAN_DISCONNECTED:
                //resetMAVConnection();
                handler.removeCallbacks(mRunnable);
                break;

        }
    }

    @SuppressLint("HandlerLeak")
    class IncomingHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REGISTER_CLIENT:
                    msgCenter = msg.replyTo;
                    if (couldNotOpenConnection) {
                        selfDestryService();
                    }
                    break;
                case MSG_UNREGISTER_CLIENT:
                    msgCenter = null;
                    break;
                case MSG_SEND_DATA:
                    Bundle b = msg.getData();
                    MAVLinkPacket packet = (MAVLinkPacket) b.getSerializable("msg");
                    if (mavConnection != null) {
                        mavConnection.sendMavPacket(packet);
                    }
                case MSG_RESTART_CONNECTION:
                    //mavlink连接超时
                    LogX.e("mavlink连接超时");
                    mavConnection.timeOut = true;
                    resetMAVConnection();
                    handler.removeCallbacks(mRunnable);
                    break;
                case MSG_RELAY_RESTART_CONNECTION:
                    //中继器连接超时
                    connectMAVconnection();
                    relayHandler.removeCallbacks(relayRunnable);
                    mavLinkRelayConnection.timeOut = true;
                    break;
                default:
                    break;
            }
        }
    }

    private Handler handler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            drone.mavLinkSendMessage.sendHeartbeat();
            handler.postDelayed(this, 200);
        }
    };

    private Handler relayHandler = new Handler();
    private Runnable relayRunnable = new Runnable() {
        @Override
        public void run() {
            drone.mavLinkSendMessage.sendRelayHeartbeat();
            relayHandler.postDelayed(relayRunnable, 1000);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        application = (HubsanHandleMessageApp) getApplication();
        this.drone = application.drone;
        if (incomingHandler == null) {
            incomingHandler = new IncomingHandler();
        }
        if (mMessenger == null) {
            mMessenger = new Messenger(incomingHandler);
        }
        drone.events.addDroneListener(this);
        aquireWakelock();
        connectMAVconnection();
        resetMAVConnection();
        regist();
    }

    /**
     * 发送心跳数据
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mMessenger.getBinder();
    }

    /**
     * 发送mavlink的消息
     *
     * @param packet
     */
    public static void setMAVLinkData(MAVLinkPacket packet) {
        if (mavConnection != null) {
            mavConnection.sendMavPacket(packet);
        }
    }


    /**
     * 发送中继器消息
     *
     * @param packet
     * @return
     */
    public static void sendRelayData(MAVLinkPacket packet) {

        if (mavLinkRelayConnection != null) {
            mavLinkRelayConnection.sendMavPacket(packet);
        }
    }

    @Override
    public void onReceiveMessage(MAVLinkMessage m) {
        try {
            if (msgCenter != null && m != null) {
                Message msg = Message.obtain(null, MAVLinkClient.MSG_RECEIVED_DATA);
                Bundle data = new Bundle();
                data.putSerializable("msg", m);
                msg.setData(data);
                msgCenter.send(msg);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnecct() {
//        LogX.e("reconnectMAVconnection :" + mavConnection.isConnected());
        if (mavConnection.isConnected()) {
            drone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_CONNETED);
        }
    }


    @Override
    public void onDisconnect() {
        couldNotOpenConnection = true;

        if (!mavConnection.isConnected()) {
//            LogX.e("onDisconnect :" + mavConnection.isConnected());
//            drone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_DISCONNECTED);
            handler.removeCallbacks(mRunnable);
            sendAirDisconnection();
        }
        selfDestryService();
    }

    @Override
    public void onRelayConnecct() {
        if (mavLinkRelayConnection.isConnected()) {
            relayHandler.removeCallbacks(relayRunnable);
            relayHandler.postDelayed(relayRunnable, 1000);
        }
    }

    @Override
    public void onRelayDisconnect() {
        if (!mavLinkRelayConnection.isConnected()) {
            relayHandler.removeCallbacks(relayRunnable);
        }
    }


    private void sendAirDisconnection(){
        try {
            if (msgCenter != null) {
                Message msg = Message.obtain(null, MAVLinkClient.MSG_DISCONNECTION);
                Bundle data = new Bundle();
                data.putSerializable("disConnection", "0");
                msg.setData(data);
                msgCenter.send(msg);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     */
    private void selfDestryService() {
        try {
            if (msgCenter != null) {
                Message msg = Message.obtain(null, MAVLinkClient.MSG_SELF_DESTRY_SERVICE);
                msgCenter.send(msg);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化中继器和mavlink的线程
     */
    public void connectMAVconnection() {
        mavLinkRelayConnection = new MAVLinkRelayConnection(this);
        mavLinkRelayConnection.start();
    }


    public void resetMAVConnection() {
        mavConnection = new MAVLinkSocketConnection(this);
        mavConnection.start();
    }


    @Override
    public void onDestroy() {
        if (incomingHandler != null) {
            incomingHandler.removeMessages(MSG_SEND_DATA);
            incomingHandler.removeMessages(MSG_REGISTER_CLIENT);
            incomingHandler.removeMessages(MSG_UNREGISTER_CLIENT);
            incomingHandler.removeMessages(MSG_RESTART_CONNECTION);
            incomingHandler = null;
        }
        if (mMessenger != null) {
            mMessenger = null;
        }
        handler.removeCallbacks(mRunnable);
        relayHandler.removeCallbacks(relayRunnable);
        disconnectMAVConnection();
        releaseWakelock();
        unregisterReceiver(connectionReceiver);
        drone.events.removeDroneListener(this);
        super.onDestroy();
    }

    /**
     * 退出时,停止中继器个mavlink线程
     */
    private void disconnectMAVConnection() {
        if (mavConnection != null) {
            mavConnection = null;
        }
        if (mavLinkRelayConnection != null) {
            mavLinkRelayConnection = null;
        }
    }

    @SuppressWarnings("deprecation")
    protected void aquireWakelock() {
        if (wakeLock == null) {
            PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
            if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("pref_keep_screen_bright", false)) {
                wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "CPU");
            } else {
                wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "CPU");
            }
            wakeLock.acquire();
        }
    }

    protected void releaseWakelock() {
        if (wakeLock != null) {
            wakeLock.release();
            wakeLock = null;
        }
    }

    private Handler broadHandler = new Handler();
    public BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectMgr = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (!wifiNetInfo.isConnected()) {
                // unconnect network
                mavConnection.closeConnection();
                mavLinkRelayConnection.closeConnection();
//                LogX.e("unconnect network");
            } else {
                // connect network
//                LogX.e("connect network");
//                if (mavConnection.isConnected()) {
////                    LogX.e("reconnectMAVconnection :" + mavConnection.isConnected());
//                } else {
//                    broadHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (!mavConnection.isConnected()) {
//                                resetMAVConnection();
//                            }
//                            if (!mavLinkRelayConnection.isConnected()) {
//                                connectMAVconnection();
//                            }
//                        }
//                    }, 1000);
//
//                }


            }
        }
    };

    public void regist() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectionReceiver, intentFilter);
    }


}
