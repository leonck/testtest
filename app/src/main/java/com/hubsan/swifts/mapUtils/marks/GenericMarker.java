package com.hubsan.swifts.mapUtils.marks;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

/**
 * 
 * Description 
 * 				通用的标记
 * @author ShengKun.Cheng
 * @date  2015年7月28日
 * @version 
 * @see [class/class#method]
 * @since [product/model]
 */
public class GenericMarker {

	public static MarkerOptions build(LatLng coord) {
		return new MarkerOptions().position(coord).draggable(true).anchor(0.5f, 0.5f);
	}

}
