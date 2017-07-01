package com.quark.wanlihuanyunuser.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.DeleteDaifaOrderRequest;
import com.quark.api.auto.bean.DeleteDaifaOrderResponse;
import com.quark.api.auto.bean.ListDaifaOrdersCompany;
import com.quark.api.auto.bean.UserdaifaOrdersListRequest;
import com.quark.api.auto.bean.UserdaifaOrdersListResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.DaiCourierAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;
import me.maxwin.view.XListView;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#商家代发快递
 */
public class ShopSendCourierActivity extends BaseActivity implements XListView.IXListViewListener {

    @InjectView(R.id.rightrig)
    ImageView rightrig;
    @InjectView(R.id.right)
    LinearLayout right;
    @InjectView(R.id.xlv)
    XListView xlstv;

    DaiCourierAdapter adapter;
    ArrayList<ListDaifaOrdersCompany> datas;
    int pn = 1;
    String kw;
    String daifa_orders_company_id;
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopsendcourier);
        ButterKnife.inject(this);
        setTopTitle("代发快递");
        setBackButton();
        right.setVisibility(View.VISIBLE);
        rightrig.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.search));

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopSendCourierActivity.this, SearchUserNameActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        kw = (String) getValue4Intent("kw");
        myinitlist();
        getData();
    }

    public void myinitlist() {
        datas = new ArrayList();
        xlstv.setFooterDividersEnabled(false);
        // 设置xlistview可以加载、刷新
        xlstv.setPullLoadEnable(true);
        xlstv.setPullRefreshEnable(true);
        xlstv.setXListViewListener(this);
        xlstv.setOnItemClickListener(listListener);
        adapter = new DaiCourierAdapter(this, datas, handler);
        xlstv.setAdapter(adapter);
        Message msg = handler.obtainMessage();
        msg.what = 1;
        msg.arg1 = datas.size();
        handler.sendMessage(msg);
    }

    AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            position = position - 1;
            if (position < datas.size()) {
//                showToast("点击了");
            }
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    xlstv.setDataSize(msg.arg1);
                    xlstv.stopRefresh();
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    xlstv.setDataSize(msg.arg1);
                    xlstv.stopLoadMore();
                    adapter.notifyDataSetChanged();
                    break;
                case 200:
                    daifa_orders_company_id = msg.arg2 + "";
                    Bundle bundle = new Bundle();
                    bundle.putString("daifa_orders_company_id", daifa_orders_company_id);
                    startActivityByClass(ShopSendDtsActivity.class, bundle);
                    break;
                case 201:
                    currentPosition = msg.arg1;
                    deleteData();
                default:
                    break;
            }
        }
    };

    public void getData() {
        UserdaifaOrdersListRequest rq = new UserdaifaOrdersListRequest();
        rq.setUser_type(AppParam.user_type);//1-注册用户，2-商家
        rq.setPn(pn);
        rq.setKw(kw);
        rq.setPage_size(10);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, ShopSendCourierActivity.this, UserdaifaOrdersListResponse.class);
            if (kd != null) {
                UserdaifaOrdersListResponse info = (UserdaifaOrdersListResponse) kd;
                if (info.getStatus() == 1) {
                    dealData(info);
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

    public void dealData(UserdaifaOrdersListResponse info) {
        if (type == 1) {
            datas.clear();
        }
        List<ListDaifaOrdersCompany> tin = info.getUserdaifaOrdersListResult().getUserdaifaOrdersList().getList();
        Message msg = handler.obtainMessage();
        msg.what = type;
        if (tin != null && tin.size() > 0) {
            datas.addAll(tin);
            msg.arg1 = tin.size();
        } else {
            msg.arg1 = 0;
        }
        handler.sendMessage(msg);
    }

    int type = 1;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onRefresh() {
        pn = 1;
        type = 1;
        getData();
    }

    @Override
    public void onLoadMore() {
        type = 2;
        pn++;
        getData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 0) {
            if (resultCode == 101) {
                kw = data.getStringExtra("kw");
                getData();
            }
        }
    }

    public void deleteData() {
        DeleteDaifaOrderRequest rq = new DeleteDaifaOrderRequest();
        rq.setUser_type(new AppParam().user_type);
        rq.setDaifa_orders_company_id(datas.get(currentPosition).getDaifa_orders_company_id() + "");
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerdelete);
    }

    private final AsyncHttpResponseHandler mHandlerdelete = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, ShopSendCourierActivity.this, DeleteDaifaOrderResponse.class);
            if (kd != null) {
                DeleteDaifaOrderResponse info = (DeleteDaifaOrderResponse) kd;
                if (info.getStatus()==1){
                    datas.remove(currentPosition);
                    adapter.notifyDataSetChanged();
                }else{
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
