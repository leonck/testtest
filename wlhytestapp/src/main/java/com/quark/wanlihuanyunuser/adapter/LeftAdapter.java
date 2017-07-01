package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quark.api.auto.bean.LeftList;
import com.quark.wanlihuanyunuser.R;

import java.util.List;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#
 */
public class LeftAdapter extends BaseAdapter {

    private Context context;
    private List<LeftList> list;

    public LeftAdapter(Context context, List<LeftList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 5;
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
        convertView = LayoutInflater.from(context).inflate(R.layout.left_item, null);
        ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
        TextView nameTv = (TextView) convertView.findViewById(R.id.name_tv);


        if (position == 1) {
            iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.s_3));
            nameTv.setText("寄件记录");

        } else if (position == 2) {
            iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.s_5));
            nameTv.setText("收寄标准");

        } else if (position == 3) {
            iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.s_2));
            nameTv.setText("投诉建议");
        } else if (position == 4) {
            iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.s_4));
            nameTv.setText("服务热线");
        }


        return convertView;


    }
}
