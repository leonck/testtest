package com.marktony.zhihudaily.detailmy;

import android.webkit.WebView;

import com.marktony.zhihudaily.BasePresenter;
import com.marktony.zhihudaily.BaseView;

/**
 * Created by Administrator on 2017/2/22 0022.
 * #Date:2017-02-22 18:36
 * #User Leon
 */

public class DetailContractMy {

    interface View extends BaseView<Presenter>{
        void showLoading();
        void showLoadingStop();
        void showError();
        void showResult(String result);
        void showRefresh();
        void showResultWithoutBody(String url);



    }

    interface Presenter extends BasePresenter{
        void requestData();
        void openUrl(WebView view , String url);

    }
}
