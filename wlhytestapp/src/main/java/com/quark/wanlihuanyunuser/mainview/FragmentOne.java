package com.quark.wanlihuanyunuser.mainview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.base.BaseFragment;
import com.quark.wanlihuanyunuser.fragment.OneFg;
import com.quark.wanlihuanyunuser.fragment.TwoFg;
import com.quark.wanlihuanyunuser.ui.home.QueryResultActivity;
import com.quark.wanlihuanyunuser.ui.personal.MyCourierActivity;
import com.quark.wanlihuanyunuser.ui.personal.MyorderActivity;
import com.quark.wanlihuanyunuser.ui.personal.myConcerned.ConcernedActivity;
import com.quark.wanlihuanyunuser.util.TLog;
import com.quark.wanlihuanyunuser.util.Utils;
import com.quark.wanlihuanyunuser.zxing.SecondActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

@SuppressLint("ResourceAsColor")
public class FragmentOne extends BaseFragment implements EasyPermissions.PermissionCallbacks {
    @InjectView(R.id.order_et)
    EditText orderEt;
    @InjectView(R.id.one_tv)
    TextView oneTv;
    @InjectView(R.id.two_tv)
    TextView twoTv;
    @InjectView(R.id.pager)
    ViewPager pager;
    @InjectView(R.id.one_iv)
    ImageView oneIv;
    @InjectView(R.id.two_iv)
    ImageView twoIv;

    View oneViewt;
    DrawerLayout menuLayout;
    private List<Fragment> fragmentList;
    int current = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneViewt = inflater.inflate(R.layout.fragment_one, container, false);
        ButterKnife.inject(this, oneViewt);
        initpage();

        return oneViewt;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.left_menu)
    public void onClick() {
        menuLayout = (DrawerLayout) getActivity().findViewById(R.id.drawerLayout);
        menuLayout.openDrawer(Gravity.LEFT);
    }

    public static final int REQUEST_CODE = 111;

    @OnClick({R.id.one_ly, R.id.two_ly, R.id.three_ly,R.id.four_ly, R.id.go, R.id.one, R.id.two})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one_ly:
                Intent in1 = new Intent(getActivity(), MyCourierActivity.class);
                startActivity(in1);
                break;
            case R.id.two_ly:
                startByPermissions();
                break;
            case R.id.three_ly:
                Intent in3 = new Intent(getActivity(), MyorderActivity.class);
                startActivity(in3);
                break;
            case R.id.go:
                if (!Utils.isEmpty(orderEt.getText().toString())) {
                    Intent in4 = new Intent(getActivity(), QueryResultActivity.class);
                    in4.putExtra("order_number", orderEt.getText().toString());
                    startActivity(in4);
                } else {
                    showToast("请输入运单号");
                }
                break;
            case R.id.one:
                current = 0;
                pager.setCurrentItem(0);
                break;
            case R.id.two:
                current = 1;
                pager.setCurrentItem(1);
                break;
            case R.id.four_ly:
                Intent inCon = new Intent(getActivity(), ConcernedActivity.class);
                startActivity(inCon);
                break;
        }
    }


    public void toScanne() {
        Intent intent = new Intent(getActivity(), SecondActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void initpage() {
        OneFg oneFg = new OneFg();
        TwoFg twoFg = new TwoFg();
        fragmentList = new ArrayList();
        fragmentList.add(oneFg);
        fragmentList.add(twoFg);

        MyPagerAdapter myFragmentAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(myFragmentAdapter);
        pager.addOnPageChangeListener(tabOnPageChangeListener);
        pager.setCurrentItem(0);
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
        oneTv.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        oneIv.setVisibility(View.INVISIBLE);
        twoTv.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        twoIv.setVisibility(View.INVISIBLE);

        if (current == 0) {
            oneTv.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue));
            oneIv.setVisibility(View.VISIBLE);
        } else if (current == 1) {
            twoTv.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue));
            twoIv.setVisibility(View.VISIBLE);
        }
    }

    private static final int CAMERA_PERM = 1;

    @AfterPermissionGranted(CAMERA_PERM)
    private void startByPermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            try {
                toScanne();
            } catch (Exception e) {
                Toast.makeText(getActivity(), R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
            }
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.str_request_camera_message), CAMERA_PERM, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        try {
            toScanne();
        } catch (Exception e) {
            Toast.makeText(getActivity(), R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(getActivity(), R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}





