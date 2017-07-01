package com.utils;

import android.os.Handler;
import android.os.HandlerThread;

/**
 *
 * Description
 *  子线程工具类
 * @author ShengKun.Cheng
 * @date  2016年5月3日
 * @version
 * @see [class/class#method]
 * @since [product/model]
 */
public class ChildThread {

    private static Handler mMyhandler;
    static {
        HandlerThread handlerThread = new HandlerThread("ThreadUtile");
        handlerThread.start(); // 创建HandlerThread后一定要记得start()
        // 子线程
        mMyhandler = new Handler(handlerThread.getLooper());
    }

    /**
     * 直接调用，没有延时操作
     * @param runnable
     */
    public static void post(Runnable runnable){
        if (mMyhandler !=null) {
            mMyhandler.post(runnable);
        }
    }

    /**
     * 调用，有延时操作
     * @param runnable
     * @param time
     */
    public static void post(Runnable runnable, long time){
        if (mMyhandler !=null) {
            mMyhandler.postDelayed(runnable, time);
        }
    }

    /**
     * 销毁线程
     */
    public static void destoeyThread() {
        if (mMyhandler !=null) {
            mMyhandler.getLooper().quit();
            mMyhandler = null;
        }
    }
}
