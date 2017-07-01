package com.quark.wanlihuanyunuser.ui.user;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.GetSetCodeRequest;
import com.quark.api.auto.bean.GetSetCodeResponse;
import com.quark.wanlihuanyunuser.AppContext;
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
 * >#重置交易密码
 */
public class EditPyPwdActivity extends BaseActivity {

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
    @InjectView(R.id.reset_pwd)
    Button resetPwd;

    Handler handlerCode;
    String telephone;
    String is_set_trade_pwd;//是否设置交易密码
    public static EditPyPwdActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpypwd);
        ButterKnife.inject(this);
        handlerCode = new Handler();

        telephone = (String) getValue4Intent("phone");
        is_set_trade_pwd = (String) getValue4Intent("is_set_trade_pwd");
        userEt.setText(telephone);
        userEt.setTextColor(ContextCompat.getColor(this, R.color.huise));
        instance = this;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.left, R.id.go_code, R.id.reset_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left:
                finish();
                break;
            case R.id.go_code:

                if (!Utils.isEmpty(userEt.getText().toString())) {
                    telephone = userEt.getText().toString();
                    getEditCodeRequest();
                } else {
                    showToast("请输入手机号");
                }
                break;
            case R.id.reset_pwd:
                Bundle bundle = new Bundle();
                bundle.putString("code", codeEt.getText().toString());
                bundle.putString("is_set_trade_pwd", is_set_trade_pwd);
                startActivityByClass(ResetPyPwdActivity.class, bundle);
                finish();
                break;
        }
    }

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
            Object kd = ApiResponse.get(arg2, EditPyPwdActivity.this, GetSetCodeResponse.class);
            if (kd != null) {
                GetSetCodeResponse info = (GetSetCodeResponse) kd;
                if (info.getStatus() == 1) {
                    UIHelper.countdown(goCode, handlerCode, EditPyPwdActivity.this, false);
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
