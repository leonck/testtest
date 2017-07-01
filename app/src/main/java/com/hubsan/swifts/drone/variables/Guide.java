//package com.hubsan.swifts.drone.variables;
//
//import android.os.Handler;
//import android.util.Log;
//
//import com.amap.api.maps.model.LatLng;
//import com.csk.hbsdrone.HubsanDroneApplication;
//import com.csk.hbsdrone.fragments.markers.MyGuideMarker;
//import com.csk.hbsdrone.helpers.units.Altitude;
//import com.hubsansdk.application.HubsanApplication;
//import com.hubsansdk.drone.HubsanDrone;
//import com.hubsansdk.drone.HubsanDroneInterfaces;
//import com.hubsansdk.drone.HubsanDroneVariable;
//
///**
// *
// * Description 照相机的焦点
// *
// * @author ShengKun.Cheng
// * @date 2015年7月28日
// * @version
// * @see [class/class#method]
// * @since [product/model]
// */
//@SuppressWarnings("incomplete-switch")
//public class Guide extends HubsanDroneVariable {
//
//	private static GuideStates state = GuideStates.UNINITIALIZED_GUIDE;
//	private LatLng coord = new LatLng(0, 0);
//	private Altitude altitude = new Altitude(0.0);
//	private int sendflag = 0;
//	public final static int CF_SEND_READY = 0;
//	public final static int CF_SEND_OK = 24;
//	public final static int CF_SEND_FAIL = 25;
//	private MyGuideMarker maker;
//	private HubsanDroneApplication app;
//
//	public MyGuideMarker getGuideMaker() {
//		return maker;
//	}
//
//	public void setGuideMaker(MyGuideMarker maker) {
//		this.maker = maker;
//	}
//
//	private enum GuideStates {
//		UNINITIALIZED_GUIDE, IDLE_GUIDE, COM_ACTIVE_GUIDE, TIMEOUT_GUIDE
//	};
//
//	public String getGuideState() {
//		return state.toString();
//	}
//
//	public int getSendflag() {
//		return sendflag;
//	}
//
//	public void setSendflag(int sendflag) {
//
//		if (this.sendflag != sendflag) {
//			this.sendflag = sendflag;
////			myDrone.events.notifyDroneEvent(DroneEventsType.ACK_RECIEVED);
//		}
//	}
//
//	public Guide(HubsanDrone myDrone) {
//		super(myDrone);
//
//	}
//
//	long delay = 3000;
//	Handler handler = new Handler();
//
//
//
//
//	public void newGuideFocus(LatLng coord) {
//		changeCoord(coord);
//	}
//
//	public void changeGuideFocusAltitude(double altChange) {
//		changeAlt(altChange);
//	}
//
//	public void forcedCameraFocusCoordinate() {
//		// if(coord)
//		initialize();
//		changeCoord(coord);
//
//	}
//
//	public void forcedGuideFocusCoordinate(LatLng coord) {
//		Log.d("csk", "forcedCameraFocusCoordinate");
//		initialize();
//		changeCoord(coord);
//
//	}
//
//	public void changeCoord() {
//		if (this.isInitialized()) {
//			changeCoord(this.coord);
//		}
//	}
//
//	public void initialize(LatLng coord) {
//		app = (HubsanDroneApplication) HubsanApplication.getApplication();
//		if (state == GuideStates.UNINITIALIZED_GUIDE) {
//			this.coord = coord;
//			altitude.set(getDroneAltConstained());
//			state = GuideStates.IDLE_GUIDE;
//			myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_GUIDE);
//		} else {
//			this.coord = coord;
//			state = GuideStates.IDLE_GUIDE;
//			myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_GUIDE);
//		}
//	}
//
//	private void initialize() {
//		app = (HubsanDroneApplication) HubsanApplication.getApplication();
//		if (state == GuideStates.UNINITIALIZED_GUIDE) {
//			coord = app.GPS.getPosition();
//			altitude.set(getDroneAltConstained());
//			state = GuideStates.IDLE_GUIDE;
//			myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_GUIDE);
//		}
//	}
//
//	public void disable() {
//		state = GuideStates.UNINITIALIZED_GUIDE;
//		myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_GUIDE);
//	}
//
//
//
//	public boolean isTimeout() {
//		return state == GuideStates.TIMEOUT_GUIDE;
//	}
//
//	public boolean isIdle() {
//		return state == GuideStates.IDLE_GUIDE;
//	}
//
//	public void idle() {
//		state = GuideStates.IDLE_GUIDE;
//	}
//
//
//	private void changeAlt(double altChange) {
//		switch (state) {
//		case UNINITIALIZED_GUIDE:
//			break;
//		case IDLE_GUIDE:
//			state = GuideStates.COM_ACTIVE_GUIDE;
//			changeAlt(altChange);
//			break;
//		case COM_ACTIVE_GUIDE:
//			double alt = Math.floor(altitude.valueInMeters());
//			alt = Math.max(alt, 2.0);
//
//			if (altChange < -1 && alt <= 10)
//				altChange = -1;
//
//			if ((alt + altChange) > 1.0) {
//				altitude.set(alt + altChange);
//			}
//			sendGuideFocus();
//			break;
//		}
//	}
//
//	private void changeCoord(LatLng coord) {
//		// sendCameraFocus();
//
//		switch (state) {
//		case UNINITIALIZED_GUIDE:
//			break;
//		case IDLE_GUIDE:
//			state = GuideStates.COM_ACTIVE_GUIDE;
//			changeCoord(coord);
//			break;
//		case COM_ACTIVE_GUIDE:
//			this.coord = coord;
//			sendGuideFocus();
//			break;
//		}
//
//	}
//
//	/**
//	 * 	发送兴趣点
//	 * Description
//	 * @see [class/class#field/class#method]
//	 */
//	@SuppressWarnings("static-access")
//	private void sendGuideFocus() {
//		if (state == GuideStates.COM_ACTIVE_GUIDE) {
//			Log.d("csk", "sendGuide");
//			this.sendflag = this.CF_SEND_READY;
////			handler.postDelayed(runnable, delay);
////			msg_set_global_position_setpoint_int msg = new msg_set_global_position_setpoint_int();
////			msg.coordinate_frame = 1;// ==1 means camera focus guide package msg
////			Log.e("csk", "sendGuideFocus:"+msg.toString()+"  msg.latitude:"+msg.latitude+"   msg.longitude:"+msg.longitude);
////			myDrone.MavClient.sendMavPacket(msg.pack());
////			myDrone.events.notifyDroneEvent(DroneEventsType.CAMERAFOCUS);
//		}
//	}
//
//	private double getDroneAltConstained() {
//
//		double alt = Math.floor(myDrone.airBaseParameters.getAltitude());
//		return Math.max(alt, 2.0);
//	}
//
//	public LatLng getGuideCoord() {
//		return coord;
//	}
//
//	public Altitude getAltitude() {
//		return this.altitude;
//	}
//
//	public boolean isActive() {
//		return (state == GuideStates.COM_ACTIVE_GUIDE);
//	}
//
//	public boolean isInitialized() {
//		return !(state == GuideStates.UNINITIALIZED_GUIDE);
//	}
//
//	public static int getLineNumber(Exception e) {
//		StackTraceElement[] trace = e.getStackTrace();
//		if (trace == null || trace.length == 0)
//			return -1; //
//		return trace[0].getLineNumber();
//	}
//
//	public void removeGuideMark(){
//		maker.removeDroneListener();
//	}
//}
