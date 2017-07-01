package com.hubsansdk.utils;

import java.math.BigDecimal;

/**
 * 参数操作
 * 
 * @author shengkun.cheng
 *
 */
public class ParameterUtil {


    private static long lastClickTime;

    /**
	 * 两个float相加
	 * @param f1 
	 * @param f2
	 * @return
	 */
	public float addFloat(float f1, float f2) {
		BigDecimal b1 = BigDecimal.valueOf(f1);
		BigDecimal b2 = BigDecimal.valueOf(f2);
		return b1.add(b2).floatValue();
	}
	
	/**
     * 通过byte数组取得float
     *
     * @param b
     * @return
     */
    public static float getFloat(byte[] b) {
        //低位在前,高位在后.  网络字节序
        int l;
        l = (int) (b[0] & 0xFF);
        l += (int) (b[1] & 0xFF) << 8;
        l += (int) (b[2] & 0xFF) << 16;
        l += (int) (b[3] & 0xFF) << 24;
        return Float.intBitsToFloat(l);
    }
    
    /*十进制转换为十六进制,一位的补0*/
    public static String textSizeAddZero(int str) {

        String value = intToStr(str);
        if (value.length() == 1) {
            return "0" + value;
        }
        return value;
    }
    /**
     * 十进制转化为十六进制(字符串)
     *
     * @param value
     * @return
     */
    public static String intToStr(int value) {

        return Integer.toHexString(value);
    }

    public synchronized static boolean isFastClick(long times) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < times) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
