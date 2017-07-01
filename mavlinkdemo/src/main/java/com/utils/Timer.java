
package com.utils;

/**
 * 
 * Description 
 *      时间转换
 * @author ShengKun.Cheng
 * @date  2015年10月14日
 * @version 
 * @see [class/class#method]
 * @since [product/model]
 */
public class Timer {
    
    /**
     * 
     * Description 传递秒数，计算成时分秒
     * @param time  需要传递的参数
     * @return
     * @see [class/class#field/class#method]
     */
    public static String secToTime(double time) {
        int s = 3600;
        double times = (double)s;
        int mTime  = (int)(times*time);
        
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = mTime / 60;
            if (minute < 60) {
                second = mTime % 60;
                timeStr = "00"+ ":"+unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = mTime - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }
    
    private static String unitFormat(int i) {
    
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
    
    /**
     * 传递秒数，返回时分秒00:00:00
     * Description 
     * @param time 秒数
     * @return
     * @see [class/class#field/class#method]
     */
    public static String secToTimeInt(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }
    
}
