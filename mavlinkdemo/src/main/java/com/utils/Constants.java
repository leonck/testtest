
package com.utils;

import android.os.Environment;


/**
 * Description
 * 定义常量
 *
 * @author ShengKun.Cheng
 * @date 2015年8月3日
 * @see [class/class#method]
 * @since [product/model]
 */
public class Constants {

    public static String image_filePath = Environment.getExternalStorageDirectory().getPath() + "/data/hubsan/ad/image_feedback/";
    public static String image_fileName_pad = "padbg.jpg";
    public static String inage_fileName_phone = "phonebg.jpg";

    /**
     * 保存分享中下载的图片
     */
    public static String DOWNLAOD_SHARE_IMAGE = Environment.getExternalStorageDirectory().getPath() + "/data/hubsan/share/";


    //    @SuppressWarnings("unused")
    //    private static final String PACKAGE_NAME = Constants.class.getPackage().getName();

    /*
     * Preferences, and default values.
     */
    public static final String PREF_BLUETOOTH_DEVICE_ADDRESS = "pref_bluetooth_device_address";

    /**
     * 连接的类型
     */
    public static final String PREF_CONNECTION_TYPE = "pref_connection_type";

    public static boolean BEL_SELECT_TAG = false;

    public static String hubsan_501_mall_url = "http://shop.hubsan.com/index.php?main_page=product_info&cPath=%2079%20&products_id=%20518";
    //    public static String hubsan_501_mall_url = "http://app.hubsan.com/alter.html?main_page=product_info&cPath=%2079%20&products_id=%20518";
    public static String hubsan_507_mall_url = "http://shop.hubsan.com/index.php?main_page=product_info&cPath=%2079%20&products_id=%20519";
//    public static String hubsan_507_mall_url = "http://app.hubsan.com/alter.html?main_page=product_info&cPath=%2079%20&products_id=%20519";

    /**
     * This is the default mavlink connection type
     *
     * @since 1.2.0
     */
//    public static final String DEFAULT_CONNECTION_TYPE = ConnectionType.TCP.name();

    /**
     * Preference screen grouping the ui related preferences.
     */
    public static final String PREF_UI_SCREEN = "pref_ui";

    /**
     * Sets whether or not the default language for the ui should be english.
     */
    public static final String PREF_UI_LANGUAGE = "pref_ui_language_english";

    /**
     * By default, the system language should be used for the ui.
     */
    public static final boolean DEFAULT_PREF_UI_LANGUAGE = false;

    /**
     * Preference key for the drone settings' category.
     */
    public static final String PREF_DRONE_SETTINGS = "pref_drone_settings";

    public static final String DATABASE = "com.csk.hbsdrone.db";

    public static final int DATABASE_VERSION = 3;

    public static final String TABLE_WAYPOINT = "waypont";

    public static final String TABLE_WAYPOINT_FIELD_ID = "_id";

    public static final String TABLE_WAYPOINT_FIELD_DATE = "date";

    public static final String TABLE_WAYPOINT_FIELD_LINE_ID = "line_id";

    public static final String TABLE_WAYPOINT_FIELD_POINT_INDEX = "pointIndex";

    /**
     * 经度
     */
    public static final String TABLE_WAYPOINT_FIELD_LNG = "wpLng";

    /**
     * 纬度
     */
    public static final String TABLE_WAYPOINT_FIELD_LAT = "wpLat";

    public static final String TABLE_WAYPOINT_FIELD_ALTTITUDE = "alttitude";
    public static final String TABLE_WAYPOINT_FIELD_SPEED = "speed";

    /**
     * 延迟
     */
    public static final String TABLE_WAYPOINT_FIELD_DELAY = "delay";

    public static final String TABLE_AIRLINE = "airline";

    public static final String TABLE_AIRLINE_FIELD_LINE_ID = "_linne_id";

    public static final String TABLE_AIRLINE_FIELD_NUM = "num";

    public static final String TABLE_AIRLINE_FIELD_LENGTH = "length";

    public static final String TABLE_AIRLINE_FIELD_DATE = "date";

    public static final String GET_LAT = "GET_LAT";

    public static final String GET_LNG = "GET_LNG";

    /**
     * 标记点击那个窗户
     */
    public static final String SHOW_WINDOW_TAG = "SHOW_WINDOW_TAG";

    /**
     * 定位弹出动画标志
     */
    public static final String ANIMITION_LOCATION_TAG = "ANIMITION_LOCATION_TAG";

    /**
     * 画航点弹出动画标志
     */
    public static final String ANIMITION_DRONE_WAY_TAG = "ANIMITION_DRONE_WAY_TAG";

    /**
     * 删除航点弹出动画标志
     */
    public static final String ANIMITION_DELETE_DRONE_WAY_TAG = "ANIMITION_DELETE_DRONE_WAY_TAG";

    /**
     * TCP 连接地址
     */
    public static final String TCP_PREF_SERVER_IP = "192.168.31.111";
//    public static final String TCP_PREF_SERVER_IP = "112.74.195.48";//华为4G
//    public static final String TCP_PREF_SERVER_IP = "192.168.42.111";//602s

//    public static final String TCP_PREF_SERVER_IP = "192.168.31.201";//602s

    /**
     * TCP 连接端口号
     */
    public static final String TCP_PREF_SERVER_PORT = "8866";

    /**
     * TCP 发送中继器连接端口号
     */
    public static final String TCP_PREF_RELAY_SERVER_IP = "192.168.31.151";

    public static final int TCP_PREF_RELAY_SERVER_PORT = 8811;
    public static final int TCP_PREF_IMAGE_SERVER_PORT = 8822;

    /**
     * UDP 发送遥感数据端口
     */
    public static final int UDP_PREF_SERVER_PORT = 8867;

    /**
     * TCP 连接图像传递
     */
    public static final String TCP_IMAGE_TRANSFER = "tcp://192.168.31.111:8855/";
//    public static final String TCP_IMAGE_TRANSFER = "tcp://192.168.42.111:8855/";//602s
//    public static final String TCP_IMAGE_TRANSFER = "tcp://112.74.195.48:8855/";//华为4G

    /**
     * 飞机的初始化数据
     */
    public static final String AIR_BASIC_INFO = "AIR_BASIC_INFO";

    /**
     * 地图校准得到的经度
     */
    public static final String PREF_OFFSET_LONGITUDE = "PREF_OFFSET_LONGITUDE";

    /**
     * 地图校准得到的纬度
     */
    public static final String PREF_OFFSET_LATITUDE = "PREF_OFFSET_LATITUDE";
    public static final String HUBSAN_AIR_LATITUDE = "HUBSAN_AIR_LATITUDE";
    public static final String HUBSAN_AIR_LONGITUDE = "HUBSAN_AIR_LONGITUDE";

    /**
     * 计算飞机和本地的经度差异
     */
    public static final String PREF_DIFFER_LON = "PREF_DIFFER_LON";

    /**
     * 计算飞机和本地的纬度差异
     */
    public static final String PREF_DIFFER_LAT = "PREF_DIFFER_LAT";

    /**
     * 允许地图校准开关
     */
    public static final String SETTING_MAP_CALIBRATION_OPEN_CLOSE = "mapCalibrationRadioOpenClose";

    /**
     * 飞机在地图显示中心开关
     */
    public static final String SETTING_MAP_CENTER_OPEN_CLOSE = "mapCenterRadioOpenClose";

    /**
     * 兴趣点开关
     */
    public static final String SETTING_MAP_GUIDE_OPEN_CLOSE = "mapGuideRadioOpenClose";

    /**
     * 地图标准模式、卫星模式、夜间模式
     */
    public static final String SETTING_MAP_NORMAL_SATELLITE_NIGHT_MODEL = "mapNormalSatelliteNightModel";

    /**
     * 中文、英文
     */
    public static final String SETTING_MAP_OTHER_CHINESE_ENGLISH = "SETTING_MAP_OTHER_CHINESE_ENGLISH";

    /**
     * 默认高度
     */
    public static final String SETTING_FLIGHTDATA_DEFAULT_HEIGHT = "DefaultHeight";

    /**
     * 最大高度
     */
    public static final String SETTING_FLIGHTDATA_MAX_HEIGHT = "MaxHeight";

    /**
     * 航线最大长度
     */
    public static final String SETTING_FLIGHTDATA_WAPPOINT_MAX_HEIGHT = "WappointMaxHeight";

    /**
     * 最大半径
     */
    public static final String SETTING_FLIGHTDATA_MAX_RADIUS = "MaxRadius";

    /**
     * 停留时间
     */
    public static final String SETTING_FLIGHTDATA_WAYPOINT_STAYTIME = "WaypointStayTime";

    public static final String SETTING_OTHER_TEST_GPS_TIMEOUT = "SETTING_OTHER_TEST_GPS_TIMEOUT";
    /**
     * 虚拟摇杆开,关
     */
    public static final String SETTING_REMOTE_OPEN_CLOSE = "SETTING_REMOTE_OPEN_CLOSE";

    /**
     * 虚拟摇杆美国手日本手_306
     */
    public static final String SETTING_REMOTE_US_JP_306 = "SETTING_REMOTE_US_JP_306";

    /**
     * 虚拟摇杆开,关_306
     */
    public static final String SETTING_REMOTE_OPEN_CLOSE_306 = "SETTING_REMOTE_OPEN_CLOSE_306";

    /**
     * 摇杆模式_306
     */
    public static final String HEARTBEAT_REMOTE_MODEL_306 = "HEARTBEAT_REMOTE_MODEL_306";

    /**
     * 虚拟摇杆美国手日本手
     */
    public static final String SETTING_REMOTE_US_JP = "SETTING_REMOTE_US_JP";

    /**
     * 操作模式设置
     */
    public static final String SETTING_REMOTE_MODEL = "SETTING_REMOTE_MODEL";
    /*自适应屏幕*/
    public static final String SETTING_ROTATING_SCREEN = "SETTING_ROTATING_SCREEN";
    /**
     * 服务器版本号
     */
    public static final String SERVICE_VERSION = "SERVICE_VERSION";

    /**
     * 免责申明
     */
    public static final String DISCLAIMER_TAG = "DISCLAIMER_TAG";

    /**
     * 退出程序
     */
    public static final String EXIT = "EXIT";

    /**
     * 退出服务
     */
    public static final int EXIT_VALUE = 90;

    /**
     * 标志点击照相机
     */
    public static final String ISCAMERAFRAGMENT = "ISCAMERAFRAGMENT";

    /**
     * 飞机型号
     */
    public static final String AIR_MODEL_NAME = "AIR_MODEL_NAME";

    /**
     * 飞机软件版本号
     */
    public static final String AIR_SOFTWAREVER = "AIR_SOFTWAREVER";
    public static final String AUTOPILOTVER = "AutopilotVer";

    /**
     * 标记心跳是否还在
     */
    public static final String HEARTBEAT_IS_LINE = "HEARTBEAT_IS_LINE";

    /**
     * 重力感应
     */
    public static final String HEARTBEAT_TZS_GRAVITY_SENSOR = "HEARTBEAT_TZS_GRAVITY_SENSOR";

    /**
     * 摇杆模式
     */
    public static final String HEARTBEAT_REMOTE_MODEL = "HEARTBEAT_REMOTE_MODEL";

    /**
     * 操作模式
     */
    public static final String HEARTBEAT_OPERATING_MODEL = "HEARTBEAT_OPERATING_MODEL";

    /**
     * 选择机型进入界面
     */
    public static final String HEARTBEAT_MODEL_ACTIVITY = "HEARTBEAT_MODEL_ACTIVITY";
    public static final String HUBSAN_AIR_NAME = "HUBSAN_AIR_NAME";
    public static final String HUBSAN_HT009_DEVICE_ADDRESS = "HUBSAN_HT009_DEVICE_ADDRESS";
    /**
     * 蓝牙名字
     */
    public static final String DEVICE_NAME = "DEVICE_NAME";

    /**
     * 蓝牙地址
     */
    public static final String DEVICE_ADDRESS = "DEVICE_ADDRESS";

    /**
     * 重力感应器OnTouch 处理
     */
    public static final String HUBSAN_GRAVITY_SENSOR = "HUBSAN_GRAVITY_SENSOR";

    /**
     * 侧飞最大值
     */
    public static final String HUBSAN_SIDE_MAX_VALUE = "HUBSAN_SIDE_MAX_VALUE";

    /**
     * 微调显示与否
     */
    public static final String HUBSAN_FINE_TUNING = "HUBSAN_FINE_TUNING";

    /**
     * 第一次启动107
     */
    public static final String HUBSAN_FIRST_START_APP = "HUBSAN_FIRST_START_APP";

    public static final String HUBSAN_FIRST_501_START = "HUBSAN_FIRST_501_START";

    /**
     * 广告页面
     */
//    public static final String HUBSAN_APP_AD = "http://120.24.160.101:8080/xinsight_android_gaode/androidbg/ch_ad.html";
    public static final String HUBSAN_APP_AD = "http://120.24.160.101:8080/xinsight_android_gaode/androidbg/ch_ad.html";

    /**
     * 播放SDCard卡和飞机的的视频
     */
    public static final String HUBSAN_PLAY_VIDEO_SDCARD_AIR = "HUBSAN_PLAY_VIDEO_SDCARD_AIR";

    /**
     * 销毁MAVLink定时器
     */
    public static final String HUBSAN_MAVLINK_CANCEL_TIMER = "HUBSAN_MAVLINK_CANCEL_TIMER";
    public static boolean isExitMavLink = false;
    public static int UNLOCK_TAG = 0;

    /**
     * 设置107的背景图片
     */
    public static final String HUBSAN_TZS_SETTING_BG = "HUBSAN_TZS_SETTING_BG";
    /**
     * 动画时间
     */
    public static long ANIM_TIME = 400;
    public static long anim_time = 200;
    /*选择地图或者是相机*/
    public static int select_mode;
    /**
     * 判断是否上锁成功
     */
    public static boolean HUBSAN_LOCK;
    /**
     * 判断是否解锁成功
     */
    public static boolean HUBSAN_UNLOCK;
    /**
     * 免责声明状态
     */
    public static boolean HUBSAN_DISCLAIMER;
    /*判断只能发送一个遥感数据*/
    public static boolean HUBSAN_SENDROCKER_DATA_TAG = false;

    /**
     * 已连接WIFI的密码
     */
    public static String HUBSAN_WIFI_PASSWORD = "HUBSAN_WIFI_PASSWORD";


    public static String HUBSAN_HY007_BLE_SSID = "HUBSAN_HY007_BLE_SSID";
    /*是否播放图传*/
    public static String HUBSAN_501_IS_PLAY = "HUBSAN_501_IS_PLAY";

    /*没有GPS时打开解锁功能*/
    public static String HUBSAN_NO_GPS_CAN_UNLOCK = "HUBSAN_NO_GPS_CAN_UNLOCK";
    /*允许用户测试GPS*/
    public static String HUBSAN_ALLOWS_USER_TEST_GPS = "HUBSAN_ALLOWS_USER_TEST_GPS";
    //起飞点
    public static String HUBSAN_UP_FLY_POINT = "HUBSAN_UP_FLY_POINT";

    /*activity退出的标记*/
    public static int HUBSAN_ACTIVITY_EXIT_TAG = 0;
    /*测试GPS成功以后的标记*/
    public static boolean TEST_GPS_FAILED = false;
    public static String HUBSAN_TEST_GPS_IS_USCCESS = "HUBSAN_TEST_GPS_IS_USCCESS";

    /**
     * 当前飞机的类型
     */
    public static int HUBSAN_SELECT_AIR_MODEL = 0;

    public static String HUBSAN_GPS_FIRST_LAT = "HUBSAN_GPS_FIRST_LAT";
    public static String HUBSAN_GPS_FIRST_LON = "HUBSAN_GPS_FIRST_LON";
    public static String HUBSAN_GPS_LOST_LAT = "HUBSAN_GPS_LOST_LAT";
    public static String HUBSAN_GPS_LOST_LON = "HUBSAN_GPS_LOST_LON";

    private final static short HUBSAN_IDLE_MODE = 0;      //0x00
    public final static short HUBSAN_MANUAL_MODE = 1;     //手动模式 0x01
    public final static short HUBSAN_ALTITUDE_HOLD = 2;   //定高模式 0x02
    public final static short HUBSAN_GPS_HOLD = 4;        //定点模式0x04
    public final static short HUBSAN_RETURN_HOME = 8;     //返航0x08
    public final static short HUBSAN_FOLLOW_MODE = 16;    // 跟飞模式0x10
    public final static short HUBSAN_WAYPOINT_FLY = 32;   // 航点模式0x20
    public final static short HUBSAN_FREE_HEAD = 64;      //无头模式0x40
    public final static short HUBSAN_SURROUND_FLY = 128;  //环绕飞行模式0x80

    /*HY007 ID*/
    public final static String SYS_GET_COM_VER = "07 80";
    public final static String SYSTEM_RESET = "01 80";
    public final static String BOOT_GET_MAGIC = "01 81";
    public final static String SYS_GET_CAR_STATUS = "06 80";
    public final static String BOOT_SET_FILEINFO = "04 81";
    public final static String BOOT_SEND_DATA = "05 81";
    public final static String BOOT_GET_CHECK_SUM = "07 81";
    public final static String BOOT_OP_END = "08 81";
    public static String SCREEN_HEIGHT = "SCREEN_HEIGHT";
    public static String SCREEN_WIDTH = "SCREEN_WIDTH";

    //中继器
    enum ht005_command {
        HUBSAN_HT005_UPDATE_BEGIN,
        HUBSAN_HT005_IMAGE_TRANSFER,
        HUBSAN_HT005_IMAGE_TRANSFER_OVER,
        HUBSAN_HT005_MD5_TRANSFER,
        HUBSAN_HT005_MD5_TRANSFER_OVER,
        HUBSAN_HT005_MD5_OK,
        HUBSAN_HT005_MD5_ERROR,
        HUBSAN_HT005_UPDATE_SUCCESS,
        HUBSAN_HT005_REBOOT,
        HUBSAN_HT005_SET_WIFI,
    }

    ;

}
