package com.quark.wanlihuanyunuser.ui.user;

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
import com.quark.api.auto.bean.GetForgetCodeRequest;
import com.quark.api.auto.bean.GetForgetCodeResponse;
import com.quark.api.auto.bean.GetSetCodeRequest;
import com.quark.api.auto.bean.GetSetCodeResponse;
import com.quark.api.auto.bean.ResetPasswordRequest;
import com.quark.api.auto.bean.ResetPasswordResponse;
import com.quark.api.auto.bean.SetPasswordRequest;
import com.quark.api.auto.bean.SetPasswordResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.util.UIHelper;
import com.quark.wanlihuanyunuser.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#忘记密码/修改密码
 */
public class EditPwdActivity extends BaseActivity {


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

    String type;//1-忘记密码 2-修改密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        handlerCode = new Handler();
        registerBt.setText("找回密码");

        type = (String) getValue4Intent("type");
         if (type.equals("2")) {
             userEt.setText(new AppParam().getTelephone(this));
             userEt.setEnabled(false);
        }
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

                if (type.equals("1")){
                    if (!Utils.isEmpty(userEt.getText().toString())) {
                        telephone = userEt.getText().toString();
                        getCodeRequest();
                    } else {
                        showToast("请输入手机号");
                    }
                }else if (type.equals("2")){
                    getEditCodeRequest();
                }


                break;
            case R.id.close_eye_ibt:
                switchPwd();
                break;
            case R.id.register_bt:
                if (check()) {
                    if (type.equals("1")){
                        getData();
                    }else if (type.equals("2")){
                        editpwdRequest();
                    }
                }
                break;
        }
    }

    /**
     * 获取码验证码
     * 忘记密码
     *
     * @author pan
     * @time 2016/11/24 0024 上午 10:34
     */
    public void getCodeRequest() {
        GetForgetCodeRequest rq = new GetForgetCodeRequest();
        rq.setTelephone(telephone);
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlercode);
    }

    private final AsyncHttpResponseHandler mHandlercode = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, EditPwdActivity.this, GetForgetCodeResponse.class);
            if (kd != null) {
                GetForgetCodeResponse info = (GetForgetCodeResponse) kd;
                if (info.getStatus() == 1) {
                    UIHelper.countdown(goCode, handlerCode, EditPwdActivity.this, false);
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
     * 修改密码
     * 获取验证码
     *
     * @author pan
     * @time 2016/11/24 0024 下午 2:46
     */
    public void getEditCodeRequest() {
        GetSetCodeRequest rq = new GetSetCodeRequest();
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlercodeedit);
    }

    private final AsyncHttpResponseHandler mHandlercodeedit = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, EditPwdActivity.this, GetSetCodeResponse.class);
            if (kd != null) {
                GetSetCodeResponse info = (GetSetCodeResponse) kd;
                if (info.getStatus() == 1) {
                    UIHelper.countdown(goCode, handlerCode, EditPwdActivity.this, false);
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
     * 找回密码请求
     *
     * @author pan
     * @time 2016/11/24 0024 上午 10:32
     */
    public void getData() {
        ResetPasswordRequest rq = new ResetPasswordRequest();
        rq.setTel_code(codeEt.getText().toString());
        rq.setPassword(pwd);
        rq.setTelephone(telephone);
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerdata);
    }

    private final AsyncHttpResponseHandler mHandlerdata = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, EditPwdActivity.this, ResetPasswordResponse.class);
            if (kd != null) {
                ResetPasswordResponse info = (ResetPasswordResponse) kd;
                if (info.getStatus() == 1) {
                    showToast(info.getMessage());
                    startActivityByClass(LoginActivity.class);
                    finish();
                }else {
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
     * 修改密码请求
     *
     * @author pan
     * @time 2016/11/24 0024 上午 10:32
     */
    public void editpwdRequest() {
        SetPasswordRequest rq = new SetPasswordRequest();
        rq.setTel_code(codeEt.getText().toString());
        rq.setPassword(pwd);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, EditPwdActivity.this, SetPasswordResponse.class);
            if (kd != null) {
                SetPasswordResponse info = (SetPasswordResponse) kd;
                if (info.getStatus() == 1) {
                    showToast(info.getMessage());
                    startActivityByClass(LoginActivity.class);
                    finish();
                }else {
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
}
