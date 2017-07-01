package com.quark.wanlihuanyunuser.ui.personal;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.AddUserAddressRequest;
import com.quark.api.auto.bean.AddUserAddressResponse;
import com.quark.api.auto.bean.ListUserAddress;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.googlemap.LocationUtils;
import com.quark.wanlihuanyunuser.ui.widget.WheelChooseDialog;
import com.quark.wanlihuanyunuser.util.TLog;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#中国国际地址
 */
public class DomesticAdsActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @InjectView(R.id.user_et)
    EditText userEt;
    @InjectView(R.id.phone_et)
    EditText phoneEt;
    @InjectView(R.id.ads_et)
    EditText adsEt;
    @InjectView(R.id.details_et)
    EditText detailsEt;
    @InjectView(R.id.save_bt)
    Button saveBt;

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
        setContentView(R.layout.activity_domesticads);
        ButterKnife.inject(this);
        setTopTitle("填写地址");
        setBackButton();

        oper_type = (String)getValue4Intent("oper_type");
        user_address_id = (String)getValue4Intent("user_address_id");
        eads = (ListUserAddress)getValue4Intent("listUserAddress");
        if ("2".equals(oper_type)){
            initEditView();
        }
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
    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.contact_ly, R.id.ads_et, R.id.save_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.contact_ly:
                startByPermissions();
                break;
            case R.id.ads_et:
                WheelChooseDialog ds = new WheelChooseDialog();
                ds.showSheetPic(this, handler,"China");

                break;
            case R.id.save_bt:
                if (check()) {
                    getData();
                }
                break;
        }
    }

    private boolean check() {
        name = userEt.getText().toString();
        telephone = phoneEt.getText().toString();
        address = detailsEt.getText().toString();

        if (Utils.isEmpty(name)) {
            showToast("请输入姓名");
            return false;
        }
        if (Utils.isEmpty(telephone)) {
            showToast("请输入手机号码");
            return false;
        }
        if (Utils.isEmpty(adsEt.getText().toString())) {
            showToast("请选择地址");
            return false;
        }
        if (Utils.isEmpty(address)) {
            showToast("请输入详细地址");
            return false;
        }
        showWait(true);
        latLng = null;
        latLng = LocationUtils.getLatlng(province + city + area + address, this);
        showWait(false);
        if (latLng == null) {
            showToast("没有找到该地址，请填写正确地址");
            return false;
        }
        return true;
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
                    adsEt.setText(province + " " + city + " " + area);

                    TLog.error("sheng" + province + "shi" + city + "qu" + area);
                    break;

                default:
                    break;
            }
        }
    };

    public void getData() {
        AddUserAddressRequest rq = new AddUserAddressRequest();
        rq.setName(name);
        rq.setTelephone(telephone);
        rq.setProvince(province);
        rq.setCity(city);
        rq.setArea(area);
        rq.setAddress(address);
        rq.setUser_type(AppParam.user_type);
        rq.setAddress_type("2");  //地址类型：1-国内，2-国际
        rq.setLatitude(latLng.latitude + "");
        rq.setLongitude(latLng.longitude + "");
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, DomesticAdsActivity.this, AddUserAddressResponse.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ContentResolver reContentResolverol = getContentResolver();
            Uri contactData = data.getData();
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null,
                    null);
            while (phone.moveToNext()) {
                String usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phoneEt.setText(usernumber);
            }
            userEt.setText(username);
        }
    }


    private static final int CAMERA_PERM = 1;

    @AfterPermissionGranted(CAMERA_PERM)
    private void startByPermissions() {
        String[] perms = {Manifest.permission.READ_CONTACTS,Manifest.permission.GET_ACCOUNTS};
        if (EasyPermissions.hasPermissions(this, perms)) {
            try {
                Intent intent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 0);
            } catch (Exception e) {
                Toast.makeText(this, "用户拒绝获取联系人信息权限", Toast.LENGTH_LONG).show();
            }
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, "申请获取联系人权限",  CAMERA_PERM, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "用户拒绝获取联系人信息权限", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "用户拒绝获取联系人信息权限", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
