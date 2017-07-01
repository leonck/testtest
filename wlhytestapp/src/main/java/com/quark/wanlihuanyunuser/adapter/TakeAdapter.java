package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.quark.api.auto.bean.ListSendOrdersPackage;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;


/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#
 */
public class TakeAdapter extends BaseSwipeAdapter {

    private Context context;
    private ArrayList<ListSendOrdersPackage> list;
    Handler handler;
    int type;//1待取件 2运送中 3已送达 4已失效

    public TakeAdapter(Context context, ArrayList<ListSendOrdersPackage> list, Handler handler, int type) {
        this.context = context;
        this.list = list;
        this.handler = handler;
        this.type = type;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeLayout;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.take_item, null);
        return v;
    }

    @Override
    public void fillValues(final int position, View convertView) {
        final SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipeLayout);
        TextView sendAds = (TextView) convertView.findViewById(R.id.send_ads);
        TextView sendName = (TextView) convertView.findViewById(R.id.send_name);
        TextView collectAds = (TextView) convertView.findViewById(R.id.collect_ads);
        TextView collectName = (TextView) convertView.findViewById(R.id.collect_name);
        TextView orderNumber = (TextView) convertView.findViewById(R.id.order_number);
        TextView remarkTv = (TextView) convertView.findViewById(R.id.remark_tv);
        TextView infoTv = (TextView) convertView.findViewById(R.id.info_tv);
        TextView dateTv = (TextView) convertView.findViewById(R.id.date_tv);

        sendAds.setText(list.get(position).getSend_area());
        sendName.setText(list.get(position).getSend_name());
        collectAds.setText(list.get(position).getCollect_area());
        collectName.setText(list.get(position).getCollect_name());
        orderNumber.setText("运单号：" + list.get(position).getWaybill_number());
        if (Utils.isEmpty(list.get(position).getRemarker())) {
            remarkTv.setText("备注：无");
        } else {
            remarkTv.setText("备注：" + list.get(position).getRemarker());
        }
        infoTv.setText(list.get(position).getSOPinfo());
        dateTv.setText(list.get(position).getPost_time());

        LinearLayout cancelLy = (LinearLayout) convertView.findViewById(R.id.cancel);
        cancelLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Message msg = new Message();
                msg.what = 201;
                msg.arg1 = position;
                handler.sendMessage(msg);
                swipeLayout.close();
            }
        });
        if (type == 1 || type == 2) {
            swipeLayout.setSwipeEnabled(false);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
