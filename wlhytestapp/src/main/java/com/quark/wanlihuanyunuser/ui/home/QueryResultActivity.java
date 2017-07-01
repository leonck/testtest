package com.quark.wanlihuanyunuser.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.SearchUserTransportList;
import com.quark.api.auto.bean.SearchUserTransportListRequest;
import com.quark.api.auto.bean.SearchUserTransportListResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.ResutlAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.ui.personal.CourierDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;
import me.maxwin.view.XListView;

/**
 * Created by pan on 2016/11/8 0008.
 * >#
 * >#批量查询结果列表
 */
public class QueryResultActivity extends BaseActivity implements XListView.IXListViewListener {
    @InjectView(R.id.xlv)
    XListView xlstv;
    @InjectView(R.id.nodata)
    TextView nodata;

    ArrayList<SearchUserTransportList> datas;
    ResutlAdapter adapter;
    String orderNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queryresult);
        ButterKnife.inject(this);
        setTopTitle("批量查询结果列表");
        setBackButton();

        orderNumber = (String) getValue4Intent("order_number");
        myinitlist();
        getData();
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    public void myinitlist() {
        datas = new ArrayList();
        xlstv.setFooterDividersEnabled(false);
        // 设置xlistview可以加载、刷新
        xlstv.setPullLoadEnable(false);
        xlstv.setPullRefreshEnable(false);
        xlstv.setXListViewListener(this);
        xlstv.setOnItemClickListener(listListener);
        adapter = new ResutlAdapter(this, datas);
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
                Intent intent = new Intent(QueryResultActivity.this, CourierDetailsActivity.class);
                intent.putExtra("waybill_number", datas.get(position).getWaybill_number() + "");
                startActivity(intent);
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
                default:
                    break;
            }
        }
    };

    int pn = 1;
    int type = 1;

    @Override
    public void onRefresh() {
        pn = 1;
        type = 1;
    }

    @Override
    public void onLoadMore() {
        type = 2;
        pn++;
    }

    public void getData() {
        SearchUserTransportListRequest rq = new SearchUserTransportListRequest();
        rq.setUser_type(AppParam.user_type);
        rq.setKw(orderNumber);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, QueryResultActivity.this, SearchUserTransportListResponse.class);
            if (kd != null) {
                SearchUserTransportListResponse info = (SearchUserTransportListResponse) kd;
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

    public void dealData(SearchUserTransportListResponse info) {
        if (type == 1) {
            datas.clear();
        }
        List<SearchUserTransportList> tin = info.getSearchUserTransportListResult().getSearchUserTransportList();
        Message msg = handler.obtainMessage();
        msg.what = type;
        if (tin != null && tin.size() > 0) {
            datas.addAll(tin);
            msg.arg1 = tin.size();
            nodata.setVisibility(View.GONE);
            xlstv.setVisibility(View.VISIBLE);
        } else {
            msg.arg1 = 0;
            nodata.setVisibility(View.VISIBLE);
            xlstv.setVisibility(View.GONE);
        }
        handler.sendMessage(msg);
    }


}
