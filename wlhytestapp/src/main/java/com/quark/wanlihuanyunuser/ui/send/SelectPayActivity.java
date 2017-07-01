package com.quark.wanlihuanyunuser.ui.send;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.PaySendOrdersRequest;
import com.quark.api.auto.bean.PaySendOrdersResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.ui.widget.ChedaiEditDialog;
import com.quark.wanlihuanyunuser.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/11/10 0010.
 * >#
 * >#选择支付方式
 */
public class SelectPayActivity extends BaseActivity {

    @InjectView(R.id.ok_pay)
    Button okPay;
    @InjectView(R.id.number_et)
    EditText numberEt;

    private String send_orders_id;//代发订单ID
    private String trade_password;//电子卡支付必须密码
    String money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectpay);
        ButterKnife.inject(this);
        setTopTitle("选择支付方式");
        setBackButton();

        send_orders_id = (String) getValue4Intent("send_orders_id");
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.ok_pay)
    public void onClick() {
        money = numberEt.getEditableText().toString();
        boolean issetpassword = new AppParam().getIs_set_trade_pwd(this);
        if (issetpassword) {
            if (!Utils.isEmpty(money)) {
                showAlertDialog("电子卡", money, "请输入支付密码");
            } else {
                showToast("请填入金额");
            }
        } else {
            showToast("您尚未设置交易密码");
        }
    }

    public void showAlertDialog(String str, final String str2, String str3) {
        final ChedaiEditDialog.Builder builder = new ChedaiEditDialog.Builder(this, mhandlerpsw);
        builder.setMessage(str);
        builder.setMessage2(str2);
        builder.setTitle(str3);

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    Handler mhandlerpsw = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 201:
                    trade_password = msg.obj + "";
                    getData();
                    break;
                default:
                    break;
            }
        }
    };

    public void getData() {
        PaySendOrdersRequest rq = new PaySendOrdersRequest();
        rq.setUser_type(AppParam.user_type);
        rq.setSend_orders_id(send_orders_id);
        rq.setTrade_password(trade_password);
        rq.setOrders_money(money);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, SelectPayActivity.this, PaySendOrdersResponse.class);
            if (kd != null) {
                PaySendOrdersResponse info = (PaySendOrdersResponse) kd;
                if (info.getStatus() == 1) {
                    showToast(info.getMessage());

                    Intent intent = new Intent("NotPayFragment");
                    intent.putExtra("option", "refresh");
                    sendBroadcast(intent);

                    Intent intent2 = new Intent("PaitFragment");
                    intent2.putExtra("option", "refresh");
                    sendBroadcast(intent2);

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
