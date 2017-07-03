package com.hubsan.swifts.activitis;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.hubsan.swifts.R;
import com.hubsan.swifts.SwiftsApplication;
import com.hubsan.swifts.drone.variables.mission.MissionItem;
import com.hubsan.swifts.fragment.OptionFragment;
import com.hubsan.swifts.fragment.RCFragment;
import com.hubsan.swifts.fragment.VideoFragment;
import com.hubsan.swifts.mapUtils.OnEditorInteraction;
import com.hubsan.swifts.mapView.GestureMapFragment;
import com.hubsansdk.application.HubsanHandleMessageApp;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.utils.LogX;
import com.utils.Constants;
import com.utils.PreferenceUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment extends Fragment implements MainContract.View, GestureMapFragment.OnPathFinshedListener,
        RCFragment.OnRockerClickListener, OptionFragment.OptionListener, OnEditorInteraction, VideoFragment.VideoListener {
    RCFragment rcFragment;
    GestureMapFragment gestureMapFragment;
    OptionFragment optionFragment;
    VideoFragment videoFragment;
    MainContract.Presenter presenter;
    FragmentManager fragmentManager;
    HubsanDrone drone;
    View view;
    SwiftsApplication app;

    @BindView(R.id.changeMapCamera)
    FrameLayout changeMapCamera;
    @BindView(R.id.hubsanPlayCamera)
    FrameLayout hubsanPlayCamera;
    @BindView(R.id.mapview)
    FrameLayout mapview;

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    Unbinder unbind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main, container, false);
        unbind = ButterKnife.bind(this, view);
        fragmentManager = getChildFragmentManager();
        drone = ((HubsanHandleMessageApp) HubsanHandleMessageApp.getApplication()).drone;
        app = (SwiftsApplication) getActivity().getApplication();

        initMap();
        initJoystick();
        initOption();
        initVideo();

        screenWidth = PreferenceUtils.getPrefInt(getActivity(), "screenWidth", screenWidth);
        screenHeight = PreferenceUtils.getPrefInt(getActivity(), "screenHeight", screenHeight);
        setCameraDefult();
        return view;
    }

    //===========================================嵌入地图，摇杆，按钮操作,视频播放界面==================
    public void initMap() {
        if (gestureMapFragment == null) {
            gestureMapFragment = new GestureMapFragment();
            fragmentManager.beginTransaction().add(R.id.mapview, gestureMapFragment).commit();
            gestureMapFragment.setOnPathFinishedListener(this);
        }
    }

    public void initJoystick() {
        if (rcFragment == null) {
            rcFragment = new RCFragment();
            fragmentManager.beginTransaction().add(R.id.containerRC, rcFragment).commit();
            rcFragment.setOnRockerClickListener(this);
        }
    }

    public void initOption() {
        if (optionFragment == null) {
            optionFragment = new OptionFragment();
            fragmentManager.beginTransaction().add(R.id.optionFragment, optionFragment).commit();
            optionFragment.setOptionListener(this);
        }
    }

    public void initVideo() {
        if (videoFragment == null) {
            videoFragment = new VideoFragment();
            fragmentManager.beginTransaction().add(R.id.hubsanPlayCamera, videoFragment).commit();
            videoFragment.setListener(this);
        }
    }

    //===========================================嵌入地图，摇杆，按钮操作界面 end=========================
    @Override
    public void onPathFinished(List<Point> path) {
        presenter.onPathFinished(path);
    }

    @Override
    public void notifyRocker() {
        LogX.e("notifyRocker");
    }

    @Override
    public void cleanMap() {
        presenter.deleteAllWaypoint();
    }

    @Override
    public void clearWaypointLine() {
        gestureMapFragment.clearWaypointLine();
    }

    @Override
    public void showJoystick() {
        touchRockerOne();
    }

    @Override
    public void beginDrawWayPoin() {
        gestureMapFragment.setOverlay1(true);
    }

    @Override
    public void uploadWayPoin() {
        presenter.uploadWayPoin();
    }

    @Override
    public void beginOrCloseWapPoin() {
        presenter.beginOrCloseWapPoin();
    }

    @Override
    public void mapCalibration(boolean status) {
        gestureMapFragment.mapCalibration(status);
    }

    public void touchRockerOne() {
        if (rcFragment == null) {
            rcFragment = new RCFragment();
            fragmentManager.beginTransaction().add(R.id.containerRC, rcFragment).commit();
        } else {
            closeJoystick();
        }
    }

    @Override
    public void unableJoystick() {
        closeJoystick();
    }

    public void closeJoystick() {
        if (rcFragment != null) {
            fragmentManager.beginTransaction().remove(rcFragment).commit();
            rcFragment = null;
            drone.joystick.setRudder(0);
            drone.joystick.setAileron(0);
            drone.joystick.setThrottle(0);
            drone.joystick.setElevator(0);
        }
    }

    @Override
    public AMap getAMap() {
        return gestureMapFragment.getSupperLinMap();
    }

    @Override
    public void updateMap(List<LatLng> newPath) {
        gestureMapFragment.addMarks(newPath);
    }

    @Override
    public void addMMarkerMap(double lat, double lon, int rotate, boolean status) {
        gestureMapFragment.addMMarkerMap(lat, lon, rotate, status);
    }

    @Override
    public void onItemClick(MissionItem item) {
        switch (optionFragment.getTool()) {
            case TRASH:
                //删除单个航点
                presenter.deleteWaypoint(item);
                break;
            default:
//                if (contextualActionBar != null) {
//                    if (mission.selectionContains(item)) {
//                        mission.removeItemFromSelection(item);
//                    } else {
//                        mission.addToSelection(item);
//                    }
//                } else {
//                    if (mission.selectionContains(item)) {
//                        mission.clearSelection();
//                        removeItemDetail();
//                    } else {
//                        optionFragment.setTool(OptionFragment.EditorTools.NONE);
//                        mission.setSelectionTo(item);
//                        showItemDetail(item);
//                    }
//                }
//                break;
        }
//        notifySelectionChanged();
    }

    @Override
    public void onMapClick(LatLng coord) {
        presenter.addFocus(coord);
    }

    @Override
    public void onFocusClick(Marker marker) {
        switch (optionFragment.getTool()) {
            case TRASH:
                app.cameraFocus.disable();
                app.localLatLon.setFocusLat(0);
                app.localLatLon.setFocusLon(0);
                break;
        }
    }

    //确认校验给过来的经纬度
    @Override
    public void onLatLonClick(double lat, double lon) {
        presenter.dealCalibration(lat, lon);
        optionFragment.mapCalibration();//更新按钮状态
    }

    private int screenWidth, screenHeight;
    int leftMargin = 40;
    int topMargin = 20;

    /**
     * 设置照相机小窗口的高宽
     */
    public void setCameraDefult() {
        //设置地图大小（int width, int height）
        FrameLayout.LayoutParams layoutParamsMap = new FrameLayout.LayoutParams(screenWidth / 3, screenHeight / 3);
        layoutParamsMap.leftMargin = leftMargin;
        layoutParamsMap.topMargin = topMargin;
        changeMapCamera.setLayoutParams(layoutParamsMap);
        hubsanPlayCamera.setLayoutParams(layoutParamsMap);
    }

    int currentShow = 1;//1为地图 2为相机

    @OnClick(R.id.changeMapCamera)
    void changeMapCamera(View view) {
        if (currentShow == 1) {
            currentShow = 2;
            selectMap();
        } else {
            currentShow = 1;
            selectPlayCamera();
        }
    }

    /**
     * 进入地图界面，地图在下，图传在上
     */
    public void selectMap() {
        Message msg = new Message();
        msg.what =129;
        optionFragment.rockerInstructions(msg);

        FrameLayout.LayoutParams layoutParamsMap = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mapview.setLayoutParams(layoutParamsMap);
        hubsanPlayCamera.bringToFront();
        FrameLayout.LayoutParams layoutParamsCamera = new FrameLayout.LayoutParams(screenWidth / 3, screenHeight / 3);
        layoutParamsCamera.leftMargin = leftMargin;
        layoutParamsCamera.topMargin = topMargin;
        hubsanPlayCamera.setLayoutParams(layoutParamsCamera);
    }

    /**
     * 选择照相机，照相机位全屏，地图位小图
     */
    public void selectPlayCamera() {
        FrameLayout.LayoutParams layoutParamsCamera = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        hubsanPlayCamera.setLayoutParams(layoutParamsCamera);
        mapview.bringToFront();
        FrameLayout.LayoutParams layoutParamsMap = new FrameLayout.LayoutParams(screenWidth / 3, screenHeight / 3);
        Constants.select_mode = 2;
        layoutParamsMap.leftMargin = leftMargin;
        layoutParamsMap.topMargin = topMargin;
        mapview.setLayoutParams(layoutParamsMap);
        hubsanPlayCamera.setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * 播放图传
     */
    @Override
    public void playCameraVoid(Surface surface) {
        presenter.playCamera(surface);
    }

    @Override
    public void receiveInstructions(Message msg) {
//        switch (msg.what) {
//            case 1:
//                optionFragment.setBattery();
//                break;
//            case 2:
//                optionFragment.setSpeak();
//                break;
//            case 3:
//                optionFragment.setAirhight();
//                break;
//            case 124:
//                optionFragment.setGpsnumber();
//                break;
//            case 4:
//                optionFragment.setHubsanTopAirLatLon();
//                break;
//            case 122:
//                optionFragment.setAirAllMode();
//                break;
//            case 123:
//                optionFragment.setHubsanTopRockerLatLon();
//                break;
//            case 125:
//                optionFragment.setAirConnectionStatus();
//                break;
//            case 129:
                optionFragment.rockerInstructions(msg);
//                optionFragment.setBattery();
//                break;
//        }
//        myHandler.sendMessage(msg);
    }

    MyHandler myHandler = new MyHandler(this);

    private class MyHandler extends Handler {
        WeakReference<Fragment> mActivityReference;

        MyHandler(Fragment activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Fragment activity = mActivityReference.get();
            if (activity != null) {
                switch (msg.what) {

                }
            }
        }
    }

}
