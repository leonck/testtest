package com.quark.wanlihuanyunuser.ui.home;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.api.ApiHttpClient;
import com.quark.wanlihuanyunuser.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pan on 2016/9/8 0008.
 * >#
 * >#轮播广告详情 web
 */
public class AdDetailActivity extends BaseActivity {

    WebView webView;
    WebSettings webSettings;
    String index_banner_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        ButterKnife.inject(this);
        setBackButton();
        index_banner_id = (String) getValue4Intent("index_banner_id");
        webView = (WebView) findViewById(R.id.web);
        webSettings = webView.getSettings();
//        h5Request();
        String url = ApiHttpClient.HOSTURL + "/app/IndexBannerManage/indexBannerDetail?index_banner_id=" + index_banner_id;
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

//    public void h5Request() {
//        OtherH5Request gr = new OtherH5Request();
//        gr.setType(type);
//        QuarkApi.HttpRequestNewList(gr, mHandlercode);
//    }
//
//    private final AsyncHttpResponseHandler mHandlercode = new AsyncHttpResponseHandler() {
//        @Override
//        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//            try {
//                String ds = new String(arg2, "UTF-8");
//                TLog.error(ds+"++++ds+++++");
//                showHtml(ds);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            showWait(false);
//        }
//
//        @Override
//        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//            AppContext.showToast("网络出错" + arg0);
//        }
//    };
//
//    public void showHtml(String content) {
////        String content="";
//        webView.getSettings().setDefaultTextEncodingName("utf-8");// 避免中文乱码
//
//        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//
//        webSettings = webView.getSettings();
//
//        webSettings.setJavaScriptEnabled(true);
//
//        webSettings.setNeedInitialFocus(false);
//
//        webSettings.setSupportZoom(true);
//
//        webSettings.setLoadWithOverviewMode(true);//适应屏幕
//
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//
//        webSettings.setLoadsImagesAutomatically(true);//自动加载图片
//
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT | WebSettings.LOAD_CACHE_ELSE_NETWORK);
//
//        Document doc_Dis = Jsoup.parse(content);
//
//        Elements ele_Img = doc_Dis.getElementsByTag("img");
//
//        if (ele_Img.size() != 0) {
//            for (Element e_Img : ele_Img) {
//                e_Img.attr("width", "100%");
//                //一定要设置 auto 不要控制其高度，让其自己跟随宽度变化情况调整
//                e_Img.attr("height", "auto");
//            }
//        }
//
//        String newHtmlContent = doc_Dis.toString();
//        webView.loadDataWithBaseURL(null, newHtmlContent, "text/html", "utf-8", null);
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                if (!webView.getSettings().getLoadsImagesAutomatically()) {
//                    webView.getSettings().setLoadsImagesAutomatically(true);
//                }
//                super.onPageFinished(view, url);
//            }
//
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
//                // 加载网页失败时处理  如：
//                view.loadDataWithBaseURL(null,
//                        "<div align=\"center\"><br><span style=\"color:#242424;display:block;padding-top:50px\">数据加载失败</span></div>",
//                        "text/html",
//                        "utf-8",
//                        null);
//            }
//        });
//    }

    @OnClick(R.id.right)
    public void onClick() {
        finish();
    }
}
