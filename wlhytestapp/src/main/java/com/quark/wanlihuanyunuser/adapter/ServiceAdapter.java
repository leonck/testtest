package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quark.api.auto.bean.LogisticsList;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.api.ApiHttpClient;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#
 */
public class ServiceAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<LogisticsList> list;

    public ServiceAdapter(Context context, ArrayList<LogisticsList> list) {
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

        convertView = LayoutInflater.from(context).inflate(R.layout.service_item, null);
        ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
        TextView nameTv = (TextView) convertView.findViewById(R.id.name_tv);
        TextView phoneTv = (TextView) convertView.findViewById(R.id.phone_tv);

        if (!Utils.isEmpty(list.get(position).getService_telephone())){
            String[] phoneStr = list.get(position).getService_telephone().split("#");
            String ps="服务热线 ：";
            for (int i=0;i<phoneStr.length;i++){
                ps += phoneStr[i]+"、";
            }
            if (ps.endsWith("、")){
                ps = ps.substring(0,ps.length()-1);
            }
            phoneTv.setText(ps);
        }
        ApiHttpClient.loadImage(list.get(position).getImages_01(), iv, R.drawable.default_1);
        nameTv.setText(list.get(position).getName());
        return convertView;
    }


}

