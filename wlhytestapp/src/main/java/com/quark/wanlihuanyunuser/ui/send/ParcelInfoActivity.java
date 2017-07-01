package com.quark.wanlihuanyunuser.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.AddSendOrdersPackageRequest;
import com.quark.api.auto.bean.AddSendOrdersPackageResponse;
import com.quark.api.auto.bean.ExpressPriceRequest;
import com.quark.api.auto.bean.ExpressPriceResponse;
import com.quark.api.auto.bean.GoodsList;
import com.quark.api.auto.bean.ListUserAddress;
import com.quark.api.auto.bean.LogisticsAdList;
import com.quark.api.auto.bean.SendOrdersPackageEdit;
import com.quark.api.auto.bean.SendOrdersPackageInfoRequest;
import com.quark.api.auto.bean.SendOrdersPackageInfoResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.GoodsAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.ui.personal.MyAdsActivity;
import com.quark.wanlihuanyunuser.ui.widget.ListViewForScrollView;
import com.quark.wanlihuanyunuser.ui.widget.WheelChooseBorthdayDialog;
import com.quark.wanlihuanyunuser.util.TLog;
import com.quark.wanlihuanyunuser.util.Utils;
import com.quark.wanlihuanyunuser.zxing.SecondActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

import static com.quark.wanlihuanyunuser.R.id.select_company;

/**
 * Created by pan on 2016/11/9 0009.
 * >#
 * 添加包裹信息 新西兰国内
 * >#包裹信息
 */
public class ParcelInfoActivity extends BaseActivity {

    @InjectView(R.id.send_one_tv)
    TextView sendOneTv;
    @InjectView(R.id.send_two_tv)
    TextView sendTwoTv;
    @InjectView(R.id.send_three_tv)
    TextView sendThreeTv;
    @InjectView(R.id.recevie_one_tv)
    TextView recevieOneTv;
    @InjectView(R.id.recevie_two_tv)
    TextView recevieTwoTv;
    @InjectView(R.id.recevie_three_tv)
    TextView recevieThreeTv;
    @InjectView(R.id.start_time)
    EditText startTime;
    @InjectView(R.id.end_time)
    EditText endTime;
    @InjectView(R.id.company_name)
    TextView companyName;
    @InjectView(R.id.logistics_number)
    EditText logisticsNumber;
    @InjectView(R.id.note_tv)
    EditText noteTv;
    @InjectView(R.id.kg_et)
    EditText kgEt;
    @InjectView(R.id.value_et)
    EditText valueEt;
    @InjectView(R.id.goods_lv)
    ListViewForScrollView goodsLv;
    @InjectView(select_company)
    LinearLayout selectCompany;

    String startTimeStr;//开始时间
    String endTimeStr; //结束时间
    String send_orders_id;//寄送包裹快递ID
    String send_user_address_id;// 	寄送地址
    String collect_user_address_id;//收地址
    String logistics_id;//物流公司ID
    String logistics_name;//物流公司名称
    String waybill_number;//运单号/快递单号/物流单号
    String declared_weight;//申报重量-kg
    String package_goods;//多物品拼接{物品名称‘A’数量#物品名称‘A’数量}如：哈哈A3#2A12{物品名称=哈哈-选择了哈哈，数量=3}
    ArrayList<GoodsList> datas;
    GoodsAdapter adapter;

    String sendCity, sendZone; //计算价格用
    int position;
    //    LogisticsAdList packages;
    String dataPositionStr, send_orders_package_id = "";

//    String logistics_name,logistics_id; //物流公司名称 物流公司id 如果有包裹进来物流公司不能选择

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelinfo);
        ButterKnife.inject(this);
        setTopTitle("包裹信息");
        setBackButton();

        datas = new ArrayList<>();
        adapter = new GoodsAdapter(ParcelInfoActivity.this, datas, handler);
        goodsLv.setAdapter(adapter);

        send_orders_id = (String) getValue4Intent("send_orders_id");
        send_orders_package_id = (String) getValue4Intent("send_orders_package_id");
        dataPositionStr = (String) getValue4Intent("position");

        String times = Utils.getCurrentTime("yyyy.MM.dd hh:mm");
        startTime.setText(times);
        String atimes = Utils.getSpecifiedDayAfter(times);
        endTime.setText(atimes);
        startTimeStr = times;
        endTimeStr = atimes;

        if (dataPositionStr != null) {
            getEditData();
        } else {
            logistics_name = (String) getValue4Intent("logistics_name");
            logistics_id = (String) getValue4Intent("logistics_id");
            if (!Utils.isEmpty(logistics_id)) {
                companyName.setText(logistics_name);
                selectCompany.setEnabled(false);
            }
        }
        initEt();

//        showToast("请先选择寄件地址");
    }

    //编辑数据
    public void getEditData() {
        SendOrdersPackageInfoRequest rq = new SendOrdersPackageInfoRequest();
        rq.setSend_orders_package_id(send_orders_package_id);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandleredit);
    }

    private final AsyncHttpResponseHandler mHandleredit = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, ParcelInfoActivity.this, SendOrdersPackageInfoResponse.class);
            if (kd != null) {
                SendOrdersPackageInfoResponse info = (SendOrdersPackageInfoResponse) kd;
                ininEditView(info.getSendOrdersPackage());
            }
            showWait(false);
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
            showWait(false);
        }
    };

    public void ininEditView(SendOrdersPackageEdit dt) {
        sendOneTv.setText(dt.getSend_name() + "  " + dt.getSend_telephone());
        sendTwoTv.setText(dt.getSend_short_area());
        sendThreeTv.setText(dt.getSend_province() + " " + dt.getSend_city() + " " + dt.getSend_area());

        sendCity = dt.getSend_city();
        sendZone = dt.getSend_area();

        collect_user_address_id = dt.getCollect_user_address_id() + "";
        recevieOneTv.setText(dt.getCollect_name() + "  " + dt.getCollect_telephone());
        recevieTwoTv.setText(dt.getCollect_short_area());
        recevieThreeTv.setText(dt.getCollect_province() + " " + dt.getCollect_city() + " " + dt.getCollect_area());

        startTimeStr = (String) dt.getHome_start_time();
        startTime.setText(startTimeStr);
        endTimeStr = (String) dt.getHome_end_time();
        endTime.setText(endTimeStr);
        send_user_address_id = dt.getSend_user_address_id() + "";

        logistics_id = dt.getLogistics_id() + "";
        logistics_name = dt.getLogistics_name();
        companyName.setText(logistics_name);

        noteTv.setText(dt.getRemarker());
        logisticsNumber.setText(dt.getWaybill_number());

        List<GoodsList> gds = dt.getSendOrdersPackageGoodsList();
        datas.addAll(gds);
        adapter.notifyDataSetChanged();

        declared_weight = dt.getDeclared_weight();
        kgEt.setText(declared_weight);
        valueEt.setText(dt.getGoods_value());
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    /**
     * 处理edittext逻辑
     *
     * @author pan
     * @time 2016/12/16 0016 上午 10:18
     */
    public void initEt() {
        kgEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        kgEt.setText(s);
                        kgEt.setSelection(s.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        valueEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        valueEt.setText(s);
                        valueEt.setSelection(s.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.send_ly, R.id.recevie_ly, R.id.start_time, R.id.end_time, R.id.scan_iv, select_company, R.id.items_ly, R.id.ok_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_ly:
                Intent intent = new Intent(ParcelInfoActivity.this, MyAdsActivity.class);
                intent.putExtra("select", "1");
                startActivityForResult(intent, 101);
                break;
            case R.id.recevie_ly:
                Intent intent2 = new Intent(ParcelInfoActivity.this, MyAdsActivity.class);
                intent2.putExtra("select", "2");
                startActivityForResult(intent2, 102);
                break;
            case R.id.start_time:
                WheelChooseBorthdayDialog dsb = new WheelChooseBorthdayDialog();
                dsb.showDateorTimePic(this, handler, 202);
                break;
            case R.id.end_time:
                WheelChooseBorthdayDialog db = new WheelChooseBorthdayDialog();
                db.showDateorTimePic(this, handler, 203);
                break;
            case select_company:
                if (Utils.isEmpty(sendCity)) {
                    showToast("请先选择寄件地址");
                } else {
                    Intent intent3 = new Intent(ParcelInfoActivity.this, SelectLogsActivity.class);
                    intent3.putExtra("sendCity", sendCity);
                    intent3.putExtra("sendZone", sendZone);
                    startActivityForResult(intent3, 99);
                }
                break;
            case R.id.items_ly:
                if (!Utils.isEmpty(logistics_id)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("logistics_id", logistics_id);
                    bundle.putSerializable("choosedatas", datas);
                    Intent intent4 = new Intent(ParcelInfoActivity.this, ItemsNameActivity.class);
                    intent4.putExtras(bundle);
                    startActivityForResult(intent4, 98);
                } else {
                    showToast("请先选择物流公司");
                }
                break;
            case R.id.scan_iv:
                Bundle bundle = new Bundle();
                bundle.putString("scan_code", "1");//
                Intent intent1 = new Intent(ParcelInfoActivity.this, SecondActivity.class);
                intent1.putExtras(bundle);
                startActivityForResult(intent1, 70);
                break;
            case R.id.ok_bt:
                if (check()) {
                    getData();
                }
                break;
        }
    }

    public boolean check() {
        StringBuilder sb = new StringBuilder();
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
//                sb.append("#" + datas.get(i).getSend_orders_package_goods_id() + "A" + datas.get(i).getGoods_number());
                sb.append("#" + datas.get(i).getLogistics_send_goods_id() + "A" + datas.get(i).getGoods_number());
            }
            package_goods = sb.substring(1, sb.length());
            TLog.error("物品名称" + package_goods);
        }
        if (Utils.isEmpty(send_user_address_id)) {
            showToast("请选择寄件地址");
            return false;
        }
        if (Utils.isEmpty(collect_user_address_id)) {
            showToast("请选择收件地址");
            return false;
        }
        if (Utils.isEmpty(startTimeStr)) {
            showToast("请选择开始时间");
            return false;
        }
        if (Utils.isEmpty(endTimeStr)) {
            showToast("请选择结束时间");
            return false;
        }
        if (Utils.isEmpty(logistics_id)) {
            showToast("请选择物流公司");
            return false;
        }
        if (Utils.isEmpty(package_goods)) {
            showToast("请选择物品名称");
            return false;
        }
        declared_weight = kgEt.getText().toString();
        if (Utils.isEmpty(declared_weight)) {
            showToast("请填写申报重量");
            return false;
        }
        if (Utils.isEmpty(valueEt.getText().toString())) {
            showToast("请填写物品货值");
            return false;
        }
        waybill_number = logisticsNumber.getText().toString();
        return true;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 202:
                    startTimeStr = (String) msg.obj;
                    startTime.setText(startTimeStr);
                    break;
                case 203:
                    endTimeStr = (String) msg.obj;
                    endTime.setText(endTimeStr);
                    break;
                case 30:
                    int delete_position = msg.arg1;
                    datas.remove(delete_position);
                    adapter.notifyDataSetChanged();
                    break;
                case 31:
                    position = msg.arg1;
                    int maxNumber = datas.get(position).getLimit_amount();
                    int number = datas.get(position).getGoods_number();
                    if (number < maxNumber || maxNumber == 0) {
                        number++;
                        datas.get(position).setGoods_number(number);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case 32:
                    position = msg.arg1;
                    int numbe = datas.get(position).getGoods_number();
                    if (numbe > 1) {
                        numbe--;
                        datas.get(position).setGoods_number(numbe);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 0) {
            if (resultCode == 101) {
                send_user_address_id = data.getStringExtra("id");
                if (!send_user_address_id.equals(collect_user_address_id)) {
                    ListUserAddress adsItem = (ListUserAddress) data.getSerializableExtra("ads_item");
                    sendOneTv.setText(adsItem.getName() + " " + adsItem.getTelephone());
                    sendTwoTv.setText(adsItem.getAddress());
                    sendThreeTv.setText(adsItem.getProvince() + " " + adsItem.getCity() + " " + adsItem.getArea());
                    sendCity = adsItem.getCity();
                    sendZone = adsItem.getArea();
                } else {
                    showToast("寄件地址不能与收货地址相同");
                    send_user_address_id = "";
                }
            } else if (resultCode == 102) {
                collect_user_address_id = data.getStringExtra("id");
                if (!collect_user_address_id.equals(send_user_address_id)) {
                    ListUserAddress adsItem = (ListUserAddress) data.getSerializableExtra("ads_item");
                    recevieOneTv.setText(adsItem.getName() + " " + adsItem.getTelephone());
                    recevieTwoTv.setText(adsItem.getAddress());
                    recevieThreeTv.setText(adsItem.getProvince() + " " + adsItem.getCity() + " " + adsItem.getArea());
                    TLog.error("收地址id" + collect_user_address_id);
                } else {
                    showToast("寄件地址不能与收货地址相同");
                    collect_user_address_id = "";
                }
            } else if (resultCode == 99) {
                logistics_id = data.getStringExtra("wuliu_id");
                logistics_name = data.getStringExtra("wuliu_name");
                companyName.setText(logistics_name);
                if (datas != null && datas.size() > 0) {
                    datas.clear();
                    adapter.notifyDataSetChanged();
                }
            } else if (resultCode == 98) {
                String goodsName = data.getStringExtra("goods_name");
                String goodsId = data.getStringExtra("logistics_send_goods_id");
                int goods_number = data.getIntExtra("goods_number", 0);
                if (goods_number == 0) {
                    goods_number = 10000;
                }
                GoodsList goodsList = new GoodsList();
                int id = Integer.valueOf(goodsId);
                goodsList.setGoods_name(goodsName);
                goodsList.setGoods_number(1);
//                goodsList.setSend_orders_package_goods_id(id);
                goodsList.setLogistics_send_goods_id(id);
                goodsList.setLimit_amount(goods_number);
                datas.add(goodsList);
                adapter.notifyDataSetChanged();
            } else if (resultCode == 70) {
                String code = data.getStringExtra("code");
                logisticsNumber.setText(code);
            }
        }
    }

    /**
     * 添加包裹信息
     *
     * @author pan
     * @time 2016/11/25 0025 下午 6:31
     */
    public void getData() {
        AddSendOrdersPackageRequest rq = new AddSendOrdersPackageRequest();
        rq.setSend_orders_id(send_orders_id);
        rq.setUser_type(AppParam.user_type);
        if (Utils.isEmpty(send_orders_package_id)) {
            rq.setSend_orders_package_id("0");
        } else {
            rq.setSend_orders_package_id(send_orders_package_id);
        }
        rq.setType("1");
        rq.setIdcard_record_id("0");
        rq.setSend_user_address_id(send_user_address_id);
        rq.setCollect_user_address_id(collect_user_address_id);
        rq.setHome_start_time(startTimeStr);
        rq.setHome_end_time(endTimeStr);
        rq.setLogistics_id(logistics_id);
        rq.setLogistics_name(logistics_name);
        rq.setWaybill_number(logisticsNumber.getText().toString());
        rq.setRemarker(noteTv.getText().toString());
        rq.setDeclared_weight(declared_weight);
        rq.setGoods_value(valueEt.getText().toString());
        rq.setPackage_goods(package_goods);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlersend);
    }

    AddSendOrdersPackageResponse payinfo;
    private final AsyncHttpResponseHandler mHandlersend = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, ParcelInfoActivity.this, AddSendOrdersPackageResponse.class);
            if (kd != null) {
                payinfo = (AddSendOrdersPackageResponse) kd;
                if (payinfo.getStatus() == 1) {
                    getPriceData();
                } else {
                    showToast(payinfo.getMessage());
                    showWait(false);
                }
            }
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
            showWait(false);
        }
    };

    public void getPriceData() {
        ExpressPriceRequest rq = new ExpressPriceRequest();
        rq.setArea(sendZone);
        rq.setCity(sendCity);
        rq.setLogistics_id(logistics_id);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, ParcelInfoActivity.this, ExpressPriceResponse.class);
            if (kd != null) {
                ExpressPriceResponse info = (ExpressPriceResponse) kd;
                if (info.getStatus() == 1) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < datas.size(); i++) {//拼接物品名称
                        sb.append(datas.get(i).getGoods_name() + "x" + datas.get(i).getGoods_number() + "、");
                    }
                    String sbStr = sb.toString();
                    if (sbStr.endsWith("、")) {
                        sbStr = sbStr.substring(0, sbStr.length() - 1);
                    }
                    double price = Double.valueOf(info.getUnit_price());
                    double declared_weightd = Double.valueOf(declared_weight);
                    double totalMoney = price * declared_weightd;
                    LogisticsAdList list;
                    if (dataPositionStr != null) {
                        list = new LogisticsAdList(logistics_name, logistics_id, package_goods, kgEt.getText().toString(), waybill_number, sbStr, totalMoney, send_orders_package_id);
                    } else {
                        list = new LogisticsAdList(logistics_name, logistics_id, package_goods, kgEt.getText().toString(), waybill_number, sbStr, totalMoney, payinfo.getSend_orders_package_id());
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("datas", list);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    if (dataPositionStr != null) {
                        setResult(91, intent);//编辑
                    } else {
                        setResult(90, intent);
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

}
