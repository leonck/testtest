//
//package com.hubsan.swifts.fragment.mission;
//
//import android.view.View;
//import android.widget.CompoundButton;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//
//import com.csk.hbsdrone.R;
//import com.csk.hbsdrone.drone.variables.mission.waypoints.Waypoint;
//import com.csk.hbsdrone.helpers.units.Altitude;
//import com.csk.hbsdrone.helpers.units.Speed;
//import com.csk.hbsdrone.utils.Constants;
//import com.csk.hbsdrone.utils.PreferenceUtils;
//import com.csk.hbsdrone.widgets.SeekBarWithText.SeekBarWithText;
//import com.csk.hbsdrone.widgets.SeekBarWithText.SeekBarWithText.OnTextSeekBarChangedListener;
//
//public class MissionWaypointFragment extends MissionDetailFragment implements OnTextSeekBarChangedListener, OnCheckedChangeListener {
//
//    private SeekBarWithText altitudeSeekBar;
//
//    private SeekBarWithText delaySeekBar, waypointSpeed;
//
//    private float maxAlititude = 0;
//
//    private float maxDelay = 0;
//
////    private float maxYawAngle = 100.0f;
//
//    private float maxSpeed = 5;
//
//    @Override
//    protected int getResource() {
//
//        return R.layout.hubsan_fragment_editor_detail_waypoint;
//    }
//
//    private void getPreferences() {
//
//        String maxH = String.valueOf(PreferenceUtils.getPrefInt(getActivity(), Constants.SETTING_FLIGHTDATA_MAX_HEIGHT, 300)).toUpperCase().toString();
//        String waypointTime = String.valueOf(PreferenceUtils.getPrefInt(getActivity(), Constants.SETTING_FLIGHTDATA_WAYPOINT_STAYTIME, 600)).toUpperCase().toString();
//        maxDelay = Float.parseFloat(waypointTime);//悬停时间
//        maxAlititude = Float.parseFloat(maxH);//默认高度
//    }
//
//    @Override
//    protected void setupViews(View view) {
//
//        super.setupViews(view);
//        typeSpinner.setSelection(commandAdapter.getPosition(MissionItemTypes.WAYPOINT));
//        getPreferences();
//        Waypoint item = (Waypoint) this.item;
//        float mMaxAlititude = (float) item.getAltitude().valueInMeters();
//        //主要是为了判断当前的航点是否和最大值相等，如果大于最大值则在这里处理
//        if (maxAlititude < mMaxAlititude) {
//            this.maxAlititude = mMaxAlititude;
//        }
//        //悬停时间
//        delaySeekBar = (SeekBarWithText) view.findViewById(R.id.waypointDelay);
//        delaySeekBar.setValue(item.getDelay());
//        delaySeekBar.setOnChangedListener(this);
//        delaySeekBar.setMinMaxInc(0, maxDelay, 1);
//
//        //默认高度
//        altitudeSeekBar = (SeekBarWithText) view.findViewById(R.id.altitudeView);
//        altitudeSeekBar.setMinMaxInc(0, maxAlititude, 1);
//        altitudeSeekBar.setValue(item.getAltitude().valueInMeters());
//        altitudeSeekBar.setOnChangedListener(this);
//
//        //飞行速度
//        waypointSpeed = (SeekBarWithText) view.findViewById(R.id.waypointSpeed);
//        int speed = (int) item.getSpeed().valueInMeters();
//        waypointSpeed.setMinMaxInc(1, maxSpeed, 1);
//        waypointSpeed.setValue(item.getSpeed().valueInMeters());//获取默认值
//        waypointSpeed.setMinMaxInc(1, maxSpeed, 1);
//        waypointSpeed.setOnChangedListener(this);
//
////        steerSeekBar = (SeekBarWithText)view.findViewById(R.id.waypointSteer);
////        steerSeekBar.setMinMaxInc(0, maxYawAngle, 1);
////        steerSeekBar.setValue(item.getYawAngle());
////        steerSeekBar.setOnChangedListener(this);
//    }
//
//
//    @Override
//    public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//
//        onSeekBarChanged();
//    }
//
//    @Override
//    public void onSeekBarChanged() {
//
//        //得到回调的值
//        Waypoint item = (Waypoint) this.item;
//        item.setAltitude(new Altitude(altitudeSeekBar.getValue()));
//        item.setDelay((float) delaySeekBar.getValue());
//        float ss = (float) (waypointSpeed.getValue()) * 10;
//        item.setSpeed(new Speed(waypointSpeed.getValue()));
//
////        item.setYawAngle((float)steerSeekBar.getValue());
//    }
//
//}
