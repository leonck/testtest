package com.quark.wanlihuanyunuser.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.DeleteOrderPackageRequest;
import com.quark.api.auto.bean.DeleteOrderPackageResponse;
import com.quark.api.auto.bean.ListSendOrdersPackage;
import com.quark.api.auto.bean.MySendOrdersListRequest;
import com.quark.api.auto.bean.MySendOrdersListResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.TakeAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseFragment;
import com.quark.wanlihuanyunuser.ui.personal.CourierDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;
import me.maxwin.view.XListView;

/**
 * Created by pan on 2016/11/7 0007.
 * >#
 * >#已送达
 */
public class SaapunutFragment extends BaseFragment implements XListView.IXListViewListener {

    View oneView;
    ArrayList<ListSendOrdersPackage> datas;
    TakeAdapter adapter;
    @InjectView(R.id.xlv)
    XListView xlv;
    @InjectView(R.id.noData)
    TextView noData;


    int currentPosition;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneView = inflater.inflate(R.layout.fragment_take, container, false);
        ButterKnife.inject(this, oneView);


        myinitlist();
        getData();
        return oneView;
    }

    public void myinitlist() {
        datas = new ArrayList();
        xlv.setFooterDividersEnabled(false);
        // 设置xlistview可以加载、刷新
        xlv.setPullLoadEnable(true);
        xlv.setPullRefreshEnable(true);
        xlv.setXListViewListener(this);
        adapter = new TakeAdapter(getActivity(), datas,handler,3);
        xlv.setAdapter(adapter);
        Message msg = handler.obtainMessage();
        msg.what = 1;
        msg.arg1 = datas.size();
        handler.sendMessage(msg);
        xlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position = position - 1;
                if (position < datas.size()) {
                    Intent intent = new Intent(getActivity(), CourierDetailsActivity.class);
                    intent.putExtra("waybill_number", datas.get(position).getWaybill_number() + "");
                    startActivity(intent);
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
                case 201:
                    currentPosition = msg.arg1;
                    deleteData();
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
        MySendOrdersListRequest rq = new MySendOrdersListRequest();
        rq.setUser_type(AppParam.user_type);//1-注册用户，2-商家
        rq.setOrder_status("3");//1-待取件，2-运送中，3-已送达，4-已失效
        rq.setPn(pn);
        rq.setPage_size(10);
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
            xlv.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        } else {
            if (type == 1) {
                xlv.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
            }
            msg.arg1 = 0;
        }
        handler.sendMessage(msg);
    }

    public void deleteData() {
        DeleteOrderPackageRequest rq = new DeleteOrderPackageRequest();
        rq.setSend_orders_package_id(datas.get(currentPosition).getSend_orders_package_id() + "");
        rq.setUser_type(AppParam.user_type);
        showWait(true);
        QuarkApi.HttpRequest(rq, deletemHandler);
    }

    private final AsyncHttpResponseHandler deletemHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), DeleteOrderPackageResponse.class);
            if (kd != null) {
                DeleteOrderPackageResponse info = (DeleteOrderPackageResponse) kd;
                showToast(info.getMessage());
                if (info.getStatus()==1){
                    datas.remove(currentPosition);
                    adapter.notifyDataSetChanged();
                    if (datas.size()==0){
                        xlv.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                    }
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
