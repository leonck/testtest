package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quark.api.auto.bean.ListUserAddress;
import com.quark.wanlihuanyunuser.R;

import java.util.ArrayList;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#
 */
public class AdsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ListUserAddress> list;
    Handler handler;

    public AdsAdapter(Context context, ArrayList<ListUserAddress> list, Handler handler) {
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

        convertView = LayoutInflater.from(context).inflate(R.layout.ads_item, null);
        ImageView adsType = (ImageView) convertView.findViewById(R.id.ads_type);
        TextView userTv = (TextView) convertView.findViewById(R.id.user_tv);
        TextView adsTv = (TextView) convertView.findViewById(R.id.ads_tv);
        TextView detailsTv = (TextView) convertView.findViewById(R.id.dtl_ads_tv);
        TextView setTv = (TextView) convertView.findViewById(R.id.set_tv);
        TextView removeTv = (TextView) convertView.findViewById(R.id.remove_tv);

        if (list.get(position).getAddress_type() == 1) {
            adsType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.outside));
        } else if (list.get(position).getAddress_type() == 2) {
            adsType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.inside));
        }
        if (list.get(position).getStatus() == 1) {
            Drawable drawable = context.getResources().getDrawable(R.drawable.uncheck_address);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            setTv.setCompoundDrawables(drawable, null, null, null);
        } else if (list.get(position).getStatus() == 2) {
            Drawable drawable = context.getResources().getDrawable(R.drawable.check_default_address);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            setTv.setCompoundDrawables(drawable, null, null, null);
        }

        userTv.setText(list.get(position).getName() + "   " + list.get(position).getTelephone());
        adsTv.setText(list.get(position).getProvince() + " " + list.get(position).getCity() + " " + list.get(position).getArea());
        detailsTv.setText(list.get(position).getAddress());

        setTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 300;
                msg.arg1 = position;
                msg.arg2 = list.get(position).getUser_address_id();
                handler.sendMessage(msg);
            }
        });

        removeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 301;
                msg.arg1 = position;
                msg.arg2 = list.get(position).getUser_address_id();
                handler.sendMessage(msg);
            }
        });
        return convertView;
    }

}

