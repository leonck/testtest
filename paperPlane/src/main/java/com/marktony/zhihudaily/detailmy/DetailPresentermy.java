package com.marktony.zhihudaily.detailmy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.webkit.WebView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.marktony.zhihudaily.R;
import com.marktony.zhihudaily.bean.StringModelImpl;
import com.marktony.zhihudaily.bean.ZhihuDailyStory;
import com.marktony.zhihudaily.customtabs.CustomFallback;
import com.marktony.zhihudaily.customtabs.CustomTabActivityHelper;
import com.marktony.zhihudaily.interfaze.OnStringListener;
import com.marktony.zhihudaily.util.Api;

import static android.R.attr.id;

/**
 * Created by Administrator on 2017/2/22 0022.
 * #Date:2017-02-22 18:37
 * #User Leon
 */

public class DetailPresentermy implements DetailContractMy.Presenter{
    Context context;
    DetailContractMy.View view;
    private StringModelImpl model;
    ZhihuDailyStory zhihuDailyStory;
    int id;
    String title;

    public DetailPresentermy(Context context,DetailContractMy.View view){
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public void start() {

    }

    @Override
    public void requestData() {
        view.showLoading();
        model.load(Api.ZHIHU_NEWS + id, new OnStringListener() {
            @Override
            public void onSuccess(String result) {
                {
                    Gson gson = new Gson();
                    try {
                        zhihuDailyStory = gson.fromJson(result, ZhihuDailyStory.class);
                        if (zhihuDailyStory.getBody() == null) {
                            view.showResultWithoutBody(zhihuDailyStory.getShare_url());
                        } else {
                            view.showResult(convertZhihuContent(zhihuDailyStory.getBody()));
                        }
                    } catch (JsonSyntaxException e) {
                        view.showError();
                    }
                    view.showLoadingStop();
                }
            }

            @Override
            public void onError(VolleyError error) {
                view.showLoadingStop();
                view.showError();
            }
        });
    }

    @Override
    public void openUrl(WebView view, String url) {
//        if (sp.getBoolean("in_app_browser", true)) {
            CustomTabsIntent.Builder customTabsIntent = new CustomTabsIntent.Builder()
                    .setToolbarColor(context.getResources().getColor(R.color.colorAccent))
                    .setShowTitle(true);
            CustomTabActivityHelper.openCustomTab(
                    (Activity) context,
                    customTabsIntent.build(),
                    Uri.parse(url),
                    new CustomFallback() {
                        @Override
                        public void openUri(Activity activity, Uri uri) {
                            super.openUri(activity, uri);
                        }
                    }
            );
//        } else {
//
//            try {
//                context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
//            } catch (android.content.ActivityNotFoundException ex) {
//                view.showBrowserNotFoundError();
//            }
//
//        }
    }
    private String convertZhihuContent(String preResult) {

        preResult = preResult.replace("<div class=\"img-place-holder\">", "");
        preResult = preResult.replace("<div class=\"headline\">", "");

        // 在api中，css的地址是以一个数组的形式给出，这里需要设置
        // in fact,in api,css addresses are given as an array
        // api中还有js的部分，这里不再解析js
        // javascript is included,but here I don't use it
        // 不再选择加载网络css，而是加载本地assets文件夹中的css
        // use the css file from local assets folder,not from network
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";


        // 根据主题的不同确定不同的加载内容
        // load content judging by different theme
        String theme = "<body className=\"\" onload=\"onLoaded()\">";
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES) {
            theme = "<body className=\"\" onload=\"onLoaded()\" class=\"night\">";
        }

        return new StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n")
                .append("\t<meta charset=\"utf-8\" />")
                .append(css)
                .append("\n</head>\n")
                .append(theme)
                .append(preResult)
                .append("</body></html>").toString();
    }
}
