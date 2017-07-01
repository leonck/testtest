package com.quark.wanlihuanyunuser;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.alibaba.fastjson.JSON;
import com.quark.api.auto.bean.LoginHistoryBean;
import com.quark.wanlihuanyunuser.util.Utils;

public class AppParam {

	public static final String app_quark_key = "2016lzewzealand_transport10181112";
	public static final String PRODUCT_ID = "PRODUCT_ID";
	public static final String CLASSIFY_TWO_ID = "CLASSIFY_TWO_ID";
	public static final String PRODUCTTYPR = "PRODUCTTYPR";
	public static final String JIFEN = "JIFEN";
	public static final String SHITI = "SHITI";
	public static final String SHOUCHENG = "SHOUCHENG";
	public static final String PREF_TEMP_IMAGES = "pref_temp_images";
	public static final String SHAREDPREFERENCESKEY = "fang.setting";
	public static String user_id;
	public static String city;
	public static String province;
	public static String district;

	public static final String user_type = "1";//用户端 2商家端

	public static boolean onlywifidownload = false;//是否只在wifi环境下下载
	private static final String SAVE_PIC_PATH = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() : "/dakatemp";//保存到SD卡
	public static final String SAVE_REAL_PATH = SAVE_PIC_PATH + "/万里环运";//保存的确切位置
	public static int picx = 850;
	public static int picy = 540;

	public  int getScreenWidth(Context context) {
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		int user_id = sp.getInt("screenWidth", 0);

		return user_id;
	}

	public  int getScreenHeight(Context context) {
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		int user_id = sp.getInt("screenHeight", 0);

		return user_id;
	}

	public String getUser_id(Context context) {
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		user_id = sp.getString("user_id", "");

		return user_id;
	}

	public String getUser_name(Context context) {
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		user_id = sp.getString("user_name", "");

		return user_id;
	}
	public String getUser_pic(Context context) {
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		user_id = sp.getString("user_pic", "");

		return user_id;
	}

	public String getInvite(Context context) {
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String invite = sp.getString("invite", "");

		return invite;
	}

	public String getCity(Context context) {
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		city = sp.getString("city", "");

		return city;
	}

	public String getProvince(Context context) {
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		province = sp.getString("province", "");

		return province;
	}


	public String getDistrict(Context context) {
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		district = sp.getString("district", "");

		return district;
	}

	public boolean isLogin(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String user_id = sp.getString("token", "");
		if("".equals(user_id)||user_id.equals(""))
			return false;
		else
			return true;
	}

	public String getType(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String type = sp.getString("type", "");

		return type;
	}

	public String getLat(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String lat = sp.getString("lat", "22.567009");

		return lat;
	}

	public String getLng(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String lng = sp.getString("lng", "113.867102");

		return lng;
	}

	public String getTelephone(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String telephone = sp.getString("telephone", "");

		return telephone;
	}

	public String getToPostTasksCount(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String toPostTasksCount = sp.getString("toPostTasksCount", "");

		return toPostTasksCount;
	}

	public String getPostingTasksCount(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String postingTasksCount = sp.getString("postingTasksCount", "");

		return postingTasksCount;
	}

	public String getPostedTasksCount(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String postedTasksCount = sp.getString("postedTasksCount", "");

		return postedTasksCount;
	}

	public String getLeft_count(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String left_count = sp.getString("left_count", "");

		return left_count;
	}

	public String getZ_history_count(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String z_history_count = sp.getString("z_history_count", "");

		return z_history_count;
	}

	public String getY_history_count(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String y_history_count = sp.getString("y_history_count", "");

		return y_history_count;
	}

	public String getQ_history_count(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String q_history_count = sp.getString("q_history_count", "");

		return q_history_count;
	}

	public String getCompany_id(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String company_id = sp.getString("company_id", "");

		return company_id;
	}

	public String getToken(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String token = sp.getString("token", "1001");

		return token;
	}


	public boolean isPoster(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String poster = sp.getString("is_poster", "0");
		if(poster.equals("1")){
			return true;
		}else{
			return false;
		}
	}

	//获取屏幕分辨率
	public String getResolution(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String resolution = sp.getString("resolution", "");

		return resolution;
	}

	//系统版本
	public String getSysVesion(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String resolution = sp.getString("sysvesion", "");

		return resolution;
	}

	//
	public String getPhone(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String phone = sp.getString("phone", "");

		return phone;
	}
	//
	public String getLanguage(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String language = sp.getString("language", "");

		return language;
	}

	public String getIs_join_ranking(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String language = sp.getString("is_join_ranking", "0");

		return language;
	}
	public String getis_perfect(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String language = sp.getString("is_perfect", "0");

		return language;
	}

	public int getShare_say_love_count(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		int language = sp.getInt("share_say_love_count", 0);
		return language;
	}
	public int getShare_lovers_count(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		int language = sp.getInt("share_lovers_count", 0);
		return language;
	}
	public int getShare_crush_count(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		int language = sp.getInt("share_crush_count", 0);
		return language;
	}

	public String getLove_user_id(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String language = sp.getString("love_user_id", "0");
		return language;
	}

	public String getLogin_id(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String language = sp.getString("login_id", "");
		return language;
	}

	public String getLogin_pwd(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String language = sp.getString("login_pwd", "");
		return language;
	}

	public String getLiaotian_user_id(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String liaotian_user_id = sp.getString("liaotian_user_id", "0");
		return liaotian_user_id;
	}
	public String getpassword(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String liaotian_user_id = sp.getString("password", "0");
		return liaotian_user_id;
	}
	public String getEmail(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String liaotian_user_id = sp.getString("email", "0");
		return liaotian_user_id;
	}

	public String getPayId(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String liaotian_user_id = sp.getString("pay_id", "");
		return liaotian_user_id;
	}


	public void setSharedPreferencesy(Context context,String key,String values){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		edit.putString(key, values);
		edit.commit();
	}

	public String getBannarjson(Context context){
		String datas = "";
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		if (sp.contains("bannarjson")){
			datas = sp.getString("bannarjson", "");
		}
		return datas;
	}

	public LoginHistoryBean getLoginHistory() {
		SharedPreferences sp = AppContext.instance.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, AppContext.instance.MODE_PRIVATE);
		String historyjson = sp.getString("historyjson", "");
		if (Utils.isEmpty(historyjson)){
			return null;
		}
		LoginHistoryBean baseeqpm = JSON.parseObject(historyjson, LoginHistoryBean.class);
		return baseeqpm;
	}

	//是否设置了交易密码 是否设置交易密码：0-否，1-已
	public boolean getIs_set_trade_pwd(Context context){
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		String liaotian_user_id = sp.getString("is_set_trade_pwd", "");
		if ("0".equals(liaotian_user_id)){
			return false;
		}else{
			return true;
		}
	}

	public void setNewSharedPreferencesy(Context context,String key,String values){
		new AppParam().setSharedPreferencesy(context, key, "");
		SharedPreferences sp = context.getSharedPreferences(AppParam.SHAREDPREFERENCESKEY, context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		edit.putString(key, values);
		edit.commit();
	}

}




