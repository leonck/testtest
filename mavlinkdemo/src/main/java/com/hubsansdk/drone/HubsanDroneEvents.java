package com.hubsansdk.drone;

import com.hubsansdk.drone.HubsanDroneInterfaces.DroneEventsType;
import com.hubsansdk.drone.HubsanDroneInterfaces.OnDroneListener;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * Description 
 * 			无人驾驶飞机事件
 * @author ShengKun.Cheng
 * @date  2015年7月28日
 * @version 
 * @see [class/class#method]
 * @since [product/model]
 */
public class HubsanDroneEvents extends HubsanDroneVariable {

	public HubsanDroneEvents(HubsanDrone myDrone) {
		super(myDrone);
	}

	private List<OnDroneListener> droneListeners = new ArrayList<OnDroneListener>();

	public void addDroneListener(OnDroneListener listener) {
		if (listener != null & !droneListeners.contains(listener))
			droneListeners.add(listener);
	}

	public void removeDroneListener(OnDroneListener listener) {
		if (listener != null && droneListeners.contains(listener))
			droneListeners.remove(listener);
	}

	public void notifyDroneEvent(DroneEventsType event) {
		try {
			if (droneListeners.size() > 0) {
				for (OnDroneListener listener : droneListeners) {
					listener.onDroneEvent(event, myDrone);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
