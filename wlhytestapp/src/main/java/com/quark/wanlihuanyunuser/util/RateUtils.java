package com.quark.wanlihuanyunuser.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 *
 * 调用聚合数据 获取实时汇率
 *
 */
public class RateUtils {
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	public static String APPKEY = "92d3192422992f1aee68e1ec6042a8e7";
	public static String url = "http://op.juhe.cn/onebox/exchange/currency";//请求接口地址
	/**
	 * @param strUrl 请求地址
	 * @param params 请求参数
	 * @param method 请求方法
	 * @return 网络请求字符串
	 * @throws Exception
	 */
	public static String net(String strUrl, Map params, String method) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if (method == null || method.equals("GET")) {
				strUrl = strUrl + "?" + urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if (method == null || method.equals("GET")) {
				conn.setRequestMethod("GET");
			} else {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params != null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(urlencode(params));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	//将map型转为请求参数型
	public static String urlencode(Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static String types = "{\n" +
			"    \"reason\": \"查询成功\",\n" +
			"    \"result\": {\n" +
			"        \"list\": [\n" +
			"            {\n" +
			"                \"name\": \"人民币\",\n" +
			"                \"code\": \"CNY\"\n" +
			"            },\n" +
			"            {\n" +
			"                \"name\": \"美元\",\n" +
			"                \"code\": \"USD\"\n" +
			"            },\n" +
			"            {\n" +
			"                \"name\": \"欧元\",\n" +
			"                \"code\": \"EUR\"\n" +
			"            },\n" +
			"            {\n" +
			"                \"name\": \"港币\",\n" +
			"                \"code\": \"HKD\"\n" +
			"            },\n" +
			"            {\n" +
			"                \"name\": \"澳大利亚元\",\n" +
			"                \"code\": \"AUD\"\n" +
			"            },\n" +
			"            {\n" +
			"                \"name\": \"新台币\",\n" +
			"                \"code\": \"TWD\"\n" +
			"            },\n" +
			"            {\n" +
			"                \"name\": \"新西兰元\",\n" +
			"                \"code\": \"NZD\"\n" +
			"            },\n" +
			"            {\n" +
			"                \"name\": \"新加坡元\",\n" +
			"                \"code\": \"SGD\"\n" +
			"            }\n" +
			"        ]\n" +
			"    },\n" +
			"    \"error_code\": 0\n" +
			"}";

// 		public static String types = "{\n" +
//			"\t\"reason\":\"查询成功\",\n" +
//			"\t\"result\":{\n" +
//			"\t\t\"list\":[\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"人民币\",\n" +
//			"\t\t\t\t\"code\":\"CNY\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"美元\",\n" +
//			"\t\t\t\t\"code\":\"USD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"日元\",\n" +
//			"\t\t\t\t\"code\":\"JPY\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"欧元\",\n" +
//			"\t\t\t\t\"code\":\"EUR\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"英镑\",\n" +
//			"\t\t\t\t\"code\":\"GBP\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"韩元\",\n" +
//			"\t\t\t\t\"code\":\"KRW\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"港币\",\n" +
//			"\t\t\t\t\"code\":\"HKD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"澳大利亚元\",\n" +
//			"\t\t\t\t\"code\":\"AUD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"加拿大元\",\n" +
//			"\t\t\t\t\"code\":\"CAD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"阿尔及利亚第纳尔\",\n" +
//			"\t\t\t\t\"code\":\"DZD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"阿根廷比索\",\n" +
//			"\t\t\t\t\"code\":\"ARS\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"埃及镑\",\n" +
//			"\t\t\t\t\"code\":\"EGP\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"阿联酋迪拉姆\",\n" +
//			"\t\t\t\t\"code\":\"AED\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"阿曼里亚尔\",\n" +
//			"\t\t\t\t\"code\":\"OMR\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"澳大利亚元\",\n" +
//			"\t\t\t\t\"code\":\"AUD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"澳门元\",\n" +
//			"\t\t\t\t\"code\":\"MOP\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"白俄罗斯卢布\",\n" +
//			"\t\t\t\t\"code\":\"BYR\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"巴林第纳尔\",\n" +
//			"\t\t\t\t\"code\":\"BHD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"保加利亚列弗\",\n" +
//			"\t\t\t\t\"code\":\"BGN\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"巴西雷亚尔\",\n" +
//			"\t\t\t\t\"code\":\"BRL\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"冰岛克朗\",\n" +
//			"\t\t\t\t\"code\":\"ISK\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"波兰兹罗提\",\n" +
//			"\t\t\t\t\"code\":\"PLN\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"丹麦克朗\",\n" +
//			"\t\t\t\t\"code\":\"DKK\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"俄罗斯卢布\",\n" +
//			"\t\t\t\t\"code\":\"RUB\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"菲律宾比索\",\n" +
//			"\t\t\t\t\"code\":\"PHP\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"港币\",\n" +
//			"\t\t\t\t\"code\":\"HKD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"哥伦比亚比索\",\n" +
//			"\t\t\t\t\"code\":\"COP\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"哥斯达黎加科朗\",\n" +
//			"\t\t\t\t\"code\":\"CRC\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"韩元\",\n" +
//			"\t\t\t\t\"code\":\"KRW\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"加拿大元\",\n" +
//			"\t\t\t\t\"code\":\"CAD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"柬埔寨瑞尔\",\n" +
//			"\t\t\t\t\"code\":\"KHR\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"捷克克朗\",\n" +
//			"\t\t\t\t\"code\":\"CZK\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"卡塔尔里亚尔\",\n" +
//			"\t\t\t\t\"code\":\"QAR\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"克罗地亚库纳\",\n" +
//			"\t\t\t\t\"code\":\"HRK\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"肯尼亚先令\",\n" +
//			"\t\t\t\t\"code\":\"KES\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"科威特第纳尔\",\n" +
//			"\t\t\t\t\"code\":\"KWD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"老挝基普\",\n" +
//			"\t\t\t\t\"code\":\"LAK\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"离岸人民币\",\n" +
//			"\t\t\t\t\"code\":\"CNH\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"黎巴嫩镑\",\n" +
//			"\t\t\t\t\"code\":\"LBP\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"林吉特\",\n" +
//			"\t\t\t\t\"code\":\"MYR\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"罗马尼亚列伊\",\n" +
//			"\t\t\t\t\"code\":\"RON\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"美元\",\n" +
//			"\t\t\t\t\"code\":\"USD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"缅甸元\",\n" +
//			"\t\t\t\t\"code\":\"BUK\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"摩洛哥道拉姆\",\n" +
//			"\t\t\t\t\"code\":\"MAD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"墨西哥元\",\n" +
//			"\t\t\t\t\"code\":\"MXN\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"南非兰特\",\n" +
//			"\t\t\t\t\"code\":\"ZAR\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"挪威克朗\",\n" +
//			"\t\t\t\t\"code\":\"NOK\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"欧元\",\n" +
//			"\t\t\t\t\"code\":\"EUR\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"人民币\",\n" +
//			"\t\t\t\t\"code\":\"CNY\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"日元\",\n" +
//			"\t\t\t\t\"code\":\"JPY\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"瑞典克朗\",\n" +
//			"\t\t\t\t\"code\":\"SEK\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"瑞士法郎\",\n" +
//			"\t\t\t\t\"code\":\"CHF\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"塞尔维亚第纳尔\",\n" +
//			"\t\t\t\t\"code\":\"RSD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"沙特里亚尔\",\n" +
//			"\t\t\t\t\"code\":\"SAR\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"斯里兰卡卢比\",\n" +
//			"\t\t\t\t\"code\":\"LKR\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"泰国铢\",\n" +
//			"\t\t\t\t\"code\":\"THB\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"坦桑尼亚先令\",\n" +
//			"\t\t\t\t\"code\":\"TZS\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"文莱元\",\n" +
//			"\t\t\t\t\"code\":\"BND\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"乌干达先令\",\n" +
//			"\t\t\t\t\"code\":\"UGX\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"新的赞比亚克瓦查\",\n" +
//			"\t\t\t\t\"code\":\"ZMK\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"新加坡元\",\n" +
//			"\t\t\t\t\"code\":\"SGD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"新台币\",\n" +
//			"\t\t\t\t\"code\":\"TWD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"新土耳其里拉\",\n" +
//			"\t\t\t\t\"code\":\"TRY\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"新西兰元\",\n" +
//			"\t\t\t\t\"code\":\"NZD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"匈牙利福林\",\n" +
//			"\t\t\t\t\"code\":\"HUF\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"叙利亚镑\",\n" +
//			"\t\t\t\t\"code\":\"SYP\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"伊拉克第纳尔\",\n" +
//			"\t\t\t\t\"code\":\"IQD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"印度卢比\",\n" +
//			"\t\t\t\t\"code\":\"INR\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"英镑\",\n" +
//			"\t\t\t\t\"code\":\"GBP\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"印尼卢比\",\n" +
//			"\t\t\t\t\"code\":\"IDR\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"以色列新锡克尔\",\n" +
//			"\t\t\t\t\"code\":\"ILS\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"约旦第纳尔\",\n" +
//			"\t\t\t\t\"code\":\"JOD\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"越南盾\",\n" +
//			"\t\t\t\t\"code\":\"VND\"\n" +
//			"\t\t\t},\n" +
//			"\t\t\t{\n" +
//			"\t\t\t\t\"name\":\"智利比索\",\n" +
//			"\t\t\t\t\"code\":\"CLP\"\n" +
//			"\t\t\t}\n" +
//			"\t\t]\n" +
//			"\t},\n" +
//			"\t\"error_code\":0\n" +
//			"}";
}
