package com.quark.wanlihuanyunuser.ui.personal.daifa;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.quark.wanlihuanyunuser.R;

public class ShopSendExpressActivity extends AppCompatActivity {
    ShopSendExpressFragment fragment;
    ShopSendExpressPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_send_express);

        if (savedInstanceState!=null){
            fragment = (ShopSendExpressFragment)getSupportFragmentManager().getFragment(savedInstanceState,"shopSendExpressFragment");
        }else{
            fragment = new ShopSendExpressFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
        }
        presenter = new ShopSendExpressPresenter(this,fragment);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "fragmentMy", fragment);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!=0){
            switch (resultCode){
                case 102:
                    String kw = data.getStringExtra("keyword");
                    presenter.getData(kw,true);
                    break;
            }
        }
    }
}
