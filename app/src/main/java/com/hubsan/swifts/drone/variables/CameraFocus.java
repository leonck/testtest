package com.hubsan.swifts.drone.variables;

import com.amap.api.maps.model.LatLng;
import com.hubsan.swifts.SwiftsApplication;
import com.hubsan.swifts.helpers.units.Altitude;
import com.hubsan.swifts.mapUtils.marks.FocusMarker;
import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces.DroneEventsType;
import com.hubsansdk.drone.HubsanDroneVariable;
import com.hubsansdk.utils.LogX;
import com.utils.Utils;

/**
 *
 * Description 照相机的焦点
 *
 * @author ShengKun.Cheng
 * @date 2015年7月28日
 * @version
 * @see [class/class#method]
 * @since [product/model]
 */
@SuppressWarnings("incomplete-switch")
public class CameraFocus extends HubsanDroneVariable {

	private static FocusStates state = FocusStates.UNINITIALIZED;
	private LatLng coord = new LatLng(0, 0);
	private Altitude altitude = new Altitude(0.0);
	private int sendflag = 0;
	public final static int CF_SEND_READY = 0;
	public final static int CF_SEND_OK = 22;
	public final static int CF_SEND_FAIL = 23;
	private FocusMarker maker;
	private SwiftsApplication application;

	public FocusMarker getMaker() {
		return maker;
	}

	public void setMaker(FocusMarker maker) {
		this.maker = maker;
	}

	private enum FocusStates {
		UNINITIALIZED, IDLE, COM_ACTIVE, TIMEOUT
	};
	public String getState() {
		return state.toString();
	}

	public int getSendflag() {
		return sendflag;
	}


	public CameraFocus(HubsanDrone myDrone) {
		super(myDrone);

	}


	public void newCameraFocus(LatLng coord) {
		changeCoord(coord);
	}

	public void changeCameraFocusAltitude(double altChange) {
		changeAlt(altChange);
	}

	public void forcedCameraFocusCoordinate() {
		// if(coord)
		initialize();
		changeCoord(coord);

	}

	public void forcedCameraFocusCoordinate(LatLng coord) {
		application = (SwiftsApplication) HubsanApplication.getApplication();
		initialize();
		changeCoord(coord);

	}

	public void changeCoord() {
		if (this.isInitialized()) {
			changeCoord(this.coord);
		}
	}


	public void initialize(LatLng coord) {

		if (state == FocusStates.UNINITIALIZED) {
			this.coord = coord;
			altitude.set(getDroneAltConstained());
			state = FocusStates.IDLE;
			myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_CAMERA_FOCUS);
		} else {
			this.coord = coord;
			state = FocusStates.IDLE;
			myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_CAMERA_FOCUS);
		}
	}

	private void initialize() {
		if (state == FocusStates.UNINITIALIZED) {
//			coord = application.GPS.getPosition();
			altitude.set(getDroneAltConstained());
			state = FocusStates.IDLE;
			myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_CAMERA_FOCUS);
		}
	}

	public void disable() {
		state = FocusStates.UNINITIALIZED;
		myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_CAMERA_FOCUS);
	}



	public boolean isTimeout() {
		return state == FocusStates.TIMEOUT;
	}

	public boolean isIdle() {
		return state == FocusStates.IDLE;
	}

	public void idle() {
		state = FocusStates.IDLE;
	}


	private void changeAlt(double altChange) {
		switch (state) {
		case UNINITIALIZED:
			break;
		case IDLE:
			state = FocusStates.COM_ACTIVE;
			changeAlt(altChange);
			break;
		case COM_ACTIVE:
			double alt = Math.floor(altitude.valueInMeters());
			alt = Math.max(alt, 2.0);

			if (altChange < -1 && alt <= 10)
				altChange = -1;

			if ((alt + altChange) > 1.0) {
				altitude.set(alt + altChange);
			}
			sendCameraFocus();
			break;
		}
	}

	private void changeCoord(LatLng coord) {
		// sendCameraFocus();

		switch (state) {
		case UNINITIALIZED:
			break;
		case IDLE:
			state = FocusStates.COM_ACTIVE;
			changeCoord(coord);
			break;
		case COM_ACTIVE:
			this.coord = coord;
			sendCameraFocus();
			break;
		}

	}

	/**
	 * 发送兴趣点
	 * Description
	 * @see [class/class#field/class#method]
	 */
	@SuppressWarnings("static-access")
	private void sendCameraFocus() {
		if (state == FocusStates.COM_ACTIVE) {
			double lat = Utils.add(Utils.getDecimal(coord.latitude),Utils.getDecimal(myDrone.locationGPS.getDifferenceLat()));
			double lon = Utils.add(Utils.getDecimal(coord.longitude),Utils.getDecimal(myDrone.locationGPS.getDifferenceLon()));
			LogX.e("focus lat1:"+ lat + "    focus lon1:"+lon);
			myDrone.mavLinkSendMessage.sendFocus((float) lat,(float) lon,30.0f);
			this.sendflag = this.CF_SEND_READY;
			myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_CAMERA_FOCUS);
		}
	}

	private double getDroneAltConstained() {
		double alt = Math.floor(myDrone.airBaseParameters.getAltitude());
		return Math.max(alt, 2.0);
	}

	public LatLng getCoord() {
		return coord;
	}

	public Altitude getAltitude() {
		return this.altitude;
	}

	public boolean isActive() {
		return (state == FocusStates.COM_ACTIVE);
	}

	public boolean isInitialized() {
		return !(state == FocusStates.UNINITIALIZED);
	}

	public static int getLineNumber(Exception e) {
		StackTraceElement[] trace = e.getStackTrace();
		if (trace == null || trace.length == 0)
			return -1; //
		return trace[0].getLineNumber();
	}

}
