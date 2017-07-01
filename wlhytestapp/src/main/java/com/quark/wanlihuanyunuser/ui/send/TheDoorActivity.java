package com.quark.wanlihuanyunuser.ui.send;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.base.BaseFragementActivity;
import com.quark.wanlihuanyunuser.fragment.OneLogisticsFragment;
import com.quark.wanlihuanyunuser.fragment.TwoLogisticsFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pan on 2016/11/9 0009.
 * >#
 * >#上门取件
 */
public class TheDoorActivity extends BaseFragementActivity {

    @InjectView(R.id.one_tv)
    TextView oneTv;
    @InjectView(R.id.two_tv)
    TextView twoTv;

    private OneLogisticsFragment oneLogisticsFragment;
    private TwoLogisticsFragment twoLogisticsFragment;
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private int current = 0;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getSupportFragmentManager();
        if (savedInstanceState != null) {
            oneLogisticsFragment = (OneLogisticsFragment) fragmentManager.findFragmentByTag("oneLogisticsFragment");
            twoLogisticsFragment = (TwoLogisticsFragment) fragmentManager.findFragmentByTag("twoLogisticsFragment");
        }
        setContentView(R.layout.activity_thedoor);
        ButterKnife.inject(this);
        setTopTitle("上门取件");
        setBackButton();
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
                if (oneLogisticsFragment == null) {
                    oneLogisticsFragment = new OneLogisticsFragment();
                    transaction.add(R.id.content_two, oneLogisticsFragment, "oneLogisticsFragment");
                } else {
                    transaction.show(oneLogisticsFragment);
                }
                break;
            case 1:
                twoTv.setTextColor(ContextCompat.getColor(this, R.color.mywhite));
                twoTv.setBackground(ContextCompat.getDrawable(this, R.drawable.active_tab_1));
                if (twoLogisticsFragment == null) {
                    twoLogisticsFragment = new TwoLogisticsFragment();
                    transaction.add(R.id.content_two, twoLogisticsFragment, "twoLogisticsFragment");
                } else {
                    transaction.show(twoLogisticsFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (oneLogisticsFragment != null) {
            transaction.hide(oneLogisticsFragment);
        }
        if (twoLogisticsFragment != null) {
            transaction.hide(twoLogisticsFragment);
        }
    }

    @OnClick({R.id.one_tv, R.id.two_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one_tv: //如果没有添加包裹不用弹出提示
                if (twoLogisticsFragment != null && twoLogisticsFragment.hasDatas()) {
                    initDialog();
                } else {
                    if (current == 1) {
                        current = 0;
                        setTabSelection(0);
                    } else if (current == 0) {
                        current = 1;
                        setTabSelection(1);
                    }
                }
                break;
            case R.id.two_tv:
                if (oneLogisticsFragment != null && oneLogisticsFragment.hasDatas()) {
                    initDialog();
                } else {
                    if (current == 1) {
                        current = 0;
                        setTabSelection(0);
                    } else if (current == 0) {
                        current = 1;
                        setTabSelection(1);
                    }
                }
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

    private void initDialog() {
        final Dialog dlg = new Dialog(this, R.style.ActionSheet);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_normal_layout, null);
        final int cFullFillWidth = 10000;
        layout.setMinimumWidth(cFullFillWidth);
        TextView positiveButton = (TextView) layout.findViewById(R.id.positiveButton);
        TextView negativeButton = (TextView) layout.findViewById(R.id.negativeButton);
        positiveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (current == 1) {
                    current = 0;
                    twoLogisticsFragment.cleanData();
                    setTabSelection(0);
                } else if (current == 0) {
                    current = 1;
                    oneLogisticsFragment.cleanData();
                    setTabSelection(1);
                }
                dlg.dismiss();
            }
        });

        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.gravity = Gravity.CENTER;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(false);
        dlg.setContentView(layout);
        dlg.show();
    }
}




