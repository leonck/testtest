package com.hubsan.swifts.drone.variables.mission.waypoints;

import com.MAVLink.common.msg_mission_item;
import com.MAVLink.enums.MAV_CMD;
import com.amap.api.maps.model.LatLng;
import com.hubsan.swifts.R;
import com.hubsan.swifts.drone.variables.mission.Mission;
import com.hubsan.swifts.drone.variables.mission.MissionItem;
import com.hubsan.swifts.helpers.units.Altitude;
import com.hubsan.swifts.helpers.units.Speed;

import java.util.List;

/**
 *
 * Description
 *      飞机航点参数设置
 * @author ShengKun.Cheng
 * @date  2015年12月24日
 * @version
 * @see [class/class#method]
 * @since [product/model]
 */
public class Waypoint extends SpatialCoordItem {
	private double delay;
	private double acceptanceRadius;
	private double yawAngle;
	private double orbitalRadius;
	private boolean orbitCCW;

	public Waypoint(MissionItem item) {
		super(item);
	}

	public Waypoint(Mission mission, LatLng point, Altitude defaultAlt, Speed speed) {
		super(mission, point, defaultAlt,speed);
	}

	public Waypoint(msg_mission_item msg, Mission mission) {
		super(mission, null, null,null);
		unpackMAVMessage(msg);
	}

//	@Override
//	public MissionDetailFragment getDetailFragment() {
//		MissionDetailFragment fragment = new MissionWaypointFragment();//设置每个航点高度，悬停时间
//		fragment.setItem(this);
//		return fragment;
//	}

	@Override
	public List<msg_mission_item> packMissionItem() {
		List<msg_mission_item> list = super.packMissionItem();
		msg_mission_item mavMsg = list.get(0);
		mavMsg.command = MAV_CMD.MAV_CMD_NAV_WAYPOINT;
		mavMsg.param1 = (float) getDelay();
//		mavMsg.param2 = (float) getAcceptanceRadius();
		mavMsg.param3 = (float) (isOrbitCCW()?getOrbitalRadius()*-1.0:getOrbitalRadius());
		mavMsg.param4 = (float) getYawAngle();
		return list;
	}

	@Override
	public void unpackMAVMessage(msg_mission_item mavMsg) {
		super.unpackMAVMessage(mavMsg);
		setDelay(mavMsg.param1);
//		setAcceptanceRadius(mavMsg.param2);
		setOrbitCCW(mavMsg.param3<0);
		setOrbitalRadius(Math.abs(mavMsg.param3));
		setYawAngle(mavMsg.param4);
	}

	public double getDelay() {
		return delay;
	}

	public void setDelay(double delay) {
		this.delay = delay;
	}

	public double getAcceptanceRadius() {
		return acceptanceRadius;
	}

	public void setAcceptanceRadius(double acceptanceRadius) {
		this.acceptanceRadius = acceptanceRadius;
	}

	public double getYawAngle() {
		return yawAngle;
	}

	public void setYawAngle(double yawAngle) {
		this.yawAngle = yawAngle;
	}

	public double getOrbitalRadius() {
		return orbitalRadius;
	}

	public void setOrbitalRadius(double orbitalRadius) {
		this.orbitalRadius = orbitalRadius;
	}

	public boolean isOrbitCCW() {
		return orbitCCW;
	}

	public void setOrbitCCW(boolean orbitCCW) {
		this.orbitCCW = orbitCCW;
	}

	@Override
	protected int getIconDrawable() {
		return R.drawable.ic_wp_map;
//		return R.drawable.map_bg_transparent_normal;
	}

	@Override
	protected int getIconDrawableSelected() {
		return R.drawable.ic_wp_map_selected;
//		return R.drawable.map_bg_transparent_normal;
	}

    @Override
    public String toString() {
        return super.toString()+"Waypoint [delay=" + delay + ", acceptanceRadius=" + acceptanceRadius
                + ", yawAngle=" + yawAngle + ", orbitalRadius=" + orbitalRadius + ", orbitCCW="
                + orbitCCW + "]";
    }

}