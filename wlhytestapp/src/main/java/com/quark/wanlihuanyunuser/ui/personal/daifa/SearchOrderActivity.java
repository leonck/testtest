package com.quark.wanlihuanyunuser.ui.personal.daifa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SearchOrderActivity extends BaseActivity {

    String keyword;

    @InjectView(R.id.left)
    LinearLayout left;
    @InjectView(R.id.title)
    EditText title;
    @InjectView(R.id.right)
    LinearLayout right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);
        keyword = (String) getValue4Intent("keyword");
        if (!Utils.isEmpty(keyword)) {
            title.setText(keyword);
        }
        title.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((InputMethodManager) title.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchOrderActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    String keyword = title.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("keyword", keyword);
                    setResult(102, intent);
                    finish();
                    return true;
                }
                return false;
            }
        });

        title.setFocusable(true);
        title.setFocusableInTouchMode(true);
        title.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) title.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(title, 0);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) title.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(title, 0);
                           }
                       },
                998);
    }

    @OnClick({R.id.right, R.id.left})
    public void ba(View v) {
        finish();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


}
