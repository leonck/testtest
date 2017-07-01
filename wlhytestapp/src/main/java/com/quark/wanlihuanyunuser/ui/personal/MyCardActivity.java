package com.quark.wanlihuanyunuser.ui.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.UserBaseInfoRequest;
import com.quark.api.auto.bean.UserBaseInfoResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.ui.user.EditPyPwdActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#我的电子卡
 */
public class MyCardActivity extends BaseActivity {

    @InjectView(R.id.one_ly)
    LinearLayout oneLy;
    @InjectView(R.id.two_ly)
    LinearLayout twoLy;
    @InjectView(R.id.three_ly)
    LinearLayout threeLy;
    @InjectView(R.id.carpic)
    ImageView carpic;
    @InjectView(R.id.money_tv)
    TextView moneyTv;
    @InjectView(R.id.history_gift_money)
    TextView historyGiftMoney;
    @InjectView(R.id.seting_pwd)
    TextView setingPwd;

    String phone;//
    String is_set_trade_pwd;// 是否设置密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycard);
        ButterKnife.inject(this);

        infoRequest();
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    public void infoRequest() {
        UserBaseInfoRequest rq = new UserBaseInfoRequest();
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    UserBaseInfoResponse info;
    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, MyCardActivity.this, UserBaseInfoResponse.class);
            if (kd != null) {
                info = (UserBaseInfoResponse) kd;
                if (info.getStatus() == 1) {
                    moneyTv.setText("余额：" + info.getUserBaseInfoResult().getUserInfo().getBalance_money());
                    historyGiftMoney.setText("累计赠送：" + info.getUserBaseInfoResult().getUserInfo().getHistory_gift_money());
                    is_set_trade_pwd = info.getUserBaseInfoResult().getUserInfo().getIs_set_trade_pwd() + "";
                    if (is_set_trade_pwd.equals("0")) {
                        setingPwd.setText("请设置交易密码");
                    } else {
                        setingPwd.setText("请修改交易密码");
                    }
                    phone = info.getUserBaseInfoResult().getUserInfo().getTelephone();
//                    ApiHttpClient.loadImage(info.getUserBaseInfoResult().getUserInfo().getImage_01(),carpic);
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
    };

    @OnClick({R.id.left, R.id.one_ly, R.id.two_ly, R.id.three_ly})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left:
                finish();
                break;
            case R.id.one_ly:
                startActivityByClass(TopUpActivity.class);
                break;
            case R.id.two_ly:
                startActivityByClass(TransactionActivity.class);
                break;
            case R.id.three_ly:
                Bundle bundle = new Bundle();
                bundle.putString("phone", phone);
                bundle.putString("is_set_trade_pwd", is_set_trade_pwd);
                startActivityByClass(EditPyPwdActivity.class, bundle);
                break;
        }
    }

}
