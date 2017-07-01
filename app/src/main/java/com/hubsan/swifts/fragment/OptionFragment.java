package com.hubsan.swifts.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MAVLink.hubsan.msg_hubsan_quad_status;
import com.hubsan.swifts.R;
import com.hubsan.swifts.SwiftsApplication;
import com.hubsan.swifts.drone.variables.mission.MissionItem;
import com.hubsan.swifts.widgets.CommonDialog;
import com.hubsan.swifts.widgets.StrokeText;
import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.application.HubsanHandleMessageApp;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces;
import com.hubsansdk.utils.Constants;
import com.hubsansdk.utils.LogX;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @project name: X-Hubsan
 * @class name：com.csk.hbsdrone.fragments
 * @class describe:
 * @anthor shengkun.cheng
 * @time 2017/2/25 21:30
 * @change
 * @chang time:
 * @class describe:
 */

public class OptionFragment extends Fragment implements HubsanDroneInterfaces.OnDroneListener {
    HubsanDrone drone;
    OptionListener optionListener;
    SwiftsApplication app;

    private EditorTools tool = EditorTools.NONE;

    public enum EditorTools {
        HISTORY, LIGHTHOUSE, MARKER, DRAW, POLY, TRASH, TRASHAll, NONE, CLEANLINE, MAPMODEL,
        BACKHMOE, COMPASSBTN, SETTINGBTN, MSWITCH, TOGGLERC, ROCKER, GUIDE, SHARE, SHAREBEGIN,
        MYLOCATION, AIRLOCATION, MAPMODE, RESETMAPlINE, CLEAN_RADIUS, CLEAN_RADIUS_EQUALS,
        BLE_RECODE_BEGING, BLE_RECODE_END, BLE_UP_FLY, BLE_DOWN_FLY, BLE_RETURN_BACK, BLE_CANCEL_RETURN_BACK,
        BLE_PHOTO, BLE_AUTO_CONNECTION
    }

    @BindView(R.id.headView)
    LinearLayout headView;
    @BindView(R.id.hubsanPitch)
    StrokeText hubsanPitch;
    @BindView(R.id.hubsanRoll)
    StrokeText hubsanRoll;
    @BindView(R.id.hubsanYaw)
    StrokeText hubsanYaw;
    @BindView(R.id.hubsanTopAirLatLon)
    StrokeText hubsanTopAirLatLon;
    @BindView(R.id.hubsanTopRockerLatLon)
    StrokeText hubsanTopRockerLatLon;
    @BindView(R.id.headDataView)
    LinearLayout headDataView;
    @BindView(R.id.hubsanHeadImage)
    ImageView hubsanHeadImage;
    @BindView(R.id.hubsanRockerThrottle)
    TextView hubsanRockerThrottle;
    @BindView(R.id.hubsanRockerRudder)
    TextView hubsanRockerRudder;
    @BindView(R.id.hubsanRockerLay)
    LinearLayout hubsanRockerLay;
    @BindView(R.id.hubsanRockerElevator)
    TextView hubsanRockerElevator;
    @BindView(R.id.hubsanRockerAileron)
    TextView hubsanRockerAileron;
    @BindView(R.id.hubsanHeadLay)
    LinearLayout hubsanHeadLay;
    @BindView(R.id.hubsanHeadRightTwo)
    LinearLayout hubsanHeadRightTwo;
    @BindView(R.id.topFind)
    ImageView topFind;
    @BindView(R.id.mapModelBtn)
    ImageView mapModelBtn;
    @BindView(R.id.topMapCalibration)
    ImageView topMapCalibration;
    @BindView(R.id.topLeftTools)
    LinearLayout topLeftTools;
    @BindView(R.id.hubsanHeadRight)
    LinearLayout hubsanHeadRight;
    @BindView(R.id.right_show)
    LinearLayout rightShow;
    @BindView(R.id.hubsanFollowModeMenu)
    ImageView hubsanFollowModeMenu;
    @BindView(R.id.hubsanLeftRocker)
    ImageView hubsanLeftRocker;
    @BindView(R.id.leftTurnBack)
    ImageView leftTurnBack;
    @BindView(R.id.leftIsUnLock)
    ImageView leftIsUnLock;
    @BindView(R.id.leftBtn)
    LinearLayout leftBtn;
    @BindView(R.id.leftView)
    LinearLayout leftView;
    @BindView(R.id.hubsanMenuRockerBtnImage)
    ImageView hubsanMenuRockerBtnImage;
    @BindView(R.id.hubsanMenuRockerBtnText)
    TextView hubsanMenuRockerBtnText;
    @BindView(R.id.hubsanMenuRockerBtn)
    LinearLayout hubsanMenuRockerBtn;
    @BindView(R.id.hubsanMenuWaypointBtnImage)
    ImageView hubsanMenuWaypointBtnImage;
    @BindView(R.id.hubsanMenuWaypointBtnText)
    TextView hubsanMenuWaypointBtnText;
    @BindView(R.id.hubsanMenuWaypointBtn)
    LinearLayout hubsanMenuWaypointBtn;
    @BindView(R.id.hubsanMenuFollowBtnImage)
    ImageView hubsanMenuFollowBtnImage;
    @BindView(R.id.hubsanMenuFollowBtnText)
    TextView hubsanMenuFollowBtnText;
    @BindView(R.id.hubsanMenuFollowBtn)
    LinearLayout hubsanMenuFollowBtn;
    @BindView(R.id.hubsanMenuSurroundBtnImage)
    ImageView hubsanMenuSurroundBtnImage;
    @BindView(R.id.hubsanMenuSurroundBtnText)
    TextView hubsanMenuSurroundBtnText;
    @BindView(R.id.hubsanMenuSurroundBtn)
    LinearLayout hubsanMenuSurroundBtn;
    @BindView(R.id.hubsanMenuVRBtnImage)
    ImageView hubsanMenuVRBtnImage;
    @BindView(R.id.hubsanMenuVRBtnText)
    TextView hubsanMenuVRBtnText;
    @BindView(R.id.hubsanMenuVRBtn)
    LinearLayout hubsanMenuVRBtn;
    @BindView(R.id.hubsanModeLayout)
    LinearLayout hubsanModeLayout;
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.airConnectionStatus)
    TextView airConnectionStatus;
    @BindView(R.id.airhight)
    TextView airhight;
    @BindView(R.id.speak)
    TextView speak;
    @BindView(R.id.gpsnumber)
    TextView gpsnumber;
    @BindView(R.id.battery)
    TextView battery;
    @BindView(R.id.hubsanWaypointOtherMode)
    TextView hubsanWaypointOtherMode;
    @BindView(R.id.hubsanWayLine)
    ImageView hubsanWayLine;
    @BindView(R.id.hubsanWayPoint)
    ImageView hubsanWayPoint;
    @BindView(R.id.hubsanWayDraw)
    LinearLayout hubsanWayDraw;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.rightDrawRoute)
    LinearLayout rightDrawRoute;
    @BindView(R.id.hubsanDelLine)
    ImageView hubsanDelLine;
    @BindView(R.id.hubsanDelPoint)
    ImageView hubsanDelPoint;
    @BindView(R.id.hubsanDeletPiont)
    LinearLayout hubsanDeletPiont;
    @BindView(R.id.rightDeleteRoute)
    LinearLayout rightDeleteRoute;
    @BindView(R.id.hubsanFocus)
    ImageView hubsanFocus;
    @BindView(R.id.hubsanGuide)
    ImageView hubsanGuide;
    @BindView(R.id.hubsanGuidePiont)
    LinearLayout hubsanGuidePiont;
    @BindView(R.id.rightGuide)
    LinearLayout rightGuide;
    @BindView(R.id.hubsanUploadImage)
    ImageView hubsanUploadImage;
    @BindView(R.id.hubsanUploadText)
    TextView hubsanUploadText;
    @BindView(R.id.rightUpload)
    LinearLayout rightUpload;
    @BindView(R.id.leftWaypoint)
    TextView leftWaypoint;
    @BindView(R.id.rightWayPointView)
    LinearLayout rightWayPointView;

    public interface OptionListener {
        void cleanMap();

        void showJoystick();

        void beginDrawWayPoin();

        void uploadWayPoin();

        void beginOrCloseWapPoin();

        void mapCalibration(boolean status);
    }

    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        mView = inflater.inflate(R.layout.fragment_option, viewGroup, false);
        drone = ((HubsanHandleMessageApp) HubsanHandleMessageApp.getApplication()).drone;
        ButterKnife.bind(this, mView);
        app = (SwiftsApplication) HubsanApplication.getApplication();
        drone.events.addDroneListener(this);
        if (mMyHandler == null) {
            mMyHandler = new MyHandler(this);
        }
        initView();
        checkConnectHandle.postDelayed(checkConnectRunnable, 1000);
        return mView;
    }

    public void initView() {
        hubsanTopRockerLatLon.setText(drone.gpsManager.mLat + "," + drone.gpsManager.mLon);
    }

    @Override
    public void onDroneEvent(HubsanDroneInterfaces.DroneEventsType event, HubsanDrone drone) {
        switch (event) {
            case HUBSAN_BATTERY://电量
                Message batteryMsg = new Message();
                batteryMsg.what = 1;
                if (mMyHandler != null) {
                    mMyHandler.sendMessage(batteryMsg);
                }
                break;
            case HUBSAN_BASE_VALUE://高度 距离
                Message heightMsg = new Message();
                heightMsg.what = 2;
                if (mMyHandler != null) {
                    mMyHandler.sendMessage(heightMsg);
                }
                break;
            case HUBSAN_AIR_ALTITUDE://飞机高度
                Message airHightMsg = new Message();
                airHightMsg.what = 3;
                if (mMyHandler != null) {
                    mMyHandler.sendMessage(airHightMsg);
                }
                break;
            case HUBSAN_LAT_LON://飞机经纬度
                Message lonMsg = new Message();
                lonMsg.what = 4;
                if (mMyHandler != null) {
                    mMyHandler.sendMessage(lonMsg);
                }
                break;
            case HUBSAN_JOYSTICK_VALUE://摇杆数据
                setRockerData();
                break;
            case HUBSAN_YES_FOLLOW_MODE: //开启跟随

                break;
            case HUBSAN_NO_FOLLOW_MODE: //关闭跟随

                break;
            case HUBSAN_AIL_MODE:// 飞机所有状态模式
                Message airModeMsg = new Message();
                airModeMsg.what = 122;
                if (mMyHandler != null) {
                    mMyHandler.sendMessage(airModeMsg);
                }
                break;
            case HUBSAN_GPS_MANAGER://
                Message localMsg = new Message();
                localMsg.what = 123;
                if (mMyHandler != null) {
                    mMyHandler.sendMessage(localMsg);
                }
                break;
            case HUBSAN_GPS_NUMBER://
                Message gpsMsg = new Message();
                gpsMsg.what = 124;
                if (mMyHandler != null) {
                    mMyHandler.sendMessage(gpsMsg);
                }
                break;
            case HUBSAN_OPEN_FOLLOW_ME_FAILED: //开启跟随失败
                Message followMsg = new Message();
                followMsg.what = 126;
                if (mMyHandler != null) {
                    mMyHandler.sendMessage(followMsg);
                }
                break;
            case HUBSAN_OPEN_SURROUND_FAILED: //开启环绕失败
                Message arrowMsg = new Message();
                arrowMsg.what = 127;
                if (mMyHandler != null) {
                    mMyHandler.sendMessage(arrowMsg);
                }
                break;
            case HUBSAN_OPEN_WAYPOINT_FAILED: //开启航点失败
                Message waypointMsg = new Message();
                waypointMsg.what = 128;
                if (mMyHandler != null) {
                    mMyHandler.sendMessage(waypointMsg);
                }
                break;
        }
    }

    MyHandler mMyHandler;

    class MyHandler extends Handler {
        WeakReference<Fragment> mActivityReference;

        MyHandler(Fragment activity) {
            mActivityReference = new WeakReference<Fragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Fragment activity = mActivityReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        battery.setText(drone.battery.getBatteryValue() + "%");
                        break;
                    case 2:
                        speak.setText(drone.airBaseParameters.getX() + "/" + drone.airBaseParameters.getVx());
                        break;
                    case 3:
                        airhight.setText("" + drone.airBaseParameters.getAltitude());
                        break;
                    case 124:
                        gpsnumber.setText("" + drone.airGPS.getGpsNumber());
                        break;
                    case 4:
                        hubsanTopAirLatLon.setText("(" + drone.airGPS.getAirLat() + "," + drone.airGPS.getAirLon() + ")");
//                        mylanlat.setText("(" + app.localLatLon.getLocalLat() + "," + app.localLatLon.getLocalLon() + ")");
                        break;
                    case 122:
                        msg_hubsan_quad_status msgHubsanQuadStatus = drone.airMode.getQuad_status();
                        short quadMode = msgHubsanQuadStatus.quad_mode_status;
                        short recStatus = msgHubsanQuadStatus.recording_status;
                        airAllMode(quadMode, recStatus);
                        break;
                    case 123:
                        hubsanTopRockerLatLon.setText(drone.gpsManager.mLat + "," + drone.gpsManager.mLon);
                        break;
                    case 125:
                        airConnectionStatus.setText("设备未连接");
                        break;
                    case 126:
                        Toast.makeText(getActivity(), "开启跟随失败", Toast.LENGTH_SHORT).show();
                        cancelFollowMode();
                        break;
                    case 127:
                        Toast.makeText(getActivity(), "开启环绕失败", Toast.LENGTH_SHORT).show();
                        cancelSounnderMode();
                        break;
                    case 128:
                        Toast.makeText(getActivity(), "开启航点失败", Toast.LENGTH_SHORT).show();
                        cancelWaypointMode();
                        break;

                }
            }
        }
    }

    /**
     * 设置显示摇杆数据
     */
    public void setRockerData() {
        hubsanRockerThrottle.setText("T: " + (int) (drone.joystick.getThrottleRaw() * 0.1) + "%");
        hubsanRockerRudder.setText("R: " + (int) (drone.joystick.getRudderRaw() * 0.1) + "%");
        hubsanRockerElevator.setText("E: " + (int) (drone.joystick.getElevatorRaw() * 0.1) + "%");
        hubsanRockerAileron.setText("A: " + (int) (drone.joystick.getAileronRaw() * 0.1) + "%");
    }

    public void setOptionListener(OptionListener listener) {
        optionListener = listener;
    }

    @OnClick({R.id.hubsanLeftRocker, R.id.hubsanFollowModeMenu, R.id.hubsanMenuRockerBtn, R.id.hubsanMenuWaypointBtn,
            R.id.hubsanMenuFollowBtn, R.id.hubsanMenuSurroundBtn, R.id.hubsanDelLine, R.id.leftIsUnLock, R.id.hubsanWayLine,
            R.id.rightUpload, R.id.leftWaypoint, R.id.topMapCalibration, R.id.hubsanFocus, R.id.hubsanDelPoint})
    public void clean(View v) {
        switch (v.getId()) {
            case R.id.hubsanLeftRocker:
                showOrhintJoystick();
                break;
            case R.id.hubsanFollowModeMenu:
                showSelectModle();
                break;
            case R.id.hubsanMenuRockerBtn: //F
                hubsanModeLayout.setVisibility(View.GONE);
                break;
            case R.id.hubsanMenuWaypointBtn: //航点
                hubsanModeLayout.setVisibility(View.GONE);
                break;
            case R.id.hubsanMenuFollowBtn: //跟随
                if (!drone.airMode.isFollowMode()) {
                    openFollowMeDialog();
                } else {
                    cancelFollowMode();
                    Toast.makeText(getActivity(), "关闭跟随", Toast.LENGTH_SHORT).show();
                    LogX.e("关闭跟飞模式");
//                    isWithfly = false;
                }
                hubsanModeLayout.setVisibility(View.GONE);
                break;
            case R.id.hubsanMenuSurroundBtn: //环绕
                if (!drone.airMode.isSurroundFly()) {
                    openSurroundDialog();
                } else {
                    cancelSounnderMode();
//                    SURROUND_FLY = false;
                    Toast.makeText(getActivity(), "关闭环绕", Toast.LENGTH_SHORT).show();
                }
                hubsanModeLayout.setVisibility(View.GONE);
                break;

            case R.id.hubsanDelLine: //删除划线
                optionListener.cleanMap();
                break;
            case R.id.leftIsUnLock:
                drone.hubsanSendInstructionAir.sendModeInstruction(HubsanDroneInterfaces.AirAllModeType.HUBSAN_UNLOCK, true);
                break;
            case R.id.hubsanWayLine://开始划线
                optionListener.beginDrawWayPoin();
                break;
            case R.id.rightUpload://提交航点
                optionListener.uploadWayPoin();
                break;
            case R.id.leftWaypoint://开启航点模式
                optionListener.beginOrCloseWapPoin();
                break;
            case R.id.topMapCalibration://校准地图
                mapCalibration();
                break;
            case R.id.hubsanDelPoint://删除点
                setTool(EditorTools.TRASH);
                break;
            case R.id.hubsanFocus://添加兴趣点
                setTool(EditorTools.LIGHTHOUSE);
                break;
        }
    }

    boolean calibrationing = false;

    public void mapCalibration() {
        if (calibrationing) {
            calibrationing = false;
            topMapCalibration.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.hubsan_top_compass_normal));
        } else {
            calibrationing = true;
            topMapCalibration.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.hubsan_top_compass_pressed));
        }
        optionListener.mapCalibration(calibrationing);
    }

    boolean joystickStatus = false;

    public void showOrhintJoystick() {
        if (joystickStatus) {
            joystickStatus = false;
            hubsanLeftRocker.setImageResource(R.drawable.hubsan_501_left_rocker_btn_bg);
            optionListener.showJoystick();
        } else {
            joystickStatus = true;
            hubsanLeftRocker.setImageResource(R.drawable.hubsan_501_left_rocker_select_btn_bg);
            optionListener.showJoystick();
        }
    }

    boolean selectModleShowing = false;

    public void showSelectModle() {
        if (selectModleShowing) {
            selectModleShowing = false;
            hubsanModeLayout.setVisibility(View.GONE);
        } else {
            selectModleShowing = true;
            hubsanModeLayout.setVisibility(View.VISIBLE);
        }
    }

    //开启环绕模式
    private void openSurroundDialog() {
        final CommonDialog surroundDialog = new CommonDialog(getActivity()
                , getResources().getString(R.string.hubsan_603_surround_fly_mode)
                , getString(R.string.hubsan_501_surround_dialog_title)
                , getResources().getString(R.string.ok)
                , getResources().getString(R.string.cancel)
                , R.drawable.hubsan_501_surround_dialog_bg);
        surroundDialog.show(getActivity());
        surroundDialog.setClicklistener(new CommonDialog.OnCommonClickListenerInterface() {
            @Override
            public void doConfirm() {
                float dist = drone.airBaseParameters.getX();
                LogX.e("当前距离：" + dist);
                if (dist < 3) {
                    Toast.makeText(getActivity(), " 当前距离" + dist + "米,小于3米不能起飞", Toast.LENGTH_LONG).show();
                } else {
                    drone.hubsanSendInstructionAir.sendModeInstruction(HubsanDroneInterfaces.AirAllModeType.HUBSAN_SURROUND_FLY, true);
                    surroundDialog.dismiss();
                }
            }

            @Override
            public void doCancel() {
                surroundDialog.dismiss();
            }
        });
    }

    //开启跟随对话框
    private void openFollowMeDialog() {
        final CommonDialog followDialog = new CommonDialog(getActivity()
                , getResources().getString(R.string.hubsan_603_follow_mode)
                , getString(R.string.hubsan_501_follow_me_dialog_title)
                , getResources().getString(R.string.ok)
                , getResources().getString(R.string.cancel)
                , R.drawable.hubsan_501_follow_me_dialpg_bg);
        followDialog.show(getActivity());
        followDialog.setClicklistener(new CommonDialog.OnCommonClickListenerInterface() {
            @Override
            public void doConfirm() {
                drone.hubsanSendInstructionAir.sendModeInstruction(HubsanDroneInterfaces.AirAllModeType.HUBSAN_FOLLOW_MODE, true);
                followDialog.dismiss();
            }

            @Override
            public void doCancel() {
                followDialog.dismiss();
            }
        });
    }

    /**
     * 取消跟飞模式
     */
    public void cancelFollowMode() {
//        if (isWithfly == true) {
        drone.hubsanSendInstructionAir.sendModeInstruction(HubsanDroneInterfaces.AirAllModeType.HUBSAN_FOLLOW_MODE, false);
//        }
    }

    //取消环绕
    public void cancelSounnderMode() {
//        if (SURROUND_FLY == true) {
        drone.hubsanSendInstructionAir.sendModeInstruction(HubsanDroneInterfaces.AirAllModeType.HUBSAN_SURROUND_FLY, false);
//        }
    }

    private void cancelWaypointMode() {
//        if (wayPointFlay == true) {
        //如果正在航点模式,则取消航点模式
        drone.hubsanSendInstructionAir.sendModeInstruction(HubsanDroneInterfaces.AirAllModeType.HUBSAN_WAYPOINT_FLY, false);
//        }
    }

    /**
     * 录像状态,以及每个模式
     *
     * @param headMode
     * @param recStatus
     */
    private int airHeadMode = 0;//飞机模式

    public void airAllMode(short headMode, short recStatus) {
//        setIsRecording();
        //这点判断特别重要,首先要判断是有头无头模式下的状态
        if (drone.airConnectionStatus.isAirConnection() == false) {
            airConnectionStatus.setText("设备未连接");
        } else {
            if (drone.airGPS.getGpsNumber() < 6) {
                airConnectionStatus.setText("GPS信号弱");
            }
            if (((headMode >> 6) & 0x01) == 1) {//无头
                airHeadMode = headMode - 64;
//            setHeadModel(1);
            } else {
                airHeadMode = headMode;
//            setHeadModel(0);
            }
            updateleftWaypoint(airHeadMode);
            if (drone.airHeartBeat.isMotorStatus() == true) {//判断在电机电机未启动的时候显示 锁定
                switch (airHeadMode) {
                    case Constants.HUBSAN_ALTITUDE_HOLD://定高模式
                        airConnectionStatus.setText(HubsanApplication.getApplication().getResources().getString(R.string.hubsan_603_altitude_hold_mode));
                        break;
                    case Constants.HUBSAN_GPS_HOLD://定点模式
                        airConnectionStatus.setText(HubsanApplication.getApplication().getResources().getString(R.string.hubsan_603_gps_hold_mode));
                        break;
                    case Constants.HUBSAN_FOLLOW_MODE://跟飞模式
                        airConnectionStatus.setText(HubsanApplication.getApplication().getResources().getString(R.string.hubsan_603_follow_mode));
                        break;
                    case Constants.HUBSAN_WAYPOINT_FLY://航点模式
                        airConnectionStatus.setText(HubsanApplication.getApplication().getResources().getString(R.string.hubsan_603_waypoint_mode));
                        break;
                    case Constants.HUBSAN_RETURN_HOME://返航
                        airConnectionStatus.setText(HubsanApplication.getApplication().getResources().getString(R.string.hubsan_603_return_home_mode));
                        break;
                    case Constants.HUBSAN_MANUAL_MODE://手动模式
                        airConnectionStatus.setText(HubsanApplication.getApplication().getResources().getString(R.string.hubsan_603_manual_mode));
                        break;
                    case Constants.HUBSAN_SURROUND_FLY://环绕飞
                        airConnectionStatus.setText(HubsanApplication.getApplication().getResources().getString(R.string.hubsan_603_surround_fly_mode));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void updateleftWaypoint(int airHeadMode) {
        if (airHeadMode == Constants.HUBSAN_WAYPOINT_FLY) {
            leftWaypoint.setText("关闭");
        } else {
            leftWaypoint.setText("开启");
        }
    }

    Handler checkConnectHandle = new Handler();
    Runnable checkConnectRunnable = new Runnable() {
        @Override
        public void run() {
            checkConnectHandle.postDelayed(checkConnectRunnable, 1000);
            if (!drone.airConnectionStatus.isAirConnection()) {
                Message msg = new Message();
                msg.what = 125;
                mMyHandler.sendMessage(msg);
            }
        }
    };

    public EditorTools getTool() {
        return tool;
    }

    public void setTool(EditorTools tool) {
        this.tool = tool;
//        if (tool == EditorTools.NONE) {
//            listener.editorToolChanged(this.tool);
//        } else {
//            listener.editorToolChanged(this.tool);
//        }
    }


}
