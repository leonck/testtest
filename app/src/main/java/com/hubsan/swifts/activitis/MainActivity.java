package com.hubsan.swifts.activitis;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.hubsan.swifts.R;
import com.hubsan.swifts.dialogs.CalibrationCompass;
import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.application.HubsanHandleMessageApp;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces;
import com.hubsansdk.utils.LogX;
import com.utils.Constants;
import com.utils.ConstantsPermission;
import com.utils.PreferenceUtils;
import com.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends BasePermissionActivity implements HubsanDroneInterfaces.OnDroneListener {
    FragmentManager fragmentManager;
    MainFragment mainFragment;
    MainPresenter presenter;
    private HubsanDrone drone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.frame);
        ButterKnife.bind(this);
        fragmentManager = getFragmentManager();
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean isLocartion = isPermissionsAllGranted(permissions,
                ConstantsPermission.QUEST_CODE_LOCTION);
        LogX.e("isLocartion权限:" + isLocartion);
        drone = ((HubsanHandleMessageApp) HubsanApplication.getApplication()).drone;
        drone.events.addDroneListener(this);
        initView();
        if (mMyHandler == null) {
            mMyHandler = new MyHandler(this);
        }
        getWindowsWH();
    }

    @Override
    public void onCreateView() {

    }

    public void initView() {
        if (mainFragment == null) {
            mainFragment = new MainFragment();
            fragmentManager.beginTransaction().add(R.id.container, mainFragment, "mainFragment").commit();
//            gestureMapFragment.setOnPathFinishedListener(this);
            presenter = new MainPresenter(this, mainFragment);
        }
    }

    @Override
    public void onDroneEvent(HubsanDroneInterfaces.DroneEventsType event, HubsanDrone drone) {
        switch (event) {
            case HUBSAN_COMPASS_CALIBRATION_HORIZONTAL://地磁水平校准
                Message cHMsg = new Message();
                cHMsg.what = 131;
                if (mMyHandler != null) {
                    mMyHandler.sendMessage(cHMsg);
                }
                break;
            case HUBSAN_COMPASS_CALIBRATION_VERTICAL://地磁水平校准
                Message cHMsg2 = new Message();
                cHMsg2.what = 132;
                if (mMyHandler != null) {
                    mMyHandler.sendMessage(cHMsg2);
                }
                break;
            case HUBSAN_COMPASS_CALIBRATION_OVER://地磁水平校准
                Message cHMsg3 = new Message();
                cHMsg3.what = 133;
                if (mMyHandler != null) {
                    mMyHandler.sendMessage(cHMsg3);
                }
                break;
            case HUBSAN_BATTERY:
                Message batterymsg = new Message();
                batterymsg.what = 134;
                if (mMyHandler != null) {
                    mMyHandler.sendMessage(batterymsg);
                }
                break;
            case HUBSAN_BASE_VALUE:
                Message basemsg = new Message();
                basemsg.what = 135;
                if (mMyHandler != null) {
                    mMyHandler.sendMessage(basemsg);
                }
                break;
        }
    }

    CalibrationCompass calibrationCompass;
    int par2, par6;
    boolean isShowCalibration, isShowCurrent;

    private void setCompassCalibration(int par2, int par6) {
        int number = PreferenceUtils.getPrefInt(HubsanApplication.getApplication(), Constants.HUBSAN_FIRST_501_START, 0);
//        if (number == 0) {
//            return;
//        }
        this.par2 = par2;
        this.par6 = par6;
        boolean isForegrounds507 = isForeground(HubsanApplication.getApplication(), "com.csk.hbsdrone.activities.Hubsan507AActivity");
        boolean isForegrounds = isForeground(HubsanApplication.getApplication(), "com.hubsan.swifts.activitis.MainActivity");
        boolean isForegroundCalibration = isForeground(HubsanApplication.getApplication(), "com.csk.hbsdrone.dialogs.CalibrationCompass");
        boolean settingDialog = Utils.isForeground(HubsanApplication.getApplication(), "com.csk.hbsdrone.dialogs.SettingDialog");

        if (par2 == 0 && par6 == 0) {
            isShowCalibration = false;
            isShowCurrent = false;
            calibHandler.removeCallbacks(calibRunnable);
            dismissCalibration();
        }
        if (isForegrounds || isForegrounds507) {
            if (isShowCalibration == false) {
                if (isShowCurrent == false) {
                    if (par2 != 0 && par6 != 0) {
                        calibHandler.postDelayed(calibRunnable, 1800);
                        isShowCurrent = true;
                    }
                }
            } else {
                if (calibrationCompass != null && calibrationCompass.isShowing()) {
                    if (par2 == 0 && par6 == 0) {
                        calibrationCompass.removerRunnable();
                        calibrationCompass.dismiss();
                        calibrationCompass = null;
                        LogX.e("********setCompassCalibration**********");
                        drone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_CALIBRATIONCOMPASS_OVER);
                    } else {
                        calibrationCompass.setCalibrationTag(par2, par6);
                    }
                }
            }
        }
    }

    private Handler calibHandler = new Handler();
    Runnable calibRunnable = new Runnable() {
        @Override
        public void run() {
            Message msg = new Message();
            msg.what = 120;
            if (mMyHandler != null) {
                mMyHandler.sendMessage(msg);
            }
        }
    };

    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className 某个界面名称
     */
    private boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) HubsanApplication.getApplication().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void dismissCalibration() {
        if (calibrationCompass != null && calibrationCompass.isShowing()) {
            calibrationCompass.removerRunnable();
            calibrationCompass.dismiss();
            calibrationCompass = null;
//            if (airIsConnection == true) {
            LogX.e("********dismissCalibration**********");
            drone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_CALIBRATIONCOMPASS_OVER);
//            }
        }
    }

    MyHandler mMyHandler;

    class MyHandler extends Handler {
        WeakReference<Activity> mActivityReference;

        MyHandler(Activity activity) {
            mActivityReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = mActivityReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case 131://水平校准
//                        setCompassCalibration(1, 1);
                        setCompassCalibration(1, 1);
                        LogX.e("水平地磁校验");
                        break;
                    case 132://垂直校准
                        setCompassCalibration(1, 2);
                        LogX.e("垂直地磁校验");
                        break;
                    case 133://校准完成
                        setCompassCalibration(0, 0);
                        LogX.e("地磁校验完成");
                        if (!isForeground(HubsanApplication.getApplication(), "com.hubsan.swifts.activitis.EquipmentCertificationDialog"))
                            certification();
                        break;
                    case 120:
                        if (par2 != 0 && par6 != 0) {
                            try {
//                                if (isScreenChange() == true) {
                                calibrationCompass = new CalibrationCompass(MainActivity.this);
                                calibrationCompass.setCalibrationTag(par2, par6);
                                calibrationCompass.show(MainActivity.this);
                                calibrationCompass.setCalibrationClicklistener(new CalibrationCompass.OnClickListenerInterface() {
                                    @Override
                                    public void doCancel() {
                                        isShowCalibration = true;
                                        calibrationCompass.removerRunnable();
                                        calibrationCompass.dismiss();
                                        calibrationCompass = null;
                                    }
                                });
                                isShowCalibration = true;
//                                } else {
//                                    isShowCalibration = false;
//                                    isShowCurrent = false;
//                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            dismissCalibration();
                        }
                        calibHandler.removeCallbacks(calibRunnable);
                        break;
                    case 134:
//                        barrty.setText(drone.battery.getBatteryValue() + "%");
                        break;
                    case 135:

                        break;
                }
            }
        }
    }

    public void certification() {
        startActivity(new Intent().setClass(this, EquipmentCertificationDialog.class));
    }

    /**
     * 获取窗口大小
     */
    public void getWindowsWH() {
        // 获取屏幕宽高（方法1）
        WindowManager manager = getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int screenWidth = outMetrics.widthPixels;
        int screenHeight = outMetrics.heightPixels;
        PreferenceUtils.setPrefInt(this, "screenWidth", screenWidth);
        PreferenceUtils.setPrefInt(this, "screenHeight", screenHeight);
        LogX.e(screenWidth + "    " + screenHeight);
    }

}
