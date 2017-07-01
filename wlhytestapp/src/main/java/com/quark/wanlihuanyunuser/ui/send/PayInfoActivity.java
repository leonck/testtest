package com.quark.wanlihuanyunuser.ui.send;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.ChargeMoneyRequest;
import com.quark.api.auto.bean.ChargeMoneyResponse;
import com.quark.api.auto.bean.PayDaifaOrdersRequest;
import com.quark.api.auto.bean.PayDaifaOrdersResponse;
import com.quark.api.auto.bean.PaymentAccountList;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.api.ApiHttpClient;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.mainview.MainActivity;
import com.quark.wanlihuanyunuser.ui.personal.TopUpActivity;
import com.quark.wanlihuanyunuser.util.FileUtils;
import com.quark.wanlihuanyunuser.util.Utils;

import org.kymjs.kjframe.Core;
import org.kymjs.kjframe.bitmap.BitmapCallBack;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by pan on 2016/11/10 0010.
 * >#
 * >#支付信息
 */
public class PayInfoActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @InjectView(R.id.pay_type)
    TextView payType;
    @InjectView(R.id.pay_name)
    TextView payName;
    @InjectView(R.id.pay_way)
    TextView payWay;
    @InjectView(R.id.pay_way_number)
    TextView payWayNumber;
    @InjectView(R.id.pay_way_name)
    TextView payWayName;
    @InjectView(R.id.name_tv)
    TextView nameTv;
    @InjectView(R.id.code_iv)
    ImageView codeIv;
    @InjectView(R.id.er_code_tv)
    TextView erCodeTv;
    @InjectView(R.id.order_number)
    TextView orderNumber;
    @InjectView(R.id.pay_typermb)
    TextView pay_typermb;
    @InjectView(R.id.pay_name_tx)
    TextView payNameTx;
    @InjectView(R.id.pay_name_et)
    EditText payNameEt;
    @InjectView(R.id.infoline)
    View infoline;
    @InjectView(R.id.payinfoview)
    LinearLayout payinfoview;
    @InjectView(R.id.tips)
    TextView tips;

    private String user_type = AppParam.user_type;//用户类型：1-用户，2-商家
    String pay_type, daifa_orders_company_id, orders_number, account_number;
    String from;//change 充值
    String charge_money_constant_id;
    String money_rmb;
    String user_id; //支付信息返回 新增加
    PaymentAccountList payinfo;
    String payAccount; //付款账号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payinfo);
        ButterKnife.inject(this);
        setTopTitle("转账信息");
        setBackButton();
        pay_type = (String) getValue4Intent("pay_type");
        daifa_orders_company_id = (String) getValue4Intent("daifa_orders_company_id");
        orders_number = (String) getValue4Intent("orders_number");
        charge_money_constant_id = (String) getValue4Intent("charge_money_constant_id");
        from = (String) getValue4Intent("from");
        money_rmb = (String) getValue4Intent("money_rmb");//转换人民币
        payinfo = (PaymentAccountList) getValue4Intent("payinfo");

        showDatas();
        String money = (String) getValue4Intent("money");
        payType.setText(money + "元");
        if (!Utils.isEmpty(money_rmb)) {
            pay_typermb.setText("(人民币：" + money_rmb + "元)");
        }
        orderNumber.setText(orders_number);
    }

    public void showDatas() {
        user_id = payinfo.getUser_id();
        if (payinfo != null) {
            payWayNumber.setText(payinfo.getAccount_number());
            nameTv.setText(payinfo.getName());
            loadImage(payinfo.getImages_01(), codeIv);
        }
        String pt = payinfo.getType() + "";
        if (pay_type.equals("3")) {//1-支付宝，2-微信，3-银行
            payWay.setText("银行账号");
            payWayName.setText("银行名称");
            payName.setText("商家预留银行信息");
            erCodeTv.setVisibility(View.GONE);
            codeIv.setVisibility(View.INVISIBLE);
        } else if (pay_type.equals("1")) {
            payWay.setText("支付宝账号");
            payWayName.setText("支付宝名称");
            payName.setText("商家预留支付宝收款信息");

            infoline.setVisibility(View.VISIBLE);
            payinfoview.setVisibility(View.VISIBLE);
            payNameTx.setText("付款支付宝账号：");
//            payNameEt.setHint("请输入付款支付宝账号");
            payNameTx.setVisibility(View.VISIBLE);

        } else if (pay_type.equals("2")) {
            payWay.setText("微信账号");
            payWayName.setText("微信昵称");
            payName.setText("商家预留微信收款信息");

            infoline.setVisibility(View.VISIBLE);
            payinfoview.setVisibility(View.VISIBLE);
            payNameTx.setText("付款微信账号：");
//            payNameEt.setHint("请输入付款微信账号");
            payNameTx.setVisibility(View.VISIBLE);
        }
        codeIv.setOnLongClickListener(lc);
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    @OnClick(R.id.ok_info)
    public void onClick() {
        payAccount = payNameEt.getText().toString();
        if ((pay_type.equals("1") || pay_type.equals("2")) && Utils.isEmpty(payAccount)) {
            showToast("请输入付款账号");
        } else {
            if ("change".equals(from)) {
                chargeMoneyRequest();
            } else {
                payData();
            }
        }
    }

    //上门取件支付
    public void payData() {
        PayDaifaOrdersRequest rq = new PayDaifaOrdersRequest();
        rq.setDaifa_orders_company_id(daifa_orders_company_id);
        rq.setPay_type(pay_type);
        rq.setPay_account(payAccount);
        showWait(true);
        QuarkApi.HttpRequest(rq, paymHandler);
    }

    private final AsyncHttpResponseHandler paymHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, PayInfoActivity.this, PayDaifaOrdersResponse.class);
            if (kd != null) {
                PayDaifaOrdersResponse info = (PayDaifaOrdersResponse) kd;
                if (info.getStatus() == 1) {
                    showToast(info.getMessage());
                    Intent in = new Intent(PayInfoActivity.this, MainActivity.class);
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

    View.OnLongClickListener lc = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            startByPermissions();
            return false;
        }
    };

    public void loadImage(String cpic, ImageView img) {
        if ((cpic != null) && (!cpic.equals(""))) {
            try {
                cpic = URLEncoder.encode(cpic, "UTF-8");
                String url = ApiHttpClient.loadImage + cpic;
                new Core.Builder().view(img).url(url)
                        .loadBitmap(new ColorDrawable(0x000000))
                        .errorBitmap(new ColorDrawable(0x000000))
                        .size(0, 0)
                        .bitmapCallBack(new BitmapCallBack() {
                            @Override
                            public void onPreLoad() {
//										bar.setVisibility(View.VISIBLE);
                            }
                            @Override
                            public void onFinish() {
//										bar.setVisibility(View.GONE);
                            }
                            @Override
                            public void onFailure(Exception arg0) {
//										AppContext.showToast(R.string.tip_load_image_faile);
                            }
                            @Override
                            public void onSuccess(Bitmap bitmap) {
                                super.onSuccess(bitmap);
                                mBitmap = bitmap;
                            }
                        }).doTask();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    Bitmap mBitmap;
    String fileName;

    private void startByPermissions() {
        fileName = "pay_" + account_number + ".jpg";//
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(PayInfoActivity.this, perms)) {
            try {
                FileUtils.saveFile(PayInfoActivity.this, mBitmap, fileName);
                showToast("保存成功");
            } catch (Exception e) {
                Toast.makeText(PayInfoActivity.this, R.string.permissions_map_error, Toast.LENGTH_LONG).show();
            }
        } else {
            EasyPermissions.requestPermissions(this,
                    getResources().getString(R.string.str_request_camera_message), 1, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        try {
            FileUtils.saveFile(PayInfoActivity.this, mBitmap, fileName);
            showToast("保存成功");
        } catch (Exception e) {
            Toast.makeText(PayInfoActivity.this, R.string.permissions_map_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(PayInfoActivity.this, R.string.permissions_map_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //用户充值

    /**
     * 充值
     *
     * @author pan
     * @time 2016/11/24 0024 下午 3:48
     */
    public void chargeMoneyRequest() {
        ChargeMoneyRequest rq = new ChargeMoneyRequest();
        rq.setUser_type(AppParam.user_type);//用户类型：1-注册用户，2-商家
        rq.setCharge_money_constant_id(charge_money_constant_id);
        rq.setPay_type(pay_type);
        rq.setOrders_number(orders_number);
        rq.setLogistics_id(user_id);
        rq.setPay_account(payAccount);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerpay);
    }

    private final AsyncHttpResponseHandler mHandlerpay = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, PayInfoActivity.this, ChargeMoneyResponse.class);
            if (kd != null) {
                ChargeMoneyResponse info = (ChargeMoneyResponse) kd;
                if (info.getStatus() == 1) {
                    showToast(info.getMessage());
                    if (TopUpActivity.instance != null) {
                        TopUpActivity.instance.finish();
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
    };

    boolean isShow = false;
    @OnClick(R.id.notice)
    public void showtips(View v) {
        if (isShow){
            isShow = false;
            tips.setVisibility(View.GONE);
        }else{
            tips.setVisibility(View.VISIBLE);
            isShow = true;
        }
    }

}
