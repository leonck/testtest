package com.quark.wanlihuanyunuser.ui.send;

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
 * >#快递条款
 */
public class TermsActivity extends BaseActivity {

    WebView webView;
    WebSettings webSettings;
    String type = "1";//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargestandardweb);
        setTopTitle("快递条款");
        setBackButton();

        webView = (WebView) findViewById(R.id.web);
        webSettings = webView.getSettings();

        String url = ApiHttpClient.HOSTURL + "/app/CommonManage/otherH5?type=" + type;
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
