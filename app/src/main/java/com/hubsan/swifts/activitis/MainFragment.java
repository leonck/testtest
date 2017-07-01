package com.hubsan.swifts.activitis;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
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
import com.hubsan.swifts.mapUtils.OnEditorInteraction;
import com.hubsan.swifts.mapView.GestureMapFragment;
import com.hubsansdk.application.HubsanHandleMessageApp;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.utils.LogX;
import com.utils.Constants;
import com.utils.PreferenceUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends Fragment implements MainContract.View, GestureMapFragment.OnPathFinshedListener,
        RCFragment.OnRockerClickListener, OptionFragment.OptionListener, OnEditorInteraction {
    RCFragment rcFragment;
    GestureMapFragment gestureMapFragment;
    OptionFragment optionFragment;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, view);
        fragmentManager = getChildFragmentManager();
        drone = ((HubsanHandleMessageApp) HubsanHandleMessageApp.getApplication()).drone;
        app = (SwiftsApplication) getActivity().getApplication();

        initMap();
        initJoystick();
        initOption();

        cameraTextureOne =  view.findViewById(R.id.cameraTextureOne);//图传SurfaceView
        cameraTextureOne.setSurfaceTextureListener(mTextureListenerOne);
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
//                deletePoint(item);
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

    TextureView cameraTextureOne;

    /**
     * 播放图传
     */
    public void playCamera() {

        SurfaceTexture surfaceTexture = cameraTextureOne.getSurfaceTexture();
        Surface surface = new Surface(surfaceTexture);
        app.videoplayer.setSurface(surface);
        surface.release();
        app.videoplayer.surfaceChanged();
        LogX.e("*************等待一秒*****************");
        mStartVideoHandler.postDelayed(mStartVideoRunnable, 1000);
    }

    public TextureView.SurfaceTextureListener mTextureListenerOne = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            playCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };

    private Handler mStartVideoHandler = new Handler();
    private Runnable mStartVideoRunnable = new Runnable() {
        @Override
        public void run() {
            app.videoplayer.start(Constants.TCP_IMAGE_TRANSFER);
            LogX.e("播放视频");
            mStartVideoHandler.removeCallbacks(mStartVideoRunnable);
        }
    };

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

    int currentShow =1;//1为地图 2为相机
   @OnClick(R.id.changeMapCamera)
   private void  changeMapCamera(View view){
       if (currentShow==1){
           currentShow = 2;
           selectMap();
       }else{
           currentShow = 1;
           selectPlayCamera();
       }
   }

    /**
     * 进入地图界面，地图在下，图传在上
     */
    public void selectMap() {
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
        cameraTextureOne.setVisibility(View.VISIBLE);
    }


}
