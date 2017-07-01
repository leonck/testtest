package com.quark.wanlihuanyunuser.mainview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loveplusplus.update.UpdateChecker;
import com.quark.api.auto.bean.CloseNnoticeRequest;
import com.quark.api.auto.bean.CloseNnoticeResponse;
import com.quark.api.auto.bean.LeftList;
import com.quark.api.auto.bean.NoticeRequest;
import com.quark.api.auto.bean.NoticeResponse;
import com.quark.example.ExampleUtil;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppManager;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.LeftAdapter;
import com.quark.wanlihuanyunuser.api.ApiHttpClient;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.ui.home.ChargeStandardActivity;
import com.quark.wanlihuanyunuser.ui.home.ExRateActivity;
import com.quark.wanlihuanyunuser.ui.home.NoticeDetailActivity;
import com.quark.wanlihuanyunuser.ui.home.OpinionActivity;
import com.quark.wanlihuanyunuser.ui.home.ServiceActivity;
import com.quark.wanlihuanyunuser.ui.personal.MyCourierActivity;
import com.quark.wanlihuanyunuser.util.TLog;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cz.msebera.android.httpclient.Header;
import pub.devrel.easypermissions.EasyPermissions;

@SuppressLint("ResourceAsColor")
public class MainActivity extends FragmentActivity implements OnClickListener, EasyPermissions.PermissionCallbacks {
    private FragmentOne oneFragment;
    private FragmentTwo twoFragment;
    private FragmentThree threeFragment;
    private View oneLayout;
    private View twoLayout;
    private View threeLayout;

    private ImageView oneImage;
    private ImageView twoImage;
    private ImageView threeImage;

    private TextView oneText;
    private TextView twoText;
    private TextView threeText;

    private int current = 0;
    private int precurrent = 0;

    private FragmentManager fragmentManager;
    public static MainActivity instance;

    public static boolean isForeground = false;
    public static boolean out = false;
    private LocationManager manager;

    ArrayList<LeftList> datas;
    LeftAdapter adapter;
    GridView gv;
    DrawerLayout menuLayout;
    LinearLayout msgLy;
    TextView msgTv;
    String notices_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            oneFragment = (FragmentOne) fragmentManager.findFragmentByTag("oneFragment");
            twoFragment = (FragmentTwo) fragmentManager.findFragmentByTag("twoFragment");
            threeFragment = (FragmentThree) fragmentManager.findFragmentByTag("threeFragment");
        }
        AppManager.getAppManager().addActivity(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //侧滑菜单
        menuLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        gv = (GridView) findViewById(R.id.gridView);
        initLeftView();

        instance = this;
        SharedPreferences sp = getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, MODE_PRIVATE);
        if (new AppParam().isLogin(this)) {
            Editor edit = sp.edit();
            edit.putString("role", "nologin");
            edit.commit();
        }

        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int sw = outMetrics.widthPixels;
        int h = outMetrics.heightPixels;

        Editor edit = sp.edit();
        edit.putInt("screenWidth", sw);
        edit.putInt("screenHeight", h);
        edit.commit();

        initViews();
        setTabSelection(current);
        noticeRequest();
        startByPermissions();

        registerBoradcastReceiverJP();
        registerMessageReceiver(new AppParam().getUser_id(this));

        startUpdatebyPermissions();
    }

    /**
     * 公告请求
     *
     * @author pan
     * @time 2016/11/24 0024 上午 9:53
     */
    public void noticeRequest() {
        NoticeRequest gr = new NoticeRequest();
        gr.setUser_type("1");
        QuarkApi.HttpRequest(gr, mHandlernotice);
    }

    private final AsyncHttpResponseHandler mHandlernotice = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, MainActivity.this, NoticeResponse.class);
            if (kd != null) {
                NoticeResponse info = (NoticeResponse) kd;
                if (info.getStatus() == 1 && info.getIs_read().equals("1")) {
                    msgLy.setVisibility(View.VISIBLE);
                    msgTv.setText(info.getNotices().getTitle());
                    notices_id = info.getNotices().getNotices_id() + "";
                } else {

                }
            }
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
        }
    };

    private void initLeftView() {
        datas = new ArrayList<>();
        adapter = new LeftAdapter(this, datas);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent in1 = new Intent(MainActivity.this, ExRateActivity.class);
                    startActivity(in1);
                    menuLayout.closeDrawers();
                } else if (position == 1) {
                    Intent in2 = new Intent(MainActivity.this, MyCourierActivity.class);
                    startActivity(in2);
                    menuLayout.closeDrawers();
                } else if (position == 2) {
                    Intent in3 = new Intent(MainActivity.this, ChargeStandardActivity.class);
                    startActivity(in3);
                    menuLayout.closeDrawers();
                } else if (position == 3) {
                    Intent in4 = new Intent(MainActivity.this, OpinionActivity.class);
                    startActivity(in4);
                    menuLayout.closeDrawers();
                } else if (position == 4) {
                    Intent in5 = new Intent(MainActivity.this, ServiceActivity.class);
                    startActivity(in5);
                    menuLayout.closeDrawers();
                }
            }
        });
    }

    private void initViews() {
        oneLayout = findViewById(R.id.one_layout);
        twoLayout = findViewById(R.id.two_layout);
        threeLayout = findViewById(R.id.three_layout);

        oneImage = (ImageView) findViewById(R.id.one_image);
        twoImage = (ImageView) findViewById(R.id.two_image);
        threeImage = (ImageView) findViewById(R.id.three_image);

        oneText = (TextView) findViewById(R.id.one_text);
        twoText = (TextView) findViewById(R.id.two_text);
        threeText = (TextView) findViewById(R.id.three_text);
        //公告信息
        msgLy = (LinearLayout) findViewById(R.id.msg_ly);
        msgTv = (TextView) findViewById(R.id.msg);

        oneLayout.setOnClickListener(this);
        twoLayout.setOnClickListener(this);
        threeLayout.setOnClickListener(this);
        msgLy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        precurrent = current;
        int id = v.getId();
        if (id == R.id.one_layout) {
            current = 0;
            setTabSelection(0);
        } else if (id == R.id.two_layout) {
            showPopwindow();
        } else if (id == R.id.three_layout) {
            current = 2;
            setTabSelection(2);
        } else if (id == R.id.msg_ly) {
            Intent in = new Intent(this, NoticeDetailActivity.class);
            in.putExtra("notices_id", notices_id);
            startActivity(in);
            msgLy.setVisibility(View.GONE);
        }
    }

    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                oneImage.setImageResource(R.drawable.tab_1_1);
                oneText.setTextColor(ContextCompat.getColor(this, R.color.blue));
                if (oneFragment == null) {
                    oneFragment = new FragmentOne();
                    transaction.add(R.id.content, oneFragment, "oneFragment");
                } else {
                    transaction.show(oneFragment);
                }
                break;
            case 1:
                /*twoImage.setImageResource(R.drawable.tab_2);
//                twoText.setTextColor(getResources().getColor(R.color.color_lv));
                if (new AppParam().isLogin(this)) {
                    if (twoFragment == null) {
                        twoFragment = new FragmentTwo();
                        transaction.add(R.id.content, twoFragment, "twoFragment");
                    } else {
                        transaction.show(twoFragment);
                    }
                } else {
                    twoFragment = new FragmentTwo();
                    transaction.add(R.id.content, twoFragment, "twoFragment");
                }*/
                break;
            case 2:
                threeImage.setImageResource(R.drawable.tab_3);
                threeText.setTextColor(getResources().getColor(R.color.blue));
                menuLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//关闭手势滑动
                if (threeFragment == null) {
                    threeFragment = new FragmentThree();
                    transaction.add(R.id.content, threeFragment, "threeFragment");
                } else {
                    transaction.show(threeFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void showPopwindow() {
        Intent intent = new Intent().setClass(this, SentActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.myactivity_up_in, 0);
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        oneImage.setImageResource(R.drawable.tab_1);
        oneText.setTextColor(Color.parseColor("#82858b"));
        twoImage.setImageResource(R.drawable.tab_2);
//        twoText.setTextColor(Color.parseColor("#82858b"));
        threeImage.setImageResource(R.drawable.tab_3_1);
        threeText.setTextColor(Color.parseColor("#82858b"));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (oneFragment != null) {
            transaction.hide(oneFragment);
        }
        if (threeFragment != null) {
            transaction.hide(threeFragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            exitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitApp() {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.setTitle("退出提示");
        dlg.setMessage("是否确定退出？");
        dlg.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        dlg.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dlg.cancel();
            }
        });
        dlg.show();
    }

    private void startByPermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {

        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permissions_request), 1, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        try {
            if (requestCode == 10002) {
                checkupdate();
            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.permissions_map_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, R.string.permissions_map_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public void showNotice(String titlestr, String notices_id) {
        this.notices_id = notices_id;
        msgLy.setVisibility(View.VISIBLE);
        msgTv.setText(titlestr);
    }

    public void hiteNotice() {
        msgLy.setVisibility(View.GONE);
    }

    @OnClick(R.id.close)
    public void msgclose(View v) {
        msgLy.setVisibility(View.GONE);
        gethiteNoticeData();
    }

    public void gethiteNoticeData() {
        CloseNnoticeRequest rq = new CloseNnoticeRequest();
        rq.setNotices_id(notices_id);
        rq.setUser_type(AppParam.user_type);
        QuarkApi.HttpRequest(rq, clmHandler);
    }

    private final AsyncHttpResponseHandler clmHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, MainActivity.this, CloseNnoticeResponse.class);
            if (kd != null) {
            }
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);

        }
    };

    //=================================极光推送======================================================
    private static final String TAG = "JPush";
    private ReceiveBroadCastJP receiveBroadCastJP;

    public void registerBoradcastReceiverJP() {
        receiveBroadCastJP = new ReceiveBroadCastJP();
        IntentFilter filter = new IntentFilter();
        filter.addAction("wlhyuser_broadJP");   // 只有持有相同的action的接受者才能接收此广播
        registerReceiver(receiveBroadCastJP, filter);
    }

    public class ReceiveBroadCastJP extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent data) {
            String opertion = data.getStringExtra("opertion");
            if (opertion.equals("out")) {
                registerMessageReceiver("");
            } else if (opertion.equals("showNotice")) {
                //显示公告
//                String titlestr = data.getStringExtra("titlestr");
//                notices_id = data.getStringExtra("notices_id");
//                showNotice(titlestr);
            } else {
                registerMessageReceiver(new AppParam().getUser_id(MainActivity.this));
            }
        }
    }

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver(String telephone) {
        TLog.error("telephone=====================" + telephone);
//        String md5Phone = com.jfinal.kit.EncryptionKit.md5Encrypt(telephone);
//        TLog.error("md5Phone=====================" + md5Phone);
        setTag(telephone);
        setAlias(telephone);
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
        setStyleCustom();   //设置样式
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
            }
        }
    }

    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;
                case MSG_SET_TAGS:
                    JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
                    break;

                default:
            }
        }
    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success======alias";
                    Log.i(TAG, logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.======alias";
                    Log.i(TAG, logs);
                    if (ExampleUtil.isConnected(getApplicationContext())) {
//	                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network======alias");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
        }
    };

    /**
     * 设置通知栏样式 - 定义通知栏Layout
     */
    private void setStyleCustom() {
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(MainActivity.this, R.layout.customer_notitfication_layout, R.id.icon, R.id.title, R.id.text);
        builder.statusBarDrawable = R.drawable.ic_launcher;
        builder.layoutIconDrawable = R.drawable.ic_launcher;
        builder.developerArg0 = "developerArg2";
        JPushInterface.setPushNotificationBuilder(2, builder);
//			Toast.makeText(PushSetActivity.this,"Custom Builder - 2", Toast.LENGTH_SHORT).show();
    }

    /**
     * @ClassName: MainActivity.java
     * @Description: 设置tag
     * @author howe
     * @date 2015-6-17
     * @version V1.0
     */
    private void setTag(String tag) {
        Set<String> tagSet = new LinkedHashSet<String>();
        tagSet.add(tag);
//			tagSet.add(tag2);
        //调用JPush API设置Tag
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));
    }

    /**
     * @ClassName: MainActivity.java
     * @Description: 设置alias
     * @author howe
     * @date 2015-6-17
     * @version V1.0
     */
    private void setAlias(String alias) {
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    //
    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success======tag";
                    Log.i(TAG, logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.======tag";
                    Log.i(TAG, logs);
                    if (ExampleUtil.isConnected(getApplicationContext())) {
//		                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network======tag");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }

//		            ExampleUtil.showToast(logs, getApplicationContext());
        }

    };

    @Override
    public void onDestroy() {
        try {
            if (receiveBroadCastJP != null)
                unregisterReceiver(receiveBroadCastJP);
            if (mMessageReceiver != null)
                unregisterReceiver(mMessageReceiver);

        } catch (Exception e) {
            Log.e("error", "MainActivity 销毁出错");
        }
        super.onDestroy();
    }
    //=================================极光推送end===================================================

    //=============更新===================
    private void startUpdatebyPermissions() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            try {
                checkupdate();
            } catch (Exception e) {
                Toast.makeText(this, "缺少运行权限,请正确授权", Toast.LENGTH_LONG).show();
            }
        } else {
            EasyPermissions.requestPermissions(this, "申请运行所需的权限，如果拒绝将影响您的正常使用", 10002, perms);
        }
    }

    public void checkupdate() {
//        showWait(true);
        String checkUrl = ApiHttpClient.HOSTURL + "/app/Setting/androidAutoUpdate?method=get&type=1&versionCode=" + Utils.getVersionCode(this);
        TLog.error(checkUrl);
        UpdateChecker.checkForDialog(this, checkUrl, ApiHttpClient.HOSTURL, handlerupdate, true);
    }

    private Handler handlerupdate = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
//                    showWait(false);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

        ;
    };
    //=============end===================
}
