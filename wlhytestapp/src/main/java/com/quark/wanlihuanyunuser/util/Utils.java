package com.quark.wanlihuanyunuser.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	/**   
	* @Description: 手机号码验证  手机号码 固话
	* @author howe   
	* @date 2014-7-22 下午4:00:37 
	*  
	*/
	
	public static boolean isTelephone(String mobiles){  
		if(mobiles==null||(mobiles.trim().equals(""))){
			return false;
		}
		Pattern p = Pattern.compile("(^(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7})$");  
		Matcher m = p.matcher(mobiles);
		
		Pattern pp = Pattern.compile("\\d{4}-\\d{8}|\\d{4}-\\d{7}|\\d(3)-\\d(8)");  
		Matcher mm = pp.matcher(mobiles);  
		
		if(m.matches()){  
            return true;  
        }else if(mm.matches()){
        	 return true;  
        }else{
        	return false;  
        }
	}

	/**
	 * 符合11位返回true
	 */

	public static boolean isTelephone11(String mobiles){
		if(mobiles==null||(mobiles.trim().equals(""))){
			return false;
		}
		Pattern p = Pattern.compile("^\\d{11,11}$");  
		Matcher m = p.matcher(mobiles);
		
		if(m.matches()){  
			return true;  
		}else{
			return false;  
		}
	}
	
	    /** 
	     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
	     */  
	    	
    	public static int dip2px(Context context, float dpValue) { 
    		final float scale = context.getResources().getDisplayMetrics().density ; 
    		return (int) (dpValue * scale + 0.5f) ;
		 }
	  
	    /** 
	     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
	     */  
	    public static int px2dip(Context context, float pxValue) {  
	        final float scale = context.getResources().getDisplayMetrics().density;  
	        return (int) (pxValue / scale + 0.5f);  
	    }  
	
	public static boolean isMoble(String mobiles){  
		if(mobiles==null||(mobiles.trim().equals(""))){
			return false;
		}
//		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,2-9]))\\d{8}$");  
		Pattern p = Pattern.compile("(^(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7})$");  
		Matcher m = p.matcher(mobiles);  
		return m.matches();  
	}
	
	//是否为正整数
	public static boolean isNumericZheng(String str){
		if(str==null||(str.trim().equals(""))){
			return false;
		}
		Pattern pattern = Pattern.compile("^[0-9]+\\.{0,1}[0-9]{0,2}$");
		return pattern.matcher(str).matches();   
	} 
	
	//是否为正数
	public static boolean isNumericzhang(String str){
		if(str==null||(str.trim().equals(""))){
			return false;
		}
		Pattern pattern = Pattern.compile("^[+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
		return pattern.matcher(str).matches();   
	} 
	
	//是否为数字
	public static boolean isNumeric(String str){
		if(str==null||(str.trim().equals(""))){
			return false;
		}
	    Pattern pattern = Pattern.compile("[0-9]*");
	    return pattern.matcher(str).matches();   
	} 
	
	
	//身高为140cm-220cm
//	public static boolean heightCheck(String heightStr){
//		if(heightStr==null||(heightStr.trim().equals(""))){
//			return false;
//		}
//		Pattern p = Pattern.compile("^1[4-9][0-9]|200cm\b$");  
//		Matcher m = p.matcher(heightStr);  
//		return m.matches(); 
//	}
	public static boolean heightCheck(String heightStr){
		if(heightStr==null||(heightStr.trim().equals(""))){
			return false;
		}
	    Pattern pattern = Pattern.compile("^[0-9]*$");
	    if(pattern.matcher(heightStr).matches()){
	    	int hei = Integer.valueOf(heightStr);
	    	if((hei>140)&&(hei<200)){
	    		return true;
	    	}else{
	    		return false;
	    	}
	    }else{
	    	return false;
	    }
	}


	public static boolean isUserName(String mobiles){
		Pattern p = Pattern.compile("^[A-Za-z\u4e00-\u9fa5]{6,15}$");
		Matcher m = p.matcher(mobiles);  
		return m.matches();  
	}

	/**
	 * 空返回true
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){  
		if(str!=null&&(!str.trim().equals(""))){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 检查是否存在SDCard
	 * @return
	 */
	public static boolean hasSdcard(){
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
	
	public static String DateToString(Date date){
		if(date!=null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			return sdf.format(date);
		}else{
			return "";
		}
	}
	  
	/**
	  * 通过年月日获取 年龄
	 * @param brithday
	 * @return
	 */
	public static String getCurrentAgeByBirthdate(String brithday) {
	
		 SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		  Date date = new Date();
		  Date mydate = null;
		try {
			mydate = myFormatter.parse(brithday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		  long day=(date.getTime()-mydate.getTime())/(24*60*60*1000) + 1;

		  String year = new DecimalFormat("#").format(day/365f);
		
		return year;
	 }
	
	public static boolean isIdCard(String idcard){
		//定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）  
	    Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");  
	    //通过Pattern获得Matcher  
		Matcher m = idNumPattern.matcher(idcard);  
		
		return m.matches();  
	}
	//工作内容 10-200字
	public static boolean isInfook(String str){  
		if(str!=null&&(!str.trim().equals(""))&&(str.length()>10)&&(str.length()<1000)){
			return true;
		}else{
			return false;  
		}
	}
	
	//详细地址 7-40字
	public static boolean isAddressDetail(String str){  
		if(str!=null&&(!str.trim().equals(""))&&(str.length()>6)&&(str.length()<41)){
			return true;
		}else{
			return false;  
		}
	}
	
	/**
	 * 保留两位数小数
	 * @param db
	 * @return
	 */
	public static String DoubleFormat(Double db){
		DecimalFormat df=new DecimalFormat("######0.00");
		String totalMoneyFormat = df.format(Math.abs(db));
		System.out.println("格式化 后的钱"+totalMoneyFormat);
		
		if(totalMoneyFormat.equals(".0")){//为0时会变成.0
			totalMoneyFormat = "0";
		}

		return totalMoneyFormat;
	}

	/**
	 * 保留4位数小数
	 * @param db
	 * @return
	 */
	public static String DoubleFormat4(Double db){
		DecimalFormat df=new DecimalFormat("######0.0000");
		String totalMoneyFormat = df.format(Math.abs(db));
		System.out.println("格式化 后的钱"+totalMoneyFormat);

		if(totalMoneyFormat.equals(".0")){//为0时会变成.0
			totalMoneyFormat = "0";
		}

		return totalMoneyFormat;
	}
	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Context context) {
	    try {
	        PackageManager manager = context.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
	        String version = info.versionName;
	        return "V"+version;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return " ";
	    }
	}

	/**
	 * 获取版本
	 * @return 当前应用的版本
	 */
	public static int getVersionCode(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			int version = info.versionCode;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return 100;
		}
	}
    /**
     * 
     * @Title : findYear
     * @Type : YearAndMonth
     * @date : 2014年4月3日 下午10:48:52
     * @Description : 获取年月
     * @return
     */
    public static String findCurrentYear()
    {
        int year;
        String date;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        date = year+"";
        return date;
    } 
    /**
     * 
     * @Title : findCurrentMonth
     * @Type : YearAndMonth
     * @date : 2014年4月3日 下午10:48:52
     * @Description : 获取年月
     * @return
     */
    public static String findCurrentMonth()
    {
        int month;
        String date;
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH) + 1;
        date = ""+( month<10 ? "0" + month : month);
        return date;
    }
    
    public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
	}

	/**
	 * 获得指定日期的后一天
	 *
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = new Date();
//		try {
//			date = new SimpleDateFormat("yyyy.mm.dd hh:mm").parse(specifiedDay);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy.MM.dd hh:mm").format(c.getTime());
		return dayAfter;
	}

}


