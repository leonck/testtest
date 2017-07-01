package com.marktony.zhihudaily.detailmy;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.marktony.zhihudaily.R;

public class DetailMyActivitydd extends AppCompatActivity {

    DetailFragmentMy fragmentMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_my);
        if (savedInstanceState != null) {
            fragmentMy = (DetailFragmentMy) getSupportFragmentManager().getFragment(savedInstanceState, "detailFragmentMy");
        } else {
            fragmentMy = new DetailFragmentMy();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentMy).commit();
        }

        Intent intent = getIntent();
        DetailPresentermy detailPresentermy = new DetailPresentermy(this,fragmentMy);
        detailPresentermy.setId(intent.getIntExtra("id", 0));
        detailPresentermy.setTitle(intent.getStringExtra("title"));
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (fragmentMy.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "fragmentMy", fragmentMy);
        }
    }
}
