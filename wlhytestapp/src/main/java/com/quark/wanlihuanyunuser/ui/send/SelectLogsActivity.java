package com.quark.wanlihuanyunuser.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/11/9 0009.
 * >#
 * >#选择物流公司
 */
public class SelectLogsActivity extends BaseActivity {
    @InjectView(R.id.search_et)
    EditText searchEt;
    @InjectView(R.id.lv)
    ListView lv;
    @InjectView(R.id.nodata)
    TextView nodata;

    ArrayList<LogisticsList> datas;
    NameAdapter adapter;
    String kw;
    String sendCity, sendZone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectlogs);
        ButterKnife.inject(this);

        sendCity = (String) getValue4Intent("sendCity");
        sendZone = (String) getValue4Intent("sendZone");

        searchEt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    kw = searchEt.getText().toString();
                    getData();
                    return true;
                }
                return false;
            }
        });
        getData();
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    @OnClick(R.id.cancel)
    public void onClick() {
        finish();
    }

    public void getData() {
        ServerHotlineRequest rq = new ServerHotlineRequest();
        rq.setArea(sendZone);
        rq.setCity(sendCity);
        rq.setKw(kw);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, SelectLogsActivity.this, ServerHotlineResponse.class);
            if (kd != null) {
                ServerHotlineResponse info = (ServerHotlineResponse) kd;
                if (info.getStatus() == 1) {
                    datas = new ArrayList<>();
                    if (info.getLogisticsList() != null && info.getLogisticsList().size() > 0) {
                        nodata.setVisibility(View.GONE);
                        lv.setVisibility(View.VISIBLE);
                        List<LogisticsList> tin = info.getLogisticsList();
                        datas.addAll(tin);
                        adapter = new NameAdapter(SelectLogsActivity.this, datas);
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(clickListener);
                    } else {
                        nodata.setVisibility(View.VISIBLE);
                        lv.setVisibility(View.GONE);
                    }
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

        @Override
        public void onFinish() {
            super.onFinish();
            showWait(false);
        }
    };

    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.putExtra("wuliu_id", datas.get(position).getLogistics_id() + "");
            intent.putExtra("wuliu_name", datas.get(position).getName());
            setResult(99, intent);
            finish();

        }
    };
}
