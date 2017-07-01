package com.hubsan.swifts.drone.variables.mission.waypoints;

import android.content.Context;
import android.graphics.Bitmap;

import com.MAVLink.common.msg_mission_item;
import com.MAVLink.enums.MAV_FRAME;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.hubsan.swifts.drone.variables.mission.Mission;
import com.hubsan.swifts.drone.variables.mission.MissionItem;
import com.hubsan.swifts.helpers.units.Altitude;
import com.hubsan.swifts.helpers.units.Speed;
import com.hubsan.swifts.mapUtils.marks.GenericMarker;
import com.hubsan.swifts.mapUtils.marks.MarkerManager;
import com.hubsan.swifts.mapUtils.marks.helpers.MarkerWithText;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic Mission item with Spatial Coordinates
 * 通用的任务项与空间坐标
 *
 */
public abstract class SpatialCoordItem extends MissionItem implements
		MarkerManager.MarkerSource {

	protected abstract int getIconDrawable();
	protected abstract int getIconDrawableSelected();

	LatLng coordinate;
	Altitude altitude;
	Speed speed;


    public SpatialCoordItem(Mission mission, LatLng coord, Altitude altitude, Speed speed) {
		super(mission);
		this.coordinate = coord;
		this.altitude = altitude;
		this.speed = speed;
	//	offsetPosition(offset)
	}

	public SpatialCoordItem(MissionItem item) {
		super(item);
		if (item instanceof SpatialCoordItem) {
			coordinate = ((SpatialCoordItem) item).getCoordinate();
			altitude = ((SpatialCoordItem) item).getAltitude();
			speed = ((SpatialCoordItem) item).getSpeed();
		} else {
			coordinate = new LatLng(0, 0);
			altitude = new Altitude(30);//默认设置高度为30m
			speed = new Speed(5);
		}
		//offsetPosition(offset);
	}

	@Override
	public MarkerOptions build(Context context) {
		return GenericMarker.build(coordinate).icon(getIcon(context));
	}

	@Override
	public void update(Marker marker, Context context) {
		marker.setPosition(coordinate);
		marker.setIcon(getIcon(context));
	}

	@Override
	public List<MarkerManager.MarkerSource> getMarkers() throws Exception {
		ArrayList<MarkerManager.MarkerSource> marker = new ArrayList<MarkerManager.MarkerSource>();
		marker.add(this);
		return marker;
	}

	@Override
	public List<LatLng> getPath() throws Exception {
		ArrayList<LatLng> points = new ArrayList<LatLng>();
		points.add(coordinate);
		return points;
	}

	protected BitmapDescriptor getIcon(Context context) {
		int drawable;
		if (mission.selectionContains(this)) {
			drawable = getIconDrawableSelected();
		} else {
			drawable = getIconDrawable();
		}
		//地图上显示点
		Bitmap marker = MarkerWithText.getMarkerWithTextAndDetail(drawable, getIconText(),
						getIconDetail(), context);
		return BitmapDescriptorFactory.fromBitmap(marker);
	}

	private String getIconDetail() {
		try {
			if (mission.getAltitudeDiffFromPreviousItem(this).valueInMeters()==0) {
				return null;
			}else{
				return null; //altitude.toString();
			}
		} catch (Exception e) {
			return null;
		}
	}

	private String getIconText() {
		return Integer.toString(mission.getNumber(this));
	}

	public void setCoordinate(LatLng position) {
		coordinate = position;
		//offsetPosition(offset);
	}

	public LatLng getCoordinate() {
		return coordinate;
	}

	public Altitude getAltitude() {
		return altitude;
	}

	public void setAltitude(Altitude altitude) {
		this.altitude = altitude;
	}


	public Speed getSpeed() {
		return speed;
	}

	public void setSpeed(Speed speed) {
		this.speed = speed;
	}

	@Override
	public List<msg_mission_item> packMissionItem() {
		List<msg_mission_item> list = new ArrayList<msg_mission_item>();
		msg_mission_item mavMsg = new msg_mission_item();
		list.add(mavMsg);
		mavMsg.autocontinue = 1;
		mavMsg.target_component = 1;
		mavMsg.target_system = 1;
		mavMsg.frame = MAV_FRAME.MAV_FRAME_GLOBAL_RELATIVE_ALT;
		String lat = String.valueOf(getCoordinate().latitude);
		String lon = String.valueOf(getCoordinate().longitude);
		mavMsg.x = Float.parseFloat(lat);
		mavMsg.y = Float.parseFloat(lon);
		mavMsg.z = (float) getAltitude().valueInMeters();
		mavMsg.param2 = (float) getSpeed().valueInMeters();
		return list;
	}

	@Override
	public void unpackMAVMessage(msg_mission_item mavMsg) {
		LatLng coord = new LatLng(mavMsg.x,mavMsg.y);
		Altitude alt = new Altitude(mavMsg.z);
		Speed speeds = new Speed(mavMsg.param2);
		setCoordinate(coord);
		setAltitude(alt);
		setSpeed(speeds);
	}
    @Override
    public String toString() {
        return "SpatialCoordItem [coordinate=" + coordinate.latitude+ coordinate.longitude+ ", altitude=" + altitude + "]";
    }
	
}