package com.quark.wanlihuanyunuser.ui.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class ActionSheetdownloadDelete {
    static String sexstr;
    static String xueliStr;
    static LinearLayout layout;
    static Context context;
    static String user_id;

    public interface OnActionSheetSelected {
        void onClick(int whichButton);
    }

    private ActionSheetdownloadDelete() {
    }

    static ArrayList<TextView> d = new ArrayList<TextView>();

    //=======sex start======
    //性别
    public static Dialog showSheetSex(final Context context, OnCancelListener cancelListener, final Handler handler) {
        SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
        user_id = sp.getString("user_id", "");

        final Dialog dlg = new Dialog(context, R.style.ActionSheet);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.actionsheetdowndelete, null);
        final int cFullFillWidth = 10000;
        layout.setMinimumWidth(cFullFillWidth);
        final TextView sex_man = (TextView) layout.findViewById(R.id.sex_man);
        final TextView down = (TextView) layout.findViewById(R.id.down);
        final TextView sex_lady = (TextView) layout.findViewById(R.id.sex_lady);
        final TextView cancel = (TextView) layout.findViewById(R.id.cancel);

        sex_man.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
                dlg.dismiss();
            }
        });

        down.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 3;
                handler.sendMessage(msg);
                dlg.dismiss();
            }
        });

        sex_lady.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
                dlg.dismiss();
            }
        });
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(false);
        if (cancelListener != null)
            dlg.setOnCancelListener(cancelListener);

        dlg.setContentView(layout);
        dlg.show();

        return dlg;
    }

}
