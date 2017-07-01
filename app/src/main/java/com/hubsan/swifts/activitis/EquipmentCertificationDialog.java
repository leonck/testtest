package com.hubsan.swifts.activitis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hubsan.swifts.R;
import com.hubsan.swifts.SwiftsApplication;
import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces;
import com.utils.Constants;
import com.utils.PreferenceUtils;

/**
 * Description
 * 设备认证
 *
 * @author shengkun.cheng
 * @date 2016/9/1
 * @see [class/class#method]
 * @since [product/model]
 */
public class EquipmentCertificationDialog extends Activity implements HubsanDroneInterfaces.OnDroneListener {

    private Button hubsanECCancel, hubsanECOk, hubsanCancelEC, hubsanECCleanAllList, hubsanECCleanAllCancel, hubsanECOtherOk;
    private LinearLayout hubsanECOneLay, hubsanECTwoLay, hubsanECThreeLay;
    private int timing = 0, time501A = 8, time507A = 10;
    private TextView hubsanECContext;
    private HubsanDrone drone;
    private SwiftsApplication app;
    private int receStatus;
    private int sendStatus;
    private boolean mAuthentication;
    private String airName;
    private int notifyTime = 0, notifyTimeOk = 0, notifyTimeFailed = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        Utils.hideNavigationBar(EquipmentCertificationDialog.this);
        super.onCreate(savedInstanceState);
        //点击空白区域不消失
        setFinishOnTouchOutside(false);
        setContentView(R.layout.hubsan_activity_equipment_certification_dialog);
        initWindow();
        init();
    }

    /**
     * 初始化窗口大小
     */
    public void initWindow() {
        app = (SwiftsApplication) HubsanApplication.getApplication();
        this.drone = app.drone;
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
        if (getResources().getBoolean(R.bool.portrait_only)) {//手机
            p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的1.0
            p.width = (int) (d.getWidth() * 0.5); // 宽度设置为屏幕的0.8
        } else {//平板
            p.height = (int) (d.getHeight() * 0.5); // 高度设置为屏幕的1.0
            p.width = (int) (d.getWidth() * 0.5); // 宽度设置为屏幕的0.8
        }
        p.alpha = 1.0f; // 设置本身透明度
        p.dimAmount = 0.0f; // 设置黑暗度
        getWindow().setAttributes(p); // 设置生效
        getWindow().setGravity(Gravity.CENTER); // 设置靠右对齐
        drone.events.addDroneListener(this);
        airName = PreferenceUtils.getPrefString(EquipmentCertificationDialog.this, Constants.HUBSAN_AIR_NAME, "");
    }

    /**
     * 初始化
     */
    private void init() {
        hubsanECContext = (TextView) findViewById(R.id.hubsanECContext);//显示的内容需要更换
        hubsanECOneLay = (LinearLayout) findViewById(R.id.hubsanECOneLay);
        hubsanECOk = (Button) findViewById(R.id.hubsanECOk);
        hubsanECCancel = (Button) findViewById(R.id.hubsanECCancel);
        hubsanECTwoLay = (LinearLayout) findViewById(R.id.hubsanECTwoLay);
        hubsanECOtherOk = (Button) findViewById(R.id.hubsanECOtherOk);
        hubsanECThreeLay = (LinearLayout) findViewById(R.id.hubsanECThreeLay);
        hubsanCancelEC = (Button) findViewById(R.id.hubsanCancelEC);
        hubsanECCleanAllList = (Button) findViewById(R.id.hubsanECCleanAllList);
        hubsanECCleanAllCancel = (Button) findViewById(R.id.hubsanECCleanAllCancel);

        hubsanECCancel.setOnClickListener(onClickListener);
        hubsanECOk.setOnClickListener(onClickListener);
        hubsanECOtherOk.setOnClickListener(onClickListener);
        hubsanCancelEC.setOnClickListener(onClickListener);
        hubsanECCleanAllList.setOnClickListener(onClickListener);
        hubsanECCleanAllCancel.setOnClickListener(onClickListener);
        notifyTime = 0;
        boolean isEC = drone.airRelayEquipmentCertification.isRepeaterEquipmentCertification();
        boolean isAirEC = drone.airRelayEquipmentCertification.isAirEquipmentCertification();
//        Utils.doLog("认证 中继器:" + isEC + " ,飞机:" + isAirEC + " ,飞机或者中继器:" + drone.wifiBean.getIsAir());
        if (drone.airConnectionStatus.getConnectionType() == 0) {
            if (isAirEC == true) {
                //表示当前飞机已认证
                hubsanECContext.setText(getResources().getString(R.string.hubsan_501_setting_other_equipment_certification_cancel_context));
                hubsanECTwoLay.setVisibility(View.GONE);
                hubsanECOneLay.setVisibility(View.GONE);
                hubsanECThreeLay.setVisibility(View.VISIBLE);
            } else {
                hubsanECContext.setText(getResources().getString(R.string.hubsan_501_setting_other_equipment_certification_context));
                hubsanECTwoLay.setVisibility(View.GONE);
                hubsanECOneLay.setVisibility(View.VISIBLE);
                hubsanECThreeLay.setVisibility(View.GONE);
            }
        } else {
            if (isEC == true) {//中继器
                hubsanECContext.setText(getResources().getString(R.string.hubsan_501_setting_other_equipment_certification_cancel_context));
                hubsanECTwoLay.setVisibility(View.GONE);
                hubsanECOneLay.setVisibility(View.GONE);
                hubsanECThreeLay.setVisibility(View.VISIBLE);
            } else {
                hubsanECContext.setText(getResources().getString(R.string.hubsan_501_setting_other_equipment_certification_context));
                hubsanECTwoLay.setVisibility(View.GONE);
                hubsanECOneLay.setVisibility(View.VISIBLE);
                hubsanECThreeLay.setVisibility(View.GONE);
            }
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.hubsanECCancel:
                    EquipmentCertificationDialog.this.finish();
                    break;
                case R.id.hubsanECOk:
                    //发送认证的命令
                    hubsanECContext.setText(getResources().getString(R.string.hubsan_501_setting_other_equipment_certification_ing));
                    hubsanECTwoLay.setVisibility(View.GONE);
                    hubsanECOneLay.setVisibility(View.GONE);
                    hubsanECThreeLay.setVisibility(View.GONE);
                    sendEquipmentInstruction(HubsanDroneInterfaces.BindingType.HUBSAN_BINGDING);
                    break;
                case R.id.hubsanCancelEC://解除认证按钮
                    hubsanECContext.setText(getResources().getString(R.string.hubsan_501_setting_other_equipment_certification_make_ing));
                    hubsanECTwoLay.setVisibility(View.GONE);
                    hubsanECOneLay.setVisibility(View.GONE);
                    hubsanECThreeLay.setVisibility(View.GONE);
                    sendEquipmentInstruction(HubsanDroneInterfaces.BindingType.HUBSAN_CANCEL_BINDING);
                    break;
                case R.id.hubsanECCleanAllList://清空所有列表按钮
                    hubsanECContext.setText(getResources().getString(R.string.hubsan_501_setting_other_equipment_certification_make_ing));
                    hubsanECTwoLay.setVisibility(View.GONE);
                    hubsanECOneLay.setVisibility(View.GONE);
                    hubsanECThreeLay.setVisibility(View.GONE);
                    sendEquipmentInstruction(HubsanDroneInterfaces.BindingType.HUBSAN_CLEAN_ALL_BINDING);
                    break;
                case R.id.hubsanECCleanAllCancel://取消
                    EquipmentCertificationDialog.this.finish();
                    break;

                case R.id.hubsanECOtherOk://解除认证后显示的按钮
                    notifyTimeOk = notifyTimeOk + 1;
                    if (notifyTimeOk <= 1) {
                        EquipmentCertificationDialog.this.finish();
                    }
                    break;
            }
        }
    };

    /**
     * 绑定的设置
     *
     * @param bindingType
     */
    public void sendEquipmentInstruction(HubsanDroneInterfaces.BindingType bindingType) {
        switch (bindingType) {
            case HUBSAN_BINGDING:
                drone.airRelayEquipmentCertification.setAuthentication(false);
                if (drone.airConnectionStatus.getConnectionType() == 0) {
                    //飞机
                    sendAirEquipment(0);
                } else {
                    //中继器
                    sendEquipment(1);
                }
                break;
            case HUBSAN_CANCEL_BINDING:
                drone.airRelayEquipmentCertification.setAuthentication(false);
                if (drone.airConnectionStatus.getConnectionType() == 0) {
                    //飞机
                    sendAirEquipment(3);
//                    myDrone.airRelayEquipmentCertification.sendAirEquipment(2);
//                    myDrone.airRelayEquipmentCertification.sendEquipment(2);
                } else {
                    sendAirEquipment(3);
//                    myDrone.airRelayEquipmentCertification.sendAirEquipment(2);
//                    myDrone.airRelayEquipmentCertification.sendEquipment(2);
                }
                break;
            case HUBSAN_CLEAN_ALL_BINDING:
                drone.airRelayEquipmentCertification.setAuthentication(false);
                sendAirEquipment(3);
                break;
        }
    }


    /**
     * @param status 0:绑定飞机 1:绑定中继器 2:解除绑定 3: 清空所有绑定
     */
    public void sendAirEquipment(int status) {
        timing = 0;
        timeHandler.removeCallbacks(mRunnable);
        this.sendStatus = status;
        drone.mavLinkSendMessage.sendAirEquipmentCertification(status);
        timeHandler.postDelayed(mRunnable, 100);
    }

    //中继器
    public void sendEquipment(int status) {
        timing = 0;
        timeHandler.removeCallbacks(mRunnable);
        this.sendStatus = status;
        drone.mavLinkSendMessage.sendEquipmentCer();
        timeHandler.postDelayed(mRunnable, 100);
    }


    private Handler timeHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Message msg = new Message();
            msg.what = sendStatus;
            mHandler.sendMessage(msg);
            if (mHandler != null) {
                timeHandler.postDelayed(mRunnable, 1000);
            }
        }
    };
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://飞机
                    timing = timing + 1;
                    if (timing >= time501A) {
                        timeHandler.removeCallbacks(mRunnable);
                        timing = 0;
//                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_AIR_EQUIPMENT_FAILED);
                        drone.airRelayEquipmentCertification.setAuthentication(false);
                        bindingFailed();
                    } else {
                        if (drone.airRelayEquipmentCertification.isAuthentication() == true) {
                            timeHandler.removeCallbacks(mRunnable);
//                            myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_AIR_EQUIPMENT_SUCCESS);
                            bindingSuccess();
                        }
                    }
                    break;
                case 1://中继器
                    timing = timing + 1;
                    if (timing >= time501A) {
                        timeHandler.removeCallbacks(mRunnable);
                        timing = 0;
//                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_AIR_EQUIPMENT_FAILED);
                        drone.airRelayEquipmentCertification.setAuthentication(false);
                        bindingFailed();
                        timing = 0;
                    } else {
                        if (drone.airRelayEquipmentCertification.isAuthentication() == true) {
                            timeHandler.removeCallbacks(mRunnable);
//                            myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_AIR_EQUIPMENT_SUCCESS);
                            bindingSuccess();
                        }
                    }
                    break;
                case 2://解除绑定
                    timing = timing + 1;
                    if (timing >= time501A) {
                        timeHandler.removeCallbacks(mRunnable);
                        timing = 0;
//                        myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_AIR_EQUIPMENT_FAILED);
                        drone.airRelayEquipmentCertification.setAuthentication(false);
                        timing = 0;
                    } else {
                        if (drone.airRelayEquipmentCertification.isAuthentication() == true) {
                            timeHandler.removeCallbacks(mRunnable);
//                            myDrone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_AIR_EQUIPMENT_SUCCESS);
                            drone.airRelayEquipmentCertification.setAuthentication(false);
                        }
                    }
                    break;
                case 3://清空所有列表
                    timing = timing + 1;
                    if (timing >= time501A) {
                        timeHandler.removeCallbacks(mRunnable);
                        timing = 0;
                        drone.airRelayEquipmentCertification.setAuthentication(false);
                        hubsanECContext.setText(getResources().getString(R.string.hubsan_501_setting_other_equipment_certification_make_failed));
                        hubsanECTwoLay.setVisibility(View.VISIBLE);
                        hubsanECOneLay.setVisibility(View.GONE);
                        hubsanECThreeLay.setVisibility(View.GONE);
                    } else {
                        if (drone.airRelayEquipmentCertification.isAuthentication() == true) {
                            timeHandler.removeCallbacks(mRunnable);
                            drone.airRelayEquipmentCertification.setAuthentication(false);
                            hubsanECContext.setText(getResources().getString(R.string.hubsan_501_setting_other_equipment_certification_make_success));
                            hubsanECTwoLay.setVisibility(View.VISIBLE);
                            hubsanECOneLay.setVisibility(View.GONE);
                            hubsanECThreeLay.setVisibility(View.GONE);
                        }
                    }
                    break;
                default:

                    break;
            }
        }
    };


    /**
     * 绑定失败
     */
    private void bindingFailed(){
        hubsanECContext.setText(getResources().getString(R.string.hubsan_501_setting_other_equipment_certification_failed));
        hubsanECOk.setText(getResources().getString(R.string.ok));
        hubsanECTwoLay.setVisibility(View.VISIBLE);
        hubsanECOneLay.setVisibility(View.GONE);
        hubsanECThreeLay.setVisibility(View.GONE);
    }

    /**
     * 绑定成功
     */
    private void bindingSuccess(){
        hubsanECContext.setText(getResources().getString(R.string.hubsan_501_setting_other_equipment_certification_success));
        hubsanECTwoLay.setVisibility(View.VISIBLE);
        hubsanECOneLay.setVisibility(View.GONE);
        hubsanECThreeLay.setVisibility(View.GONE);
        hubsanECOk.setText(getResources().getString(R.string.ok));
        boolean isTestGPSSuccecc = PreferenceUtils.getPrefBoolean(EquipmentCertificationDialog.this, Constants.HUBSAN_TEST_GPS_IS_USCCESS, false);
//        if (isTestGPSSuccecc == false) {
//            startActivity(new Intent(EquipmentCertificationDialog.this, CheckGPSDialog.class));
//            EquipmentCertificationDialog.this.finish();
//        }
        drone.events.removeDroneListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        drone.events.removeDroneListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            if (hasFocus) {
                Window window = this.getWindow();
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    public void onDroneEvent(HubsanDroneInterfaces.DroneEventsType droneEventsType, HubsanDrone hubsanDrone) {
        switch (droneEventsType) {
            case HUBSAN_CLOSE_SETTINGDIALOG:
                EquipmentCertificationDialog.this.finish();
                break;
            case HUBSAN_CLOSE_EQUIMENT_DIALOG:
                EquipmentCertificationDialog.this.finish();
                break;
        }
    }
}


