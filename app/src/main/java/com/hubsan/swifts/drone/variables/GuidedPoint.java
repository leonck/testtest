//package com.hubsan.swifts.drone.variables;
//
//import android.content.Context;
//
//import com.amap.api.maps.model.LatLng;
//import com.amap.api.maps.model.Marker;
//import com.amap.api.maps.model.MarkerOptions;
//import com.csk.hbsdrone.HubsanDroneApplication;
//import com.csk.hbsdrone.fragments.helpers.MapPath.PathSource;
//import com.csk.hbsdrone.fragments.markers.GuidedMarker;
//import com.csk.hbsdrone.fragments.markers.MarkerManager.MarkerSource;
//import com.csk.hbsdrone.helpers.units.Altitude;
//import com.hubsansdk.application.HubsanApplication;
//import com.hubsansdk.drone.HubsanDrone;
//import com.hubsansdk.drone.HubsanDroneInterfaces;
//import com.hubsansdk.drone.HubsanDroneVariable;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GuidedPoint extends HubsanDroneVariable implements MarkerSource,
//		PathSource {
//
//	private GuidedStates state = GuidedStates.UNINITIALIZED;
//	private LatLng coord = new LatLng(0, 0);
//	private HubsanDroneApplication app;
//
//	private enum GuidedStates {
//		UNINITIALIZED, IDLE, ACTIVE
//	};
//
//	public GuidedPoint(HubsanDrone myDrone) {
//		super(myDrone);
//
//
//	}
//
//
//	public void newGuidedCoord(LatLng coord) {
//		changeCoord(coord);
//	}
//
//	public void changeGuidedAltitude(double altChange) {
//		changeAlt(altChange);
//	}
//
//	public void forcedGuidedCoordinate(LatLng coord) {
//		initialize();
//		changeCoord(coord);
//	}
//
//	private void initialize() {
//		app = (HubsanDroneApplication) HubsanApplication.getApplication();
//		if (state == GuidedStates.UNINITIALIZED) {
//			coord = app.GPS.getPosition();
//			app.altitude.set(getDroneAltConstained());
//			state = GuidedStates.IDLE;
//			myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_GUIDEDPOINT);
//		}
//	}
//
//	private void disable() {
//		state = GuidedStates.UNINITIALIZED;
////		myDrone.events.notifyDroneEvent(DroneEventsType.GUIDEDPOINT);
//	}
//
//	private void changeAlt(double altChange) {
//		switch (state) {
//		case UNINITIALIZED:
//			break;
//		case IDLE:
//			state = GuidedStates.ACTIVE;
//			changeAlt(altChange);
//			break;
//		case ACTIVE:
//			double alt = Math.floor(app.altitude.valueInMeters());
//			alt = Math.max(alt, 2.0);
//
//			if (altChange < -1 && alt <= 10)
//				altChange = -1;
//
//			if ((alt + altChange) > 1.0) {
//				app.altitude.set(alt + altChange);
//			}
//			sendGuidedPoint();
//			break;
//		}
//	}
//
//	private void changeCoord(LatLng coord) {
//		sendGuidedPoint();
//
//
//		switch (state) {
//		case UNINITIALIZED:
//			break;
//		case IDLE:
//			state = GuidedStates.ACTIVE;
//			changeCoord(coord);
//			break;
//		case ACTIVE:
//			this.coord = coord;
//			sendGuidedPoint();
//			break;
//		}
//
//	}
//
//	private void sendGuidedPoint() {
//		if (state == GuidedStates.ACTIVE) {
//			myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_GUIDEDPOINT);
////			MavLinkModes.setGuidedMode(myDrone, coord.latitude,coord.longitude, altitude.valueInMeters());
//		}
//	}
//
//	private double getDroneAltConstained() {
//		double alt = Math.floor(myDrone.airBaseParameters.getAltitude());
//		return Math.max(alt, 2.0);
//	}
//
//	public LatLng getCoord() {
//		return coord;
//	}
//
//	public Altitude getAltitude() {
//		return this.app.altitude;
//	}
//
//	public boolean isActive() {
//		return (state == GuidedStates.ACTIVE);
//	}
//
//	public boolean isInitialized() {
//		return !(state == GuidedStates.UNINITIALIZED);
//	}
//
//	@Override
//	public MarkerOptions build(Context context) {
//		return GuidedMarker.build(this, app.altitude, context);
//	}
//
//	@Override
//	public void update(Marker markerFromGcp, Context context) {
//		GuidedMarker.update(markerFromGcp, this, app.altitude, context);
//	}
//    public static int getLineNumber(Exception e){
//        StackTraceElement[] trace =e.getStackTrace();
//        if(trace==null||trace.length==0) return -1; //
//        return trace[0].getLineNumber();
//        }
//	@Override
//	public List<LatLng> getPathPoints() {
//		List<LatLng> path = new ArrayList<LatLng>();
//		if (isActive()) {
//			path.add(app.GPS.getPosition());
//			path.add(coord);
//		}
//		return path;
//	}
//}
