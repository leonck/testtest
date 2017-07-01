package com.hubsan.swifts.mapView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnCameraChangeListener;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMapLongClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.AMap.OnMarkerDragListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.Polygon;
import com.hubsan.swifts.R;
import com.hubsan.swifts.activitis.MainFragment;
import com.hubsan.swifts.dialogs.CustomDialog;
import com.hubsan.swifts.drone.variables.mission.MissionItem;
import com.hubsan.swifts.drone.variables.mission.waypoints.SpatialCoordItem;
import com.hubsan.swifts.mapUtils.OnEditorInteraction;
import com.hubsan.swifts.mapUtils.marks.MarkerManager;
import com.hubsansdk.utils.LogX;
import com.utils.PreferenceUtils;

@SuppressLint("UseSparseArrays")
@SuppressWarnings("unused")

/**
 *
 * Description
 * 	用作编辑航点 兴趣点操作
 * @author ShengKun.Cheng
 * @date  2015年7月15日
 * @version
 * @see [class/class#method]
 * @since [product/model]
 */
public class EditorMapFragment extends DroneMap implements
        OnMapLongClickListener, OnMarkerDragListener, OnMapClickListener, OnCameraChangeListener,
        OnMarkerClickListener,AMap.OnMapTouchListener{

//    public MapPath polygonPath;
    public Polygon polygon;
    private OnEditorInteraction editorListener;
//    private EditDroneMarker droneMarker;
    private boolean longpresstocalibration = false;
//    private OnCameraCoordListener mCameraListener;
    public void clearLine() {
        this.clearLines();
    }

    @Override
    public void onTouch(MotionEvent motionEvent) {

    }

//    public interface OnCameraCoordListener {
//        public void coordValue(float bearing);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle bundle) {
        View view = super.onCreateView(inflater, viewGroup, bundle);

//        mCameraListener = (OnCameraCoordListener) getActivity();
//        droneMarker = new EditDroneMarker(this);
        editorListener = (OnEditorInteraction)getActivity().getFragmentManager().findFragmentByTag("mainFragment");
        getaMap().setOnMarkerDragListener(this);
        getaMap().setOnMarkerClickListener(this);
        getaMap().setOnMapClickListener(this);
        getaMap().setOnMapLongClickListener(this);
        getaMap().setOnCameraChangeListener(this);
//        polygonPath = new MapPath(getaMap(), getActivity().getResources().getColor(
//                R.color.hubasn_point_item_dialog_num_text), getResources());
//        app.mission.init(PreferenceUtils.getPrefInt(getActivity(), Constants.SETTING_FLIGHTDATA_DEFAULT_HEIGHT, 30));
//        app.mission.initSpeed(5);
//        if (polygon == null) {
//            polygon = new Polygon();
//        }
        return view;
    }

//    public void setListener(OnEditorInteraction editorListener){
//        this.editorListener = editorListener;
//    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferences();
    }

    private void getPreferences() {

        /** 获取允许地图是否校准 */
        // isDisPlayMapZoom =
//        longpresstocalibration = PreferenceUtils.getPrefBoolean(getActivity(), Constants.SETTING_MAP_CALIBRATION_OPEN_CLOSE, false);
    }

    @Override
    public void update() {
        super.update();
//        markers.updateMarkers(polygon.getPolygonPoints(), true, context);
//        polygonPath.update(polygon);
    }

    /**
     * 对移动地图结束事件回调
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        float bear = cameraPosition.bearing;
//        mCameraListener.coordValue(bear);
        if (getaMap() != null) {
            float bearing = getaMap().getCameraPosition().bearing;
            setMapViewRotateAngle(bearing);
            getaMap().setMyLocationRotateAngle(0.0f);//设置旋转角度位置
            float zoom = getaMap().getCameraPosition().zoom;
            app.localLatLon.setMapZoom(zoom);
            LogX.e("isZoom:"+zoom);
            setaMapTouch(false);
            touchHandle.removeCallbacks(touchRunnable);
            touchHandle.postDelayed(touchRunnable,800);
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        LatLng target = cameraPosition.target;
//        LogX.e("lat:"+target.latitude+"     ,lon:"+target.longitude);
        //这里可以得到当前屏幕中央位置的经纬度
        app.localLatLon.setMapCenterLat(target.latitude);
        app.localLatLon.setLocationMapLon(target.longitude);
    }

    @Override
    public void onMapLongClick(final LatLng point) {

//        longpresstocalibration = PreferenceUtils.getPrefBoolean(getActivity(),
//                Constants.SETTING_MAP_CALIBRATION_OPEN_CLOSE, false);
//        boolean isCalibration = PreferenceUtils.getPrefBoolean(getActivity(), "isCalibration", false);
//        LogX.e("onMapLongClick isCalibration:"+isCalibration);
        // mListener.onAddPoint(point);
//        if (longpresstocalibration == true || isCalibration == true) {
        if (mapCalibrationEnable) {
            CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
            builder.setMessage(getString(R.string.dialog_calibrate_map_message));
            builder.setTitle(R.string.lat_long_mag_title);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    onGenerateOffset(point);
                    dialog.dismiss();
                    PreferenceUtils.setPrefBoolean(getActivity(), "isCalibration", false);
                }
            });
            builder.setNegativeButton(android.R.string.cancel,
                    new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
//                            editorListener.onLatLonCancel();
                            dialog.dismiss();
                            PreferenceUtils.setPrefBoolean(getActivity(), "isCalibration", false);
                        }
                    });
            builder.create().show(getActivity());
            builder.setCancelable(true);
        }
//        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        MarkerManager.MarkerSource source = markers.getSourceFromMarker(marker);
        checkForWaypointMarkerMoving(source, marker, true);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        MarkerManager.MarkerSource source = markers.getSourceFromMarker(marker);
        checkForWaypointMarkerMoving(source, marker, false);
    }

    private void checkForWaypointMarkerMoving(MarkerManager.MarkerSource source,
                                              Marker marker, boolean dragging) {
        if (SpatialCoordItem.class.isInstance(source)) {
            LatLng position = marker.getPosition();

            // update marker source
            SpatialCoordItem waypoint = (SpatialCoordItem) source;
            waypoint.setCoordinate(position);

            // update flight path
            missionPath.update(mission);
        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        MarkerManager.MarkerSource source = markers.getSourceFromMarker(marker);
        checkForWaypointMarker(source, marker);
        checkForPolygonMarker(source, marker);
    }

    private void checkForWaypointMarker(MarkerManager.MarkerSource source, Marker marker) {
//        if (SpatialCoordItem.class.isInstance(source)) {
            // mListener.onMoveWaypoint((SpatialCoordItem) source,
            // marker.getPosition());
//        }
    }

    private void checkForPolygonMarker(MarkerManager.MarkerSource source, Marker marker) {
//        if (PolygonPoint.class.isInstance(source)) {
            // mListener.onMovePolygonPoint((PolygonPoint)
            // source,marker.getPosition());
//        }
    }

    @Override
    public void onMapClick(LatLng point) {
        editorListener.onMapClick(point);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
//        if (marker.equals(app.cameraFocus.getMaker().getFocusMarker())) {
//            editorListener.onFocusClick(marker);
//            return true;
//        }
//        if (marker.equals(app.mGuide.getGuideMaker().getMyGuideMarker())) {
//            editorListener.onGuideClick(marker);
//            return true;
//        }
        MarkerManager.MarkerSource source = markers.getSourceFromMarker(marker);
        if (source instanceof MissionItem) {
            editorListener.onItemClick((MissionItem) source);
            return true;
        } else {
            return false;
        }

    }

    @Override
    protected boolean isMissionDraggable() {
        return true;
    }

    private void animateCamera(LatLng coord) {
        getaMap().animateCamera(CameraUpdateFactory.newLatLng(coord));

    }
    private Handler touchHandle = new Handler();
    private Runnable touchRunnable = new Runnable() {
        @Override
        public void run() {
            setaMapTouch(true);
            touchHandle.removeCallbacks(touchRunnable);
        }
    };

    /**
     * 获取校准后的经纬度保存起来
     * Description
     *
     * @param coord
     * @see [class/class#field/class#method]
     */
    public void onGenerateOffset(LatLng coord) {
        editorListener.onLatLonClick(coord.latitude, coord.longitude);
    }

    @Override
    public void onDestroy() {
        //删除上一个目录的回调监听
//        if (droneMarker != null) {
//            droneMarker.removeDroneListener();
//        }
        touchHandle.removeCallbacks(touchRunnable);
        super.onDestroy();
    }
    boolean mapCalibrationEnable;
    public void mapCalibration(boolean status){
        this.mapCalibrationEnable = status;
    }
}
