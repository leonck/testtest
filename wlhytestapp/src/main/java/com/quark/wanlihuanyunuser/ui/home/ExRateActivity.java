package com.quark.wanlihuanyunuser.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.quark.api.auto.bean.RateBean;
import com.quark.api.auto.bean.RateResponse;
import com.quark.api.auto.bean.RateResultBean;
import com.quark.api.auto.bean.RateResultResponse;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.ui.widget.WheelChooseRateDialog;
import com.quark.wanlihuanyunuser.util.RateUtils;
import com.quark.wanlihuanyunuser.util.TLog;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pan on 2016/11/8 0008.
 * >#
 * >#汇率查询
 */
public class ExRateActivity extends BaseActivity {
    List<RateBean> rateTypes;

    @InjectView(R.id.time)
    TextView time;
    @InjectView(R.id.number_et)
    EditText numberEt;
    @InjectView(R.id.from_typesx)
    TextView fromTypesx;
    @InjectView(R.id.from_typename)
    TextView fromTypename;
    @InjectView(R.id.to_typesx)
    TextView toTypesx;
    @InjectView(R.id.to_typename)
    TextView toTypename;
    @InjectView(R.id.result)
    TextView resultView;

    String fromTypesxStr = "CNY", fromTypenameStr = "人民币", toTypesxStr = "NZD", toTypenameStr = "新西兰元";
    double number;
    List<RateResultBean> resultRates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exrate);
        ButterKnife.inject(this);
        setTopTitle("汇率查询");
        setBackButton();

        RateResponse noticejiadians = JSON.parseObject(RateUtils.types, RateResponse.class);
        rateTypes = noticejiadians.getResult().getList();
    }

    @OnClick({R.id.left_ly, R.id.right_ly})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_ly:
                WheelChooseRateDialog dsb = new WheelChooseRateDialog();
                dsb.showSheetPic(this, handler, 202, rateTypes, "50", 50);
                break;
            case R.id.right_ly:
                WheelChooseRateDialog db2 = new WheelChooseRateDialog();
                db2.showSheetPic(this, handler, 203, rateTypes, "50", 50);
                break;
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 202:
                    int position = msg.arg1;
                    fromTypesxStr = rateTypes.get(position).getCode();
                    fromTypenameStr = rateTypes.get(position).getName();
                    fromTypesx.setText(fromTypesxStr);
                    fromTypename.setText(fromTypenameStr);
                    break;
                case 203:
                    int positionto = msg.arg1;
                    toTypesxStr = rateTypes.get(positionto).getCode();
                    toTypenameStr = rateTypes.get(positionto).getName();
                    toTypesx.setText(toTypesxStr);
                    toTypename.setText(toTypenameStr);
                    break;
                default:
                    break;
            }
        }
    };

    boolean isExchange = false;

    @OnClick(R.id.exchange)
    public void exchange(View b) {
        if (resultRates != null && resultRates.size() > 1) {
            if (isExchange) { //换过了
                isExchange = false;
                Message msg = new Message();
                msg.obj = resultRates.get(0);
                msg.what = 1;
                handlershow.sendMessage(msg);
            } else {
                isExchange = true;
                Message msg = new Message();
                msg.obj = resultRates.get(1);
                msg.what = 1;
                handlershow.sendMessage(msg);
            }

            String tfrom,tcode;
            tfrom = fromTypenameStr;
            tcode = fromTypesxStr;

            fromTypenameStr = toTypenameStr;
            fromTypesxStr = toTypesxStr;

            toTypenameStr = tfrom;
            toTypesxStr = tcode;

            fromTypesx.setText(fromTypesxStr);
            fromTypename.setText(fromTypenameStr);
            toTypesx.setText(toTypesxStr);
            toTypename.setText(toTypenameStr);
        }
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    @OnClick(R.id.login_bt)
    public void login_bt(View b) {
        String numberstr = numberEt.getText().toString();
        if (Utils.isEmpty(numberstr)) {
            showToast("请输入金额");
        } else {
            try {
                number = Double.valueOf(numberstr);
                showWait(true);
                getData();
            } catch (Exception e) {
                showToast("请输入正确金额");
                e.printStackTrace();
            }

        }
    }


    //============================================对接聚合数据=================================
    public void getData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    getRequest3();
                } catch (Exception e) {
                    e.printStackTrace();
                    showWait(false);
                }
            }
        }.start();
    }

    //3.实时汇率查询换算
    public void getRequest3() {
        String result = null;
        Map params = new HashMap();//请求参数
        params.put("from", fromTypesxStr);//转换汇率前的货币代码
        params.put("to", toTypesxStr);//转换汇率成的货币代码
        params.put("key", RateUtils.APPKEY);//应用APPKEY(应用详细页查询)
        try {
            result = RateUtils.net(RateUtils.url, params, "GET");
            TLog.error("查询结果" + result);
            RateResultResponse resultResponse = JSON.parseObject(result, RateResultResponse.class);
            if ("0".equals(resultResponse.getError_code())) {
                resultRates = resultResponse.getResult();
                Message msg = new Message();
                msg.obj = resultRates.get(0);
                msg.what = 1;
                handlershow.sendMessage(msg);
                isExchange = false;
            } else {
                showToast(resultResponse.getReason());
                showWait(false);
            }
        } catch (Exception e) {
            showToast("查询失败");
            showWait(false);
            e.printStackTrace();
        }
    }

    private Handler handlershow = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    RateResultBean result = (RateResultBean) msg.obj;
                    showResult(result);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

        ;
    };

    public void showResult(RateResultBean result) {
        showWait(false);
        time.setText(result.getUpdateTime());
        try {
            double exchange = Double.valueOf(result.getExchange());
            resultView.setText(number + result.getCurrencyF() + "  =  " + Utils.DoubleFormat4(exchange * number) + result.getCurrencyT());
        } catch (Exception e) {
            e.printStackTrace();
            showToast("查询失败");
        }
    }

}
