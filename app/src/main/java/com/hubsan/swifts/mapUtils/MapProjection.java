package com.hubsan.swifts.mapUtils;

import android.graphics.Point;


import com.amap.api.maps.AMap;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Description 
 * 			地图投影
 * @author ShengKun.Cheng
 * @date  2015年7月28日
 * @version 
 * @see [class/class#method]
 * @since [product/model]
 */
public class MapProjection {
	
	public static List<LatLng> projectPathIntoMap(List<Point> path,AMap map) {
		List<LatLng> coords = new ArrayList<LatLng>();
		Projection projection = map.getProjection();
		for (Point point : path) {
			coords.add(projection.fromScreenLocation(point));
		}
		return coords;
	}
}
