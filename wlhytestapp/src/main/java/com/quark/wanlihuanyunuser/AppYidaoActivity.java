//package com.quark.kuaishi;
//
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.os.Bundle;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.quark.kuaishi.ui.userinfo.LoginActivity;
//import com.umeng.analytics.MobclickAgent;
//
//import java.util.ArrayList;
//
//
//public class AppYidaoActivity extends Activity{
//     private int pos = 0;
//     private int maxPos = 0;
//     private int currentPageScrollStatus;
//
//	int height;
//	//////////
//	private ArrayList<View> listViews = null;
//	private ViewPager pager;
//	private MyPageAdapter adapter;
//	ImageView rightrig;
//	TextView shop_text;
//	TextView order_text;
//	TextView terminal_number_text;
//	TextView id_text;
//	TextView goods_name;
//	TextView number;
//    int[] ids;
//
//	TextView go;
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.start_yindao);
////		System.out.println(ApplicationControl.isFirstIn+"============s==========================");
//		SharedPreferences sp1 = getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, MODE_PRIVATE);
//		   //判断是不是首次登录，
//		if(sp1.getBoolean("firststart", true)) {
//			Editor editjb = sp1.edit();
//		 //将登录标志位设置为false，下次登录时不在显示首次登录界面
//		    editjb.putBoolean("firststart", false);
//		    editjb.commit();
//			maxPos = 4;
//			ids = new int[4];
//			ids[0] = R.drawable.yd_1;
//			ids[1] = R.drawable.yd_2;
//			ids[2] = R.drawable.yd_3;
//			ids[3] = R.drawable.yd_4;
//			init();
//            go = (TextView)findViewById(R.id.go);
//            go.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent();
//                    intent.setClass(AppYidaoActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            });
//		}else{
//			Intent intent = new Intent();
//			intent.setClass(AppYidaoActivity.this, AppStart.class);
//			startActivity(intent);
//			finish();
//		}
//	}
//
//	public void init(){
//		pager = (ViewPager) findViewById(R.id.viewpager);     // 找到ViewPager
//		pager.setOnPageChangeListener(pageChangeListener);    // 设置页面滑动监听
//
//		initListViews();//
//
//		adapter = new MyPageAdapter(listViews);// 构造adapter
//		pager.setAdapter(adapter);// 设置适配器
//		adapter.notifyDataSetChanged();// 刷新
//	}
//
//	private void initListViews() {
//		if (listViews == null)
//			listViews = new ArrayList<View>();
//		for (int i = 0; i < 4; i++) {
//			View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.yidao_item, null);
//			ImageView ig = (ImageView)view.findViewById(R.id.pic);
//			ig.setImageDrawable(getResources().getDrawable(ids[i]));
////			view.setBackgroundDrawable(getResources().getDrawable(ids[i]));
//			listViews.add(view);
//		}
//	}
//
//	/**
//	 * 页面监听事件
//	 */
//	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
//
//		@Override
//		public void onPageSelected(int position) {
//			// 切换page设置当前position值
//			pos = position;
//		}
//
//		@Override
//		public void onPageScrolled(int position, float positionOffset,
//				int positionOffsetPixels) {
//
//			if (pos == 0) {
//				// 如果offsetPixels是0页面也被滑动了，代表在第一页还要往左划
//				if (positionOffsetPixels == 0 && currentPageScrollStatus == 1) {
//					Log.e("e", "===============在第一页还要往左划========");
//				}
//			} else if (pos == (maxPos - 1)) {
//				// 已经在最后一页还想往右划
//				if (positionOffsetPixels == 0 && currentPageScrollStatus == 1) {
//					Log.e("e", "=================最后一页还想往右划============");
////					if(ApplicationControl.isFirstIn){
////						ApplicationControl.isFirstIn = false;
////						Log.e("e", "=================最后一页还想往右划111111111111111111============");
////						Intent intent = new Intent();
////						intent.setClass(AppYidaoActivity.this, MainTabActivity.class);
////						startActivity(intent);
////						finish();
////					}
//				}
//			}
//		}
//		boolean hl = false;
//		public void onPageScrollStateChanged(int arg0) {// 滑动状态改变
//														// 有三种状态（0，1，2）。arg0
//														// ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了
//			System.out.println("滑动状态改变");
//			// 记录page滑动状态，如果滑动了state就是1
//			currentPageScrollStatus = arg0;
//
//			// 只有一张的时候
////			if (maxPos == 1) {
////				Log.e("e", "=================只有一张============");
////				ApplicationControl.isFirstIn = false;
////				Intent intent = new Intent();
////				intent.setClass(AppYidaoActivity.this, MainTabActivity.class);
////				startActivity(intent);
////				finish();
////			}
//		}
//	};
//
//
//    /**
//	 * ViewPager适配器
//	 *
//	 * @author zhou
//	 *
//	 */
//	class MyPageAdapter extends PagerAdapter {
//
//		private ArrayList<View> listViews;// content
//
//		private int size;// 页数
//
//		public MyPageAdapter(ArrayList<View> listViews) {// 构造函数
//															// 初始化viewpager的时候给的一个页面
//			this.listViews = listViews;
//			size = listViews == null ? 0 : listViews.size();
//
//		}
//
//		public void setListViews(ArrayList<View> listViews) {// 自己写的一个方法用来添加数据
//			this.listViews = listViews;
//			size = listViews == null ? 0 : listViews.size();
//		}
//
//		@Override
//		public int getCount() {// 返回数量
//			return size;
//		}
//
//		@Override
//		public void destroyItem(View arg0, int arg1, Object arg2) {// 销毁view对象
//			((ViewPager) arg0).removeView(listViews.get(arg1 % size));
//		}
//
//		@Override
//		public void finishUpdate(View arg0) {
//		}
//
//		@Override
//		public Object instantiateItem(View arg0, int arg1) {// 返回view对象
//			try {
//				((ViewPager) arg0).addView(listViews.get(arg1 % size), 0);
//			} catch (Exception e) {
//				Log.e("zhou", "exception：" + e.getMessage());
//			}
//			return listViews.get(arg1 % size);
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			return arg0 == arg1;
//		}
//
//		@Override
//		public int getItemPosition(Object object) {
//			return POSITION_NONE;
//		}
//	}
//    @Override
//    protected void onPause() {
//        JPushInterface.onPause(this);
//        MobclickAgent.onPause(this);
//
//        super.onPause();
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        JPushInterface.onResume(this);
//        MobclickAgent.onResume(this);
//
//    }
//  }
//
//
//
//
//
//
//
//
//
//
