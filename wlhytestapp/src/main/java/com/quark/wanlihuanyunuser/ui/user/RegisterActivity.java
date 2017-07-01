package com.quark.wanlihuanyunuser.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.GetRegisterCodeRequest;
import com.quark.api.auto.bean.GetRegisterCodeResponse;
import com.quark.api.auto.bean.RegistTelRequest;
import com.quark.api.auto.bean.RegistTelResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.mainview.MainActivity;
import com.quark.wanlihuanyunuser.util.UIHelper;
import com.quark.wanlihuanyunuser.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#注册
 */
public class RegisterActivity extends BaseActivity {


    @InjectView(R.id.user_et)
    EditText userEt;
    @InjectView(R.id.user_iv)
    ImageView userIv;
    @InjectView(R.id.parent)
    RelativeLayout parent;
    @InjectView(R.id.code_et)
    EditText codeEt;
    @InjectView(R.id.go_code)
    Button goCode;
    @InjectView(R.id.code_ly)
    LinearLayout codeLy;
    @InjectView(R.id.pwd_et)
    EditText pwdEt;
    @InjectView(R.id.pwd_iv)
    ImageView pwdIv;
    @InjectView(R.id.close_eye_ibt)
    ImageButton closeEyeIbt;
    @InjectView(R.id.register_bt)
    Button registerBt;

    String telephone;
    String pwd;
    Handler handlerCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        handlerCode = new Handler();

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.left, R.id.go_code, R.id.close_eye_ibt, R.id.register_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left:
                finish();
                break;
            case R.id.go_code:
                if (!Utils.isEmpty(userEt.getText().toString())) {
                    telephone = userEt.getText().toString();
                    getCodeRequest();
                } else {
                    showToast("请输入手机号");
                }
                break;
            case R.id.close_eye_ibt:
                switchPwd();
                break;
            case R.id.register_bt:

                if (check()) {
                    getData();
                }

                break;
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

    /**
     * 获取验证码
     *
     * @author pan
     * @time 2016/11/24 0024 上午 10:34
     */
    public void getCodeRequest() {
        GetRegisterCodeRequest rq = new GetRegisterCodeRequest();
        rq.setTelephone(telephone);
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlercode);
    }

    private final AsyncHttpResponseHandler mHandlercode = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, RegisterActivity.this, GetRegisterCodeResponse.class);
            if (kd != null) {
                GetRegisterCodeResponse info = (GetRegisterCodeResponse) kd;
                if (info.getStatus() == 1) {
                    UIHelper.countdown(goCode, handlerCode, RegisterActivity.this, false);
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

    /**
     * 注册请求
     *
     * @author pan
     * @time 2016/11/24 0024 上午 10:32
     */
    public void getData() {
        RegistTelRequest rq = new RegistTelRequest();
        rq.setTel_code(codeEt.getText().toString());
        rq.setPassword(pwd);
        rq.setTelephone(telephone);
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, RegisterActivity.this, RegistTelResponse.class);
            if (kd != null) {
                RegistTelResponse info = (RegistTelResponse) kd;
                if (info.getStatus() == 1) {
                    showToast(info.getMessage());
                    startActivityByClass(LoginActivity.class);

                    new AppParam().setSharedPreferencesy(RegisterActivity.this, "token", info.getUser().getToken());
                    new AppParam().setSharedPreferencesy(RegisterActivity.this, "telephone", info.getUser().getTelephone());
                    new AppParam().setSharedPreferencesy(RegisterActivity.this, "is_set_trade_pwd", info.getUser().getIs_set_trade_pwd() + "");

                    Intent in = new Intent(RegisterActivity.this, MainActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);

                    if(GuidePgActivity.instance!=null){
                        GuidePgActivity.instance.finish();
                    }
                    if(LoginActivity.instance!=null){
                        LoginActivity.instance.finish();
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
}
