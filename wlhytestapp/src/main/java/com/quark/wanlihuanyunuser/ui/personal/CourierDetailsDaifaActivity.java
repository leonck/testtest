package com.quark.wanlihuanyunuser.ui.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.DaifaOrdersInfoRequest;
import com.quark.api.auto.bean.DaifaOrdersInfoResponse;
import com.quark.api.auto.bean.SendOrdersPackageInformation;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.LogisticsInfoAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.ui.widget.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#快递详情
 */
public class CourierDetailsDaifaActivity extends BaseActivity {
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

    LogisticsInfoAdapter adapter;
    String id;
    private String waybill_number;   //运单号/快递单号/物流单号
    List<SendOrdersPackageInformation> datas;

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
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    public void getData() {
        DaifaOrdersInfoRequest rq = new DaifaOrdersInfoRequest();
        rq.setUser_type(AppParam.user_type);
        rq.setWaybill_number(waybill_number);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, CourierDetailsDaifaActivity.this, DaifaOrdersInfoResponse.class);
            if (kd != null) {
                DaifaOrdersInfoResponse info = (DaifaOrdersInfoResponse) kd;
                if (info.getStatus() == 1) {
                    personname.setText("收件人：" + info.getsOrdersPackage().getCollect_name() + "  " + info.getsOrdersPackage().getCollect_telephone());
                    if (info.getDaifaOrdersPackageInformation() != null) {
                        List<SendOrdersPackageInformation> ds = info.getDaifaOrdersPackageInformation();
                        datas.addAll(ds);
                        adapter.notifyDataSetChanged();
                    } else {
                        nodata.setVisibility(View.VISIBLE);
                        wuliuview.setVisibility(View.GONE);
                    }
                } else {
                    wuliuview.setVisibility(View.GONE);
                    nodata.setVisibility(View.VISIBLE);
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

