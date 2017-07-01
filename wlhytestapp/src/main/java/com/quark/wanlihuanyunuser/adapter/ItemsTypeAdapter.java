package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quark.api.auto.bean.LogisticsSendGoodsList;
import com.quark.wanlihuanyunuser.R;

import java.util.ArrayList;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#
 */
public class ItemsTypeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<LogisticsSendGoodsList> list;

    public ItemsTypeAdapter(Context context, ArrayList<LogisticsSendGoodsList> list) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.itemstype_item, null);
        TextView itemName = (TextView) convertView.findViewById(R.id.items_name);
        TextView itemNumber = (TextView) convertView.findViewById(R.id.items_number);

        itemName.setText(list.get(position).getName());
        itemNumber.setText(list.get(position).getLimit_amount() + "");
        if (0==list.get(position).getLimit_amount()) {
            itemNumber.setText("不限");
        }

        return convertView;
    }

}

