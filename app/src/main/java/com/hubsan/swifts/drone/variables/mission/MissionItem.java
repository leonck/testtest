package com.hubsan.swifts.drone.variables.mission;

import com.MAVLink.common.msg_mission_item;
import com.amap.api.maps.model.LatLng;
import com.hubsan.swifts.mapUtils.marks.MarkerManager;

import java.util.List;

public abstract class MissionItem implements Comparable<MissionItem> {
	protected Mission mission;
	
	public MissionItem(Mission mission) {
		this.mission = mission;
	}

	public MissionItem(MissionItem item) {
		this(item.mission);
	}

	/**
	 * Gets a flight path for this item
	 * @return the path as a list
	 * @throws Exception if path not available
	 */
	public abstract List<LatLng> getPath() throws Exception;

	/**
	 * Gets all markers for this item
	 * @return list of markers
	 * @throws Exception if this item doesn't have markers
	 */
	public abstract List<MarkerManager.MarkerSource> getMarkers() throws Exception;

	/**
	 * Return a new detail Fragment for this MissionItem
	 * @return
	 */
//	public abstract MissionDetailFragment getDetailFragment();

	/**
	 * Return a new list (one or more) of MAVLinkMessage msg_mission_item that
	 * represent this MissionItem
	 * 
	 * @return
	 */
	public abstract List<msg_mission_item> packMissionItem();
	
	/**
	 * Gets data from MAVLinkMessage msg_mission_item for this MissionItem
	 * @return
	 */
	public abstract void unpackMAVMessage(msg_mission_item mavMsg);
	
	
	public Mission getMission(){
		return mission;
	}
	
	@Override
	public int compareTo(MissionItem another) {
		return mission.getNumber(this) - mission.getNumber(another);
	}
	
}