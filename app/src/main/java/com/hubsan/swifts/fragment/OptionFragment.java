package com.hubsan.swifts.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
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
import com.hubsan.swifts.bean.CommonStatus;
import com.hubsan.swifts.fragment.optionFragment.HeadDataFragment;
import com.hubsan.swifts.widgets.CommonDialog;
import com.hubsan.swifts.widgets.StrokeText;
import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.application.HubsanHandleMessageApp;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces;
import com.hubsansdk.utils.Constants;
import com.hubsansdk.utils.LogX;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

public class OptionFragment extends Fragment {
    HubsanDrone drone;
    OptionListener optionListener;
    SwiftsApplication app;
    private CommonStatus.EditorTools tool = CommonStatus.EditorTools.NONE;
    FragmentManager fragmentManager;
    HeadDataFragment headDataFragment;

    public interface OptionListener {
        void cleanMap();

        void showJoystick();

        void beginDrawWayPoin();

        void uploadWayPoin();

        void beginOrCloseWapPoin();

        void mapCalibration(boolean status);
    }

    View mView;
    Unbinder unbind;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        mView = inflater.inflate(R.layout.fragment_option, viewGroup, false);
        drone = ((HubsanHandleMessageApp) HubsanHandleMessageApp.getApplication()).drone;
        unbind = ButterKnife.bind(this, mView);
        fragmentManager = getChildFragmentManager();

        app = (SwiftsApplication) HubsanApplication.getApplication();
        if (mMyHandler == null) {
            mMyHandler = new MyHandler(this);
        }
        initView();
        checkConnectHandle.postDelayed(checkConnectRunnable, 1000);
        initHeadDataView();
        return mView;
    }

    public void initHeadDataView() {
        if (headDataFragment == null) {
            headDataFragment = new HeadDataFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_option_head_view, headDataFragment).commit();
        }
    }

    public void initView() {
        hubsanTopRockerLatLon.setText(drone.gpsManager.mLat + "," + drone.gpsManager.mLon);
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
                    case 4:
                        hubsanTopAirLatLon.setText("(" + drone.airGPS.getAirLat() + "," + drone.airGPS.getAirLon() + ")");
                        break;
                    case 123:
                        hubsanTopRockerLatLon.setText(drone.gpsManager.mLat + "," + drone.gpsManager.mLon);
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
                     case 129:
//                         setRockerData();
                        break;

                }
            }
        }
    }

    public void rockerInstructions(Message msg){
        mMyHandler.sendMessage(msg);
    }
    /**
     * 设置显示摇杆数据
     */
    public void setRockerData() {
        hubsanRockerThrottle.setText("T:1 " + (int) (drone.joystick.getThrottleRaw() * 0.1) + "%");
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
                setTool(CommonStatus.EditorTools.TRASH);
                break;
            case R.id.hubsanFocus://添加兴趣点
                setTool(CommonStatus.EditorTools.LIGHTHOUSE);
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

    public CommonStatus.EditorTools getTool() {
        return tool;
    }

    public void setTool(CommonStatus.EditorTools tool) {
        this.tool = tool;
//        if (tool == EditorTools.NONE) {
//            listener.editorToolChanged(this.tool);
//        } else {
//            listener.editorToolChanged(this.tool);
//        }
    }

    public void setBattery() {
        headDataFragment.setBattery();
    }

    public void setSpeak() {
        headDataFragment.setSpeak();
    }

    public void setAirhight() {
        headDataFragment.setAirhight();
    }

    public void setGpsnumber() {
        headDataFragment.setGpsnumber();
    }

    public void setAirAllMode() {
        headDataFragment.airAllMode();
    }

    public void setAirConnectionStatus() {
        headDataFragment.setAirConnectionStatus();
    }

    public void setHubsanTopAirLatLon() {
        hubsanTopAirLatLon.setText("(" + drone.airGPS.getAirLat() + "," + drone.airGPS.getAirLon() + ")");
    }

    public void setHubsanTopRockerLatLon() {
        hubsanTopRockerLatLon.setText(drone.gpsManager.mLat + "," + drone.gpsManager.mLon);
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbind.unbind();
//        checkConnectHandle.removeCallbacks(checkConnectRunnable);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbind.unbind();
        checkConnectHandle.removeCallbacks(checkConnectRunnable);
    }
}
