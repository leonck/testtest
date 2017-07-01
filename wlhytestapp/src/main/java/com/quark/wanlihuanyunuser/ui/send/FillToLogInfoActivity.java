package com.quark.wanlihuanyunuser.ui.send;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.quark.api.auto.bean.ListProduct;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.base.BaseFragementActivity;
import com.quark.wanlihuanyunuser.fragment.ShopOneLogisticsFragment;
import com.quark.wanlihuanyunuser.fragment.ShopTwoLogisticsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pan on 2016/11/9 0009.
 * >#
 * >#商家代发 填写物流信息
 */
public class FillToLogInfoActivity extends BaseFragementActivity {
    @InjectView(R.id.one_tv)
    TextView oneTv;
    @InjectView(R.id.two_tv)
    TextView twoTv;

    private ShopOneLogisticsFragment shopOneLogisticsFragment;
    private ShopTwoLogisticsFragment shopTwoLogisticsFragment;
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private int current = 0;
    String daifa_orders_company_id;
    String logistics_company_id;
    String money;

    ArrayList<ListProduct> newGoodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getSupportFragmentManager();
        if (savedInstanceState != null) {
            shopOneLogisticsFragment = (ShopOneLogisticsFragment) fragmentManager.findFragmentByTag("shopOneLogisticsFragment");
            shopTwoLogisticsFragment = (ShopTwoLogisticsFragment) fragmentManager.findFragmentByTag("shopTwoLogisticsFragment");
        }
        setContentView(R.layout.activity_thedoor);
        ButterKnife.inject(this);
        setTopTitle("填写物流信息");
        setBackButton();

        daifa_orders_company_id = (String) getValue4Intent("daifa_orders_company_id");
        logistics_company_id = (String) getValue4Intent("logistics_company_id");
        newGoodsList = (ArrayList<ListProduct>) getValue4Intent("list");
        money = (String) getValue4Intent("money");
        setTabSelection(current);
    }

    private void setTabSelection(int index) {
        clearState();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                oneTv.setTextColor(ContextCompat.getColor(this, R.color.mywhite));
                oneTv.setBackground(ContextCompat.getDrawable(this, R.drawable.active_tab_1));
                if (shopOneLogisticsFragment == null) {
                    shopOneLogisticsFragment = new ShopOneLogisticsFragment();
                    transaction.add(R.id.content_two, shopOneLogisticsFragment, "shopOneLogisticsFragment");
                    Bundle bundle = new Bundle();
                    bundle.putString("daifa_orders_company_id", daifa_orders_company_id);
                    bundle.putString("logistics_company_id", logistics_company_id);
                    bundle.putSerializable("list", newGoodsList);
                    bundle.putString("money",money);
                    shopOneLogisticsFragment.setArguments(bundle);
                } else {
                    transaction.show(shopOneLogisticsFragment);
                }

                break;
            case 1:
                twoTv.setTextColor(ContextCompat.getColor(this, R.color.mywhite));
                twoTv.setBackground(ContextCompat.getDrawable(this, R.drawable.active_tab_1));
                if (shopTwoLogisticsFragment == null) {
                    shopTwoLogisticsFragment = new ShopTwoLogisticsFragment();
                    transaction.add(R.id.content_two, shopTwoLogisticsFragment, "shopTwoLogisticsFragment");
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("daifa_orders_company_id", daifa_orders_company_id);
                    bundle2.putString("logistics_company_id", logistics_company_id);
                    bundle2.putSerializable("list", newGoodsList);
                    bundle2.putString("money",money);
                    shopTwoLogisticsFragment.setArguments(bundle2);
                } else {
                    transaction.show(shopTwoLogisticsFragment);
                }
                break;

        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {

        if (shopOneLogisticsFragment != null) {

            transaction.hide(shopOneLogisticsFragment);
        }
        if (shopTwoLogisticsFragment != null) {

            transaction.hide(shopTwoLogisticsFragment);
        }
    }


    @OnClick({R.id.one_tv, R.id.two_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one_tv:
                current = 0;
                setTabSelection(0);
                break;
            case R.id.two_tv:
                current = 1;
                setTabSelection(1);
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

        oneTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        twoTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        oneTv.setBackground(null);
        twoTv.setBackground(null);

    }
}
