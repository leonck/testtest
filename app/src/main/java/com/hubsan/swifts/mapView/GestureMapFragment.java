package com.hubsan.swifts.mapView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.hubsan.swifts.R;
import com.hubsan.swifts.helpers.geoTools.Simplify;
import com.hubsansdk.utils.LogX;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description 地图上覆盖手势
 *
 * @author ShengKun.Cheng
 * @date 2015年7月22日
 * @see [class/class#method]
 * @since [product/model]
 */
public class GestureMapFragment extends Fragment implements OnGestureListener {
    @BindView(R.id.overlay1)
    GestureOverlayView overlay1;

    FragmentManager fragmentManager;
    View view;
    EditorMapFragment editorMapFragment;
    Unbinder unbinder;
    private double toleranceInPixels;
    OnPathFinshedListener listener;
//    AMap aMap;

    public interface OnPathFinshedListener {
        void onPathFinished(List<Point> paht);
        void unableJoystick();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hubsan_fragment_gesture_map, container, false);
        unbinder = ButterKnife.bind(this, view);
        toleranceInPixels = scaleDpToPixels(1);
        overlay1.addOnGestureListener(this);
        initMapFragment();

        setOverlay1(false);
        return view;
    }

    public void initMapFragment() {
//        fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        if (editorMapFragment == null) {
            editorMapFragment = new EditorMapFragment();
            fragmentTransaction.add(R.id.editmapview, editorMapFragment).commit();
        }
    }

    public AMap getSupperLinMap() {
        return editorMapFragment.getaMap();
    }

    public void setGestureEnable(boolean enable) {
        overlay1.setEnabled(enable);
    }

    public void setOnPathFinishedListener(OnPathFinshedListener listener) {

        this.listener = listener;
    }

    @Override
    public void onGestureStarted(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
        LogX.e("手势绘制完成");
    }

    @Override
    public void onGesture(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
        LogX.e("手势绘制完成");
    }

    @Override
    public void onGestureEnded(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
        LogX.e("手势绘制完成");
        overlay1.setEnabled(false);
        List<Point> path = new ArrayList<>();
        float[] points = gestureOverlayView.getGesture().getStrokes().get(0).points;
        for (int i = 0; i < points.length; i += 2) {
            path.add(new Point((int) points[i], (int) points[i + 1]));
        }
        if (path.size() > 1) {
            // 调用算法
            path = Simplify.simplify(path, toleranceInPixels);
        }
        listener.onPathFinished(path);
    }

    @Override
    public void onGestureCancelled(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
        LogX.e("手势绘制完成");
    }

    private int scaleDpToPixels(double value) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) Math.round(value * scale);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void cleanMap(){
        if (editorMapFragment != null) {
            editorMapFragment.cleanMap();
        }
        overlay1.setEnabled(true);
        listener.unableJoystick();
    }

    public void addMarks(List<LatLng> newPath){
        editorMapFragment.addMarks(newPath);
    }

    public void setOverlay1(boolean status){
        overlay1.setEnabled(status);
    }

    public void mapCalibration(boolean status){
        editorMapFragment.mapCalibration(status);
    }

    public void addMMarkerMap(double lat, double lon, int rotate, boolean status){
        editorMapFragment.addMMarkerMap(lat,lon,rotate,status);
    }

    public void clearWaypointLine() {
        editorMapFragment.clearLine();
    }

    public void findMyLocation(double lat, double lon) {
        if (editorMapFragment != null) {
            editorMapFragment.findMyLocation(lat, lon);
        }
    }
}
