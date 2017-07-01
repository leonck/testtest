package com.hubsan.swifts.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hubsan.swifts.R;

/**
 * 自定义对话框
 * Created by chengshengkun on 16/11/1.
 */

public class CommonDialog extends Dialog {
    private Context context;
    private String title;
    private String confirmButtonText;
    private String cacelButtonText;
    private String content;
    private int image;
    private OnCommonClickListenerInterface clickListenerInterface;

    public interface OnCommonClickListenerInterface {

        void doConfirm();

        void doCancel();
    }

    /**
     * @param context           上下文
     * @param title             标题
     * @param confirmButtonText 确定
     * @param cacelButtonText   取消
     * @param image             等于0 表示不显示
     */
    public CommonDialog(Context context, String title, String content, String confirmButtonText, String cacelButtonText, int image) {
        super(context, R.style.LockDialogSetting);//需要更换
        this.context = context;
        this.title = title;
        this.confirmButtonText = confirmButtonText;
        this.cacelButtonText = cacelButtonText;
        this.image = image;
        this.content = content;
//        Utils.setFullScreen((Activity) context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //隐藏虚拟按钮
//        hideNavigationBar();
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }


    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.hubsan_common_dialog, null);
        setContentView(view);

        TextView tvTitle = (TextView) view.findViewById(R.id.hubsanCommonTitle);
        final TextView tvContent = (TextView) view.findViewById(R.id.hubsanCommonContent);
        Button tvConfirm = (Button) view.findViewById(R.id.hubsanCommonConfirm);
        Button tvCancel = (Button) view.findViewById(R.id.hubsanCommonCancel);
        ImageView tvImg = (ImageView) view.findViewById(R.id.hubsanCommonImage);
        tvTitle.setText(title);
        tvConfirm.setText(confirmButtonText);
        tvCancel.setText(cacelButtonText);
        tvContent.setText(content);
        ViewTreeObserver vto = tvContent.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int line = tvContent.getLineCount();
                if (line > 1) {
                    tvContent.setGravity(Gravity.TOP);
                } else {
                    tvContent.setGravity(Gravity.CENTER| Gravity.TOP);
                }
                return true;
            }
        });


        if (image == 0) {
            tvImg.setVisibility(View.GONE);
        } else {
            tvImg.setVisibility(View.VISIBLE);
            tvImg.setImageResource(image);
        }
        tvConfirm.setOnClickListener(new clickListener());
        tvCancel.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用


        if (context.getResources().getBoolean(R.bool.portrait_only)) {//手机
            lp.width = (int) (d.widthPixels * 0.6);
            lp.height = (int) (d.heightPixels * 0.6);
        } else {//平板
            lp.width = (int) (d.widthPixels * 0.6);
            lp.height = (int) (d.heightPixels * 0.5);
        }
        dialogWindow.setAttributes(lp);
        getWindow().setGravity(Gravity.CENTER);
    }

    public void setClicklistener(OnCommonClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int id = v.getId();
            switch (id) {
                case R.id.hubsanCommonConfirm:
                    clickListenerInterface.doConfirm();
                    break;
                case R.id.hubsanCommonCancel:
                    clickListenerInterface.doCancel();
                    break;
            }
        }

    }

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
        this.getWindow().getDecorView().setSystemUiVisibility(
                context.getWindow().getDecorView().getSystemUiVisibility());
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
