package com.hubsansdk.utils;

/**
 * 基本的参数
 * 
 * @author shengkun.cheng
 *
 */
public class Constants {

	public final static short HUBSAN_IDLE_MODE = 0; // 0x00
	public final static short HUBSAN_MANUAL_MODE = 1; // 手动模式 0x01
	public final static short HUBSAN_ALTITUDE_HOLD = 2; // 定高模式 0x02
	public final static short HUBSAN_GPS_HOLD = 4; // 定点模式0x04
	public final static short HUBSAN_RETURN_HOME = 8; // 返航0x08
	public final static short HUBSAN_FOLLOW_MODE = 16; // 跟飞模式0x10
	public final static short HUBSAN_WAYPOINT_FLY = 32; // 航点模式0x20
	public final static short HUBSAN_FREE_HEAD = 64; // 无头模式0x40
	public final static short HUBSAN_SURROUND_FLY = 128; // 环绕飞行模式0x80
}
