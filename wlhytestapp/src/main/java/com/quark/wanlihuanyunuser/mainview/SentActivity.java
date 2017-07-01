package com.quark.wanlihuanyunuser.mainview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.ui.send.ShopSendActivity;
import com.quark.wanlihuanyunuser.ui.send.TheDoorActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#>#弹出发货
 */
public class SentActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.layout)
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.Transparent);
        setContentView(R.layout.activity_middle);
        ButterKnife.inject(this);
        layout.getBackground().setAlpha(230);

        LinearLayout btnCamera = (LinearLayout) findViewById(R.id.one_ly);
        LinearLayout btnAlbum = (LinearLayout) findViewById(R.id.two_ly);
        LinearLayout btnCancel = (LinearLayout) findViewById(R.id.di_ly);

        btnCamera.setOnClickListener(this);
        btnAlbum.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one_ly:
//                setTheme(R.style.AppTheme);
                Intent intent1 = new Intent(this, TheDoorActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.two_ly:
//                setTheme(R.style.AppTheme);
                Intent intent2 = new Intent(this, ShopSendActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.di_ly:
//                setTheme(R.style.AppTheme);
                finish();
                break;
        }
    }
}
