package com.quark.wanlihuanyunuser.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.DeleteIdcardRecordRequest;
import com.quark.api.auto.bean.DeleteIdcardRecordResponse;
import com.quark.api.auto.bean.IdcardRecordList;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.HistoryAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import me.maxwin.view.XListView;

/**
 * Created by pan on 2016/11/10 0010.
 * >#
 * >#历史记录
 */
public class HistoryRecordActivity extends BaseActivity implements XListView.IXListViewListener {

    ArrayList<IdcardRecordList> datas;
    HistoryAdapter adapter;
    int delete_position;
    String idcard_record_id;
    List<IdcardRecordList> idhistory;

    @InjectView(R.id.remove_tv)
    TextView removeTv;
    @InjectView(R.id.xlstv)
    XListView xlstv;
    @InjectView(R.id.noData)
    TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyrecord);
        ButterKnife.inject(this);
        setTopTitle("历史记录");
        setBackButton();
        idhistory = (List<IdcardRecordList>)getValue4Intent("idhistory");
        myinitlist();
        dealData();
    }

    public void myinitlist() {
        datas = new ArrayList();
        xlstv.setFooterDividersEnabled(false);
        // 设置xlistview可以加载、刷新
        xlstv.setPullLoadEnable(false);
        xlstv.setPullRefreshEnable(false);
        xlstv.setXListViewListener(this);
        xlstv.setOnItemClickListener(listListener);
        adapter = new HistoryAdapter(this, datas, handler);
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
                Intent intent = new Intent();
                intent.putExtra("id_number", datas.get(position).getIdcard_number());
                intent.putExtra("front_card", datas.get(position).getFront_card());
                intent.putExtra("back_card", datas.get(position).getBack_card());
                Bundle bundle = new Bundle();
                bundle.putSerializable("idhistory", (Serializable) idhistory);
                intent.putExtras(bundle);
                setResult(88, intent);
                finish();
            }
        }
    };

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onLoadMore() {
    }

    @OnClick(R.id.remove_tv)
    public void onClick() {
        for (int i = 0; i < datas.size(); i++) {
            idcard_record_id = datas.get(i).getIdcard_record_id() + "";
            deleteData();
        }
    }

    /**
     * 获取身份证历史记录
     *
     * @author pan
     * @time 2016/11/29 0029 下午 2:12
     */
//    public void getData() {
//        IdcardRecordListRequest rq = new IdcardRecordListRequest();
//        rq.setUser_type(AppParam.user_type);
//        showWait(true);
//        QuarkApi.HttpRequest(rq, mHandlerget);
//    }
//
//    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
//        @Override
//        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//            Object kd = ApiResponse.get(arg2, HistoryRecordActivity.this, IdcardRecordListResponse.class);
//            if (kd != null) {
//                IdcardRecordListResponse info = (IdcardRecordListResponse) kd;
//                if (info.getStatus() == 1) {
//                    dealData(info);
//                }
//            }
//            showWait(false);
//        }
//
//        @Override
//        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//            AppContext.showToast("网络出错" + arg0);
//            showWait(false);
//        }
//    };

    public void dealData() {
        if (idhistory != null && idhistory.size() > 0) {
            datas.addAll(idhistory);
        } else {
            removeTv.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 删除记录
     *
     * @author pan
     * @time 2016/11/29 0029 下午 2:20
     */
    public void deleteData() {
        DeleteIdcardRecordRequest rq = new DeleteIdcardRecordRequest();
        rq.setIdcard_record_id(idcard_record_id);
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, HistoryRecordActivity.this, DeleteIdcardRecordResponse.class);
            if (kd != null) {
                DeleteIdcardRecordResponse info = (DeleteIdcardRecordResponse) kd;
                if (info.getStatus() == 1) {
                    datas.remove(delete_position);
                    adapter.notifyDataSetChanged();
                    showToast(info.getMessage());
                } else {
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
    };

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 119:
                    delete_position = msg.arg1;
                    idcard_record_id = msg.arg2 + "";
                    deleteData();
                    break;
                default:
                    break;
            }
        }
    };
}
