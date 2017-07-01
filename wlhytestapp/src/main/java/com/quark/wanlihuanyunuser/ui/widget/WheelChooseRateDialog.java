package com.quark.wanlihuanyunuser.ui.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quark.api.auto.bean.RateBean;
import com.quark.wanlihuanyunuser.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
/**
* @ClassName:   WheelChooseDialog
* @Description: 只要省市
* @author       lihao
* @date         2015-4-3 下午8:16:48
* @version      V1.0
*/
@SuppressLint("NewApi")
public class WheelChooseRateDialog implements View.OnClickListener, OnWheelChangedListener {
	public String city;
	private static WheelView mViewProvince;
	private static WheelView mViewCity;
	private static TextView mBtnConfirm;
	public Context context;
	Handler handler;
	/**
	 * 所有省
	 */
	//货币符号：中国(CNY)、美国(USD)、韩国(KRW)、日本(JPY)
//	String[] mProvinceDatas = {"CNY","USD","KRW","JPY"};
	String[] mProvinceDatas;
	/**
	 * key - 省 value - 市
	 */
	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 市 values - 区
	 */
	protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

	/**
	 * key - 区 values - 邮编
	 */
	protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

	/**
	 * 当前省的名称
	 */
	protected String mCurrentProviceName="";
	/**
	 * 当前市的名称
	 */
	protected String mCurrentCityName;
	/**
	 * 当前区的名称
	 */
	protected String mCurrentDistrictName ="";

	/**
	 * 当前区的邮政编码
	 */
	protected String mCurrentZipCode ="";

    int newValue;
	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == mViewProvince) {
            this.newValue = newValue;
			mCurrentProviceName = mProvinceDatas[newValue];
		}
	}

	@Override
	public void onClick(View v) {
//				showSelectedResult();
	}

	//显示底部 层
	public Dialog showSheetPic(final Context context,final Handler handler,final int code, List<RateBean> rateTypes,String initdata,int position) {
		this.context = context;
//		mCurrentProviceName = initdata;
//		mProvinceDatas = datas;
		mProvinceDatas = new String[rateTypes.size()];
			for (int i = 0; i < rateTypes.size(); i++) {
				mProvinceDatas[i] = rateTypes.get(i).getName();
			}

		final Dialog dlg = new Dialog(context, R.style.ActionSheet);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.pick_moneytype, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		mViewProvince = (WheelView) layout.findViewById(R.id.id_province);

		mBtnConfirm = (TextView) layout.findViewById(R.id.btn_confirm);

		// 添加change事件
		mViewProvince.addChangingListener(this);
		// 添加change事件
		// 添加onclick事件
		mBtnConfirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = code;
				msg.arg1 = newValue;
				handler.sendMessage(msg);

				dlg.dismiss();
			}
		});

		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(context, mProvinceDatas));
		// 设置可见条目数量
		mViewProvince.setVisibleItems(7);

		Calendar c = Calendar.getInstance();
		int tyear = c.get(Calendar.YEAR);
		int tmonth = c.get(Calendar.MONTH);
//		int tm = 2116-tyear;
//		System.out.println("============================" + tm + "===" + tmonth);
//		mViewProvince.setCurrentItem(position);


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

	///////////////////////////end
}
