package com.hubsan.swifts.bean;

/**
 * 功能按钮状态
 * @author shengkun.cheng
 *
 */
public class CommonStatus {

	//返航按钮的状态
	private boolean hubsanTurnBack;
	//判断当前是否退出退出操作界面
	public static boolean isExitAirConnection;
	private boolean closeSettingDialog = false;
	//是不是航点历史记录，历史记录在地图以第一个点为中心显示在中间
	private boolean waypointHistory = false;
	

	public boolean isHubsanTurnBack() {
		return hubsanTurnBack;
	}

	public void setHubsanTurnBack(boolean hubsanTurnBack) {
		this.hubsanTurnBack = hubsanTurnBack;
	}

	public boolean isCloseSettingDialog() {
		return closeSettingDialog;
	}

	public void setCloseSettingDialog(boolean closeSettingDialog) {
		this.closeSettingDialog = closeSettingDialog;
	}

	public boolean isWaypointHistory() {
		return waypointHistory;
	}

	public void setWaypointHistory(boolean waypointHistory) {
		this.waypointHistory = waypointHistory;
	}
}
