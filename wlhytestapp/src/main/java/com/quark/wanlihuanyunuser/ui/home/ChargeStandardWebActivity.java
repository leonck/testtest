package com.quark.wanlihuanyunuser.ui.home;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.api.ApiHttpClient;
import com.quark.wanlihuanyunuser.base.BaseActivity;

/**
 * Created by pan on 2016/11/8 0008.
 * >#
 * >#收费标准  web详细内容
 */
public class ChargeStandardWebActivity extends BaseActivity {


    WebView webView;
    WebSettings webSettings;
    String id;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargestandardweb);
        setTopTitle("收费标准");
        setBackButton();

        webView = (WebView) findViewById(R.id.web);
        webSettings = webView.getSettings();

        id = (String) getValue4Intent("id");
        String url = ApiHttpClient.HOSTURL + "/app/CommonManage/feeStandardDetail?logistics_id=" + id;
        showWebInfo(url);
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
