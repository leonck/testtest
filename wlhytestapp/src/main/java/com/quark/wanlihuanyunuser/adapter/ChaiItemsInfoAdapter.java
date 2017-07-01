package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quark.api.auto.bean.DaifaOrdersGoodsList;
import com.quark.wanlihuanyunuser.R;

import java.util.ArrayList;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#
 */
public class ChaiItemsInfoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DaifaOrdersGoodsList> list;
    boolean showOrHite;
    public ChaiItemsInfoAdapter(Context context, ArrayList<DaifaOrdersGoodsList> list,boolean showOrHite) {
        this.context = context;
        this.list = list;
        this.showOrHite = showOrHite;
    }

    @Override
    public int getCount() {
        if (showOrHite) {
            return list.size();
        } else {
            if (list.size() > 1) {
                return 2;
            } else {
                return list.size();
            }
        }
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

        convertView = LayoutInflater.from(context).inflate(R.layout.itemsinfo_item, null);
        TextView itemName = (TextView) convertView.findViewById(R.id.items_name);
        TextView itemContent = (TextView) convertView.findViewById(R.id.items_content);
        TextView monneyTv = (TextView) convertView.findViewById(R.id.money_tv);
        TextView numberTv = (TextView) convertView.findViewById(R.id.number_tv);

        itemName.setText(list.get(position).getTitle());
        monneyTv.setText(list.get(position).getCompany_price() + "元");
        numberTv.setText("x" + list.get(position).getBuy_number());

        return convertView;
    }
}

