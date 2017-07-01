package com.quark.wanlihuanyunuser.ui.home;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.LogisticsList;
import com.quark.api.auto.bean.ServerHotlineRequest;
import com.quark.api.auto.bean.ServerHotlineResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.PhoneAdapter;
import com.quark.wanlihuanyunuser.adapter.ServiceAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;
import me.maxwin.view.XListView;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by pan on 2016/11/8 0008.
 * >#
 * >#服务热线
 */
public class ServiceActivity extends BaseActivity implements XListView.IXListViewListener, EasyPermissions.PermissionCallbacks {

    @InjectView(R.id.xlv)
    XListView xlstv;

    ArrayList<LogisticsList> datas;
    ServiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.inject(this);
        setTopTitle("服务热线");
        setBackButton();

        myinitlist();
        getData();
    }

    public void myinitlist() {
        datas = new ArrayList();
        xlstv.setFooterDividersEnabled(false);
        // 设置xlistview可以加载、刷新
        xlstv.setPullLoadEnable(false);
        xlstv.setPullRefreshEnable(false);
        xlstv.setXListViewListener(this);
        xlstv.setOnItemClickListener(listListener);
//        xlstv.setDivider(null);
        adapter = new ServiceAdapter(this, datas);
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

                if (!Utils.isEmpty(datas.get(position).getService_telephone())) {
                    initDialog(datas.get(position).getService_telephone());
                }
            }
        }
    };

    private void initDialog(String phone) {
        final Dialog dlg = new Dialog(this, R.style.ActionSheet);
        LayoutInflater inflater = (LayoutInflater) ServiceActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.actionsheetlist, null);
        final int cFullFillWidth = 10000;
        layout.setMinimumWidth(cFullFillWidth);
//        TextView mContent = (TextView) layout.findViewById(R.id.content);
        TextView mCancel = (TextView) layout.findViewById(R.id.cancel);
        ListView listView = (ListView) layout.findViewById(R.id.list);

        String[] phoneStr = phone.split("#");
        final List<String> phl = new ArrayList<>();
        for (int i = 0; i < phoneStr.length; i++) {
            phl.add(phoneStr[i]);
        }
        PhoneAdapter adapter = new PhoneAdapter(ServiceActivity.this, phl, null);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                phonecur = phl.get(position);
                startByPermissions();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(false);
        dlg.setContentView(layout);
        dlg.show();
    }


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    xlstv.setDataSize(msg.arg1);
                    xlstv.stopRefresh();
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    xlstv.setDataSize(msg.arg1);
                    xlstv.stopLoadMore();
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


    int pn = 1;
    int type = 1;

    @Override
    public void onRefresh() {
        pn = 1;
        type = 1;
    }

    @Override
    public void onLoadMore() {
        type = 2;
        pn++;
    }

    public void getData() {
        ServerHotlineRequest rq = new ServerHotlineRequest();
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, ServiceActivity.this, ServerHotlineResponse.class);
            if (kd != null) {
                ServerHotlineResponse info = (ServerHotlineResponse) kd;
                if (info.getStatus() == 1) {

                    if (info.getLogisticsList() != null && info.getLogisticsList().size() > 0) {
                        List<LogisticsList> tin = info.getLogisticsList();
                        datas.addAll(tin);
                        adapter.notifyDataSetChanged();
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

        @Override
        public void onFinish() {
            super.onFinish();
            showWait(false);
        }
    };

    String phonecur;

    private void startByPermissions() {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phonecur));
            startActivity(intent);
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permissions_request), 1, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        try {
            Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phonecur));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, R.string.permissions_map_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, R.string.permissions_map_error, Toast.LENGTH_LONG).show();
    }
}
