package com.quark.wanlihuanyunuser.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pan on 2016/11/9 0009.
 * >#
 * >#输入客户姓名查询
 */
public class SearchUserNameActivity extends BaseActivity {


    @InjectView(R.id.search_et)
    EditText searchEt;
    @InjectView(R.id.cancel)
    TextView cancel;
    String kw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchuser);
        ButterKnife.inject(this);

        searchEt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    kw = searchEt.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("kw",kw);
                    setResult(101,intent);
                    finish();
                    return true;
                }
                return false;
            }
        });


    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }



    @OnClick({R.id.left, R.id.cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left:
                finish();
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }
}
