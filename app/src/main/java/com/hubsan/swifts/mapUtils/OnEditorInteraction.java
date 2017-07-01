package com.hubsan.swifts.mapUtils;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.hubsan.swifts.drone.variables.mission.MissionItem;

public interface OnEditorInteraction {
//    public boolean onItemLongClick(MissionItem item);

    public void onItemClick(MissionItem item);

    public void onMapClick(LatLng coord);
//
//    public void onListVisibilityChanged();
//
//    public void onListClear();
//
    public void onFocusClick(Marker marker);
//
//    public void onGuideClick(Marker marker);

    public void onLatLonClick(double lat, double lon);
//    void onLatLonCancel();

}
