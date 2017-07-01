package com.hubsan.swifts.mapUtils.marks;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.hubsan.swifts.helpers.polygon.PolygonPoint;

/**
 * 
 * Description 
 * 			多边形标记
 * @author ShengKun.Cheng
 * @date  2015年7月28日
 * @version 
 * @see [class/class#method]
 * @since [product/model]
 */
public class PolygonMarker {

	public static MarkerOptions build(PolygonPoint wp) {
		return new MarkerOptions()
				.position(wp.coord)
				.draggable(true)
				.title("Poly")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
	}

	public static void update(Marker marker, PolygonPoint wp) {
		marker.setPosition(wp.coord);
		marker.setTitle("Poly");
		marker.setIcon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
	}

}
