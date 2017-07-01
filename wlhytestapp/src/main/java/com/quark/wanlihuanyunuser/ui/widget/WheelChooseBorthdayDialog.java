package com.quark.wanlihuanyunuser.ui.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quark.wanlihuanyunuser.R;

import java.util.ArrayList;
import java.util.Calendar;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;


@SuppressLint("NewApi")
public class WheelChooseBorthdayDialog implements OnClickListener, OnWheelChangedListener{
	public String city;
	private static WheelView mViewProvince;
	private static WheelView mViewCity;
	private WheelView mViewDistrict;
	private static WheelView mViewHour;
	private static WheelView mViewMinute;
	private static TextView mBtnConfirm;
	public Context context;
//	int tyear;
	int tm ;
	int tmonth;
	boolean isc = false;
	protected String[] mYears = new String[200];
	ArrayList<String> temptimes = new ArrayList<String>();

	protected String[] days;
	String[] months = {"1","2","3","4","5","6","7","8","9","10","11","12"};

	protected String hour = "00";
	protected String minute = "00";

	protected String year;
	protected String month = "01";
	protected String day ="01";
	protected String mCurrentZipCode ="";
	String[] hours = new String[24];
	String[] minutes = new String[60];
	Calendar c;

	 //=========================show city=================
	 @Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {

		 if (wheel == mViewProvince) {
			 updateCities();
		 } else if (wheel == mViewCity) {
			 updateAreas();
		 } else if (wheel == mViewDistrict) {
			 day = days[newValue];
		 } else if (wheel == mViewHour) {
			 hour = hours[newValue];
		 } else if (wheel == mViewMinute) {
			 minute = minutes[newValue];
		 }
		}

		/**
		 * 根据当前的市，更新区WheelView的信息
		 */
		private void updateAreas() {
			int pCurrent = mViewCity.getCurrentItem();
			month = months[pCurrent];
			days = getperDay();
			mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(context, days));
		}

		private void updateCities() {
			int pCurrent = mViewProvince.getCurrentItem();
			year = mYears[pCurrent];
			mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(context, months));
			updateAreas();
		}


		@Override
		public void onClick(View v) {
//				showSelectedResult();
		}

		//显示底部 层
		public Dialog showSheetPic(final Context context,final EditText time,final Handler handler,final int code) {
			this.context = context;
			final Dialog dlg = new Dialog(context, R.style.ActionSheet);
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.pick_borthday, null);
			final int cFullFillWidth = 10000;
			layout.setMinimumWidth(cFullFillWidth);

			mViewProvince = (WheelView) layout.findViewById(R.id.id_province);
			mViewCity = (WheelView) layout.findViewById(R.id.id_city);
			mViewDistrict = (WheelView) layout.findViewById(R.id.id_district);

			mBtnConfirm = (TextView) layout.findViewById(R.id.btn_confirm);

	    	mViewProvince.addChangingListener(this);
	    	mViewCity.addChangingListener(this);
	    	mViewDistrict.addChangingListener(this);
	    	mBtnConfirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					time.setText(year+"-"+month+"-"+day);
					Message msg = new Message();
					msg.what = code;
					msg.obj = year+"-"+month+"-"+day;
					handler.sendMessage(msg);
					dlg.dismiss();
				}
			});

//	    	initProvinceDatas(context);
	    	getDate();
			mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(context, mYears));
			// 设置可见条目数量
			mViewProvince.setVisibleItems(7);
			mViewCity.setVisibleItems(7);
			mViewDistrict.setVisibleItems(7);

//			Calendar c = Calendar.getInstance();
//			tyear = c.get(Calendar.YEAR);
//			tmonth = c.get(Calendar.MONTH);
//			tm = tyear - 2014;

			mViewProvince.setCurrentItem(100);
//			mViewCity.setCurrentItem(tmonth);

			updateCities();
//			updateAreas();

			Window w = dlg.getWindow();
			WindowManager.LayoutParams lp = w.getAttributes();
			lp.x = 0;
			final int cMakeBottom = -1000;
			lp.y = cMakeBottom;
			lp.gravity = Gravity.BOTTOM;
			dlg.onWindowAttributesChanged(lp);
			dlg.setCanceledOnTouchOutside(false);
			dlg.setContentView(layout);
			dlg.show();

			return dlg;
		}

		//获取 下一千天日期
		public void getDate(){
            for(int i=0;i<200;i++){
            	mYears[i] = (1915+i)+"";
            }
		}

	//获取 小时
	public void getHours() {

		for (int i = 0; i <= 23; i++) {
			if (i < 10) {
				hours[i] = "0" + i;
			} else {
				hours[i] = i + "";
			}

		}

	}

	//获取 分钟
	public void getMinute() {

		for (int i = 0; i <= 59; i++) {
			if (i < 10) {
				minutes[i] = "0" + i;
			} else {
				minutes[i] = i + "";
			}
		}

	}

		public String[] getperDay(){
			int totaldays = getDaysByYearMonth(year,month);
			String[] days = new String[totaldays];
			for (int i = 0; i < totaldays; i++) {
				days[i] = (i+1)+"";
			}
			return days;
		}
		 /**
	     * 根据年 月 获取对应的月份 天数
	     * */
	    public int getDaysByYearMonth(String years, String months) {
	    	int year = Integer.valueOf(years);
	    	int month = Integer.valueOf(months);
	        Calendar a = Calendar.getInstance();
	        a.set(Calendar.YEAR, year);
	        a.set(Calendar.MONTH, month - 1);
	        a.set(Calendar.DATE, 1);
	        a.roll(Calendar.DATE, -1);
	        int maxDate = a.get(Calendar.DATE);
	        return maxDate;
	    }
		///////////////////////////end



	/**
	 * 选择日期和时间
	 */
	public Dialog showDateorTimePic(final Context context, final Handler handler, final int code) {
		this.context = context;
		final Dialog dlg = new Dialog(context, R.style.ActionSheet);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.pick_dateortime, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		mViewProvince = (WheelView) layout.findViewById(R.id.id_province);
		mViewCity = (WheelView) layout.findViewById(R.id.id_city);
		mViewDistrict = (WheelView) layout.findViewById(R.id.id_district);
		mViewHour = (WheelView) layout.findViewById(R.id.id_hour);
		mViewMinute = (WheelView) layout.findViewById(R.id.id_minute);

		mBtnConfirm = (TextView) layout.findViewById(R.id.btn_confirm);

		mViewProvince.addChangingListener(this);
		mViewCity.addChangingListener(this);
		mViewDistrict.addChangingListener(this);
		mViewHour.addChangingListener(this);
		mViewMinute.addChangingListener(this);
		mBtnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//time.setText(year+"-"+month+"-"+day);
				Message msg = new Message();
				msg.what = code;
				msg.obj = year + "." + month + "." + day + " " + hour + ":" + minute;
				handler.sendMessage(msg);
				dlg.dismiss();

			}
		});

//	    	initProvinceDatas(context);
		getDate();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(context, mYears));
		getHours();
		getMinute();
		mViewHour.setViewAdapter(new ArrayWheelAdapter<String>(context, hours));
		mViewMinute.setViewAdapter(new ArrayWheelAdapter<String>(context, minutes));
		// 设置可见条目数量
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		mViewHour.setVisibleItems(7);
		mViewMinute.setVisibleItems(7);

//			Calendar c = Calendar.getInstance();
//			tyear = c.get(Calendar.YEAR);
//			tmonth = c.get(Calendar.MONTH);
//			tm = tyear - 2014;

		c = Calendar.getInstance();
		int years = c.get(Calendar.YEAR);
		int months = c.get(Calendar.MONTH);
		int days = c.get(Calendar.DAY_OF_MONTH) - 1;
		int inthour = c.get(Calendar.HOUR_OF_DAY);
		int intminutes = c.get(Calendar.MINUTE);
		mViewProvince.setCurrentItem(102);
		mViewCity.setCurrentItem(months);
		mViewDistrict.setCurrentItem(days);
		mViewHour.setCurrentItem(inthour);
		mViewMinute.setCurrentItem(intminutes);

		updateCities();
//			updateAreas();

		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		final int cMakeBottom = -1000;
		lp.y = cMakeBottom;
		lp.gravity = Gravity.BOTTOM;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(false);
		dlg.setContentView(layout);
		dlg.show();

		return dlg;
	}
}
