package com.quark.wanlihuanyunuser.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.UpdateOtherRequest;
import com.quark.api.auto.bean.UpdateOtherResponse;
import com.quark.wanlihuanyunuser.AppContext;
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
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#修改昵称
 */
public class EditNameActivity extends BaseActivity {

    @InjectView(R.id.sign)
    TextView sign;
    @InjectView(R.id.rightrig)
    ImageView rightrig;
    @InjectView(R.id.right)
    LinearLayout right;
    @InjectView(R.id.name_et)
    EditText nameEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editname);
        ButterKnife.inject(this);
        setTopTitle("");
        setBackButton();

        right.setVisibility(View.VISIBLE);
        sign.setText("确定");

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.sign)
    public void onClick() {

        if (Utils.isEmpty(nameEt.getText().toString())) {
            showToast("请输入昵称");

        }else {
            getData();
        }

    }

     public void getData() {
             UpdateOtherRequest rq = new UpdateOtherRequest();
             rq.setNickname(nameEt.getText().toString());
             showWait(true);
             QuarkApi.HttpRequest(rq, mHandler);
         }

      private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
          @Override
          public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
              Object kd = ApiResponse.get(arg2, EditNameActivity.this, UpdateOtherResponse.class);
              if (kd!=null){
                  UpdateOtherResponse info = (UpdateOtherResponse)kd;
                  if (info.getStatus() == 1){
                      Intent in = new Intent();
                      in.putExtra("name",nameEt.getText().toString());
                      setResult(101,in);
                      finish();
                  }else {
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
