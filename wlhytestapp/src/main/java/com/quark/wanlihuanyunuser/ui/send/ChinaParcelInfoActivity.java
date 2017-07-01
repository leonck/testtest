package com.quark.wanlihuanyunuser.ui.send;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.AddSendOrdersPackageRequest;
import com.quark.api.auto.bean.AddSendOrdersPackageResponse;
import com.quark.api.auto.bean.ExpressPriceRequest;
import com.quark.api.auto.bean.ExpressPriceResponse;
import com.quark.api.auto.bean.GoodsList;
import com.quark.api.auto.bean.IdcardRecordList;
import com.quark.api.auto.bean.IdcardRecordListRequest;
import com.quark.api.auto.bean.IdcardRecordListResponse;
import com.quark.api.auto.bean.ListUserAddress;
import com.quark.api.auto.bean.LogisticsAdList;
import com.quark.api.auto.bean.SendOrdersPackageEdit;
import com.quark.api.auto.bean.SendOrdersPackageInfoRequest;
import com.quark.api.auto.bean.SendOrdersPackageInfoResponse;
import com.quark.api.auto.bean.UpdateImageRequest;
import com.quark.api.auto.bean.UpdateImageResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.GoodsAdapter;
import com.quark.wanlihuanyunuser.api.ApiHttpClient;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.ui.chooseImage.FileItem;
import com.quark.wanlihuanyunuser.ui.chooseImage.ImageUtils;
import com.quark.wanlihuanyunuser.ui.personal.MyAdsActivity;
import com.quark.wanlihuanyunuser.ui.widget.ListViewForScrollView;
import com.quark.wanlihuanyunuser.ui.widget.WheelChooseBorthdayDialog;
import com.quark.wanlihuanyunuser.util.FileUtils;
import com.quark.wanlihuanyunuser.util.StringUtils;
import com.quark.wanlihuanyunuser.util.TLog;
import com.quark.wanlihuanyunuser.util.Utils;
import com.quark.wanlihuanyunuser.zxing.SecondActivity;

import org.kymjs.kjframe.http.HttpCallBack;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by pan on 2016/11/9 0009.
 * >#
 * 添加包裹信息 国际到中国物流
 * >#包裹信息
 */
public class ChinaParcelInfoActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @InjectView(R.id.send_one_tv)
    TextView sendOneTv;
    @InjectView(R.id.send_two_tv)
    TextView sendTwoTv;
    @InjectView(R.id.send_three_tv)
    TextView sendThreeTv;
    @InjectView(R.id.send_ly)
    LinearLayout sendLy;
    @InjectView(R.id.recevie_one_tv)
    TextView recevieOneTv;
    @InjectView(R.id.recevie_two_tv)
    TextView recevieTwoTv;
    @InjectView(R.id.recevie_three_tv)
    TextView recevieThreeTv;
    @InjectView(R.id.recevie_ly)
    LinearLayout recevieLy;
    @InjectView(R.id.start_time)
    EditText startTime;
    @InjectView(R.id.end_time)
    EditText endTime;
    @InjectView(R.id.company_name)
    TextView companyName;
    @InjectView(R.id.select_company)
    LinearLayout selectCompany;
    @InjectView(R.id.logistics_number)
    EditText logisticsNumber;
    @InjectView(R.id.note_tv)
    EditText noteTv;
    @InjectView(R.id.kg_et)
    EditText kgEt;
    @InjectView(R.id.value_et)
    EditText valueEt;
    @InjectView(R.id.ok_bt)
    Button okBt;
    @InjectView(R.id.goods_lv)
    ListViewForScrollView goodsLv;
    @InjectView(R.id.id_list)
    RelativeLayout idList;
    @InjectView(R.id.id_zheng)
    ImageView idZheng;
    @InjectView(R.id.id_fan)
    ImageView idFan;
    GoodsAdapter adapter;
    ArrayList<GoodsList> datas;
    String startTimeStr;
    String endTimeStr;
    @InjectView(R.id.id_number)
    EditText idNumber;

    private String send_orders_package_id;//寄送包裹快递ID
    private String user_type;//用户类型：1-注册用户，2-商家
    private String type;//1-新加波物流，2-国际中国物流
    private String idcard_record_id;//身份证记录是否国际物流：0-国内，其他-国际（大于0）
    private String idcard_number;//身份证号码
    private String front_card;//身份证正面
    private String back_card;//身份证背面
    private String send_user_address_id;//寄送地址
    private String collect_user_address_id;//收地址
    private String home_start_time;//上门开始时间
    private String home_end_time;//上门结束时间
    private String logistics_id;//物流公司ID
    private String logistics_name;//物流公司名称
    private String waybill_number;//运单号/快递单号/物流单号
    private String remarker;//备注
    private String package_goods;//多物品拼接{物品名称‘A’数量#物品名称‘A’数量}如：哈哈A3#2A12{物品名称=哈哈-选择了哈哈，数量=3}
    private String declared_weight;//申报重量-kg
    private String goods_value;//物品货值-元
    private int id_type;
    int position;
    String dataPositionStr;
    String sendCity, sendZone; //计算价格用
    String send_orders_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinaparcelinfo);
        ButterKnife.inject(this);
        setTopTitle("包裹信息");
        setBackButton();

        String times = Utils.getCurrentTime("yyyy.MM.dd hh:mm");
        startTime.setText(times);
        String atimes = Utils.getSpecifiedDayAfter(times);
        endTime.setText(atimes);
        startTimeStr = times;
        endTimeStr = atimes;

        datas = new ArrayList<>();
        adapter = new GoodsAdapter(ChinaParcelInfoActivity.this, datas, handler);
        goodsLv.setAdapter(adapter);

        send_orders_package_id = (String) getValue4Intent("send_orders_package_id");
        send_orders_id = (String) getValue4Intent("send_orders_id");
        dataPositionStr = (String) getValue4Intent("position");
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
        getIDHistoryData();
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
            Object kd = ApiResponse.get(arg2, ChinaParcelInfoActivity.this, SendOrdersPackageInfoResponse.class);
            if (kd != null) {
                SendOrdersPackageInfoResponse info = (SendOrdersPackageInfoResponse) kd;
                if (info.getStatus() == 1) {
                    ininEditView(info.getSendOrdersPackage());
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

        idcard_number = dt.getIdcard_number();
        front_card = dt.getFront_card();
        back_card = dt.getBack_card();
        idNumber.setText(idcard_number);
        ApiHttpClient.loadImage(front_card, idZheng, R.drawable.camera);
        ApiHttpClient.loadImage(back_card, idFan, R.drawable.camera);
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

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.send_ly, R.id.recevie_ly, R.id.start_time, R.id.end_time, R.id.scan_iv, R.id.select_company, R.id.items_ly, R.id.id_list, R.id.ok_bt, R.id.id_zheng, R.id.id_fan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_ly:
                Intent intent = new Intent(ChinaParcelInfoActivity.this, MyAdsActivity.class);
                intent.putExtra("select", "1");
                startActivityForResult(intent, 101);
                break;
            case R.id.recevie_ly:
                Intent intent2 = new Intent(ChinaParcelInfoActivity.this, MyAdsActivity.class);
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
            case R.id.select_company:
                if (Utils.isEmpty(sendCity)) {
                    showToast("请先选择寄件地址");
                } else {
                    Intent intent3 = new Intent(ChinaParcelInfoActivity.this, SelectLogsActivity.class);
                    intent3.putExtra("sendCity", sendCity);
                    intent3.putExtra("sendZone", sendZone);
                    startActivityForResult(intent3, 99);
                }
                break;
            case R.id.items_ly:
                if (!Utils.isEmpty(logistics_id)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("logistics_id", logistics_id);
                    Intent intent4 = new Intent(ChinaParcelInfoActivity.this, ItemsNameActivity.class);
                    intent4.putExtras(bundle);
                    startActivityForResult(intent4, 98);
                } else {
                    showToast("请先选择物流公司");
                }
                break;
            case R.id.scan_iv:
                Bundle bundle = new Bundle();
                bundle.putString("scan_code", "1");  //
                Intent inte2 = new Intent(ChinaParcelInfoActivity.this, SecondActivity.class);
                inte2.putExtras(bundle);
                startActivityForResult(inte2, 70);
                break;
            case R.id.id_list://身份证历史记录
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("idhistory", (Serializable) idhistory);
                Intent intent1 = new Intent(this, HistoryRecordActivity.class);
                intent1.putExtras(bundle1);
                startActivityForResult(intent1, 88);
                break;
            case R.id.ok_bt:
                if (check()) {
                    getData();
                }
                break;
            case R.id.id_zheng:
                id_type = 1;
                ImageUtils.showSheetPic(ChinaParcelInfoActivity.this, handlerphoto);
                break;
            case R.id.id_fan:
                id_type = 2;
                ImageUtils.showSheetPic(ChinaParcelInfoActivity.this, handlerphoto);
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
        if (Utils.isEmpty(idNumber.getText().toString())) {
            showToast("请输入身份证号码");
            return false;
        }
        if (Utils.isEmpty(front_card)) {
            showToast("请上传身份证正面");
            return false;
        }
        if (Utils.isEmpty(back_card)) {
            showToast("请上传身份证反面");
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

            Bundle data = msg.getData();
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
                    if (number < maxNumber) {
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
    AddSendOrdersPackageResponse addinfo;

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
        rq.setType("2");
        rq.setIdcard_record_id("1");
        rq.setIdcard_number(idNumber.getText().toString());
        rq.setSend_user_address_id(send_user_address_id);
        rq.setCollect_user_address_id(collect_user_address_id);
        rq.setHome_start_time(startTimeStr);
        rq.setHome_end_time(endTimeStr);
        rq.setLogistics_id(logistics_id);
        rq.setLogistics_name(logistics_name);
        rq.setFront_card(front_card);
        rq.setBack_card(back_card);
        rq.setWaybill_number(logisticsNumber.getText().toString());
        rq.setRemarker(noteTv.getText().toString());
        rq.setDeclared_weight(kgEt.getText().toString());
        rq.setGoods_value(valueEt.getText().toString());
        rq.setPackage_goods(package_goods);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlersend);
    }

    private final AsyncHttpResponseHandler mHandlersend = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, ChinaParcelInfoActivity.this, AddSendOrdersPackageResponse.class);
            if (kd != null) {
                addinfo = (AddSendOrdersPackageResponse) kd;
                if (addinfo.getStatus() == 1) {
                    getPriceData();
                } else {
                    showToast(addinfo.getMessage());
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
            Object kd = ApiResponse.get(arg2, ChinaParcelInfoActivity.this, ExpressPriceResponse.class);
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
                        list = new LogisticsAdList(logistics_name, logistics_id, package_goods, kgEt.getText().toString(), waybill_number, sbStr, totalMoney, addinfo.getSend_orders_package_id());
                    }
//                    LogisticsAdList list = new LogisticsAdList(logistics_name, package_goods, kgEt.getText().toString(), waybill_number, sbStr, totalMoney,addinfo.getSend_orders_package_id());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("datas", list);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    if (dataPositionStr != null) {
                        setResult(91, intent);
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

    /*=========================拍照===========================*/
    public static final int ACTION_TYPE_ALBUM = 0;
    public static final int ACTION_TYPE_PHOTO = 1;
    private boolean isChangeFace = false;

    private final static int CROP = 200;

    private final static String FILE_SAVEPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/daka/";
    private Uri origUri;
    private File protraitFile;
    private Bitmap protraitBitmap;
    private String protraitPath;

    /**
     * 上传身份证照片
     */
    private void uploadNewPhoto() {
//        showWaitDialog("正在上传头像...");

        // 获取头像缩略图
        if (!StringUtils.isEmpty(protraitPath) && protraitFile.exists()) {
            protraitBitmap = ImageUtils.loadImgThumbnail(protraitPath, AppParam.picx, AppParam.picy);
        } else {
            AppContext.showToast("图像不存在，上传失败");
        }
        if (protraitBitmap != null) {
            try {
                List<FileItem> ls = new LinkedList<FileItem>();
                FileItem f = new FileItem("images_01", protraitFile);
                ls.add(f);
                UpdateImageRequest rq = new UpdateImageRequest();
                showWait(true);
                QuarkApi.HttpuploadFileNewList(rq, ls, httpCallBack);
            } catch (Exception e) {
                AppContext.showToast("图像不存在，上传失败");
            }
        }
    }

    HttpCallBack httpCallBack = new HttpCallBack() {
        @Override
        public void onSuccess(String t) {
            super.onSuccess(t);
            showWait(false);
            Log.e("error", "==" + t);
            try {
                UpdateImageResponse info = new UpdateImageResponse(t);
                showToast(info.getMessage());
                if (id_type == 1) {
                    idZheng.setImageBitmap(protraitBitmap);
                    front_card = info.getFileName();
                } else if (id_type == 2) {
                    idFan.setImageBitmap(protraitBitmap);
                    back_card = info.getFileName();
                }
            } catch (Exception e) {
                Log.e("error", "數據解析出錯");
            }
            showWait(false);
        }

        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("图片上传失败");

            showWait(false);
        }
    };

    /**
     * 选择图片裁剪
     */
    private void startImagePick() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
        }
    }

    private void startTakePhoto() {
        Intent intent;
        // 判断是否挂载了SD卡
        String savePath = "";
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/dakatemp/";
            File savedir = new File(savePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }
        // 没有挂载SD卡，无法保存文件
        if (StringUtils.isEmpty(savePath)) {
            AppContext.showToastShort("无法保存照片，请检查SD卡是否挂载");
            return;
        }

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String fileName = "temp_" + timeStamp + ".jpg";// 照片命名
        File out = new File(savePath, fileName);
        Uri uri = Uri.fromFile(out);
        origUri = uri;

        String theLarge = savePath + fileName;

        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent,
                ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
    }

    // 裁剪头像的绝对路径
    private Uri getUploadTempFile(Uri uri) {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File savedir = new File(FILE_SAVEPATH);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            AppContext.showToast("无法保存上传的图片，请检查SD卡是否挂载");
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

        // 如果是标准Uri
        if (StringUtils.isEmpty(thePath)) {
            thePath = ImageUtils.getAbsoluteImagePath(ChinaParcelInfoActivity.this, uri);
        }
        String ext = FileUtils.getFileFormat(thePath);
        ext = StringUtils.isEmpty(ext) ? "jpg" : ext;
        // 照片命名
        String cropFileName = "daka_" + timeStamp + "." + ext;
        // 裁剪头像的绝对路径
        protraitPath = FILE_SAVEPATH + cropFileName;
        protraitFile = new File(protraitPath);

        return Uri.fromFile(protraitFile);
    }

    /**
     * 拍照后裁剪
     *
     * @param data 原始图片
     */
    private void startActionCrop(Uri data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("output", this.getUploadTempFile(data));
        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 9999);// 裁剪框比例
//        intent.putExtra("aspectY", 9998);
        intent.putExtra("aspectX", 85);// 裁剪框比例
        intent.putExtra("aspectY", 54);
        intent.putExtra("outputX", AppParam.picx);// 输出图片大小
        intent.putExtra("outputY", AppParam.picy);
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        startActivityForResult(intent,
                ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode,
                                 final Intent imageReturnIntent) {
        if (requestCode != 0) {
            if (resultCode == 101) {
                send_user_address_id = imageReturnIntent.getStringExtra("id");
                if (!send_user_address_id.equals(collect_user_address_id)) {
                    ListUserAddress adsItem = (ListUserAddress) imageReturnIntent.getSerializableExtra("ads_item");
                    sendOneTv.setText(adsItem.getProvince() + " " + adsItem.getName() + " " + adsItem.getTelephone());
                    sendTwoTv.setText(adsItem.getAddress());
                    sendThreeTv.setText(adsItem.getProvince() + adsItem.getCity() + adsItem.getArea());
                    sendCity = adsItem.getCity();
                    sendZone = adsItem.getArea();

                } else {
                    showToast("寄件地址不能与收货地址相同");
                    send_user_address_id = "";
                }
            } else if (resultCode == 102) {
                collect_user_address_id = imageReturnIntent.getStringExtra("id");
                if (!collect_user_address_id.equals(send_user_address_id)) {
                    ListUserAddress adsItem = (ListUserAddress) imageReturnIntent.getSerializableExtra("ads_item");
                    recevieOneTv.setText(adsItem.getProvince() + " " + adsItem.getName() + " " + adsItem.getTelephone());
                    recevieTwoTv.setText(adsItem.getAddress());
                    recevieThreeTv.setText(adsItem.getProvince() + adsItem.getCity() + adsItem.getArea());
                    TLog.error("收地址id" + collect_user_address_id);
                    checkIsInHistory(adsItem.getName());
                } else {
                    showToast("寄件地址不能与收货地址相同");
                    collect_user_address_id = "";
                }
            } else if (resultCode == 99) {
                logistics_id = imageReturnIntent.getStringExtra("wuliu_id");
                logistics_name = imageReturnIntent.getStringExtra("wuliu_name");
                companyName.setText(logistics_name);
                if (datas != null && datas.size() > 0) {
                    datas.clear();
                    adapter.notifyDataSetChanged();
                }
            } else if (resultCode == 98) {
                String goodsName = imageReturnIntent.getStringExtra("goods_name");
                String goodsId = imageReturnIntent.getStringExtra("logistics_send_goods_id");
                int goods_number = imageReturnIntent.getIntExtra("goods_number", 0);
                if (goods_number == 0) {
                    goods_number = 10000;
                }
                int id = Integer.valueOf(goodsId);
                GoodsList goodsList = new GoodsList();
                goodsList.setGoods_name(goodsName);
                goodsList.setGoods_number(1);
//                goodsList.setSend_orders_package_goods_id(id);
                 goodsList.setLogistics_send_goods_id(id);

                goodsList.setLimit_amount(goods_number);
                datas.add(goodsList);
                adapter.notifyDataSetChanged();

            } else if (resultCode == 88) {
                idcard_number = imageReturnIntent.getStringExtra("id_number");
                front_card = imageReturnIntent.getStringExtra("front_card");
                back_card = imageReturnIntent.getStringExtra("back_card");
                idhistory = (List<IdcardRecordList>) imageReturnIntent.getSerializableExtra("idhistory");

                idNumber.setText(idcard_number);
                ApiHttpClient.loadImage(front_card, idZheng, R.drawable.camera);
                ApiHttpClient.loadImage(back_card, idFan, R.drawable.camera);
            } else if (resultCode == 70) {
                String code = imageReturnIntent.getStringExtra("code");
                logisticsNumber.setText(code);
            }

        }
        //圖片
        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
                startActionCrop(origUri);// 拍照后裁剪
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
                startActionCrop(imageReturnIntent.getData());// 选图后裁剪
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
                uploadNewPhoto();
                break;
        }
    }

    private static final int CAMERA_PERM = 1;

    @AfterPermissionGranted(CAMERA_PERM)
    private void startTakePhotoByPermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(ChinaParcelInfoActivity.this, perms)) {
            try {
                startTakePhoto();
            } catch (Exception e) {
                Toast.makeText(ChinaParcelInfoActivity.this, R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
            }
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.str_request_camera_message), CAMERA_PERM, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        try {
            startTakePhoto();
        } catch (Exception e) {
            Toast.makeText(ChinaParcelInfoActivity.this, R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(ChinaParcelInfoActivity.this, R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private Handler handlerphoto = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    startTakePhotoByPermissions();
                    break;
                case 2:
                    startImagePick();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

        ;
    };
/*=========================拍照end===========================*/

    List<IdcardRecordList> idhistory;

    /**
     * 获取身份证历史记录
     *
     * @author pan
     * @time 2016/11/29 0029 下午 2:12
     */
    public void getIDHistoryData() {
        IdcardRecordListRequest rq = new IdcardRecordListRequest();
        rq.setUser_type(AppParam.user_type);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, ChinaParcelInfoActivity.this, IdcardRecordListResponse.class);
            if (kd != null) {
                IdcardRecordListResponse info = (IdcardRecordListResponse) kd;
                if (info.getStatus() == 1) {
                    idhistory = info.getIdcardRecordList();
                    if (idhistory == null) {
                        idhistory = new ArrayList<>();
                    }
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

    public void checkIsInHistory(String name) {
        for (int i = 0; i < idhistory.size(); i++) {
            if (name.equals(idhistory.get(i).getName())) {
                front_card = idhistory.get(i).getFront_card();
                back_card = idhistory.get(i).getBack_card();

                idNumber.setText(idhistory.get(i).getIdcard_number());
                ApiHttpClient.loadImage(front_card, idZheng, R.drawable.camera);
                ApiHttpClient.loadImage(back_card, idFan, R.drawable.camera);
                break;
            }
        }
    }
}
