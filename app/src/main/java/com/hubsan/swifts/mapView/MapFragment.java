package com.hubsan.swifts.mapView;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.hubsan.swifts.R;
import com.hubsan.swifts.SwiftsApplication;
import com.hubsan.swifts.helpers.polygon.PolygonPoint;
import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces;
import com.hubsansdk.utils.LogX;
import com.utils.AirMarkerThread;
import com.utils.Constants;
import com.utils.PreferenceUtils;

import java.util.List;

/**
 * @project name: X-Hubsan
 * @class name：com.csk.hbsdrone.fragments
 * @class describe: 基本地图 只用做定位显示当前位置
 * @anthor shengkun.cheng
 * @time 2017/2/25 21:30
 * @change
 * @chang time:
 * @class describe:
 */
//public class MapFragment extends Fragment {
public class MapFragment extends Fragment implements LocationSource,
        AMapLocationListener {

    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private int STROKE_COLOR = Color.argb(0, 0, 0, 0);
    private int FILL_COLOR = Color.argb(0, 0, 0, 0);
    private HubsanDrone drone;
    private SwiftsApplication app;
    private UiSettings mUiSettings;
    private Marker marker, loctionMarker;
    private MarkerOptions mMarkerOptions, locationMarkerOptions;
    private LatLng mLatLng;
    private boolean mapIsTouch = true;
    private float rotateAngle = 0;
    private boolean loctionSuccess = false;
    private double airLat, airLon;
    private float airRotate;
    private boolean airIsSelect;
    private AirMarkerThread airMarkerThread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View mView = inflater.inflate(R.layout.hubsan_fragment_automap, viewGroup, false);
        mapView = (MapView) mView.findViewById(R.id.map);
        mapView.onCreate(bundle);// 此方法必须重写
        init();
        airMarkerThread = new AirMarkerThread();
        return mView;
    }

    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
            setUpMap();
        }
    }

    public AMap getaMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        return aMap;
    }

    /*初始化当前位置*/
    public void initLocation() {
        String lat = PreferenceUtils.getPrefString(getActivity(), Constants.GET_LAT, "");
        String lon = PreferenceUtils.getPrefString(getActivity(), Constants.GET_LNG, "");
        mMarkerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.hubsan_map_location)).draggable(true);
        if ((lat.equals("") || lat == "") && (lon.equals("") || lon == "")) {
            mMarkerOptions.position(new LatLng(0, 0));
        } else {
            mMarkerOptions.position(new LatLng(Double.valueOf(lat), Double.valueOf(lon)));
        }
        loctionMarker = aMap.addMarker(mMarkerOptions);
        loctionMarker.setRotateAngle(rotateAngle);//图片旋转的角度，从正北开始，逆时针计算。
    }


    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        app = (SwiftsApplication) HubsanApplication.getApplication();
        this.drone = app.drone;
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        setupLocationStyle();
        mUiSettings.setZoomControlsEnabled(false);//设置地图默认的缩放按钮是否显示
        mUiSettings.setScaleControlsEnabled(true);//显示比例尺
        mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);//设置比例尺和显示位置
    }

    /**
     * 设置自定义定位蓝点
     */
    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.hubsan_map_location));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        initLocation();
        mMarkerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.hubsan_air_loction)).draggable(true);
        marker = aMap.addMarker(mMarkerOptions);
        marker.setPosition(new LatLng(airLat, airLon));
        marker.setRotateAngle(-airRotate + rotateAngle);//图片旋转的角度，从正北开始，逆时针计算。
    }

    /**
     * 设置地图不能拖动
     * Description
     *
     * @param isScroll
     * @see [class/class#field/class#method]
     */
    public void isScrollGestures(boolean isScroll) {
        mUiSettings.setScrollGesturesEnabled(isScroll);//设置地图是否可以手势滑动
        mUiSettings.setZoomGesturesEnabled(isScroll);//设置地图是否可以手势缩放大小
        mUiSettings.setTiltGesturesEnabled(isScroll);//设置地图是否可以倾斜
        mUiSettings.setRotateGesturesEnabled(isScroll);//设置地图是否可以旋转
    }

    /**
     * Description 设置地图显示的类型
     *
     * @param layerName
     * @see [class/class#field/class#method]
     */
    public void setLayer(int layerName) {
        if (aMap != null) {
            if (layerName == 0) {
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);
            } else if (layerName == 1) {
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
            } else if (layerName == 2) {
                aMap.setMapType(AMap.MAP_TYPE_NIGHT);
            }
        }
    }

    /**
     * 动态添加飞机位置
     *
     * @param lat
     * @param lon
     * @param rotate 旋转的角度，从正北开始，逆时针计算
     */
    public void addMMarkerMap(double lat, double lon, float rotate, boolean isSelect) {
        this.airLat = lat;
        this.airRotate = rotate;
        this.airLon = lon;
        this.airIsSelect = isSelect;
        if (airMarkerThread != null) {
            airMarkerThread.post(airRunnbale);
        }
    }

    private Runnable airRunnbale = new Runnable() {
        @Override
        public void run() {
            setAirToMap();
        }
    };

    private void setAirToMap() {
        if (aMap != null) {
            try {
                boolean mapCenter = PreferenceUtils.getPrefBoolean(getActivity(), Constants.SETTING_MAP_CENTER_OPEN_CLOSE, false);
                if (mapCenter == true && drone.airHeartBeat.isMotorStatus() == true) {
                    if (mapIsTouch == true) {
                        if (app.localLatLon.getMapZoom() == 0) {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(airLat, airLon), 18));
                        } else {
                            aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(airLat, airLon)));
                        }
                    }
                } else {
                    if (airIsSelect == true) {
                        if (app.localLatLon.getMapZoom() == 0) {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(airLat, airLon), 20));
                        } else {
                            aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(airLat, airLon)));
                        }
                    }
                }
                marker.setPosition(new LatLng(airLat, airLon));
                marker.setRotateAngle(-airRotate + rotateAngle);//图片旋转的角度，从正北开始，逆时针计算。
                marker.setToTop();//设置当前marker在最上面。
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addNoNetWorkMarker(double lat, double lon) {
        try {
            loctionMarker.setPosition(new LatLng(lat, lon));
            loctionMarker.setRotateAngle(0);//图片旋转的角度，从正北开始，逆时针计算。
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Mapview旋转角度
     *
     * @param rotateAngle
     */
    public void setMapViewRotateAngle(float rotateAngle) {
        this.rotateAngle = rotateAngle;
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
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                double geoLat = Double.valueOf(String.format("%.7f", amapLocation.getLatitude()));
                double geoLng = Double.valueOf(String.format("%.7f", amapLocation.getLongitude()));
                LogX.e("===========================================地图得到的数据===================================（"+amapLocation.getLatitude()+","+amapLocation.getLongitude()+")");
                app.localLatLon.setLocationMapLat(geoLat);
                app.localLatLon.setLocationMapLon(geoLng);
                if (app.localLatLon.getLocalGPSNumber() == 0) {
                    drone.events.notifyDroneEvent(HubsanDroneInterfaces.DroneEventsType.HUBSAN_NO_GPS_LOCATION);
                }
                if (drone.airConnectionStatus.isAirConnection()) {
                    if (app.localLatLon.getLocalGPSNumber() <= 5) {
//                        LogX.e("定位成功后回调函数 geoLat:" + geoLat + "," + geoLng + "    ,getLocalGPSNumber:" + app.localLatLon.getLocalGPSNumber());
                        addNoNetWorkMarker(geoLat, geoLng);
                    }
                } else {
                    addNoNetWorkMarker(geoLat, geoLng);
                }
                PreferenceUtils.setPrefString(getActivity(), Constants.GET_LAT, String.valueOf(geoLat));
                PreferenceUtils.setPrefString(getActivity(), Constants.GET_LNG, String.valueOf(geoLng));
                aMap.setMyLocationRotateAngle(0);//设置旋转角度位置
                loctionSuccess = true;
                if (showMyPositon){
                    findMyLocation(geoLat,geoLng);
                    showMyPositon =false;
                }
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
//                LogX.e(errText);
                loctionSuccess = false;
            }
        } else {
            LogX.e("定位失败");
        }
    }

    boolean showMyPositon = true;

    private void setZoom() {
        if (aMap != null) {
            float bearing = aMap.getCameraPosition().bearing;
            if (bearing < 10) {
                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                aMap.setMyLocationRotateAngle(17);
            } else {
                aMap.moveCamera(CameraUpdateFactory.zoomTo(bearing));
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setLocationCacheEnable(true);//使用缓存
            mLocationOption.setWifiActiveScan(true);
            setZoom();
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * Description 找到当前所在位置
     *
     * @see [class/class#field/class#method]
     */
    public void findMyLocation(double lat, double lon) {
        if (aMap != null) {
            LogX.e("findMyLocation lat:" + lat + "  ," + lon);
            if (lat != 0 && lon != 0) {
                if (app.localLatLon.getMapZoom() == 0) {
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 15));
                } else {
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), app.localLatLon.getMapZoom()));
                }
            }
        }
    }

    /**
     * 主要是为了让航点显示在地图正中
     */
    public void findWaypointLocation(double lat, double lon) {
        LogX.e("findMyLocation lat:" + lat + "  ," + lon);
        if (aMap != null) {
            if (lat != 0 && lon != 0) {
                if (app.localLatLon.getMapZoom() == 0) {
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 15));
                } else {
//                aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(getMyLocation(lat, lon), app.localLatLon.getMapZoom(), 0, 0)));
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), app.localLatLon.getMapZoom()));
                }
            }
        }
    }

    public void setaMapTouch(boolean touch) {
        this.mapIsTouch = touch;
    }

    // size ==13: 大图传小地图
    public void findAirLocation(double lat, double lon, int size) {
        if (aMap != null) {
            if (app.localLatLon.getMapZoom() == 0) {
                aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(lat, lon), size, 0, 0)));
            } else {
                if (size == 13) {
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 13));
                    aMap.setMyLocationRotateAngle(13);
                } else {
                    if (app.localLatLon.getMapZoom() <= 13) {
                        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(lat, lon), 18, 0, 0)));
                    }
                }
            }
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        mapView.onPause();
        deactivate();
        super.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        if (airMarkerThread != null) {
            airMarkerThread.destoeyThread();
            LogX.e("map onDestroy");
        }
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
        if (marker != null) {
            marker.remove();
            marker.destroy();
            marker = null;
        }
        if (mMarkerOptions != null) {
            mMarkerOptions = null;
        }
        if (aMap != null) {
            aMap.clear();
            aMap = null;
        }
        if (mapView != null) {
            mapView.removeAllViews();
            mapView.onDestroy();
        }
        super.onDestroy();
    }

    public void cleanMap(){
        aMap.clear();
    }

//    List<LatLng> points;
//    private Polyline missionPath;
    public void addMarks(List<LatLng> newPath) {
        for (int i = 0, size = newPath.size(); i < size; i++) {
            MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_wp_map))
                    .position(newPath.get(i))
                    .draggable(true)
                    .anchor(0.5f, 0.5f); //设置Marker覆盖物的锚点比例。
            Marker marker = aMap.addMarker(markerOption);
            PolygonPoint p = new PolygonPoint(newPath.get(i).latitude, newPath.get(i).longitude);
        }
        update(newPath);
    }

    public void update(List<LatLng> points) {
        PolylineOptions flightPath = new PolylineOptions();
        flightPath.color(ContextCompat.getColor(getActivity(), R.color.hubasn_connection_status_true_color)).setUseTexture(true);
        flightPath.addAll(points);
        Polyline missionPath = aMap.addPolyline(flightPath);
        missionPath.setPoints(points);
        missionPath.setVisible(true);
    }

    public void showAir(){
        double lat = drone.airGPS.getAirLat() + drone.locationGPS.getDifferenceLat();
        double lon = drone.airGPS.getAirLat() + drone.locationGPS.getDifferenceLon();
    }

}
