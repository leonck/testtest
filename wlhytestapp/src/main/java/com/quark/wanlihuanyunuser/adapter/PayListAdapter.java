package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quark.api.auto.bean.PaymentAccountList;
import com.quark.wanlihuanyunuser.R;

import java.util.List;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#
 */
public class PayListAdapter extends BaseAdapter {

    private Context context;
    private List<PaymentAccountList> list;
    Handler handler;
    int checkPositon;
    public PayListAdapter(Context context, List<PaymentAccountList> list, Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
    }

    public void setCheckPositon(int positon){
        checkPositon = positon;
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
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_selectpay_item, null);
        ImageView icon = (ImageView)convertView.findViewById(R.id.icon);
        TextView name = (TextView)convertView.findViewById(R.id.name);
        ImageView check = (ImageView)convertView.findViewById(R.id.check);

        if (position==checkPositon){
            check.setVisibility(View.VISIBLE);
        }else{
            check.setVisibility(View.GONE);
        }

        if(1==list.get(position).getType()){
            name.setText("支付宝");
            icon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.alipay));
        }else if(2==list.get(position).getType()){
            name.setText("微信");
            icon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.weixinpay));
        }else if(3==list.get(position).getType()){
            name.setText("银行");
            icon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.bank_card_pay));
        }else if(4==list.get(position).getType()){
            name.setText("电子卡");
            icon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.carpay));
        }else if(5==list.get(position).getType()){
            name.setText("寄付月结");
            icon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.month_pay));
        }

        return convertView;
    }
}

