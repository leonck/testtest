package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quark.api.auto.bean.LogisticsAdList;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#物流 item
 */
public class LogisticsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<LogisticsAdList> list;
    Handler handler;

    public LogisticsAdapter(Context context, ArrayList<LogisticsAdList> list, Handler handler) {
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

        convertView = LayoutInflater.from(context).inflate(R.layout.logistics_item, null);
        TextView one = (TextView) convertView.findViewById(R.id.one);
        TextView two = (TextView) convertView.findViewById(R.id.two);
        TextView three = (TextView) convertView.findViewById(R.id.three);
        ImageView removeIv = (ImageView) convertView.findViewById(R.id.remove_iv);

        String waybill_number = list.get(position).getWaybill_number();
        if (Utils.isEmpty(waybill_number)) {
            waybill_number = "暂无运单号";
        }
        one.setText(list.get(position).getWuliu() + " " + waybill_number);
        two.setText("物品名称：" + list.get(position).getGoodsNameShow());
        three.setText("物品重量：" + list.get(position).getWeight()+"kg");
        removeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 110;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }
        });
        return convertView;
    }
}

