package com.quark.wanlihuanyunuser.ui.send;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.ExchangeRateRequest;
import com.quark.api.auto.bean.ExchangeRateResponse;
import com.quark.api.auto.bean.PayDaifaOrdersRequest;
import com.quark.api.auto.bean.PayDaifaOrdersResponse;
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
import com.quark.wanlihuanyunuser.mainview.MainActivity;
import com.quark.wanlihuanyunuser.ui.widget.ChedaiEditDialog;
import com.quark.wanlihuanyunuser.ui.widget.ListViewForScrollView;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

import static com.quark.wanlihuanyunuser.AppParam.user_type;

/**
 * Created by pan on 2016/11/10 0010.
 * >#
 * >#选择支付方式 商家代发
 */
public class SelectPayShopActivity extends BaseActivity {
    @InjectView(R.id.left_img)
    ImageView leftImg;
    @InjectView(R.id.left)
    LinearLayout left;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.sign)
    TextView sign;
    @InjectView(R.id.rightrig)
    ImageView rightrig;
    @InjectView(R.id.right)
    LinearLayout right;
    @InjectView(R.id.head)
    RelativeLayout head;
    @InjectView(R.id.number_et)
    EditText numberEt;
    @InjectView(R.id.right_tbt)
    ToggleButton rightTbt;
    @InjectView(R.id.list)
    ListViewForScrollView list;
    @InjectView(R.id.ok_pay)
    Button okPay;

    private String daifa_orders_company_id;//代发订单ID
    private String pay_type;//支付方式：1-支付宝，2-微信，3-银行转账，4-电子卡支付
    private String trade_password;//电子卡支付必须密码
    //    private String pay_text = "";//支付方式：1-支付宝，2-微信，3-银行转账，4-电子卡支付
    private String money, orders_number;
    String from;         //daifa  代付进入 价格不能编辑
    double exchange_rate;//新西兰元对人民币的汇率
    String money_rmb; //人民币

    PayListAdapter adapter;
    List<PaymentAccountList> payInfoLists;
    List<PaymentAccountList> payInfoListsTemp; //没有支付宝 微信 只有纽币
    int currentPositon;  //当前位置
    boolean idCarOrderRMB = false; //如果有银行卡 银行卡是否支持人民币
    String company_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectpayshop);
        ButterKnife.inject(this);
        setTopTitle("选择支付方式");
        setBackButton();

        from = (String) getValue4Intent("from");
        if ("daifa".equals(from)) {
            numberEt.setEnabled(false);
        }
        money = (String) getValue4Intent("money");
        daifa_orders_company_id = (String) getValue4Intent("send_orders_id");
        orders_number = (String) getValue4Intent("orders_number");
        company_id = (String) getValue4Intent("company_id");
        showMoney(money);

        setTopTitle("选择支付方式");
        setBackButton();
        money = (String) getValue4Intent("money");
        payInfoLists = new ArrayList<>();
        payInfoListsTemp = new ArrayList<>();
        adapter = new PayListAdapter(this, payInfoLists, mhandlerpsw);
        list.setAdapter(adapter);
        list.setOnItemClickListener(ck);
        getPayInfoListData();  //支付信息列表
    }

    AdapterView.OnItemClickListener ck = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            adapter.setCheckPositon(position);
            adapter.notifyDataSetChanged();
            pay_type = payInfoLists.get(position).getType() + "";
            currentPositon = position;
//            if (1 == payInfoLists.get(position).getType() || 2 == payInfoLists.get(position).getType()) {
//                getRMBMoney("RMB");
//            } else {
            getRMBMoney();
//            }

        }
    };

    public void setpayInfoListsLocal() {
        PaymentAccountList pa = new PaymentAccountList();
        pa.setName("电子卡支付'");
        pa.setType(4);
        payInfoLists.add(pa);

    }

    public void showMoney(String moneyStr) {
        numberEt.setText(moneyStr);
    }

    public void getRMBMoney() {
        if ("1".equals(pay_type) || "2".equals(pay_type) ||
                ("3".equals(pay_type) && idCarOrderRMB && rightTbt.isChecked())) {
            try {
                double moneydb = Double.valueOf(money);
                String mstr = Utils.DoubleFormat(exchange_rate * moneydb);
                money_rmb = mstr;
                showMoney(mstr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showMoney(money);
        }
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.right_tbt, R.id.ok_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_tbt:
                if (rightTbt.isChecked()) {
                    payInfoLists.clear();
                    payInfoLists.addAll(payInfoListsTemp);
                    pay_type = payInfoLists.get(0).getType() + "";
                    currentPositon = 0;

                    getRMBMoney();
                    adapter = new PayListAdapter(this, payInfoLists, mhandlerpsw);
                    adapter.notifyDataSetChanged();
                    list.setAdapter(adapter);
                } else {
                    for (int i = 0, size = payInfoListsTemp.size(); i < size; i++) {
                        if (1 == payInfoListsTemp.get(i).getType() || 2 == payInfoListsTemp.get(i).getType()) {
                            PaymentAccountList pd = payInfoListsTemp.get(i);
                            payInfoLists.remove(pd);
                        }
                    }
                    pay_type = payInfoLists.get(0).getType() + "";
                    currentPositon = 0;
                    getRMBMoney();
                    adapter = new PayListAdapter(this, payInfoLists, mhandlerpsw);
                    adapter.notifyDataSetChanged();
                    list.setAdapter(adapter);
                }
                break;

            case R.id.ok_pay:
                if (!Utils.isEmpty(numberEt.getText().toString())) {
                    if (pay_type.equals("4")) {
                        showAlertDialog("电子卡支付", numberEt.getText().toString(), "请输入支付密码");
                    } else if (pay_type.equals("5")) {
                        getData();
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("pay_type", pay_type);
                        bundle.putString("money", money);
                        if (rightTbt.isChecked() && idCarOrderRMB) { //开启人民币支付
                            bundle.putString("money_rmb", money_rmb);
                        }
                        bundle.putString("orders_number", orders_number);
                        bundle.putString("daifa_orders_company_id", daifa_orders_company_id);
                        bundle.putSerializable("payinfo", payInfoLists.get(currentPositon));

                        Intent intent = new Intent(this, PayInfoActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                } else {
                    showToast("请输入金额");
                }
                break;
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

    //上门取件支付
    public void getData() {
        PayDaifaOrdersRequest rq = new PayDaifaOrdersRequest();
        rq.setDaifa_orders_company_id(daifa_orders_company_id);
        rq.setPay_type(pay_type);
        rq.setTrade_password(trade_password);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerd);
    }

    private final AsyncHttpResponseHandler mHandlerd = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, SelectPayShopActivity.this, PayDaifaOrdersResponse.class);
            if (kd != null) {
                PayDaifaOrdersResponse info = (PayDaifaOrdersResponse) kd;
                if (info.getStatus() == 1) {
                    showToast(info.getMessage());
                    Intent in = new Intent(SelectPayShopActivity.this, MainActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
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
    };

    //获取汇率
    public void getExchangeRate() {
        ExchangeRateRequest rq = new ExchangeRateRequest();
        rq.setUser_type("2");
        rq.setCompany_id(new AppParam().getPayId(this));
        QuarkApi.HttpRequest(rq, mHandlerrate);
    }

    private final AsyncHttpResponseHandler mHandlerrate = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, SelectPayShopActivity.this, ExchangeRateResponse.class);
            if (kd != null) {
                ExchangeRateResponse info = (ExchangeRateResponse) kd;
                String exchange_rateStr = info.getExchange_rate();
                try {
                    exchange_rate = Double.valueOf(exchange_rateStr);
                    getRMBMoney();
                } catch (Exception e) {
                    e.printStackTrace();
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
        if (Utils.isEmpty(company_id)) {
            rq.setCompany_id(new AppParam().getPayId(this));
        } else {
            rq.setCompany_id(company_id);
        }
        rq.setUser_type(user_type);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, SelectPayShopActivity.this, PaymentAccountResponse.class);
            if (kd != null) {
                PaymentAccountResponse info = (PaymentAccountResponse) kd;
                if (info.getStatus() == 1 || info.getStatus() == 2) {
                    List<PaymentAccountList> dt = info.getPaymentAccountList();
                    payInfoLists.addAll(dt);
                    setpayInfoListsLocal();
                    payInfoListsTemp.addAll(payInfoLists); //保存所有数据
                    adapter.notifyDataSetChanged();
                    currentPositon = 0;
                    pay_type = payInfoLists.get(currentPositon).getType() + "";
                    //银行卡是否支持人民币
                    for (int i = 0, size = payInfoLists.size(); i < size; i++) {
                        if (3 == payInfoLists.get(i).getType()) {
                            if (payInfoLists.get(i).getIs_pay_rmb() == 2) {
                                idCarOrderRMB = true;
                                break;
                            }
                        }
                    }
                    getExchangeRate();
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
}
