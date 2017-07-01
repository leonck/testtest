package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quark.api.auto.bean.ListChargeLog;
import com.quark.wanlihuanyunuser.R;

import java.util.ArrayList;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#交易记录
 */
public class TransactionAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ListChargeLog> list;

    public TransactionAdapter(Context context, ArrayList<ListChargeLog> list) {
        this.context = context;
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.transaction_item, null);
        TextView orderCode = (TextView) convertView.findViewById(R.id.order_code);
        TextView moneyTv = (TextView) convertView.findViewById(R.id.money_tv);
        TextView payState = (TextView) convertView.findViewById(R.id.pay_state);
        TextView dateTv = (TextView) convertView.findViewById(R.id.date_tv);

        orderCode.setText("订单号：" + list.get(position).getOrders_number());

        if (list.get(position).getType() == 1) {
            moneyTv.setText("充值 - " + list.get(position).getMoney() + "元 ");
        } else if (list.get(position).getType() == 2) {
            moneyTv.setText("运单支付 - " + list.get(position).getMoney() + "元 ");
        }
        if (list.get(position).getStatus() == 1) {
            payState.setText("(待确认)");
            payState.setTextColor(ContextCompat.getColor(context, R.color.blue));
        } else if (list.get(position).getStatus() == 2) {
            payState.setText("(到账)");
            payState.setTextColor(ContextCompat.getColor(context, R.color.menu_lan));


        }

        dateTv.setText(list.get(position).getPost_time());

        return convertView;


    }
}
