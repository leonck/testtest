//package com.quark.kuaishi.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.quark.api.auto.bean.ListComment;
//import com.quark.locklove.R;
//
//import java.util.List;
//
///**
// * @author Administrator
// */
//public class CommonAdapter extends BaseAdapter {
//    private ViewHolder holder;
//    private List<ListComment> list;
//    private Context context;
//
//    public CommonAdapter(Context context, List<ListComment> list) {
//        this.list = list;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int i, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            holder = new ViewHolder();
//            convertView = LayoutInflater.from(context).inflate(R.layout.common_item, null);
//            holder.bill_type = (TextView) convertView.findViewById(R.id.bill_type);
//            holder.bill_time = (TextView) convertView.findViewById(R.id.bill_time);
//            holder.bill_state = (TextView) convertView.findViewById(R.id.bill_state);
//            holder.start1 = (ImageView) convertView.findViewById(R.id.start1);
//            holder.start2 = (ImageView) convertView.findViewById(R.id.start2);
//            holder.start3 = (ImageView) convertView.findViewById(R.id.start3);
//            holder.start4 = (ImageView) convertView.findViewById(R.id.start4);
//            holder.start5 = (ImageView) convertView.findViewById(R.id.start5);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        int starts = list.get(i).getTotal_stars();
//        if (starts > 0) {
//            holder.start1.setVisibility(View.VISIBLE);
//        } else {
//            holder.start1.setVisibility(View.GONE);
//        }
//        if (starts > 1) {
//            holder.start2.setVisibility(View.VISIBLE);
//        } else {
//            holder.start2.setVisibility(View.GONE);
//        }
//        if (starts > 2) {
//            holder.start3.setVisibility(View.VISIBLE);
//        } else {
//            holder.start3.setVisibility(View.GONE);
//        }
//        if (starts > 3) {
//            holder.start4.setVisibility(View.VISIBLE);
//        } else {
//            holder.start4.setVisibility(View.GONE);
//        }
//        if (starts > 4) {
//            holder.start5.setVisibility(View.VISIBLE);
//        } else {
//            holder.start5.setVisibility(View.GONE);
//        }
//        holder.bill_type.setText(list.get(i).getNote());
//        holder.bill_time.setText(list.get(i).getComment_name());
//        holder.bill_state.setText(list.get(i).getPost_date());
//
//        return convertView;
//    }
//
//    private static class ViewHolder {
//        TextView bill_type;//
//        TextView bill_money;//
//        TextView bill_time;//
//        TextView bill_state;//
//        ImageView start1;
//        ImageView start2;
//        ImageView start3;
//        ImageView start4;
//        ImageView start5;
//    }
//
//}
//
//
