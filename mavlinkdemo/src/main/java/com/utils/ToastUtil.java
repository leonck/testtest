package com.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hubsan.mavlinkdemo.R;
import com.hubsansdk.application.HubsanApplication;

/**
 * Description
 * 吐司工具类
 *
 * @author ShengKun.Cheng
 * @date 2015年8月18日
 * @see [class/class#method]
 * @since [product/model]
 */
public class ToastUtil {

    public void showToast(int resID) {
        showToast(HubsanApplication.getInstance(), Toast.LENGTH_SHORT,
                resID);
    }

    public void showToast(String text) {
        showToast(HubsanApplication.getInstance(), Toast.LENGTH_SHORT, text);
    }

    public void showToast(Context ctx, int resID) {
        showToast(ctx, Toast.LENGTH_SHORT, resID);
    }

    public void showToast(Context ctx, String text) {
        showToast(ctx, Toast.LENGTH_SHORT, text);
    }

    public void showLongToast(Context ctx, int resID) {
        showToast(ctx, Toast.LENGTH_LONG, resID);
    }

    public void showLongToast(int resID) {
        showToast(HubsanApplication.getInstance(), Toast.LENGTH_LONG, resID);
    }

    public void showLongToast(Context ctx, String text) {
        showToast(ctx, Toast.LENGTH_LONG, text);
    }

    /**
     * 传入需要显示的内容,长时间显示
     *
     * @param text
     */
    public void showLongToast(String text) {
        showToast(HubsanApplication.getInstance(), Toast.LENGTH_LONG, text);
    }

    public void showToast(Context ctx, int duration, int resID) {
        showToast(ctx, duration, ctx.getString(resID));
    }

    /**
     * Toast一个图片
     */
    public Toast showToastImage(Context ctx, int resID) {
        final Toast toast = Toast.makeText(ctx, "", Toast.LENGTH_SHORT);
        View mNextView = toast.getView();
        if (mNextView != null)
            mNextView.setBackgroundResource(resID);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return toast;
    }

    public void showToast(final Context ctx, final int duration,
                          final String text) {
        final Toast toast = Toast.makeText(ctx, text, duration);
        View view = RelativeLayout.inflate(ctx, R.layout.hubsan_toast_utils_layout, null);
        TextView mNextView = (TextView) view.findViewById(R.id.hubsan_toast_name);
        toast.setView(view);
        mNextView.setText(text);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 在UI线程运行弹出
     */
    public void showToastOnUiThread(final Activity ctx, final String text) {
        if (ctx != null) {
            ctx.runOnUiThread(new Runnable() {
                public void run() {
                    showToast(ctx, text);
                }
            });
        }
    }
}
