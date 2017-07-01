package com.quark.wanlihuanyunuser.util;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ListQuarkWebViewClient extends WebViewClient { 
	private Context context;
	public ListQuarkWebViewClient(Context context){
		this.context = context;
	}
	
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) { 
//    	 view.loadUrl(url); 
    	
        return true; 
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
} 
