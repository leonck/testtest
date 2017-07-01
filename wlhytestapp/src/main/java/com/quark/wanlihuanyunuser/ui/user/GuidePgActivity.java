package com.quark.wanlihuanyunuser.ui.user;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#登录引导页
 */
public class GuidePgActivity extends BaseActivity {

    @InjectView(R.id.login_bt)
    Button loginBt;
    @InjectView(R.id.register_bt)
    Button registerBt;

    public static GuidePgActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidepg);
        ButterKnife.inject(this);
        instance = this;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.login_bt, R.id.register_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_bt:
                clear();
                loginBt.setBackground(ContextCompat.getDrawable(this, R.drawable.button_shenlan));
                loginBt.setTextColor(ContextCompat.getColor(this, R.color.mywhite));
                startActivityByClass(LoginActivity.class);
                break;
            case R.id.register_bt:
                clear();
                registerBt.setBackground(ContextCompat.getDrawable(this, R.drawable.button_shenlan));
                registerBt.setTextColor(ContextCompat.getColor(this, R.color.mywhite));
                startActivityByClass(RegisterActivity.class);
                break;
        }
    }

    public void clear(){
        loginBt.setBackground(ContextCompat.getDrawable(this,R.drawable.button_white));
        registerBt.setBackground(ContextCompat.getDrawable(this, R.drawable.button_white));
        loginBt.setTextColor(ContextCompat.getColor(this, R.color.blue));
        registerBt.setTextColor(ContextCompat.getColor(this,R.color.blue));

    }
}
