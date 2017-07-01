package com.quark.wanlihuanyunuser.googlemap;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class LocationUtils {
    //通过地址获取经纬度  存在跟google地图导航结果有偏差

    public static LatLng getLatlng(String locationName, Context context) {
        Log.e("error", "转换地址：" + locationName);
        int maxResults = 5;
        Geocoder geoCoder = new Geocoder(context);
        List<Address> addressList = null;
        try {
            addressList = geoCoder.getFromLocationName(locationName, maxResults);
        } catch (IOException e) {
            Toast.makeText(context,"无法连接到地址解析服务器",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        LatLng position = null;
        if (addressList == null || addressList.isEmpty()) {
            Log.e("error", "没有找到该地址");
        } else {
            Address address = addressList.get(0);
            double lat = address.getLatitude();
            double lng = address.getLongitude();
            Log.e("error", "获得经纬度：" + lat + "  " + lng);
            address.getAdminArea();

            position = new LatLng(lat, lng);
        }
        return position;
    }


//新西兰国内地址修改
    public static Address getLatlng(String locationName, Context context,int type) {
        Log.e("error", "转换地址：" + locationName);
        int maxResults = 5;
        Geocoder geoCoder = new Geocoder(context);
        List<Address> addressList = null;
        try {
            addressList = geoCoder.getFromLocationName(locationName, maxResults);
        } catch (IOException e) {
            Toast.makeText(context,"无法连接到地址解析服务器",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        Address address = null;
        if (addressList == null || addressList.isEmpty()) {
            Log.e("error", "没有找到该地址");
        } else {
            address = addressList.get(0);
        }
        return address;
    }
}



