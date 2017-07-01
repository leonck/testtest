package com.quark.wanlihuanyunuser.ui.personal.daifa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.DeleteDaifaOrderRequest;
import com.quark.api.auto.bean.DeleteDaifaOrderResponse;
import com.quark.api.auto.bean.ListDaifaOrdersCompany;
import com.quark.api.auto.bean.UserdaifaOrdersListRequest;
import com.quark.api.auto.bean.UserdaifaOrdersListResponse;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.ui.personal.ShopSendDtsActivity;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.quark.wanlihuanyunuser.base.BaseApplication.showToast;

class ShopSendExpressPresenter implements ShopSendExpressContract.Presenter {
    private Activity context;
    private ShopSendExpressContract.View view;
    private ArrayList<ListDaifaOrdersCompany> datas = new ArrayList<>();
    private boolean refresh;
    private int pn = 0;
    String keystr;

    ShopSendExpressPresenter(Activity context, ShopSendExpressContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        getData("", true);
    }

    @Override
    public void getData(String kw, boolean refresh) {
        keystr = kw;
        this.refresh = refresh;
        if (!refresh) {
            pn++;
        } else {
            pn = 1;
        }
        UserdaifaOrdersListRequest rq = new UserdaifaOrdersListRequest();
        rq.setUser_type(AppParam.user_type);//1-注册用户，2-商家
        rq.setPn(pn);
        rq.setKw(kw);
        rq.setPage_size(10);
        QuarkApi.HttpRequest(rq, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                Object kd = ApiResponse.get(arg2, context, UserdaifaOrdersListResponse.class);
                if (kd != null) {
                    UserdaifaOrdersListResponse info = (UserdaifaOrdersListResponse) kd;
                    if (info.getStatus() == 1) {
                        dealData(info);
                    }
                }
            }
            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                showToast("网络出错" + arg0);
            }
        });
    }

    @Override
    public void toDetail(int position) {
        Intent intent = new Intent().setClass(context, ShopSendDtsActivity.class);
        int daifa_orders_company_id = datas.get(position).getDaifa_orders_company_id();
        Bundle bundle = new Bundle();
        bundle.putString("daifa_orders_company_id", daifa_orders_company_id + "");
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void dealData(UserdaifaOrdersListResponse info) {
        if (refresh) {
            datas.clear();
        }
        List<ListDaifaOrdersCompany> tin = info.getUserdaifaOrdersListResult().getUserdaifaOrdersList().getList();
        if (tin != null && tin.size() > 0) {
            datas.addAll(tin);
        }
        view.showResult(datas);
        if (refresh) {
            view.stopRefresh(tin == null ? 0 : tin.size());
        } else {
            view.stopLoard(tin == null ? 0 : tin.size());
        }
    }

    public void deleteData(final int currentPosition) {
        DeleteDaifaOrderRequest rq = new DeleteDaifaOrderRequest();
        rq.setUser_type(AppParam.user_type);
        rq.setDaifa_orders_company_id(datas.get(currentPosition).getDaifa_orders_company_id() + "");
        view.showLoarding(true);
        QuarkApi.HttpRequest(rq, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                Object kd = ApiResponse.get(arg2, context, DeleteDaifaOrderResponse.class);
                if (kd != null) {
                    DeleteDaifaOrderResponse info = (DeleteDaifaOrderResponse) kd;
                    if (info.getStatus() == 1) {
                        datas.remove(currentPosition);
                        view.notifyListView();
                    } else {
                        showToast(info.getMessage());
                    }
                }
                view.showLoarding(false);
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                showToast("网络出错" + arg0);
                view.showLoarding(false);
            }
        });
    }

    public void toSearch() {
        Intent intent = new Intent();
        intent.setClass(context, SearchOrderActivity.class);
        intent.putExtra("key", keystr);
        context.startActivityForResult(intent, 102);
    }
}
