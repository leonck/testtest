package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quark.api.auto.bean.ListPaySendOrdersList;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.List;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#
 */
public class PaitOrderAdapter extends BaseAdapter {

    private Context context;
    private List<ListPaySendOrdersList> list;
    Handler handler;

    public PaitOrderAdapter(Context context, List<ListPaySendOrdersList> list, Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.order_item, null);
        TextView orderNumber = (TextView) convertView.findViewById(R.id.order_number);
        TextView cancelTv = (TextView) convertView.findViewById(R.id.cancel_tv);
        TextView payTv = (TextView) convertView.findViewById(R.id.pay_tv);
        TextView shopState = (TextView) convertView.findViewById(R.id.shop_state);
        TextView orderType = (TextView) convertView.findViewById(R.id.order_type);
        TextView oneTv = (TextView) convertView.findViewById(R.id.one_tv);
        TextView twoTv = (TextView) convertView.findViewById(R.id.two_tv);
        TextView numbetTV = (TextView) convertView.findViewById(R.id.number_tv);

        payTv.setVisibility(View.GONE);
        shopState.setVisibility(View.GONE);
        cancelTv.setText("删除订单");
        cancelTv.setVisibility(View.VISIBLE);
        orderNumber.setText("订单号：" + list.get(position).getOrders_number());
        if (list.get(position).getOrders_type().equals("1")) { //  	订单类型：1-上门取件，2-商家代发，3-物流代发
            orderType.setVisibility(View.GONE);
            if (!Utils.isEmpty(list.get(position).getStatus_message())) {
                oneTv.setText("订单状态：" + list.get(position).getStatus_message());
            } else {
                oneTv.setText("订单状态：暂无");
            }
            twoTv.setVisibility(View.GONE);
            numbetTV.setText(list.get(position).getPackage_number() + "个包裹");
        } else if (list.get(position).getOrders_type().equals("2")) {
            twoTv.setVisibility(View.VISIBLE);
            orderType.setVisibility(View.VISIBLE);
            oneTv.setText("收件人：" + list.get(position).getCollect_name() + " " + list.get(position).getCollect_telephone());
            twoTv.setText("收件地址：" + list.get(position).getCollect_address());
            numbetTV.setText(list.get(position).getProduct_number() + "个商品");

            if ("2".equals(list.get(position).getPay_type_comfity())){
                shopState.setVisibility(View.VISIBLE);
                cancelTv.setVisibility(View.GONE);
            }
        }

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 20;
                msg.arg1 = position;
                msg.obj = list.get(position).getOrders_id();
                handler.sendMessage(msg);
            }
        });
        return convertView;
    }


}

