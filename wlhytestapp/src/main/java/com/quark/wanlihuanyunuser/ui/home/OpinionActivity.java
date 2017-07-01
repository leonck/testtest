package com.quark.wanlihuanyunuser.ui.home;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.FeedBackRequest;
import com.quark.api.auto.bean.FeedBackResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/11/8 0008.
 * >#
 * >#投诉建议
 */
public class OpinionActivity extends BaseActivity {

    @InjectView(R.id.content_et)
    EditText contentEt;
    @InjectView(R.id.sumbit_bt)
    Button sumbitBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        ButterKnife.inject(this);
        setTopTitle("投诉建议");
        setBackButton();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.sumbit_bt)
    public void onClick() {
        if (Utils.isEmpty(contentEt.getText().toString())) {
            showToast("请填写您的意见");
        } else {
            getData();
        }

    }

    public void getData() {
        FeedBackRequest rq = new FeedBackRequest();
        rq.setUser_type(AppParam.user_type);
        rq.setContent(contentEt.getText().toString());
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, OpinionActivity.this, FeedBackResponse.class);
            if (kd != null) {
                FeedBackResponse info = (FeedBackResponse) kd;
                if (info.getStatus() == 1) {
                    showToast(info.getMessage());
                    finish();
                } else {
                    showToast(info.getMessage());
                }
            }
            showWait(false);
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
            showWait(false);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            showWait(false);
        }
    };

}
