package com.quark.wanlihuanyunuser.ui.personal;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mob.tools.utils.UIHandler;
import com.quark.api.auto.bean.AddFollowLogisticsRequest;
import com.quark.api.auto.bean.AddFollowLogisticsResponse;
import com.quark.api.auto.bean.PackageInfoRequest;
import com.quark.api.auto.bean.SendOrdersInfoResponse;
import com.quark.api.auto.bean.SendOrdersPackageInformation;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.LogisticsInfoAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.shearUtil.ShearUi;
import com.quark.wanlihuanyunuser.ui.widget.ListViewForScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cz.msebera.android.httpclient.Header;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#快递详情
 */
public class CourierDetailsActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks,
        PlatformActionListener, Handler.Callback {
    @InjectView(R.id.right)
    LinearLayout right;
    @InjectView(R.id.lv)
    ListViewForScrollView lv;
    @InjectView(R.id.nodata)
    TextView nodata;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.personname)
    TextView personname;
    @InjectView(R.id.wuliuview)
    ScrollView wuliuview;
    @InjectView(R.id.rightrig)
    ImageView rightrig;
    @InjectView(R.id.sign)
    TextView sign;

    LogisticsInfoAdapter adapter;
    String id;
    private String waybill_number;   //运单号/快递单号/物流单号
    List<SendOrdersPackageInformation> datas;
    SendOrdersInfoResponse info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courierdetails);
        ButterKnife.inject(this);
        setTopTitle("物流信息");
        setBackButton();
        right.setVisibility(View.VISIBLE);
        waybill_number = (String) getValue4Intent("waybill_number");
        datas = new ArrayList<>();

        adapter = new LogisticsInfoAdapter(this, datas);
        lv.setAdapter(adapter);
        name.setText(waybill_number);
        getData();
        startByPermissions();
    }

    public void showRight() {
        right.setVisibility(View.VISIBLE);
        rightrig.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.share));
        sign.setVisibility(View.GONE);
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    public void getData() {
//        SendOrdersInfoRequest rq = new SendOrdersInfoRequest();
//        rq.setUser_type(AppParam.user_type);
//        rq.setWaybill_number(waybill_number);
//        showWait(true);
//        QuarkApi.HttpRequest(rq, mHandler);

        PackageInfoRequest rq = new PackageInfoRequest();
        rq.setWaybill_number(waybill_number);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            try {
                Object kd = ApiResponse.get(arg2, CourierDetailsActivity.this, SendOrdersInfoResponse.class);
                if (kd != null) {
                    info = (SendOrdersInfoResponse) kd;
                    if (info.getStatus() == 1) {
                        showRight();
                        personname.setText("收件人：" + info.getsOrdersPackage().getCollect_name() + "  " + info.getsOrdersPackage().getCollect_telephone());
                        if ("2".equals(info.getSend_type())) {
                            if (info.getDaifaOrdersPackageInformation() != null) {
                                List<SendOrdersPackageInformation> ds = info.getDaifaOrdersPackageInformation();
                                datas.addAll(ds);
                                adapter.notifyDataSetChanged();
                                nodata.setVisibility(View.GONE);
                                wuliuview.setVisibility(View.VISIBLE);
                            } else {
                                nodata.setVisibility(View.VISIBLE);
                                wuliuview.setVisibility(View.GONE);
                            }
                        } else {
                            if (info.getSendOrdersPackageInformation() != null) {
                                List<SendOrdersPackageInformation> ds = info.getSendOrdersPackageInformation();
                                datas.addAll(ds);
                                adapter.notifyDataSetChanged();
                                nodata.setVisibility(View.GONE);
                                wuliuview.setVisibility(View.VISIBLE);
                            } else {
                                nodata.setVisibility(View.VISIBLE);
                                wuliuview.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        wuliuview.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                        showToast(info.getMessage());
                    }
                }
            } catch (Exception e) {
                wuliuview.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
            showWait(false);
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
            showWait(false);
        }
    };

    private static final int CAMERA_PERM = 1;

    private void startByPermissions() {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, perms)) {

        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, "请求拨打电话权限", CAMERA_PERM, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "无法获得拨打电话权限", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @OnClick(R.id.right)
    public void rh(View v) {
        ShearUi.showShear(this, shearhandler);
    }

    private Handler shearhandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ShearUi.wechat(CourierDetailsActivity.this, CourierDetailsActivity.this);
                    break;
                case 2:
                    ShearUi.wechatMoments(CourierDetailsActivity.this, CourierDetailsActivity.this);
                    break;
                case 3:
                    addgetData();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

        ;
    };

    public void addgetData() {
        AddFollowLogisticsRequest rq = new AddFollowLogisticsRequest();
        rq.setUser_type(AppParam.user_type);
        rq.setSend_type(info.getSend_type());
        rq.setOrders_package_id(info.getsOrdersPackage().getSend_orders_package_id());
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandleradd);
    }

    private final AsyncHttpResponseHandler mHandleradd = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, CourierDetailsActivity.this, AddFollowLogisticsResponse.class);
            if (kd != null) {
                AddFollowLogisticsResponse info = (AddFollowLogisticsResponse) kd;
                showToast(info.getMessage());
            }
            showWait(false);
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
            showWait(false);
        }
    };

    private static final int MSG_ACTION_CCALLBACK = 2;

    @Override
    public void onCancel(Platform platform, int action) {
        //取消监听,handle the cancel msg
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> arg2) {
        //成功监听,handle the successful msg
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onError(Platform platform, int action, Throwable t) {
        //打印错误信息,print the error msg
        t.printStackTrace();
        //错误监听,handle the error msg
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = t;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_ACTION_CCALLBACK: {
                switch (msg.arg1) {
                    case 1:
                        showToast("分享成功");
                        break;
                    case 2:
                        Throwable t = (Throwable) msg.obj;
                        showToast("分享失败");
//                        showToast("分享失败"+t.getMessage().toString());
//                        showToast("分享失败"+t.toString());
                        break;
                    case 3:
                        showToast("分享取消");
                        break;
                }
            }
            break;
        }
        return false;
    }
}
