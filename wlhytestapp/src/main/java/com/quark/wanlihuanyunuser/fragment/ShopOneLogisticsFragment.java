package com.quark.wanlihuanyunuser.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.AddDaifaOrdersRequest;
import com.quark.api.auto.bean.AddDaifaOrdersResponse;
import com.quark.api.auto.bean.ListProduct;
import com.quark.api.auto.bean.ListUserAddress;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.ItemsInfoAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseFragment;
import com.quark.wanlihuanyunuser.ui.personal.MyAdsActivity;
import com.quark.wanlihuanyunuser.ui.send.SelectPayShopActivity;
import com.quark.wanlihuanyunuser.ui.send.TermsActivity;
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
 * ># 商家 新西兰国内物流
 */
public class ShopOneLogisticsFragment extends BaseFragment {

    View oneView;
    ArrayList<String> datas;
    ItemsInfoAdapter adapter;

    @InjectView(R.id.lv)
    ListViewForScrollView lv;
    @InjectView(R.id.selected_tv)
    TextView selectedTv;
    @InjectView(R.id.send_one_tv)
    TextView sendOneTv;
    @InjectView(R.id.send_two_tv)
    TextView sendTwoTv;
    @InjectView(R.id.send_three_tv)
    TextView sendThreeTv;
    @InjectView(R.id.recevie_one_tv)
    TextView recevieOneTv;
    @InjectView(R.id.recevie_two_tv)
    TextView recevieTwoTv;
    @InjectView(R.id.recevie_three_tv)
    TextView recevieThreeTv;
    @InjectView(R.id.number)
    TextView number;

    private String orders_number;
    private String type;//1-新加波物流，2-国际中国物流
    private String daifa_orders_company_id;//代发订单ID
    private String money;//费用
    private String idcard_record_id;//身份证记录是否国际物流：0-国内，其他-国际（大于0）
    private String idcard_number;//身份证号码
    private String front_card;//身份证正面
    private String back_card;//身份证背面
    private String send_user_address_id;//寄送地址
    private String collect_user_address_id;//收地址
    private String logistics_company_id;//商家公司ID
    ArrayList<ListProduct> newGoodsList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneView = inflater.inflate(R.layout.fragment_logistics_shop_one, container, false);
        ButterKnife.inject(this, oneView);

        daifa_orders_company_id = getArguments().getString("daifa_orders_company_id");
        logistics_company_id = getArguments().getString("logistics_company_id");
        newGoodsList = (ArrayList<ListProduct>) getArguments().getSerializable("list");
        money = getArguments().getString("money");
        adapter = new ItemsInfoAdapter(getActivity(), newGoodsList);
        lv.setAdapter(adapter);

        number.setText(money + "元");
        return oneView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private boolean is_select;

    @OnClick({R.id.send_ly, R.id.collect_ly, R.id.selected_tv, R.id.terms_tv, R.id.sumbit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_ly:
                Intent intent = new Intent(getActivity(), MyAdsActivity.class);
                intent.putExtra("select", "3");
                startActivityForResult(intent, 401);
                break;
            case R.id.collect_ly:
                Intent intent1 = new Intent(getActivity(), MyAdsActivity.class);
                intent1.putExtra("select", "4");
                startActivityForResult(intent1, 402);
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
            case R.id.terms_tv:
                Intent in = new Intent(getActivity(), TermsActivity.class);
                startActivity(in);
                break;
            case R.id.sumbit_tv:
                if (is_select) {
                    if (Utils.isEmpty(collect_user_address_id)) {
                        showToast("请选择收件地址");
                    } else if (Utils.isEmpty(send_user_address_id)) {
                        showToast("请选择寄件地址");
                    } else {
                        getData();
                    }
                } else {
                    showToast("同意快递条款才能提交");
                }
                break;
        }
    }

    public void getData() {
        AddDaifaOrdersRequest rq = new AddDaifaOrdersRequest();
        rq.setType(AppParam.user_type);
        rq.setDaifa_orders_company_id(daifa_orders_company_id);
        rq.setMoney(money);
        rq.setIdcard_record_id("0"); //0国内物流 1国际物流
        rq.setIdcard_number(idcard_number);
        rq.setFront_card(front_card);
        rq.setBack_card(back_card);
        rq.setSend_user_address_id(send_user_address_id);
        rq.setCollect_user_address_id(collect_user_address_id);
        rq.setLogistics_company_id(logistics_company_id);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), AddDaifaOrdersResponse.class);
            if (kd != null) {
                AddDaifaOrdersResponse info = (AddDaifaOrdersResponse) kd;
                if (info.getStatus() == 1) {
                    orders_number = info.getOrders_number();
                    Intent in1 = new Intent(getActivity(), SelectPayShopActivity.class);
                    in1.putExtra("send_orders_id", daifa_orders_company_id);
                    in1.putExtra("orders_number", info.getOrders_number());
                    in1.putExtra("money", money);
                    in1.putExtra("from", "daifa");
                    startActivity(in1);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 0) {
            if (resultCode == 401) {
                send_user_address_id = data.getStringExtra("id");
                if (!send_user_address_id.equals(collect_user_address_id)) {
                    ListUserAddress adsItem = (ListUserAddress) data.getSerializableExtra("ads_item");
                    sendOneTv.setText(adsItem.getProvince() + " " + adsItem.getName() + " " + adsItem.getTelephone());
                    sendTwoTv.setText(adsItem.getAddress());
                    sendThreeTv.setText(adsItem.getProvince() + adsItem.getCity() + adsItem.getArea());
                    TLog.error("寄送地址id" + send_user_address_id);
                } else {
                    showToast("寄件地址不能与收货地址相同");
                    send_user_address_id = "";
                }
            }
            if (resultCode == 402) {
                collect_user_address_id = data.getStringExtra("id");
                if (!collect_user_address_id.equals(send_user_address_id)) {
                    ListUserAddress adsItem = (ListUserAddress) data.getSerializableExtra("ads_item");
                    recevieOneTv.setText(adsItem.getProvince() + " " + adsItem.getName() + " " + adsItem.getTelephone());
                    recevieTwoTv.setText(adsItem.getAddress());
                    recevieThreeTv.setText(adsItem.getProvince() + adsItem.getCity() + adsItem.getArea());
                    TLog.error("收地址id" + collect_user_address_id);
                } else {
                    showToast("寄件地址不能与收货地址相同");
                    collect_user_address_id = "";
                }
            }
        }
    }
}
