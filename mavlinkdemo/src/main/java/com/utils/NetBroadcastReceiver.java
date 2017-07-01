
package com.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.hubsansdk.application.HubsanHandleMessageApp;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces;

/**
 * Description
 * NetBroadcastReceiver类继承BroadcastReceiver 并实现onReceive方法 在onReceive方法通知接口完成加载
 *
 * @author ShengKun.Cheng
 * @date 2015年11月11日
 * @see [class/class#method]
 * @since [product/model]
 */
public class NetBroadcastReceiver extends BroadcastReceiver {
    private HubsanHandleMessageApp application;
    private HubsanDrone drone;
    @Override
    public void onReceive(Context context, Intent intent) {
        application = (HubsanHandleMessageApp) HubsanHandleMessageApp.getApplication();
        this.drone = application.drone;
        boolean success = false;
        //获得网络连接服务
        ConnectivityManager connManager = (ConnectivityManager) HubsanHandleMessageApp.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取WIFI网络连接状态
        NetworkInfo.State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        // 判断是否正在使用WIFI网络
        if (NetworkInfo.State.CONNECTED == state) {
            success = true;
            WifiManager wifiManager = (WifiManager) HubsanHandleMessageApp.getApplication().getApplicationContext().getSystemService(HubsanHandleMessageApp.getApplication().WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            application.drone.netWorkUtil.wifiConnection= true;
//            LogX.e("网络连接状态:" + wifiInfo.getSSID());
           }else {
//            LogX.e("网络连接状态:" + "断开");
            application.drone.netWorkUtil.wifiConnection= false;
            }

        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (!wifiInfo.isConnected()){
//            LogX.e("wifi网络连接状态:断开");
            application.drone.netWorkUtil.wifiConnection= false;
            drone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_NETWORK_DISCONNECTION);
        }else {
            WifiManager wifi = (WifiManager) HubsanHandleMessageApp.getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            String ssid = info.getSSID();
//            LogX.e("wifi网络连接状态:可以使用");
            drone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_NETWORK_CONNECTION);
            application.drone.netWorkUtil.wifiConnection= true;
        }
    }

}