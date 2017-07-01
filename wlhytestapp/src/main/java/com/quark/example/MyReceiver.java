package com.quark.example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.AppStart;
import com.quark.wanlihuanyunuser.mainview.MainActivity;
import com.quark.wanlihuanyunuser.ui.home.NoticeDetailActivity;
import com.quark.wanlihuanyunuser.ui.personal.CourierDetailsActivity;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            openNotification(context, bundle);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String myValue = "";
        String notices_id = "";
        String titleStr = "";
        try {
            JSONObject extrasJson = new JSONObject(extras);
            myValue = extrasJson.optString("type");
            notices_id = extrasJson.optString("notices_id");
            titleStr = extrasJson.optString("title");
        } catch (Exception e) {
            System.out.println("Unexpected: extras is not a valid json");
            return;
        }
        //如果app打开 更新首页底部公告
        if ("1".equals(myValue) && MainActivity.instance != null && new AppParam().isLogin(context)) {
            MainActivity.instance.showNotice(titleStr, notices_id);
        }
    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String myValue = "";
        String notices_id = "";
        String titleStr = "";
        String waybill_number;
        try {
            JSONObject extrasJson = new JSONObject(extras);
            myValue = extrasJson.optString("type");
            notices_id = extrasJson.optString("notices_id");
            titleStr = extrasJson.optString("title");
            waybill_number = extrasJson.optString("waybill_number");
        } catch (Exception e) {
            System.out.println("Unexpected: extras is not a valid json");
            return;
        }

        //先进到 首页 在打开消息页面
        if (MainActivity.instance != null && new AppParam().isLogin(context)) {
            Intent main = new Intent(context, MainActivity.class);
            main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(main);

            if ("1".equals(myValue)) { //type=1 公告，type=2 快递。
                Intent in = new Intent(context, NoticeDetailActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                in.putExtra("notices_id", notices_id);
                context.startActivity(in);

                MainActivity.instance.showNotice(titleStr, notices_id);
            } else {
                Intent mIntent = new Intent(context, CourierDetailsActivity.class);
                mIntent.putExtra("waybill_number", waybill_number);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(mIntent);
            }
        } else {
            Intent mIntent = new Intent(context, AppStart.class);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
        }
    }
}
