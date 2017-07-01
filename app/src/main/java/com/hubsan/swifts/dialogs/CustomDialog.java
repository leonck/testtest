package com.hubsan.swifts.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hubsan.swifts.R;
import com.hubsansdk.application.HubsanApplication;

/**
 * Description
 * 自定义对话框
 *
 * @author ShengKun.Cheng
 * @date 2015年8月18日
 * @see [class/class#method]
 * @since [product/model]
 */
public class CustomDialog extends Dialog {

    private Context context;

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
//        Utils.setFullScreen((Activity) context);

    }


    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private String deleteSubmitButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;
        private DialogInterface.OnClickListener deleteSubmitButtonClickListener;
        private boolean isDismiss;
        private TextView mTitle;
        private TextView mMessage;
        private LinearLayout hubsanDeleteSubmitLay;
        private Button hubsanCommDialogDeleteSubmit;
        private boolean isVisibility = false;
        private ImageView hubsanCommDialogBottomLine;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }


        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public void setButtonVisibility(boolean visibility) {
            this.isVisibility = visibility;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setCancelable(boolean isDismiss) {
            this.isDismiss = isDismiss;
            return this;
        }


        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * 删除并提交
         *
         * @param deleteSubmitButtonText
         * @param listener
         * @return
         */
        public Builder setDeleteAndSubmitButton(int deleteSubmitButtonText,
                                                DialogInterface.OnClickListener listener) {
            this.deleteSubmitButtonText = (String) context
                    .getText(deleteSubmitButtonText);
            this.deleteSubmitButtonClickListener = listener;
            return this;
        }


        @SuppressWarnings("deprecation")
        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.CustomDialog);
            final View layout = inflater.inflate(R.layout.hubsan_common_dialog_normal_layout, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            Window win = dialog.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.FILL_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);
            // set the dialog title
            mTitle = (TextView) layout.findViewById(R.id.dialogTittleTxt);
            mTitle.setText(title);
            mMessage = (TextView) layout.findViewById(R.id.message);
            mMessage.setText(message);
            hubsanCommDialogDeleteSubmit = (Button) layout.findViewById(R.id.hubsanCommDialogDeleteSubmit);
            hubsanDeleteSubmitLay = (LinearLayout) layout.findViewById(R.id.hubsanCommDialogBottomLay);
            hubsanCommDialogBottomLine = (ImageView) layout.findViewById(R.id.hubsanCommDialogBottomLine);
            if (isVisibility ==true){
                hubsanDeleteSubmitLay.setVisibility(View.VISIBLE);
                hubsanCommDialogBottomLine.setVisibility(View.VISIBLE);
            }else {
                hubsanDeleteSubmitLay.setVisibility(View.GONE);
                hubsanCommDialogBottomLine.setVisibility(View.GONE);
            }
            ViewTreeObserver vto = mMessage.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    int line = mMessage.getLineCount();
                    if (line > 1) {
                        mMessage.setGravity(Gravity.LEFT);
                    } else {
                        mMessage.setGravity(Gravity.CENTER);
                    }
                    return true;
                }
            });
//            ((TextView) layout.findViewById(R.id.dialogTittleTxt)).setText(title);
            // set cancel button
            ((ImageView) layout.findViewById(R.id.dialogCancel)).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });
            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    layout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }
            // set the content message
            if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((ScrollView) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((ScrollView) layout.findViewById(R.id.content)).addView(
                        contentView, new LayoutParams(
                                LayoutParams.FILL_PARENT,
                                LayoutParams.FILL_PARENT));
            }
            hubsanCommDialogDeleteSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteSubmitButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEUTRAL);
                }
            });
            dialog.setContentView(layout);
            dialog.setCancelable(isDismiss);
            return dialog;
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
        WindowManager wm = (WindowManager) HubsanApplication.getApplication().getSystemService(Context.WINDOW_SERVICE);
        wm.updateViewLayout(this.getWindow().getDecorView(), this.getWindow().getAttributes());
        //Clearly not ideal but seems to be an Android bug, they should check if the Window has immersive set.
    }
}
