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

import java.util.ArrayList;

/**
 * Created by pan on 2016/11/4 0004.
 * ># * >#
 */
public class parcelInfoAddAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<LogisticsAdList> list;
    Handler handler;
    private String type; //type==2 查看详情

    public parcelInfoAddAdapter(Context context, ArrayList<LogisticsAdList> list, Handler handler, String type) {
        this.context = context;
        this.list = list;
        this.handler = handler;
        this.type = type;
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
        convertView = LayoutInflater.from(context).inflate(R.layout.parcelinfoadd_item, null);
        ImageView removeIv = (ImageView) convertView.findViewById(R.id.remove_iv);
        TextView nameCode = (TextView) convertView.findViewById(R.id.name_code);
        TextView logisticsStatus = (TextView) convertView.findViewById(R.id.logistics_status);
        TextView itemsName = (TextView) convertView.findViewById(R.id.items_name);
        TextView itemsWeight = (TextView) convertView.findViewById(R.id.items_weight);

        TextView wlchaTv = (TextView) convertView.findViewById(R.id.wlcha_tv);
        if ("2".equals(type)){
            wlchaTv.setVisibility(View.VISIBLE);
            removeIv.setVisibility(View.GONE);

            wlchaTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message msg = new Message();
                    msg.what = 67;
                    msg.arg1 = position;
                    msg.obj = list.get(position).getSend_orders_package_id();
                    handler.sendMessage(msg);
                }
            });
        }else{
            wlchaTv.setVisibility(View.GONE);
            removeIv.setVisibility(View.VISIBLE);
        }

        nameCode.setText(list.get(position).getWuliu() + " " + list.get(position).getWaybill_number());
        logisticsStatus.setVisibility(View.GONE);

        itemsName.setText("物品名称：" + list.get(position).getGoodsNameShow());
        itemsWeight.setText("物品重量：" + list.get(position).getWeight() + "kg");
        removeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 66;
                msg.arg1 = position;
                msg.obj = list.get(position).getSend_orders_package_id();
                handler.sendMessage(msg);
            }
        });

        return convertView;
    }
}

