package com.quark.wanlihuanyunuser.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.ui.send.ShopSendActivity;
import com.quark.wanlihuanyunuser.ui.send.TheDoorActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pan on 2016/11/8 0008.
 * >#
 * >#寄件
 */
public class MiddleActivity extends BaseActivity {

    @InjectView(R.id.one_ly)
    LinearLayout oneLy;
    @InjectView(R.id.two_ly)
    LinearLayout twoLy;
    @InjectView(R.id.cancel_iv)
    ImageView cancelIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle);
        ButterKnife.inject(this);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.one_ly, R.id.two_ly, R.id.cancel_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one_ly:
                startActivityByClass(TheDoorActivity.class);
                break;
            case R.id.two_ly:
                startActivityByClass(ShopSendActivity.class);
                break;
            case R.id.cancel_iv:
                finish();
                break;
        }
    }
}
