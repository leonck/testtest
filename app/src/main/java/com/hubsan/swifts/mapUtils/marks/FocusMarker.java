package com.hubsan.swifts.mapUtils.marks;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.hubsan.swifts.R;
import com.hubsan.swifts.SwiftsApplication;
import com.hubsan.swifts.mapUtils.marks.helpers.MarkerWithText;
import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces;

/**
 * Description 焦点标记
 *
 * @author ShengKun.Cheng
 * @date 2015年7月15日
 * @see [class/class#method]
 * @since [product/model]
 */
public class FocusMarker implements HubsanDroneInterfaces.OnDroneListener {

    private Marker focusMarker;
    //	private DroneMap flightMapFragment;
    private AMap aMap;
    private HubsanDrone drone;
    private SwiftsApplication app;

    public Marker getFocusMarker() {
        return focusMarker;
    }

    /**
     * 焦点标记
     */
    public FocusMarker(AMap aMap) {
        this.aMap = aMap;
//		this.drone = droneMap.drone;
        app = (SwiftsApplication) HubsanApplication.getApplication();
        drone = app.drone;
        addMarkerToMap();
        drone.events.addDroneListener(this);
    }

    private void updatePosition(boolean visible, float yaw, LatLng coord) {
        focusMarker.setPosition(coord);
        // focusMarker.setRotation(yaw);
        focusMarker.setVisible(visible);
    }

    /**
     * 将标记添加到地图
     */
    private void addMarkerToMap() {
        focusMarker = aMap.addMarker(new MarkerOptions()
                .anchor((float) 0.5, (float) 0.5).position(new LatLng(0, 0))
                .icon(getIcon(HubsanApplication.getApplication()))
                .visible(app.cameraFocus.isInitialized()));
    }

    public void update() {
        updatePosition(app.cameraFocus.isInitialized(), 0,
                app.cameraFocus.getCoord());
    }


    @Override
    public void onDroneEvent(HubsanDroneInterfaces.DroneEventsType event, HubsanDrone drone) {
        switch (event) {
            case HUBSAN_CAMERA_FOCUS:
                updatePosition(app.cameraFocus.isInitialized(), 0,
                        app.cameraFocus.getCoord());
                break;
            default:
                break;
        }

    }

    /**
     * Description  相机
     *
     * @param context
     * @return
     * @see [class/class#field/class#method]
     */
    private static BitmapDescriptor getIcon(Context context) {
        return BitmapDescriptorFactory.fromBitmap(MarkerWithText
                .getMarkerWithTextAndDetail(R.drawable.ic_wp_map, "Focus", "",
                        context));
    }

    public void removeDroneListener() {
        drone.events.removeDroneListener(this);
        if (focusMarker != null) {
            focusMarker.remove();
            focusMarker.destroy();
            focusMarker = null;
        }
    }
}
