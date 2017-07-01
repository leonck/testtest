//package com.quark.kuaishi.ui.widget;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.TableLayout;
//import android.widget.TableRow;
//import android.widget.TextView;
//
//import AppParam;
//import com.quark.kuaishi.R;
//import Utils;
//
//import java.util.ArrayList;
//
//@SuppressLint({ "ResourceAsColor", "NewApi" })
//public class SaixuanUi {
//	/**
//     * 动态生成地址
//     */
//    public static void initDidian(final Context context,final ArrayList<ListBoutique> citys,TableLayout table, final int idhead,int psw){
//    	if(citys.size()>0){
//    		System.out.println("=================================="+citys.size()/4.0);
//    	 for (int j = 0; j < (int)Math.ceil(citys.size()/4.0); j++) {
//         	TableRow tableRow = new TableRow(context);
////         	tableRow.setPaddingRelative(40, 10, 20, 10);
//         	for(int i=0; i<4&&j*4+i<citys.size(); i++){
//     			TextView textView  = new TextView(context);
//                final String tempqu = citys.get(j*4+i).getClassify_two_name();
//     			final int catalog_id = citys.get(j*4+i).getClassify_two_id();
//     			textView.setText(tempqu);
//
//     			textView.setPadding(Utils.dip2px(context, 5), Utils.dip2px(context, 15), Utils.dip2px(context, 5), Utils.dip2px(context, 15));
//     			textView.setTextSize(14);
//     			textView.setMinEms(6);
//     			textView.setWidth(psw);
//     			textView.setGravity(Gravity.CENTER);
//     			textView.setId(j * 4 + i + 1 + idhead * 1000);
//                textView.setBackground(context.getResources().getDrawable(R.drawable.grid));
//     			tableRow.addView(textView);
//
//     			textView.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//                        Bundle bundle = new Bundle();
//                        bundle.putString(ProductList2Activity.PTITLE, tempqu);
//                        bundle.putString(AppParam.PRODUCTTYPR, AppParam.SHITI);
//						bundle.putString("classify_two_id", catalog_id+"");
//                        Intent intent = new Intent();
//                        intent.setClass(context, ProductList2Activity.class);
//                        intent.putExtras(bundle);
//                        context.startActivity(intent);
//					}
//				});
////     			TextView textView2  = new TextView(context);
////     			textView2.setWidth(20);
////     			tableRow.addView(textView2);
//     			tableRow.setGravity(Gravity.LEFT);
//         	}
//         	table.addView(tableRow);
//         }
//    	}
//    }
//
//}
