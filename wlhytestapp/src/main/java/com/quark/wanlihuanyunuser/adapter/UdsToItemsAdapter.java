package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quark.api.auto.bean.ListProduct;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.api.ApiHttpClient;

import java.util.ArrayList;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#代发物品
 */
public class UdsToItemsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ListProduct> list;
    Handler handler;
//    HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

    public UdsToItemsAdapter(Context context, ArrayList<ListProduct> list, Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
    }

    public ArrayList<ListProduct> getList() {
        return list;
    }

    public void setList(ArrayList<ListProduct> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.udstoitems_item, null);
        LinearLayout ly = (LinearLayout) convertView.findViewById(R.id.ly);
        ImageView selectIv = (ImageView) convertView.findViewById(R.id.select_iv);
        ImageView goodsIv = (ImageView) convertView.findViewById(R.id.items_iv);
        TextView titleTv = (TextView) convertView.findViewById(R.id.title_tv);
        TextView moneyTv = (TextView) convertView.findViewById(R.id.money_tv);
        TextView isSendTv = (TextView) convertView.findViewById(R.id.is_send);
        final EditText numberEt = (EditText) convertView.findViewById(R.id.number_et);
        numberEt.setText(list.get(position).getGoods_number()+"");
        numberEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String goodsNumber = s.toString();
                try {
                    int gn = Integer.valueOf(goodsNumber);
                    list.get(position).setGoods_number(gn);
                    Message msg = new Message();
                    msg.what = 116;
                    msg.arg1 = position;
                    msg.arg2 = gn;
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if (list.get(position).getSend_type() == 1) {
            isSendTv.setVisibility(View.GONE);
        } else {
            isSendTv.setVisibility(View.VISIBLE);
        }
        ApiHttpClient.loadImage(list.get(position).getImages_01(), goodsIv, R.drawable.image);
        titleTv.setText(list.get(position).getTitle());
        moneyTv.setText(list.get(position).getCompany_price() + "元");

        if (list.get(position).isCheck()) {
            selectIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.check_product));
        } else {
            selectIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.uncheck_product));
        }
        selectIv.setEnabled(true);
        selectIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 115;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }
        });

        return convertView;
    }

}

