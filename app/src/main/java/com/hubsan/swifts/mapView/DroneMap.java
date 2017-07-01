package com.hubsan.swifts.mapView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.hubsan.swifts.R;
import com.hubsan.swifts.SwiftsApplication;
import com.hubsan.swifts.drone.variables.mission.Mission;
import com.hubsan.swifts.mapUtils.MapPath;
import com.hubsan.swifts.mapUtils.marks.FocusMarker;
import com.hubsan.swifts.mapUtils.marks.MarkerManager;
import com.hubsan.swifts.mapView.MapFragment;
import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.application.HubsanHandleMessageApp;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces;

/**
 * Description
 * 无人机的地图 用于摄像机位置等控制
 *
 * @author ShengKun.Cheng
 * @date 2015年7月15日
 * @see [class/class#method]
 * @since [product/model]
 */
public abstract class DroneMap extends MapFragment implements HubsanDroneInterfaces.OnDroneListener {

    protected MarkerManager markers;
    protected MapPath missionPath;
        public FocusMarker focusMarker;
//    public MyGuideMarker guideMarker;
    public HubsanDrone drone;
    public SwiftsApplication app;
    public Mission mission;
    protected Context context;
    private final int MINLEVEL = 3;

    protected abstract boolean isMissionDraggable();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {

        View view = super.onCreateView(inflater, viewGroup, bundle);
        context = getActivity().getApplicationContext();
        app = (SwiftsApplication) HubsanApplication.getApplication();
        drone = ((HubsanHandleMessageApp) getActivity().getApplication()).drone;
        mission = app.mission;
        markers = new MarkerManager(getaMap());
        focusMarker = new FocusMarker(getaMap());
//        app.cameraFocus.setMaker(focusMarker);
//        guideMarker = new MyGuideMarker(this);
//        app.mGuide.setGuideMaker(guideMarker);
        missionPath = new MapPath(getaMap(), ContextCompat.getColor(getActivity(),
                R.color.hubasn_point_item_dialog_num_text), getResources());

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        drone.events.addDroneListener(this);
        loadCameraPosition();
        update();
//        focusMarker.update();
//        guideMarker.updateGuide();
    }

    @Override
    public void onStop() {

        super.onStop();
        drone.events.removeDroneListener(this);
        saveCameraPosition();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * 保存相机位置 Description
     *
     * @see [class/class#field/class#method]
     */
    public void saveCameraPosition() {

        if (getaMap() != null) {
            CameraPosition camera = getaMap().getCameraPosition();
            SharedPreferences settings = context.getSharedPreferences("MAP", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putFloat("lat", (float) camera.target.latitude);
            editor.putFloat("lng", (float) camera.target.longitude);
            editor.putFloat("bea", camera.bearing);
            editor.putFloat("tilt", camera.tilt);
            editor.putFloat("zoom", camera.zoom);
            editor.commit();
        }
    }

    /**
     * 加载摄像头的位置 Description
     *
     * @see [class/class#field/class#method]
     */
    private void loadCameraPosition() {
        if (getaMap() != null) {
            com.amap.api.maps.model.CameraPosition.Builder camera = new CameraPosition.Builder();
            SharedPreferences settings = context.getSharedPreferences("MAP", 0);
            camera.bearing(settings.getFloat("bea", 0));
            camera.tilt(settings.getFloat("tilt", 0));
            camera.zoom(settings.getFloat("zoom", 0));
            camera.target(new LatLng(settings.getFloat("lat", 0), settings.getFloat("lng", 0)));
            getaMap().moveCamera(CameraUpdateFactory.newCameraPosition(camera.build()));
        }
    }

    @Override
    public void onDroneEvent(HubsanDroneInterfaces.DroneEventsType event, HubsanDrone drone) {

        switch (event) {
            case HUBSAN_MISSION_UPDATE:
                update();
                break;
            default:
                break;
        }
    }

    /**
     * 调用清除航线的方法
     */
    public void clearLines() {
        if (missionPath != null) {
            missionPath.cancelPolyline();
        }
    }

    /**
     * 更新地图上的点
     */
    public void update() {
        markers.clean();
        markers.updateMarkers(mission.getMarkers(), isMissionDraggable(), context);
        missionPath.update(mission);
    }

    @Override
    public void onDestroy() {
        if (markers != null) {
            markers.clean();
            markers = null;
        }
//        if (focusMarker!= null){
//            focusMarker.removeDroneListener();
//            focusMarker = null;
//        }
//        if (guideMarker!= null){
//            guideMarker.removeDroneListener();
//            guideMarker = null;
//        }

        super.onDestroy();
    }
}
