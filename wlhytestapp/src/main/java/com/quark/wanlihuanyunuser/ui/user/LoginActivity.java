package com.quark.wanlihuanyunuser.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.LoginRequest;
import com.quark.api.auto.bean.LoginResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppManager;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.mainview.MainActivity;
import com.quark.wanlihuanyunuser.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#登录
 */
public class LoginActivity extends BaseActivity {

    @InjectView(R.id.user_et)
    EditText userEt;
    @InjectView(R.id.user_iv)
    ImageView userIv;
    @InjectView(R.id.parent)
    RelativeLayout parent;
    @InjectView(R.id.pwd_et)
    EditText pwdEt;
    @InjectView(R.id.pwd_iv)
    ImageView pwdIv;
    @InjectView(R.id.close_eye_ibt)
    ImageButton closeEyeIbt;
    @InjectView(R.id.login_bt)
    Button loginBt;
    @InjectView(R.id.forget_ly)
    LinearLayout forgetLy;

    String telephone;
    String pwd;

    String from; //405的时候跳转过来
    public static LoginActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        instance = this;
        if (!Utils.isEmpty(new AppParam().getTelephone(this))) {
            userEt.setText(new AppParam().getTelephone(this));
//            pwdEt.setText(new AppParam().getpassword(this));
        }

        from = (String) getValue4Intent("from");
        new AppParam().setSharedPreferencesy(LoginActivity.this, "token", "");
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.left, R.id.close_eye_ibt, R.id.login_bt, R.id.forget_ly})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left:
                if ("exception".equals(from)) {
                    AppManager.getAppManager().finishAllActivity();
                }
                finish();
                break;
            case R.id.close_eye_ibt:
                switchPwd();
                break;
            case R.id.login_bt:
                if (check()) {
                    loginRequest();
                }
                break;
            case R.id.forget_ly:
                Bundle bundle = new Bundle();
                bundle.putString("type", "1");
                startActivityByClass(EditPwdActivity.class, bundle);
                break;
        }
    }

    //密码显示切换
    boolean showpssword = false;

    private void switchPwd() {
        if (!showpssword) {
            // 设置为明文显示
            showpssword = true;
            pwdEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            closeEyeIbt.setBackground(ContextCompat.getDrawable(this, R.drawable.open_eye));
        } else {
            // 设置为秘闻显示
            showpssword = false;
            pwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            closeEyeIbt.setBackground(ContextCompat.getDrawable(this, R.drawable.close_eye));
        }
        // 切换后将EditText光标置于末尾
        CharSequence charSequence = pwdEt.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    private boolean check() {
        telephone = userEt.getText().toString();
        pwd = pwdEt.getText().toString();
        if (Utils.isEmpty(telephone)) {
            showToast("请输入手机号");
            return false;
        }
        if (Utils.isEmpty(pwd)) {
            showToast("请输入密码");
            return false;
        }
        return true;
    }

    /**
     * 登录请求
     *
     * @author pan
     * @time 2016/11/24 0024 上午 11:13
     */
    public void loginRequest() {
        LoginRequest rq = new LoginRequest();
        rq.setTelephone(telephone);
        rq.setPassword(pwd);
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlercode);
    }

    private final AsyncHttpResponseHandler mHandlercode = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, LoginActivity.this, LoginResponse.class);
            if (kd != null) {
                LoginResponse info = (LoginResponse) kd;
                if (info.getStatus() == 1) {
                    new AppParam().setSharedPreferencesy(LoginActivity.this, "token", info.getUser().getToken());
                    new AppParam().setSharedPreferencesy(LoginActivity.this, "telephone", info.getUser().getTelephone());
                    new AppParam().setSharedPreferencesy(LoginActivity.this, "is_set_trade_pwd", info.getUser().getIs_set_trade_pwd() + "");
                    new AppParam().setSharedPreferencesy(LoginActivity.this, "user_id", info.getUser().getUser_id() + "");
                    Intent in = new Intent(LoginActivity.this, MainActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
                    if(GuidePgActivity.instance!=null){
                        GuidePgActivity.instance.finish();
                    }
                    finish();
                } else {
                    showToast(info.getMessage());
                }
            }
            showWait(false);
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
            showWait(false);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            showWait(false);
        }
    };

    //返回退出 关闭所有进程
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            if ("exception".equals(from)) {
                AppManager.getAppManager().finishAllActivity();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
