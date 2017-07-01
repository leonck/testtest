package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quark.api.auto.bean.SendOrdersPackageInformation;
import com.quark.wanlihuanyunuser.R;

import java.util.List;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#
 */
public class LogisticsInfoAdapter extends BaseAdapter {

    private Context context;
    private List<SendOrdersPackageInformation> list;

    public LogisticsInfoAdapter(Context context, List<SendOrdersPackageInformation> list) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.logisticsinformatio_item, null);
        TextView dateTv = (TextView) convertView.findViewById(R.id.date_tv);
        TextView timeTv = (TextView) convertView.findViewById(R.id.time_tv);
        ImageView stateIv = (ImageView) convertView.findViewById(R.id.state_iv);
        TextView contentTv = (TextView) convertView.findViewById(R.id.content_tv);

        if (position == 0) {
            stateIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active));
            dateTv.setTextColor(ContextCompat.getColor(context, R.color.blue));
            timeTv.setTextColor(ContextCompat.getColor(context, R.color.blue));
            contentTv.setTextColor(ContextCompat.getColor(context, R.color.blue));
            contentTv.setTextSize(18);
        } else {
            stateIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.normal));
            dateTv.setTextColor(ContextCompat.getColor(context, R.color.qianhuise));
            timeTv.setTextColor(ContextCompat.getColor(context, R.color.qianhuise));
            contentTv.setTextColor(ContextCompat.getColor(context, R.color.qianhuise));
            contentTv.setTextSize(14);
        }

        String times = list.get(position).getPost_time();
        String date = "", time = "";
        if (times.length() > 10) {
            date = times.substring(0, 10);
        }
        if (times.length() > 10) {
            time = times.substring(11, 16);
        }
        dateTv.setText(date);
        timeTv.setText(time);
        contentTv.setText(list.get(position).getContent());
        contentTv.setAutoLinkMask(Linkify.PHONE_NUMBERS);
        contentTv.setMovementMethod(LinkMovementMethod.getInstance());


        return convertView;
    }

}

