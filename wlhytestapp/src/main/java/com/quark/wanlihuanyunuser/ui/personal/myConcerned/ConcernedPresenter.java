package com.quark.wanlihuanyunuser.ui.personal.myConcerned;

import android.app.Activity;
import android.content.Intent;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.DeleteFollowLogisticsRequest;
import com.quark.api.auto.bean.DeleteFollowLogisticsResponse;
import com.quark.api.auto.bean.ListSendOrdersPackage;
import com.quark.api.auto.bean.MyFollowLogisticsListRequest;
import com.quark.api.auto.bean.MyFollowLogisticsListResponse;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.ui.personal.CourierDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.quark.wanlihuanyunuser.base.BaseApplication.showToast;

class ConcernedPresenter implements ConcernedContract.Presenter {
    private Activity context;
    private ConcernedContract.View view;
    private ArrayList<ListSendOrdersPackage> datas = new ArrayList<>();
    private boolean refresh;
    private int pn = 0;

    ConcernedPresenter(Activity context, ConcernedContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        getData(true);
    }

    @Override
    public void getData(boolean refresh) {
        this.refresh = refresh;
        if (!refresh) {
            pn++;
        } else {
            pn = 1;
        }

        MyFollowLogisticsListRequest rq = new MyFollowLogisticsListRequest();
        rq.setUser_type(AppParam.user_type);//1-注册用户，2-商家
        rq.setPn(pn);
        rq.setPage_size(10);
        QuarkApi.HttpRequest(rq, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                Object kd = ApiResponse.get(arg2, context, MyFollowLogisticsListResponse.class);
                if (kd != null) {
                    MyFollowLogisticsListResponse info = (MyFollowLogisticsListResponse) kd;
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
        if (position < datas.size()) {
            Intent intent = new Intent(context, CourierDetailsActivity.class);
            intent.putExtra("waybill_number",datas.get(position).getWaybill_number());
            context.startActivity(intent);
        }
    }

    public void dealData(MyFollowLogisticsListResponse info) {
        if (refresh) {
            datas.clear();
        }
        List<ListSendOrdersPackage> tin = info.getMyFollowLogisticsListResult().getMyFollowLogisticsList().getList();
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
        DeleteFollowLogisticsRequest rq = new DeleteFollowLogisticsRequest();
        rq.setUser_type(AppParam.user_type);
        rq.setFollow_logistics_id(datas.get(currentPosition).getFollow_logistics_id() + "");
        view.showLoarding(true);
        QuarkApi.HttpRequest(rq, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                Object kd = ApiResponse.get(arg2, context, DeleteFollowLogisticsResponse.class);
                if (kd != null) {
                    DeleteFollowLogisticsResponse info = (DeleteFollowLogisticsResponse) kd;
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

}
