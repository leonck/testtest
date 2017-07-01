package com.quark.wanlihuanyunuser.ui.personal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.ListMyInformation;
import com.quark.api.auto.bean.MyInformationListRequest;
import com.quark.api.auto.bean.MyInformationResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.MsgAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;
import me.maxwin.view.XListView;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#我的消息
 */
public class MyMsgActivity extends BaseActivity implements XListView.IXListViewListener{

    @InjectView(R.id.xlv)
    XListView xlv;

    MsgAdapter adapter;
    ArrayList<ListMyInformation> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymsg);
        ButterKnife.inject(this);
        setTopTitle("消息列表");
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
        xlv.setFooterDividersEnabled(false);
        // 设置xlistview可以加载、刷新
        xlv.setPullLoadEnable(true);
        xlv.setPullRefreshEnable(true);
        xlv.setXListViewListener(this);
        xlv.setOnItemClickListener(listListener);
        adapter = new MsgAdapter(this, datas);
        xlv.setAdapter(adapter);
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
                if (!Utils.isEmpty(datas.get(position).getWaybill_number())){
                    Bundle bundle  = new Bundle();
                    bundle.putString("waybill_number",datas.get(position).getWaybill_number());
                    startActivityByClass(CourierDetailsActivity.class,bundle);
                }
            }
        }
    };

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

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    xlv.setDataSize(msg.arg1);
                    xlv.stopRefresh();
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    xlv.setDataSize(msg.arg1);
                    xlv.stopLoadMore();
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    public void getData() {
        MyInformationListRequest rq = new MyInformationListRequest();
        rq.setUser_type(AppParam.user_type);//用户类型：1-注册用户，2-商家
        rq.setPn(1);
        rq.setPage_size(10);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, MyMsgActivity.this, MyInformationResponse.class);
            if (kd != null) {
                MyInformationResponse info = (MyInformationResponse) kd;
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

    public void dealData(MyInformationResponse info) {
        if (type == 1) {
            datas.clear();
        }
        List<ListMyInformation> tin = info.getMyInformationListResult().getMyInformationList().getList();
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
