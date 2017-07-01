package com.quark.wanlihuanyunuser.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.ChargeMoneyListRequest;
import com.quark.api.auto.bean.ChargeMoneyListResponse;
import com.quark.api.auto.bean.ExchangeRateRequest;
import com.quark.api.auto.bean.ExchangeRateResponse;
import com.quark.api.auto.bean.PaymentAccountList;
import com.quark.api.auto.bean.PaymentAccountRequest;
import com.quark.api.auto.bean.PaymentAccountResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.PayListAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.ui.send.PayInfoActivity;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#充值
 */
public class TopUpActivity extends BaseActivity {
    String charge_money_constant_id;//
    String pay_type = "1";  //1-支付宝，2-微信，3-银行转账，4-电子卡支付,5-商家寄付月结
    String orders_number;   //预生成的订单编号
    String money;
    public static TopUpActivity instance;
    String money_rmb;      //转换成人民币
    List<PaymentAccountList> payInfoLists;
    PayListAdapter adapter;
    int currentPositon;

    @InjectView(R.id.right)
    LinearLayout right;
    @InjectView(R.id.one_bt)
    Button oneBt;
    @InjectView(R.id.two_bt)
    Button twoBt;
    @InjectView(R.id.three_bt)
    Button threeBt;
    @InjectView(R.id.four_bt)
    Button fourBt;
    @InjectView(R.id.five_bt)
    Button fiveBt;
    @InjectView(R.id.list)
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);
        ButterKnife.inject(this);
        setTopTitle("充值");
        setBackButton();
        instance = this;

        payInfoLists = new ArrayList<>();
        adapter = new PayListAdapter(this, payInfoLists, handlert);
        list.setAdapter(adapter);
        list.setOnItemClickListener(ck);

        chargeMoneyListRequest();
        getPayInfoListData();
    }

    AdapterView.OnItemClickListener ck = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            adapter.setCheckPositon(position);
            adapter.notifyDataSetChanged();
            pay_type = payInfoLists.get(position).getType() + "";
            currentPositon = position;
        }
    };

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.one_bt, R.id.two_bt, R.id.three_bt, R.id.four_bt, R.id.five_bt, R.id.ok})
    public void onClick(View view) {
        if(info!=null){
            switch (view.getId()) {
                case R.id.one_bt:
                    initBt();
                    oneBt.setBackground(ContextCompat.getDrawable(this, R.drawable.button_addcard_select));
                    oneBt.setTextColor(ContextCompat.getColor(this, R.color.blue));
                    charge_money_constant_id = info.getChargeMoneyList().get(0).getCharge_money_constant_id() + "";
                    money = info.getChargeMoneyList().get(0).getMoney() + "";
                    break;
                case R.id.two_bt:
                    initBt();
                    twoBt.setBackground(ContextCompat.getDrawable(this, R.drawable.button_addcard_select));
                    twoBt.setTextColor(ContextCompat.getColor(this, R.color.blue));
                    charge_money_constant_id = info.getChargeMoneyList().get(1).getCharge_money_constant_id() + "";
                    money = info.getChargeMoneyList().get(1).getMoney() + "";
                    break;
                case R.id.three_bt:
                    initBt();
                    threeBt.setBackground(ContextCompat.getDrawable(this, R.drawable.button_addcard_select));
                    threeBt.setTextColor(ContextCompat.getColor(this, R.color.blue));
                    charge_money_constant_id = info.getChargeMoneyList().get(2).getCharge_money_constant_id() + "";
                    money = info.getChargeMoneyList().get(2).getMoney() + "";
                    break;
                case R.id.four_bt:
                    initBt();
                    fourBt.setBackground(ContextCompat.getDrawable(this, R.drawable.button_addcard_select));
                    fourBt.setTextColor(ContextCompat.getColor(this, R.color.blue));
                    charge_money_constant_id = info.getChargeMoneyList().get(3).getCharge_money_constant_id() + "";
                    money = info.getChargeMoneyList().get(3).getMoney() + "";
                    break;
                case R.id.five_bt:
                    initBt();
                    fiveBt.setBackground(ContextCompat.getDrawable(this, R.drawable.button_addcard_select));
                    fiveBt.setTextColor(ContextCompat.getColor(this, R.color.blue));
                    charge_money_constant_id = info.getChargeMoneyList().get(4).getCharge_money_constant_id() + "";
                    money = info.getChargeMoneyList().get(4).getMoney() + "";
                    break;
                case R.id.ok:
                    if ("1".equals(pay_type) || "2".equals(pay_type)) {
                        getExchangeRate();
                    } else {
                        toPay();
                    }
                    break;
            }
        }
    }

    public void toPay() {
        Bundle bundle = new Bundle();
        bundle.putString("pay_type", pay_type);
        bundle.putString("money", money);
        bundle.putString("from", "change");
        bundle.putString("orders_number", orders_number);
        bundle.putString("money_rmb", money_rmb);
        bundle.putString("charge_money_constant_id", charge_money_constant_id);
        bundle.putSerializable("payinfo", payInfoLists.get(currentPositon));
        Intent intent = new Intent(this, PayInfoActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void initBt() {
        oneBt.setBackground(ContextCompat.getDrawable(this, R.drawable.button_addcard));
        oneBt.setTextColor(ContextCompat.getColor(this, R.color.black));
        twoBt.setBackground(ContextCompat.getDrawable(this, R.drawable.button_addcard));
        twoBt.setTextColor(ContextCompat.getColor(this, R.color.black));
        threeBt.setBackground(ContextCompat.getDrawable(this, R.drawable.button_addcard));
        threeBt.setTextColor(ContextCompat.getColor(this, R.color.black));
        fourBt.setBackground(ContextCompat.getDrawable(this, R.drawable.button_addcard));
        fourBt.setTextColor(ContextCompat.getColor(this, R.color.black));
        fiveBt.setBackground(ContextCompat.getDrawable(this, R.drawable.button_addcard));
        fiveBt.setTextColor(ContextCompat.getColor(this, R.color.black));
    }

    /**
     * 价格常量
     *
     * @author pan
     * @time 2016/11/24 0024 下午 3:47
     */
    public void chargeMoneyListRequest() {
        ChargeMoneyListRequest rq = new ChargeMoneyListRequest();
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerlist);
    }

    ChargeMoneyListResponse info;
    private final AsyncHttpResponseHandler mHandlerlist = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, TopUpActivity.this, ChargeMoneyListResponse.class);
            if (kd != null) {
                info = (ChargeMoneyListResponse) kd;
                if (info.getStatus() == 1) {
                    oneBt.setText(info.getChargeMoneyList().get(0).getMoney() + "元");
                    twoBt.setText(info.getChargeMoneyList().get(1).getMoney() + "元");
                    threeBt.setText(info.getChargeMoneyList().get(2).getMoney() + "元");
                    fourBt.setText(info.getChargeMoneyList().get(3).getMoney() + "元");
                    fiveBt.setText(info.getChargeMoneyList().get(4).getMoney() + "元");
                    charge_money_constant_id = info.getChargeMoneyList().get(0).getCharge_money_constant_id() + "";
                    money = info.getChargeMoneyList().get(0).getMoney() + "";
                    orders_number = info.getOrders_number();
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

    //获取汇率
    public void getExchangeRate() {
        showWait(true);
        ExchangeRateRequest rq = new ExchangeRateRequest();
        rq.setUser_type("1");
        rq.setCompany_id("0");
        QuarkApi.HttpRequest(rq, mHandlerrate);
    }

    private AsyncHttpResponseHandler mHandlerrate = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, TopUpActivity.this, ExchangeRateResponse.class);
            if (kd != null) {
                ExchangeRateResponse info = (ExchangeRateResponse) kd;
                if (info.getStatus() == 1) {
                    String exchange_rateStr = info.getExchange_rate();
                    try {
                        double exchange_rate = Double.valueOf(exchange_rateStr);
                        money_rmb = Utils.DoubleFormat(Double.valueOf(money) * exchange_rate);
                        toPay();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    public void getPayInfoListData() {
        PaymentAccountRequest rq = new PaymentAccountRequest();
        rq.setCompany_id(new AppParam().getPayId(this));
        rq.setUser_type("3");//1-用户，2-商家，3-充值
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerks);
    }

    private final AsyncHttpResponseHandler mHandlerks = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, TopUpActivity.this, PaymentAccountResponse.class);
            if (kd != null) {
                PaymentAccountResponse info = (PaymentAccountResponse) kd;
                if (info.getStatus() == 1) {
                    List<PaymentAccountList> dt = info.getPaymentAccountList();
                    payInfoLists.addAll(dt);
                    adapter.notifyDataSetChanged();
                    currentPositon = 0;
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

    private Handler handlert = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

        ;
    };
}
