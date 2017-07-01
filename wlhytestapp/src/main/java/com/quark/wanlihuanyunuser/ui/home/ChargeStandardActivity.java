package com.quark.wanlihuanyunuser.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.LogisticsList;
import com.quark.api.auto.bean.ServerHotlineRequest;
import com.quark.api.auto.bean.ServerHotlineResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.NameAdapter;
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
 * Created by pan on 2016/11/8 0008.
 * >#
 * >#收费标准
 */
public class ChargeStandardActivity extends BaseActivity implements XListView.IXListViewListener {

    @InjectView(R.id.xlv)
    XListView xlstv;

    ArrayList<LogisticsList> datas;
    NameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargestandard);
        ButterKnife.inject(this);
        setTopTitle("收费标准");
        setBackButton();

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
        adapter = new NameAdapter(this, datas);
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
                Bundle bundle = new Bundle();
                bundle.putString("id", datas.get(position).getLogistics_id() + "");
                startActivityByClass(ChargeStandardWebActivity.class, bundle);
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
        ServerHotlineRequest rq = new ServerHotlineRequest();
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, ChargeStandardActivity.this, ServerHotlineResponse.class);
            if (kd != null) {
                ServerHotlineResponse info = (ServerHotlineResponse) kd;
                if (info.getStatus() == 1) {

                    if (info.getLogisticsList() != null && info.getLogisticsList().size() > 0) {
                        List<LogisticsList> tin = info.getLogisticsList();
                        datas.addAll(tin);
                    }

                }else {
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

}
