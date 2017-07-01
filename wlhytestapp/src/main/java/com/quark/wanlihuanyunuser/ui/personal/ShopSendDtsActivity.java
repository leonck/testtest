package com.quark.wanlihuanyunuser.ui.personal;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.DaifaOrdersDetialRequest;
import com.quark.api.auto.bean.DaifaOrdersDetialResponse;
import com.quark.api.auto.bean.DaifaOrdersGoodsList;
import com.quark.api.auto.bean.DaifaOrdersPackageList;
import com.quark.api.auto.bean.DaifaOrderss;
import com.quark.api.auto.bean.ListDaifaOrdersPackageGoodsList;
import com.quark.api.auto.bean.LogisticsAdList;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.ChaiItemsInfoAdapter;
import com.quark.wanlihuanyunuser.adapter.parcelInfoAddAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.ui.widget.ListViewForScrollView;
import com.quark.wanlihuanyunuser.util.TLog;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

import static com.quark.wanlihuanyunuser.R.id.is_show;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#代发详情 订单详情
 */
public class ShopSendDtsActivity extends BaseActivity {

    @InjectView(R.id.left_img)
    ImageView leftImg;
    @InjectView(R.id.left)
    LinearLayout left;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.sign)
    TextView sign;
    @InjectView(R.id.rightrig)
    ImageView rightrig;
    @InjectView(R.id.right)
    LinearLayout right;
    @InjectView(R.id.head)
    RelativeLayout head;
    @InjectView(R.id.pay_state)
    ImageView payState;
    @InjectView(R.id.pay_state_top)
    TextView payStateTop;
    @InjectView(R.id.order_code)
    TextView orderCode;
    @InjectView(R.id.order_date)
    TextView orderDate;
    @InjectView(R.id.lv)
    ListViewForScrollView lv;
    @InjectView(R.id.result_tv)
    TextView resultTv;
    @InjectView(R.id.collect_name)
    TextView collectName;
    @InjectView(R.id.collect_ads)
    TextView collectAds;
    @InjectView(R.id.details_tv)
    TextView detailsTv;
    @InjectView(R.id.send_name)
    TextView sendName;
    @InjectView(R.id.send_ads)
    TextView sendAds;
    @InjectView(R.id.ji_details_tv)
    TextView jiDetailsTv;
    @InjectView(R.id.logisticstype)
    TextView logisticstype;
    @InjectView(R.id.tips)
    TextView tips;
    @InjectView(R.id.add_info)
    ImageView addInfo;
    @InjectView(R.id.two_lv)
    ListViewForScrollView twoLv;

    ArrayList<DaifaOrdersGoodsList> datas;
    ArrayList<LogisticsAdList> packageDatas;
    ChaiItemsInfoAdapter chiaItemsInfoAdapter;
    parcelInfoAddAdapter parcelInfoAdapter;
    String order_type;
    private String daifa_orders_company_id;//商家代发订单ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopsenddts);
        ButterKnife.inject(this);
        setTopTitle("订单详情");
        setBackButton();

        daifa_orders_company_id = (String) getValue4Intent("daifa_orders_company_id");

        datas = new ArrayList<>();
        packageDatas = new ArrayList<>();
        addInfo.setVisibility(View.GONE);
        parcelInfoAdapter = new parcelInfoAddAdapter(this, packageDatas, handler, "2");
        twoLv.setAdapter(parcelInfoAdapter);
        getData();
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    public void getData() {
        DaifaOrdersDetialRequest rq = new DaifaOrdersDetialRequest();
        rq.setDaifa_orders_company_id(daifa_orders_company_id);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, ShopSendDtsActivity.this, DaifaOrdersDetialResponse.class);
            if (kd != null) {
                DaifaOrdersDetialResponse info = (DaifaOrdersDetialResponse) kd;
                if (info.getStatus() == 1) {
                    dealData(info);
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

    public void dealData(DaifaOrdersDetialResponse info) {
        DaifaOrderss daifaOrderss = info.getDaifaOrdersDetialResult().getDaifaOrderss();
        orderCode.setText("订单编号：" + daifaOrderss.getOrders_number());
        orderDate.setText(daifaOrderss.getPost_time());
        collectName.setText(daifaOrderss.getCollect_name() + " " + daifaOrderss.getCollect_telephone());
        collectAds.setText(daifaOrderss.getCollect_address());
        sendName.setText(daifaOrderss.getSend_name() + " " + daifaOrderss.getSend_telephone());
        sendAds.setText(daifaOrderss.getSend_address());

        //获取两个地址栏的高度 设置两个地址栏等高
        ViewTreeObserver vto2 = sendAddressView.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(sendListener);
        ViewTreeObserver vto = collectView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(cListener);

        List<DaifaOrdersGoodsList> goodsLists = daifaOrderss.getDaifaOrdersGoodsList();
        if (goodsLists != null && goodsLists.size() > 0) {
            datas.addAll(goodsLists);
            chiaItemsInfoAdapter = new ChaiItemsInfoAdapter(ShopSendDtsActivity.this, datas, false);
            lv.setAdapter(chiaItemsInfoAdapter);

            double companyMoney = 0;
            int totalNumber = 0;
            for (int i = 0; i < datas.size(); i++) {
                int buyNumber = datas.get(i).getBuy_number();
                Double goods_money = Double.valueOf(datas.get(i).getCompany_price());
                companyMoney += goods_money * buyNumber;
                totalNumber += buyNumber;
            }
            resultTv.setText("共" + totalNumber + "件," + Utils.DoubleFormat(companyMoney) + "元");
        }

        List<DaifaOrdersPackageList> packageLists = daifaOrderss.getDaifaOrdersPackageList();
        if (packageLists != null && packageLists.size() > 0) {
            ArrayList<LogisticsAdList> pgd = new ArrayList<>();
            int pakageSize = packageLists.size();
            for (int i = 0, size = packageLists.size(); i < size; i++) {
                DaifaOrdersPackageList lper = packageLists.get(i);

                LogisticsAdList lg = new LogisticsAdList();
                lg.setWuliu(lper.getLogistics_name());
                lg.setWaybill_number(lper.getWaybill_number());
                lg.setWeight(lper.getDeclared_weight());
                List<ListDaifaOrdersPackageGoodsList> goods = lper.getDaifaOrdersPackageGoodsList();
                String showName = "";
                for (int j = 0, jsize = goods.size(); j < jsize; j++) {
                    showName += goods.get(j).getGoods_name() + "x" + goods.get(j).getGoods_number() + "、";
                }
                if (showName.endsWith("、")) {
                    showName = showName.substring(0, showName.length() - 1);
                }
                lg.setGoodsNameShow(showName);

                pgd.add(lg);
            }
            packageDatas.addAll(pgd);
            parcelInfoAdapter.notifyDataSetChanged();
            tips.setText(" - 共" + pakageSize + "个包裹");
            tips.setTextSize(14);
        }

        order_type = daifaOrderss.getType() + "";
        if ("1".equals(order_type)) {
            logisticstype.setText("物流：国内物流");
        } else {
            logisticstype.setText("物流：国际物流");
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 67:
                    int delete_position = msg.arg1;
//                    Intent intent = new Intent(ShopSendDtsActivity.this, CourierDetailsDaifaActivity.class);
                    Intent intent = new Intent(ShopSendDtsActivity.this, CourierDetailsActivity.class);
                    intent.putExtra("waybill_number", packageDatas.get(delete_position).getWaybill_number());
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };

    @InjectView(is_show)
    TextView isShow;
    private boolean showOrHite;//true show

    @OnClick({is_show})
    public void onClick(View view) {
        switch (view.getId()) {
            case is_show:
                if (showOrHite) {
                    showOrHite = false;
                    chiaItemsInfoAdapter = new ChaiItemsInfoAdapter(ShopSendDtsActivity.this, datas, false);
                    lv.setAdapter(chiaItemsInfoAdapter);
                    isShow.setText("展开");
                    Drawable nav_up = ContextCompat.getDrawable(this, R.drawable.downn);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    isShow.setCompoundDrawables(null, null, nav_up, null);
                } else {
                    showOrHite = true;
                    chiaItemsInfoAdapter = new ChaiItemsInfoAdapter(ShopSendDtsActivity.this, datas, true);
                    lv.setAdapter(chiaItemsInfoAdapter);
                    isShow.setText("关闭");
                    Drawable nav_up = ContextCompat.getDrawable(this, R.drawable.upn);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    isShow.setCompoundDrawables(null, null, nav_up, null);
                }
                break;
        }
    }

    @InjectView(R.id.sendAddressView)
    RelativeLayout sendAddressView;
    @InjectView(R.id.collectView)
    RelativeLayout collectView;
    int sendAVHeight = 0;
    int colletAVheight = 0;

    ViewTreeObserver.OnGlobalLayoutListener sendListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            sendAddressView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            sendAVHeight = sendAddressView.getHeight();

            TLog.error("collectAds=" + sendAVHeight);
            if (colletAVheight > 0) {
                if (sendAVHeight > colletAVheight) {
                    ViewGroup.LayoutParams params2 = collectView.getLayoutParams();
                    params2.height = sendAVHeight;
                    collectView.setLayoutParams(params2);
                } else {
                    ViewGroup.LayoutParams params2 = sendAddressView.getLayoutParams();
                    params2.height = colletAVheight;
                    sendAddressView.setLayoutParams(params2);
                }
            }

        }
    };
    ViewTreeObserver.OnGlobalLayoutListener cListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            collectView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            colletAVheight = collectView.getHeight();
            TLog.error("collectAds=" + colletAVheight);
            if (sendAVHeight > 0) {
                if (colletAVheight > sendAVHeight) {
                    ViewGroup.LayoutParams params2 = sendAddressView.getLayoutParams();
                    params2.height = colletAVheight;
                    sendAddressView.setLayoutParams(params2);
                } else {
                    ViewGroup.LayoutParams params2 = collectView.getLayoutParams();
                    params2.height = sendAVHeight;
                    collectView.setLayoutParams(params2);
                }
            }
        }
    };

}
