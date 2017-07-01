package com.quark.wanlihuanyunuser.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quark.wanlihuanyunuser.AppManager;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.ui.widget.WaitDialog;
import com.quark.wanlihuanyunuser.util.ToastUtil;
import com.quark.wanlihuanyunuser.util.ValidateHelper;

public abstract class BaseAppCompatActivity extends AppCompatActivity {
    public static final String TAG = "com.quark";
    protected TextView title;
    protected Button right;
    protected ImageView back;
    private boolean isInit = false;
    protected WaitDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
    }

    public void setRightLayout(View.OnClickListener listener) {
        LinearLayout right_button = (LinearLayout) findViewById(R.id.right);
        right_button.setVisibility(View.VISIBLE);
        if (null != listener) {
            right_button.setOnClickListener(listener);
        } else {
            Log.e(TAG, "set right button error! listener is null");
        }
    }

    public void setRightImage(int id, View.OnClickListener listener) {
        if (id > 0) {
            ImageView right_img = (ImageView) findViewById(R.id.rightrig);
            LinearLayout right = (LinearLayout) findViewById(R.id.right);
            right_img.setImageResource(id);
            right.setVisibility(View.VISIBLE);
            right_img.setVisibility(View.VISIBLE);
            if (null != listener) {
                right.setOnClickListener(listener);
            } else {
                Log.e(TAG, "set right button error! listener is null");
            }
        } else {
            Log.e(TAG, "set right button error! name is null");
        }
    }

    public void setRight(int resId) {
        right.setText(resId);
    }

    public void setTopTitle(String titlestr) {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(titlestr);
    }

    public void setBackButton() {
        LinearLayout back_lay = (LinearLayout) findViewById(R.id.left);
        back_lay.setVisibility(View.VISIBLE);
        back_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void startActivityByClass(Class clazz) {
        startActivityByClass(clazz, null);
    }

    public void startActivityByClass(Class clazz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public Object getValue4Intent(String key) {
        if (ValidateHelper.isEmptyString(key)) {
            return null;
        }

        Bundle b = getIntent().getExtras();

        if (null == b) {
            return null;
        }

        return b.get(key);
    }

    public void showToast(String msg) {
        if (ValidateHelper.isEmptyString(msg)) {
            return;
        }

        ToastUtil.showShortToast(msg);
    }

    public void showToast(int resid) {
        ToastUtil.showShortToast(resid);
    }

    protected void showWait(boolean isShow) {
        if (isShow) {
            if (null == dialog) {
                dialog = new WaitDialog(this);
            }
            dialog.show();
        } else {
            if (null != dialog) {
                dialog.dismiss();
            }
        }
    }

    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

}
