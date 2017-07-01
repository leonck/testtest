package com.quark.wanlihuanyunuser.ui.personal.myConcerned;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.quark.wanlihuanyunuser.R;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#我的快递
 */
public class ConcernedActivity extends AppCompatActivity {
    ConcernedFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop_send_express);
        if (savedInstanceState!=null){
            fragment = (ConcernedFragment)getSupportFragmentManager().getFragment(savedInstanceState,"shopSendExpressFragment");
        }else{
            fragment = new ConcernedFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
        }
        ConcernedPresenter presenter = new ConcernedPresenter(this,fragment);
    }
}
