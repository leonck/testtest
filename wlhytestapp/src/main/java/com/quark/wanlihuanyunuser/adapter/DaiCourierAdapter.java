package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.quark.api.auto.bean.ListDaifaOrdersCompany;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.databinding.DaicourierBindItemBinding;

import java.util.ArrayList;

public class DaiCourierAdapter extends BaseSwipeAdapter {
    private Context context;
    private ArrayList<ListDaifaOrdersCompany> list;
    private Handler handler;

    public DaiCourierAdapter(Context context, ArrayList<ListDaifaOrdersCompany> list, Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeLayout;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
//        View v = LayoutInflater.from(context).inflate(R.layout.daicourier_item, null);

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Create the binding
        DaicourierBindItemBinding binding = DaicourierBindItemBinding.inflate(inflater, parent, false);
        binding.setBean(list.get(position));
        return binding.getRoot();
    }

    @Override
    public void fillValues(final int position, View convertView) {
        final SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipeLayout);
        Button bt = (Button) convertView.findViewById(R.id.bt);
        TextView orderCode = (TextView) convertView.findViewById(R.id.order_code);
        TextView userName = (TextView) convertView.findViewById(R.id.user_name);
        orderCode.setText("订单编号：" + list.get(position).getOrders_number());
//        userName.setText("收件人：" + list.get(position).getCollect_name() + " " + list.get(position).getCollect_telephone());
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 200;
                msg.arg1 = position;
                msg.arg2 = list.get(position).getDaifa_orders_company_id();
                handler.sendMessage(msg);
            }
        });

        LinearLayout cancelLy = (LinearLayout) convertView.findViewById(R.id.cancel);
        cancelLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Message msg = new Message();
                msg.what = 201;
                msg.arg1 = position;
                handler.sendMessage(msg);
                swipeLayout.close();
            }
        });
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
