
package com.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.utils.LogX;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;



/**
 * Description
 * <p>
 * util工具类
 *
 * @author ShengKun.Cheng
 * @date 2015年11月5日
 * @see [class/class#method]
 * @since [product/model]
 */
public class Utils {

    private static long lastClickTime;
    /**
     * 图片名字
     */
    public static final String SMB_IMAGE_NAME = "SMB_IMAGE_NAME";

    public static final String SMB_IMAGE_NAME_PATH = "SMB_IMAGE_NAME_PATH";

    public static final String SMB_IMAGE_SD_AIR_TAG = "SMB_IMAGE_SD_AIR_TAG";

    /**
     * 缩略图
     */
    public static final String SMB_URI_SMALL = "smb://192.168.31.111/hubsan/small/";

    /**
     * 大图
     */
    public static final String SMB_URI_BIG = "smb://192.168.31.111/hubsan/big/";

    /**
     * 视频路径
     */
    public static final String SMB_VIDEO_URI = "smb://192.168.31.111/hubsan/video/";

    public static final String SMB_VIDEO_PLAY = "http://192.168.31.111:8080/video/";

    /**
     * 往服务器写数据
     */
//    public static final String WRITE_SERVIVE_DATA= "http://192.168.31.151:8080/test";
//    public static final String WRITE_SERVIVE_DATA= " http://112.74.195.48:8080/ExceptionServer/ExceptionManagerServlet";

    public static final String WRITE_SERVIVE_DATA = "http://192.168.20.53/hubsanmall/index.php/Home/Upload/upload.html";
    /**
     * 保存分享中下载的图片
     */
    public static String DOWNLAOD_SHARE_IMAGE = Environment.getExternalStorageDirectory().getPath() + "/data/hubsan/share/";

    /**
     * 保存在下HY007固件
     */
    public static String DOWNLAOD_HY007_FILE = Environment.getExternalStorageDirectory().getPath() + "/data/hubsan/hy007/";

    private static ArrayList<String> paths = new ArrayList<String>();

    private static Handler myHandler;
    private static HandlerThread myHandlerThread;


    /**
     * Used to update the user interface language.
     *
     * @param context Application context
     */
    public static void updateUILanguage(Context context) {

        final boolean isUiLanguageEnglish = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(Constants.PREF_UI_LANGUAGE, Constants.DEFAULT_PREF_UI_LANGUAGE);

        if (isUiLanguageEnglish) {
            Configuration config = new Configuration();
            config.locale = Locale.ENGLISH;
            final Resources res = context.getResources();
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
    }

    /**
     * 二进制转十进制
     * Description
     *
     * @param bit
     * @see [class/class#field/class#method]
     */
    public static int bitToInt(String bit) {

        return Integer.parseInt(bit, 2);
    }

    /**
     * 十进制转化为十六进制
     * Description
     *
     * @return
     * @see [class/class#field/class#method]
     */
    public static int intToByte(int value) {

        return Integer.parseInt(Integer.toHexString(value));
    }

    /**
     * 十进制转化为十六进制(字符串)
     *
     * @param value
     * @return
     */
    public static String intToStr(int value) {

        return Integer.toHexString(value);
    }

    /**
     * 字节转换为int
     *
     * @param b
     * @return
     */
    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }


    /**
     * 字节数组转十六进制
     */
    public static String byteToHex(byte b) {

        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex.toUpperCase();
    }

    /**
     * float保留两位小数
     */
    public static String floatBigDec(float vlaue) {

        BigDecimal bd = new BigDecimal(vlaue);
        bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP); //保留两位小数
        return String.valueOf(bd);
    }

    /**
     * 二进制转16进制
     * Description
     *
     * @param bString
     * @return
     * @see [class/class#field/class#method]
     */
    public static String binaryString2hexString(String bString) {

        if (bString == null || bString.equals("") || bString.length() % 8 != 0)
            return null;
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }

    //10进制转16进制
    public static String IntToHex(int n) {
        char[] ch = new char[20];
        int nIndex = 0;
        while (true) {
            int m = n / 16;
            int k = n % 16;
            if (k == 15)
                ch[nIndex] = 'F';
            else if (k == 14)
                ch[nIndex] = 'E';
            else if (k == 13)
                ch[nIndex] = 'D';
            else if (k == 12)
                ch[nIndex] = 'C';
            else if (k == 11)
                ch[nIndex] = 'B';
            else if (k == 10)
                ch[nIndex] = 'A';
            else
                ch[nIndex] = (char) ('0' + k);
            nIndex++;
            if (m == 0)
                break;
            n = m;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(ch, 0, nIndex);
        sb.reverse();
        return sb.toString();
    }

    /**
     * 解析data前四位
     *
     * @param frame
     * @return
     */
    public static String parsingStartStr(byte[] frame) {
        StringBuffer startString = new StringBuffer();
        for (int i = 0; i < frame.length; i++) {
            if (i >= 6 && i <= 9) {
                int startStr = Integer.parseInt(String.valueOf(frame[i] & 0xff), 16);
                startString.append(".").append(startStr);
            }
        }
        String start = startString.reverse().toString();//倒序
        return start.substring(0, start.length() - 1);
    }

    /**
     * 解析data后四位
     *
     * @param frame
     * @return
     */
    public static String parsingEndStr(byte[] frame) {
        StringBuffer endString = new StringBuffer();
        for (int i = 0; i < frame.length; i++) {
            if (i >= 10 && i <= 13) {
                int endStr = Integer.parseInt(String.valueOf(frame[i] & 0xff), 16);
                endString.append(".").append(endStr);
            }
        }
        String end = endString.reverse().toString();
        return end.substring(0, end.length() - 1);
    }

    /*十进制转换为十六进制,一位的补0*/
    public static String textSizeAddZero(int str) {

        String value = intToStr(str);
        if (value.length() == 1) {
            return "0" + value;
        }
        return value;
    }


    /**
     * int -> byte[]
     * 将int类型的数据转换为byte数组
     * 原理：将int数据中的四个byte取出，分别存储
     *
     * @param n int数据
     * @return 生成的byte数组
     */
    public static byte[] intToBytes2(int n) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (n >> (24 - i * 8));
        }
        return b;
    }

    /**
     * byte[] -> float
     *
     * @param b 字节数组转换为float
     * @return
     */
    public static float getFloat(byte[] b) {
        // 4 bytes
        int accum = 0;
        for (int shiftBy = 0; shiftBy < 4; shiftBy++) {
            accum |= (b[shiftBy] & 0xff) << shiftBy * 8;
        }
        return Float.intBitsToFloat(accum);
    }

    /**
     * 截取ID
     *
     * @param frame
     * @return
     */

    public static String getIdString(byte[] frame) {
        StringBuffer idString = new StringBuffer();
        for (int i = 0; i < frame.length; i++) {
            if (i >= 2 && i <= 5) {
                int idStr = frame[i] & 0xff;

                idString.append(" ").append(IntToHex(idStr));
            }
        }
        return idString.toString();
    }


    /**
     * Description 组装数据
     *
     * @param one   油门
     * @param two   左右转向
     * @param three 左右转向微调
     * @param four  前后
     * @param five  侧飞
     * @param six   前后微调
     * @param seven 侧飞微调
     * @param eight 特殊寄存器
     * @see [class/class#field/class#method]
     */
    public static byte[] bytes(int one, int two, int three, int four, int five, int six, int seven, int eight) {

        byte b[] = new byte[9];
        b[0] = (byte) (one); //Elev 油门
        b[1] = (byte) (two);//Ail 左右旋转
        b[2] = (byte) (three);//左右转向微调
        b[3] = (byte) (four);//Thruter 前后
        b[4] = (byte) (five);//Rudder 侧飞
        b[5] = (byte) (six);//前后微调
        b[6] = (byte) (seven);//侧飞微调
        b[7] = (byte) (eight);//功能按钮参数
        b[8] = (byte) (checkBytes(one, two, three, four, five, six, seven, eight));//校验数据
        return b;
    }

    /**
     * 校验
     * Description
     *
     * @param one
     * @param two
     * @param three
     * @param four
     * @param five
     * @param six
     * @param seven
     * @param eight
     * @see [class/class#field/class#method]
     */
    public static int checkBytes(int one, int two, int three, int four, int five, int six, int seven, int eight) {

        return 255 - (one + two + three + four + five + six + seven + eight);

    }

    /**
     * 计算剩余电量使用时间
     * Description
     *
     * @param a     毫安時
     * @param value 剩余电量
     * @param v     电压
     * @param w     功率
     * @return
     * @see [class/class#field/class#method]
     */
    public static double useTime(double a, double value, double v, double w) {

        double time = (a * (v / 100) * value) / w;
        return time;
    }

    /**
     * Description 防止重复点击
     *
     * @return
     * @see [class/class#field/class#method]
     */
    public synchronized static boolean isFastClick() {

        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 拍照为了防止重复点击
     *
     * @return
     */
    public synchronized static boolean isCameraFastClick(long times) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < times) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static String toStringHex(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");//UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }


    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isTablet(Context context) {

        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 判断字符串是否包含一些字符 indexOf
     * Description
     *
     * @param src  字符串
     * @param dest 包含值
     * @return
     * @see [class/class#field/class#method]
     */
    public static boolean indexOfString(String src, String dest) {

        boolean flag = false;
        if (src.indexOf(dest) != -1) {
            flag = true;
        }
        return flag;
    }

    /**
     * 显示或者隐藏虚拟按钮
     * Description
     *
     * @param main
     * @see [class/class#field/class#method]
     */
    public static void systemUiVisible(View main) {

        int i = main.getSystemUiVisibility();
        if (i == View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) {
            main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        } else if (i == View.SYSTEM_UI_FLAG_VISIBLE) {
            main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        } else if (i == View.SYSTEM_UI_FLAG_LOW_PROFILE) {
            main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }


    /**
     * 读取图片并显示
     * Description
     *
     * @param uri uri
     * @param iv  ImageView
     * @return
     * @see [class/class#field/class#method]
     */
//    public ImageView ShowImg(String uri, ImageView iv) {
//
//        try {
//            SmbFileInputStream sTS = new SmbFileInputStream(uri);
//            BufferedInputStream bs = new BufferedInputStream(sTS);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 2;
//            Bitmap btp = BitmapFactory.decodeStream(bs, null, options);
//            iv.setImageBitmap(btp);
//            bs.close();
//            sTS.close();
//            btp = null;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return iv;
//    }

    /**
     * 保存文件
     *
     * @param
     * @param fileName
     * @throws IOException
     */
    public boolean saveFile(Context mContext, Bitmap mBitmap, String dirPath, String fileName) throws IOException {
        File file = null;
        try {
            file = new File(dirPath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        File myCaptureFile = new File(dirPath + fileName);
        if (myCaptureFile.exists()) {
            return true;
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        if (mBitmap != null) {
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        }
        MediaScannerConnection.scanFile(mContext, new String[]{dirPath + fileName}, null, null);
        bos.flush();
        bos.close();
        return false;
    }

    /**
     * 删除已下载到SDCard中的图片
     * Description
     *
     * @param path
     * @see [class/class#field/class#method]
     */
    public void deleteSDCardImage(String path, Context mContext) {
        File file = new File(path);
        if (file.isFile()) {
            file.delete();
            MediaScannerConnection.scanFile(mContext, new String[]{
                    path
            }, null, null);
        }
        file.exists();

    }

    /**
     * 根据position查找SDCard中对应的图片
     * Description
     *
     * @param position
     * @return
     * @see [class/class#field/class#method]
     */
    public static String findImagePath(int position) {

        File baseFile = new File(DOWNLAOD_SHARE_IMAGE);
        File[] files = baseFile.listFiles();
        if (files.length > 0) {
            return files[position].getAbsolutePath();
        } else {
            return "";
        }
    }

    /**
     * 获取图片地址列表
     *
     * @param file
     * @return
     */
    public static ArrayList<String> imagePath(File file) {

        ArrayList<String> list = new ArrayList<String>();
        if (list.size() > 0) {
            list.clear();
        }
        File[] files = file.listFiles();
        for (File f : files) {
            list.add(f.getAbsolutePath());
        }
        Collections.sort(list);
        return list;
    }

    /**
     * 读取sdcard文件夹中的图片，并生成略缩图
     *
     * @return
     * @throws FileNotFoundException
     */
    public Map<String, Bitmap> buildThum() throws FileNotFoundException {

        Bitmap bitmap = null;
        File baseFile = new File(DOWNLAOD_SHARE_IMAGE);
        if (!baseFile.exists()) {
            baseFile.mkdirs();
        }
        // 使用TreeMap，排序问题就不需要纠结了
        Map<String, Bitmap> maps = new TreeMap<String, Bitmap>();
        if (baseFile != null && baseFile.exists()) {
            paths = imagePath(baseFile);
            if (!paths.isEmpty()) {
                for (int i = 0; i < paths.size(); i++) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true; // 设置了此属性一定要记得将值设置为false
                    options.inJustDecodeBounds = false;
                    int be = options.outHeight / 200;
                    if (be <= 0) {
                        be = 10;
                    }
                    options.inSampleSize = be;
                    if (getVideoFileName(paths.get(i))) {
                        bitmap = getVideoThumbnail(paths.get(i), 200, 200, MediaStore.Images.Thumbnails.MICRO_KIND);
                    } else {
                        bitmap = BitmapFactory.decodeFile(paths.get(i), options);
                    }
//                    bitmap = BitmapFactory.decodeFile(paths.get(i), options);
                    //bitmap = BitmapFactory.decodeFile(paths.get(i));
                    maps.put(paths.get(i), bitmap);
                }
            }
        }
        return maps;
    }


    /**
     * 获取本地的SDCard缩略图
     * Description
     *
     * @param path
     * @return
     * @see [class/class#field/class#method]
     */
    public Bitmap getBuildBitmap(String path) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 设置了此属性一定要记得将值设置为false
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;
        int be = options.outHeight / 100;
        if (be <= 0) {
            be = 10;
        }
        options.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    /**
     * 播放视频
     * Description
     *
     * @param mContext
     * @param strSMBURLPath
     * @see [class/class#field/class#method]
     */
    public void playVideo(Context mContext, String strSMBURLPath) {
        Log.e("csk", "strSMBURLPath:" + strSMBURLPath);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(strSMBURLPath), "video/*");
        mContext.startActivity(intent);
    }


    /**
     * Description zh：汉语 en：英语
     *
     * @return
     * @see [class/class#field/class#method]
     */
    public static boolean isZh(Context mContext) {

        Locale locale = mContext.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }

    /**
     * 判断文件是否包含MP4
     * Description
     *
     * @param fileAbsolutePath
     * @return
     * @see [class/class#field/class#method]
     */
    public boolean getVideoFileName(String fileAbsolutePath) {

        File file = new File(fileAbsolutePath);
        // 判断是否为文件夹
        String filename = file.getName();
        // 判断是否为MP4结尾
        if (filename.trim().toLowerCase().endsWith(".mp4")) {
            return true;
        }
        return false;
    }


    /**
     * 判断SDCard是否存在
     * Description
     *
     * @param path
     * @return
     * @see [class/class#field/class#method]
     */
    public static boolean isVideoExists(String path) {
        String name = path.substring(path.lastIndexOf("/") + 1, path.length());
        File baseFile = new File(DOWNLAOD_SHARE_IMAGE);
        if (!baseFile.exists()) {
            baseFile.mkdirs();
        }
        if (baseFile != null && baseFile.exists()) {
            paths = imagePath(baseFile);
            if (!paths.isEmpty()) {
                for (int i = 0; i < paths.size(); i++) {
                    if (paths.get(i) == name) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 根据uri读取SDCard中的图片
     *
     * @param filepath
     * @return
     */
    public Bitmap getBitmapFromUri(String filepath) {

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            // 读取uri所在的图片
            Bitmap bitmap = BitmapFactory.decodeFile(filepath);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 显示或隐藏状态栏
     *
     * @param mContext
     */
    public static void hideNavigationBar(Activity mContext) {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN// hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE;

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            uiFlags |= 0x00001000;    //SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide navigation bars - compatibility: building API level is lower thatn 19, use magic number directly for higher API target level
        } else {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        mContext.getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    /**
     * * 两个Double数相加 *
     *
     * @param v1 *
     * @param v2 *
     * @return Double
     */
    public static Double add(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.add(b2).doubleValue());
    }

    public static float addFloat(float f1, float f2) {
        return f1 + f2;
    }

    /**
     * * 两个Double数相减 *
     *
     * @param v1 *
     * @param v2 *
     * @return Double
     */
    public static Double sub(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.subtract(b2).doubleValue());
    }

    /**
     * * 两个Double数相除 *
     *
     * @param v1 *
     * @param v2 *
     * @return Double
     */
    public static Double div(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.divide(b2, 10, BigDecimal.ROUND_HALF_UP)
                .doubleValue());
    }

    /**
     * * 两个Double数相乘 *
     *
     * @param v1 *
     * @param v2 *
     * @return Double
     */
    public static Double mul(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.multiply(b2).doubleValue());
    }

    /**
     * 保留七位小数
     *
     * @param value
     * @return
     */
    public static double getDecimal(double value) {

        String pattern = "#0.0000000";//格式代码，".000"代表保留三位小数，是0的输出0
        DecimalFormat formatter = new DecimalFormat();
        formatter.applyPattern(pattern);
        double fat = Double.valueOf(formatter.format(value));
        return fat;
    }

    public static float getPrice(float price) {
        String pattern = "#0.0000000";//格式代码，".000"代表保留三位小数，是0的输出0
        DecimalFormat formatter = new DecimalFormat();
        formatter.applyPattern(pattern);
        return Float.parseFloat(formatter.format(price));
    }

    /**
     * 保留七位小数
     * @param value
     * @return
     */
    public static Double getBigDec(double value) {
        DecimalFormat df = new DecimalFormat("###.0000000");
        return Double.valueOf(df.format(value));
    }

    /**
     * 转换seekbar的参数
     *
     * @param seekBar
     * @return
     */
    public static String getSeekBarValue(int seekBar) {
        String zero = "00";
        String one = "00";
        String values = "";
        if (seekBar < 0) {
            //将数据转化为负的十六进制
            values = Utils.IntToHex(65536 + seekBar);
        } else {
            values = Utils.IntToHex(seekBar);
        }
        switch (values.length()) {
            case 1:
                zero = "00";
                one = "00";
                break;
            case 2:
                zero = values.substring(0, 2);
                break;
            case 3:
                zero = values.substring(1, values.length());
                one = values.substring(0, 1);
                if (one.length() <= 1) {
                    one = "0" + one;
                }
                break;
            case 4:
                one = values.substring(0, 2);
                zero = values.substring(2, values.length());
                break;
        }
        String data = zero + " " + one + " " + "00 00 00 00 00 00";
        LogX.e("data:" + data);
        return data;
    }


    /*根据路径读取字节数组*/
    public static byte[] readFileToBytes(String filePath) {
        byte[] fileContent = null;
        File file = new File(filePath);
        FileInputStream fin = null;
        if (!file.isFile()) {
            Log.e("fileUtil", "file is null");
            return null;
        }
        try {
            fin = new FileInputStream(file);
            fileContent = new byte[(int) file.length()];
            fin.read(fileContent);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileContent;
    }

    /**
     * 十六进制字符串转十进制
     *
     * @param string 十六进制字符串
     * @return 十进制
     */
    public static int strHexNumber(String string) {
        int num = Integer.parseInt(string, 16);
        return (byte) num;
    }

    static final char[] HEX_CHAR_TABLE =
            new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String getHexString(byte[] raw, int offset, int count) {
        StringBuffer hex = new StringBuffer();
        for (int i = offset; i < offset + count; i++) {
            int v = raw[i] & 255;
            hex.append(HEX_CHAR_TABLE[v >>> 4]);
            hex.append(HEX_CHAR_TABLE[v & 15]);
            hex.append(" ");
        }
        return hex.toString();
    }

    public static String getHexString(byte raw) {
        StringBuffer hex = new StringBuffer();
        int v = raw & 255;
        hex.append(HEX_CHAR_TABLE[v >>> 4]);
        hex.append(HEX_CHAR_TABLE[v & 15]);
        return hex.toString();
    }

    public static int getHexToInt(byte raw) {
        String hexStr = getHexString(raw);
        return Integer.valueOf(hexStr, 16);
    }

    // 99 9F 33 01 build_time --高地位转换--转10进制  20160409
    public static long bytesToInt(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = bytes.length - 1; i > -1; i--) {
            sb.append(Utils.getHexString(bytes[i]));
        }
        return Long.valueOf(sb.toString(), 16);
    }

    public static float bytesToFloat(byte[] bytes2) {
        StringBuffer sb = new StringBuffer();
        for (int i = bytes2.length - 1; i > -1; i--) {
            sb.append(Utils.getHexString(bytes2[i]));
        }
        return Float.valueOf(Long.valueOf(sb.toString(), 16));
    }

    //A536F78E 不知道什么原因？？？？只能转string
    public static String bytesToString(byte[] bytes4) {
        StringBuffer sb = new StringBuffer();
        for (int i = 3; i > -1; i--) {
            sb.append(Utils.getHexString(bytes4[i]));
        }
        return sb.toString();
    }

    public static byte[] getStringhex(String ST) {
        char[] buffer = ST.replaceAll(" ", "").toCharArray();
        byte[] Byte = new byte[(buffer.length / 2)];
        int index = 0;
        int bit_st = 0;
        for (int i = 0; i < buffer.length; i++) {
            int v = buffer[i] & 255;
            if ((v <= 47 || v >= 58) && ((v <= 64 || v >= 71) && (v <= 96 || v >= 103))) {
                if (v == 32) {
                    //Log.v("getStringhex", "spance");
                    if (bit_st != 0) {
                        index++;
                        bit_st = 0;
                    }
                }
            } else if (bit_st == 0) {
                //Log.v("getStringhex", "F True");
                Byte[index] = (byte) (Byte[index] | (getASCvalue(buffer[i]) * 16));
                //Log.v("getStringhex", String.valueOf(Byte[index]));
                bit_st = 1;
            } else {
                Byte[index] = (byte) (Byte[index] | getASCvalue(buffer[i]));
                //Log.v("getStringhex", "F false");
                //Log.v("getStringhex", String.valueOf(Byte[index]));
                bit_st = 0;
                index++;
            }
        }
        return Byte;
    }

    public static byte getASCvalue(char in) {
        switch (in) {
            case '0':
                return (byte) 0;
            case '1':
                return (byte) 1;
            case '2':
                return (byte) 2;
            case '3':
                return (byte) 3;
            case '4':
                return (byte) 4;
            case '5':
                return (byte) 5;
            case '6':
                return (byte) 6;
            case '7':
                return (byte) 7;
            case '8':
                return (byte) 8;
            case '9':
                return (byte) 9;
            case 'A':
                return (byte) 10;
            case 'B':
                return (byte) 11;
            case 'C':
                return (byte) 12;
            case 'D':
                return (byte) 13;
            case 'E':
                return (byte) 14;
            case 'F':
                return (byte) 15;
            case 'a':
                return (byte) 10;
            case 'b':
                return (byte) 11;
            case 'c':
                return (byte) 12;
            case 'd':
                return (byte) 13;
            case 'e':
                return (byte) 14;
            case 'f':
                return (byte) 15;
            default:
                return (byte) 0;
        }
    }

    /**
     * 十六进制字符串转float.
     *
     * @param hexStr
     * @return
     * @throws
     */
//    public static Float hexStr2Float(String hexStr) throws Exception {
//        Float result = null;
//        // 先通过apahce的 hex类转换十六进制字符串为byte数组. 也可以自己用JDK原声的方法来循环转
//        // Character.digit(ch, 16);
//        byte[] decodes = org.apache.commons.codec.binary.Hex.decodeHex(hexStr.toCharArray());
//        // 获得byte转float的结果
//        result = getFloat(decodes, 0);
//        return result;
//    }

    /**
     * 通过byte数组取得float
     *
     * @param b
     * @param index 第几位开始取.
     * @return
     */
    public static float getFloat(byte[] b, int index) {
        int l;
        l = b[index + 0];
        l &= 0xff;
        l |= ((long) b[index + 1] << 8);
        l &= 0xffff;
        l |= ((long) b[index + 2] << 16);
        l &= 0xffffff;
        l |= ((long) b[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }


    /**
     * 遍历指定路径的视频
     * Description
     *
     * @see [class/class#field/class#method]
     */
//    public List<SDcardImageBean> getImageVideoData(Context mContext) {
//
//        List<SDcardImageBean> list = new ArrayList<SDcardImageBean>();
//        ContentResolver resolver = mContext.getContentResolver();
//        //查找视频
//        Cursor cursor = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                String path = cursor.getString(cursor.getColumnIndex("_data"));
//                //截取最后一个反斜杠之前的字符串
//                String name = path.substring(0, path.lastIndexOf("/") + 1);
//                String fileName = path.substring(path.lastIndexOf("/"), path.length());
//                if (name.endsWith(DOWNLAOD_SHARE_IMAGE)) {
//                    SDcardImageBean bean = new SDcardImageBean();
//                    bean.setId(cursor.getInt(cursor.getColumnIndex("_ID")));
//                    bean.setPath(cursor.getString(cursor.getColumnIndex("_data")));// 路径
//                    bean.setDate(getFileDtae(path));// 日期
//                    if (fileName.trim().toLowerCase().endsWith(".mp4")) {
//                        bean.setPlayTime(secToTimeInt((int) getVideoDetial(path) / 1000));// 播放时长
//                        bean.setIsPicture(1);
//                        Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(resolver, bean.getId(), MediaStore.Video.Thumbnails.MICRO_KIND, null);
//                        bean.setBitmap(bitmap);
//                    }
//                    bean.setCheck(false);
//                    list.add(bean);
//                }
//            }
//        }
//        //查找图片
//        Cursor cursorImage = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
//        if (cursorImage != null) {
//            while (cursorImage.moveToNext()) {
//                String path = cursorImage.getString(cursorImage.getColumnIndex("_data"));
//                //截取最后一个反斜杠之前的字符串
//                String name = path.substring(0, path.lastIndexOf("/") + 1);
//                String fileName = path.substring(path.lastIndexOf("/"), path.length());
//                if (name.endsWith(DOWNLAOD_SHARE_IMAGE)) {
//                    SDcardImageBean bean = new SDcardImageBean();
//                    bean.setId(cursorImage.getInt(cursorImage.getColumnIndex("_ID")));
//                    bean.setPath(cursorImage.getString(cursorImage.getColumnIndex("_data")));// 路径
//                    bean.setDate(getFileDtae(path));// 日期
//                    bean.setIsPicture(0);
//                    Bitmap thumbnail = MediaStore.Images.Thumbnails.getThumbnail(resolver, bean.getId(), MediaStore.Images.Thumbnails.MICRO_KIND, null);
//                    bean.setBitmap(thumbnail);
//                    bean.setCheck(false);
//                    list.add(bean);
//                }
//            }
//        }
//        cursor.close();
//        cursorImage.close();
//        Collections.sort(list, new VideoManagerComparator());
//        return list;
//    }

//    public SDcardImageBean getSdcardImage(Context mContext, String sdcardImagePath) {
//        SDcardImageBean bean = new SDcardImageBean();
//        String name = sdcardImagePath.substring(0, sdcardImagePath.lastIndexOf("/") + 1);
//        LogX.e("getSdcardImage name:" + name + "   ,sdcardImagePath:" + sdcardImagePath);
//        bean.setId((int) System.currentTimeMillis() / 10000000);
//        bean.setPath(sdcardImagePath);// 路径
//        bean.setDate(getFileDtae(sdcardImagePath));// 日期
//        bean.setIsPicture(0);
//        bean.setBitmap(getImageThumbnail(sdcardImagePath, 80, 80));
//        bean.setCheck(false);
//        LogX.e("showImage:" + getImageThumbnail(sdcardImagePath, 80, 80) + "    ,getFileDtae(sdcardImagePath):" + getFileDtae(sdcardImagePath));
//        return bean;
//    }


//    public SDcardImageBean getSdcardVideo(Context mContext, String sdcardVideoPath) {
//        SDcardImageBean bean = new SDcardImageBean();
//        bean.setId((int) System.currentTimeMillis() / 10000000);
//        bean.setPath(sdcardVideoPath);// 路径
//        bean.setDate(getFileDtae(sdcardVideoPath));// 日期
//        String fileName = sdcardVideoPath.substring(sdcardVideoPath.lastIndexOf("/"), sdcardVideoPath.length());
//        if (fileName.trim().toLowerCase().endsWith(".mp4")) {
//            bean.setPlayTime(secToTimeInt((int) getVideoDetial(sdcardVideoPath) / 1000));// 播放时长
//            bean.setIsPicture(1);
//            bean.setBitmap(getVideoThumbnail(sdcardVideoPath, 100, 100, MediaStore.Images.Thumbnails.MICRO_KIND));
//            LogX.e("时长:" + secToTimeInt((int) getVideoDetial(sdcardVideoPath) / 1000) + "   ,sdcardVideoimage:" + getVideoThumbnail(sdcardVideoPath, 100, 100, MediaStore.Images.Thumbnails.MICRO_KIND));
//
//        }
//        bean.setCheck(false);
//        return bean;
//    }


    /**
     * 视频和图片管理排序
     */
//    public static class VideoManagerComparator implements Comparator<Object> {
//
//        public int compare(Object arg0, Object arg1) {
//
//            SDcardImageBean mSourceVideoBean0 = (SDcardImageBean) arg0;
//            SDcardImageBean mSourceVideoBean1 = (SDcardImageBean) arg1;
//            int flag = String.valueOf(mSourceVideoBean1.getDate()).compareTo(String.valueOf(mSourceVideoBean0.getDate()));
//            return flag;
//        }
//    }

    /**
     * 获取指定路径下文件的时间
     * Description
     *
     * @param path
     * @return 是11位数字
     * @see [class/class#field/class#method]
     */
    public static long getFileDtae(String path) {

        File mFile = new File(path);
        return mFile.lastModified();
    }


    /**
     * 获取smba服务器视频的时长
     *
     * @param path
     * @return
     */
//    public static long getSmbaFileDate(String path) {
//        try {
//            SmbFile smbFile = new SmbFile(path);
//            return smbFile.lastModified();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//
//    }

    /**
     * 获取视频的时长
     * Description
     *
     * @param filePath
     * @see [class/class#field/class#method]
     */
    public static long getVideoDetial(String filePath) {

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        long duration = 0;
        try {
            mmr.setDataSource(filePath);
            duration = Long.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)); // 播放时长单位为毫秒
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return duration;
    }

    /**
     * 返回分秒
     *
     * @param time
     * @return
     */
    public static String secToTimeInt(int time) {

        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    private static String unitFormat(int i) {

        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * 读取图片
     * Description
     *
     * @param uri
     * @param uri
     * @return
     * @see [class/class#field/class#method]
     */
//    public static Bitmap showImage(String uri) {
//        Bitmap btp = null;
//        try {
//            SmbFileInputStream sTS = new SmbFileInputStream(uri);
//            BufferedInputStream bs = new BufferedInputStream(sTS);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 2;
//            btp = BitmapFactory.decodeStream(bs, null, options);
//            bs.close();
//            sTS.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return btp;
//    }


    /**
     * @param mFloat 参数值
     * @param keep   需要保留的几位小数 一位小数10  两位100 以此类推
     * @return 返回处理的值
     */
    public static float round(float mFloat, int keep) {
        return (float) (Math.round(mFloat * keep)) / keep;//(这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)
    }

    /**
     * 读取smba服务器图片和视频排序
     */
//    public static class SambaFileComparator implements Comparator<Object> {
//
//        public int compare(Object arg0, Object arg1) {
//
//            AirImageBean mAddSourceVideoBean0 = (AirImageBean) arg0;
//            AirImageBean mAddSourceVideoBean1 = (AirImageBean) arg1;
//            int flag = String.valueOf(mAddSourceVideoBean1.getDate()).compareTo(String.valueOf(mAddSourceVideoBean0.getDate()));
//            return flag;
//        }
//    }


    /**
     * 转换大图,防止图片导致内存泄漏
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 判断手机是否GSP模块
     *
     * @param context
     * @return
     */
    public static boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) HubsanApplication.getApplication().getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    /**
     * 计算两个GPS之间的距离
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return 距离
     */
    public static double getDistance(double lat1, double lon1,double lat2, double lon2) {
        float[] results = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        return results[0];
    }

    /**
     * 计算预测经纬度
     *
     * @param oneLat
     * @param oneLon
     * @param twoLat
     * @param twoLon
     */
    public static String calculateLatLon(double oneLat, double oneLon, double twoLat, double twoLon) {
        StringBuffer sbDH1, sbDV1;
        //得到水平距离和速度
        double v1distance = Utils.getDistance(oneLat, oneLon, twoLat, oneLon);
        float v1speed = (float) v1distance / 0.5f;

        //得到垂直距离和速度
        double v2distance = Utils.getDistance(oneLat, oneLon, oneLat, twoLon);
        float v2speed = (float) v2distance / 0.5f;

        //dh1= 2   对应到倒数第三位  lat  分米 倒数第二位    里面 倒数第一位
        DecimalFormat df = new DecimalFormat("######0.00");
        double dh1 = Double.valueOf(df.format(v1speed * 0.2f));
        //dh1= 2   对应到倒数第三位  lon
        double dv1 = Double.valueOf(df.format(v2speed * 0.2f));
//        LogX.e("水平距离:" + dh1 + "   ,垂直距离:" + dv1);

        //将得到的水平速度转化为七位数值 0.46
        if (dh1 != 0.0) {
            sbDH1 = new StringBuffer(Utils.div(dh1, 1000.0).toString());
            sbDH1.insert(3, "00");
        } else {
            sbDH1 = new StringBuffer();
            sbDH1.append("0.0000000");
        }
        //将得到的垂直速度转化为七位数值
        if (dv1 != 0.0) {
            sbDV1 = new StringBuffer(Utils.div(dv1, 1000.0).toString());
            sbDV1.insert(3, "00");
        } else {
            sbDV1 = new StringBuffer();
            sbDV1.append("0.0000000");
        }

//        if (twoLat > oneLat) {
//            Gps.lat = Utils.add(twoLat, Double.valueOf(sbDH1.toString()));
//        } else {
//            Gps.lat = Utils.sub(twoLat, Double.valueOf(sbDH1.toString()));
//        }
//
//        if (twoLon > oneLon) {
//            Gps.lon = Utils.add(twoLon, Double.valueOf(sbDV1.toString()));
//        } else {
//            Gps.lon = Utils.sub(twoLon, Double.valueOf(sbDV1.toString()));
//        }
//        StringBuffer sb = new StringBuffer();
//        sb.append("处理后:" + "\n 经度:" + Gps.lon).append("\n纬度:" + Gps.lat);
//        return sb.toString();
        return "";

    }

    /**
     * 传递两个参数相除,并且将得到的结果设置为7位小数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double doubleDiv(double d1, double d2) {
        StringBuffer sb = new StringBuffer(Utils.div(d1, d2).toString());
        sb.insert(3, "00");
        return Double.valueOf(sb.toString());
    }


    static {
        myHandlerThread = new HandlerThread("AppUtils");
        myHandlerThread.start();
        myHandler = new Handler(myHandlerThread.getLooper());
    }

    /**
     * 开始一个线程,在多个地方都可以调用
     *
     * @param delayMs
     * @param command
     */
    public static void runOnMyThread(int delayMs, Runnable command) {
        LogX.e("myHandler:" + myHandler);
        if (myHandler != null) {
            if (delayMs <= 0)
                myHandler.post(command);
            else
                myHandler.postDelayed(command, delayMs);
        }
    }


    /**
     * 转换IP地址
     *
     * @param i
     * @return
     */
    public static String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    /**
     * 判断两个double之间的大小
     *
     * @param val1
     * @param val2
     * @return
     */
    public static int compare(BigDecimal val1, BigDecimal val2) {
        int result = -10;
        //第二位数大
        if (val1.compareTo(val2) < 0) {
            result = -1;
        }
        //"两位数一样大！
        if (val1.compareTo(val2) == 0) {
            result = 0;
        }
        //第一位数大！
        if (val1.compareTo(val2) > 0) {
            result = 1;
        }
        return result;
    }

    /**
     * 传入秒转换成 00:00:00
     *
     * @param second 秒数
     * @return
     */
    public static String formatSecond(int second) {
        String html = "";
        if (second <= 0) {
            return "00:00:00";
        } else {
            Integer hours = second / (60 * 60);
            Integer minutes = second / 60 - hours * 60;
            Integer seconds = second - minutes * 60 - hours * 60 * 60;
            if (hours > 0) {
                html = unitFormat(hours) + ":" + unitFormat(minutes) + ":" + unitFormat(seconds);
            } else if (minutes > 0) {
                html = unitFormat(hours) + ":" + unitFormat(minutes) + ":" + unitFormat(seconds);
            } else {
                html = unitFormat(hours) + ":" + unitFormat(minutes) + ":" + unitFormat(seconds);
            }
        }
        return html;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            try {
                //如果仅仅是用来判断网络连接 　　　　　　
                //则可以使用 cm.getActiveNetworkInfo().isAvailable();
                NetworkInfo[] info = cm.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * long 转为字节
     *
     * @param num long
     * @return
     */
    public static byte[] long2Bytes(long num) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }

    /**
     * 直接转为long
     *
     * @param byteNum 字节
     * @return
     */
    public static long bytes2Long(byte[] byteNum) {
        long num = 0;
        for (int ix = 0; ix < 8; ++ix) {
            num <<= 8;
            num |= (byteNum[ix] & 0xff);
        }
        return num;
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className 某个界面名称
     */
    public static boolean isForeground(Context context, String className) {
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

    /**
     * 设置全屏
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置隐藏标题栏
     *
     * @param activity
     */
    public static void setNoTitleBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 将大图压缩
     *
     * @param bigBitmap
     * @return
     */
    public static Bitmap bigBitmapToSmall(int bigBitmap) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 1;    //这个的值压缩的倍数（2的整数倍），数值越小，压缩率越小，图片越清晰
        return BitmapFactory.decodeResource(HubsanApplication.getApplication().getResources(), bigBitmap, opts);
    }

    /**
     * 获取当前系统语言
     * Description
     *
     * @return true:中文  false:英文
     * @see [class/class#field/class#method]
     */
    public static boolean isZh() {

        Locale locale = HubsanApplication.getApplication().getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }

    /**
     * 保存位图到本地
     *
     * @param bitmap
     * @param path   本地路径
     * @return void
     */
    public static void SavaImage(Bitmap bitmap, String path) {
        File file = new File(path);
        FileOutputStream fileOutputStream = null;
        //文件夹不存在，则创建它
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            fileOutputStream = new FileOutputStream(file + "/" + "username" + ".png");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savaADImage(Context mContext, Bitmap bitmap, String path, String imageName) {
        File file = new File(path);
        FileOutputStream fileOutputStream = null;
        //文件夹不存在，则创建它
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            fileOutputStream = new FileOutputStream(file + "/" + imageName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            MediaScannerConnection.scanFile(mContext, new String[]{path}, null, null);
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断路径是否有效
     *
     * @param path
     * @return
     */
    public boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 根据指定的图像路径和大小来获取缩略图
     * 此方法有两点好处：
     * 1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
     * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。
     * 2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
     * 用这个工具生成的图像不会被拉伸。
     *
     * @param imagePath 图像的路径
     * @param width     指定输出图像的宽度
     * @param height    指定输出图像的高度
     * @return 生成的缩略图
     */
    public Bitmap getImageThumbnail(String imagePath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath 视频的路径
     * @param width     指定输出视频缩略图的宽度
     * @param height    指定输出视频缩略图的高度度
     * @param kind      参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    public Bitmap getVideoThumbnail(String videoPath, int width, int height,
                                    int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    public Bitmap GetImageInputStream(final String imageurl) {
        URL url;
        HttpURLConnection connection = null;
        Bitmap bitmap = null;
        try {
            url = new URL(imageurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            LogX.e("GetImageInputStream:");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 根据类型 转换 坐标
     */
//    public LatLng convert(LatLng sourceLatLng, Context context) {
//        CoordinateConverter converter = new CoordinateConverter(context);
//        // CoordType.GPS 待转换坐标类型
//        converter.from(CoordinateConverter.CoordType.GPS);
//        // sourceLatLng待转换坐标点
//        converter.coord(sourceLatLng);
//        // 执行转换操作
//        LatLng desLatLng = converter.convert();
//        return desLatLng;
//    }

    /**
     * 将应用退到桌面上,保留自身
     *
     * @param context
     */
    public static void makeAppBackToHome(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
        LogX.e("----退到后台");
    }

    /**
     * 退到桌面 并且结束当前应用
     *
     * @param context
     */
    public static void backHomeFinishSelf(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
        LogX.e("----退到桌面 并且结束当前应用");
    }

    /**
     * 判断是否超过24小时
     *
     * @param start
     * @param end
     * @return boolean
     * @throws Exception
     */
    public static boolean judgmentDate(long start, long end) throws Exception {
        long cha = end - start;
        if (cha < 0) {
            return false;
        }
        double result = cha * 1.0 / (1000 * 60 * 60);
        if (result <= 24) {
            return true;
        } else {
            return false;
        }
    }
    public double sevenDouble(double value) {
        BigDecimal b = new BigDecimal(value);
        return b.setScale(7, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
