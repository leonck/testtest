package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quark.wanlihuanyunuser.R;

import java.util.List;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#
 */
public class PhoneAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    Handler handler;

    public PhoneAdapter(Context context, List<String> list, Handler handler) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.phone_item, null);
        TextView content = (TextView) convertView.findViewById(R.id.content);
        content.setText(list.get(position));
        return convertView;
    }

}

