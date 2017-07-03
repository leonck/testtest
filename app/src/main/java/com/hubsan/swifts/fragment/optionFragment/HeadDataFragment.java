package com.hubsan.swifts.fragment.optionFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.MAVLink.hubsan.msg_hubsan_quad_status;
import com.hubsan.swifts.R;
import com.hubsan.swifts.SwiftsApplication;
import com.hubsan.swifts.fragment.OptionFragment;
import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.application.HubsanHandleMessageApp;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HeadDataFragment extends Fragment {
    View view;
    HubsanDrone drone;
    OptionFragment.OptionListener optionListener;
    SwiftsApplication app;

    @BindView(R.id.airhight)
    TextView airhight;
    @BindView(R.id.speak)
    TextView speak;
    @BindView(R.id.airConnectionStatus)
    TextView airConnectionStatus;
    @BindView(R.id.gpsnumber)
    TextView gpsnumber;
    @BindView(R.id.battery)
    TextView battery;
    @BindView(R.id.headView)
    LinearLayout headView;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_option_head_view, container, false);
        drone = ((HubsanHandleMessageApp) HubsanHandleMessageApp.getApplication()).drone;
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setBattery() {
        battery.setText(drone.battery.getBatteryValue() + "22%");
    }

    public void setSpeak() {
        speak.setText(drone.airBaseParameters.getX() + "/" + drone.airBaseParameters.getVx());
    }

    public void setAirhight() {
        airhight.setText("" + drone.airBaseParameters.getAltitude());
    }

    public void setGpsnumber() {
        gpsnumber.setText("" + drone.airGPS.getGpsNumber());
    }
    public void setAirConnectionStatus() {
        airConnectionStatus.setText("设备未连接");
    }


    /**
     * 录像状态,以及每个模式
     *
     * @param headMode
     * @param recStatus
     */
    private int airHeadMode = 0;//飞机模式

    public void airAllMode() {
        msg_hubsan_quad_status msgHubsanQuadStatus = drone.airMode.getQuad_status();
        short headMode = msgHubsanQuadStatus.quad_mode_status;
        short recStatus = msgHubsanQuadStatus.recording_status;
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
//            updateleftWaypoint(airHeadMode);
            if (drone.airHeartBeat.isMotorStatus() == true) {//判断在电机电机未启动的时候显示 锁定
                switch (airHeadMode) {
                    case Constants.HUBSAN_ALTITUDE_HOLD://定高模式
                        airConnectionStatus.setText(HubsanApplication.getApplication().getResources().getString(com.hubsan.swifts.R.string.hubsan_603_altitude_hold_mode));
                        break;
                    case Constants.HUBSAN_GPS_HOLD://定点模式
                        airConnectionStatus.setText(HubsanApplication.getApplication().getResources().getString(com.hubsan.swifts.R.string.hubsan_603_gps_hold_mode));
                        break;
                    case Constants.HUBSAN_FOLLOW_MODE://跟飞模式
                        airConnectionStatus.setText(HubsanApplication.getApplication().getResources().getString(com.hubsan.swifts.R.string.hubsan_603_follow_mode));
                        break;
                    case Constants.HUBSAN_WAYPOINT_FLY://航点模式
                        airConnectionStatus.setText(HubsanApplication.getApplication().getResources().getString(com.hubsan.swifts.R.string.hubsan_603_waypoint_mode));
                        break;
                    case Constants.HUBSAN_RETURN_HOME://返航
                        airConnectionStatus.setText(HubsanApplication.getApplication().getResources().getString(com.hubsan.swifts.R.string.hubsan_603_return_home_mode));
                        break;
                    case Constants.HUBSAN_MANUAL_MODE://手动模式
                        airConnectionStatus.setText(HubsanApplication.getApplication().getResources().getString(com.hubsan.swifts.R.string.hubsan_603_manual_mode));
                        break;
                    case Constants.HUBSAN_SURROUND_FLY://环绕飞
                        airConnectionStatus.setText(HubsanApplication.getApplication().getResources().getString(com.hubsan.swifts.R.string.hubsan_603_surround_fly_mode));
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
