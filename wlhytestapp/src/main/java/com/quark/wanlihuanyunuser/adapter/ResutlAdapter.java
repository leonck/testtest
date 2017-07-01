package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quark.api.auto.bean.SearchUserTransportList;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#
 */
public class ResutlAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SearchUserTransportList> list;

    public ResutlAdapter(Context context, ArrayList<SearchUserTransportList> list) {
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

        convertView = LayoutInflater.from(context).inflate(R.layout.result_item, null);
        TextView sendAds = (TextView) convertView.findViewById(R.id.send_ads);
        TextView sendName = (TextView) convertView.findViewById(R.id.send_name);
        TextView collectAds = (TextView) convertView.findViewById(R.id.collect_ads);
        TextView collectName = (TextView) convertView.findViewById(R.id.collect_name);
        TextView remarkTv = (TextView) convertView.findViewById(R.id.remark_tv);
        TextView infoTv = (TextView) convertView.findViewById(R.id.info_tv);
        TextView orderNumber = (TextView) convertView.findViewById(R.id.order_number);
        TextView dateTv = (TextView) convertView.findViewById(R.id.date_tv);

        sendAds.setText(list.get(position).getSend_area());
        sendName.setText(list.get(position).getSend_name());
        collectAds.setText(list.get(position).getCollect_area());
        collectName.setText(list.get(position).getCollect_name());
        if (Utils.isEmpty(list.get(position).getRemarker())){
            remarkTv.setText("备注：无");
        }else{
            remarkTv.setText("备注："+list.get(position).getRemarker());
        }
        infoTv.setText(list.get(position).getSOPinfo());
        orderNumber.setText("运单号："+list.get(position).getWaybill_number());
        dateTv.setText(list.get(position).getPost_time());

        return convertView;
    }


}

