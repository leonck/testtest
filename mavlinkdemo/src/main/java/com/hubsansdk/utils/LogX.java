package com.hubsansdk.utils;

import android.util.Log;

public class LogX {
	public static boolean enabled = true;
	public static String TAG = "csk";
	static String className;
	static String methodName;
	static int lineNumber;

	private LogX() {
	}

	public static void doLog(String message) {
		Log.e(TAG, message);
	}

	public static boolean isDebuggable() {
		return enabled;
	}

	public static void setDebuggable(boolean mEnabled){
		 enabled = mEnabled;
	}

	private static String getMethodNames(StackTraceElement[] sElements, String log) {
		className = sElements[1].getFileName();
		methodName = sElements[1].getMethodName();
		lineNumber = sElements[1].getLineNumber();
		StringBuffer buffer = new StringBuffer();
		buffer.append(methodName);
		buffer.append("(").append(className).append(":").append(lineNumber).append(")");
		buffer.append(log);
		return buffer.toString();
	}

	public static void e(String message) {
		if(isDebuggable()) {
			Log.e(TAG, getMethodNames((new Throwable()).getStackTrace(), message));
		}
	}

	public static void i(String message) {
		if(isDebuggable()) {
			Log.i(TAG, getMethodNames((new Throwable()).getStackTrace(), message) + "\tmessage:" + message);
		}
	}

	public static void d(String message) {
		if(isDebuggable()) {
			Log.d(TAG, getMethodNames((new Throwable()).getStackTrace(), message));
		}
	}

	public static void v(String message) {
		if(isDebuggable()) {
			Log.v(TAG, getMethodNames((new Throwable()).getStackTrace(), message));
		}
	}

	public static void w(String message) {
		if(isDebuggable()) {
			Log.w(TAG, getMethodNames((new Throwable()).getStackTrace(), message));
		}
	}

	public static void wtf(String message) {
		if(isDebuggable()) {
			Log.wtf(TAG, getMethodNames((new Throwable()).getStackTrace(), message));
		}
	}
}
