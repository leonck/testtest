package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.quark.api.auto.bean.GoodsList;
import com.quark.wanlihuanyunuser.R;

import java.util.ArrayList;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#
 */
public class GoodsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<GoodsList> list;
    Handler handler;

    public GoodsAdapter(Context context, ArrayList<GoodsList> list, Handler handler) {
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

        convertView = LayoutInflater.from(context).inflate(R.layout.goods_item, null);
        TextView goodsName = (TextView) convertView.findViewById(R.id.goods_name);
        ImageView removeIv = (ImageView) convertView.findViewById(R.id.remove_iv);
        final TextView numberTv = (TextView) convertView.findViewById(R.id.number_tv);
        Button jianBt = (Button) convertView.findViewById(R.id.jian_bt);
        Button jiaBt = (Button) convertView.findViewById(R.id.jia_bt);

        if (list.get(position).getGoods_number() > 1) {
            jianBt.setBackground(ContextCompat.getDrawable(context, R.drawable.minus_2));
        } else {
            jianBt.setBackground(ContextCompat.getDrawable(context, R.drawable.minus_1));
        }

        if (list.get(position).getGoods_number() < list.get(position).getLimit_amount()
                || list.get(position).getLimit_amount() == 0) {
            jiaBt.setBackground(ContextCompat.getDrawable(context, R.drawable.plus));
        } else {
            jiaBt.setBackground(ContextCompat.getDrawable(context, R.drawable.plus_no));
        }

        numberTv.setText(list.get(position).getGoods_number() + "");
        removeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 30;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }
        });

        jiaBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 31;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }
        });

        jianBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 32;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }
        });
        goodsName.setText(list.get(position).getGoods_name());
        return convertView;
    }

}

