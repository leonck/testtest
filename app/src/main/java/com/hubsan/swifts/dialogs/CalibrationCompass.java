package com.hubsan.swifts.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.hubsan.swifts.R;
import com.hubsan.swifts.SwiftsApplication;
import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces;
import com.hubsansdk.utils.LogX;
import com.utils.Constants;

/**
 * 自定义对话框
 * Created by chengshengkun on 16/11/1.
 */

public class CalibrationCompass extends Dialog {
    private ImageView calibartionVertical, calibartionHorizintal, calibartionCancel;
    private int mAnimationLoop = 300;
    /*时间*/
    private int mTime = 14;
    private int msgTag;
    private static int mPar2, mPar6;
    private FrameLayout hubsanCalibreteLay;
    public boolean isSuccess = false;
    private TextView hubsanCalibrationText;
    private OnClickListenerInterface clickListenerInterface;
    private Context mContext;
    private SwiftsApplication app;
    private HubsanDrone drone;

    public interface OnClickListenerInterface {
        void doCancel();
    }

//    /**
//     * @param context 上下文
//     */
    public CalibrationCompass(Context context) {
        super(context, R.style.LockDialogSetting);//需要更换
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        app = (SwiftsApplication) HubsanApplication.getApplication();
        this.drone = app.drone;
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.hubsan_activity_calibration_compass_dialog, null);
        setContentView(view);
        calibartionHorizintal = (ImageView) findViewById(R.id.calibartionHorizintal);
        calibartionVertical = (ImageView) findViewById(R.id.calibartionVertical);
        calibartionCancel = (ImageView) findViewById(R.id.calibartionCancel);
        hubsanCalibreteLay = (FrameLayout) findViewById(R.id.hubsanCalibreteLays);
        hubsanCalibrationText = (TextView) findViewById(R.id.hubsanCalibrationText);
        calibartionCancel.setVisibility(View.GONE);
        hubsanCalibreteLay.setVisibility(View.VISIBLE);
        if (mPar2 == 1 & mPar6 == 1) {
            startHorizintal();
        } else if (mPar2 == 1 & mPar6 == 2) {
            startVertical();
        } else if (mPar2 == 0 & mPar6 == 0) {
            dismiss();
        }
        calibartionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hubsanCalibreteLay.setVisibility(View.GONE);
                clickListenerInterface.doCancel();
                removerRunnable();
            }
        });
        ViewTreeObserver vto = hubsanCalibrationText.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int line = hubsanCalibrationText.getLineCount();
                if (line > 1) {
                    hubsanCalibrationText.setGravity(Gravity.TOP);
                } else {
                    hubsanCalibrationText.setGravity(Gravity.CENTER);
                }
                return true;
            }
        });
        Window dialogWindow = getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = app.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        if (HubsanApplication.getApplication().getResources().getBoolean(R.bool.portrait_only)) {//手机
            lp.width = (int) (d.widthPixels * 0.34);
            lp.height = (int) (d.heightPixels * 0.74);
        } else {//平板
            lp.width = (int) (d.widthPixels * 0.38);
            lp.height = (int) (d.heightPixels * 0.6);
        }
        dialogWindow.setAttributes(lp);
        getWindow().setGravity(Gravity.CENTER);
        //通知其他，只显示当前地磁校准界面，其他先隐藏掉
        drone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_CLOSE_SETTINGDIALOG);
    }

    public void setCalibrationClicklistener(OnClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 3:
                    if (mPar2 == 1 && mPar6 == 1) {
                        mTime--;
                        setStartHorizintal(mTime);
                        if (mTime == 1) {
                            setStartHorizintal(mTime);
                            mTime = 14;
                        }
                    } else {
                        startVertical();
                    }
                    break;
                case 2:
                    if (mPar2 == 1 && mPar6 == 2) {
                        mTime--;
                        setStartVertical(mTime);
                        if (mTime == 1) {
                            setStartVertical(mTime);
                            mTime = 14;
                        }
                    } else {
                        removerRunnable();
                        dismiss();
                        isSuccess = true;
                        hubsanCalibreteLay.setVisibility(View.GONE);
                        LogX.e("========HUBSAN_CALIBRATIONCOMPASS_OVER==========");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public void setCalibrationTag(int par2, int par6) {
        this.mPar2 = par2;
        this.mPar6 = par6;
        if (par2==0&& par6==0){
            timeOutHandler.removeCallbacks(timeOutRunnable);
            handler.removeCallbacks(mRunnable);
            dismiss();
        }else {
            timeOutHandler.removeCallbacks(timeOutRunnable);
            timeOutHandler.postDelayed(timeOutRunnable,4000);
            if (drone!= null) {
                drone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_CLOSE_SETTINGDIALOG);
            }
        }
    }

    /*设置水平地磁校准*/
    public void startHorizintal() {
        msgTag = 3;
        // 指定动画的帧的列表
        handler.postDelayed(mRunnable, mAnimationLoop);
    }

    /*设置垂直地磁校准*/
    public void startVertical() {
        calibartionHorizintal.setVisibility(View.GONE);
        calibartionVertical.setVisibility(View.VISIBLE);
        msgTag = 2;
        handler.postDelayed(mRunnable, mAnimationLoop);
    }

    private Runnable mRunnable = new Runnable() {

        public void run() {
            Message msg = new Message();
            msg.what = msgTag;
            handler.sendMessage(msg);
            handler.postDelayed(mRunnable, mAnimationLoop);
        }
    };

    public void setStartVertical(int tag) {
        int selectAir = Constants.HUBSAN_SELECT_AIR_MODEL;
        if (selectAir == 1) {
            switch (tag) {
                case 1:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_one);
                    break;
                case 2:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_twelve);
                    break;
                case 3:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_eleven);
                    break;
                case 4:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_ten);
                    break;
                case 5:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_nine);
                    break;
                case 6:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_eight);
                    break;
                case 7:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_selven);
                    break;
                case 8:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_six);
                    break;
                case 9:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_five);
                    break;
                case 10:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_four);
                    break;
                case 11:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_three);
                    break;
                case 12:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_two);
                    break;
                case 13:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_one);
                    break;
                default:
                    break;
            }
        } else {
            switch (tag) {
                case 1:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_507_thirtee);
                    break;
                case 2:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_507_twelve);
                    break;
                case 3:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_507_eleven);
                    break;
                case 4:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_507_ten);
                    break;
                case 5:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_507_nine);
                    break;
                case 6:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_507_eight);
                    break;
                case 7:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_507_seven);
                    break;
                case 8:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_507_six);
                    break;
                case 9:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_507_five);
                    break;
                case 10:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_507_four);
                    break;
                case 11:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_507_three);
                    break;
                case 12:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_507_two);
                    break;
                case 13:
                    calibartionVertical.setImageResource(R.drawable.hubsan_calibration_ver_507_one);
                    break;
                default:
                    break;
            }
        }

    }

    public void setStartHorizintal(int tag) {
        int selectAir = Constants.HUBSAN_SELECT_AIR_MODEL;
        if (selectAir == 1) {
            switch (tag) {
                case 1:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_one);
                    break;
                case 2:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_twelve);
                    break;
                case 3:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_eleven);
                    break;
                case 4:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_ten);
                    break;
                case 5:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_nine);
                    break;
                case 6:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_eight);
                    break;
                case 7:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_seven);
                    break;
                case 8:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_six);
                    break;
                case 9:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_five);
                    break;
                case 10:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_four);
                    break;
                case 11:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_three);
                    break;
                case 12:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_two);
                    break;
                case 13:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_one);
                    break;
                default:
                    break;
            }
        } else {
            switch (tag) {
                case 1:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_507_thirtee);
                    break;
                case 2:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_507_twelve);
                    break;
                case 3:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_507_eleven);
                    break;
                case 4:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_507_ten);
                    break;
                case 5:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_507_nine);
                    break;
                case 6:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_507_eight);
                    break;
                case 7:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_507_seven);
                    break;
                case 8:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_507_six);
                    break;
                case 9:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_507_five);
                    break;
                case 10:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_507_four);
                    break;
                case 11:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_507_three);
                    break;
                case 12:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_507_two);
                    break;
                case 13:
                    calibartionHorizintal.setImageResource(R.drawable.hubsan_calibration_507_one);
                    break;
                default:
                    break;
            }
        }
    }

    public void removerRunnable() {
        handler.removeCallbacks(mRunnable);
        timeOutHandler.removeCallbacks(timeOutRunnable);
    }

    private Handler timeOutHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    LogX.e("------------------关闭对话框--------------------");
                    dismiss();
                    break;
            }
        }
    };
    private Runnable timeOutRunnable = new Runnable() {
        @Override
        public void run() {
            timeOutHandler.removeCallbacks(timeOutRunnable);
            Message message = new Message();
            message.what = 1;
            timeOutHandler.sendMessage(message);

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Window window = this.getWindow();
        //Window window = getDialog().getWindow();如果是在activity中则用这段代码
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //window.requestWindowFeature(Window.FEATURE_NO_TITLE); 用在activity中，去标题
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(uiOptions);
        }

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

    public void show(Activity context) {
        //Here's the magic..
        //Set the dialog to not focusable (makes navigation ignore us adding the window)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        //Set the dialog to immersive
        this.getWindow().getDecorView().setSystemUiVisibility(context.getWindow().getDecorView().getSystemUiVisibility());
        //Show the dialog! (Hopefully no soft navigation...)
        super.show();
        //Clear the not focusable flag from the window
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        //Update the WindowManager with the new attributes (no nicer way I know of to do this)..
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.updateViewLayout(this.getWindow().getDecorView(), this.getWindow().getAttributes());
        //Clearly not ideal but seems to be an Android bug, they should check if the Window has immersive set.
    }

}
