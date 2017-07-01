
package com.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.utils.LogX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * Description 
 *          判断当前网络是否可用
 * @author ShengKun.Cheng
 * @date  2015年9月16日
 * @version 
 * @see [class/class#method]
 * @since [product/model]
 */
public class NetworkDetector {
    
    public static boolean detect(Context context) {
        ConnectivityManager manager = (ConnectivityManager) HubsanApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }
        return true;
    }

    /**
     * 检测网络是否连接
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        // 得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) HubsanApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        // 去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            return manager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    /**
     * 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     * @return
     */

    public static final boolean ping() {

        String result = null;
        try {
            String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            LogX.e("------ping-----result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            LogX.e("----result--result = " + result);
        }
        return false;
    }
}
