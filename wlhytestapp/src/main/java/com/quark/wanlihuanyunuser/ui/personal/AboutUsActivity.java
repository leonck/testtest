package com.quark.wanlihuanyunuser.ui.personal;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.api.ApiHttpClient;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.util.TLog;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#关于我们
 */
public class AboutUsActivity extends BaseActivity {


    WebView webView;
    WebSettings webSettings;
    String type = "1";//1-关于我们，2-公司介绍
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        setTopTitle("关于我们");
        setBackButton();

        webView = (WebView) findViewById(R.id.web);
        webSettings = webView.getSettings();

        String url = ApiHttpClient.HOSTURL + "/app/CommonManage/aboutUsH5?type=" + type;
        showWebInfo(url);
        TLog.error(url+"地址");
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public void showWebInfo(String url) {
        webView.getSettings().setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);//自动加载图片
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!webView.getSettings().getLoadsImagesAutomatically()) {
                    webView.getSettings().setLoadsImagesAutomatically(true);
                }
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 加载网页失败时处理  如：
                view.loadDataWithBaseURL(null,
                        "<div align=\"center\"><br><span style=\"color:#242424;display:block;padding-top:50px\">数据加载失败</span></div>",
                        "text/html",
                        "utf-8",
                        null);
            }
        });
    }

}
