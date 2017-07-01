package com.hubsan.swifts;

import com.hubsan.swifts.drone.variables.CameraFocus;
import com.hubsan.swifts.drone.variables.mission.Mission;
import com.hubsan.swifts.helpers.units.Altitude;
import com.hubsansdk.application.HubsanHandleMessageApp;
import com.hubsansdk.utils.LogX;
import com.hubsansdk.utils.NetUtil;
import com.utils.CommonDialog;
import com.utils.Constants;
import com.utils.PreferenceUtils;
import com.utils.TestNetworkIsConn;
import com.utils.ToastUtil;

import com.example.androidvideoplayer.VideoPlayer;

/**
 * @project name: X-Hubsan
 * @class name：com.csk.hbsdrone
 * @class describe:
 * @anthor shengkun.cheng
 * @time 2017/2/9 11:13
 * @change
 * @chang time:
 * @class describe:
 */
public class SwiftsApplication extends HubsanHandleMessageApp {

    /*---------------开始 初始化类------------------*/
    //例如 Person person = new Person(drone);
//    public GPS GPS;
    public CommonDialog mCommonDialog;
    public Mission mission;
//    public GuidedPoint guidedPoint;
    public CameraFocus cameraFocus;
    public ToastUtil toastUtil = new ToastUtil();
//    public Guide mGuide;
    public VideoPlayer videoplayer;
//    public Rocker mRocker = new Rocker();
//    public Config config = new Config();
//    public ThreeBean threeBean;
//    public HttpThread httpThread;
//    public WebViewSet webViewSet = new WebViewSet();
//    public GsonBean gsonBean = new GsonBean();
//    public SDcardImageBean sDcardImageBean = new SDcardImageBean();
//    public Utils utils = new Utils();
//    public UcPreserter ucPreserter = new UcPreserter();
//    public AirLine currentAirline = new AirLine(-1);
    public Altitude altitude = new Altitude(0.0f);
//    public Freshcoordinate freshcoordinate = new Freshcoordinate();
//    public BluetoothNotify bluetoothNotify;
//    public LocalLatLon localLatLon = new LocalLatLon();
    public NetUtil netUtil = new NetUtil();
    public TestNetworkIsConn testNetworkIsConn;
//    public Dao dao;
    private boolean isStopTag = false;
//    public BluetoothLeService bluetoothLeService;

    /*---------------结束 初始化类------------------*/
    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    public void initData() {
        //设置可以显示日志
        LogX.enabled = true;
        //设置不可显示日志
//        LogX.enabled = false;
//        GPS = new GPS(drone);
//        threeBean = new ThreeBean(drone);
//        httpThread = new HttpThread(drone);
        mCommonDialog = new CommonDialog();
        mission = new Mission(drone);
//        guidedPoint = new GuidedPoint(drone);
        cameraFocus = new CameraFocus(drone);
//        mGuide = new Guide(drone);
//        bluetoothNotify = new BluetoothNotify(drone);
//        testNetworkIsConn = new TestNetworkIsConn(drone);

        PreferenceUtils.setPrefInt(getApplicationContext(), Constants.EXIT, 0);
        PreferenceUtils.setPrefBoolean(getApplicationContext(), Constants.HUBSAN_NO_GPS_CAN_UNLOCK, false);
//        mission.init(30);
//        dao = new Dao(drone, getApplicationContext());
        videoplayer = new VideoPlayer();
    }
    //各个平台的配置，建议放在全局Application或者程序入口
//    {

//        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //微信 appid appsecret
//        PlatformConfig.setSinaWeibo("3921700954","04b48b094faeb16683c32669824ebdad");
        //微信
//        PlatformConfig.setWeixin("wxd7880d034f470d9c", "05e76a7036480e3d2dd7c54743b84cb1");
//        //新浪微博
//        PlatformConfig.setSinaWeibo("153910718", "e0951b1cf864ee23dc45e50e45f0b6b2");
//        PlatformConfig.setQQZone("1105083420", "1F3qdyRTwgXozEv9");//可以使用
//        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
//        PlatformConfig.setPinterest("1439206");
//    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

//    public void stopVideoplayer() {
//        if (videoplayer != null) {
//            if (videoplayer.getPlayState() == 1 || videoplayer.getPlayState() == 2) {
//                if (isStopTag == false) {
//                    isStopTag = true;
//                    mHandler.postDelayed(mRunnable, 10);
//                }
//            }
//        }
//    }
//
//    public void setVideoplayer(VideoPlayer videoplayer) {
//        this.videoplayer = videoplayer;
//    }
//
//    private Handler mHandler = new Handler();
//    private Runnable mRunnable = new Runnable() {
//        @Override
//        public void run() {
//            mHandler.postDelayed(mRunnable, 1000);
//            videoplayer.stop();
//            mHandler.removeCallbacks(mRunnable);
//            isStopTag = false;
//        }
//    };
}
