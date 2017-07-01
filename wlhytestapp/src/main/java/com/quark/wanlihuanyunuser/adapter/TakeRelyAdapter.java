package com.quark.wanlihuanyunuser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quark.api.auto.bean.ListSendOrdersPackage;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#
 */
public class TakeRelyAdapter extends RecyclerView.Adapter<TakeRelyAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ListSendOrdersPackage> list;

    public TakeRelyAdapter(Context context, ArrayList<ListSendOrdersPackage> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.take_item_index,parent, false));
    }

    //点击接口
    public interface OnItemClickLitener {
        /**
         * 点击事件处理
         *
         * @author pan
         * @time 2016/10/31 0031 上午 11:18
         */
        void onItemClick(View view, int position);

        /**
         * 长按点击事件处理
         *
         * @author pan
         * @time 2016/10/31 0031 上午 11:18
         */
        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        int sw = new AppParam().getScreenWidth(context);
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
        if (!Utils.isEmpty(list.get(position).getCollect_name())) {
            holder.sendAds.setText(list.get(position).getSend_area());
            holder.sendName.setText(list.get(position).getSend_name());
            holder.collectAds.setText(list.get(position).getCollect_area());
            holder.colectName.setText(list.get(position).getCollect_name());
            holder.orderNumber.setText("运单号：" + list.get(position).getWaybill_number());
            if (Utils.isEmpty(list.get(position).getRemarker())){
                holder.remarkTv.setText("备注：无");
            }else{
                holder.remarkTv.setText("备注：" + list.get(position).getRemarker());
            }
            if (!Utils.isEmpty(list.get(position).getsOPinfo())){
                holder.infoTv.setText("" + list.get(position).getsOPinfo());
            }else{
                holder.infoTv.setText("暂无物流信息");
            }
            holder.dateTv.setText(list.get(position).getPost_time());
        } else {
            holder.itemLy.setVisibility(View.GONE);
            ViewGroup.LayoutParams params3 = holder.nodata.getLayoutParams();
            params3.width = sw;
            holder.nodata.setLayoutParams(params3);
            holder.nodata.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sendAds;
        TextView sendName;
        TextView collectAds;
        TextView colectName;
        TextView orderNumber;
        TextView remarkTv;
        TextView infoTv;
        TextView dateTv;
        TextView nodata;
        LinearLayout itemLy;

        public ViewHolder(View itemView) {
            super(itemView);
            sendAds = (TextView) itemView.findViewById(R.id.send_ads);
            sendName = (TextView) itemView.findViewById(R.id.send_name);
            collectAds = (TextView) itemView.findViewById(R.id.collect_ads);
            colectName = (TextView) itemView.findViewById(R.id.collect_name);
            orderNumber = (TextView) itemView.findViewById(R.id.order_number);
            remarkTv = (TextView) itemView.findViewById(R.id.remark_tv);
            infoTv = (TextView) itemView.findViewById(R.id.info_tv);
            dateTv = (TextView) itemView.findViewById(R.id.date_tv);
            nodata = (TextView) itemView.findViewById(R.id.noData);
            itemLy = (LinearLayout) itemView.findViewById(R.id.items_ly);
        }
    }

}
