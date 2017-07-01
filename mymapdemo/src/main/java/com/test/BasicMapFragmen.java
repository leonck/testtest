package com.test;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.hubsan.mymapdemo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * AMapV2地图中介绍如何显示一个基本地图
 */
public class BasicMapFragmen extends Fragment implements OnClickListener {
	private MapView mapView;
	private AMap aMap;
	private Button basicmap;
	private Button rsmap;
	private Button nightmap;
	private Button navimap;

	private CheckBox mStyleCheckbox;
	
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.basicmap_activity, container, false);
		mapView = (MapView)view.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写

		init();
		return view;
	}




	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
		}
		setMapCustomStyleFile(getActivity());
		basicmap = (Button)view.findViewById(R.id.basicmap);
		basicmap.setOnClickListener(this);
		rsmap = (Button)view.findViewById(R.id.rsmap);
		rsmap.setOnClickListener(this);
		nightmap = (Button)view.findViewById(R.id.nightmap);
		nightmap.setOnClickListener(this);
		navimap = (Button)view.findViewById(R.id.navimap);
		navimap.setOnClickListener(this);

		mStyleCheckbox = (CheckBox) view.findViewById(R.id.check_style);

		mStyleCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				aMap.setMapCustomEnable(b);
			}
		});

	}

	private void setMapCustomStyleFile(Context context) {
		String styleName = "style_json.json";
		FileOutputStream outputStream = null;
		InputStream inputStream = null;
		String filePath = null;
		try {
			inputStream = context.getAssets().open(styleName);
			byte[] b = new byte[inputStream.available()];
			inputStream.read(b);

			filePath = context.getFilesDir().getAbsolutePath();
			File file = new File(filePath + "/" + styleName);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			outputStream = new FileOutputStream(file);
			outputStream.write(b);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();

				if (outputStream != null)
					outputStream.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		aMap.setCustomMapStylePath(filePath + "/" + styleName);

		aMap.showMapText(false);

	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
    public void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
    public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
    public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.basicmap:
				aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
				break;
			case R.id.rsmap:
				aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
				break;
			case R.id.nightmap:
				aMap.setMapType(AMap.MAP_TYPE_NIGHT);//夜景地图模式
				break;
			case R.id.navimap:
				aMap.setMapType(AMap.MAP_TYPE_NAVI);//导航地图模式
				break;
		}

		mStyleCheckbox.setChecked(false);

	}

}
