package com.quark.wanlihuanyunuser.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.ListSendOrdersPackage;
import com.quark.api.auto.bean.NewOrdersPackageRequest;
import com.quark.api.auto.bean.NewOrdersPackageResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.TakeRelyAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseFragment;
import com.quark.wanlihuanyunuser.ui.personal.CourierDetailsActivity;
import com.quark.wanlihuanyunuser.util.TLog;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/11/7 0007.
 * >#
 * >#最新快递
 */
public class OneFg extends BaseFragment {
    View oneView;
    ArrayList<ListSendOrdersPackage> datas;
    TakeRelyAdapter adapter;

    @InjectView(R.id.xlstv)
    RecyclerView xlstv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneView = inflater.inflate(R.layout.fragment_onefg, container, false);
        ButterKnife.inject(this, oneView);

        int jianju = Utils.px2dip(getActivity(), 36);
        TLog.error("间距是多少" + jianju);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        xlstv.setLayoutManager(layoutManager);

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

        getData();
        return oneView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * 最新快递请求
     *
     * @author pan
     * @time 2016/11/26 0026 上午 10:47
     */
    public void getData() {
        NewOrdersPackageRequest rq = new NewOrdersPackageRequest();
        rq.setUser_type(AppParam.user_type);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), NewOrdersPackageResponse.class);
            if (kd != null) {
                NewOrdersPackageResponse info = (NewOrdersPackageResponse) kd;
                if (info.getStatus() == 1) {
                    if (info.getIs_send() == 1) {//上门取件
                        ListSendOrdersPackage listSendOrdersPackage = new ListSendOrdersPackage();
                        listSendOrdersPackage.setSend_area(info.getSendOrdersPackage().getSend_area());
                        listSendOrdersPackage.setSend_name(info.getSendOrdersPackage().getSend_name());
                        listSendOrdersPackage.setCollect_area(info.getSendOrdersPackage().getCollect_area());
                        listSendOrdersPackage.setCollect_name(info.getSendOrdersPackage().getCollect_name());
                        listSendOrdersPackage.setsOPinfo(info.getSendOrdersPackage().getsOPinfo());
                        if (Utils.isEmpty(info.getSendOrdersPackage().getWaybill_number())) {
                            listSendOrdersPackage.setWaybill_number("等待分配订单号");
                        } else {
                            listSendOrdersPackage.setWaybill_number(info.getSendOrdersPackage().getWaybill_number());
                        }

                        listSendOrdersPackage.setRemarker(info.getSendOrdersPackage().getRemarker());
                        listSendOrdersPackage.setPost_time(info.getSendOrdersPackage().getPost_time());

                        datas.add(listSendOrdersPackage);
                        if (datas.size() == 0) {
                            ListSendOrdersPackage listSendOrdersPackagenull = new ListSendOrdersPackage();
                            datas.add(listSendOrdersPackagenull);
                        }
                    } else {
                        ListSendOrdersPackage listSendOrdersPackage = new ListSendOrdersPackage();
                        listSendOrdersPackage.setSend_area(info.getDaifaOrdersPackage().getDaifaOrdersCompany().getSend_area());
                        listSendOrdersPackage.setSend_name(info.getDaifaOrdersPackage().getDaifaOrdersCompany().getSend_name());
                        listSendOrdersPackage.setCollect_area(info.getDaifaOrdersPackage().getDaifaOrdersCompany().getCollect_area());
                        listSendOrdersPackage.setCollect_name(info.getDaifaOrdersPackage().getDaifaOrdersCompany().getCollect_name());
                        listSendOrdersPackage.setPost_time(info.getDaifaOrdersPackage().getPost_time());
                        if (Utils.isEmpty(info.getDaifaOrdersPackage().getWaybill_number())) {
                            listSendOrdersPackage.setWaybill_number("等待分配订单号");
                        } else {
                            listSendOrdersPackage.setWaybill_number(info.getDaifaOrdersPackage().getWaybill_number());
                        }
                        datas.add(listSendOrdersPackage);
                        if (datas.size() == 0) {
                            ListSendOrdersPackage listSendOrdersPackagenull = new ListSendOrdersPackage();
                            datas.add(listSendOrdersPackagenull);
                        }
                    }
                }
                if (datas.size() == 0) {
                    ListSendOrdersPackage listSendOrdersPackagenull = new ListSendOrdersPackage();
                    datas.add(listSendOrdersPackagenull);
                }
                adapter.notifyDataSetChanged();
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
