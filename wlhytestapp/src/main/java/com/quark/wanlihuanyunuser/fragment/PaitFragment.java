package com.quark.wanlihuanyunuser.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.ListPaySendOrdersList;
import com.quark.api.auto.bean.MyPayOrdersListRequest;
import com.quark.api.auto.bean.MyPayOrdersListResponse;
import com.quark.api.auto.bean.SetPayOrdersRequest;
import com.quark.api.auto.bean.SetPayOrdersResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.PaitOrderAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseFragment;
import com.quark.wanlihuanyunuser.ui.personal.MyorderActivity;
import com.quark.wanlihuanyunuser.util.TLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;
import me.maxwin.view.XListView;

/**
 * Created by pan on 2016/11/7 0007.
 * >#
 * >#支付完成
 */
public class PaitFragment extends BaseFragment implements XListView.IXListViewListener {
    View oneView;
    List<ListPaySendOrdersList> datas;
    PaitOrderAdapter adapter;
    private String order_id;//用户寄件id为send_orders_id 商家代发id为daifa_orders_company_id
    private String set_type;//1-取消订单，2-删除订单
    private String order_type;//1-用户寄件，2-商家代发
    int delete_position;

    @InjectView(R.id.xlv)
    XListView xlv;
    @InjectView(R.id.nodata)
    TextView nodata;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneView = inflater.inflate(R.layout.fragment_notpay, container, false);
        ButterKnife.inject(this, oneView);
        myinitlist();
        getData();
        registerBoradcastReceiver();

        return oneView;
    }

    public void myinitlist() {
        datas = new ArrayList();
        xlv.setFooterDividersEnabled(false);
        // 设置xlistview可以加载、刷新
        xlv.setPullLoadEnable(false);
        xlv.setPullRefreshEnable(true);
        xlv.setXListViewListener(this);

        xlv.setDivider(null);
        adapter = new PaitOrderAdapter(getActivity(), datas, handler);
        xlv.setAdapter(adapter);
        xlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position = position - 1;
                if (position < datas.size()) {
//                    Intent intent = new Intent(getActivity(), CourierDetailsActivity.class);
//                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
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
                case 20:
                    set_type = "2";
                    order_id = msg.obj + "";
                    delete_position = msg.arg1;
                    order_type = datas.get(delete_position).getOrders_type();
                    setPayRequest();
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
        getData();
    }

    @Override
    public void onLoadMore() {
        type = 2;
        pn++;
        getData();
    }

    /**
     * @author pan
     * @time 2016/11/25 0025 上午 9:18
     */
    public void getData() {
        MyPayOrdersListRequest rq = new MyPayOrdersListRequest();
        rq.setUser_type(AppParam.user_type);//1-注册用户，2-商家
        rq.setPay_status("2");     //1-待支付，2-支付完，3-失效订单
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerget);
        TLog.error("已完成订单请求");
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), MyPayOrdersListResponse.class);
            if (kd != null) {
                MyPayOrdersListResponse info = (MyPayOrdersListResponse) kd;
                if (info.getStatus() == 1) {
                    if(MyorderActivity.instance!=null){
                        MyorderActivity.instance.setPaidNumber(info.getMyPayOrdersListResult().getPaySendOrdersList().size());
                    }
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

    public void dealData(MyPayOrdersListResponse info) {
        if (type == 1) {
            datas.clear();
        }
        List<ListPaySendOrdersList> tdatas = info.getMyPayOrdersListResult().getPaySendOrdersList();
        Message msg = handler.obtainMessage();
        msg.what = type;
        if (tdatas != null && tdatas.size() > 0) {
            datas.addAll(tdatas);
            msg.arg1 = tdatas.size();
            nodata.setVisibility(View.GONE);
            xlv.setVisibility(View.VISIBLE);
        } else {
            if (type == 1) {
                nodata.setVisibility(View.VISIBLE);
                xlv.setVisibility(View.GONE);
            }
            msg.arg1 = 0;
        }
        handler.sendMessage(msg);
    }

    /**
     * 取消订单
     *
     * @author pan
     * @time 2016/12/1 0001 下午 3:25
     */
    public void setPayRequest() {
        SetPayOrdersRequest rq = new SetPayOrdersRequest();
        rq.setOrder_id(order_id);
        rq.setSet_type(set_type);
        rq.setOrder_type(order_type);
        rq.setUser_type(AppParam.user_type);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), SetPayOrdersResponse.class);
            if (kd != null) {
                SetPayOrdersResponse info = (SetPayOrdersResponse) kd;
                if (info.getStatus() == 1) {
                    showToast(info.getMessage());
                    datas.remove(delete_position);
                    adapter.notifyDataSetChanged();

                    Intent intent = new Intent("OverdueOrderFragment");
                    intent.putExtra("option", "refresh");
                    getActivity().sendBroadcast(intent);

                    if (datas.size()==0){
                        xlv.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    }
                }else {
                    showToast(info.getMessage());
                }
                if(MyorderActivity.instance!=null){
                    MyorderActivity.instance.setPaidNumber(datas.size());
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

    // 注册广播
    private ReceiveBroadCast receiveBroadCast;
    public void registerBoradcastReceiver() {
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("PaitFragment"); // 只有持有相同的action的接受者才能接收此广播
        getActivity().registerReceiver(receiveBroadCast, filter);
    }

    public class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent data) {
            String position = data.getStringExtra("option");
            if (position.equals("refresh")) {
                onRefresh();
            }
        }
    }

    @Override
    public void onDestroy() {
        try {
            if (receiveBroadCast!=null)
                getActivity().unregisterReceiver(receiveBroadCast);
        }catch (Exception e){
            Log.e("error", "MainActivity 销毁出错");
        }
        super.onDestroy();
    }
}
