package com.hubsan.swifts.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hubsan.joystick.R;
import com.hubsan.joystick.widgets.JoystickMovedListener;
import com.hubsan.joystick.widgets.JoystickView;
import com.hubsan.swifts.SwiftsApplication;
import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.drone.HubsanDrone;
import com.utils.Constants;
import com.utils.PreferenceUtils;

/**
 * Description 遥控
 * 摇杆
 *
 * @author ShengKun.Cheng
 * @date 2015年7月28日
 * @see [class/class#method]
 * @since [product/model]
 */
public class RCFragment extends Fragment{

    private JoystickView joystickL;
    private TextView textViewAileron;
    private TextView textViewElevator;
    private HubsanDrone drone;
    private SwiftsApplication app;
    private boolean usOrJp;
    /*操作模式*/
    private int operatingMode;
    private boolean isStaticMode = false;
    private View view;
    private OnRockerClickListener onRockerClickListener;


    public interface OnRockerClickListener {
        void notifyRocker();
    }
    public void setOnRockerClickListener(OnRockerClickListener listener){
        onRockerClickListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hubsan_501_fragment_rc, container, false);
        initView(view);
        return view;
    }


//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        onRockerClickListener = (OnRockerClickListener) activity;
//
//    }

    public void initView(View view) {
        app = (SwiftsApplication) getActivity().getApplication();
        this.drone = app.drone;
        TextView textViewThrottle = (TextView) view.findViewById(R.id.textViewRCThrottle);
        textViewThrottle.setText("Throttle:0");

        TextView textViewRudder = (TextView) view.findViewById(R.id.textViewRCRudder);
        textViewRudder.setText("Rudder:0");

        textViewElevator = (TextView) view.findViewById(R.id.textViewRCElevator);
        textViewElevator.setText("Elevator:0");

        textViewAileron = (TextView) view.findViewById(R.id.textViewRCAileron);
        textViewAileron.setText("Aileron:0");

        joystickL = (JoystickView) view.findViewById(R.id.joystickViewL);
        JoystickView joystickR = (JoystickView) view.findViewById(R.id.joystickViewR);
        joystickL.setOnJostickMovedListener(lJoystick);
        joystickR.setOnJostickMovedListener(rJoystick);

    }



    //----------------------------自由模式下的摇杆--------------------------------------
    //左
    JoystickMovedListener lJoystick = new JoystickMovedListener() {

        @Override
        public void OnReturnedToCenter() {
        }

        @Override
        public void OnReleased() {
        }

        @Override
        public void OnMoved(float pans, float tilts) {
            //摇杆操作模式
            operatingMode = PreferenceUtils.getPrefInt(HubsanApplication.getApplication(), Constants.SETTING_REMOTE_MODEL, 3);
            usOrJp = PreferenceUtils.getPrefBoolean(getActivity(), Constants.SETTING_REMOTE_US_JP, true);
            drone.joystick.setJoystickMode(usOrJp);
            //转换为负数
            if (tilts > 0) {
                tilts = -tilts;
            } else {
                tilts = Math.abs(tilts);
            }
//            if (app.bluetoothNotify.ismConnected() == false) {
                drone.joystick.setRockerLeftValue(pans, tilts, operatingMode, usOrJp);
//            }
        }
    };

    //右
    JoystickMovedListener rJoystick = new JoystickMovedListener() {

        @Override
        public void OnReturnedToCenter() {
        }

        @Override
        public void OnReleased() {
        }

        @Override
        public void OnMoved(float pans, float tilts) {
            operatingMode = PreferenceUtils.getPrefInt(HubsanApplication.getApplication(), Constants.SETTING_REMOTE_MODEL, 3);
            usOrJp = PreferenceUtils.getPrefBoolean(getActivity(), Constants.SETTING_REMOTE_US_JP, true);
            drone.joystick.setJoystickMode(usOrJp);
            if (tilts > 0) {
                tilts = -tilts;
            } else {
                tilts = Math.abs(tilts);//将负数变为正数
            }
//            if (app.bluetoothNotify.ismConnected() == false) {
                drone.joystick.setRockerRightValue(pans, tilts, operatingMode, usOrJp);
//            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        usOrJp = PreferenceUtils.getPrefBoolean(getActivity(), Constants.SETTING_REMOTE_US_JP, true);//美国手，日本手
        isStaticMode = PreferenceUtils.getPrefBoolean(getActivity(), Constants.SETTING_REMOTE_OPEN_CLOSE, false);
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        joystickL.recycleBitmap();
//        drone.isUnLockStatus.setUnLockCloseWhile(false);
        drone.joystick.setRudder(0);
        drone.joystick.setAileron(0);
        drone.joystick.setThrottle(0);
        drone.joystick.setElevator(0);
        drone.joystick.setRudderRaw(0);
        drone.joystick.setAileronRaw(0);
        drone.joystick.setThrottleRaw(0);
        drone.joystick.setElevatorRaw(0);
        System.gc();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
