//package com.quark.kuaishi.util;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.location.Location;
//import android.os.Bundle;
//import android.os.Handler;
//import android.widget.TextView;
//
//import com.amap.api.location.AMapLocation;
//import com.amap.api.location.AMapLocationListener;
//import com.amap.api.location.LocationManagerProxy;
//import com.amap.api.location.LocationProviderProxy;
//import com.quark.locklove.AppParam;
//
///**
// * 高德地图定位
// * AMapV1中简单介绍混合定位
// */
//public class LocationNetwork  implements AMapLocationListener, Runnable {
//	private LocationManagerProxy aMapLocManager = null;
//	private AMapLocation aMapLocation;// 用于判断定位超时
//	private Handler handlerbase = new Handler();
//	Context activity;
//	TextView city;
//	public static double lng=113.867102;
//	public static double lat=22.567009;
//
//	public LocationNetwork(Context activity,TextView city){
//		this.activity = activity;
//		this.city = city;
//	}
//
//	public LocationNetwork(Context activity){
//		this.activity = activity;
//	}
//
//	public void getPosition() {
//
//		aMapLocManager = LocationManagerProxy.getInstance(activity);
//		/*
//		 * mAMapLocManager.setGpsEnable(false);//
//		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
//		 * API定位采用GPS和网络混合定位方式
//		 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
//		 */
//		aMapLocManager.requestLocationUpdates(LocationProviderProxy.AMapNetwork, 2000, 10, this);
//		handlerbase.postDelayed(this, 12000);          // 设置超过12秒还没有定位到就停止定位
//	}
//
//	/**
//	 * 销毁定位
//	 */
//	private void stopLocation() {
//		if (aMapLocManager != null) {
//			aMapLocManager.removeUpdates(this);
//			aMapLocManager.destory();
//		}
//		aMapLocManager = null;
//	}
//
//	/**
//	 * 此方法已经废弃
//	 */
//	@Override
//	public void onLocationChanged(Location location) {
//	}
//
//	@Override
//	public void onProviderDisabled(String provider) {
//
//	}
//
//	@Override
//	public void onProviderEnabled(String provider) {
//
//	}
//
//	@Override
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//
//	}
//
//	/**
//	 * 混合定位回调函数
//	 */
//	@Override
//	public void onLocationChanged(AMapLocation location) {
//		System.out.println("=======================開始定位=============================");
//		if (location != null) {
//			this.aMapLocation = location;// 判断超时机制
//			Double geoLat = location.getLatitude();
//			Double geoLng = location.getLongitude();
//			String cityCode = "";
//			String desc = "";
//			Bundle locBundle = location.getExtras();
//			if (locBundle != null) {
//				cityCode = locBundle.getString("citycode");
//				desc = locBundle.getString("desc");
//			}
//
////			String str = ("定位成功:(" + geoLng + "," + geoLat + ")"
////					+ "\n精    度    :" + location.getAccuracy() + "米"
////					+ "\n定位方式:" + location.getProvider() + "\n定位时间:"
////					+ AMapUtil.convertToTime(location.getTime()) + "\n城市编码:"
////					+ cityCode + "\n位置描述:" + desc + "\n省:"
////					+ location.getProvince() + "\n市:" + location.getCity()
////					+ "\n区(县):" + location.getDistrict() + "\n区域编码:" + location
////					.getAdCode());
////			System.out.println(str);
////			city.setText(location.getCity());
//			lng = geoLng;
//			lat = geoLat;
////			Toast.makeText(activity, "定位深圳市",0).show();
//			//使用文件保存当前位置
//		    SharedPreferences sp = activity.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, activity.MODE_PRIVATE);
//	        Editor edit = sp.edit();
//	        edit.putString("lng", geoLng+"");
//	        edit.putString("lat", geoLat+"");
//	        edit.putString("province", location.getProvince());
//	        edit.putString("district", location.getDistrict());
//	        edit.putString("city", location.getCity());
//	        edit.commit();
//
//			stopLocation();
//		}
//	}
//	@Override
//	public void run() {
//		if (aMapLocation == null) {
////			Toast.makeText(activity, "定位失败，默认定位深圳市",0).show();
//			//使用文件保存当前位置
//		    SharedPreferences sp = activity.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, activity.MODE_PRIVATE);
//	        Editor edit = sp.edit();
//	        edit.putString("province", "广东省");
//	        edit.putString("city", "深圳市");
//	        edit.putString("district", "宝安区");
//	        edit.putString("lng", lng+"");
//	        edit.putString("lat", lat+"");
//	        edit.commit();
//			stopLocation();// 销毁掉定位
//		}
//	}
//}
