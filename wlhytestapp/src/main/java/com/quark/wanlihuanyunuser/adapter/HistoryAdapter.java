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

import com.quark.api.auto.bean.IdcardRecordList;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#
 */
public class HistoryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<IdcardRecordList> list;
    Handler handler;

    public HistoryAdapter(Context context, ArrayList<IdcardRecordList> list, Handler handler) {
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

        convertView = LayoutInflater.from(context).inflate(R.layout.history_item, null);
        TextView userTv = (TextView) convertView.findViewById(R.id.user_name_id);
        ImageView iv = (ImageView) convertView.findViewById(R.id.remove_iv);


        String name = list.get(position).getName();
        if (Utils.isEmpty(name)) {
            name = "***";
        }else {
            name = list.get(position).getName();
        }
        userTv.setText(name + "-" + list.get(position).getIdcard_number());
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 119;
                msg.arg1 = position;
                msg.arg2 = list.get(position).getIdcard_record_id();
                handler.sendMessage(msg);
            }
        });

        return convertView;
    }

}

