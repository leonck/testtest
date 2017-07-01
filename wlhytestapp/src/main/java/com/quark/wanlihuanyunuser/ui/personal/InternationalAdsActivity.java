package com.quark.wanlihuanyunuser.ui.personal;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.AddUserAddressRequest;
import com.quark.api.auto.bean.AddUserAddressResponse;
import com.quark.api.auto.bean.ListUserAddress;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseFragementActivity;
import com.quark.wanlihuanyunuser.googlemap.LocationUtils;
import com.quark.wanlihuanyunuser.googlemap.PlaceAutocompleteAdapter;
import com.quark.wanlihuanyunuser.ui.widget.WheelChooseDialog;
import com.quark.wanlihuanyunuser.util.TLog;
import com.quark.wanlihuanyunuser.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

import static com.quark.wanlihuanyunuser.R.id.ads_et;
import static com.quark.wanlihuanyunuser.R.id.phone_et;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#新西兰国内地址
 */
public class InternationalAdsActivity extends BaseFragementActivity implements
        GoogleApiClient.OnConnectionFailedListener {
    @InjectView(R.id.user_et)
    EditText userEt;
    @InjectView(R.id.details_et)
    AutoCompleteTextView detailsEt;
    @InjectView(phone_et)
    EditText phoneEt;
    @InjectView(R.id.save_bt)
    Button saveBt;
    @InjectView(ads_et)
    EditText adsEt;

    String name;//姓名
    String telephone;//手机号码
    String province;//省
    String city;//市
    String area;//区
    String address;//详细地址
    LatLng latLng = null;

    String oper_type;//1-添加，2-编辑
    String user_address_id;//编辑
    ListUserAddress eads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internationalads);
        ButterKnife.inject(this);
        setTopTitle("填写地址");
        setBackButton();
        oper_type = (String)getValue4Intent("oper_type");
        user_address_id = (String)getValue4Intent("user_address_id");
        eads = (ListUserAddress)getValue4Intent("listUserAddress");
        if ("2".equals(oper_type)){
            initEditView();
        }
        autoSet();
    }

    public void initEditView(){
        userEt.setText(eads.getName());
        phoneEt.setText(eads.getTelephone());
        detailsEt.setText(eads.getAddress());
        area = eads.getArea();
        city = eads.getCity();
        province = eads.getProvince();
        adsEt.setText(area + " " + city + " " + province);

    }

    @OnClick({ads_et})
    public void onClick(View view) {
        switch (view.getId()) {
            case ads_et:
                WheelChooseDialog ds = new WheelChooseDialog();
                ds.showSheetPic(this, handler,"NewZealand");
                break;
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 201:
                    String address = msg.obj + "";
                    String addStr[] = address.split(",");
                    province = addStr[0];
                    city = addStr[1];
                    area = addStr[2];
                    adsEt.setText(area + " " + city + " " + province);

                    TLog.error("sheng" + province + "shi" + city + "qu" + area);
                    break;

                default:
                    break;
            }
        }
    };


    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    private boolean check() {
        name = userEt.getText().toString();
        telephone = phoneEt.getText().toString();
//        area = detailsEt.getText().toString();
        address = detailsEt.getText().toString();
//        city = cityEt.getText().toString();
//        province = provinceEt.getText().toString();

        if (Utils.isEmpty(area)) {
            showToast("please input choose zone/city/province");
            return false;
        }
        if (Utils.isEmpty(address)) {
            showToast("please input apartment,suite,unit,building,floor");
            return false;
        }
//        if (Utils.isEmpty(city)) {
//            showToast("please input city");
//            return false;
//        }
//        if (Utils.isEmpty(province)) {
//            showToast("please input state/province/region");
//            return false;
//        }
        if (Utils.isEmpty(name)) {
            showToast("please input full name");
            return false;
        }
        if (Utils.isEmpty(telephone)) {
            showToast("please input phone number");
            return false;
        }
        latLng = null;

        Address addressRE = LocationUtils.getLatlng(province + " "   + " " + area + " " + address, this, 1);
        if (addressRE == null) {
            showToast("没有找到该地址，请填写正确地址");
        return false;
        }
        latLng = new LatLng(addressRE.getLatitude(), addressRE.getLongitude());
        showWait(false);
//
        return true;
    }

    public void getData() {
        AddUserAddressRequest rq = new AddUserAddressRequest();
        rq.setName(name);
        rq.setTelephone(telephone);
        rq.setProvince(province);
        rq.setCity(city);
        rq.setArea(area);
        rq.setAddress(address);
        rq.setUser_type(AppParam.user_type);
        rq.setAddress_type("1");   //地址类型：1-国内，2-国际
        rq.setLatitude(latLng.latitude + "");
        rq.setLongitude(latLng.longitude + "");
        rq.setOper_type(oper_type);
        rq.setUser_address_id(user_address_id);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, InternationalAdsActivity.this, AddUserAddressResponse.class);
            if (kd != null) {
                AddUserAddressResponse info = (AddUserAddressResponse) kd;
                if (info.getStatus() == 1) {
                    showToast(info.getMessage());
                    Intent in = new Intent();
                    setResult(108, in);
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

    @OnClick(R.id.save_bt)
    public void onClick() {
        if (check()) {
//            analysisData();
            getData();

        }
    }

    //============================自动填充==============================
    public void autoSet() {
        mPlaceGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addApi(Places.GEO_DATA_API)
                .build();
        detailsEt.setOnItemClickListener(mAutocompleteClickListener);
        mAdapter = new PlaceAutocompleteAdapter(this, mPlaceGoogleApiClient, BOUNDS_GREATER_SYDNEY, null);
        detailsEt.setAdapter(mAdapter);
    }

    GoogleApiClient mPlaceGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;
    boolean fromTianchong = false;
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-47, 166), new LatLng(-34, 178));

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);
            detailsEt.setText(primaryText.toString());
            detailsEt.setSelection(primaryText.toString().length());//将光标移至文字末尾
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mPlaceGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            final Place place = places.get(0);
            latLng = place.getLatLng();
            String showad = place.getAddress().toString();
            String shiname = place.getName().toString();
//            bgetData("http://maps.google.com/maps/api/geocode/json?latlng=" + latLng.latitude + "," + latLng.longitude + "&sensor=false");
//            fromTianchong = true;
            places.release();
        }
    };

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    //===========================自动填充end=============================
    //===================================================================
//    public void bgetData(String url) {
//        showWait(true);
//        ApiHttpClient.get(url, null, bgmHandler);
//    }
//
//    private final AsyncHttpResponseHandler bgmHandler = new AsyncHttpResponseHandler() {
//        @Override
//        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//            try {
//                String ds = new String(arg2, "UTF-8");
//                dealdata(ds);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            showWait(false);
//        }
//
//        @Override
//        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//            AppContext.showToast("网络出错" + arg0);
//            showWait(false);
//        }
//    };
//
//    String premise = "", street_number = "", route = "";
//    String addressStr = "";
//    String street, zipcode, lngStr, latStr;
//    String zone;//区
//
//    public Object dealdata(String ds) {
//        route = street = street_number = premise = zone = province = "";
//        Object obj = null;
//        try {
////        String ds = new String(arg2, "UTF-8");
//            Log.e("error", ds);
//            JSONObject js = new JSONObject(ds);
//            String status = js.getString("status");
//            if (status.equals("OK")) {
//                JSONArray jlist = js.getJSONArray("results");
//                if (jlist != null && jlist.length() > 0) {
//                    JSONObject res = (JSONObject) jlist.get(0);
//                    //只要街道 號碼
//                    JSONArray jlistper = res.getJSONArray("address_components");
//                    for (int Ii = 0; Ii < jlistper.length(); Ii++) {
//                        JSONObject perdata = (JSONObject) jlistper.get(Ii);
//                        JSONArray types = perdata.getJSONArray("types");
//                        for (int a = 0; a < types.length(); a++) {
//                            String type = (String) types.get(a);
//                            if (type.equals("premise")) {  //大廈
//                                premise = (String) perdata.get("long_name");
//                            }
//                            if (type.equals("street_number")) {//門牌號
//                                street_number = (String) perdata.get("long_name");
//                            }
//                            if (type.equals("route")) {//街道
//                                route = (String) perdata.get("long_name");
//                                street = (String) perdata.get("long_name");
//                            }
//                            if (type.equals("postal_code")) { //邮政编码
//                                zipcode = (String) perdata.get("long_name");
//                            }
//                            if (type.equals("sublocality")) { //区
//                                zone = (String) perdata.get("long_name");
//                            }
//                            if (type.equals("locality")) { //市
//                                area = (String) perdata.get("long_name");
//                            }
//
//                            if (type.equals("administrative_area_level_1")) { //省
//                                city = (String) perdata.get("long_name");
//                            }
//                        }
//                    }
////                    address_et.setText(zone + route + "" + street_number + "" + premise);
//                    TLog.error("zone=" + zone + "route=" + route + "street_number=" + street_number + "premise=" + premise + "area=" + "," + area + "zipcode=" + "," + zipcode);
////                    areaEt.setText(zone);
////                    cityEt.setText(area);
////                    provinceEt.setText(city);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("error", "JSON解析出错");
//        }
//        return obj;
//    }
    //===============================end==================================

    //================================通过地址解析经纬度====================
//    public void analysisData() {
//        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + area + "," + city + "," + province;
//        showWait(true);
//        ApiHttpClient.get(url, null, analysismHandler);
//    }
//
//    private final AsyncHttpResponseHandler analysismHandler = new AsyncHttpResponseHandler() {
//        @Override
//        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//            try {
//                String ds = new String(arg2, "UTF-8");
//                analysisData(ds);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            showWait(false);
//        }
//
//        @Override
//        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//            AppContext.showToast("网络出错" + arg0);
//            showWait(false);
//        }
//    };
//
//    public void analysisData(String ds) {
//        try {
//            Log.e("error", ds);
//            JSONObject js = new JSONObject(ds);
//            String status = js.getString("status");
//            if (status.equals("OK")) {
//                JSONArray jlist = js.getJSONArray("results");
//                if (jlist != null && jlist.length() > 0) {
//                    JSONObject res = (JSONObject) jlist.get(0);
//                    JSONObject geometryRes = res.getJSONObject("geometry");
//                    JSONObject location = geometryRes.getJSONObject("location");
//                    latLng = new LatLng(location.getDouble("lat"), location.getDouble("lng"));
//                    TLog.error("latitude=" + latLng.latitude + "longitude=" + latLng.longitude);
//                    showWait(false);
//                    if (latLng == null) {
//                        showToast("没有找到该地址，请填写正确地址");
//                    } else {
//                        getData();
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("error", "JSON解析出错");
//        }
//    }
    //================================通过地址解析经纬度end=================
}
