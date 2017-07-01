package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quark.api.auto.bean.DaifaOrdersPackageList;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.ui.personal.CourierDetailsActivity;

import java.util.ArrayList;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#
 */
public class parcelInfoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DaifaOrdersPackageList> list;

    public parcelInfoAdapter(Context context, ArrayList<DaifaOrdersPackageList> list) {
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

        convertView = LayoutInflater.from(context).inflate(R.layout.parcelinfo_item, null);
        TextView detailsTv = (TextView) convertView.findViewById(R.id.details_tv);
        TextView nameCode = (TextView) convertView.findViewById(R.id.name_code);
        TextView itemsName = (TextView) convertView.findViewById(R.id.items_name);
        TextView itemsWeight = (TextView) convertView.findViewById(R.id.items_weight);
        TextView logisticStatus = (TextView) convertView.findViewById(R.id.logistics_status);

        nameCode.setText(list.get(position).getLogistics_name() + " " + list.get(position).getWaybill_number());

        itemsWeight.setText("物品重量：" + list.get(position).getDeclared_weight());

//        StringBuilder sb = new StringBuilder();
////        List<DaifaOrdersPackageList> packageList = list.get(position).get;
//        for (int i = 0; i < list.size(); i++) {
//            sb.append("、" + list.get(i).get + "x" + packageList.get(i).getGoods_number());
//        }
        itemsName.setText("物品名称：");

        if (list.get(position).getStatus() == -1) {//-1-无效，0-未出单，1-已出单，2-运送中，3-已运达
            logisticStatus.setText("物流状态：无效");
        } else if (list.get(position).getStatus() == 0) {
            logisticStatus.setText("物流状态：未出单");
        } else if (list.get(position).getStatus() == 1) {
            logisticStatus.setText("物流状态：已出单");
        } else if (list.get(position).getStatus() == 2) {
            logisticStatus.setText("物流状态：运送中");
        } else if (list.get(position).getStatus() == 3) {
            logisticStatus.setText("物流状态：已送达");
        }
        detailsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourierDetailsActivity.class);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

}

