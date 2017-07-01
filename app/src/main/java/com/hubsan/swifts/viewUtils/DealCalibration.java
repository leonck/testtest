//package com.hubsan.swifts.viewUtils;
//
//import android.app.Activity;
//import android.app.ActivityManager;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//
//import com.hubsan.swifts.TestShow.TestShowActivity;
//import com.hubsan.swifts.activitis.EquipmentCertificationDialog;
//import com.hubsan.swifts.activitis.MainActivity;
//import com.hubsan.swifts.dialogs.CalibrationCompass;
//import com.hubsansdk.application.HubsanApplication;
//import com.hubsansdk.drone.HubsanDrone;
//import com.hubsansdk.drone.HubsanDroneInterfaces;
//import com.hubsansdk.utils.LogX;
//import com.utils.Constants;
//import com.utils.PreferenceUtils;
//import com.utils.Utils;
//
//import java.lang.ref.WeakReference;
//import java.util.List;
//
///**
// *
// * Created by Leon.Li on 2017/6/21.
// */
//
//public class DealCalibration {
//    CalibrationCompass calibrationCompass;
//    int par2, par6;
//    boolean isShowCalibration, isShowCurrent;
//    HubsanDrone drone;
//    public DealCalibration(HubsanDrone drone){
//        this.drone = drone;
//    }
//
//    public void setCompassCalibration(int par2, int par6) {
//        int number = PreferenceUtils.getPrefInt(HubsanApplication.getApplication(), Constants.HUBSAN_FIRST_501_START, 0);
////        if (number == 0) {
////            return;
////        }
//        this.par2 = par2;
//        this.par6 = par6;
//        boolean isForegrounds507 = isForeground(HubsanApplication.getApplication(), "com.csk.hbsdrone.activities.Hubsan507AActivity");
//        boolean isForegrounds = isForeground(HubsanApplication.getApplication(), "com.hubsan.swifts.activitis.MainActivity");
//        boolean isForegroundCalibration = isForeground(HubsanApplication.getApplication(), "com.csk.hbsdrone.dialogs.CalibrationCompass");
//        boolean settingDialog = Utils.isForeground(HubsanApplication.getApplication(), "com.csk.hbsdrone.dialogs.SettingDialog");
//
//        if (par2 == 0 && par6 == 0) {
//            isShowCalibration = false;
//            isShowCurrent = false;
//            calibHandler.removeCallbacks(calibRunnable);
//            dismissCalibration();
//        }
//        if (isForegrounds || isForegrounds507) {
//            if (isShowCalibration == false) {
//                if (isShowCurrent == false) {
//                    if (par2 != 0 && par6 != 0) {
//                        calibHandler.postDelayed(calibRunnable, 1800);
//                        isShowCurrent = true;
//                    }
//                }
//            } else {
//                if (calibrationCompass != null && calibrationCompass.isShowing()) {
//                    if (par2 == 0 && par6 == 0) {
//                        calibrationCompass.removerRunnable();
//                        calibrationCompass.dismiss();
//                        calibrationCompass = null;
//                        LogX.e("********setCompassCalibration**********");
//                        drone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_CALIBRATIONCOMPASS_OVER);
//                    } else {
//                        calibrationCompass.setCalibrationTag(par2, par6);
//                    }
//                }
//            }
//        }
//    }
//
//    private Handler calibHandler = new Handler();
//    Runnable calibRunnable = new Runnable() {
//        @Override
//        public void run() {
//            Message msg = new Message();
//            msg.what = 120;
//            if (mMyHandler != null) {
//                mMyHandler.sendMessage(msg);
//            }
//        }
//    };
//
//    /**
//     * 判断某个界面是否在前台
//     *
//     * @param context
//     * @param className 某个界面名称
//     */
//    private boolean isForeground(Context context, String className) {
//        if (context == null || TextUtils.isEmpty(className)) {
//            return false;
//        }
//
//        ActivityManager am = (ActivityManager) HubsanApplication.getApplication().getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
//        if (list != null && list.size() > 0) {
//            ComponentName cpn = list.get(0).topActivity;
//            if (className.equals(cpn.getClassName())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private void dismissCalibration() {
//        if (calibrationCompass != null && calibrationCompass.isShowing()) {
//            calibrationCompass.removerRunnable();
//            calibrationCompass.dismiss();
//            calibrationCompass = null;
////            if (airIsConnection == true) {
//            LogX.e("********dismissCalibration**********");
//            drone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_CALIBRATIONCOMPASS_OVER);
////            }
//        }
//    }
//
//
//
//}
