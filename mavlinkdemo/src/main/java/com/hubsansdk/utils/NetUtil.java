
package com.hubsansdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.hubsansdk.drone.bean.CommonUrl;

import static android.content.Context.WIFI_SERVICE;

/**
 * Description
 * 判断网络的工具类
 *
 * @author ShengKun.Cheng
 * @date 2015年11月11日
 * @see [class/class#method]
 * @since [product/model]
 */
public class NetUtil {

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 网关地址转换
     * @param ip
     * @return
     */
    public static String long2ip(long ip) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf((int) (ip & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 8) & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 16) & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 24) & 0xff)));
        return sb.toString();
    }

    /**
     * 判断wifi是否连接
     *
     * @param mContext
     * @return
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为自己的模块
     */
    public static boolean isSunFlowerWifi(Context context, String IpAddress) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        long serverAddress = dhcpInfo.serverAddress;
        // 获得主机地址
        String ip = long2ip(serverAddress);
        if (ip.equals(IpAddress)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是不是中继器的IP
     * @param context
     * @return
     */
    public static boolean isRelayerWifi(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        long serverAddress = dhcpInfo.serverAddress;
        // 获得主机地址
        String ip = long2ip(serverAddress);
        if (ip.equals(CommonUrl.hubsanRelayIp)) {
            return true;
        }
        return false;
    }

}