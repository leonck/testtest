package com.marktony.zhihudaily.detailmy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.marktony.zhihudaily.R;
import com.marktony.zhihudaily.detail.DetailActivity;

import static com.marktony.zhihudaily.R.id.imageView;
import static com.marktony.zhihudaily.R.id.refreshLayout;

/**
 * Created by Administrator on 2017/2/22 0022.
 * #Date:2017-02-22 18:38
 * #User Leon
 */


public class DetailFragmentMy extends Fragment implements DetailContractMy.View{
    SwipeRefreshLayout refreshLayout;
    WebView webView;
    DetailContractMy.Presenter presenter;
    ImageView imageView;
    NestedScrollView scrollView;
    CollapsingToolbarLayout toolbarLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view  = inflater.inflate(R.layout.universal_read_layout,container,false);
        initViews(view);
        setHasOptionsMenu(true);

        presenter.requestData();

        return view;
    }

    @Override
    public void setPresenter(DetailContractMy.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);

        webView = (WebView) view.findViewById(R.id.web_view);
        webView.setScrollbarFadingEnabled(true);

        DetailMyActivity activity = (DetailMyActivity) getActivity();
        activity.setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) view.findViewById(R.id.image_view);
        scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
        toolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.toolbar_layout);

        //能够和js交互
        webView.getSettings().setJavaScriptEnabled(true);
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        webView.getSettings().setBuiltInZoomControls(false);
        //缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //开启DOM storage API功能
        webView.getSettings().setDomStorageEnabled(true);
        //开启application Cache功能
        webView.getSettings().setAppCacheEnabled(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                presenter.openUrl(view, url);
                return true;
            }
        });
    }

    @Override
    public void showLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void showLoadingStop() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showError() {
        Snackbar.make(imageView,"加载失败",Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.requestData();
            }
        }).show();
    }


    @Override
    public void showRefresh() {

    }

    @Override
    public void showResult(String result) {
        webView.loadDataWithBaseURL("x-data://base",result,"text/html","utf-8",null);
    }

    @Override
    public void showResultWithoutBody(String url) {
        webView.loadUrl(url);
    }

}



