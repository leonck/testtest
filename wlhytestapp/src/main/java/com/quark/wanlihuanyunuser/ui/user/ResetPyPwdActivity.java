package com.quark.wanlihuanyunuser.ui.user;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.SetTradePasswordRequest;
import com.quark.api.auto.bean.SetTradePasswordResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#设置交易密码界面
 */
public class ResetPyPwdActivity extends BaseActivity {

    String code;
    String password;
    String newPassword;
    @InjectView(R.id.one)
    TextView one;
    @InjectView(R.id.two)
    TextView two;
    @InjectView(R.id.three)
    TextView three;
    @InjectView(R.id.four)
    TextView four;
    @InjectView(R.id.five)
    TextView five;
    @InjectView(R.id.six)
    TextView six;
    @InjectView(R.id.password)
    EditText editView;
    @InjectView(R.id.password_two)
    EditText editViewTwo;
    final TextView[] dian = new TextView[6];
    @InjectView(R.id.hint_tv)
    TextView hintTv;

    String is_set_trade_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpypwd);
        ButterKnife.inject(this);
        setBackButton();
        setTopTitle("");

        code = (String) getValue4Intent("code");
        is_set_trade_pwd = (String) getValue4Intent("is_set_trade_pwd");
        if (is_set_trade_pwd.equals("0")) {
            hintTv.setText("请设置交易密码");
        } else {
            hintTv.setText("请修改交易密码");
        }
//        getData();

        dian[0] = one;
        dian[1] = two;
        dian[2] = three;
        dian[3] = four;
        dian[4] = five;
        dian[5] = six;

        editView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//					System.out.println("onTextChanged "+" s="+s.toString()+"  start="+start+"  before = "+before+"  count"+count);
                setvisiable(start, s.toString());
                if (start == 5) {
                    password = editView.getText().toString();
                    editView.setVisibility(View.GONE);
                    editViewTwo.setVisibility(View.VISIBLE);

                    editViewTwo.setFocusable(true);
                    editViewTwo.setFocusableInTouchMode(true);
                    editViewTwo.requestFocus();

                    hintTv.setText("请再次确认密码");
                    lastPostion = -1;
                    st = "";
                    for (int i = 0; i < start + 1; i++) {
                        dian[i].setVisibility(View.INVISIBLE);
                    }
//                    showToast("密码是：" + password);
//                    initTwoPwd();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("=======beforeTextChanged===== " + " s=" + s.toString() + "  start=" + start + "  after = " + after + "  count" + count);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        initTwoPwd();

        editView.setFocusable(true);
        editView.setFocusableInTouchMode(true);
        editView.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) editView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editView, 0);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) editView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(editView, 0);
                           }
                       },
                998);
    }

    private void initTwoPwd() {
//        editViewTwo.setInputType(123456789);
        editViewTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//					System.out.println("onTextChanged "+" s="+s.toString()+"  start="+start+"  before = "+before+"  count"+count);
                setvisiable(start, s.toString());
                if (start == 5) {
                    newPassword = editViewTwo.getText().toString();
                    if (!password.equals(newPassword)) {
                        showToast("密码不一致");
                    } else {
                        getData();
                    }
//                    showToast("新密码是：" + newPassword);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("=======beforeTextChanged===== " + " s=" + s.toString() + "  start=" + start + "  after = " + after + "  count" + count);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    int lastPostion = -1;
    String st = "";

    public void setvisiable(int start, String s) {
        if ((lastPostion == start || lastPostion > start) && st.length() > s.length()) {//删除
            dian[start].setVisibility(View.INVISIBLE);
        } else {                                      //增加
            dian[start].setVisibility(View.VISIBLE);
            for (int i = start + 1; i < 6; i++) {
                dian[i].setVisibility(View.INVISIBLE);
            }
        }
        if ((start == 0 && lastPostion == 1) || (start == 0 && lastPostion == 0)) {   //fuck wen ti
            lastPostion = -1;
        } else {
            lastPostion = start;
        }
        st = s;
    }


    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public void getData() {
        SetTradePasswordRequest rq = new SetTradePasswordRequest();
        rq.setTel_code(code);
        rq.setPassword(password);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, ResetPyPwdActivity.this, SetTradePasswordResponse.class);
            if (kd != null) {
                SetTradePasswordResponse info = (SetTradePasswordResponse) kd;
                if (info.getStatus() == 1) {
                    showToast(info.getMessage());
                    new AppParam().setSharedPreferencesy(ResetPyPwdActivity.this, "is_set_trade_pwd", "1");
                    if (EditPyPwdActivity.instance != null) {
                        EditPyPwdActivity.instance.finish();
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
