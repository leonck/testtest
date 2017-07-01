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
import com.quark.api.auto.bean.GoodsList;
import com.quark.api.auto.bean.LogisticsSendGoodsList;
import com.quark.api.auto.bean.LogisticsSendGoodsListRequest;
import com.quark.api.auto.bean.LogisticsSendGoodsListResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.ItemsTypeAdapter;
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
 * >#物品名称
 */
public class ItemsNameActivity extends BaseActivity {
    @InjectView(R.id.search_et)
    EditText searchEt;
    @InjectView(R.id.lv)
    ListView lv;
    @InjectView(R.id.nodata)
    TextView nodata;

    ArrayList<LogisticsSendGoodsList> datas;
    ItemsTypeAdapter adapter;
    String logistics_id;
    String kw;

    ArrayList<GoodsList> choosedatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemsname);
        ButterKnife.inject(this);

        choosedatas = (ArrayList<GoodsList>)getValue4Intent("choosedatas");

        logistics_id = (String) getValue4Intent("logistics_id");
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
        LogisticsSendGoodsListRequest rq = new LogisticsSendGoodsListRequest();
        rq.setLogistics_id(logistics_id);
        rq.setKw(kw);
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, ItemsNameActivity.this, LogisticsSendGoodsListResponse.class);
            if (kd != null) {
                LogisticsSendGoodsListResponse info = (LogisticsSendGoodsListResponse) kd;
                if (info.getStatus() == 1) {
                    datas = new ArrayList<>();
                    if (info.getLogisticsSendGoodsList().size() > 0 && info.getLogisticsSendGoodsList() != null) {
                        nodata.setVisibility(View.GONE);
                        lv.setVisibility(View.VISIBLE);

                        List<LogisticsSendGoodsList> logLists = info.getLogisticsSendGoodsList();
                        datas.addAll(logLists);
                        adapter = new ItemsTypeAdapter(ItemsNameActivity.this, datas);
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(listener);
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
    };

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String chooseId = datas.get(position).getLogistics_send_goods_id()+"";
            if (choosedatas!=null){
                for (int i=0,size = choosedatas.size();i<size;i++){
                    if (chooseId.equals(choosedatas.get(i).getLogistics_send_goods_id()+"")){
                        showToast("该物品已经在您货物列表中");
                        return;
                    }
                }
            }
            Intent intent = new Intent();
            intent.putExtra("logistics_send_goods_id", datas.get(position).getLogistics_send_goods_id() + "");
            intent.putExtra("goods_name", datas.get(position).getName());
            intent.putExtra("goods_number", datas.get(position).getLimit_amount());
            setResult(98, intent);
            finish();
        }
    };
}
