package com.quark.wanlihuanyunuser.api;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.util.TLog;

import org.kymjs.kjframe.Core;
import org.kymjs.kjframe.bitmap.BitmapCallBack;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

import cz.msebera.android.httpclient.client.params.ClientPNames;

public class ApiHttpClient {

//    public final static String HOST = "120.76.137.59:8089";
//    private static String API_URL = "http://120.76.137.59:8089/%s";
//    public static String HOSTURL = "http://120.76.137.59:8089";
    public final static String HOST = "222.73.135.228:8080";
    private static String API_URL = "http://222.73.135.228:8080/%s";
    public static String HOSTURL = "http://222.73.135.228:8080";

//    http://hey.kksdapp.com:8080/
//    public final static String HOST = "hey.kksdapp.com";
//    private static String API_URL = "http://hey.kksdapp.com:8080/%s";
//    public static String HOSTURL = "http://hey.kksdapp.com:8080";

//    public final static String HOST = "113.59.82.41:8080";
//    private static String API_URL = "http://113.59.82.41:8080/ocean_lite/%s";
//    public static String HOSTURL = "http://113.59.82.41:8080/ocean_lite";

    public static final String loadImage = HOSTURL+"/files/image?name=";
    public static final String loadImagePio = HOSTURL+"/files/oceanImg?name=";

    public static final String DELETE = "DELETE";
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static AsyncHttpClient client;

    public static final String  aliPayAysn = HOSTURL+"/app/Pays/aliPayAysn";
    public static final String  h5url = HOSTURL + "/app/Informations/InformationH5";
    public ApiHttpClient() {
    }

    public static AsyncHttpClient getHttpClient() {
        return client;
    }

    public static void cancelAll(Context context) {
        client.cancelRequests(context, true);
    }

    public static void clearUserCookies(Context context) {
        // (new HttpClientCookieStore(context)).a();
    }

    public static void delete(String partUrl, AsyncHttpResponseHandler handler) {
        client.delete(getAbsoluteApiUrl(partUrl), handler);
        log(new StringBuilder("DELETE ").append(partUrl).toString());
    }

    public static void get(String partUrl, AsyncHttpResponseHandler handler) {
        client.get(getAbsoluteApiUrl(partUrl), handler);
        log(new StringBuilder("GET ").append(partUrl).toString());
    }

    public static void get(String partUrl, RequestParams params, AsyncHttpResponseHandler handler) {
        String url = getAbsoluteApiUrl(partUrl);
        client.setTimeout(30000);
        client.get(url, params, handler);
        Log.d("DebugError", new StringBuilder("").append(url).append("?").append(params).toString());
        log(new StringBuilder("GET ").append(partUrl).append("&").append(params).toString());
    }

    public static String getAbsoluteApiUrl(String partUrl) {
        String url = partUrl;
        if (!partUrl.startsWith("http:") && !partUrl.startsWith("https:")) {
            url = String.format(API_URL, partUrl);
        }
        return url;
    }

    public static String getApiUrl() {
        return API_URL;
    }

    public static void getDirect(String url, AsyncHttpResponseHandler handler) {
        client.get(url, handler);
        log(new StringBuilder("GET ").append(url).toString());
    }

    public static void log(String log) {
        Log.d("BaseApi", log);
        TLog.log("Test", log);
    }

    public static void post(String partUrl, AsyncHttpResponseHandler handler) {
        client.post(getAbsoluteApiUrl(partUrl), handler);
        log(new StringBuilder("POST ").append(partUrl).toString());
    }

    public static void post(String partUrl, RequestParams params,
                            AsyncHttpResponseHandler handler) {
        client.post(getAbsoluteApiUrl(partUrl), params, handler);
        log(new StringBuilder("POST ").append(partUrl).append("&")
                .append(params).toString());
    }

    public static void postDirect(String url, RequestParams params,
                                  AsyncHttpResponseHandler handler) {
        client.post(url, params, handler);
        log(new StringBuilder("POST ").append(url).append("&").append(params)
                .toString());
    }

    public static void put(String partUrl, AsyncHttpResponseHandler handler) {
        client.put(getAbsoluteApiUrl(partUrl), handler);
        log(new StringBuilder("PUT ").append(partUrl).toString());
    }

    public static void put(String partUrl, RequestParams params,
                           AsyncHttpResponseHandler handler) {
        client.put(getAbsoluteApiUrl(partUrl), params, handler);
        log(new StringBuilder("PUT ").append(partUrl).append("&")
                .append(params).toString());
    }

    public static void setApiUrl(String apiUrl) {
        API_URL = apiUrl;
    }

    public static void setHttpClient(AsyncHttpClient c) {
        client = c;
        client.addHeader("Accept-Language", Locale.getDefault().toString());
        client.addHeader("Host", HOST);
        client.addHeader("Connection", "Keep-Alive");
        client.getHttpClient().getParams()
                .setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);

        setUserAgent(ApiClientHelper.getUserAgent(AppContext.getInstance()));
    }

    public static void setUserAgent(String userAgent) {
        client.setUserAgent(userAgent);
    }

    public static void setCookie(String cookie) {
        client.addHeader("Cookie", cookie);
    }

    private static String appCookie;

    public static void cleanCookie() {
        appCookie = "";
    }

    public static String getCookie(AppContext appContext) {
        if (appCookie == null || appCookie == "") {
            appCookie = appContext.getProperty("cookie");
        }
        return appCookie;
    }

    public static void loadImage(String cpic,ImageView img){
        if((cpic!=null)&&(!cpic.equals(""))){
            try {
                cpic = URLEncoder.encode(cpic, "UTF-8");
                String url = ApiHttpClient.loadImage + cpic;
                new Core.Builder().view(img).url(url)
                        .loadBitmap(new ColorDrawable(0x000000))
                        .errorBitmap(new ColorDrawable(0x000000))
                        .size(0, 0)
                        .bitmapCallBack(new BitmapCallBack() {
                            @Override
                            public void onPreLoad() {
//										bar.setVisibility(View.VISIBLE);
                            }
                            @Override
                            public void onFinish() {
//										bar.setVisibility(View.GONE);
                            }
                            @Override
                            public void onFailure(Exception arg0) {
//										AppContext.showToast(R.string.tip_load_image_faile);
                            }
                        }).doTask();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadImage2(String cpic, ImageView img) {
        if ((cpic != null) && (!cpic.equals(""))) {
            new Core.Builder().view(img).url(cpic)
                    .loadBitmap(new ColorDrawable(0x000000))
                    .errorBitmap(new ColorDrawable(0x000000))
                    .size(0, 0)
                    .bitmapCallBack(new BitmapCallBack() {
                        @Override
                        public void onPreLoad() {
//										bar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFinish() {
//										bar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Exception arg0) {
//										AppContext.showToast(R.string.tip_load_image_faile);
                        }
                    }).doTask();
        }
    }

    public static void loadImage(String cpic, final ImageView img, final int defaultpic){
        if((cpic!=null)&&(!cpic.equals(""))){
            try {
                cpic = URLEncoder.encode(cpic, "UTF-8");
                String url = ApiHttpClient.loadImage + cpic;
                new Core.Builder().view(img).url(url)
                        .loadBitmap(new ColorDrawable(0x000000))
                        .errorBitmap(new ColorDrawable(0x000000))
                        .size(0, 0)
                        .bitmapCallBack(new BitmapCallBack() {
                            @Override
                            public void onPreLoad() {
//										bar.setVisibility(View.VISIBLE);
//
                            }
                            @Override
                            public void onFinish() {
//										bar.setVisibility(View.GONE);
                            }
                            @Override
                            public void onFailure(Exception arg0) {
//										AppContext.showToast(R.string.tip_load_image_faile);
                                img.setImageResource(defaultpic);
                            }
                        }).doTask();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadImagePio(String cpic, final ImageView img, final int defaultpic){
        if((cpic!=null)&&(!cpic.equals(""))){
            try {
                cpic = URLEncoder.encode(cpic, "UTF-8");
                String url = ApiHttpClient.loadImagePio + cpic;
                new Core.Builder().view(img).url(url)
                        .loadBitmap(new ColorDrawable(0x000000))
                        .errorBitmap(new ColorDrawable(0x000000))
                        .size(0, 0)
                        .bitmapCallBack(new BitmapCallBack() {
                            @Override
                            public void onPreLoad() {
//										bar.setVisibility(View.VISIBLE);
//
                            }
                            @Override
                            public void onFinish() {
//										bar.setVisibility(View.GONE);
                            }
                            @Override
                            public void onFailure(Exception arg0) {
//										AppContext.showToast(R.string.tip_load_image_faile);
                                img.setImageResource(defaultpic);
                            }
                        }).doTask();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
//    public static void loadImagePio(String cpic, final ImageView img, final int defaultpic,int maxwithd,int maxheight){
//        if((cpic!=null)&&(!cpic.equals(""))){
//            try {
//                cpic = URLEncoder.encode(cpic, "UTF-8");
//                String url = ApiHttpClient.loadImagePio + cpic;
//                new Core.Builder().view(img).url(url)
//                        .loadBitmap(new ColorDrawable(0x000000))
//                        .errorBitmap(new ColorDrawable(0x000000))
//                        .size(maxwithd, maxheight)
//                        .bitmapCallBack(new BitmapCallBack() {
//                            @Override
//                            public void onPreLoad() {
////										bar.setVisibility(View.VISIBLE);
////
//                            }
//                            @Override
//                            public void onFinish() {
////										bar.setVisibility(View.GONE);
//                            }
//                            @Override
//                            public void onFailure(Exception arg0) {
////										AppContext.showToast(R.string.tip_load_image_faile);
//                                img.setImageResource(defaultpic);
//                            }
//                        }).doTask();
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    public static void loadImagehaspre(String cpic, final ImageView img, final int defaultpic){
        if((cpic!=null)&&(!cpic.equals(""))){
            try {
                cpic = URLEncoder.encode(cpic, "UTF-8");
                String url = ApiHttpClient.loadImage + cpic;
                new Core.Builder().view(img).url(url)
                        .loadBitmap(new ColorDrawable(0x000000))
                        .errorBitmap(new ColorDrawable(0x000000))
                        .size(0, 0)
                        .bitmapCallBack(new BitmapCallBack() {
                            @Override
                            public void onPreLoad() {
//										bar.setVisibility(View.VISIBLE);
                                img.setImageResource(defaultpic);
                            }
                            @Override
                            public void onFinish() {
//										bar.setVisibility(View.GONE);
                            }
                            @Override
                            public void onFailure(Exception arg0) {
//										AppContext.showToast(R.string.tip_load_image_faile);
                                img.setImageResource(defaultpic);
                            }
                        }).doTask();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
