package com.hubsansdk.drone.bean;

import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces.DroneEventsType;
import com.hubsansdk.drone.HubsanDroneVariable;

/**
 * 公用的通知
 * @author shengkun.cheng
 *
 */
public class CommonNotify extends HubsanDroneVariable {

	public CommonNotify(HubsanDrone myDrone) {
		super(myDrone);
	}

	//判断是否取消返航模式
	private boolean isCancelTurnBack;


	public boolean isCancelTurnBack() {
		return isCancelTurnBack;
	}


	public void setCancelTurnBack(boolean isCancelTurnBack) {
		this.isCancelTurnBack = isCancelTurnBack;
	}

	public void udpCancelTurnback(){
		//发送通知,发送取消返航
		myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_COLSE_TURNBACK);
	}

}
