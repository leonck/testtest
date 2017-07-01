package com.quark.wanlihuanyunuser.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.ListSendOrdersPackage;
import com.quark.api.auto.bean.MySendOrdersListRequest;
import com.quark.api.auto.bean.MySendOrdersListResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.TakeRelyAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseFragment;
import com.quark.wanlihuanyunuser.ui.personal.CourierDetailsActivity;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/11/7 0007.
 * >#
 * >#快递记录
 */
public class TwoFg extends BaseFragment {
    @InjectView(R.id.xlstv)
    RecyclerView xlstv;
    @InjectView(R.id.nodata)
    TextView nodata;

    View oneView;
    ArrayList<ListSendOrdersPackage> datas;
    TakeRelyAdapter adapter;

    int pn = 1;
    int type = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneView = inflater.inflate(R.layout.fragment_twofg, container, false);
        ButterKnife.inject(this, oneView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        xlstv.setLayoutManager(layoutManager);

        myinitlist();
        getData();
        return oneView;
    }

    public void myinitlist() {
        datas = new ArrayList<>();
        adapter = new TakeRelyAdapter(getActivity(), datas);
        xlstv.setAdapter(adapter);
        adapter.setOnItemClickLitener(new TakeRelyAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!Utils.isEmpty(datas.get(position).getWaybill_number())){
                    Intent intent = new Intent(getActivity(), CourierDetailsActivity.class);
                    intent.putExtra("waybill_number", datas.get(position).getWaybill_number());
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * @author pan
     * @time 2016/11/25 0025 上午 9:18
     */
    public void getData() {
        MySendOrdersListRequest rq = new MySendOrdersListRequest();
        rq.setUser_type(AppParam.user_type);//1-注册用户，2-商家
        rq.setOrder_status("3");//1-待取件，2-运送中，3-已送达，4-已失效
        rq.setPn(pn);
        rq.setPage_size(1000);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), MySendOrdersListResponse.class);
            if (kd != null) {
                MySendOrdersListResponse info = (MySendOrdersListResponse) kd;
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

    public void dealData(MySendOrdersListResponse info) {
        if (type == 1) {
            datas.clear();
        }
        List<ListSendOrdersPackage> tin = info.getMySendOrdersListResult().getMySendOrdersList().getList();
        Message msg = handler.obtainMessage();
        msg.what = type;
        if (tin != null && tin.size() > 0) {
            datas.addAll(tin);
            msg.arg1 = tin.size();
        } else {
            msg.arg1 = 0;
            if (type == 1){
                ListSendOrdersPackage aPackage = new ListSendOrdersPackage();
                datas.add(aPackage);
            }
        }
        handler.sendMessage(msg);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };
}
