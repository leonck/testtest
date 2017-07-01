package com.test;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hubsan.mymapdemo.R;


/**
 * AMapV2地图中介绍如何显示一个基本地图
 */
public class BasicMapActivity extends Activity {
    BasicMapFragmen basicMapFragmen;
    FragmentManager fragmentManager;

    FrameLayout mapView;
    LinearLayout cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_changebigsmall);
        fragmentManager = getFragmentManager();
        initMap();
        mapView = (FrameLayout) findViewById(R.id.mapview);
        cameraView = (LinearLayout) findViewById(R.id.hubsanPlayCamera);

    }

    public void initMap() {
        if (basicMapFragmen == null) {
            basicMapFragmen = new BasicMapFragmen();
            fragmentManager.beginTransaction().add(R.id.mapview, basicMapFragmen).commit();
        }
    }


    /**
     * 进入地图界面，地图在下，图传在上
     */
    public void selectMap() {

        RelativeLayout.LayoutParams layoutParamsMap = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mapView.setLayoutParams(layoutParamsMap);
        cameraView.bringToFront();
        RelativeLayout.LayoutParams layoutParamsCamera = new RelativeLayout.LayoutParams(800, 500);
        cameraView.setLayoutParams(layoutParamsCamera);

    }

    /**
     * 选择照相机，照相机位全屏，地图位小图
     */
    public void selectCamera() {
        RelativeLayout.LayoutParams layoutParamsCamera = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        cameraView.setLayoutParams(layoutParamsCamera);
        mapView.bringToFront();
        RelativeLayout.LayoutParams layoutParamsMap = new RelativeLayout.LayoutParams(600, 400);
        mapView.setLayoutParams(layoutParamsMap);

    }

    boolean wintag = false;

    public void change(View v) {
        if (wintag) {
            wintag = false;
            selectCamera();
        } else {
            wintag = true;
            selectMap();
        }
    }

}
