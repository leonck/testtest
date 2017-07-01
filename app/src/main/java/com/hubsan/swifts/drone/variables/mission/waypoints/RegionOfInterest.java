//package com.hubsan.swifts.drone.variables.mission.waypoints;
//
//import com.MAVLink.common.msg_mission_item;
//import com.amap.api.maps.model.LatLng;
//import com.csk.hbsdrone.R;
//import com.csk.hbsdrone.drone.variables.mission.MissionItem;
//import com.csk.hbsdrone.fragments.markers.MarkerManager.MarkerSource;
//import com.csk.hbsdrone.fragments.mission.MissionDetailFragment;
//import com.csk.hbsdrone.fragments.mission.MissionRegionOfInterestFragment;
//
//import java.util.List;
//
//public class RegionOfInterest extends SpatialCoordItem implements MarkerSource{
//
//	public RegionOfInterest(MissionItem item) {
//		super(item);
//	}
//
//	@Override
//	public List<LatLng> getPath() throws Exception {
//		throw new Exception();
//	}
//
//	@Override
//	public MissionDetailFragment getDetailFragment() {
//		MissionDetailFragment fragment = new MissionRegionOfInterestFragment();
//		fragment.setItem(this);
//		return fragment;
//	}
//
//	@Override
//	public List<msg_mission_item> packMissionItem() {
//		// TODO Auto-generated method stub
//		return super.packMissionItem();
//	}
//
//	@Override
//	public void unpackMAVMessage(msg_mission_item mavMsg) {
//		// TODO Auto-generated method stub
//		super.unpackMAVMessage(mavMsg);
//	}
//
//	@Override
//	protected int getIconDrawable() {
//		return R.drawable.ic_wp_map;
////		return R.drawable.map_bg_transparent_normal;
//	}
//
//	@Override
//	protected int getIconDrawableSelected() {
//		return R.drawable.ic_wp_map_selected;
////		return R.drawable.map_bg_transparent_normal;
//	}
//
//	/*
//	private static String getRoiDetail(GenericWaypoint wp, Context context) {
//		if (wp.getParam1() == MAV_ROI.MAV_ROI_WPNEXT)
//			return context.getString(R.string.next);
//		else if (wp.getParam1() == MAV_ROI.MAV_ROI_TARGET)
//			return String.format(Locale.US,"wp#%.0f", wp.getParam2());
//		else if (wp.getParam1() == MAV_ROI.MAV_ROI_TARGET)
//			return String.format(Locale.US,"tg#%.0f", wp.getParam2());
//		else
//			return "";
//	}
//	*/
//}
