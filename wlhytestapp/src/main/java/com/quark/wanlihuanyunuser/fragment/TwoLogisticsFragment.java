package com.quark.wanlihuanyunuser.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.AddSendOrdersRequest;
import com.quark.api.auto.bean.AddSendOrdersResponse;
import com.quark.api.auto.bean.DeleteSendOrdersPackageRequest;
import com.quark.api.auto.bean.DeleteSendOrdersPackageResponse;
import com.quark.api.auto.bean.FirstSendOrdersPremissRequest;
import com.quark.api.auto.bean.FirstSendOrdersPremissResponse;
import com.quark.api.auto.bean.LogisticsAdList;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.LogisticsAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseFragment;
import com.quark.wanlihuanyunuser.ui.send.ChinaParcelInfoActivity;
import com.quark.wanlihuanyunuser.ui.widget.ListViewForScrollView;
import com.quark.wanlihuanyunuser.util.TLog;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/11/7 0007.
 * >#
 * >国际物流到中国物流
 */
public class TwoLogisticsFragment extends BaseFragment{
    @InjectView(R.id.lv)
    ListViewForScrollView lv;
    @InjectView(R.id.selected_tv)
    TextView selectedTv;
    @InjectView(R.id.number)
    TextView numberTv;

    View oneView;
    ArrayList<LogisticsAdList> datas;
    LogisticsAdapter adapter;
    String send_orders_id;
    double money = 0;//运费
    String send_orders_package_id;
    int delete_position;
    int type = 1;
    private boolean is_select = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneView = inflater.inflate(R.layout.fragment_logistics, container, false);
        ButterKnife.inject(this, oneView);
        datas = new ArrayList<>();
        initListView();

        return oneView;
    }
    public void initListView() {
        datas = new ArrayList<>();
        adapter = new LogisticsAdapter(getActivity(), datas, handler);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(cl);
    }

    AdapterView.OnItemClickListener cl = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle bundle = new Bundle();
            bundle.putString("position", position + "");
            bundle.putString("send_orders_package_id", datas.get(position).getSend_orders_package_id());
            bundle.putString("send_orders_id", send_orders_id);
            Intent intent = new Intent(getActivity(), ChinaParcelInfoActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, 91);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 110:
                    delete_position = msg.arg1;
                    deleteRequest();
                    break;
                default:
                    break;
            }
        }
    };

    @OnClick({R.id.add_ly, R.id.sumbit_tv, R.id.selected_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_ly:  //to添加包裹
                if (Utils.isEmpty(send_orders_id)) {
                    getData();
                } else {
                    toAddGoods();
                }
                break;
            case R.id.sumbit_tv:
                if (is_select) {
                    tiJiaoRequest();
                } else {
                    showToast("同意快递条款才能提交");
                }
                break;
            case R.id.selected_tv:
                if (is_select) {
                    is_select = false;
                    Drawable drawable = this.getResources().getDrawable(R.drawable.agree_o);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    selectedTv.setCompoundDrawables(drawable, null, null, null);
                } else {
                    is_select = true;
                    Drawable drawable = this.getResources().getDrawable(R.drawable.agree);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    selectedTv.setCompoundDrawables(drawable, null, null, null);
                }
                break;
        }
    }

    public void toAddGoods() {
        Intent intent = new Intent(getActivity(), ChinaParcelInfoActivity.class);
        intent.putExtra("send_orders_id", send_orders_id);
        if (datas!=null&&datas.size()>0){
            intent.putExtra("logistics_name", datas.get(0).getWuliu());
            intent.putExtra("logistics_id", datas.get(0).getLogistics_id());
        }
        startActivityForResult(intent, 91);
    }

    /**
     * 上门取件第一次调用
     *
     * @author pan
     * @time 2016/11/25 0025 下午 4:42
     */
    public void getData() {
        FirstSendOrdersPremissRequest rq = new FirstSendOrdersPremissRequest();
        rq.setUser_type(AppParam.user_type);//用户类型：1-注册用户，2-商家
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerdata);
    }

    private final AsyncHttpResponseHandler mHandlerdata = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), FirstSendOrdersPremissResponse.class);
            if (kd != null) {
                FirstSendOrdersPremissResponse info = (FirstSendOrdersPremissResponse) kd;
                if (info.getStatus() == 1) {
                    send_orders_id = info.getSend_orders_id();
//                    addRequest();
                    toAddGoods();
                }else {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 0) {
            if (resultCode == 90) {
                LogisticsAdList list = (LogisticsAdList) data.getSerializableExtra("datas");
                datas.add(list);
                adapter.notifyDataSetChanged();
                reckonMoney();
            } else if (resultCode == 91) { //编辑
                LogisticsAdList list = (LogisticsAdList) data.getSerializableExtra("datas");
                int position = data.getIntExtra("position", 0);
                datas.remove(position);
                datas.add(position, list);
                adapter.notifyDataSetChanged();
                reckonMoney();
            }
        }
    }

    /**
     * 点击提交
     *
     * @author pan
     * @time 2016/11/28 0028 下午 5:50
     */
    public void tiJiaoRequest() {
        AddSendOrdersRequest rq = new AddSendOrdersRequest();
        rq.setSend_orders_id(send_orders_id);
        rq.setUser_type(AppParam.user_type);//用户类型：1-注册用户，2-商家
        rq.setType("2");//1-新加波物流，2-国际中国物流
        rq.setMoney(money+"");//运费
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
        TLog.error("提交快递id" + send_orders_id);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), AddSendOrdersResponse.class);
            if (kd != null) {
                AddSendOrdersResponse info = (AddSendOrdersResponse) kd;
                if (info.getStatus() == 1) {
                    showToast(info.getMessage());
                    getActivity().finish();
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

    /**
     * 删除包裹请求
     *
     * @author pan
     * @time 2016/11/29 0029 上午 9:27
     */
    public void deleteRequest() {
        DeleteSendOrdersPackageRequest rq = new DeleteSendOrdersPackageRequest();
        rq.setSend_orders_package_id(send_orders_package_id);
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerdelete);
    }

    private final AsyncHttpResponseHandler mHandlerdelete = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), DeleteSendOrdersPackageResponse.class);
            if (kd != null) {
                DeleteSendOrdersPackageResponse info = (DeleteSendOrdersPackageResponse) kd;
                if (info.getStatus() == 1) {
                    datas.remove(delete_position);
                    adapter.notifyDataSetChanged();
                    reckonMoney();
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

    //计算运费
    public void reckonMoney() {
        money = 0;
        for (int i = 0; i < datas.size(); i++) {
            money += datas.get(i).getTotalMoney();
        }
        numberTv.setText(Utils.DoubleFormat(money));
    }

    //是否已经添加了包裹 true 是
    public boolean hasDatas(){
        if (datas.size()>0){
            return true;
        }else{
            return false;
        }
    }

    public void cleanData(){
        datas.clear();
        adapter.notifyDataSetChanged();
    }
}
