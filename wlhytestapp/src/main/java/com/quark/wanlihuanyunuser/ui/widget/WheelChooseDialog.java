

package com.quark.wanlihuanyunuser.ui.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mrwujay.cascade.model.CityModel;
import com.mrwujay.cascade.model.DistrictModel;
import com.mrwujay.cascade.model.ProvinceModel;
import com.mrwujay.cascade.service.XmlParserHandler;
import com.quark.wanlihuanyunuser.R;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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
public class WheelChooseDialog implements View.OnClickListener, OnWheelChangedListener {
	public String city;
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private static TextView mBtnConfirm;
	public Context context;

	String country;//China  or NewZealand
	/**
	 * 所有省
	 */
	protected String[] mProvinceDatas;
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
	protected String mCurrentProviceName;
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

	/**
	 * 解析省市区的XML数据
	 */

    protected void initProvinceDatas(Context context)
	{
		List<ProvinceModel> provinceList = null;
    	AssetManager asset = context.getAssets();
        try {
			InputStream input;
			if ("NewZealand".equals(country)){
				input = asset.open("NewZealand_address_data.xml");
			}else {
				input = asset.open("province_data.xml");
			}

            // 创建一个解析xml的工厂对象
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// 解析xml
			SAXParser parser = spf.newSAXParser();
			XmlParserHandler handler = new XmlParserHandler();
			parser.parse(input, handler);
			input.close();
			// 获取解析出来的数据
			provinceList = handler.getDataList();
			//*/ 初始化默认选中的省、市、区
			if (provinceList!= null && !provinceList.isEmpty()) {
				mCurrentProviceName = provinceList.get(0).getName();
				List<CityModel> cityList = provinceList.get(0).getCityList();
				if (cityList!= null && !cityList.isEmpty()) {
					mCurrentCityName = cityList.get(0).getName();
					List<DistrictModel> districtList = cityList.get(0).getDistrictList();
					mCurrentDistrictName = districtList.get(0).getName();
					mCurrentZipCode = districtList.get(0).getZipcode();
				}
			}
			//*/
			mProvinceDatas = new String[provinceList.size()];
        	for (int i=0; i< provinceList.size(); i++) {
        		// 遍历所有省的数据
        		mProvinceDatas[i] = provinceList.get(i).getName();
        		List<CityModel> cityList = provinceList.get(i).getCityList();
        		String[] cityNames = new String[cityList.size()];
        		for (int j=0; j< cityList.size(); j++) {
        			// 遍历省下面的所有市的数据
        			cityNames[j] = cityList.get(j).getName();
        			List<DistrictModel> districtList = cityList.get(j).getDistrictList();
        			String[] distrinctNameArray = new String[districtList.size()];
        			DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
        			for (int k=0; k<districtList.size(); k++) {
        				// 遍历市下面所有区/县的数据
        				DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
        				// 区/县对于的邮编，保存到mZipcodeDatasMap
        				mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
        				distrinctArray[k] = districtModel;
        				distrinctNameArray[k] = districtModel.getName();
        			}
        			// 市-区/县的数据，保存到mDistrictDatasMap
        			mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
        		}
        		// 省-市的数据，保存到mCitisDatasMap
        		mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
        	}
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
	}

	 //=========================show city=================
	 @Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			if (wheel == mViewProvince) {
				updateCities();
			} else if (wheel == mViewCity) {
				updateAreas();
			}
			else if (wheel == mViewDistrict) {
				mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
				mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
			}
		}

		/**
		 * 根据当前的市，更新区WheelView的信息
		 */
		private void updateAreas() {
			int pCurrent = mViewCity.getCurrentItem();
			mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
			String[] areas = mDistrictDatasMap.get(mCurrentCityName);

			if (areas == null) {
				areas = new String[] { "" };
				mCurrentDistrictName = "";
			}else{
				mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
			}
			mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(context, areas));
			mViewDistrict.setCurrentItem(0);
		}

		/**
		 * 根据当前的省，更新市WheelView的信息
		 */
		private void updateCities() {
			int pCurrent = mViewProvince.getCurrentItem();
			mCurrentProviceName = mProvinceDatas[pCurrent];
			String[] cities = mCitisDatasMap.get(mCurrentProviceName);
			if (cities == null) {
				cities = new String[] { "" };
			}
			mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(context, cities));
			mViewCity.setCurrentItem(0);
			updateAreas();
		}

		@Override
		public void onClick(View v) {
//				showSelectedResult();
		}

		//显示底部 层
		public Dialog showSheetPic(final Context context, final android.os.Handler handler,String country) {
			this.context = context;
            this.country = country;
			final Dialog dlg = new Dialog(context, R.style.ActionSheet);
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.pick_shengshi, null);
			final int cFullFillWidth = 10000;
			layout.setMinimumWidth(cFullFillWidth);

			mViewProvince = (WheelView) layout.findViewById(R.id.id_province);
			mViewCity = (WheelView) layout.findViewById(R.id.id_city);
			mViewDistrict = (WheelView) layout.findViewById(R.id.id_district);

			mBtnConfirm = (TextView) layout.findViewById(R.id.btn_confirm);

			// 添加change事件
	    	mViewProvince.addChangingListener(this);
	    	// 添加change事件
	    	mViewCity.addChangingListener(this);
			// 添加change事件
			mViewDistrict.addChangingListener(this);
			// 添加onclick事件
	    	// 添加onclick事件
	    	mBtnConfirm.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println(mCurrentProviceName+" "+mCurrentCityName+" "+mCurrentDistrictName);

					SharedPreferences sp = context.getSharedPreferences("youyi.setting", context.MODE_PRIVATE);
					SharedPreferences.Editor edit = sp.edit();
					edit.putString("province", mCurrentProviceName);
					edit.putString("city", mCurrentCityName);
					edit.putString("district", mCurrentDistrictName);
					edit.commit();

					Message msg = new Message();
					msg.what = 201;
					msg.obj = mCurrentProviceName+","+mCurrentCityName+","+mCurrentDistrictName;
					handler.sendMessage(msg);

//					cityView.setText(mCurrentCityName);
//					Toast.makeText(AddaddressActivity.this, "当前选中:"+mCurrentProviceName+","+mCurrentCityName+","+mCurrentDistrictName+","+mCurrentZipCode, Toast.LENGTH_SHORT).show();
					dlg.dismiss();
				}
			});

	    	initProvinceDatas(context);
			mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(context, mProvinceDatas));
			// 设置可见条目数量
			mViewProvince.setVisibleItems(7);
			mViewCity.setVisibleItems(7);
			mViewDistrict.setVisibleItems(7);
			updateCities();
			updateAreas();

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
