package com.quark.wanlihuanyunuser.ui.personal;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.base.BaseFragementActivity;
import com.quark.wanlihuanyunuser.fragment.ExpiredFragment;
import com.quark.wanlihuanyunuser.fragment.SaapunutFragment;
import com.quark.wanlihuanyunuser.fragment.TransportFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#我的快递
 */
public class MyCourierActivity extends BaseFragementActivity {

    @InjectView(R.id.two_iv)
    ImageView twoIv;
    @InjectView(R.id.three_iv)
    ImageView threeIv;
    @InjectView(R.id.four_iv)
    ImageView fourIv;
    @InjectView(R.id.two_tv)
    TextView twoTv;
    @InjectView(R.id.three_tv)
    TextView threeTv;
    @InjectView(R.id.four_tv)
    TextView fourTv;

    private int current = 1;

//    private TakeFragment takeFragment;
    private TransportFragment transportFragment;
    private SaapunutFragment saapunutFragment;
    private ExpiredFragment expiredFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getSupportFragmentManager();
        if (savedInstanceState != null) {
//            takeFragment = (TakeFragment) fragmentManager.findFragmentByTag("takeFragment");
            transportFragment = (TransportFragment) fragmentManager.findFragmentByTag("transportFragment");
            saapunutFragment = (SaapunutFragment) fragmentManager.findFragmentByTag("saapunutFragment");
            expiredFragment = (ExpiredFragment) fragmentManager.findFragmentByTag("expiredFragment");
        }
        setContentView(R.layout.activity_mycourier);
        ButterKnife.inject(this);
        setTopTitle("我的快递");
        setBackButton();

        setTabSelection(current);
    }

    private void setTabSelection(int index) {
        clearState();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
//            case 0:
//                oneIv.setVisibility(View.VISIBLE);
//                oneTv.setTextColor(ContextCompat.getColor(this, R.color.blue));
//                if (takeFragment == null) {
//                    takeFragment = new TakeFragment();
//                    transaction.add(R.id.content_two, takeFragment, "takeFragment");
//                } else {
//                    transaction.show(takeFragment);
//                }
//                break;
            case 1:
                twoIv.setVisibility(View.VISIBLE);
                twoTv.setTextColor(ContextCompat.getColor(this, R.color.blue));

                if (transportFragment == null) {
                    transportFragment = new TransportFragment();
                    transaction.add(R.id.content_two, transportFragment, "transportFragment");
                } else {
                    transaction.show(transportFragment);
                }
                break;
            case 2:
                threeIv.setVisibility(View.VISIBLE);
                threeTv.setTextColor(ContextCompat.getColor(this, R.color.blue));

                if (saapunutFragment == null) {
                    saapunutFragment = new SaapunutFragment();
                    transaction.add(R.id.content_two, saapunutFragment, "saapunutFragment");
                } else {
                    transaction.show(saapunutFragment);
                }
                break;
            case 3:
                fourIv.setVisibility(View.VISIBLE);
                fourTv.setTextColor(ContextCompat.getColor(this, R.color.blue));

                if (expiredFragment == null) {
                    expiredFragment = new ExpiredFragment();
                    transaction.add(R.id.content_two, expiredFragment, "expiredFragment");
                } else {
                    transaction.show(expiredFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
//        if (takeFragment != null) {
//            transaction.hide(takeFragment);
//        }
        if (transportFragment != null) {
            transaction.hide(transportFragment);
        }
        if (saapunutFragment != null) {
            transaction.hide(saapunutFragment);
        }
        if (expiredFragment != null) {
            transaction.hide(expiredFragment);
        }
    }

    @OnClick({R.id.two_ly, R.id.three_ly, R.id.four_ly})
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.one_ly:
//                current = 0;
//                setTabSelection(0);
//                break;
            case R.id.two_ly:
                current = 1;
                setTabSelection(1);
                break;
            case R.id.three_ly:
                current = 2;
                setTabSelection(2);
                break;
            case R.id.four_ly:
                current = 3;
                setTabSelection(3);
                break;
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public void clearState() {
//        oneIv.setVisibility(View.INVISIBLE);
        twoIv.setVisibility(View.INVISIBLE);
        threeIv.setVisibility(View.INVISIBLE);
        fourIv.setVisibility(View.INVISIBLE);
//        oneTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        twoTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        threeTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        fourTv.setTextColor(ContextCompat.getColor(this, R.color.black));
    }
}
