package com.quark.wanlihuanyunuser.ui.personal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.ListChargeLog;
import com.quark.api.auto.bean.UserChargeLogListRequest;
import com.quark.api.auto.bean.UserChargeLogListResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.TransactionAdapter;
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
 * >#交易记录
 */
public class TransactionActivity extends BaseActivity implements XListView.IXListViewListener {

    @InjectView(R.id.xlv)
    XListView xlstv;

    ArrayList<ListChargeLog> datas;
    TransactionAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        ButterKnife.inject(this);
        setTopTitle("交易记录");
        setBackButton();

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
        adapter = new TransactionAdapter(this, datas);
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
            if (position < 5) {

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

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    int pn = 1;
    int type = 1;

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

    public void getData() {
        UserChargeLogListRequest rq = new UserChargeLogListRequest();
        rq.setUser_type(AppParam.user_type);
        rq.setPn(pn);
        rq.setPage_size(10);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, TransactionActivity.this, UserChargeLogListResponse.class);
            if (kd != null) {
                UserChargeLogListResponse info = (UserChargeLogListResponse) kd;
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

        @Override
        public void onFinish() {
            super.onFinish();
            showWait(false);
        }
    };

    public void dealData(UserChargeLogListResponse info) {
        if (type == 1) {
            datas.clear();
        }
        List<ListChargeLog> tin = info.getUserChargeLogListResult().getChargeLogList().getList();
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
}
