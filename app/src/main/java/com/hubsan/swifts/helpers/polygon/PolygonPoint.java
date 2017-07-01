package com.hubsan.swifts.helpers.polygon;

import android.content.Context;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.hubsan.swifts.mapUtils.marks.MarkerManager;
import com.hubsan.swifts.mapUtils.marks.PolygonMarker;

public class PolygonPoint implements MarkerManager.MarkerSource
{

	public LatLng coord;

	public PolygonPoint(Double lat, Double lng) {
		coord = new LatLng(lat, lng);
	}

	public PolygonPoint(LatLng coord) {
		this.coord = coord;
	}

	@Override
	public MarkerOptions build(Context context) {
		return PolygonMarker.build(this);
	}

	@Override
	public void update(Marker marker, Context context) {
		PolygonMarker.update(marker, this);

	}
}
