package com.quark.wanlihuanyunuser.ui.personal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.base.BaseFragementActivity;
import com.quark.wanlihuanyunuser.fragment.NotPayFragment;
import com.quark.wanlihuanyunuser.fragment.OverdueOrderFragment;
import com.quark.wanlihuanyunuser.fragment.PaitFragment;
import com.quark.wanlihuanyunuser.util.TLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.quark.wanlihuanyunuser.R.id.two_tv;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#我的订单
 */
public class MyorderActivity extends BaseFragementActivity {
    @InjectView(R.id.one_tv)
    TextView oneTv;
    @InjectView(R.id.one_iv)
    ImageView oneIv;
    @InjectView(two_tv)
    TextView twoTv;
    @InjectView(R.id.two_iv)
    ImageView twoIv;
    @InjectView(R.id.three_tv)
    TextView threeTv;
    @InjectView(R.id.three_iv)
    ImageView threeIv;
    @InjectView(R.id.pager)
    ViewPager pager;

    private List<Fragment> fragmentList;
    int current = 0;
    public static MyorderActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.inject(this);
        setTopTitle("我的订单");
        setBackButton();
        instance = this;
        initpage();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public void setNotPayNumber(int number) {
        if (number>0){
            oneTv.setText("待支付(" + number + ")");
        }else{
            oneTv.setText("待支付");
        }
    }

    public void setPaidNumber(int number) {
        if (number>0) {
            twoTv.setText("支付完成(" + number + ")");
        }else{
            twoTv.setText("支付完成");
        }
    }

    public void setOverdueNumber(int number) {
        if (number>0) {
            threeTv.setText("失效订单(" + number + ")");
        }else{
            threeTv.setText("失效订单");
        }
    }

    public void initpage() {
        NotPayFragment notPayFragment = new NotPayFragment();
        PaitFragment paitFragment = new PaitFragment();
        OverdueOrderFragment overdueOrderFragment = new OverdueOrderFragment();

        fragmentList = new ArrayList();
        fragmentList.add(notPayFragment);
        fragmentList.add(paitFragment);
        fragmentList.add(overdueOrderFragment);

        MyPagerAdapter myFragmentAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(myFragmentAdapter);
        pager.addOnPageChangeListener(tabOnPageChangeListener);
        pager.setOffscreenPageLimit(3);
        pager.setCurrentItem(0);
    }

    @OnClick({R.id.one_ly, R.id.two_ly, R.id.three_ly})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one_ly:
                current = 0;
                pager.setCurrentItem(0);
                break;
            case R.id.two_ly:
                current = 1;
                pager.setCurrentItem(1);
                break;
            case R.id.three_ly:
                current = 2;
                pager.setCurrentItem(2);
                break;
        }
    }

    /**
     * ViewPager的适配器
     *
     * @author zj
     *         2012-5-24 下午2:26:57
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragmentList.get(arg0);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        /**
         * 注销super
         * 设置fragemnt不重新加载
         *
         * @author pan
         * @time 2016/10/28 0028 上午 10:15
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            TLog.error("销毁" + position);
        }
    }

    ViewPager.OnPageChangeListener tabOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            cleantab(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void cleantab(int current) {

        oneTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        oneIv.setVisibility(View.INVISIBLE);
        twoTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        twoIv.setVisibility(View.INVISIBLE);
        threeTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        threeIv.setVisibility(View.INVISIBLE);
        if (current == 0) {
            oneTv.setTextColor(ContextCompat.getColor(this, R.color.blue));
            oneIv.setVisibility(View.VISIBLE);
        } else if (current == 1) {
            twoTv.setTextColor(ContextCompat.getColor(this, R.color.blue));
            twoIv.setVisibility(View.VISIBLE);
        } else if (current == 2) {
            threeTv.setTextColor(ContextCompat.getColor(this, R.color.blue));
            threeIv.setVisibility(View.VISIBLE);
        }
    }
}
