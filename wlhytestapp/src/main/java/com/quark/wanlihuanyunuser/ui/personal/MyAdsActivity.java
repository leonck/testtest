package com.quark.wanlihuanyunuser.ui.personal;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.ListUserAddress;
import com.quark.api.auto.bean.MyUserAddressListRequest;
import com.quark.api.auto.bean.MyUserAddressListResponse;
import com.quark.api.auto.bean.SetUserAddressRequest;
import com.quark.api.auto.bean.SetUserAddressResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.AdsAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import me.maxwin.view.XListView;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#我的地址  地址管理
 */
public class MyAdsActivity extends BaseActivity implements XListView.IXListViewListener {

    @InjectView(R.id.rightrig)
    ImageView rightrig;
    @InjectView(R.id.right)
    LinearLayout right;
    @InjectView(R.id.search_et)
    EditText searchEt;
    @InjectView(R.id.xlv)
    XListView xlv;

    ArrayList<ListUserAddress> datas;
    AdsAdapter adapter;
    String show_type = "0";
    int delete_position;
    String set_type;
    String user_address_id;
    String select;
    String kw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myads);
        ButterKnife.inject(this);
        setTopTitle("地址管理");
        setBackButton();

        select = (String) getValue4Intent("select");
        right.setVisibility(View.VISIBLE);
        rightrig.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.more_dot));
        getData();
        myinitlist();

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
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public void myinitlist() {
        datas = new ArrayList();
        xlv.setFooterDividersEnabled(false);
        // 设置xlistview可以加载、刷新
        xlv.setPullLoadEnable(true);
        xlv.setPullRefreshEnable(true);
        xlv.setXListViewListener(this);
        xlv.setOnItemClickListener(listListener);
        adapter = new AdsAdapter(this, datas, handler);
        xlv.setAdapter(adapter);
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
                if (!Utils.isEmpty(select)) {
                    if (select.equals("1")) {
                        Intent intent = new Intent();
                        intent.putExtra("id", datas.get(position).getUser_address_id() + "");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ads_item", datas.get(position));
                        intent.putExtras(bundle);
                        setResult(101, intent);
                        finish();
                    } else if (select.equals("2")) {
                        Intent intent = new Intent();
                        intent.putExtra("id", datas.get(position).getUser_address_id() + "");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ads_item", datas.get(position));
                        intent.putExtras(bundle);
                        setResult(102, intent);
                        finish();
                    } else if (select.equals("3")) {
                        Intent intent = new Intent();
                        intent.putExtra("id", datas.get(position).getUser_address_id() + "");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ads_item", datas.get(position));
                        intent.putExtras(bundle);
                        setResult(401, intent);
                        finish();
                    } else if (select.equals("4")) {
                        Intent intent = new Intent();
                        intent.putExtra("id", datas.get(position).getUser_address_id() + "");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ads_item", datas.get(position));
                        intent.putExtras(bundle);
                        setResult(402, intent);
                        finish();
                    }
                } else { //编辑
                    if (datas.get(position).getAddress_type() == 1) {
                        Intent in = new Intent(MyAdsActivity.this, InternationalAdsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("listUserAddress", datas.get(position));
                        bundle.putString("user_address_id", datas.get(position).getUser_address_id() + "");
                        bundle.putString("oper_type", "2");
                        in.putExtras(bundle);
                        startActivityForResult(in, 109);
                    } else {
                        Intent in = new Intent(MyAdsActivity.this, DomesticAdsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("listUserAddress", datas.get(position));
                        bundle.putString("user_address_id", datas.get(position).getUser_address_id() + "");
                        bundle.putString("oper_type", "2");
                        in.putExtras(bundle);
                        startActivityForResult(in, 109);
                    }
                }
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

    @OnClick({R.id.right, R.id.add_ads})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right:
                initPopupwindow(view);
                break;
            case R.id.add_ads:
                initDialog();
                break;
        }
    }

    //添加地址 选择
    private void initDialog() {
        final Dialog dlg = new Dialog(this, R.style.ActionSheet);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.actionsheet, null);
        final int cFullFillWidth = 10000;
        layout.setMinimumWidth(cFullFillWidth);
        TextView mContent = (TextView) layout.findViewById(R.id.content);
        TextView mCancel = (TextView) layout.findViewById(R.id.cancel);
        TextView mTitle = (TextView) layout.findViewById(R.id.title);

        mTitle.setText("添加新西兰收件地址");
        mContent.setText("添加中国收件地址");

        mTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//新西兰国内
                Intent in = new Intent(MyAdsActivity.this, InternationalAdsActivity.class);
                startActivityForResult(in, 108);
//                startActivityByClass(DomesticAdsActivity.class);
                dlg.dismiss();
            }
        });

        mContent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(MyAdsActivity.this, DomesticAdsActivity.class);
                startActivityForResult(in, 108);
//                startActivityByClass(InternationalAdsActivity.class);
                dlg.dismiss();
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
                    xlv.setDataSize(msg.arg1);
                    xlv.stopRefresh();
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    xlv.setDataSize(msg.arg1);
                    xlv.stopLoadMore();
                    adapter.notifyDataSetChanged();
                    break;
                case 300:
                    set_type = "1";
                    user_address_id = msg.arg2 + "";
                    setOrdeleteAdsRequest();
                    break;
                case 301:
                    set_type = "2";
                    delete_position = msg.arg1;
                    user_address_id = msg.arg2 + "";
                    setOrdeleteAdsRequest();
                    break;
                default:
                    break;
            }
        }
    };

    public void initPopupwindow(View v) {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View popview = inflater.inflate(R.layout.popupwindow, null);
        final PopupWindow popupWindow;
        LinearLayout per = (LinearLayout) popview.findViewById(R.id.per);
        per.getBackground().setAlpha(180);
        popupWindow = new PopupWindow(popview, Utils.dip2px(this, 110), Utils.dip2px(this, 120));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });

        per.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        //
        TextView all = (TextView) popview.findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_type = "0";
                getData();
                popupWindow.dismiss();

            }
        });

        //
        TextView yichang = (TextView) popview.findViewById(R.id.yichang);
        yichang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_type = "1";
                getData();
                popupWindow.dismiss();

            }
        });

        //
        TextView email = (TextView) popview.findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_type = "2";
                getData();
                popupWindow.dismiss();

            }
        });
        popupWindow.showAsDropDown(v);
    }

    public void getData() {
        MyUserAddressListRequest rq = new MyUserAddressListRequest();
        rq.setUser_type(AppParam.user_type);//用户类型：1-注册，2-商家
        rq.setShow_type(show_type);//0-显示全部，1-显示国内，2-显示国际
        rq.setPn(pn);
        rq.setKw(kw);
        rq.setPage_size(10);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerads);
    }

    private final AsyncHttpResponseHandler mHandlerads = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, MyAdsActivity.this, MyUserAddressListResponse.class);
            if (kd != null) {
                MyUserAddressListResponse info = (MyUserAddressListResponse) kd;
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
    };

    public void dealData(MyUserAddressListResponse info) {
        if (type == 1) {
            datas.clear();
        }
        List<ListUserAddress> tin = info.getMyUserAddressListResult().getMyUserAddressList().getList();
        Message msg = handler.obtainMessage();
        msg.what = type;
        if (tin != null && tin.size() > 0) {
            datas.addAll(tin);
            msg.arg1 = tin.size();
        } else {
            msg.arg1 = 0;
        }
        handler.sendMessage(msg);
    }

    /**
     * 设置默认地址或者删除地址
     *
     * @author pan
     * @time 2016/11/25 0025 下午 2:51
     */
    public void setOrdeleteAdsRequest() {
        SetUserAddressRequest rq = new SetUserAddressRequest();
        rq.setUser_type(AppParam.user_type);//用户类型：1-注册，2-商家
        rq.setSet_type(set_type);//1-设置默认地址，2-删除地址
        rq.setUser_address_id(user_address_id);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, MyAdsActivity.this, SetUserAddressResponse.class);
            if (kd != null) {
                SetUserAddressResponse info = (SetUserAddressResponse) kd;
                if (info.getStatus() == 1) {

                    if (set_type.equals("1")) {
                        getData();
                    } else if (set_type.equals("2")) {
                        datas.remove(delete_position);
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
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 0) {
            if (resultCode == 108) {
                getData();
            }else if(resultCode == 109){
                onRefresh();
            }
        }
    }

}
