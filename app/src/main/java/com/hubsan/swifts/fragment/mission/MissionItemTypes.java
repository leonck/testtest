package com.hubsan.swifts.fragment.mission;


import com.hubsan.swifts.drone.variables.mission.MissionItem;
import com.hubsan.swifts.drone.variables.mission.waypoints.Waypoint;

import java.security.InvalidParameterException;

public enum MissionItemTypes {
	WAYPOINT("Waypoint"), TAKEOFF("Takeoff"),
	LAND("Land"),
	SURVEY("Survey");

	private final String name;

	private MissionItemTypes(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public MissionItem getNewItem(MissionItem item) throws InvalidItemException {
		switch (this) {
		case WAYPOINT:
			return new Waypoint(item);
		case SURVEY:
			throw new InvalidItemException();
		}
		throw new InvalidParameterException();
	}

	class InvalidItemException extends Exception {
		private static final long serialVersionUID = 1L;

	}
}